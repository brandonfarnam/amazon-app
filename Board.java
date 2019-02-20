package amazons;

import java.util.Collections;
import java.util.Iterator;
import java.util.Stack;

import static amazons.Piece.*;
import static amazons.Move.mv;



/** The state of an Amazons Game.
 *  @author Khang
 */
class Board {

    /** The number of squares on a side of the board. */
    static final int SIZE = 10;
    /** Define map to store x,y using 2D Array. */
    private Piece [][]board;

    /** Initializes a game board with SIZE squares on a side in the
     *  initial position. */
    Board() {
        init();
    }

    /** Initializes a copy of MODEL. */
    Board(Board model) {
        copy(model);
    }

    /** Copies MODEL into me. */
    void copy(Board model) {
        if (model == this) {
            return;
        } else {
            init();
            for (int col = 0; col < SIZE; col++) {
                for (int row = 0; row < SIZE; row++) {
                    board[col][row] = model.board[col][row];
                }
            }
            _turn = model._turn;
            _winner = model._winner;
        }
    }

    /** Clears the board to the initial position. */
    void init() {
        board = new Piece[Board.SIZE][Board.SIZE];
        put(WHITE, 0, 3);
        put(WHITE, 3, 0);
        put(WHITE, 6, 0);
        put(WHITE, 9, 3);
        put(BLACK, 0, 6);
        put(BLACK, 3, 9);
        put(BLACK, 9, 6);
        put(BLACK, 6, 9);
        for (int col = 0; col < Board.SIZE; col++) {
            for (int row = 0; row < Board.SIZE; row++) {
                if (board[col][row] == null) {
                    put(EMPTY, col, row);
                }
            }
        }
        _move = new Stack<Move>();
        _turn = WHITE;
        _winner = null;
    }

    /** Return the Piece whose move it is (WHITE or BLACK). */
    Piece turn() {
        return _turn;
    }

    /** Return the number of moves (that have not been undone) for this
     *  board. */
    int numMoves() {
        return _move.size();
    }

    /** Return the winner in the current position, or null if the game is
     *  not yet finished. */
    Piece winner() {
        return _winner;
    }

    /** Return the contents the square at S. */
    final Piece get(Square s) {

        return get(s.col(), s.row());
    }

    /** Return the contents of the square at (COL, ROW), where
     *  0 <= COL, ROW <= 9. */
    final Piece get(int col, int row) {
        return board[col][row];
    }

    /** Return the contents of the square at COL ROW. */
    final Piece get(char col, char row) {
        return get(col - 'a', row - '1');
    }

    /** Set square S to P. */
    final void put(Piece p, Square s) {
        board[s.col()][s.row()] = p;
    }

    /** Set square (COL, ROW) to P. */
    final void put(Piece p, int col, int row) {
        board[col][row] = p;
    }

    /** Set square COL ROW to P. */
    final void put(Piece p, char col, char row) {
        put(p, col - 'a', row - '1');
    }

    /** Return true iff FROM - TO is an unblocked queen move on the current
     *  board, ignoring the contents of ASEMPTY, if it is encountered.
     *  For this to be true, FROM-TO must be a queen move and the
     *  squares along it, other than FROM and ASEMPTY, must be
     *  empty. ASEMPTY may be null, in which case it has no effect. */
    boolean isUnblockedMove(Square from, Square to, Square asEmpty) {
        boolean check = false;
        if (from.isQueenMove(to)) {
            check = true;
            int dir = from.direction(to);
            int totalSteps;
            if (dir == 0 || dir == 4) {
                totalSteps = Math.abs(from.row() - to.row());
            } else if (dir == 2 || dir == 6) {
                totalSteps = Math.abs(from.col() - to.col());
            } else {
                totalSteps = Math.abs(from.col() - to.col());
            }
            for (int i = 1; i < totalSteps + 1; i++) {
                Square checkedSquare = from.queenMove(dir, i);
                if (checkedSquare != null && checkedSquare != asEmpty
                        && get(checkedSquare) != EMPTY) {
                    check = false;
                }
            }
        }
        return check;
    }

