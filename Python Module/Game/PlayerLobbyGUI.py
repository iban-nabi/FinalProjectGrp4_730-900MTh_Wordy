import Tkinter
import Other.project_utils
from PIL import ImageTk


def start_new_game(game, name):
    global game_actions
    global game_id
    global username
    global root
    game_actions = game
    username = name
    game_id = game_actions.joinGame(username)
    root = Tkinter.Tk()
    root.title("")
    root.resizable(False, False)
    Other.project_utils.centered_window(root)
    root.geometry("840x521")

    bg = ImageTk.PhotoImage(
        file=Other.project_utils.root_path() + "res\\lobby.gif")
    canvas = Tkinter.Canvas()
    canvas.pack(fill="both", expand=True)
    canvas.create_image(0, 0, image=bg, anchor="nw")

    waiting_label = canvas.create_text(420, 100, text="Waiting for other players...", font=("Arial Bold", 25),
                                       fill='white')

    seconds_left = [game_actions.getTimer(game_id) - 1]
    countdown_label = canvas.create_text(420, 150, text="Time left: {}".format(seconds_left) + "s", font=("Arial Bold", 25),
                                         fill='white')

    def countdown():
        seconds_left[0] += 1
        canvas.itemconfig(countdown_label, text=str(seconds_left[0]))
        if seconds_left[0] == 11:
            no_of_players = len(game_actions.getGamePlayers(game_id))
            if no_of_players == 1:
                canvas.itemconfig(waiting_label, text="No players found!", fill="darkred")
                canvas.delete(countdown_label)
                root.after(3000, go_to_main_menu)

            else:
                canvas.delete(countdown_label)
                canvas.itemconfig(waiting_label, text=str(no_of_players) + " player(s) found!")
                root.after(2000, go_to_initialize_gui)
                # dispose this
        else:
            root.after(1000, countdown)

    countdown()
    root.mainloop()


def go_to_main_menu():
    from Other import MainMenu
    root.destroy()
    MainMenu.main_menu(game_actions, username)


def go_to_initialize_gui():
    from Game import InitializeGUI
    root.destroy()
    InitializeGUI.initialize_gui(game_actions, game_id, username)

