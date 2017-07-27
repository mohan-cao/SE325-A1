package nz.ac.auckland.concert.server;

import nz.ac.auckland.concert.common.Concert;
import nz.ac.auckland.concert.common.ConcertService;

import java.io.Console;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String args[]) throws UnknownHostException{
        try {
            final int MAX_SHAPES = 100;

            // Create the Registry on the localhost.
            Registry lookupService = LocateRegistry.createRegistry(9090);

            // Instantiate ShapeFactoryServant.
            ConcertService service = new ConcertServiceServant(MAX_SHAPES);

            // Advertise the ShapeFactory service using the Registry.
            lookupService.rebind(ConcertService.class.getName(), service);

            Console c = System.console();
            c.readLine("Press Enter to shutdown the server on " + InetAddress.getLocalHost());
            lookupService.unbind(ConcertService.class.getName());

            System.out.println(
                    "The Concert service is no longer bound in the RMI registry. "
                            + "Waiting for lease to expire.");

        } catch(RemoteException e) {
            System.out.println("Unable to start or register proxy with the RMI Registry");
            e.printStackTrace();
        } catch(NotBoundException e) {
            System.out.println("Unable to remove proxy from the  RMI Registry");
        }
    }
}
