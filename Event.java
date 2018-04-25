// Change to Station sta
public class Event{
  private Station sta;
  private double time;
  private Car car;
  private int type;
  public Event ( Station sta , double time , Car car, int type){
   this.sta = sta ;   // The station that the event occurs in, includes all kinda of
                    //RrechargeStations and Cities
   this.time = time ; // The point in time that the event occurs
   this.car = car;    // The car involved in the event
   this.type = type;  // The type of event that this is
  }
  public double getTime(){
   return time ;
  }

  public int getType (){
    return type;
  }
  public Station getStation(){
    return sta;
  }
  public Car getCar(){
    return car;
  }
}
