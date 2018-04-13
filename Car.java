//Car Type
//Mileage?
//Battery type / Time to charge
//[Assume Fully Charged, most charging happens at home]
//Distance to Destination
//Distance to next charge
//Time to stay / leave time from destination
//Will the car need a station? If not, delete this object
//Stores stats.


// home and desitnation int meanings
// 1 = "Richmond"
// 2 = "DC"
// 3 = "Philly"
// 4 = "NY"
// 5 = "Boston"


public class Car{
  public double range;
  public double searchRange;
  public int carType;
  public int wealth;
  public int home;
  public double stayTime;
  public int destination;
  public String carTypeSTR[] = {"null", "BMW i3", "Tesla Model 3", "Chevy Bolt"};
  public int chargeTimeArr[] = {0,40,50,80}; // time to charge in minutes
  public double rangeArr[] = {0,114,270,238}; // range of car.
  public int homePointArr[] = {0,0,109,248,346,561}; // distances from richmond

  /**
  * Creates Car for use in simulation; we will be considering several factors,
  * which will determine under what conditions the car will stop at a charging station.
  *
  * @param carType Int value that determines the type of electric car (1 = BMW i3, 2 = Tesla Model 3, 3 = Chevy Bolt)
  * @param wealth wealth status of driver (1 = lower, 2 = middle, 3 = upper)
  * @param home Starting City of vehicle (1 = Richmond, 2 = D.C., 3 = Philly, 4 = NYC, 5 = Boston)
  * @param stayTime Time staying at the destination city.
  * @param destination the city destination of the vehicle.
  */
  public Car(int carType,
             int wealth,
             int home,
             double stayTime,
             int destination){

    double chargePer = 100.0; // The % that the batery is charged.
    this.carType = carType;
    this.wealth = wealth;
    this.home = home;
    this.stayTime = stayTime;
    this.destination = destination;

    // Make the things that come with the attributes
    int chargeTime = chargeTimeArr[carType]; // Time it takes to charge a car, will differ, find
    range = rangeArr[carType]; // This is the range on a full battery, possible distance is calculated from range*%batterty
    searchRange = range/4; // This is the threshhold where it will look for more
    int homePoint = homePointArr[home]; //distance from richmond
    int destinationPoint = homePointArr[destination];
    int distance = destinationPoint - homePoint; // This is the distance the car needs to travel
    final int originalDistance = distance;
  }

  /**
  * Deletes car (i.e., when car is finished, or if not applicable to simulation)
  * @param car Car to be deleted.
  */
  public static void deleteCar(Car car){
    car = null;
  }
  public Station getNextStation(){
    Station ans = new RechargeStation();
    return ans;
  }
  // NOT FINISHED
  public void moveUp(){

  }
//  public ArrayList<Station> getStationList(){
    //Basic Logic
    // Can I get to my destination?
    // If I can, add it to the end of the list
    // if not add the furthest station in my range
    // if there is no station, throw an exception
 // }

}
