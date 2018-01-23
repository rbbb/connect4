package connect4;

import java.io.IOException;

public class Connect4 {
	public static void main(String[] args) {
		(new Connect4()).runGame();
	}
	
	private enum Token{
		Red,
		Green,
		None
	}
	
	Token[][] gameBoard;
	int turn;
	boolean gameFinished;
	
	public void runGame() {
		resetGame();
		while(true) {
			
			displayBoard();
			
			String prompt;
			if(gameFinished) {
				prompt = "R to reset, Q to quit: ";
				char result = promptNewGame(prompt);
				if(result == 'Q') {
					System.exit(0);
				}
				if(result == 'R') {
					resetGame();
				}
			}else{
				Token tokenColor;
				if(turn % 2 == 0) {
					prompt = "Player 1 [RED] - choose column (1-7): ";
					tokenColor = Token.Red;
				}else {
					prompt = "Player 2 [GREEN] - choose column (1-7): ";
					tokenColor = Token.Green;
				}
				int column = promptColumn(prompt);
				
				insertTokenInColumn(tokenColor, column);
				
				Token winner = checkVictory();
				
				turn++;
				gameFinished = winner!=Token.None || turn==42;
				
				if(winner==Token.Red) {
					System.out.println("Player 1 [RED] wins!");
				}else if(winner==Token.Green) {
					System.out.println("Player 2 [GREEN] wins!");
				}else if(turn==42) {
					System.out.println("Game ended in a draw!");
				}
				
			}
		}
	}

	private void insertTokenInColumn(Token tokenColor, int column) {
		
		int line=5;
		for(int y=5;y>=0;y--) {
			if(gameBoard[column][y]==Token.None) {
				line = y;
			}
		}
		gameBoard[column][line] = tokenColor;
	}
	
	private Token checkVictory() {
		
		//check horizontal connect4
		for(int x=0;x<4;x++) {
			for(int y=0;y<6;y++) {
				if(gameBoard[x][y]!=Token.None
					&& gameBoard[x][y] == gameBoard[x+1][y]
					&& gameBoard[x][y] == gameBoard[x+2][y]
					&& gameBoard[x][y] == gameBoard[x+3][y]) {
					return gameBoard[x][y];
				}
			}
		}
		
		//check vertical connect4
		for(int x=0;x<7;x++) {
			for(int y=0;y<3;y++) {
				if(gameBoard[x][y]!=Token.None
					&& gameBoard[x][y] == gameBoard[x][y+1]
					&& gameBoard[x][y] == gameBoard[x][y+2]
					&& gameBoard[x][y] == gameBoard[x][y+3]) {
					return gameBoard[x][y];
				}
			}
		}
		
		//check diagonal connect4
		for(int x=0;x<4;x++) {
			for(int y=0;y<3;y++) {
				if(gameBoard[x][y]!=Token.None
					&& gameBoard[x][y] == gameBoard[x+1][y+1]
					&& gameBoard[x][y] == gameBoard[x+2][y+2]
					&& gameBoard[x][y] == gameBoard[x+3][y+3]) {
					return gameBoard[x][y];
				}
			}
		}
		
		//check other diagonal connect4
		for(int x=0;x<4;x++) {
			for(int y=3;y<6;y++) {
				if(gameBoard[x][y]!=Token.None
					&& gameBoard[x][y] == gameBoard[x+1][y-1]
					&& gameBoard[x][y] == gameBoard[x+2][y-2]
					&& gameBoard[x][y] == gameBoard[x+3][y-3]) {
					return gameBoard[x][y];
				}
			}
		}
		
		return Token.None;
	}
	
	private int promptColumn(String prompt) {
		while(true) {
			System.out.print(prompt);
			
			int inputChar = -1;
			try{
				while(inputChar==-1 || inputChar=='\r' || inputChar=='\n') {
					inputChar = System.in.read();
				}
			}catch(IOException ioe) {
				System.err.println(ioe);
			}
			System.out.println();
			
			if(inputChar >= '1' && inputChar <= '7') {
				int column = Integer.parseInt(((char)inputChar)+"");
				if(gameBoard[column-1][5] != Token.None) {
					System.out.println("Column full !");
				}else{
					return column-1;
				}
			}
			
			
		}
	}
	
	private char promptNewGame(String prompt) {
		while(true) {
			System.out.print(prompt);
			
			int inputChar = -1;
			try{
				while(inputChar==-1 || inputChar=='\r' || inputChar=='\n') {
					inputChar = System.in.read();
				}
			}catch(IOException ioe) {
				System.err.println(ioe);
			}
			System.out.println();
			
			if(inputChar == 'q' || inputChar == 'Q' ) {
				return 'Q';
			}
			if(inputChar == 'r' || inputChar == 'R') {
				return 'R';
			}
		}
	}
	
	private void resetGame() {
		//reset all game variables
		turn = 0;
		gameFinished = false;
		gameBoard = new Token[7][];
		for(int i=0;i<7;i++) {
			gameBoard[i] = new Token[6];
			for(int j=0;j<6;j++) {
				gameBoard[i][j] = Token.None;
			}
		}
	}
	
	private void displayBoard() {
		for(int y=5;y>=0;y--) {
			System.out.print("|");
			for(int x=0;x<7;x++) {
				switch(gameBoard[x][y]) {
				case Red: System.out.print("R|");break;
				case Green: System.out.print("G|");break;
				case None: System.out.print(" |");break;
				}
			}
			System.out.println();
		}
	}
}
