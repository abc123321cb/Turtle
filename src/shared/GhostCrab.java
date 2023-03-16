package shared;

// A cool enemy that runs from you
public class GhostCrab extends Enemy{

    private static final double maxspeed = 0.01;
    private static final double accelration = 0.1;
    private static final int CrabMaxHealth = 1;

    GhostCrab(double x, double y, int dimen, int chunkx, int chunky) {
        super(x, y, dimen, chunkx, chunky, CrabMaxHealth);
        this.setImgIndex(0);
    }

    @Override
    public void move() {
        super.move();
        if(getDistance()[0] < 10){
            accelrate(getDistance()[1]+180);
        }else{
            setXvel(getXvel()*.9);
            setYvel(getYvel()*.9);
        }

    }

    private void accelrate(double direction){
        double xvel = getXvel()+Math.cos(direction)*accelration;
        double yvel = getYvel()+Math.sin(direction)*accelration;
        double totalSpeed = Math.pow(Math.pow(xvel,2)+Math.pow(yvel,2),0.5);
        if(totalSpeed>maxspeed){
            xvel = maxspeed*xvel/totalSpeed;
            yvel = maxspeed*yvel/totalSpeed;
        }
        setXvel(xvel);
        setYvel(yvel);
    }
}
