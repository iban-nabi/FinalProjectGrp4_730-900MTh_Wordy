package Client_Java;

import CORBA_IDL.GameActions;
import CORBA_IDL.GameActionsHelper;
import Client_Java.LogIn.LogInGUI;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

public class Client {
    public static void main(String[] args) {
        try{
            ORB orb = ORB.init(args,null);

            org.omg.CORBA.Object ojbRef = orb.resolve_initial_references("NameService");

            NamingContextExt ncRef = NamingContextExtHelper.narrow(ojbRef);

            String name = "WordyGame";
            GameActions gameActions = GameActionsHelper.narrow(ncRef.resolve_str(name));

            new LogInGUI(gameActions).setVisible(true);
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
