package PortasLogicas;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TabelaVerdadeTest {

    @Test
    public void testeANDSimples() {
        Map<Character, Boolean> valores = new HashMap<>();
        valores.put('A', true);
        valores.put('B', true);

        boolean resultado = TabelaVerdade.avaliarExpressao("A.B", valores);

        assertEquals(true, resultado);
    }

    @Test
    public void testeORComNOT() {
        Map<Character, Boolean> valores = new HashMap<>();
        valores.put('A', false);
        valores.put('B', false);

        boolean resultado = TabelaVerdade.avaliarExpressao("A + B'", valores);

        assertEquals(true, resultado);
    }

    @Test
    public void testeComParenteses() {
        Map<Character, Boolean> valores = new HashMap<>();
        valores.put('A', true);
        valores.put('B', false);
        valores.put('C', true);

        boolean resultado = TabelaVerdade.avaliarExpressao("(A.B) + C", valores);
        assertEquals(true, resultado);
    }

    @Test
    public void testeTudoFalso() {
        Map<Character, Boolean> valores = new HashMap<>();
        valores.put('A', false);
        valores.put('B', false);

        boolean resultado = TabelaVerdade.avaliarExpressao("A.B", valores);

        assertEquals(false, resultado);
    }
}