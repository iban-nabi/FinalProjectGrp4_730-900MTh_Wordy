def centered_window(window):
    """Will center the provided window in the center of the user's screen. You can specify the width and height of
    your window before calling this function."""
    window.update_idletasks()
    width = window.winfo_width()
    height = window.winfo_height()
    x = (window.winfo_screenwidth() // 2) - (width // 2)
    y = (window.winfo_screenheight() // 2) - (height // 2)
    window.geometry('{}x{}+{}+{}'.format(width, height, x, y))


def root_path():
    """Returns a string containing the path to the project root. Simply concatenate whenever needed."""
    # Specify the file path going to this project.
    # Example: path = C:\\Users\\admin\\PycharmProjects\\finalprojectgrp4_730-900mth_other\\
    path = "C:\\Users\\Lenovo\\PycharmProjects\\Client_Python\\"
    return path

