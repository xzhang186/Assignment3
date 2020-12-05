import java.util.*;


public class TowersOfFourOrMore {
    int rows;
    int cols;
    int filled;
    int[][] board;
    List<String> movesList;

    //default value -1
    private static final int DEFAULT_VALUE = -1;
    //value 0
    private static final int ZERO_VALUE = 0;
    //value 1
    private static final int ONE_VALUE = 1;

    //Constructor
    public TowersOfFourOrMore(int rows, int cols, int filled){
        this.rows = rows;
        this.cols = cols;
        this.filled = filled;
        
        this.board = new int[rows][cols];
        this.movesList = new ArrayList<String>();
    }

    //Initial board
    private void initBoard()
    {
        for(int i = 0;i < rows;i++){
            for(int j = 0; j < cols; j++){
                board[i][j] = DEFAULT_VALUE;
            }
        }
    }

    //Fill board
    private void fillBoard(){
        List<Integer> list = new ArrayList<Integer>(filled * rows);
        
        int value = ZERO_VALUE;

        for(int i = 0; i < filled; i++){
            for(int j = 0; j < rows; j++){
                list.add(value);
            }

            if(value == ZERO_VALUE){
	//set value 1
                value++;
            }
            else {
	//set value 0
                value--;
            }
        }

        int i = 0;
        int random = -1;

        while(!(list.isEmpty())){
            for(int j = 0; j < filled; j++){
                random = new Random().nextInt(list.size());
                board[i][j] = list.get(random);
                list.remove(random);
            }

            i++;
        }
    }

    //Filled
    public void filled(){
        initBoard();
        fillBoard();
    }

    //Print line-through
    private void printLineThrough(int count) {
	for(int i = 0; i < count; i++){
	    System.out.print("- ");
	}

	System.out.println();
    }

    //Print value sequence
    private void printValueSequence(int count) {
        for(int i = 0; i < count; i++){
            System.out.print(i + " ");
        }

        System.out.println();
    }

    //Print board
    private void printBoard(){
        for(int i = 0;i < rows; i++){
            for(int j = 0; j < cols; j++){
                if(board[i][j] != DEFAULT_VALUE) {
                    System.out.print(board[i][j] + " ");
                }
            }

            System.out.println();
        }
    }



    //Show Panel
    public void showPanel(int cols){
        printLineThrough(cols);
        printBoard();
        printLineThrough(cols);
        printValueSequence(cols);
        System.out.println();
    }

    //Is column flag
    private boolean isColumnFlag(int column){
        int value = board[0][column];
        boolean flag = true;

        for(int i = 0; i < rows; i++){
            if((board[i][column] == value) && (value != DEFAULT_VALUE)){
                continue;
            }

            flag = false;
            break;
        }

        return flag;
    }


    //Make moves toward the goal state
    public void makeMoves(){
        int oneCount = 0;
        int zeroCount = 0;
        int check = 0;
        int count= 0 ;
        int index = 0;

        while(count < filled - 1) {
            int z = count + 2;

            for(int i = count; i < z; i++) {
                for (int j = 0; j < rows; j++) {
                    int m = board[rows - j - 1][cols - check - 1];
                    
	    if(board[j][i] == 0){
                        zeroCount++;
	     }
                    else if(board[j][i] == 1) {
                        oneCount++;
	     }

                    board[rows - j - 1][cols - check - 1] = board[j][i];
                    movesList.add(" t" + i + " - t" + (cols-check-1));
                    board[j][i] = m;
                }

                check++;
            }

            check = 0;
            index = count;

            if(zeroCount >= oneCount) {
                int x = 0;
                int y = 0;
                
	for (int k = cols - 2; k < cols; k++) {
                    for (int l = 0; l < rows; l++) {
                        if (!isColumnFlag(index)) {
                            if (board[l][k] == 0) {
                                board[rows - x - 1][index] = board[l][k];
                                movesList.add(" t" + k + " - t" + (index));
                                x++;
                                board[l][k] = -1;
                            }
                            else {
                                board[rows - y - 1][index + 1] = board[l][k];
                                movesList.add(" t"+k+" - t"+(index+1));
                                y++;
                                board[l][k] = -1;
                            }
                        }
                        else {
                            board[rows - y - 1][index + 1] = board[l][k];
			    movesList.add(" t" + k + " - t" + (index + 1));
                            y++;
                            board[l][k] = -1;
                        }
                    }
                }
            }
            else if (zeroCount < oneCount) {
                int x = 0;
                int y = 0;

                for (int k = cols - 2; k < cols; k++) {
                    for (int l = 0; l < rows; l++) {
                        if (!isColumnFlag(index)) {
                            if (board[l][k] == 1) {
                                board[rows - x - 1][index] = board[l][k];
                                movesList.add(" t" + k + " - t" + (index));
                                x++;
                                board[l][k] = -1;
                            }
                            else {
                                board[rows - y - 1][index + 1] = board[l][k];
				movesList.add(" t" + k + " - t" + (index + 1));
                                y++;
                                board[l][k] = -1;
                            }
                        }
                        else {
                            board[rows - y - 1][index + 1] = board[l][k];
			    movesList.add(" t" + k + " - t" + (index + 1));
                            y++;
                            board[l][k] = -1;
                        }
                    }
                }
            }

            count++;
            zeroCount = 0;
            oneCount = 0;
        }
    }

    //Show moves
    public void showMoves(){
        System.out.println("Show moves toward the goal state:");

        for(String moves : movesList){
            System.out.println(moves);
        }

        System.out.println();
    }

    //Main
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int cols = 0;
        int rows = 0;
        int filled = 0 ;

        if(args.length != 3){
            System.out.println("Usage:\njava TowersOfFourOrMore 5 4 2");
            System.exit(1);
        }

        cols = Integer.parseInt(args[0]);
        rows = Integer.parseInt(args[1]);
        filled = Integer.parseInt(args[2]);
        
        TowersOfFourOrMore tower = new TowersOfFourOrMore(rows,cols,filled);
        System.out.println("Generate the start of the game:");
        tower.filled();
	tower.showPanel(cols);
        tower.makeMoves();
	tower.showMoves();
    }
}
