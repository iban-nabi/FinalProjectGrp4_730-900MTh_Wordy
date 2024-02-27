import Tkinter
from Other.project_utils import centered_window


def how_to_play(game, name):
    global root
    global game_actions
    global username
    game_actions = game
    root = Tkinter.Tk()
    username = name
    root.title("How to Play")
    root.geometry("1152x648")
    root.resizable(False, False)
    centered_window(root)

    root.config(bg="#36454F")

    label = Tkinter.Label(
        text="\nWELCOME TO WORDY!\nUpon starting a new game, you will be automatically added to an "
             "existing\n lobby with other players. If no lobbies exist, you will be made the host "
             "of \none. If 10 seconds pass and you are the sole player in the lobby, you will \n" +
             "be returned to the Main Menu. The game will only start if there is at least \n" +
             "one other player in the lobby. \n\n" +
             "At the start of the game, you will be presented with 17 random letters\n" +
             "(with 5-7 vowels), and all players must try to send valid words using these\n" +
             "letters. All words must be at least 5 letters long, and can repeat letters from\n" +
             "the 17 random letters. Submit as many words as you can within 10 seconds,\n" +
             "and the player who submitted the longest word will be the winner as soon as\n" +
             "the round ends. No winners will be declared in the event of a tie, or if no \n" +
             "player submitted a valid word.\n\nThe first player to win 3 rounds will be declared "
             "the eventual winner.\nGood luck!\n", font=("Arial Bold", 15), fg="white", bg="#36454F")
    label.pack(pady=20)

    return_to_menu_button = Tkinter.Button(root, text="Return to Main Menu", font=("Arial Bold", 15),
                                           command=go_to_main)
    return_to_menu_button.pack(pady=20)
    return_to_menu_button.config(background="white")
    return_to_menu_button.config(foreground="black")


def go_to_main():
    import MainMenu
    root.destroy()
    MainMenu.main_menu(game_actions, username)
