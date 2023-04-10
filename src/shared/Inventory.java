package shared;

public class Inventory {
    private static int base_size = 20;
    int[][] i;
    private int x;
    private int y;

    private boolean visable = false;

    public Inventory(){
        i = new int[20][20];
    }

    public void draw(){
        if(visable){

        }
    }

    public void makeVisable(int x, int y){
        visable=true;
        this.x=x;
        this.y=y;
    }

    public void makeInvisable(){
        visable=false;
    }

}
