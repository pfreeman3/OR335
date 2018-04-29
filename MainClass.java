public class MainClass{
 public static void main(String argv[]) throws Exception{
  Simulator ss = new Simulator();
  ss.FutureEventList = new EventList();
  ss.stream=new Rand();
  ss.Clock=0.0;
  // STATION STATS
  ss.stationFlatCost  = 600000;
  ss.stationOutletCost= 4000; // Cost to add 1 outlet to a Station
  ss.stationEnergyCost= 1; // This is Dollars per Hour to run 1 outlet in a station
  ss.stationCapacity = 30;
  // DELAY TIME
  ss.delayTime = .1000; // This is the delay between cars, right now this is .1 hours, so 6 min between cars
  ss.Initialization();            
  //Loop until clock is greater than 10000
  while (ss.Clock<=10000){
   Event evt = ss.FutureEventList.getMin();  //get imminent event
   ss.FutureEventList.dequeue();             //delete the event
   ss.Clock=evt.getTime();                  //advance in time
   switch(evt.getType()){
      case 1: ss.ProcessCityDeparture(evt); 
      break;
      case 2: ss.ProcessStationArrival(evt);
      break;
      case 3: ss.ProcessOutletDeparture(evt);
      break;
      case 5: ss.ProcessCityArrival(evt);
      break;
    }
   ss.numEvents++;
  }
  
  ss.ReportGeneration();
    }
}
