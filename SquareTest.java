package amazons;

import org.junit.Test;
import ucb.junit.textui;

import static org.junit.Assert.assertEquals;

/** The suite of all JUnit tests for the amazons package.
 *  @author Khang and Paul.
 */
public class SquareTest {

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
    public void testqueenMove() {
        assertEquals(Square.sq(2, 4).queenMove(0, 2), Square.sq(2, 6));
    }
    @Test
    public void direction() {
        assertEquals(Square.sq(2, 4).direction(Square.sq(2, 6)), 0);
        assertEquals(Square.sq(2, 4).direction(Square.sq(1, 4)), 6);
    }

    @Test
    public void queenMove() {
        assertEquals(Square.sq(2, 4).queenMove(2, 1), Square.sq(3, 4));
        assertEquals(Square.sq(5, 5).queenMove(5, 2), Square.sq(3, 3));

    }

    @Test
    public void sq() {
        assertEquals(Square.sq(0, 0), Square.sq("a1"));
        assertEquals(Square.sq(3, 4), Square.sq("d5"));
    }

}
