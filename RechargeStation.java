public class RechargeStation extends Station{

  protected double position;
  protected int carQueue;
  protected double getCosts;
  protected String name;
  protected int capacity;

  public RechargeStation(String name, double position, int capacity){
    this.name = name;
    this.position = position;
    this.capacity = capacity;

    carQueue = 0;
  }
  public double getPosition(){
    return position;
  }
  public int getCarqueue(){
    return carQueue;
  }
  public double getCosts(){
    return 1000000;
  }
  public int getCapacity(){
    return capacity;
  }


}
