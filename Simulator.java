import Math.*;

public class Simulator {
 public Simulator(){}
    public final static int departCity=1;
    public final static int arriveStation=2;
    public final static int departStation=3;
    public final static int arriveCity=4;
    public double Clock,LastEventTime;
    public int highwaySpeed;        
    public EventList FutureEventList;
    //public Queue Customers;        
    public Rand stream;
    
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
      sta = evt.sta;
      car = evt.car;
      car.moveUp(); // This moves the car's station up by 1
      car.chargePer = car.chargePer-(Math.abs(car.getLastStation().position - sta.position)/car.range);
     //*Customers.enqueue(evt);
        //*wightedQueueLength+=(Clock-LastEventTime)*Queuelength;
        //*Queuelength++;
        //if the server is idle, fetch the event, do statistics and put into service
        if(sta.outletsInUse < sta.capacity){
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
     Event departStation = new Event(car.getNextStation(), Clock + chargeTime + travelTime, car, departStation);
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
      Event cityDeparture = new Event(city, Clock + .1, car, departCity); // delay will be random
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
      while(cityNum = destinationNum){
        int destinationNum = (int)uniform(stream,1,6);
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

    
    

        
