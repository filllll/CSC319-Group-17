import java.util.*;
import java.io.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Collections;

public class MapDataDrawer {
  private int[][] grid;
  
  public MapDataDrawer(String filename, int rows, int cols) throws IOException {
    // initialize grid 
    //read the data from the file into the grid
    grid = new int[rows][cols];
    scanIn(filename, grid);
  }
  
  public void scanIn(String filename, int[][] grid) throws FileNotFoundException, IOException {
    try (Scanner read = new Scanner(new File(filename))){
      int i = 0, j=0;
      while (i != grid.length){
        grid[i][j++] = Integer.parseInt(read.next());
        if(j == grid[0].length){
          i++;
          j = 0;
        }
      }
      
      
      System.out.println("length "+ grid.length);
      System.out.println("[0]length "+grid[0].length);
    /**
     * @return the min value in the entire grid
     */
    
    }
  }
  
  
  public int findMinValue(){
    int min = grid[0][0];
    for (int x = 0; x < grid.length; x++){
      for(int y = 0 ;y < grid[0].length; y++){
        if(grid[x][y] < min){
          min = grid[x][y];
        }
      }
    }
    return min;
  /**
    * @return the min value in the entire grid
    */
  }
  
  public int findMaxValue(){
    int max = grid[0][0];
    for(int x =0; x < grid.length; x++){
      for(int y = 0; y < grid[0].length; y++){
        if(grid[x][y] > max){
          max = grid[x][y];
        }
      }
    }
    return max;
  }
  
  public int indexOfMinInCol(int col){
    int min = grid[0][col];
    int numb = 0;
    for(int i =0; i<grid.length; i++){
      if(grid[i][col] < min){
        min = grid[i][col];
        numb = i;
      }
    }
    return numb;
   /**
    * @param col the column of the grid to check
    * @return the index of the row with the lowest value in the given col
    * for the grid
    */ 
  }
  /**
   * Draws the grid using the given Graphics object. Colors should be
   * grayscale values 0-255, scaled based on min/max values in grid
   */
  
  public void drawMap(Graphics g){
    int mn = findMinValue();
    int mx = findMaxValue();
    for(int b =0; b < grid[0].length ; b++){
      for(int y = 0; y < grid.length; y++){
        int w = (grid[y][b] - mn) / ((mx-mn)/255); // make the shade of color.
        g.setColor(new Color(w, w, w));
        g.fillRect(b, y, 1, 1); // paint
      }
    }
  }
 public int greedyPath(int numb, int top, int mid, int down) {
        int sum = 0;
        int topgReed = Math.abs(numb - top);
        int midgReed = Math.abs(numb - mid);
        int downgReed = Math.abs(numb - down);
        int mn;
        mn = Math.min(Math.min(topgReed, midgReed), downgReed);
        if (mn == midgReed) {
            sum = mid;
        } else if (mn == topgReed && mn != downgReed) {// top line
            sum = top;
            
        } else if (mn == downgReed && mn != topgReed) {//down  line 
            sum = down;
        } else if (mn == topgReed && mn == downgReed) { // if mid equal randon for go top or down
            int x = (int) (Math.random() * 100);
            if (x < 50) {
                sum = top;
            } else {
                sum = down;
            }
        }
        return sum;

    }
  /**
     * Find a path from West-to-East starting at given row. Choose a foward step
     * out of 3 possible forward locations, using greedy method described in
     * assignment.
     *
     * @return the total change in elevation traveled from West-to-East
     */
  public int drawLowestElevPath(Graphics g, int row){ // for draw  the green line.
    // best ways to walk.
    int mid, top, down;
        g.fillRect(0, row, 1, 1);
        int num = grid[row][0];
        int minNumber;
        int change = 0;
        for (int y = 1; y < grid[0].length; y++) {
            if(row == 0) {
                mid = grid[row][y];
                down = grid[row + 1][y];
                top = 999999;
            } else if (row == grid.length - 1) { 
                mid = grid[row][y];
                top = grid[row - 1][y];
                down = 999999;
            } else{
                mid = grid[row][y];
                top = grid[row - 1][y];
                down = grid[row + 1][y];
            }
            minNumber = greedyPath(num, top, mid, down);
            change += Math.abs(num-minNumber);
            if(top == minNumber) {
                row = row - 1;
            }else if (down == minNumber) {
                row = row + 1;
            }
            num = grid[row][y];
            g.fillRect(y, row, 1, 1);

        }

        return change;
  }
  
  /**
   * @return the index of the starting row for the lowest-elevation-change
   * path in the entire grid.
   */
  
  public int indexOfLowestElevPath(Graphics g){
    int bestRow = 999999;
    int chg;
    int ea = 0;
    for(int x =0; x< grid.length ; x++){
      chg = drawLowestElevPath(g, x);
      if(chg < bestRow){
        bestRow = chg;
        ea = x;
      }
    }
    return ea;
  }
}







































