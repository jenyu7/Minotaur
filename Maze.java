/***
 * Minotaur: Jennifer Yu, Jason Lam, Bryan Chan
 * APCS2 Period 5
 * HW #11: Thinkers of the Corn, Do You Hear the People SING!
 * 2017-03-03
***/
/***
 * SKELETON for class 
 * MazeSolver
 * Implements a blind depth-first exit-finding algorithm.
 * Displays probing in terminal.
 * 
 * USAGE: 
 * $ java Maze [mazefile]
 * (mazefile is ASCII representation of maze, using symbols below)
 * 
 * ALGORITHM for finding exit from starting position:
 * 1. Hero is dropped in at a random location in the maze (on the path)
 * 2. Check immediate surroundings (the four boxes to the hero's left, right, front and back). 
 * 3a. If it's on the path, step onto it, and repeat. 
 * 3b. If not, then backtrack and mark the box as a visited path. 
 * 4. If the hero finds an exit in her/his immediate surroundings, return the maze as solved. 
 ***/

//enable file I/O
import java.io.*;
import java.util.*;


class MazeSolver 
{
    private char[][] maze;
    private int h, w; //height, width of maze
    private boolean solved;

    //initialize constants for map component symbols
    final private char HERO =         '@';
    final private char PATH =         '#';
    final private char WALL =         ' ';
    final private char EXIT =         '$';
    final private char VISITED_PATH = '.';


    public MazeSolver( String inputFile ) 
    {
	delay(50);
	// init 2D array to represent maze 
	// (80x25 is default terminal window size)
	maze = new char[80][25];
	h = 0;
	w = 0;

	//transcribe maze from file into memory
	try {
	    Scanner sc = new Scanner( new File(inputFile) );

	    System.out.println( "reading in file..." );

	    int row = 0;

	    while( sc.hasNext() ) {

		String line = sc.nextLine();

		if ( w < line.length() ) 
		    w = line.length();

		for( int i=0; i<line.length(); i++ )
		    maze[i][row] = line.charAt( i );

		h++;
		row++;
	    } 

	    for( int i=0; i<w; i++ )
		maze[i][row] = WALL;
	    h++;
	    row++;

	} catch( Exception e ) { System.out.println( "Error reading file" ); }

	//at init time, maze has not been solved:
	solved = false;
    }//end constructor


    public String toString() 
    {
	//send ANSI code "ESC[0;0H" to place cursor in upper left
	String retStr = "[0;0H";  
	//emacs shortcut: C-q, then press ESC
	//emacs shortcut: M-x quoted-insert, then press ESC
	//String retStr = "";
	int i, j;
	for( i=0; i<h; i++ ) {
	    for( j=0; j<w; j++ )
		retStr = retStr + maze[j][i];
	    retStr = retStr + "\n";
	}
	return retStr;
    }


    //helper method to keep try/catch clutter out of main flow
    private void delay( int n ) 
    {
	try {
	    Thread.sleep(n);
	} catch( InterruptedException e ) {
	    System.exit(0);
	}
    }


    /*********************************************
     * void solve(int x,int y) -- recursively finds maze exit (depth-first)
     * @param x starting x-coord, measured from left
     * @param y starting y-coord, measured from top
     *********************************************/
    public void solve( int x, int y ) {

	delay(150); //slow it down enough to be followable
	
	//primary base case
	if ( solved ) {
	    System.exit(0);
	}

	//other base case(s)...
	else if ( !(onPath(x,y))  ) {
	    return;
	}
	//recursive reduction
	else {
	    maze[x][y] = HERO;
		System.out.println( this );
		solve(x , y + 1);
	    solve(x , y - 1);
	    solve(x + 1, y);
	    solve(x - 1, y);
	    maze[x][y] = VISITED_PATH;
	    //System.out.println( this );
	}
    }

    //accessor method to help with randomized drop-in location
    public boolean onPath( int x, int y) { 
	//Checks if the hero is off grid before taking an index. 
	//Implication: no walls need to border the maze.
	if ((x<0)||(y<0)||(x>maze.length)||(y>maze[0].length)){return false;}
	//Check if the hero has seen an exit. 
	else if ( maze[x][y] == EXIT ) {
	    solved = true;
	    System.out.println( this );
	    return false;
	}
	//Otherwise, determine if the hero has seen a viable path. 
	return maze[x][y] == PATH; }

}//end class MazeSolver


public class Maze 
{
    public static void main( String[] args ) 
    {
	MazeSolver ms = null;
	try {
	    String mazeInputFile = args[0];
	    ms = new MazeSolver( mazeInputFile );
	    //clear screen
	    System.out.println( "[2J" ); 
		} 
		catch( Exception e ) { 
	    System.out.println( "Error reading input file." );
	    System.out.println( "Usage: java Maze <filename>" );
		}
	    //display maze 
	    System.out.println( ms );

	    //drop hero into the maze (coords must be on path)
	    //comment next line out when ready to randomize startpos
	    //  ms.solve( 1,0 ); 

	    
	    //drop our hero into maze at random location on path
	    //the Tim Diep way:
	    Random r = new Random();
	    int startX = r.nextInt( 80 );
	    int startY = r.nextInt( 25 );
	    while ( !ms.onPath(startX,startY) ) {
		startX = r.nextInt( 80 );
		startY = r.nextInt( 25 );
	    }
	    ms.solve( startX, startY );
		/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	 
	}

}//end class Maze
