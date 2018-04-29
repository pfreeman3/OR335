import java.math.*;
import java.util.*;
import javax.swing.*;
public class Simulator {
 public Simulator(){}
    public final static int departCity=1;
    public final static int arriveStation=2;
    public final static int departOutlet=3;
    public final static int departStation=4;
    public final static int arriveCity=5;
    public double delayTime = 100000.0; // Time between car spawns
    public double Clock,LastEventTime;
    public int highwaySpeed;
    public EventList FutureEventList;
    public Rand stream;
    public ArrayList<Station> cityList = new ArrayList<Station>();
    public ArrayList<Station> stationList= new ArrayList<Station>();
    public int numEvents = 0;
    public int numCarsMade = 0;
    public int numFinishes = 0;
    public int stationFlatCost;
    public int stationOutletCost;
    public int stationEnergyCost;
    public int stationCapacity;
    public Station[] StationArray;
    public double avgRoundTrip = 0;
    public double longestTime=0;
//    public ArrayList<Car> carList = new ArrayList<Car>();
    
    public void Initialization() throws Exception{
      StationArray = new Station[]{ 
        new City           ("Richmond",0,this),
        new RechargeStation("S1",45,stationCapacity,this,stationFlatCost,stationOutletCost,stationEnergyCost),
        new RechargeStation("S2",104,stationCapacity,this,stationFlatCost,stationOutletCost,stationEnergyCost),
        new City           ("Washington D.C.",109,this),
        new RechargeStation("S3",136,stationCapacity,this,stationFlatCost,stationOutletCost,stationEnergyCost),
        new RechargeStation("S4",210,stationCapacity,this,stationFlatCost,stationOutletCost,stationEnergyCost),
        new City           ("Philly", 248,this),
        new RechargeStation("S5",250,stationCapacity,this,stationFlatCost,stationOutletCost,stationEnergyCost),
        new RechargeStation("S6",315,stationCapacity,this,stationFlatCost,stationOutletCost,stationEnergyCost),
        new RechargeStation("S7",335,stationCapacity,this,stationFlatCost,stationOutletCost,stationEnergyCost),
        new City           ("New York City",346,this),
        new RechargeStation("S8",404.5,stationCapacity,this,stationFlatCost,stationOutletCost,stationEnergyCost),
        new RechargeStation("S9",490,stationCapacity,this,stationFlatCost,stationOutletCost,stationEnergyCost),
        new RechargeStation("S10",538,stationCapacity,this,stationFlatCost,stationOutletCost,stationEnergyCost),
        new City           ("Boston",561,this)};
      Clock=0.0;
      highwaySpeed = 60;
      LastEventTime=0.0;
      for(int i=0;i<StationArray.length;i++){
        if(StationArray[i] instanceof City){
          cityList.add(StationArray[i]);
        }
      }
      // Put the stations in a seperate list
      for(int i=0;i<StationArray.length;i++){
        if(StationArray[i] instanceof RechargeStation){
          stationList.add(StationArray[i]);
        }
      }
      //Create First Arrival Event
      Car car = randomCar();
      Event evt = new Event(car.getNextStation(), Clock, car, departCity);
      FutureEventList.enqueue(evt);
      // Put the cities in cityList
      
      
    }

    // Gonna change this to ProcessStationArrival
    // FRAMEWORK: DONE
    // FLESHING: NOT DONE
    // Extra Functions Used
    public void ProcessStationArrival (Event evt) throws Exception{
      // Who and where?
      Station sta = evt.getStation();
      Car car = evt.getCar();
      // Move the car up 1 in its list of stations it needs to get to
      car.moveUp(); // This moves the car's station up by 1
      // Reduce the car's charge by how long it drove
      car.chargePer = car.chargePer-((Math.abs(car.getLastStation().getPosition() - sta.getPosition())/car.range)*100);
      if(car.chargePer <0){
        throw new Exception("Car arrived with less than 0 charge");
      }
      if(evt.getStation().getOutletsInUse() < evt.getStation().getCapacity()){
        ScheduleOutletDeparture((RechargeStation)sta, car);
      }
      else {
        sta.queueCar(car);  //server is busy
      }
      LastEventTime = Clock;
    }

    // sta.chargeRate is the amount of time it takes to charge 100% IN HOURS
    // car.direction is +1 if it's going + on the line, -1 if it's going - on the line
    public void ScheduleOutletDeparture(RechargeStation sta, Car car) throws Exception{
     sta.queueOutlet(car);
     double chargeTime = (100-car.chargePer)*(car.chargeTime/60.0)/100; // This may be car specific
     sta.updateCost(chargeTime);
     Event departOutletEvent = new Event(sta, Clock + chargeTime, car, departOutlet);
     car.chargePer = 100.0;
     FutureEventList.enqueue(departOutletEvent);
    }
    
    // I'm so scared that none of this will work
    public void ProcessOutletDeparture(Event e) throws Exception{
      // Send the Car to the next Station
      if(e.getStation() instanceof City){
        throw new Exception("You tried to process an outlet departure from a city, dummy");
      }
      double travelTime = Math.abs( (e.getStation().getPosition() - e.getCar().getNextStation().getPosition()) /highwaySpeed);
      if(e.getCar().getNextStation() instanceof City){
        Event arriveCityEvent = new Event(e.getCar().getNextStation(), Clock + travelTime, e.getCar(), arriveCity);
        FutureEventList.enqueue(arriveCityEvent);
      }
      if(e.getCar().getNextStation() instanceof RechargeStation){
        Event arriveStationEvent = new Event(e.getCar().getNextStation(), Clock + travelTime, e.getCar(), arriveStation);
        FutureEventList.enqueue(arriveStationEvent);
      }
      
      // Queue the next car if there is one
      if (e.getStation().getQueueLength()>0){
        e.getStation().dequeueOutlet(e.getCar());
        ScheduleOutletDeparture((RechargeStation)e.getStation(), e.getStation().getCarQueue().get(0));
        e.getStation().dequeueCar(e.getStation().getCarQueue().get(0));
      }
      else 
        e.getStation().dequeueOutlet(e.getCar());
    }
    