    /** Return true iff FROM is a valid starting square for a move. */
    boolean isLegal(Square from) {
        if (_turn == board[from.col()][from.row()]
                && EMPTY != board[from.col()][from.row()]) {
            return true;
        }
        return false;
    }

    /** Return true iff FROM-TO is a valid first part of move, ignoring
     *  spear throwing. */
    boolean isLegal(Square from, Square to) {
        return isUnblockedMove(from, to, null);
    }

    /** Return true iff FROM-TO(SPEAR) is a legal move in the current
     *  position. */
    boolean isLegal(Square from, Square to, Square spear) {
        if (isLegal(from) && isUnblockedMove(from, to, null)) {
            return isUnblockedMove(to, spear, from);
        }
        return false;

    }

    /** Return true iff MOVE is a legal move in the current
     *  position. */
    boolean isLegal(Move move) {
        if (move != null) {
            return isLegal(move.from(), move.to(), move.spear());
        } else {
            return false;
        }
    }

    /** Move FROM-TO(SPEAR), assuming this is a legal move. */
    void makeMove(Square from, Square to, Square spear) {

        if (isLegal(from, to, spear)) {
            board[to.col()][to.row()] = board[from.col()][from.row()];
            board[from.col()][from.row()] = EMPTY;
            board[spear.col()][spear.row()] = SPEAR;
        }

        _turn = _turn.opponent();
        Iterator<Move> iter = new LegalMoveIterator(_turn);
        if (!iter.hasNext()) {
            _winner = _turn.opponent();
        } else {
            _winner = null;
        }
    }

    /** Move according to MOVE, assuming it is a legal move. */
    void makeMove(Move move) {
        makeMove(move.from(), move.to(), move.spear());
        _move.add(move);
    }

    /** Undo one move.  Has no effect on the initial board. */
    void undo() {
        if (!_move.isEmpty()) {
            Move removedMove = _move.pop();
            board[removedMove.spear().col()][removedMove.spear().row()] = EMPTY;
            board[removedMove.from().col()][removedMove.from().row()] =
                    board[removedMove.to().col()][removedMove.to().row()];
            board[removedMove.to().col()][removedMove.to().row()] = EMPTY;


            _turn = _turn.opponent();
        }

    }

    /** Return an Iterator over the Squares that are reachable by an
     *  unblocked queen move from FROM. Does not pay attention to what
     *  piece (if any) is on FROM, nor to whether the game is finished.
     *  Treats square ASEMPTY (if non-null) as if it were EMPTY.  (This
     *  feature is useful when looking for Moves, because after moving a
     *  piece, one wants to treat the Square it came from as empty for
     *  purposes of spear throwing.) */


    /**Start with ReachableFromIterator, start from step of 1,
     * keep incrementing it in one direction
     *     until you hit a nonempty square or run out of the board,
     *     then increment direction and do it
     *     again until you wrapped around the circle.
     * @tag from
     * @tag return
     */
    /**
     *
     * @param from from
     * @param asEmpty empty
     * @return reachablefrom
     */
    Iterator<Square> reachableFrom(Square from, Square asEmpty) {
        return new ReachableFromIterator(from, asEmpty);
    }

    /** Return an Iterator over all legal moves on the current board. */
    Iterator<Move> legalMoves() {
        return new LegalMoveIterator(_turn);
    }

    /** Return an Iterator over all legal moves on the current board for
     *  SIDE (regardless of whose turn it is). */
    Iterator<Move> legalMoves(Piece side) {
        return new LegalMoveIterator(side);
    }

    /** An iterator used by reachableFrom. */
    private class ReachableFromIterator implements Iterator<Square> {

        /** Iterator of all squares reachable by queen move from FROM,
         *  treating ASEMPTY as empty. */
        ReachableFromIterator(Square from, Square asEmpty) {
            _from = from;
            _dir = -1;
            _steps = 0;
            _asEmpty = asEmpty;
            toNext();
        }

        @Override
        public boolean hasNext() {
            return _dir < 8;
        }

        @Override
        public Square next() {

            if (hasNext()) {
                Square s = _from.queenMove(_dir, _steps);
                toNext();
                return s;
            } else {
                return null;
            }
        }

