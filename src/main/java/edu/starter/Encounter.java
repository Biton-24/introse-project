package edu.starter;
import java.util.Scanner;
import java.util.function.Function;

public class Encounter {
    public static boolean fight(Scanner input, Combatant... players) {
        // first player in players is the main player (all others are enemies/bots)
        int turn = 0;
        boolean alldead;
        while (true) {
            // check if game is over (main player is dead or if all others are dead)
            if (!players[0].isAlive()) {
                lost();
                return false;
            }
            alldead = true;
            for (Combatant player : players) {
                if (player.isAlive() && player != players[0]) alldead = false;
            }
            if (alldead) {
                players[0].useDefence();
                return true;
            }

            if (!players[turn].isAlive()) {
                turn = (turn + 1) % players.length;
                continue;
            }

            // main turn logic
            if (turn == 0) getMove(players, turn, input);
            else autoMove(turn, players);

            turn = (turn + 1) % players.length;
        }
    }


    static void autoMove(int turn, Combatant... players) {
        players[turn].attack(players[0]);
    }


    static void getMove(Combatant[] players, int turn, Scanner input) {
        while (true) {
            Combatant currPlayer = players[turn];

            // print status
            System.out.print("\nStatus:\n");
            for (Combatant player : players) {
                System.out.printf("%s - HP: %d, Resources: %d, attack power: %d\n", player.name(), player.getHP(), player.getResource(), player.getAttackPower());
            }
            System.out.println();

            // choose target
            // if (choices.length > 0) target = players[choices[0] - 1];
            String message = String.format("Current player is %s, players:", currPlayer.name());
            Combatant target = pickOption(input, message, "target", false, players);

            // self action
            if (target == currPlayer) {
                message = String.format("%s is targeting themselves, actions:", currPlayer.name());
                Combatant.SelfAction action = pickOption(input, message, "action", true, currPlayer.selfActions);
                if (action == null) continue;
                if (action.action().apply(currPlayer)) break;
            }

            // enemy action
            else {
                message = String.format("%s is targeting %s, actions:", currPlayer.name(),target.name());
                Combatant.EnemyAction action = pickOption(input, message, "action", true, currPlayer.enemyActions);
                if (action == null) continue;
                if (action.action().apply(currPlayer, target)) break;
            }
        }
    }


    private static record RewardAction(String name, Function<Combatant, Boolean> action) implements Named {};
    private static final RewardAction[] rewards = {
        new RewardAction("Recover", Combatant::recoverHP),
        new RewardAction("Power up", Combatant::powerUp)
    };

    private static void getReward(Scanner input, Combatant player) {
        while (true) {
            if (pickOption(input, "Rewards: ", "Reward", false, rewards).action.apply(player)) break;
        }
    }


    public static <T extends Named> T pickOption(Scanner input, String message, String optionName, boolean back, T... options) {
        int optionsNum = back ? options.length + 1 : options.length;
        System.out.println(message);
        while (true) {
            for (int i = 0; i < options.length; i++) System.out.printf("%d: %s\n", i + 1, options[i].name());
            if (back) System.out.printf("%d: Back\n", optionsNum);
            System.out.printf("Enter %s: ", optionName);
            int choice = ConsoleChoice.read(input, 1, optionsNum);
            if (choice == -1) {
                System.out.printf("Enter number between 1 and %d, try again\n", optionsNum);
                continue;
            }
            // Check if the user selected "Back"
            if (back && choice == optionsNum) return null;

            return options[choice - 1];
        }
    }


    public static void won() {
        System.out.println("You have won the game!");
    }


    public static void encounterWon(Scanner input, Combatant player) {
        System.out.println("\nYou have won the encounter!\n");
        getReward(input, player);
    }


    public static void lost() {
        System.out.println("You have lost the game :(");
    }
}
