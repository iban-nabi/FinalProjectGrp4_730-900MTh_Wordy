import Tkinter as tk
from PIL import ImageTk
import Other.project_utils
import threading


def game_gui(game, id_game, name, number):  # game, id_game, name, number
    global game_actions
    global game_id
    global username
    global round_number
    global root
    global canvas
    global flag
    game_actions = game
    game_id = id_game
    username = name
    round_number = number + 1
    flag = threading.Event()
    root = tk.Tk()
    root.title("WORDY")
    root.geometry("1152x648")
    root.resizable(False, False)
    Other.project_utils.centered_window(root)

    # background depends on the file location
    bg = ImageTk.PhotoImage(
        file=Other.project_utils.root_path() + "res\\Ingame.gif")
    canvas = tk.Canvas()
    canvas.pack(fill="both", expand=True)
    canvas.create_image(0, 0, image=bg, anchor="nw")

    letters = game_actions.getCharacters(game_id)
    sorted_letters = sorted(letters)
    concatenated_string = ' '.join(sorted_letters)

    canvas.create_text(120, 50, text="WINS: " + str(game_actions.getNoWinInGame(game_id, username)),
                       font=("Agency FB", 45, "bold"), fill='white')
    canvas.create_text(570, 50, text="ROUND " + str(round_number), font=("Agency FB", 45, "bold"), fill='white')

    canvas.create_text(574, 465, text=concatenated_string, font=("Agency FB", 19, "bold"), fill='white')

    # show sent words by other players
    playerList = [""]
    global list_one
    list_one = tk.Listbox(root, width=55, height=10, bg="mediumorchid4", fg="white", bd=10,
                          font=("Agency FB", 15, "bold"))
    scroll_one = tk.Scrollbar(root, command=list_one.yview)
    list_one.configure(yscrollcommand=scroll_one.set)

    thread = threading.Thread(target=show_input_words)
    thread.start()

    list_one.place(relx=0.5, rely=0.40, anchor="center")

    # text box for input
    global text_label
    text_label = canvas.create_text(345, 505, text="ENTER YOUR ANSWERS HERE:", font=("Arial", 15), fill="white",
                                    anchor="nw")
    global text_box
    text_box = tk.Text(root, height=3, width=35, font=("Century Gothic", 15, "bold"))
    text_box.place(relx=0.47, rely=0.87, anchor="center")
    text_box.bind("<Return>", enter_text_key)

    # enter button
    enter_button = tk.Button(root, height=2, width=7, text="Enter", bg="green4", fg="white",
                             font=("Agency FB", 15, "bold"), command=enter_text_button)
    # enter key
    enter_button.place(relx=0.69, rely=0.87, anchor="center")

    thread_run = threading.Thread(target=thread_run_game)
    thread_run.start()
    # countdown
    seconds_left = [game_actions.getTimer(game_id)]
    countdown_label = canvas.create_text(1030, 50, text="Time left: {}".format(seconds_left), font=("Arial Bold", 25),
                                         fill='white')

    # exit button
    canvas.exit_button = tk.Button(canvas, text="Exit", font=("Arial Bold", 15),
                                   command=exit_game, width=7)
    canvas.exit_button.config(background="red")
    canvas.exit_button.config(foreground="white")
    canvas.exit_button.place(x=1020, y=580)

    def countdown():
        global countdown_root
        canvas.itemconfig(countdown_label, text=str(seconds_left[0]))
        if seconds_left[0] < 10:
            countdown_root = root.after(950, countdown)
            seconds_left[0] += 1

    global root_go_to_winner
    root_go_to_winner = root.after(10000, go_to_winner_gui)
    countdown()
    root.mainloop()


def thread_run_game():
    game_actions.runGame(game_id)


def go_to_winner_gui():
    flag.set()
    try:
        root.after_cancel(update_text_root)
    except Exception:
        pass

    import WinnerGUI
    root.destroy()
    WinnerGUI.show_winner(game_actions, username, game_id, int(round_number))


def exit_game():
    flag.set()
    root.after_cancel(countdown_root)
    root.after_cancel(root_go_to_winner)
    try:
        root.after_cancel(update_text_root)
    except Exception:
        pass

    game_actions.quitGame(game_id, username)
    root.destroy()
    import Other.MainMenu
    Other.MainMenu.main_menu(game_actions, username)


def enter_text_key(event):
    text = text_box.get("1.0", "end-1c")
    text_box.delete("1.0", tk.END)
    try:
        print text
        game_actions.sendWord(game_id, username, str(text))
    except Exception as e:
        update_text_label(str(type(e).__name__))


    return "break"


def enter_text_button():
    text = text_box.get("1.0", "end-1c")
    text_box.delete("1.0", tk.END)
    try:
        print text
        game_actions.sendWord(game_id, username, str(text))
    except Exception as e:
        update_text_label(str(type(e).__name__))


def update_text_label(error):
    global update_text_root

    if error == "NoWordException":
        error_message = "NO SUCH WORD EXISTING! TRY AGAIN."
    elif error == "ShortWordException":
        error_message = "THE WORD IS TOO SHORT! TRY AGAIN."
    elif error == "WordRepeatedException":
        error_message = "REPEATING A WORD IS NOT ALLOWED! TRY AGAIN."
    else:
        error_message = "USE THE PROVIDED LETTERS! TRY AGAIN."

    canvas.itemconfig(text_label, text=error_message, fill="red", anchor="nw")
    update_text_root = root.after(2000, normalize_text_label)


def normalize_text_label():
    canvas.itemconfig(text_label, text="ENTER YOUR ANSWERS HERE:", fill="white", anchor="nw")


def show_input_words():
    list_of_entered_words = []
    while not flag.is_set():
        new_list_of_entered_words = game_actions.getEnteredWords(game_id)
        if len(new_list_of_entered_words) > len(list_of_entered_words) and new_list_of_entered_words[-1] != " ":
            print "pasok"
            list_of_entered_words = new_list_of_entered_words
            print new_list_of_entered_words
            print new_list_of_entered_words[0]
            print list_of_entered_words
            print list_of_entered_words[0]
            list_one.insert(tk.END, new_list_of_entered_words[len(new_list_of_entered_words)-1])
