package main;

import pl.cnf.Clause;
import pl.core.Negation;
import pl.core.Sentence;
import pl.core.Symbol;
import pl.examples.WumpusWorldKB;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;


/**
 * Created by danielsaltz on 3/3/17.
 */
public class TestDPLL {

    public static void main(String... args){

        List<String> failures = new ArrayList<String>();


        // trying to determine if the kb entails alpha
        // we achieve this by showing that (kb ^ !alpha) is unsatisfiable
        // therefore, the "alpha" we send into the dpll algorithm is really (kb ^ !alpha),
        // and if returns false, then (kb ^ !alpha) is not satisfiable, and we know kb entails alpha


        // we need to combine the KB and not Beta
        // for the KB to be true, all of its sentences must be true
        // so to combine the KB and !Beta, we create a new list of sentences that constists of KB and !Beta


        /************************************************
         WUMPUS WORLD TESTS
         ************************************************/

//        canInferNotP11
        DPLL test = new DPLL(new WumpusWorldKB());
        Sentence notP11 = new Negation(new Symbol("P1,1"));
        Collection<Sentence> sentences = test.kb.sentences();
        sentences.add(new Negation(notP11)); // add !Beta
        Set<Clause> clauses = test.convert(sentences);
//        if (test.satisfiable(clauses)){ // we expect it to be NOT satisfiable
//            failures.add("Test failed: canInferNotP11");
//        }

//       cantInferP12
        test = new DPLL(new WumpusWorldKB());
        Sentence p12 = new Symbol("P1,2");
        sentences = test.kb.sentences();
        sentences.add(new Negation(p12)); // add !Beta
        clauses = test.convert(sentences);
        if (!test.satisfiable(clauses)){ // we expect it to be YES satisfiable --> (can't infer p12)
            failures.add("Test failed: cantInferP12");
        }
//
////        cantInferNotP12
//        test = new DPLL(new WumpusWorldKB());
//        Sentence notP12 = new Negation(new Symbol("P1,2"));
//        sentences = test.kb.sentences();
//        sentences.add(new Negation(notP12)); // add !Beta
//        clauses = test.convert(sentences);
//        if (!test.satisfiable(clauses)){
//            failures.add("Test failed: cantInferNotP12");
//        }
//
////        cantInferP31
//        // Given just our current KB, we don't know if the pit is in 2,2 or 3,1
//        test = new DPLL(new WumpusWorldKB());
//
//        // we should not be able to infer the P31=True
//        sentences = test.kb.sentences();
//        Sentence p31 = new Symbol("P3,1");
//        sentences.add(new Negation(p31)); // add !Beta
//        clauses = test.convert(sentences);
//        if (!test.satisfiable(clauses)){  // we expect it to be satisfiable --> (can't entail Beta)
//            failures.add("Test failed: cantInferP31");
//        }
//
////        givenNotP22InferP31
//        // generate new sentence in KB saying that P22=False
//        Sentence notP22 = new Negation(new Symbol("P2,2"));
//        WumpusWorldKB kb = new WumpusWorldKB();
//        kb.add(notP22);
//        test = new DPLL(kb);
//
//        // Now, we are able to use this added information to infer that P31 is true
//        sentences = test.kb.sentences();
//        p31 = new Symbol("P3,1");
//        sentences.add(new Negation(p31)); // add !Beta
//        clauses = test.convert(sentences);
//        if (test.satisfiable(clauses)){  // we expect it to be !satisfiable --> (Yes entail Beta)
//            failures.add("Test failed: givenNotP22InferP31");
//        }



        /************************************************
                     MODUS PONENS TESTS
         ************************************************/

        // inferQTrue
//        test = new DPLL(new ModusPonensKB());
//        Sentence q = new Symbol("Q");
//        sentences = test.kb.sentences();
//        sentences.add(new Negation(q)); // add !Beta
//        clauses = test.convert(sentences);
//        if (test.satisfiable(clauses)){ // we expect it to be NOT unsatisfiable
//            failures.add("Test failed: inferQTrue");
//        }


        printFailures(failures);
    }

    private static void printFailures(List<String> failures) {
        System.out.println();
        if (failures.isEmpty()){
            System.out.println("No failures!");
        } else {
            for (String s : failures){
                System.out.println(s);
            }
        }
    }

}
