package main;

import pl.core.KB;
import pl.core.Model;
import pl.core.Sentence;
import pl.core.Symbol;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by danielsaltz on 3/1/17.
 */
public class MyModel implements Model{

    protected HashMap<String,Boolean> map;

    public MyModel(){
       map = new HashMap<String,Boolean>();
    }

    // added for DPLL algorithm
    public MyModel assign(Symbol sym, boolean value){
        map.put(sym.toString(),value);
        return this;
    }

    @Override
    public void set(Symbol sym, boolean value) {
        map.put(sym.toString(),value);
    }

    // this is the method that ultimately compares every Symbol in a sentence to the symbol of the query
    // get(sym) should return true if the model has sym being true, and false if the model has sym being false
    @Override
    public boolean get(Symbol sym) {
//        if (map.containsKey(sym.toString())) {
            return map.get(sym.toString());
//        }
//        return true; // if the map doesn't contain that key, return true so it doesn't rule out a possibility
    }

    public boolean containsSymbol(Symbol sym){
        return map.containsKey(sym.toString());
    }


    // if the any sentence does not satisfy the KB, then the model fails
    public boolean satisfies(KB kb) {
        for (Sentence s : kb.sentences()){
            if (!s.isSatisfiedBy(this)){
               return false;
            }
        }
        return true;
    }

    @Override
    public boolean satisfies(Sentence sentence) {
        return sentence.isSatisfiedBy(this);
    }

    @Override
    public void dump() {
        if (!map.isEmpty()){
            for (Map.Entry<String,Boolean> entry : map.entrySet()){
                System.out.print(entry.getKey() + ": " + entry.getValue() + "  ");
            }
        }
    }

}
