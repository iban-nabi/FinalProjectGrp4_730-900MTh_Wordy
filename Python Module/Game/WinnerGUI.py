import Tkinter as tk

import Other.project_utils
from PIL import ImageTk


def show_winner(game, name, id, number):  # game, name, id, number
    global game_actions
    global username
    global game_id
    global round_number
    global root
    game_actions = game
    username = name
    game_id = id
    round_number = number
    # round_number = 1
    root = tk.Tk()
    root.geometry("1152x648")
    result = game_actions.getRoundWinner(game_id)  # get the round results

    if result != "tie":
        bg = ImageTk.PhotoImage(
            file=Other.project_utils.root_path() + "res\\Roundwinner.gif")
        canvas = tk.Canvas()
        canvas.pack(fill="both", expand=True)
        canvas.create_image(0, 0, image=bg, anchor="nw")

        round_winner = result
        split_string = round_winner.split(':')
        game_winner = game_actions.getGameWinner(game_id)

        if game_winner == " ":
            root.title("Round Winner")
            canvas.create_text(585, 150, text="Game Round " + str(round_number), font=("Agency FB", 35, "bold"),
                               fill='yellow')  # 1) same x,y
            name = split_string[0]  # split regex name using :
            canvas.create_text(575, 435, text="Player: " + name, font=("Times New Roman", 35, "bold"),
                               fill='yellow')  # 2) same x,y
            word = split_string[1]  # split regex name using :
            canvas.create_text(580, 564, text="Word: " + word, font=("Times New Roman", 20, "bold"), fill='yellow')
            root.after(5000, go_to_main_game)
        else:
            root.title("Game Winner")
            canvas.create_text(585, 150, text="Game Winner", font=("Agency FB", 35, "bold"),
                               fill='yellow')  # 1) same x,y
            canvas.create_text(575, 435, text="Winner: " + game_winner, font=("Times New Roman", 35, "bold"),
                               fill='yellow')  # 2) same x,y
            root.resizable(False, False)

            canvas.return_to_menu_button = tk.Button(canvas, text="Return to Main Menu", font=("Arial Bold", 15),
                                                     command=go_to_main_menu)
            canvas.return_to_menu_button.config(background="red")
            canvas.return_to_menu_button.config(foreground="white")
            canvas.return_to_menu_button.place(x=280, y=535)

            canvas.win_leaderboards_button = tk.Button(canvas, text="Win Leaderboards", font=("Arial Bold", 15),
                                                       command=go_to_win_leaderboards, width=19)
            canvas.win_leaderboards_button.config(background="orange")
            canvas.win_leaderboards_button.config(foreground="white")
            canvas.win_leaderboards_button.place(x=640, y=535)

            canvas.longest_word_button = tk.Button(canvas, text="Longest Word LBoards", font=("Arial Bold", 15),
                                                   command=show_longest_word_game)
            canvas.longest_word_button.config(background="green")
            canvas.longest_word_button.config(foreground="white")
            canvas.longest_word_button.place(x=640, y=580)

            # root.after(5000, open_new_gui)
            # add buttons

    else:
        root.title("Tie")
        bg = ImageTk.PhotoImage(
            file=Other.project_utils.root_path() + "res\\RoundDraw.gif")
        canvas = tk.Canvas()
        canvas.pack(fill="both", expand=True)
        canvas.create_image(0, 0, image=bg, anchor="nw")
        canvas.create_text(585, 120, text="ROUND " + str(round_number), font=("Agency FB", 35, "bold"),
                           fill='yellow')
        root.after(5000, go_to_main_game)

    root.mainloop()


def go_to_main_menu():
    from Client_Python.Other import MainMenu
    root.destroy()
    MainMenu.main_menu(game_actions, username)


def go_to_main_game():
    import MainGameGUI
    root.destroy()
    game_actions.initializeGame(game_id)
    MainGameGUI.game_gui(game_actions, game_id, username, int(round_number))


def show_longest_word_game():
    import Client_Python.Leaderboard_GUI.LongestWordLeaderboardGUI
    root.destroy()
    Client_Python.Leaderboard_GUI.LongestWordLeaderboardGUI.show_longest_word_leaderboards(game_actions, username)


def go_to_win_leaderboards():
    import Client_Python.Leaderboard_GUI.WinLeaderboardGUI
    root.destroy()
    Client_Python.Leaderboard_GUI.WinLeaderboardGUI.show_winleaderboards(game_actions, username)
