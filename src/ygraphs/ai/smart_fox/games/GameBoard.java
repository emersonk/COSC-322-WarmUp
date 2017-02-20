package ygraphs.ai.smart_fox.games;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GameBoard extends JPanel {
	private static final long serialVersionUID = 1L;
	private int rows = 10;
	private int cols = 10;

	int width = 500;
	int height = 500;
	int cellDim = width / 10;
	int offset = width / 20;

	int posX = -1;
	int posY = -1;

	int r = 0;
	int c = 0;

	Jose game = null;
	private BoardGameModel gameModel = null;

	boolean playerAMove;
	
	public GameBoard(Jose game) {
		this.game = game;
		gameModel = new BoardGameModel(this.rows + 1, this.cols + 1);
		init(true);
	}



	public void init(boolean isPlayerA) {
		String tagB = null;
		String tagW = null;

		tagB = BoardGameModel.POS_MARKED_BLACK;
		tagW = BoardGameModel.POS_MARKED_WHITE;

		gameModel.gameBoard[1][4] = tagW;
		gameModel.gameBoard[1][7] = tagW;
		gameModel.gameBoard[3][1] = tagW;
		gameModel.gameBoard[3][10] = tagW;

		gameModel.gameBoard[8][1] = tagB;
		gameModel.gameBoard[8][10] = tagB;
		gameModel.gameBoard[10][4] = tagB;
		gameModel.gameBoard[10][7] = tagB;
	}
	

	/**
	 * repaint the part of the board
	 * 
	 * @param qrow
	 *            queen row index
	 * @param qcol
	 *            queen col index
	 * @param arow
	 *            arrow row index
	 * @param acol
	 *            arrow col index
	 * @param qfr
	 *            queen original row
	 * @param qfc
	 *            queen original col
	 */
	public boolean markPosition(int qrow, int qcol, int arow, int acol, int qfr, int qfc, boolean opponentMove) {

		System.out.println(qrow + ", " + qcol + ", " + arow + ", " + acol + ", " + qfr + ", " + qfc);

		boolean valid = gameModel.positionMarked(qrow, qcol, arow, acol, qfr, qfc, opponentMove);
		repaint();
		return valid;
	}

	// JCmoponent method
	protected void paintComponent(Graphics gg) {
		Graphics g = (Graphics2D) gg;

		for (int i = 0; i < rows + 1; i++) {
			g.drawLine(i * cellDim + offset, offset, i * cellDim + offset, rows * cellDim + offset);
			g.drawLine(offset, i * cellDim + offset, cols * cellDim + offset, i * cellDim + offset);
		}

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {

				posX = c * cellDim + offset;
				posY = r * cellDim + offset;

				posY = (9 - r) * cellDim + offset;

				if (gameModel.gameBoard[r + 1][c + 1].equalsIgnoreCase(BoardGameModel.POS_AVAILABLE)) {
					g.clearRect(posX + 1, posY + 1, 49, 49);
				}

				if (gameModel.gameBoard[r + 1][c + 1].equalsIgnoreCase(BoardGameModel.POS_MARKED_BLACK)) {
					g.fillOval(posX, posY, 50, 50);
				} else if (gameModel.gameBoard[r + 1][c + 1].equalsIgnoreCase(BoardGameModel.POS_MARKED_ARROW)) {
					g.clearRect(posX + 1, posY + 1, 49, 49);
					g.drawLine(posX, posY, posX + 50, posY + 50);
					g.drawLine(posX, posY + 50, posX + 50, posY);
				} else if (gameModel.gameBoard[r + 1][c + 1].equalsIgnoreCase(BoardGameModel.POS_MARKED_WHITE)) {
					g.drawOval(posX, posY, 50, 50);
				}
			}
		}

	}// method

	// JComponent method
	public Dimension getPreferredSize() {
		return new Dimension(750, 500);
	}


}// end 