    public void ScheduleCityDeparture(Car car, City city){
      //double delayTime = .1; // This will be random in the future
      Event cityDeparture = new Event(city, Clock + delayTime, car, departCity); // delay will be random, right now it's .1
      FutureEventList.enqueue(cityDeparture);
    }
    
    public void ProcessCityDeparture(Event e) throws Exception{
      // Send it to a station,
      if(e.getCar().destBound){
        //and make a new car from a new city
        Car newCar = randomCar();
//        carList.add(newCar);
        ScheduleCityDeparture(newCar, (City)cityList.get(newCar.home-1));
      }
      double travelTime = Math.abs( (e.getStation().getPosition() - e.getCar().getNextStation().getPosition()) /highwaySpeed);
      if(e.getCar().getNextStation() instanceof City){
        Event arriveCityEvent = new Event(e.getCar().getNextStation(), Clock + travelTime, e.getCar(), arriveCity);
        FutureEventList.enqueue(arriveCityEvent);
      }
      if(e.getCar().getNextStation() instanceof RechargeStation){
        Event arriveStationEvent = new Event(e.getCar().getNextStation(), Clock + travelTime, e.getCar(), arriveStation);
        FutureEventList.enqueue(arriveStationEvent);
      }
    }
    
    public void ProcessCityArrival(Event e) throws Exception{
      e.getCar().moveUp();
      if(!e.getCar().destBound){
        double time = e.getCar().getTime();
        if(time > longestTime){
          longestTime = time;
        }
        avgRoundTrip = (avgRoundTrip*numFinishes + time) / (numFinishes+1);
        numFinishes++;

//        carList.remove(e.getCar());
        e.getCar().deleteCar(e.getCar());
      }
      else{
        // Send the car to its next station after it's stayTime + travelTime
        e.getCar().destBound = false;
        double travelTime = Math.abs( (e.getStation().getPosition() - e.getCar().getNextStation().getPosition()) /highwaySpeed);
        if(e.getCar().getNextStation() instanceof City){
          Event arriveCityEvent = new Event(e.getCar().getNextStation(), Clock + travelTime + e.getCar().stayTime, e.getCar(), arriveCity);
          FutureEventList.enqueue(arriveCityEvent);
        }
        if(e.getCar().getNextStation() instanceof RechargeStation){
          Event arriveStationEvent = new Event(e.getCar().getNextStation(), Clock + travelTime + e.getCar().stayTime, e.getCar(), arriveStation);
          FutureEventList.enqueue(arriveStationEvent);
        }
      }
    }

    // RANDOM FUNCTIONS -----------------------------------------------------------------------
    // Makes a random car
    // Produces a car with random attributes
    public Car randomCar() throws Exception{
      numCarsMade++;
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
      Car newCar = new Car(carType,wealthLvl,cityNum, stayTime,destinationNum, this); 
      return newCar;
    }
    
    
    // RANDOM NUMBER GENERATIONS --------------------------------------------------------------
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
    
    // END OF RNG -----------------------------------------------------------------------------
    
    public String tableAsString(JTable table){
      String tableString = "";
      for (int row = 0; row < table.getRowCount(); row++) {
        for (int col = 0; col < table.getColumnCount(); col++) {
          tableString += table.getValueAt(row, col);
          tableString += "\t";
        }
        tableString += "\n";
      }
      return tableString;
    }
    
    public JTable getDataTable(){
      String colNames[] = 
      { "name",
        "position",
        "capacity", 
        "inOutlet", 
        "maxOutl", 
        "qLength",
        "maxQLen", 
        "Waiting", 
        "Util", 
        "departs",
        "cost"};
      Object[][] dat = new Object[stationList.size() +1][colNames.length];
      dat[0] = colNames;
      int count = 1;
      for(int i=0; i < StationArray.length; i++){
        if(StationArray[i] instanceof RechargeStation){
          dat[count] = StationArray[i].getData();
          count++;
        }
      }
      JTable table = new JTable(dat,colNames);
      return table;
    }
    
    public  void ReportGeneration(){
//     double RHO=TotalBusy/Clock;
//        System.out.println("\n  Server Utilization                         " +RHO );
//        double AverageWaittime=SumWaitTime/NumberOfCustomers;
//        System.out.println("\n  Average Wait Time In Queue                 " +  AverageWaittime);
//        double AverageQueueLength=wightedQueueLength/Clock;
//        System.out.println("\n  Average Number Of Customers In Queue       " +  AverageQueueLength);
//        System.out.println("\n  Maximum Number Of Customers In Queue       " +  MaxQueueLength);
//    }
       System.out.println("Clock:" + Clock);
       System.out.println("Events:" + numEvents);
       System.out.println("Cars:" + numCarsMade);
       System.out.println("CarsFinished:" + numFinishes);
       System.out.println("Average:" + avgRoundTrip);
       System.out.println("Longest Time:" + longestTime + "\n");
       System.out.println(tableAsString(getDataTable()));
    }
}

    
    

        
