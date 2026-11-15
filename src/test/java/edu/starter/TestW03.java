package edu.starter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import java.util.Scanner;

public class TestW03 extends StarterTest {
    // setup: Player at 90 HP selects Recovery
    // result: HP becomes 100
    @Test void W03_RWD_01() {
        Combatant player = newPlayer();
        setHP(player, 90);
        Scanner in = keys(RECOVERY);
        Encounter.encounterWon(in, player);
        assertEquals(100, player.getHP());
    }

    // setup: Player Attack 10 selects Power
    // result: Next basic Attack deals 12
    @Test void W03_RWD_02() {
        Combatant player = newPlayer();
        Scanner in = keys(POWER, ATTACK.repeat(2));
        Encounter.encounterWon(in, player);
        assertEquals(12, player.getAttackPower());
        assertTrue(Encounter.fight(in, player, enemy(12)));
        assertEquals(100, player.getHP());
    }

    // setup: Enter unknown reward, then choose Power
    // result: Unknown entry changes nothing
    @Test void W03_RWD_04() {
        Combatant player = newPlayer();
        setHP(player, 90);
        Scanner in = keys("abc\n9\n0\n\n", POWER);
        Encounter.encounterWon(in, player);
        assertEquals(90, player.getHP());
        assertEquals(12, player.getAttackPower());
    }
}
