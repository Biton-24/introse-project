package edu.starter;
import java.lang.reflect.Field;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class StarterTest {
    static final String ATTACK  = "2\n1\n";
    static final String SPECIAL = "2\n2\n";
    static final String DEFEND  = "1\n1\n";
    static final String RECOVER_RESOURCE = "1\n2\n";
    static final String BACK_FROM_ACTIONS = "2\n3\n";
    static final String RECOVERY = "1\n";
    static final String POWER    = "2\n";

    static Scanner keys(String... parts) {
        return new Scanner(String.join("", parts));
    }

    static Combatant newPlayer() { return new Combatant("Player", 100); }
    static Combatant enemy(int hp) { return new Combatant("Enemy", hp); }

    // set private fields
    static void setField(Object target, String field, Object value) {
        try {
            Field f = target.getClass().getDeclaredField(field);
            f.setAccessible(true);
            f.set(target, value);
        } catch (ReflectiveOperationException e) {
            throw new AssertionError("Test fixture needs a private field '" + field + "' in "
                    + target.getClass().getSimpleName() + " - rename it in TestBase.java", e);
        }
    }

    static void setHP(Combatant c, int hp)       { setField(c, "HP", hp); }
    static void setResource(Combatant c, int r)  { setField(c, "resources", r); }


    @Test void clampingIsCorrect() { assertEquals(10, Checks.within(12, 0, 10)); }
    @Test void missingNumberIsRejected() { assertEquals(-1, ConsoleChoice.read(new java.util.Scanner("abc\n"), 1, 4)); }
}
