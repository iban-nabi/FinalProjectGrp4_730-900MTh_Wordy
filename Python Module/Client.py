import sys
from omniORB import CORBA
import CORBA_IDL
from Other.Login import login_gui

# run this
orb = CORBA.ORB_init()
obj = orb.string_to_object("corbaname::192.168.197.116:10000/NameService#WordyGame")
game_actions = obj._narrow(CORBA_IDL.GameActions)

if game_actions is None:
    print("Unable to narrow the server interface.")
    sys.exit(1)

login_gui(game_actions)
