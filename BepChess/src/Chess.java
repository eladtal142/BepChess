
public class Chess {
	
	private int[][] board = new int[8][8];
	final String startPos = "-3,-5,-4,-2,-1,-4,-5,-3/-6,-6,-6,-6,-6,-6,-6,-6/0,0,0,0,0,0,0,0/0,0,0,0,0,0,0,0/0,0,0,0,0,0,0,0/0,0,0,0,0,0,0,0/6,6,6,6,6,6,6,6/3,5,4,2,1,4,5,3";
	boolean whiteTurn = true;
	
	public Chess() {
		loadPieces();
	}
	
	public int[][] getBoard(){
		return board;
	}
	
	public boolean isWhiteTurn() {
		return whiteTurn;
	}

	
	public void movePiece(int startX, int startY, int x, int y) {
		whiteTurn = !whiteTurn;
		board[y][x] = board[startY][startX];
		board[startY][startX] = 0;
		if (isWhiteCheckmated()) {
			System.out.println("White is checkmated!");
			loadPieces();
		}
		if (isBlackCheckmated()) {
			System.out.println("Black is checkmated!");
			loadPieces();
		}
	}

	public boolean isLegalPawn(int x1, int y1, int x2, int y2){
		int piece = board[y1][x1];
		int spot = board[y2][x2];
		
		if (spot != 0 && Math.abs(spot)/spot == Math.abs(piece)/piece)
			return false;
		
		if (piece > 0) {

			if (y1 == 6 && (y2 == 5 || y2 == 4) && x1 == x2 && spot == 0)
				return true;
			if (y2 == y1 - 1 && x1 == x2 && spot == 0)
				return true;
			if (y2 == y1 - 1 && (x2 == x1 + 1 || x2 == x1 - 1) && spot != 0)
				return true;

		}else{
			
			if (y1 == 1 && (y2 == 2 || y2 == 3) && x1 == x2 && spot == 0)
				return true;
			if (y2 == y1 + 1 && x1 == x2 && spot == 0)
				return true;
			if (y2 == y1 + 1 && (x2 == x1 + 1 || x2 == x1 - 1) && spot != 0)
				return true;

		}

		return false;
	}

	public boolean isLegalKnight(int x1, int y1, int x2, int y2){
		int piece = board[y1][x1];
		int spot = board[y2][x2];
		
		if (spot != 0 && Math.abs(spot)/spot == Math.abs(piece)/piece)
			return false;
		
		if (Math.abs(x2 - x1) == 2 && Math.abs(y2 - y1) == 1)
			return true;
		if (Math.abs(x2 - x1) == 1 && Math.abs(y2 - y1) == 2)
			return true;
		
		return false;
	}

	public boolean isLegalBishop(int x1, int y1, int x2, int y2){
		int piece = board[y1][x1];
		int spot = board[y2][x2];
		
		if (spot != 0 && Math.abs(spot)/spot == Math.abs(piece)/piece)
			return false;
		
		if (Math.abs(x2 - x1) == Math.abs(y2 - y1)){
			int x = x1;
			int y = y1;
			int xIndex = (x2 - x1)/Math.abs(x2 - x1);
			int yIndex = (y2 - y1)/Math.abs(y2 - y1);
			while (x != x2 && y != y2){
				x += xIndex;
				y += yIndex;
				if (board[y][x] != 0 && (x != x2 || y != y2))
					return false;
			}
			return true;
		}
		
		return false;
	}
	
	public boolean isLegalRook(int x1, int y1, int x2, int y2){
		int piece = board[y1][x1];
		int spot = board[y2][x2];
		
		if (spot != 0 && Math.abs(spot)/spot == Math.abs(piece)/piece)
			return false;

		if (x1 == x2){
			int y = y1;
			int yIndex = (y2 - y1)/Math.abs(y2 - y1);
			while (y != y2){
				y += yIndex;
				if (board[y][x1] != 0 && y != y2)
					return false;
			}
			return true;
		}

		if (y1 == y2){
			int x = x1;
			int xIndex = (x2 - x1)/Math.abs(x2 - x1);
			while (x != x2){
				x += xIndex;
				if (board[y1][x] != 0 && x != x2)
					return false;
			}
			return true;
		}

		return false;
		
	}

