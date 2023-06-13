package project5;
import java.io.*;
import java.util.ArrayList;



public class BinaryTreeMaze {
    public static void main(String[] args){
        Maze maze = new Maze();
        try {
            String fileName = args[0];
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(" ");
                maze.add(new MazeNode(parts[0], Integer.parseInt(parts[1])));
            }
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<ArrayList<MazeNode>> validPath = maze.findPaths();
        for(ArrayList<MazeNode> path: validPath){
            for(MazeNode node : path){
                System.out.print(node.label+" ");
            }
            System.out.println("\n");
        }
    }
}