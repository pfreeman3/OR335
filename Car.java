//Car Type
//Mileage?
//Battery type / Time to charge
//[Assume Fully Charged, most charging happens at home]
//Wealth Level (1 = lower, 2 = middle, 3 = upper)
//Distance to Destination
//Distance to next charge
//Time to stay / leave time from destination
//Will the car need a station? If not, delete this object
//Stores stats.


// Time is in units of Minutes
// home and desitnation int meanings
// 1 = "Richmond"
// 2 = "DC"
// 3 = "Philly"
// 4 = "NY"
// 5 = "Boston"


public class Car{
  /**
  * Creates Car for use in simulation; we will be considering several factors, which will determine under what conditions the 
  * car will stop at a charging station.
  *
  * @param carType Int value that determines the type of electric car (1 = BMW i3, 2 = Tesla Model 3, 3 = Chevy Bolt)
  * @param wealth wealth status of driver (1 = lower, 2 = middle, 3 = upper)
  * @param home Starting City of vehicle
  * @param stayTime Time staying at the destination city.
  */
  public Car(int carType, int wealth, int home, int stayTime, int destination){
     double chargePer = 100.0; // The % that the batery is charged.
      carType = this.carType; // This is a number that corresponds to the type of car, 1 = Tesla MS
      wealth = this.wealth;
      home = this.home; // Home is one of the cities. 1-5.
      stayTime = this.stayTime;
      destination = this.destination;
    // Make the things that come with the attributes
     int chargeTime; // Time it takes to charge a car, will differ, find
    // PLACEHOLDER PLACEHOLDER PLACEHOLDER
    switch(carType){ // These are placeholder numvers, please change once we have proper data
      case 1: chargeTime = 3;
      break;
      case 2: chargeTime = 4;
      break;
      case 3: chargeTime = 5;
      break;
    }
    int range; // This is the range on a full battery, possible distance is calculated from range*%batterty
    // PLACEHOLDER PLACEHOLDER PLACEHOLDER
    switch(carType){ // These are placeholder numbers, please change once we have proper data
      case 1: range = 3;
      break;
      case 2: range = 4;
      break;
      case 3: range = 5;
      break;
    }
     double searchRange = range/4; // This is the threshhold where it will look for more 
     double homePoint;
    switch(home){ // These are placeholder numbers, please change once we have proper data
      case 1: homePoint = 0;
      break;
      case 2: homePoint = 1;
      break;
      case 3: homePoint = 2;
      break;
      case 4: homePoint = 3;
      break;
      case 5: homePoint = 4;
      break;
    }
    
     int destinationPoint;
    switch(home){ // These are placeholder numbers, please change once we have proper data
      case 1: destinationPoint = 0; // These numbers are the points on which the ities lie
      break;
      case 2: destinationPoint = 1;
      break;
      case 3: destinationPoint = 2;
      break;
      case 4: destinationPoint = 3;
      break;
      case 5: destinationPoint = 4;
      break;
    }
     int distance = destinationPoint - homePoint; // This is the distance the car needs to travel
    final int originalDistance = distance;
  }
  
}
