package amazons;

import static amazons.Move.isGrammaticalMove;
import static amazons.Move.mv;
import static amazons.Board.*;

/** A Player that takes input as text commands from the standard input.
 *  @author Khang and Paul
 */
class TextPlayer extends Player {

    /** A new TextPlayer with no piece or controller (intended to produce
     *  a template). */
    TextPlayer() {
        this(null, null);
    }

    /** A new TextPlayer playing PIECE under control of CONTROLLER. */
    private TextPlayer(Piece piece, Controller controller) {
        super(piece, controller);
    }

    @Override
    Player create(Piece piece, Controller controller) {
        return new TextPlayer(piece, controller);
    }

    @Override
    String myMove() {
        /**
         * line g1-g7(b7)
         */
        while (true) {
            String line = _controller.readLine();
            if (line == null) {
                return "quit";
            } else if (isGrammaticalMove(line)
                    && _controller.board().winner() == null) {
                if (!_controller.board().isLegal(mv(line))) {
                    _controller.reportError("Invalid move. "
                            + "Please try again.");
                } else {
                    return line;
                }
            } else {
                return line;
            }
        }
    }
}
