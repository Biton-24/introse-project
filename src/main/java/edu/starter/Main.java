package edu.starter;
import java.util.Scanner;

public final class Main {
    public static void main(String[] args){
        // initialize players/enemies
        Combatant player       = new Combatant("Player"      , 100);
        Combatant evil_enemy   = new Combatant("Evil Enemy"  , 30 );
        Combatant eviler_enemy = new Combatant("Eviler Enemy", 45 );

        Scanner input = new Scanner(System.in);

        // main game
        if (!Encounter.fight(input, player, evil_enemy  )) return;
        Encounter.encounterWon(input, player);
        if (!Encounter.fight(input, player, eviler_enemy)) return;
        Encounter.won();
    }
}
