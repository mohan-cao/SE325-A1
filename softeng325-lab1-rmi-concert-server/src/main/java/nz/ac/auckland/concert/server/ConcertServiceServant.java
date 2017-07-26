package nz.ac.auckland.concert.server;

import nz.ac.auckland.concert.common.Concert;
import nz.ac.auckland.concert.common.ConcertService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class ConcertServiceServant extends UnicastRemoteObject implements ConcertService{

    private static final long serialVersionUID = 1L;

    private Map<Long,Concert> _concerts;	// List of Shapes created a ShapeFactoryServant.
    private final int _maxSize;   // Capacity of a ShapeFactoryServant.
    private long _nextID;

    protected ConcertServiceServant(final int maxSize) throws RemoteException {
        super();
        _concerts = new HashMap<Long,Concert>();
        _maxSize = maxSize;
        _nextID = 1;
    }

    @Override
    public synchronized Concert createConcert(Concert concert) throws RemoteException {
        Concert c = new Concert(concert.title(),concert.date());
        if(concert.id()<0||_concerts.containsKey(concert.id())) return null;
        _concerts.put(_nextID++,concert);
        c.setUniqueID(_nextID);
        return c;
    }

    @Override
    public synchronized Concert getConcert(Long id) throws RemoteException {
        return _concerts.get(id);
    }

    @Override
    public synchronized boolean updateConcert(Concert concert) throws RemoteException {
        if(_concerts.containsKey(concert.id())) {
            _concerts.put(concert.id(), concert);
            return true;
        }
        return false;
    }

    @Override
    public synchronized boolean deleteConcert(Long id) throws RemoteException {
        return (_concerts.remove(id)!=null);
    }

    @Override
    public synchronized List<Concert> getAllConcerts() throws RemoteException {
        List<Concert> all = new LinkedList<Concert>();
        for(Long ids : _concerts.keySet()){
            all.add(_concerts.get(ids));
        }
        return all;
    }

    @Override
    public synchronized void clear() throws RemoteException {
        _concerts.clear();
    }
}
