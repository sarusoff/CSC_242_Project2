package main;

import pl.cnf.CNFConverter;
import pl.cnf.Clause;
import pl.cnf.Literal;
import pl.core.KB;
import pl.core.Sentence;
import pl.core.Symbol;

import java.util.*;

/**
 * Created by danielsaltz on 3/2/17.
 */
public class DPLL {

    protected KB kb;

    public DPLL(KB kb){
        this.kb = kb;
    }

    /**
     * We need to prove that a AND not-b is unsatisfiable using a proof by contradiction
     * This means there is no model where a is true and b is false
     */

    public boolean satisfiable(Set<Clause> clauses) {
        Stack<Symbol> symbols = new Stack<Symbol>();
        symbols.addAll(kb.symbols());
        return dpll(clauses, symbols, new MyModel());
    }

// algorithm from figure 7.17
    private boolean dpll(Set<Clause> clauses, Stack<Symbol> symbols, MyModel model) {

        if (allClausesTrueInModel(clauses,model)) {
            return true;
        }
        if (atLeastOneClauseInModelIsFalse(clauses,model)){
            return false;
        }

        // Literally the purest of variables
        Literal pure = findPureSymbol(clauses,model,symbols);
        if (pure != null) {
            symbols = removeLitFromSymbols(pure,symbols);
            return dpll(clauses,symbols,model.assign(pure.getContent(),getBool(pure)));
        }

        SymbolAssignment symAssign = findUnitClause(clauses,model,symbols);
        if (symAssign != null){
            symbols = removeLitFromSymbols(symAssign,symbols);
            return dpll(clauses,symbols,model.assign(symAssign.getSymbol(),symAssign.getValue()));
        }

        Symbol p  = symbols.pop();
        return dpll(clauses,symbols,model.assign(p,true))
                || dpll(clauses,symbols,model.assign(p,false));

    }

    public Stack<Symbol> removeLitFromSymbols(SymbolAssignment symAssign, Stack<Symbol> symbols){
        Stack<Symbol> temp = new Stack<Symbol>();
        for (Symbol sym : symbols){
            if (!sym.toString().equals(symAssign.getSymbol().toString())){
                temp.add(sym);
            }
        }
        return temp;
    }

    // in dpll, a unit clause is defined as a clause where every literal is assigned false by the model,
    // except for one literal which is unassigned by the model. The unassigned literal
    // must be true for the clause (and therefore the sentence) to be true, so we assign it to be true
    private SymbolAssignment findUnitClause(Set<Clause> clauses, MyModel model, Stack<Symbol> symbols) {
        for (Clause clause : clauses){
            int unassigned = 0;
            int trueInModel = 0;
            SymbolAssignment symbolAssignment = null;
            for (Literal l : clause){
                if (model.containsSymbol(l.getContent())){ // if assigned in model
                    if (l.isSatisfiedBy(model)) {// if the literal is true in the model
                        trueInModel++;
                    }
                } else { // unassigned in model
                    symbolAssignment = new SymbolAssignment(l.getContent(),getBool(l));
                    unassigned++;
                }
                if (unassigned > 1 || trueInModel > 0){ // not unit clause, so try the next clause
                    break;
                }
            }
            if (unassigned == 1 && trueInModel == 0){
                return symbolAssignment;
            }
        }
        return null;
    }

    private boolean getBool(Literal lit) {
        return (lit.getPolarity() == Literal.Polarity.POSITIVE) ? true : false;
    }

    private Stack<Symbol> removeLitFromSymbols(Literal lit, Stack<Symbol> symbols) {
        Stack<Symbol> temp = new Stack<Symbol>();
        for (Symbol sym : symbols){
            if (!sym.toString().equals(lit.getContent().toString())){
                temp.add(sym);
            }
        }
        return temp;
    }


    private Literal findPureSymbol(Set<Clause> clauses, MyModel model, Stack<Symbol> symbols) {

        // add all literals of all clauses
        // made it a hashmap so that we could link the map key to the Literal object
        HashMap<String,Literal> map = new HashMap<String,Literal>();
        for (Clause clause : clauses){
            for (Literal l : clause){
                if (l.getPolarity() == Literal.Polarity.NEGATIVE){
                    map.put("n"+l.getContent().toString(),l);
                } else {
                    map.put(l.getContent().toString(),l);
                }
            }
        }

        // if there is any symbol in the clauses that only has a positive or negative polarity, and not both, return that symbol
        for (Clause clause : clauses){
            for (Literal l : clause) {
                if (map.containsKey(l.getContent().toString()) && !map.containsKey("n" + l.getContent().toString())) {
                    return map.get(l.getContent().toString());
                }
                else if (!map.containsKey(l.getContent().toString()) && map.containsKey("n" + l.getContent().toString())){
                    return map.get("n" + l.getContent().toString());
                }
            }
        }
        return null;
    }

    private boolean allSymbolsInClauseAreInModel(Clause clause, MyModel model){
        for (Literal l : clause){
            if (!model.containsSymbol(l.getContent())){
                return false;
            }
        }
        return true;
    }


    private boolean atLeastOneClauseInModelIsFalse(Set<Clause> clauses, MyModel model) {
        for (Clause clause : clauses){
            if (!allSymbolsInClauseAreInModel(clause,model)){
                continue;     // only test the clause if each symbol in that clause is assigned in the model already
            }
            if (!atLeastOneLiteralIsTrue(clause,model)){
                return true;    // not a single literal in clause is true, so the clause is false
            }
        }
        return false;
    }

    private boolean atLeastOneLiteralIsTrue(Clause clause, MyModel model){
        for (Literal l : clause){
            if (l.isSatisfiedBy(model)){
                return true;
            }
        }
        return false;
    }

    private boolean allSymbolsInClausesAssignedInModel(Set<Clause> clauses, MyModel model){
        for (Clause clause : clauses){
            for (Literal l : clause){
                Symbol s = l.getContent();
                if (!model.containsSymbol(s)){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean allClausesTrueInModel(Set<Clause> clauses, MyModel model) {

        // first, check to make sure that any symbols in the clauses have been assigned in the model
        // if a symbol in a clause hasn't been assigned in the model yet, then there's no way all clauses are true in the model
        if (!allSymbolsInClausesAssignedInModel(clauses,model)) {
            return false;
        }

        // then, check to see if each symbol is true in the model
        for (Clause clause : clauses){
            if (!clause.isSatisfiedBy(model)){
                return false;
            }
        }
        return true;
    }

    // takes a collection of sentences and returns a set of clauses from those sentences
    public Set<Clause> convert(Collection<Sentence> sentences) {
        Set<Clause> clauses = new HashSet<Clause>();
        for (Sentence s : sentences) {
            clauses.addAll(CNFConverter.convert(s));
        }
        return clauses;
    }

}
