// Change to Station sta
public class Event{
 private double time ;
    private int type;
    public Event ( Station _sta , double _time , Car _car, int _type){
     sta = _sta ;
     time = _time ;
     car = _car;
     type = _type;
    }

    public double get_time(){
     return time ;
    }
    
    public int get_type (){
     return type;
    }
}