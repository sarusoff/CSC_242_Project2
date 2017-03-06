package main;

import pl.core.Negation;
import pl.core.Sentence;
import pl.core.Symbol;
import pl.examples.HornClausesKB;
import pl.examples.ModusPonensKB;
import pl.examples.WumpusWorldKB;
import pl.examples.TruthTellersKB;
import pl.examples.AdvancedTruthTellersKB;

import java.util.ArrayList;
import java.util.List;

public class TestModelChecking {

    public static void main(String...args){

        ModelChecking test = new ModelChecking();
        List<String> failures = new ArrayList<String>();

        failures = testWumpusWorld(test,failures);
        failures = testModusPonens(test,failures);
        failures = testHornClauses(test,failures);
        failures = testTruthTellers1(test,failures);
        failures = testTruthTellers2(test,failures);
        printFailures(failures);
    }
    
    
    private static List<String> testTruthTeller1(ModelChecking test, List<String> failures){
        TruthTellersKB truthteller = new TruthTellersKB();
        //part A
        
        //infer Amy is false
       Sentence amy = new Symbol("amy");
       if(test.entails(truthteller,amy)){
           failures.add("Test failed: interAmyFalse");
       }
       //Bob is false
       truthteller= new TruthTellersKB();
        Sentence bob = new Symobl("bob");
        if(test.entails(truthteller,bob)){
           failures.add("Test failed: interBobFalse");
       }
   //----------------------------------------------------------     
     //Part B
     
     //infer Amy is True
       truthteller = new TruthTellersKB();
      Sentence amy2 = new Symbol("amy2");
      if(!test.entails(truthteller,amy2)){
           failures.add("Test failed: interAmy2True");
       }
       //infer Amy is False
        truthteller = new TruthTellersKB();
         amy2= new Symbol("amy2");                        
      if(test.entails(truthteller,amy2)){
           failures.add("Test failed: interAmy2False");
       }                          
           }  
                                 
    private static List<String> testTruthTellers2(ModelChecking test, List<String> failures){
        //infer Amy False
        AdvancedTruthTellersKB Advtruthtellers = new AdvancedTruthTellersKB();
        Sentence amy = new Symbol("amy");
        if(test.entails(Advtruthtellers,amy){
            failures.add("Test failed: inferAmyFalse"); 
    }
           //infer not Gil
           Advtruthtellers = new AdvancedTruthTellersKB();
             Sentence gil = new Symbol("gil");
        if(test.entails(Advtruthtellers,amy){
            failures.add("Test failed: inferGilFalse"); 
    }
          //infer not Ida
          Advtruthtellers = new AdvancedTruthTellersKB();
        Sentence ida = new Symbol("ida");
        if(test.entails(Advtruthtellers,ida){
            failures.add("Test failed: inferIdaFalse"); 
    }
           }
                          
    
    private static List<String> testHornClauses(ModelChecking test, List<String> failures) {
        // tests question a
        //inferMythicalFalse
        HornClausesKB hornClausesKB = new HornClausesKB();
        Sentence myth = new Symbol("myth");
        if (test.entails(hornClausesKB,myth)){
            failures.add("Test failed: inferMythicalFalse");
        }

        // tests question b
        //inferMagicalTrue
        hornClausesKB = new HornClausesKB();
        Sentence mag = new Symbol("mag");
        if (!test.entails(hornClausesKB,mag)){
            failures.add("Test failed: inferMagicalTrue");
        }

        // tests question c
        //inferHornedTrue
        hornClausesKB = new HornClausesKB();
        Sentence h = new Symbol("h");
        if (!test.entails(hornClausesKB,h)){
            failures.add("Test failed: inferHornedTrue");
        }
        return failures;
    }

    private static List<String> testModusPonens(ModelChecking test, List<String> failures) {
//        inferQTrue
        ModusPonensKB modusPonensKB = new ModusPonensKB();
        Sentence q = new Symbol("Q");
        if (!test.entails(modusPonensKB,q)){
            failures.add("Test failed: inferQTrue");
        }

//          inferNotQFalse
        modusPonensKB = new ModusPonensKB();
        Sentence notQ = new Negation(new Symbol("Q"));
        if (test.entails(modusPonensKB,notQ)){
            failures.add("Test failed: inferNotQFalse");
        }
        return failures;
    }

    private static List<String> testWumpusWorld(ModelChecking test, List<String> failures) {
//        inferThatNotP11True
        WumpusWorldKB wumpusWorldKB = new WumpusWorldKB();
        Sentence p11 = new Negation(new Symbol("P1,1"));
        if (!test.entails(wumpusWorldKB,p11)){
            failures.add("Test failed: inferThatNotP11True");
        }

//       inferThatP12False
        wumpusWorldKB = new WumpusWorldKB();
        Sentence p12 = new Symbol("P1,2");
        if (test.entails(wumpusWorldKB,p12)){
            failures.add("Test failed: inferThatP12False");
        }

//        inferThatNotP12True
        wumpusWorldKB = new WumpusWorldKB();
        Sentence notP12 = new Negation(new Symbol("P1,2"));
        if (!test.entails(wumpusWorldKB,notP12)){
            failures.add("Test failed: inferThatNotP12True");
        }

//        cantInferP31
        // Given just our current KB, we don't know if the pit is in 2,2 or 3,1
        wumpusWorldKB = new WumpusWorldKB();

        // we should not be able to infer the P31=True
        Sentence p31 = new Symbol("P3,1");
        if (test.entails(wumpusWorldKB,p31)){
            failures.add("Test failed: cantInferP31");
        }

//        givenNotP22InferP31
        // generate new sentence in KB saying that P22=False
        Sentence notP22 = new Negation(new Symbol("P2,2"));
        wumpusWorldKB = new WumpusWorldKB();
        wumpusWorldKB.add(notP22);

        // Now, we are able to use this added information to infer that P31 is true
        p31 = new Symbol("P3,1");
        if (!test.entails(wumpusWorldKB,p31)){
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
