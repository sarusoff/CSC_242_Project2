package pl.examples;

import pl.core.*;

public class AdvancedTruthTellersKB extends KB {

    public AdvancedTruthTellersKB(){
        super();
        
    Symbol amy = intern("amy");.
    Symbol bob = intern("bob");
    Symbol cal = intern("cal");
    Symbol dee = intern("dee");
    Symbol eli = intern("eli");
    Symbol fay = intern("fay");
    Symbol gil = intern("gil");
    Symbol hal = intern("hal");
    Symbol ida = intern("ida");
    Symbol jay = intern("jay");
    Symbol kay = intern("kay");
    Symbol lee = intern("lee");
    add new(Implication(amy, new Conjunction(hal,ida)));
    add new(Implication(bob, new Conjunction(amy,lee)));
    add new(Implication(cal, new Conjunction(bob,gil)));
    add new(Implication(dee, new Conjunction(eli,lee)));
    add new(Implication(eli, new Conjunction(cal,hal))); 
    add new(Implication(fay, new Conjunction(dee,ida)));
    add new(Impication(gil, new Negation(new Conjunction(eli,jay))));
    add new(Impication(hal, new Negation(new Conjunction(fay,kay))));
    add new(Impication(ida, new Negation(new Conjunction(gil,kay))));
    add new(Impication(jay, new Negation(new Conjunction(amy,cal))));
    add new(Impication(kay, new Negation(new Conjunction(dee,fay))));
    add new(Impication(lee, new Negation(new Conjunction(bob,jay))));
  }
  
    public static void main(String[] argv) {
		      new AdvancedTruthTellersKB().dump();
	  }
}

       
