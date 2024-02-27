import Tkinter as tk
from PIL import ImageTk
import Other.project_utils


def initialize_gui(game, id_game, name):
    global game_actions
    global username
    global game_id
    global root
    game_actions = game
    username = name
    game_id = id_game
    root = tk.Tk()

    game_actions.initializeGame(game_id)
    root.title("Game Initializing")
    root.geometry("1152x648")
    root.resizable(False, False)
    Other.project_utils.centered_window(root)

    # background depends on the file location
    bg = ImageTk.PhotoImage(
        file=Other.project_utils.root_path() + "res\\Initializing.gif")
    canvas = tk.Canvas()
    canvas.pack(fill="both", expand=True)
    canvas.create_image(0, 0, image=bg, anchor="nw")

    canvas.create_text(580, 90, text="Preparing Game...", font=("Agency FB", 35, "bold"), fill='white')

    canvas.create_text(580, 215, text="PLAYERS", font=("Agency FB", 35, "bold"), fill='white')

    playerList = game_actions.getGamePlayers(game_id)

    list_one = tk.Listbox(root, width=38, height=9, bg="mediumorchid4", fg="white", bd=20,
                          font=("Agency FB", 17, "bold"))
    scroll_one = tk.Scrollbar(root, command=list_one.yview)
    list_one.configure(yscrollcommand=scroll_one.set)

    for item in playerList:
        list_one.insert(tk.END, item)

    # list_one.insert(0, 'cali')
    # list_one.insert(1, 'bea')
    # list_one.insert(2, 'jessie')
    # list_one.insert(3, 'banie')
    # list_one.insert(4, 'joshie')
    # list_one.insert(5, 'maxiee')
    # list_one.insert(6, 'cali')
    # list_one.insert(7, 'bea')
    # list_one.insert(8, 'jessie')
    # list_one.insert(9, 'banie')
    # list_one.insert(10, 'joshie')
    # list_one.insert(11, 'maxiee')

    list_one.place(relx=0.50, rely=0.65, anchor="center")
    root.after(3500, go_to_main_game_gui)
    root.mainloop()


def go_to_main_game_gui():
    import MainGameGUI
    root.destroy()
    MainGameGUI.game_gui(game_actions, game_id, username, 0)