	public boolean isLegalQueen(int x1, int y1, int x2, int y2){
		int piece = board[y1][x1];
		int spot = board[y2][x2];
		
		if (spot != 0 && Math.abs(spot)/spot == Math.abs(piece)/piece)
			return false;

		if (Math.abs(x2 - x1) == Math.abs(y2 - y1)){
			int x = x1;
			int y = y1;
			int xIndex = (x2 - x1)/Math.abs(x2 - x1);
			int yIndex = (y2 - y1)/Math.abs(y2 - y1);
			while (x != x2 && y != y2){
				x += xIndex;
				y += yIndex;
				if (board[y][x] != 0 && (x != x2 || y != y2))
					return false;
			}
			return true;
		}

		if (x1 == x2){
			int y = y1;
			int yIndex = (y2 - y1)/Math.abs(y2 - y1);
			while (y != y2){
				y += yIndex;
				if (board[y][x1] != 0 && y != y2)
					return false;
			}
			return true;
		}

		if (y1 == y2){
			int x = x1;
			int xIndex = (x2 - x1)/Math.abs(x2 - x1);
			while (x != x2){
				x += xIndex;
				if (board[y1][x] != 0 && x != x2)
					return false;
			}
			return true;
		}

		return false;

	}
	
	public boolean isLegalKing(int x1, int y1, int x2, int y2) {
		int piece = board[y1][x1];
		int spot = board[y2][x2];
		
		if (spot != 0 && Math.abs(spot)/spot == Math.abs(piece)/piece)
			return false;

		if (Math.abs(x2 - x1) <= 1 && Math.abs(y2 - y1) <= 1)
			return true;

		return false;

	}

	public int getNumMoves(int x, int y){
		int num = 0;
		for (int i =  0; i < board.length; i++){
			for (int j = 0; j < board.length; j++){
				if (isLegal(x, y, j, i))
					num++;
			}
		}
		return num;
	}

	public boolean isWhiteCheckmated() {
		if (!isWhiteTurn())
			return false;
		int sum = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] > 0) {
					sum += getNumMoves(j, i);
				}
			}
		}
		if (sum == 0)
			return true;

		return false;

	}

	public boolean isBlackCheckmated() {
		if (isWhiteTurn())
			return false;
		int sum = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] < 0) {
					sum += getNumMoves(j, i);
				}
			}
		}
		if (sum == 0)
			return true;

		return false;

	}

	public boolean isKingChecked(){

		int kingX = 0;
		int kingY = 0;
		int king = 0;
		int turn = (isWhiteTurn()) ? 1 : -1;
		if (turn == 1){
			king = 1;
		}
		else{
			king = -1;
		}

		for (int y = 0; y < 8; y++){
			for (int x = 0; x < 8; x++){
				if (board[y][x] == king){
					kingX = x;
					kingY = y;
				}
			}
		}

		for (int y = 0; y < 8; y++){
			for (int x = 0; x < 8; x++){
				if (board[y][x] != 0 && Math.abs(board[y][x])/board[y][x] != turn){
					int pt = Math.abs(board[y][x]);
					if (pt == 6 && isLegalPawn(x, y, kingX, kingY)) 
						return true; 
					if (pt == 5 && isLegalKnight(x, y, kingX, kingY)) 
						return true; 
					if (pt == 4 && isLegalBishop(x, y, kingX, kingY)) 
						return true; 
					if (pt == 3 && isLegalRook(x, y, kingX, kingY)) 
						return true; 
					if (pt == 2 && isLegalQueen(x, y, kingX, kingY)) 
						return true; 
					
				}
			}
		}

		return false;
	}

	public boolean isLegal(int x1, int y1, int x2, int y2){
		int pieceType = Math.abs(board[y1][x1]);
			
		int[][] temp = new int[board.length][board[0].length];
		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board[0].length; j++)
				temp[i][j] = board[i][j];
		board[y2][x2] = board[y1][x1];
		board[y1][x1] = 0;
		if (isKingChecked()){
			board = temp;
			return false;
		}
		board = temp;

		if(pieceType == 6) return isLegalPawn(x1, y1, x2, y2);
		if(pieceType == 5) return isLegalKnight(x1, y1, x2, y2);
		if(pieceType == 4) return isLegalBishop(x1, y1, x2, y2);
		if(pieceType == 3) return isLegalRook(x1, y1, x2, y2);
		if(pieceType == 2) return isLegalQueen(x1, y1, x2, y2);
		if(pieceType == 1) return isLegalKing(x1, y1, x2, y2);
		return false;
	}
	
	public int[][] legalMoves(int x, int y){
		int numMoves = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (isLegal(x, y, i, j))
					numMoves++;
			}
		}
		int[][] legalMoves = new int[numMoves][2];
		int movesAdded = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (isLegal(x, y, i, j)) {
					int[] move = {i, j};
					legalMoves[movesAdded] = move;
					movesAdded++;
				}
			}
		}
		return legalMoves;
	}
	
	public void loadPieces() {
		String[] ranks = startPos.split("/");
		for (int i = 0; i < ranks.length; i++) {
			String[] pieces = ranks[i].split(",");
			for (int j = 0; j < pieces.length; j++) {
				board[i][j] = Integer.parseInt(pieces[j]);
			}
		}
		whiteTurn = true;
	}
	
}
