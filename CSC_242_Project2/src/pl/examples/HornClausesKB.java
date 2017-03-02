package pl.examples;

import pl.core.KB;

/**
 * Created by danielsaltz on 3/1/17.
 */
public class HornClausesKB extends KB {

    public HornClausesKB() {
        super();

        // these are the rules for Monus Ponens
        // convert to Horn Clauses
//        Symbol p = intern("P");
//        Symbol q = intern("Q");
//        add(p);
//        add(new Implication(p, q));
    }

    public static void main(String[] argv) {
        new HornClausesKB().dump();
    }
}
