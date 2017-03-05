package main;

import pl.core.Symbol;


/**
 * Created by danielsaltz on 3/5/17.
 */
public class SymbolAssignment {

    private boolean value;
    private Symbol symbol;

    public SymbolAssignment(Symbol symbol, boolean value){
        this.symbol = symbol;
        this.value = value;
    }

    public Symbol getSymbol(){
        return symbol;
    }

    public boolean getValue(){
        return value;
    }

}
