package shared;

import client.Game;

public class Hitbox implements Loopers {
    double x,y,width,height;
    int duration, damage;
    boolean friendly;
    boolean collided = false;

    Hitbox(double x, double y, double width, double height, int duration, boolean friendly, int damage){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.duration = duration;
        this.friendly = friendly;
        this.damage = damage;
        Game.hitboxes.add(this);
    }

    public boolean move(){
        duration--;
        if(duration<=0)
            return true;
        if(!collided) {
            if (friendly) {
                for (Enemy e : Game.enemies) {
                    if (Utility.rectCollide(x, y, width/(double)Options.TILE_SIZE, height/(double)Options.TILE_SIZE,
                            e.getX() + (e.getChunkx() - Game.chunks[4].chunkx) * FinalOptions.CHUNKSIZE, e.getY() + (e.getChunky() - Game.chunks[4].chunky) * FinalOptions.CHUNKSIZE,
                            e.getWidth()/(double)Options.TILE_SIZE, e.getHeight()/(double)Options.TILE_SIZE)) {
                        e.damage(damage);
                        collided=true;
                    }
                }
            } else {
                for (protag p : Game.playerList) {
                    if (Utility.rectCollide(x, y, width, height,
                            p.localx, p.localy, p.dimen, p.dimen)) {
                        p.updateHealth(p.health - damage);
                        collided=true;
                    }
                }
            }
        }
        return false;
    }


}
