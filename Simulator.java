import java.math.*;
import java.util.*;
public class Simulator {
 public Simulator(){}
    public final static int departCity=1;
    public final static int arriveStation=2;
    public final static int departOutlet=3;
    public final static int departStation=4;
    public final static int arriveCity=5;
    public double Clock,LastEventTime;
    public int highwaySpeed;
    public EventList FutureEventList;
//    public Station StationArr[];
    //public Queue Customers;
    public Rand stream;
    public ArrayList<City> cityList;
    public ArrayList<Station> stationList;

    public Station StationArray[] = { new City("Richmond",0),
                                    new RechargeStation("S1",45,30),
                                    new RechargeStation("S2",104,30),
                                    new City("Washington D.C.",109),
                                    new RechargeStation("S3",136,30),
                                    new RechargeStation("S4",210,30),
                                    new City("Philly", 248),
                                    new RechargeStation("S5",250,30),
                                    new RechargeStation("S6",315,30),
                                    new RechargeStation("S7",335,30),
                                    new City("New York City",346),
                                    new RechargeStation("S8",404.5,30),
                                    new RechargeStation("S9",490,30),
                                    new RechargeStation("S10",538,30),
                                    new City("Boston",561)};

    public void Initialization(){
     Clock=0.0;
     highwaySpeed = 45;
     LastEventTime=0.0;
     //Create First Arrival Event
     Car car = randomCar();
     Event evt = new Event(car.getNextStation(), Clock, car, departCity);
     FutureEventList.enqueue(evt);

    }

    // Gonna change this to ProcessStationArrival
    // FRAMEWORK: DONE
    // FLESHING: NOT DONE
    // Extra Functions Used
    public void ProcessStationArrival(Event evt){
      // Who and where?
      Station sta = evt.getStation();
      Car car = evt.getCar();
      // Move the car up 1 in its list of stations it needs to get to
      car.moveUp(); // This moves the car's station up by 1
      // Reduce the car's charge by how long it drove
      car.chargePer = car.chargePer-(Math.abs(car.getLastStation().getPosition() - sta.getPosition())/car.range);
      if(sta.outletsInUse < sta.capacity){
        ScheduleStationDeparture(sta, car);
      }
      else
        sta.queueCar(car);  //server is busy
      FutureEventList.enqueue(next_arrival);
      LastEventTime = Clock;
    }

    // FRAMEWORK: KINDA-DONE
    // FLESHING: NOT DONE
    // sta.chargeRate is the amount of time it takes to charge 100% IN HOURS
    // car.direction is +1 if it's going + on the line, -1 if it's going - on the line
    public void ScheduleOutletDeparture(Station sta, Car car){
     //NumberOfCustomers++;
     sta.outletsInUse++;
     sta.queueLength--;
     double chargeTime = (1-car.chargePer)*sta.chargeRate; // This may be car specific
     Event departOutlet = new Event(sta, Clock + chargeTime, car, departOutlet);
     car.chargePer = 100.0;
     FutureEventList.enqueue(departOutlet);
    }

    //
    public void ProcessOutletDeparture(Event e){
      // Send the Car to the next Station
      double travelTime = Math.abs( (e.sta.position - e.car.getNextStation().position) /highwaySpeed);
      Event departStation = new Event(car.getNextStation(), Clock + travelTime, car, departStation);
      FutureEventList.enqueue(departStation);

      // Queue the next car if there is one
      if (e.sta.queuelength>0){
        sta.outletsInUse--;
        ScheduleOutletDeparture(e.sta, e.sta.queue.get(0));
      }
      else
        e.sta.outletsInUse=0;
    }

    // FRAMEWORK: KINDA DONE
    public void ProcessStationDeparture(Event e){
      //get the customers description
      Event arriveStation = new Event(car.getNextStation(), Clock + travelTime, car, arriveStation);
      e.sta.departures++;
      FutureEventList.enqueue(arriveStation);
      LastEventTime=Clock;
    }

    public void ScheduleCityDeparture(Car car, City city){
      Event cityDeparture = new Event(city, Clock + .1, car, departCity); // delay will be random
      FutureEventList.enqueue(cityDeparture);
    }

    public void ProcessCityDeparture(Car car, City city){
      // Send it to a station,
      double travelTime = Math.abs( (city.position - car.getNextStation().position) /highwaySpeed);
      Event evt = new Event(car.getNextStation, Clock + travelTime, car, stationArrival);

      //and make a new car from a new city
      int delayTime; // This is how long it takes to make the next car
      Car newCar = randomCar();
      ScheduleCityDeparture(newCar, cityList.get(cityNum));
    }

    // Produces a car with random attributes
    public Car randomCar(){
      // Logic:
      // Get 2 numbers from 1-5 that are no the same
      // Generate a random car type from 1-3
      // generate a random welath level (unused) between 1-3
      int cityNum = (int)uniform(stream, 1,6);
      int destinationNum = cityNum;
      while(cityNum == destinationNum){
        destinationNum = (int)uniform(stream,1,6);
      }
      int carType = (int)uniform(stream,1,4);
      int wealthLvl = (int)uniform(stream,1,4);
      double stayTime = 24; // This says that all cars stay for a day no matter what, that will not be true
      Car newCar = new Car(carType,wealthLvl,cityNum, stayTime,destinationNum);
      return newCar;
    }

    // Produces a random number between a and b
    public static double uniform(Rand rng, double a, double b) {
     return (b-a)*rng.next()+a;
    }

    public static double exponential(Rand rng,double mean){
     return -mean*Math.log(rng.next());
    }

    public static double triangular(Rand rng,double a, double b, double c){
     double R = rng.next();
     double x;
     if (R <= (b-a)/(c-a))
      x = a + Math.sqrt((b-a)*(c-a)*R);
     else
      x = c - Math.sqrt((c-b)*(c-a)*(1-R));
     return x;
    }

    public  void ReportGeneration(){
     double RHO=TotalBusy/Clock;
        System.out.println("\n  Server Utilization                         " +RHO );
        double AverageWaittime=SumWaitTime/NumberOfCustomers;
        System.out.println("\n  Average Wait Time In Queue                 " +  AverageWaittime);
        double AverageQueueLength=wightedQueueLength/Clock;
        System.out.println("\n  Average Number Of Customers In Queue       " +  AverageQueueLength);
        System.out.println("\n  Maximum Number Of Customers In Queue       " +  MaxQueueLength);
    }
}
