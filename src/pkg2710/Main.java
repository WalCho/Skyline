package pkg2710;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
/**
 * Recursive Divide and Conquer Algorithm for finding Skyline
 *
 * @author Nikos Valtsiogs
 * @email nvaltsiog@csd.auth.gr
 */
public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<Point> allPoints = new ArrayList<>();
        int size = 0;
        try {
            //Reading the input files
            String inputs = args[0];
            FileReader fileReader = new FileReader(inputs);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            try (Scanner input = new Scanner(bufferedReader)) {
                // Storing first int of input in a variable named size
                size = (input.nextInt());
                while (input.hasNext()) {
                    // Constructs a point, storing nextInt of input at the specified (x,y) location
                    Point p = new Point(input.nextInt(),input.nextInt());
                    allPoints.add(p);
                    if ( input.hasNextLine()) {
                        input.nextLine();
                    }
                }
            }
        }
        catch (FileNotFoundException ex) {
            System.out.println("Unable to open file");
        }
        // Sorting allPoints by y coordinate
        // If two points have the same y then i keep the point with the smallest x
        // I used help from various stackoverflow.com sources for this
        // This does O(n log n)work
        Collections.sort(allPoints, (Point o1, Point o2) -> {
            if (o1.getY()==o2.getY()){
                return Double.compare(o1.getX(),o2.getX());
            }
            return Double.compare(o1.getY(),o2.getY());
        });
        allPoints=SkylineQueries(allPoints);
        // Prining allPoints by the format that was asked
        for (Point p : allPoints) {
            System.out.println(p.x +" "+ p.y);
        }
    }
    /**
     * Recursive Divide and Conquer Algorithm
     *
     * @param allPoints Point array list with all the points
     * @return Results
     */
    public static ArrayList<Point> SkylineQueries(ArrayList<Point> allPoints){

        if(allPoints.size()<=1){
            return allPoints;
        }
        // Declaring new Arraylist of points 
        ArrayList<Point> LeftResultsList=new ArrayList<>();
        ArrayList<Point> LeftList=new ArrayList<>();
        ArrayList<Point> RightResultsList=new ArrayList<>();
        ArrayList<Point> RightList=new ArrayList<>();
        // Beginning of the recursive function for dividing allPoints into subproblems calling SkylineQueries until size()<=1
        // This does O(n log n)work
        for (int i=0;i<allPoints.size()/2;i++){
            LeftList.add(allPoints.get(i));
        }
        for (int i=allPoints.size()/2;i<allPoints.size();i++){
            RightList.add(allPoints.get(i));
        }
        LeftResultsList=SkylineQueries(LeftList);
        RightResultsList=SkylineQueries(RightList);
        /*The method i used for the conquer step 
          was based on this stackoverflow.com post 
          https://stackoverflow.com/questions/49603454/skyline-of-2d-points-divide-and-conquer-algorithm 
          The thought of this post is that he finds the point with the minimum y value on the left side and he removes every point on the right side with bigger y value
          (Points are pre-sorted based on their x values).
          My initial thought was so similar, only that i had thought about finding the minimum x value,
          and having the arraylist pre-sorted by their y value,thats why i kept my thought.          
        */
        int minX=1001;
        int i = 0;
        while (i<LeftResultsList.size()){
            if (LeftResultsList.get(i).x<minX) {
                minX=LeftResultsList.get(i).x;
            }
            i++;
        }
        int j = 0;
        // I used while loop beacuse when i remove a point, the rest is shifted left
        // This does O(1) work per point, so if there are n total points, this does O(n) work
        while (j<RightResultsList.size()){
            if(RightResultsList.get(j).x>=minX) {
                RightResultsList.remove(j);
            }
            else
                j++;
        }
        RightResultsList.addAll(LeftResultsList);
        return RightResultsList;
    }
}