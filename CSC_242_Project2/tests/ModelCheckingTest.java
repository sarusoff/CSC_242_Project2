import main.ModelChecking;
import org.junit.Assert;
import org.junit.Test;
import pl.core.Negation;
import pl.core.Sentence;
import pl.core.Symbol;
import pl.examples.HornClausesKB;
import pl.examples.ModusPonensKB;
import pl.examples.WumpusWorldKB;


/**
 * Created by danielsaltz on 3/1/17.
 */
public class ModelCheckingTest {

    /************************************************
                WUMPUS WORLD TESTS
     ************************************************/

    @Test
    public void inferThatNotP11True() {
        ModelChecking test = new ModelChecking(new WumpusWorldKB());
        Sentence p11 = new Negation(new Symbol("P1,1"));
        Assert.assertTrue(test.entails(p11));
    }

    @Test
    public void inferThatP12False() {
        ModelChecking test = new ModelChecking(new WumpusWorldKB());
        Sentence p12 = new Symbol("P1,2");
        Assert.assertFalse(test.entails(p12));
    }

    @Test
    public void inferThatNotP12True() {
        ModelChecking test = new ModelChecking(new WumpusWorldKB());
        Sentence notP12 = new Negation(new Symbol("P1,2"));
        Assert.assertTrue(test.entails(notP12));
    }

    @Test
    public void cantInferP31() {

        // Given just our current KB, we don't know if the pit is in 2,2 or 3,1
        ModelChecking test = new ModelChecking(new WumpusWorldKB());

        // we should not be able to infer the P31=True
        Sentence p31 = new Symbol("P3,1");
        Assert.assertFalse(test.entails(p31));
    }

    @Test
    public void givenNotP22InferP31() {

        // generate new sentence in KB saying that P22=False
        Sentence notP22 = new Negation(new Symbol("P2,2"));
        WumpusWorldKB kb = new WumpusWorldKB();
        kb.add(notP22);
        ModelChecking test = new ModelChecking(kb);

        // Now, we are able to use this added information to infer that P31 is true
        Sentence p31 = new Symbol("P3,1");
        Assert.assertTrue(test.entails(p31));
    }



    /************************************************
                    MODUS PONENS TESTS
     ************************************************/

    @Test
    public void inferQTrue() {
        ModelChecking test = new ModelChecking(new ModusPonensKB());
        Sentence q = new Symbol("Q");
        Assert.assertTrue(test.entails(q));
    }

    @Test
    public void inferNotQFalse() {
        ModelChecking test = new ModelChecking(new ModusPonensKB());
        Sentence notQ = new Negation(new Symbol("Q"));
        Assert.assertFalse(test.entails(notQ));
    }


    /************************************************
                 HORN CLAUSES TESTS
     ************************************************/

    // tests question a
    @Test
    public void inferMythicalFalse(){
        ModelChecking test = new ModelChecking(new HornClausesKB());
        Sentence myth = new Symbol("myth");
        Assert.assertFalse(test.entails(myth));
    }

    // tests question b
    @Test
    public void inferMagicalTrue(){
        ModelChecking test = new ModelChecking(new HornClausesKB());
        Sentence mag = new Symbol("mag");
        Assert.assertTrue(test.entails(mag));
    }

    // tests question c
    @Test
    public void inferHornedTrue(){
        ModelChecking test = new ModelChecking(new HornClausesKB());
        Sentence h = new Symbol("h");
        Assert.assertTrue(test.entails(h));
    }

}
