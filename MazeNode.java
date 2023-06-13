package project5;

public class MazeNode implements Comparable<MazeNode>{
    String label;
    int lifePoint;

    MazeNode(String label, int lifePoint){
        this.label = label;
        this.lifePoint = lifePoint;
    }
    public String getLabel() {
        return this.label;
    }

    public int getLifePoints() {
        return this.lifePoint;
    }
    @Override
    public int compareTo(MazeNode other){
        return label.compareTo(other.label);
    }
}