        /** Advance _dir and _steps, so that the next valid Square is
         *  _steps steps in direction _dir from _from. */
        private void toNext() {
            _steps = _steps + 1;
            square = _from.queenMove(_dir, _steps);
            while (hasNext() && (square == null
                    || !isUnblockedMove(_from, square, _asEmpty))) {
                _dir = _dir + 1;
                _steps = 1;
                square = _from.queenMove(_dir, _steps);
            }
        }

        /** Starting square. */
        private Square _from;
        /** Current direction. */
        private int _dir;
        /** Current distance. */
        private int _steps;
        /** Square treated as empty. */
        private Square _asEmpty;
        /** temporary Square. */
        private Square square;
        /** Found boolean. */
        private boolean found;

    }

    /** An iterator used by legalMoves. */
    private class LegalMoveIterator implements Iterator<Move> {

        /** All legal moves for SIDE (WHITE or BLACK). */
        LegalMoveIterator(Piece side) {
            _startingSquares = Square.iterator();
            _spearThrows = NO_SQUARES;
            _pieceMoves = NO_SQUARES;
            _fromPiece = side;
            toNext();
        }

        @Override
        public boolean hasNext() {
            return  (_spearThrows.hasNext());
        }

        @Override
        public Move next() {
            Square sq = _spearThrows.next();
            Move move = mv(_start, _nextSquare, sq);
            toNext();
            return move;
        }

        /** Advance so that the next valid Move is
         *  _start-_nextSquare(sp), where sp is the next value of
         *  _spearThrows. */
        private void toNext() {

            boolean checkSpear = _spearThrows.hasNext();
            boolean checkPieceMoveOrStart;

            if (!checkSpear) {
                pieceMoveCheck = _pieceMoves.hasNext();
                if (!pieceMoveCheck) {
                    startingSquareCheck = _startingSquares.hasNext();
                    if (startingSquareCheck) {
                        _start = _startingSquares.next();
                    } else {
                        return;
                    }

                    if (get(_start) == _fromPiece) {
                        _pieceMoves = reachableFrom(_start, null);
                    }
                }

                pieceMoveCheck = _pieceMoves.hasNext();

                if (pieceMoveCheck) {
                    _nextSquare = _pieceMoves.next();
                    _spearThrows = reachableFrom(_nextSquare, _start);
                }
            }

            startingSquareCheck = _startingSquares.hasNext();

            checkPieceMoveOrStart = (_pieceMoves.hasNext()
                    || startingSquareCheck);

            if (!_spearThrows.hasNext() && checkPieceMoveOrStart) {
                toNext();
            }
        }
        /** Color of side whose moves we are iterating. */
        private Piece _fromPiece;
        /** Current starting square. */
        private Square _start;
        /** Remaining starting squares to consider. */
        private Iterator<Square> _startingSquares;
        /** Current piece's new position. */
        private Square _nextSquare;
        /** Remaining moves from _start to consider. */
        private Iterator<Square> _pieceMoves;
        /** Remaining spear throws from _piece to consider. */
        private Iterator<Square> _spearThrows;
        /** sq. */
        private Square sp;
        /** result. */
        private Move result;
        /** Boolean keep track _spearthrow.*/
        private boolean found;
        /** Check startingSquare has next().*/
        private boolean startingSquareCheck;
        /**PieceMove check.*/
        private boolean pieceMoveCheck;
        /** Check same color PIECE.*/
        private boolean pieceColorCheck;
    }

    @Override
    public String toString() {
        String output = "";
        for (int row = Board.SIZE - 1; row >= 0; row--) {
            output += "  ";
            for (int col = 0; col < Board.SIZE; col++) {
                output += (" " + board[col][row]);
            }
            output += "\n";
        }

        return output;
    }

    /** An empty iterator for initialization. */
    private static final Iterator<Square> NO_SQUARES =
        Collections.emptyIterator();

    /** Piece whose turn it is (BLACK or WHITE). */
    private Piece _turn;
    /** Cached value of winner on this board, or EMPTY if it has not been
     *  computed. */
    private Piece _winner;
    /** Stack to save move. */
    private Stack<Move> _move;
}
