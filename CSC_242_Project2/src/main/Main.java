package main;

import pl.core.Negation;
import pl.core.Sentence;
import pl.core.Symbol;
import pl.examples.WumpusWorldKB;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danielsaltz on 3/1/17.
 */
public class Main {

    public static void main(String...args){

        List<String> failures = new ArrayList<String>();

        /************************************************
         WUMPUS WORLD TESTS
         ************************************************/

//        inferThatNotP11True
            ModelChecking test = new ModelChecking(new WumpusWorldKB());
            Sentence p11 = new Negation(new Symbol("P1,1"));
            if (!test.entails(p11)){
                failures.add("Test failed: inferThatNotP11True");
            }

//       inferThatP12False
            test = new ModelChecking(new WumpusWorldKB());
            Sentence p12 = new Symbol("P1,2");
            if (test.entails(p12)){
                failures.add("Test failed: inferThatP12False");
            }

//        inferThatNotP12True
            test = new ModelChecking(new WumpusWorldKB());
            Sentence notP12 = new Negation(new Symbol("P1,2"));
            if (!test.entails(notP12)){
                failures.add("Test failed: inferThatNotP12True");
            }

//        cantInferP31
            // Given just our current KB, we don't know if the pit is in 2,2 or 3,1
            test = new ModelChecking(new WumpusWorldKB());

            // we should not be able to infer the P31=True
            Sentence p31 = new Symbol("P3,1");
            if (test.entails(p31)){
                failures.add("Test failed: cantInferP31");
            }

//        givenNotP22InferP31
            // generate new sentence in KB saying that P22=False
            Sentence notP22 = new Negation(new Symbol("P2,2"));
            WumpusWorldKB kb = new WumpusWorldKB();
            kb.add(notP22);
            test = new ModelChecking(kb);

            // Now, we are able to use this added information to infer that P31 is true
            p31 = new Symbol("P3,1");
            if (!test.entails(p31)){
                failures.add("Test failed: givenNotP22InferP31");
            }

//
//        /************************************************
//         MODUS PONENS TESTS
//         ************************************************/
//
////        inferQTrue
//            test = new ModelChecking(new ModusPonensKB());
//            Sentence q = new Symbol("Q");
//            if (!test.entails(q)){
//                failures.add("Test failed: inferQTrue");
//            }
//
////          inferNotQFalse
//            test = new ModelChecking(new ModusPonensKB());
//            Sentence notQ = new Negation(new Symbol("Q"));
//            if (test.entails(notQ)){
//                failures.add("Test failed: inferNotQFalse");
//            }
//
//        /************************************************
//         HORN CLAUSES TESTS
//         ************************************************/
//
//        // tests question a
//        //nferMythicalFalse
//            test = new ModelChecking(new HornClausesKB());
//            Sentence myth = new Symbol("myth");
//            if (test.entails(myth)){
//                failures.add("Test failed: nferMythicalFalse");
//            }
//
//        // tests question b
//        //inferMagicalTrue
//            test = new ModelChecking(new HornClausesKB());
//            Sentence mag = new Symbol("mag");
//            if (!test.entails(mag)){
//                failures.add("Test failed: inferMagicalTrue");
//            }
//
//        // tests question c
//        //inferHornedTrue
//            test = new ModelChecking(new HornClausesKB());
//            Sentence h = new Symbol("h");
//            if (!test.entails(h)){
//                failures.add("Test failed: inferHornedTrue");
//            }

        printFailures(failures);
        test.printModels();
    }

    private static void printFailures(List<String> failures) {
        if (failures.isEmpty()){
            System.out.println("\nNo failures!");
        } else {
            for (String s : failures){
                System.out.println(s);
            }
        }
    }
}
