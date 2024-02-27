import Tkinter as tk
from Other.HowToPlayGUI import how_to_play
from Other.CreditsGUI import credits
from Game.PlayerLobbyGUI import start_new_game
import Other.project_utils
from PIL import ImageTk
from Leaderboard_GUI.WinLeaderboardGUI import show_winleaderboards


def join_game():
    root.destroy()
    start_new_game(game_actions, username)


def show_leaderboards():
    root.destroy()
    show_winleaderboards(game_actions, username)
    # show_longest_word_leaderboards(game_actions)


def show_how_to_play():
    root.destroy()
    how_to_play(game_actions, username)


def show_credits():
    root.destroy()
    credits(game_actions, username)


def view_credits():
    root.destroy()


def exit_game():
    root.destroy()
    game_actions.logout(username)


def main_menu(game, name):
    global root
    global game_actions
    global username
    username = name
    game_actions = game
    root = tk.Tk()
    root.title("Main Menu")
    root.geometry("840x450")
    root.resizable(False, False)
    Other.project_utils.centered_window(root)

    # background depends on the file location
    bg = ImageTk.PhotoImage(
        file=Other.project_utils.root_path() + "res\\clientMenu.gif")
    canvas = tk.Canvas()
    canvas.pack(fill="both", expand=True)
    canvas.create_image(0, 0, image=bg, anchor="nw")

    canvas.create_text(580, 35, text="WORDY: The Game", font=("Agency FB", 35, "bold"), fill='white')

    canvas.new_game_button = tk.Button(canvas, text="New Game", font=("Agency FB", 15, "bold"), width=20,
                                       command=join_game)
    canvas.new_game_button.config(background="darkgreen")
    canvas.new_game_button.config(foreground="white")
    canvas.new_game_button.place(x=550, y=100)

    canvas.leaderboards_button = tk.Button(canvas, text="Leaderboards", font=("Agency FB", 15, "bold"),
                                           width=20, command=show_leaderboards)
    canvas.leaderboards_button.config(background="darkgreen")
    canvas.leaderboards_button.config(foreground="white")
    canvas.leaderboards_button.place(x=550, y=170)

    canvas.how_to_play_button = tk.Button(canvas, text="How To Play", font=("Agency FB", 15, "bold"),
                                          width=20, command=show_how_to_play)
    canvas.how_to_play_button.config(background="darkgreen")
    canvas.how_to_play_button.config(foreground="white")
    canvas.how_to_play_button.place(x=550, y=240)

    canvas.credits_button = tk.Button(canvas, text="Credits", font=("Agency FB", 15, "bold"),
                                      width=20, command=show_credits)
    canvas.credits_button.config(background="darkgreen")
    canvas.credits_button.config(foreground="white")
    canvas.credits_button.place(x=550, y=310)

    canvas.exit_button = tk.Button(canvas, text="Exit Game", font=("Agency FB", 15, "bold"),
                                   width=20, command=exit_game)
    canvas.exit_button.config(background="darkgreen")
    canvas.exit_button.config(foreground="white")
    canvas.exit_button.place(x=550, y=380)

    canvas.create_text(18, 401, text="Logged In As: " + username, font=("Agency FB", 20, "bold"), fill='white', anchor="nw")
    root.mainloop()
