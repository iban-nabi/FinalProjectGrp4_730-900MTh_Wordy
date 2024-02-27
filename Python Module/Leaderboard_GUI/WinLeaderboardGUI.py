import Tkinter as tk
from Tkconstants import LEFT
from PIL import ImageTk
from Other import project_utils
from LongestWordLeaderboardGUI import show_longest_word_leaderboards


def show_winleaderboards(game, name):
    global username
    global root
    global game_actions
    username=name
    game_actions = game
    winboards_list = game_actions.getLeaderboardWinList()
    root = tk.Tk()
    root.title("Main Menu")
    root.geometry("1152x648")
    root.resizable(False, False)
    project_utils.centered_window(root)

    # background depends on the file location
    bg = ImageTk.PhotoImage(
        file=project_utils.root_path() + "res\\PLeaderboard.gif")
    canvas = tk.Canvas()
    canvas.pack(fill="both", expand=True)
    canvas.create_image(0, 0, image=bg, anchor="nw")
    # Add a text in canvas

    canvas.return_to_menu_button = tk.Button(canvas, text="Return to Main Menu", font=("Arial Bold", 15),
                                             command=go_to_main_menu)
    canvas.return_to_menu_button.config(background="red")
    canvas.return_to_menu_button.config(foreground="white")
    canvas.return_to_menu_button.place(x=270, y=580)

    canvas.longest_word_button = tk.Button(canvas, text="Longest Word LBoards", font=("Arial Bold", 15),
                                           command=show_longest_word_game)
    canvas.longest_word_button.config(background="green")
    canvas.longest_word_button.config(foreground="white")
    canvas.longest_word_button.place(x=650, y=580)

    canvas.create_text(580, 200, text="Note: Only players with 1 or more wins qualify for the leaderboard!",
                       font=("Arial Bold", 13), fill='yellow')


    # # Names
    canvas.create_text(370, 240, text=winboards_list[0][1], font=("Arial Bold", 15), fill='white')
    canvas.create_text(370, 310, text=winboards_list[1][1], font=("Arial Bold", 15), fill='white')
    canvas.create_text(370, 376, text=winboards_list[2][1], font=("Arial Bold", 15), fill='white')
    canvas.create_text(370, 444, text=winboards_list[3][1], font=("Arial Bold", 15), fill='white')
    canvas.create_text(370, 508, text=winboards_list[4][1], font=("Arial Bold", 15), fill='white')
    # Wins
    canvas.create_text(850, 240, text=winboards_list[0][0], font=("Arial Bold", 15), fill='white')
    canvas.create_text(850, 310, text=winboards_list[1][0], font=("Arial Bold", 15), fill='white')
    canvas.create_text(850, 376, text=winboards_list[2][0], font=("Arial Bold", 15), fill='white')
    canvas.create_text(850, 444, text=winboards_list[3][0], font=("Arial Bold", 15), fill='white')
    canvas.create_text(850, 508, text=winboards_list[4][0], font=("Arial Bold", 15), fill='white')
    canvas.pack(side=LEFT)

    root.mainloop()


def go_to_main_menu():
    from Other import MainMenu
    root.destroy()
    MainMenu.main_menu(game_actions, username)


def show_longest_word_game():
    root.destroy()
    show_longest_word_leaderboards(game_actions, username)
