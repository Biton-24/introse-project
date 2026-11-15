package edu.starter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import java.util.Scanner;

public class TestW02 extends StarterTest {
    // setup: Make Enemy A = 30 HP and Enemy B = 45 HP;
    // result:  damage A for 10
    @Test void W02_OBJ_01() {
        Combatant a = new Combatant("A", 30), b = new Combatant("B", 45);
        assertEquals(30, a.getHP());
        assertEquals(45, b.getHP());
        assertTrue(newPlayer().attack(a));
        assertEquals(20, a.getHP());
        assertEquals(45, b.getHP());
    }

    // setup: Finish Encounter 1 at HP 80, resource 2
    // result: Encounter 2 begins HP 80, resource 2
    @Test void W02_ENC_01() {
        Combatant player = newPlayer();
        Scanner in = keys(SPECIAL, ATTACK.repeat(2), POWER);
        Encounter.fight(in, player, enemy(30));
        assertEquals(80, player.getHP());
        assertEquals(2, player.getResource());
        Encounter.encounterWon(in, player);
        assertEquals(80, player.getHP());
        assertEquals(2, player.getResource());
    }

    // setup: Enemy A defeated at 0 HP
    // result: Enemy B still starts at 45 HP
    @Test void W02_ENC_02() {
        Combatant player = newPlayer(), A = new Combatant("A", 30), B = new Combatant("B", 45);
        Scanner in = keys(ATTACK.repeat(3), RECOVERY);
        Encounter.fight(in, player, A);
        Encounter.encounterWon(in, player);
        assertEquals(45, B.getHP());
    }


    // setup: Use Special with only 2 resource
    // result: Action rejected; no counterattack
    @Test void W01_REG_01() { // called W01_INV_01 in the homework
        Combatant player = newPlayer();
        setResource(player, 2);
        Scanner in = keys(SPECIAL, ATTACK.repeat(3));
        Encounter.fight(in, player, enemy(20));
        assertEquals(90, player.getHP());
    }
}
