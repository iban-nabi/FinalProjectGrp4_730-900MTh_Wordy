import Tkinter as Tk
import tkMessageBox
from Tkconstants import END
from PIL import ImageTk
import hashlib
import Other.project_utils


def go_to_main_menu():
    import MainMenu
    root.destroy()
    MainMenu.main_menu(game_actions, username)


def login():
    if usernameEntry.get() == 'Username' or usernameEntry.get() == '' or\
            passwordEntry.get() == 'Password' or passwordEntry.get() == '':
        tkMessageBox.showinfo('Error', 'Username or password cannot be empty')
    else:
        try:
            game_actions.login(usernameEntry.get(), passwordEntry.get())
            global username
            username = usernameEntry.get()
            root.after(100, go_to_main_menu)

        except Exception as e:
            if type(e).__name__ == "UserNotFoundException":
                tkMessageBox.showinfo("Invalid", "Invalid username or password")

            elif type(e).__name__ == "WrongCredentials":
                tkMessageBox.showinfo("Wrong Credentials", "Invalid username or password")

            elif type(e).__name__ == "UserOnlineException":
                tkMessageBox.showinfo("User Already Online", "User Account is already online")


def login_gui(game):
    global game_actions
    game_actions = game

    # Create the main window
    global root
    root = Tk.Tk()
    root.title("Login Page")
    root.geometry("1152x648")

    bg = ImageTk.PhotoImage(
        file=Other.project_utils.root_path() + "res\\GAME.jpg")
    canvas = Tk.Canvas()
    canvas.pack(fill="both", expand=True)
    canvas.create_image(0, 0, image=bg, anchor="nw")

    # Create a label for the background image
    background_label = Tk.Label(root, image=bg)
    background_label.place(x=0, y=0, relwidth=1, relheight=1)

    heading = Tk.Label(root, text='LOG IN', font=('sansserif', 25, 'bold'), bg='white')
    heading.place(x=683, y=75)

    # Create the username label and entry
    global usernameEntry
    usernameEntry = Tk.Entry(root, width=25, font=('arial', 11, 'bold'), bd=0, fg='darkgray')
    usernameEntry.place(x=560, y=285)
    usernameEntry.insert(0, 'Username')
    usernameEntry.bind('<FocusIn>', username_enter)
    usernameEntry.bind('<FocusOut>', username_leave)

    # Username frame
    frame1 = Tk.Frame(root, width=370, height=3)
    frame1.place(x=560, y=310)

    # Create the password label and entry
    global passwordEntry
    passwordEntry = Tk.Entry(root, width=25, font=('arial', 11, 'bold'), bd=0, fg='darkgray')
    passwordEntry.place(x=560, y=380)
    passwordEntry.insert(0, 'Password')
    passwordEntry.bind('<FocusIn>', password_enter)
    passwordEntry.bind('<FocusOut>', password_leave)
    # Password frame
    frame2 = Tk.Frame(root, width=370, height=3)
    frame2.place(x=560, y=405)

    global eye
    global eyeButton
    eye = ImageTk.PhotoImage(file=Other.project_utils.root_path() + 'res/eye.png')
    eyeButton = Tk.Button(root, image=eye, bd=0, bg='white', activebackground='white', cursor='hand2', command=hide)
    eyeButton.place(x=900, y=380)

    # Create the signin button
    loginButton = Tk.Button(root, width=30, text="Log in", font=('sansserif', 14, 'bold'), fg='white', command=login,
                            bg='royal blue')
    loginButton.place(x=560, y=450)

    # Run the main loop
    root.mainloop()


# Username
def username_leave(event):
    name = usernameEntry.get()
    if name == '':
        usernameEntry.insert(0, 'Username')


# Password
def computeMD5hash(my_string):
    m = hashlib.md5()
    m.update(my_string.encode('utf-8'))
    return m.hexdigest()


def show():
    eye_show = ImageTk.PhotoImage(
        file=Other.project_utils.root_path() + 'res/eye.png')
    passwordEntry.config(show='')
    eyeButton.config(image=eye_show, command=hide)
    eyeButton.image = eye_show


def hide():
    eye_hide = ImageTk.PhotoImage(file=Other.project_utils.root_path() + 'res/eye_hide.png')
    passwordEntry.config(show='*')
    eyeButton.config(image=eye_hide, command=show)
    eyeButton.image = eye_hide

def passNull():
    eye_show = ImageTk.PhotoImage(
        file=Other.project_utils.root_path() + 'res/eye.png')
    passwordEntry.insert(0, 'Password')
    passwordEntry.config(show='')
    eyeButton.config(image=eye_show, command=show)
    eyeButton.image = eye_show


def username_enter(event):
    usernameEntry.delete(0, END)


def password_enter(event):
    passwordEntry.delete(0, END)
    hide()


def password_leave(event):
    password = passwordEntry.get()
    if password == '':
        passNull()
