import Tkinter
from Other.project_utils import centered_window


def credits(game, name):
    global root
    global game_actions
    global username
    game_actions = game
    root = Tkinter.Tk()
    username = name
    root.title("Credits")
    root.geometry("1152x648")
    root.resizable(False, False)
    centered_window(root)

    root.config(bg="#36454F")

    label = Tkinter.Label(
        text="\nSaint Louis University\nSchool of Accountancy, Management, Computing, and Information Sciences\n" +
             "\nIT222L: Integrative Technologies\nFinal Laboratory Group Project\n\n" +
             "Group 4\nAromin, Eddyson Tristan\n" +
             "Arzadon, Maxwell John, A. \n" +
             "Del Rosario, Carliah Beatriz, M. \n" +
             "Lalwet, Carl Joshua, A. \n" +
             "Masaba, Jessalyn Mae, G.\n" +
             "Paguyo, Jan Ivan Ezra, D. \n" +
             "", font=("Arial Bold", 15), fg="white", bg="#36454F")
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
