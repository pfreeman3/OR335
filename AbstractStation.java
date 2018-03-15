abstract public class AbstractStation{
  int length;
  int implementingCost;
  int chargingCost;
  int overheadCost;
  
  abstract int getPosition();
  
  abstract int getCarqueue();
  
  abstract double getCosts();
  
}
