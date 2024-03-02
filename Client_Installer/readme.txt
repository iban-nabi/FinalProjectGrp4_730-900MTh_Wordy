Hello! This text file contains installation instructions for our Client program written in Python. 

--------------------------------------------------------------------------------------------------------------------------------------

In order to successfully set up the program on your computer, make sure that you have completed the following requirements:
	a. Installed IntelliJ Idea 20222.1.3 (Community Edition)
	b. Installed Java version 1.8 and added to PATH
	c. Downloaded our server folder: Server_Java
	d. Orbd running (orbd -ORBInitialHost <hostname> -ORBInitialPort <portnumber>)
	e. Java server opened and running in IntelliJ with same configurations as orbd
	f. Installed PyCharm 2022.1.3 (Community Edition)
	g. Installed Python version 2.7.18
		https://www.python.org/ftp/python/2.7.18/python-2.7.18.amd64.msi
	h. Wampserver64 must be running
	i. Wordy.sql must also be imported in the database

--------------------------------------------------------------------------------------------------------------------------------------

Python Configuration Steps:
	1. Open the Client_Python folder in PyCharm. 
	2. Go to File > Settings > Project > Python Interpreter. 
	3. Choose Python 2.7 by specifying the directory of your python.exe. 
		* Make sure to choose “Existing” when selecting an interpreter.
	4. Install the required packages needed such as Pillow or PIL, and OmniORB to be able to 
	   run the Python files without errors. 
		* You can do this by clicking on the plus button below the text "Python Interpreter"
		* The package that should be installed are as follows:
			1. Pillow
			2. omniorb
			3. pip (appears once the Python Interpreter is specified)
			4. setuptools (appears once the Python Interpreter is specified)
	5. Once a message appears that the package was installed successfully, Python files can now run smoothly.

--------------------------------------------------------------------------------------------------------------------------------------

Python Interpreter Installation Guide:
	1. Download the python 2.7.18 version according to the architecture of your device.
		* If your computer architecture is AMD64/EM64T/x64 download the python-2.7.18.amd64.msi file
		* If your computer architecture is Window x86 download the python-2.7.18.msi file
	2.  After successfully installing the interpreter, add the path to your environment variables
	    by following the instructions below:
		1.  Go to the system properties.
		2. Click the 'Environment Variables' button.
		3. Under the system variables edit the Path or PATH.
		4. Under the Path or PATH click the new button then enter the
		   path of where your interpreter was installed and the path of 
		   Scripts folder under the Python27. To add another file just click new.
		5. After successfully adding it click 'OK' 
	3. To check if you have properly installed it, go to cmd, then enter the command "python --version" 
	   the result must be Python 2.7.18.

--------------------------------------------------------------------------------------------------------------------------------------

Java 8 Installation Guide:
	1. Download Java 8 or you can just access the folder named "zulu8.70.0.23-ca-jdk8.0.372-win_x64" inside the Client_Installer. 
	   You can download it on either of the following links:
		* https://www.azul.com/downloads/?version=java-8-lts&os=windows&package=jdk#zulu
		* https://www.oracle.com/in/java/technologies/javase/javase8-archive-downloads.html
	2. After successfully downloading the java 8, unzip it and add the path of jdk bin folder to the environment variables.
	   Refer to the following instructions:
		1. Go to the system properties.
		2. Click the 'Environment Variables' button.
		3. Under the system variables edit the Path or PATH.
		4. Under the Path or PATH click the new button then enter the path of the jdk bin folder.
		5. After successfully adding it click 'OK' 
	3. To check if you have properly installed it, go to cmd, then enter the command "java --version" 
	   the result must be Java 1.8.

--------------------------------------------------------------------------------------------------------------------------------------


