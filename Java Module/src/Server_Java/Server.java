package Server_Java;

import CORBA_IDL.GameActions;
import CORBA_IDL.GameActionsHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

public class Server {
    public static void main(String[] args){
        /**
         * Set the corba connection
         */
        try{
            ORB orb = ORB.init(args, null);

            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            Servant servant = new Servant();

            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(servant);
            GameActions href = GameActionsHelper.narrow(ref);

            org.omg.CORBA.Object objRef  = orb.resolve_initial_references("NameService");

            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            String name = "WordyGame";
            NameComponent path[] = ncRef.to_name(name);
            ncRef.rebind(path, href);

            System.out.println("Server running........");
            orb.run();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
