package nz.ac.auckland.concert.common;

import java.io.Serializable;

public class Concert implements Serializable{
    private long _id;
    private String _title, _date;
    public Concert(String title, String date){
        _id = -1;
        _title = title;
        _date = date;
    }
    public void setUniqueID(final long id){
        _id = id;
    }
    public long id(){
        return _id;
    }
    public String toString(){
        return _title + ", " + _date + " with id " + _id;
    }

    public String title() {
        return _title;
    }

    public String date() {
        return _date;
    }
}