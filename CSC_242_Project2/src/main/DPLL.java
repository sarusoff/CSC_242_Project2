package main;

import pl.cnf.CNFConverter;
import pl.cnf.Clause;
import pl.core.KB;
import pl.core.Sentence;
import pl.core.Symbol;

import java.util.Collection;
import java.util.Set;

/**
 * Created by danielsaltz on 3/2/17.
 */
public class DPLL {

    private KB kb;

    public DPLL(KB kb){
        this.kb = kb;
    }

    public boolean satisfiable(Sentence alpha) {
        Collection<Symbol> symbols = kb.symbols();
        Set<Clause> clauses = CNFConverter.convert(alpha);
        MyModel model = new MyModel();
        return dpll(clauses,symbols,model);
    }

    private boolean dpll(Set<Clause> clauses, Collection<Symbol> symbols, MyModel model) {

        if (allClausesTrueInModel(clauses,model)) return true;
        if (someClausesTrueInModel(clauses,model)) return false;



        return true;
    }

    private boolean someClausesTrueInModel(Set<Clause> clauses, MyModel model) {

        return false;
    }

    private boolean allClausesTrueInModel(Set<Clause> clauses, MyModel model) {

        return false;
    }


}
