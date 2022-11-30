import processing.core.*;
import java.awt.Point;
import java.util.ArrayList;

public class DrawingSurface extends PApplet{

	PImage boardImage;
	int[] boardSize = {800, 800};
	ArrayList<PImage> pieceImages = new ArrayList<PImage>();
	final String imagePath;
	String[] pieceNames = {"king", "queen", "rook", "bishop", "knight", "pawn"};
	Chess chessGame; 
	Point selected = null;
	
	public DrawingSurface() {
		imagePath = "images/";
		chessGame = new Chess();
	}
	
	public void setup() {
		boardImage = loadImage(imagePath + "board.png");
		boardImage.resize(boardSize[0], boardSize[1]);
		
		for (int i = 0; i < pieceNames.length; i++) {
			PImage img = loadImage(imagePath + "white-" + pieceNames[i] + ".png");
			img.resize(boardSize[0]/chessGame.getBoard().length, boardSize[1]/chessGame.getBoard().length);
			pieceImages.add(img);
		}
		for (int i = 0; i < pieceNames.length; i++) {
			PImage img = loadImage(imagePath + "black-" + pieceNames[i] + ".png");
			img.resize(boardSize[0]/chessGame.getBoard().length, boardSize[1]/chessGame.getBoard().length);
			pieceImages.add(img);		
		}

	}
	
	public void settings() {
		size(800,800);
	}
	
	public void draw() {
		background(252, 246, 235);
		image(boardImage, 0, 0);
		
		if (selected != null) {
			fill(200, 150, 70, 150);
			noStroke();
			rect(selected.x*boardSize[0]/chessGame.getBoard().length, selected.y*boardSize[1]/chessGame.getBoard().length, boardSize[0]/chessGame.getBoard().length, boardSize[1]/chessGame.getBoard().length);
		}
		
		int[][] board = chessGame.getBoard();
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] > 0)
					image(pieceImages.get(board[i][j]-1), boardSize[0]/chessGame.getBoard().length*j, boardSize[1]/chessGame.getBoard().length*i);
				else if (board[i][j] < 0)
					image(pieceImages.get(Math.abs(board[i][j])+6-1), boardSize[0]/chessGame.getBoard().length*j, boardSize[1]/chessGame.getBoard().length*i);
			}
		}
		
		if (selected != null) {
			int[][] legalMoves = chessGame.legalMoves(selected.x, selected.y);
			for (int i = 0; i < legalMoves.length; i++) {
				if (chessGame.getBoard()[legalMoves[i][1]][legalMoves[i][0]] == 0) {
					fill(50, 50, 50, 50);
					noStroke();
					ellipse(legalMoves[i][0]*boardSize[0]/chessGame.getBoard().length + boardSize[0]/chessGame.getBoard().length/2, legalMoves[i][1]*boardSize[1]/chessGame.getBoard().length + boardSize[1]/chessGame.getBoard().length/2, boardSize[0]/chessGame.getBoard().length/3.3f, boardSize[1]/chessGame.getBoard().length/3.3f);
				}else{
					noFill();
					stroke(50, 50, 50, 50);
					strokeWeight(10);
					ellipse(legalMoves[i][0]*boardSize[0]/chessGame.getBoard().length + boardSize[0]/chessGame.getBoard().length/2, legalMoves[i][1]*boardSize[1]/chessGame.getBoard().length + boardSize[1]/chessGame.getBoard().length/2, boardSize[0]/chessGame.getBoard().length/1.1f, boardSize[1]/chessGame.getBoard().length/1.1f);
				}
				
			}
		}
		
	}
	
	public Point getTile(float x, float y) {
		return new Point((int)(x/(boardSize[0]/chessGame.getBoard().length)), (int)(y/(boardSize[1]/chessGame.getBoard().length)));
	}
	
	public void mousePressed() {
		if (selected == null) {
			if((chessGame.isWhiteTurn() && chessGame.getBoard()[getTile(mouseX, mouseY).y][getTile(mouseX, mouseY).x] > 0) || (!chessGame.isWhiteTurn() && chessGame.getBoard()[getTile(mouseX, mouseY).y][getTile(mouseX, mouseY).x] < 0))
				selected = getTile(mouseX, mouseY);
		}else {
			int[][] legalMoves = chessGame.legalMoves(selected.x, selected.y);
			Point current = getTile(mouseX, mouseY);
			boolean isLegal = false;
			for (int[] pos : legalMoves) {
				if (pos[0] == current.x && pos[1] == current.y)
					isLegal = true;
			}
			if (isLegal) {
				chessGame.movePiece(selected.x, selected.y, current.x, current.y);
			}
			selected = null;
		}
	}
	
}
