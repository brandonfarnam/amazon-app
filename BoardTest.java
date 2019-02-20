package amazons;

import org.junit.Test;
import ucb.junit.textui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/** The suite of all JUnit tests for the amazons package.
 *  @author
 */
public class BoardTest {

    /**
     * Run the JUnit tests in this package. Add xxxTest.class entries to
     * the arguments of runClasses to run other JUnit tests.
     */
    public static void main(String[] ignored) {
        textui.runClasses(UnitTest.class);
    }

    /**
     * Tests basic correctness of put and get on the initialized board.
     */

    @Test
    public void testToString() {
        Board b = new Board();
        assertEquals(INIT_BOARD_STATE, b.toString());
        assertFalse(b.isLegal(Square.sq(1, 0)));
        assertTrue(b.isLegal(Square.sq(3, 0)));
        assertFalse(b.isLegal(Square.sq(3, 0), Square.sq(7, 0)));
        assertTrue(b.isLegal(Square.sq(3, 0), Square.sq(4, 0)));
        assertTrue(b.isLegal(Square.sq(3, 0), Square.sq(7, 4)));
        assertTrue(b.isLegal(Square.sq(3, 0)));
        assertFalse(b.isLegal(Square.sq("d1"),
                Square.sq("e1"), Square.sq("h1")));
        assertTrue(b.isLegal(Square.sq("d1"),
                Square.sq("e1"), Square.sq("d2")));
        assertTrue(b.isLegal(Square.sq("a4"),
                Square.sq("a5"), Square.sq("a4")));


        assertTrue(b.isLegal(Square.sq("d10"),
                Square.sq("d7"), Square.sq("e8")));


    }

    @Test
    public void testUndo() {
        Board b = new Board();
        Move move =  Move.mv(Square.sq("d1"), Square.sq("e1"), Square.sq("d2"));
        b.makeMove(move);
        b.undo();
        assertEquals(b.toString(), INIT_BOARD_STATE);
    }
    static final String INIT_BOARD_STATE =
            "   - - - B - - B - - -\n"
                    + "   - - - - - - - - - -\n"
                    + "   - - - - - - - - - -\n"
                    + "   B - - - - - - - - B\n"
                    + "   - - - - - - - - - -\n"
                    + "   - - - - - - - - - -\n"
                    + "   W - - - - - - - - W\n"
                    + "   - - - - - - - - - -\n"
                    + "   - - - - - - - - - -\n"
                    + "   - - - W - - W - - -\n";
}
