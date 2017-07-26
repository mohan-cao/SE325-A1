package nz.ac.auckland.concert.client;

import nz.ac.auckland.concert.common.Concert;
import nz.ac.auckland.concert.common.ConcertService;
import org.junit.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import static org.junit.Assert.*;

public class Client {
    private static ConcertService _proxy;
    @Before
    public void setUp() throws Exception {
    }
    /**
     * One-time setup method to retrieve the ConcertService proxy from the RMI
     * Registry.
     */
    @BeforeClass
    public static void getProxy() {
        try {
            // Instantiate a proxy object for the RMI Registry, expected to be
            // running on the local machine and on a specified port.
            Registry lookupService = LocateRegistry.getRegistry("localhost", 9090);

            // Retrieve a proxy object representing the ShapeFactory.
            _proxy = (ConcertService)lookupService.lookup(ConcertService.class.getName());
        } catch (RemoteException e) {
            System.out.println("Unable to connect to the RMI Registry");
        } catch (NotBoundException e) {
            System.out.println("Unable to acquire a proxy for the Concert service");
        }
    }
    @After
    public void reset() throws RemoteException{
        _proxy.clear();
    }

    /**
     * Test for creation of new shape.
     */
    @Test
    public void testCreate() throws RemoteException {
        Concert b = new Concert("Auckland Live","12/12/17");
        Concert c = _proxy.createConcert(b);

        System.out.println("Concert's Id is " + b.id());
        System.out.println("Concert proxy's Id is " + c.id());

        // Query the remote service
        List<Concert> remoteConcerts = _proxy.getAllConcerts();

        assertTrue(remoteConcerts.contains(c));
        assertEquals(1, remoteConcerts.size());

        for(Concert cc : remoteConcerts) {
            // First iteration of this loop calls getAllstate() on the
            // same remote Shape object that shapeA acts as a remote
            // reference for, the second iteration on shapeB's remote
            // object.
            System.out.println(cc.toString());
        }
    }
}