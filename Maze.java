package project5;
import java.util.ArrayList;

public class Maze extends BST<MazeNode>{
    
    public ArrayList<ArrayList<MazeNode>> findPaths() {
        ArrayList<ArrayList<MazeNode>> validPaths = new ArrayList<>();
        Hero hero = new Hero(0);
        findPaths(root, new ArrayList<>(), hero, validPaths);
        return validPaths;
    }
public void findPaths(BSTNode current, ArrayList<MazeNode> currentPath, Hero hero, ArrayList<ArrayList<MazeNode>> validPaths){
    if(current == null){
        return;
    }
    int lengthOut = 0;
    currentPath.add(current.data);
    hero.gainlifePoint(current.data.lifePoint);
    hero.travel();
    if(current.left == null && current.right == null && hero.getLifePoints() >= 0){
        if(currentPath.size()==lengthOut){
            validPaths.add(currentPath);
        }
        else if(currentPath.size() > lengthOut){
            validPaths.clear();
            validPaths.add(currentPath);
            lengthOut = currentPath.size();
        }
    }
    else{
        findPaths(current.left, currentPath, hero.clone(), validPaths);
        findPaths(current.right, currentPath, hero.clone(), validPaths);

    }

}
}
