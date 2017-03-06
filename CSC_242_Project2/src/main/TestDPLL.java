package main;

import pl.core.Negation;
import pl.core.Sentence;
import pl.core.Symbol;
import pl.examples.*;

import java.util.ArrayList;
import java.util.List;

public class TestDPLL {

    public static void main(String... args){

        DPLL test = new DPLL();
        List<String> failures = new ArrayList<String>();

        failures = testWumpusWorld(test,failures);
        failures = testModusPonens(test,failures);
        failures = testHornClauses(test,failures);
//        failures = testTruthTellers(test,failures);
//        failures = testAdvancedTruthTellers(test,failures);

        printFailures(failures);
    }
//    private static List<String> testTruthTellers(ModelChecking test, List<String> failures){
//        TruthTellersKB truthTellersKB = new TruthTellersKB();
//
//        //part A
//        //infer Amy is false
//       Sentence amy = new Symbol("amy");
//       if (test.entails(truthTellersKB,amy)){
//           failures.add("Test failed: interAmyFalse");
//       }
//       //Bob is false
//        truthTellersKB= new TruthTellersKB();
//        Sentence bob = new Symbol("bob");
//        if(test.entails(truthTellersKB,bob)){
//           failures.add("Test failed: interBobFalse");
//       }
//   //----------------------------------------------------------
//     //Part B
//
//     //infer Amy is True
//        truthTellersKB = new TruthTellersKB();
//      Sentence amy2 = new Symbol("amy2");
//      if(!test.entails(truthTellersKB,amy2)){
//           failures.add("Test failed: interAmy2True");
//       }
//       //infer Amy is False
//        truthTellersKB = new TruthTellersKB();
//         amy2= new Symbol("amy2");
//      if(test.entails(truthTellersKB,amy2)){
//           failures.add("Test failed: interAmy2False");
//       }
//       return failures;
//    }
//
//    private static List<String> testAdvancedTruthTellers(ModelChecking test, List<String> failures){
//        AdvancedTruthTellersKB Advtruthtellers = new AdvancedTruthTellersKB();
//
//        //infer Amy False
//        Sentence amy = new Symbol("amy");
//        if (test.entails(Advtruthtellers,amy)){
//            failures.add("Test failed: inferAmyFalse");
//        }
//           //infer not Gil
//           Advtruthtellers = new AdvancedTruthTellersKB();
//             Sentence gil = new Symbol("gil");
//        if (test.entails(Advtruthtellers,amy)){
//            failures.add("Test failed: inferGilFalse");
//        }
//          //infer not Ida
//          Advtruthtellers = new AdvancedTruthTellersKB();
//        Sentence ida = new Symbol("ida");
//        if (test.entails(Advtruthtellers,ida)){
//            failures.add("Test failed: inferIdaFalse");
//        }
//        return failures;
//    }

    private static List<String> testHornClauses(DPLL test, List<String> failures) {
        // tests question a
        //inferMythicalFalse
        HornClausesKB hornClausesKB = new HornClausesKB();
        Sentence myth = new Symbol("myth");
        if (!test.satisfiable(hornClausesKB,myth)){
            failures.add("Test failed: nferMythicalFalse");
        }

        // tests question b
        //inferMagicalTrue
        hornClausesKB = new HornClausesKB();
        Sentence mag = new Symbol("mag");
        if (test.satisfiable(hornClausesKB,mag)){
            failures.add("Test failed: inferMagicalTrue");
        }

        // tests question c
        //inferHornedTrue
        hornClausesKB = new HornClausesKB();
        Sentence h = new Symbol("h");
        if (test.satisfiable(hornClausesKB,h)){
            failures.add("Test failed: inferHornedTrue");
        }
        return failures;
    }

    private static List<String> testModusPonens(DPLL test, List<String> failures) {

        // inferQ
        ModusPonensKB modusPonensKB = new ModusPonensKB();
        Sentence q = new Symbol("Q");
        if (test.satisfiable(modusPonensKB,q)){ // we expect it to be NOT unsatisfiable
            failures.add("Test failed: inferQ");
        }

        // inferNotQ
        modusPonensKB = new ModusPonensKB();
        Sentence notQ = new Negation(new Symbol("Q"));
        if (!test.satisfiable(modusPonensKB,notQ)){ // we expect it to be YES satisfiable
            failures.add("Test failed: inferNotQ");
        }
        return failures;
    }

    private static List<String> testWumpusWorld(DPLL test, List<String> failures) {
 //        canInferNotP11
        WumpusWorldKB wumpusWorldKB = new WumpusWorldKB();
        Sentence notP11 = new Negation(new Symbol("P1,1"));
        if (test.satisfiable(wumpusWorldKB,notP11)){ // we expect it to be NOT satisfiable
            failures.add("Test failed: canInferNotP11");
        }

//       canInferNotP12
        wumpusWorldKB = new WumpusWorldKB();
        Sentence p12 = new Negation(new Symbol("P1,2"));
        if (test.satisfiable(wumpusWorldKB,p12)){ // we expect it to be NOT satisfiable --> (we know p12=False)
            failures.add("Test failed: canInferNotP12");
        }

//        cantInferP12
        wumpusWorldKB = new WumpusWorldKB();
        Sentence notP12 = new Symbol("P1,2");
        if (!test.satisfiable(wumpusWorldKB,notP12)){ // NOT satisfiable, we can't infer it
            failures.add("Test failed: cantInferP12");
        }

//        cantInferP31
        // Given just our current KB, we don't know if the pit is in 2,2 or 3,1
        wumpusWorldKB = new WumpusWorldKB();

        // we should not be able to infer the P31=True
        Sentence p31 = new Symbol("P3,1");
        if (!test.satisfiable(wumpusWorldKB,p31)){  // we expect it to be YES satisfiable --> (can't entail Beta)
            failures.add("Test failed: cantInferP31");
        }

//        givenNotP22InferP31
        // generate new sentence in KB saying that P22=False
        Sentence notP22 = new Negation(new Symbol("P2,2"));
        wumpusWorldKB = new WumpusWorldKB();
        wumpusWorldKB.add(notP22);

        // Now, we are able to use this added information to infer that P31 is true
        p31 = new Symbol("P3,1");
        if (test.satisfiable(wumpusWorldKB,p31)){  // we expect it to be NOT satisfiable --> (Yes infer that P3,1=TRUE)
            failures.add("Test failed: givenNotP22InferP31");
        }
        return failures;
    }

    private static void printFailures(List<String> failures) {
        System.out.println();
        if (failures.isEmpty()){
            System.out.println("All tests passed!");
        } else {
            for (String s : failures){
                System.out.println(s);
            }
        }
    }

}
