package shared;

public class Rock extends NPC{
    private int spoken;

    public Rock(double x,double y, double xvel, double yvel, int chunkx, int chunky, int width, int height){
        super(x,y,xvel,yvel,chunkx,chunky,width,height, 0);
        spoken = 0;

    }

    public String interact(){
        if(spoken==0){
            return "I am just a rock";
        } if (spoken==1) {
            return "ahh how did you break my cover.";
        }

        return "...";
    }





}
