public class TicTacToe {
	
	public enum Symbol {
		None, x, o
	}
	
	private static Symbol[][] board =  new Symbol[3][3];
	
	public static void configureBoard() {
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j< 3; j++) {
				board[i][j] = Symbol.None;
			}
		}        
    }
}
