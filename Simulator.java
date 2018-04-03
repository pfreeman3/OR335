import Math.*;

public class Simulator {
 public Simulator(){}
    public final static int departCity=1;
    public final static int arriveStation=2;
    public final static int departStation=3;
    public final static int arriveCity=4;
    public double Clock,LastEventTime;
    public long NumberOfCustomers,Queuelength, TotalCustomers,NumberInService, NumberOfDepartures, MaxQueueLength;
    public int counter,counters, highwaySpeed;        
    public EventList FutureEventList;
    public Queue Customers;        
    public Rand stream;
    
    public void Initialization(){
     Clock=0.0;
     //NumberInService=0;
     // Queuelength=0;
     highwaySpeed = 45;
     LastEventTime=0.0;
     //TotalBusy= 0.0 ;
     //MaxQueueLength = 0 ;
     //SumResponseTime=0.0;
     //NumberOfDepartures=0;
     //SumWaitTime=0.0;
     //wightedQueueLength=0.0;
     //Create First Arrival Event
     Event evt = new Event(arrival,exponential(stream,MeanInterArrivalTime));
     FutureEventList.enqueue(evt);
    }

    // Gonna change this to ProcessStationArrival
    // FRAMEWORK: DONE
    // FLESHING: NOT DONE
    // Extra Functions Used
    public void ProcessStationArrival(Event evt){
      sta = evt.sta;
      car = evt.car;
     //*Customers.enqueue(evt);
        //*wightedQueueLength+=(Clock-LastEventTime)*Queuelength;
        //*Queuelength++;
        //if the server is idle, fetch the event, do statistics and put into service
      car.moveUp();
        if(sta.outletsInUse < sta.capacity) 
        {
         ScheduleStationDeparture(sta, car);
        }
        else 
         sta.queueCar(car);  //server is busy
        //adjust max Queue Length statistics
        //*if(MaxQueueLength< Queuelength)
        //*    MaxQueueLength=Queuelength;
        //Schedule the next arrival
        // CHANGE THIS CHANGE THIS CHANGE THIS
        // No need to make a new event, we already know which events will happen when a car spawns
        //* Event next_arrival = new Event(,Clock+exponential(stream,MeanInterArrivalTime));
        // CHANGE THIS CHANGE THIS CHANGE THIS
        FutureEventList.enqueue(next_arrival);
        LastEventTime = Clock;
    }
    
    // FRAMEWORK: KINDA-DONE
    // FLESHING: NOT DONE
    // sta.chargeRate is the amount of time it takes to charge 100% IN HOURS
    // car.direction is +1 if it's going + on the line, -1 if it's going - on the line
    public void ScheduleStationDeparture(Station sta, Car car){
     //NumberOfCustomers++;
     car.chargePer = 100.0;
     sta.outletsInUse++;
     sta.queueLength--;
     double chargeTime = (1-car.chargePer)*sta.chargeRate; // This may be car specific
     double travelTime = Math.abs( (sta.position - car.getNextStation().position) /highwaySpeed) );
     Event departStation = new Event(car.getNextStation(), Clock + chargeTime + travelTime, car);
        //*Event depart= new Event(Simulator.departue,Clock+triangular(stream,1,3,8));
        //*double arrive = Customers.Get(0).get_time();
        //*double wait= Clock-arrive;
        //*SumWaitTime+=wait;
        FutureEventList.enqueue(departStation);
        //*NumberInService=1;
        //*Queuelength--;
    }
    // FRAMEWORK: KINDA DONE
    public void ProcessStationDeparture(Event e){
     //get the customers description
      // THIS TAKES THE EVENT OUT
        Event finished= (Event) Customers.dequeue();
        // if there are customers in the queue then schedule the departure of the next one
        //wightedQueueLength+=(Clock-LastEventTime)*Queuelength;
        if (e.sta.queuelength>0) 
         ScheduleStationDeparture(e.sta, e.sta.queue.get(0));
        else 
         e.sta.outletsInUse=0;
        //measure the response time and add to the sum
        //double response= (Clock-finished.get_time());
        //SumResponseTime+=response;
        //TotalBusy+=(Clock-LastEventTime);
        e.sta.departures++;
        //LastEventTime=Clock;
    }
    
    public void ScheduleCityDeparture(Car car, City city){
      Event cityDeparture = new Event(city, Clock + , car);
    }

    public void ProcessCityDeparture(Car car, City city){
      // Send it to a station, 
      double travelTime = Math.abs( (city.position - car.getNextStation().position) /highwaySpeed) );
      Event evt = new Event(car.getNextStation, Clock + travelTime
      
      //and make a new car from a new city
      int delayTime; // This is how long it takes to make the next car
      int cityNum = (int)uniform(stream, 1,6);
      int destinationNum = cityNum;
      while(cityNum = destinationNum){
        int destinationNum = (int)uniform(stream,1,6);
      }
      //public void Car(int carType_, int wealth_, int home_, int stayTime_, int destination_){
      int carType = (int)uniform(stream,1,4);
      int wealthLvl = (int)uniform(stream,1,4);
      double stayTime = 24; // This says that all cars stay for a day no matter what, that will not be true
      Car newCar = new Car(carType,wealthLvl,cityNum, stayTime,destinationNum); // This is a big thing. This will make a random car
      ScheduleCityDeparture(newCar, cityList.get(cityNum));
      
                            
      
    }
    
    
    
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

    
    

        
