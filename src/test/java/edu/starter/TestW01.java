package edu.starter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class TestW01 extends StarterTest {
    // attack against a 30-HP test opponent leaves 20 HP
    @Test void W01_ATK_01() {
        Combatant player = newPlayer(), foe = enemy(30);
        assertTrue(player.attack(foe));
        assertEquals(20, foe.getHP());
        assertEquals(100, player.getHP());
    }

    // defense against a 10-damage counterattack causes 5 HP loss
    @Test void W01_DEF_01() {
        Combatant player = newPlayer(), foe = enemy(30);
        assertTrue(player.defend());
        assertTrue(foe.attack(player));
        assertEquals(95, player.getHP());
    }

    // special at resource 5 leaves 2 and removes 15 enemy HP
    @Test void W01_SPC_01() {
        Combatant player = newPlayer(), foe = enemy(30);
        assertTrue(player.special(foe));
        assertEquals(15, foe.getHP());
        assertEquals(2, player.getResource());
    }

    // resource at 9 recovers to 10, not 12
    @Test void W01_RES_01() {
        Combatant player = newPlayer();
        setResource(player, 9);
        assertTrue(player.recover());
        assertEquals(10, player.getResource());
    }

    // invalid action produces no enemy counterattack
    @Test void W01_INV_01() {
        Combatant player = newPlayer(), foe = enemy(10);
        boolean won = Encounter.fight(keys("abc\n9\n0\n\n", ATTACK), player, foe);
        assertTrue(won);
        assertEquals(100, player.getHP());
    }

    
}
