package project5;

public class Hero {
    private int lifePoints = 0;
    
    public Hero(int lifePoints) {
        this.lifePoints = lifePoints;
    }
    public void travel(){
        this.lifePoints--;
    }
    public void gainlifePoint(int lifePoint){
        this.lifePoints += lifePoint;
    }
    public int getLifePoints(){
        return this.lifePoints;
    }
    public Hero clone(){
        return new Hero(lifePoints);
    }
}
