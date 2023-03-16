package shared;

public class Projectile {
    private double x;
    private double y;
    private double xvel;
    private double yvel;
    private int number;
    private int chunkx;
    private int chunky;
    private boolean friendly;
    private int lifespan;

    public Projectile(double x, double y, int chunkx, int chunky, boolean friendly, int lifespan){
        this.x = x;
        this.y = y;
        this.chunkx = chunkx;
        this.chunky = chunky;
        this.friendly = friendly;
        this.lifespan = lifespan;

    }

    public void move(){

        lifespan--;
        if(lifespan<1){

        }
    }

    public void draw(){

    }


}
