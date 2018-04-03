// Change to Station sta
public class Event{
 private double time ;
    private int type;
    public Event ( Station _sta , double _time , Car _car, int _type){
     sta = _sta ; // The station that the event occurs in, includes all kinda of RrechargeStations and Cities
     time = _time ; // The point in time that the event occurs
     car = _car; // The car involved in the event
     type = _type; // The type of event that this is
    }

    public double get_time(){
     return time ;
    }
    
    public int get_type (){
     return type;
    }
}