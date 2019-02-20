package amazons;
import org.junit.Test;
import static org.junit.Assert.*;
import ucb.junit.textui;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/** Junit tests for our Board iterators.
 *  @author
 */
public class IteratorTests {

    /** Run the JUnit tests in this package. */
    public static void main(String[] ignored) {
        textui.runClasses(IteratorTests.class);
    }

    /** Tests reachableFromIterator to make sure it returns all reachable
     *  Squares. This method may need to be changed based on
     *   your implementation. */
    @Test
    public void testReachableFrom() {
        Board b = new Board();
        int numSquares = 0;
        Set<Square> squares = new HashSet<>();
        Iterator<Square> reachableFrom = b.reachableFrom(Square.sq(5, 4), null);
        while (reachableFrom.hasNext()) {
            Square s = reachableFrom.next();
            assertTrue(SQUARES.contains(s));
            numSquares += 1;
            squares.add(s);
        }
        System.out.println(squares);
        System.out.print(SQUARES);
        System.out.print(numSquares);

        assertEquals(SQUARES.size(), numSquares);
        assertEquals(SQUARES.size(), squares.size());
    }

    /** Tests legalMovesIterator to make sure it returns all legal Moves.
     *  This method needs to be finished and may need to be changed
     *  based on your implementation. */
    @Test
    public void testLegalMoves() {
        Board b = new Board();
        int numMoves = 0;
        Set<Move> moves = new HashSet<>();
        Iterator<Move> legalMoves = b.legalMoves(Piece.BLACK);
        while (legalMoves.hasNext()) {
            Move m = legalMoves.next();
            assertTrue(MOVE_HASH_SET.contains(m));
            numMoves += 1;
            moves.add(m);
        }
        assertEquals(MOVE_HASH_SET.size(), numMoves);
        assertEquals(MOVE_HASH_SET.size(), moves.size());
    }

    @Test
    public void testLegalMoves1() {
        Board b = new Board();
        int numMoves = 0;
        Set<Move> moves = new HashSet<>();

        Iterator<Move> legalMoves = b.legalMoves(Piece.WHITE);
        while (legalMoves.hasNext()) {
            Move m = legalMoves.next();
            System.out.println(m);
            assertTrue(b.isLegal(m));
            numMoves += 1;
            moves.add(m);
        }
        System.out.print(numMoves);
        assertEquals(2176, numMoves);
        assertEquals(2176, moves.size());
    }


    /** Tests legalMovesIterator to make sure it returns all legal Moves.
     *  This method needs to be finished and may need to be changed
     *  based on your implementation. */
    @Test
    public void testReachableFromSp() {
        Board b = new Board();
        int numSquares = 0;
        Set<Square> squares = new HashSet<>();
        Iterator<Square> reachableFrom =
                b.reachableFrom(Square.sq("c9"), Square.sq("b9"));
        while (reachableFrom.hasNext()) {
            Square s = reachableFrom.next();
            numSquares += 1;
            squares.add(s);
        }

        System.out.println(squares);
        System.out.print(numSquares);
    }


    public static void buildBoard(Board b, Piece[][] target) {
        for (int col = 0; col < Board.SIZE; col++) {
            for (int row = Board.SIZE - 1; row >= 0; row--) {
                Piece piece = target[Board.SIZE - row - 1][col];
                b.put(piece, Square.sq(col, row));
            }
        }
        System.out.println(b);
    }

    /** Tests legalMovesIterator to make sure it returns all legal Moves.
     *  This method needs to be finished and may need to be changed
     *  based on your implementation. */
    @Test
    public void testWin() {
        Board b = new Board();
        int numSquares = 0;
        Set<Square> squares = new HashSet<>();
        Iterator<Square> reachableFrom =
                b.reachableFrom(Square.sq("c9"), Square.sq("b9"));
        while (reachableFrom.hasNext()) {
            Square s = reachableFrom.next();
            numSquares += 1;
            squares.add(s);
        }

        System.out.println(squares);
        System.out.print(numSquares);
    }


    @Test
    public void testAI() {
        Board b = new Board();
        int numSquares = 0;
        Set<Square> squares = new HashSet<>();
        Iterator<Square> reachableFrom =
                b.reachableFrom(Square.sq("c9"), Square.sq("b9"));
        while (reachableFrom.hasNext()) {
            Square s = reachableFrom.next();
            numSquares += 1;
            squares.add(s);
        }

        System.out.println(squares);
        System.out.print(numSquares);
    }

    static final Piece E = Piece.EMPTY;

    static final Piece W = Piece.WHITE;

    static final Piece B = Piece.BLACK;

    static final Piece S = Piece.SPEAR;

    static final Set<Square> SQUARES =
            new HashSet<>(Arrays.asList(
                    Square.sq(5, 5),
                    Square.sq(4, 5),
                    Square.sq(4, 4),
                    Square.sq(6, 4),
                    Square.sq(7, 4),
                    Square.sq(6, 5),
                    Square.sq(7, 6),
                    Square.sq(8, 7)));


    static final Set<Move> MOVE_HASH_SET =
            new HashSet<>(Arrays.asList(
                    Move.mv(Square.sq("c9"),
                            Square.sq("b9"), Square.sq("c9"))));

}
