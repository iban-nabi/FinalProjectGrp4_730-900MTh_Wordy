import Tkinter as tk
from Tkconstants import LEFT

from PIL import Image, ImageTk, ImageDraw, ImageFont
from Other import project_utils


def show_longest_word_leaderboards(game, name):
    global username
    global root
    global game_actions
    username=name
    game_actions=game
    long_words_list = game_actions.getLeaderBoardLongestWordsList()
    root = tk.Tk()
    root.geometry("1152x648")
    root.title("Longest Word Leaderboards")
    root.resizable(False, False)
    project_utils.centered_window(root)

    # background depends on the file location
    bg = ImageTk.PhotoImage(
        file=project_utils.root_path()+"res\\PLWLeaderboard.gif")
    canvas = tk.Canvas()
    canvas.pack(fill="both", expand=True)
    canvas.create_image(0, 0, image=bg, anchor="nw")
    # Add a text in canvas

    # Names

    canvas.create_text(490, 204, text=long_words_list[0][0], font=("Arial Bold", 15), fill='black')
    canvas.create_text(769, 204, text=long_words_list[0][1], font=("Arial Bold", 15), fill='black')  # name

    canvas.create_text(490, 270, text=long_words_list[1][0], font=("Arial Bold", 15), fill='black')
    canvas.create_text(769, 270, text=long_words_list[1][1], font=("Arial Bold", 15), fill='black')  # name

    canvas.create_text(490, 338, text=long_words_list[2][0], font=("Arial Bold", 15), fill='black')
    canvas.create_text(769, 338, text=long_words_list[2][1], font=("Arial Bold", 15), fill='black')  # name

    canvas.create_text(490, 402, text=long_words_list[3][0], font=("Arial Bold", 15), fill='black')
    canvas.create_text(769, 402, text=long_words_list[3][1], font=("Arial Bold", 15), fill='black')  # name

    canvas.create_text(490, 474, text=long_words_list[4][0], font=("Arial Bold", 15), fill='black')
    canvas.create_text(769, 474, text=long_words_list[4][1], font=("Arial Bold", 15), fill='black')  # name

    canvas.pack(side=LEFT)

    canvas.return_to_menu_button = tk.Button(canvas, text="Return to Main Menu", font=("Arial Bold", 15),
                                             command=go_to_main_menu)
    canvas.return_to_menu_button.config(background="red")
    canvas.return_to_menu_button.config(foreground="white")
    canvas.return_to_menu_button.place(x=255, y=550)

    canvas.win_leaderboards_button = tk.Button(canvas, text="Win Leaderboards", font=("Arial Bold", 15),
                                             command=go_to_win_leaderboards)
    canvas.win_leaderboards_button.config(background="green")
    canvas.win_leaderboards_button.config(foreground="white")
    canvas.win_leaderboards_button.place(x=705, y=550)

    root.resizable(False, False)
    root.mainloop()


def go_to_main_menu():
    from Other import MainMenu
    root.destroy()
    MainMenu.main_menu(game_actions, username)


def go_to_win_leaderboards():
    import WinLeaderboardGUI
    root.destroy()
    WinLeaderboardGUI.show_winleaderboards(game_actions, username)
