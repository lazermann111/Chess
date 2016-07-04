package unitTests;

import static org.junit.Assert.*;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import chessModel.Board;
import chessModel.piece.Piece;
import chessViewController.ChessView;

public class RealBoardTests {

	private static Board b = new Board();

	// brings up board on failure
	@Rule
	public DisplayOnFail failRule = new DisplayOnFail(DisplayOnFail.REALBOARD);

	@BeforeClass
	public static void setup() {
		UIManager.put("OptionPane.minimumSize", new Dimension(400, 400));
	}

	@Before
	public void beforeTest() {
		// blank the test board
		b = new Board();
	}

	@Test
	public void testFENStartPosition() {

		assertFalse("Board is upside down", b.getFEN().startsWith("RNBQKBNR/PPPPPPPP/8/8/8/8/pppppppp/rnbqkbnr"));
		assertTrue(b.getFEN().startsWith("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR"));
		assertEquals("Something at the end is wrong.", b.getFEN(),
				"rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
	}

	@Test
	public void testFENWithMovesFull() {
		// create a standard board

		assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", b.getFEN());
		b.move(6, 4, 4, 4);
		assertEquals("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1", b.getFEN());
		b.move(1, 2, 3, 2);
		assertEquals("rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR w KQkq c6 0 2", b.getFEN());
		b.move(7, 6, 5, 5);
		assertEquals("rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2", b.getFEN());
	}

	@Test
	public void testEnPassant() {
		b.move(1, 6, 2, 6);
		b.move(4, 1, 3, 1);
		b.move(1, 0, 3, 0);
		b.move(3, 1, 2, 0);
		assertTrue(b.getPiece(2, 0) == null);
	}

	@Test
	public void testFENClocks() {

		assertTrue(b.getFEN().endsWith("0 1"));
		b.move(6, 4, 4, 4);
		assertTrue(b.getFEN().endsWith("0 1"));
		b.move(1, 1, 3, 1);
		assertTrue(b.getFEN().endsWith("0 2"));
		b.move(7, 6, 5, 5);
		assertTrue(b.getFEN().endsWith("1 2"));
	}
	
	@Test
	public void testGetAllMoves(){
		ArrayList<Integer[]> allMoves = b.getAllMoves(0);
		int i = 1;
		for (Integer[] move : allMoves){
			if (move == null){
				fail("Move " + i + " the move was null.");
			}
			System.out.println(move[0] + "," + move[1]);
			Piece p = b.getPiece(move[0], move[1]);
			if (p == null){
				fail("Move " + i + " in list failed because the piece was invalid.");
			}
			assertTrue("Move " + i + " in list failed.",p.validMove(move[2], move[3], b.getSquareStatus(move[2], move[3], 0)));
			i++;
		}
	}

	public static void display(Board displayboard) {
		if (displayboard.getPieces().size() == 0) {
			return;
		}
		JOptionPane.showMessageDialog(null, new ChessView(displayboard), "Error Display View",
				JOptionPane.WARNING_MESSAGE);
	}

	public static void display(String str) {
		JOptionPane.showMessageDialog(null, str, "Error Message View", JOptionPane.WARNING_MESSAGE);
	}

	public static Board getBoard() {
		return b;
	}
}