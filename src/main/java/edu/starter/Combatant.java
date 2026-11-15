package edu.starter;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Combatant implements Named {
    private int HP;
    private final int maxHP;
    private int resources = 5;
    private final int maxResources = 10;
    private boolean isDefenced = false;
    private boolean isAlive = true;
    private final String name;
    private int attackPower = 10;


    public Combatant(String name, int HP) {
        this.name = name;
        this.maxHP = HP;
        this.HP = HP;
    }


    @Override
    public String name() {
        return this.name;
    }


    public boolean isAlive() {
        return this.isAlive;
    }


    private boolean kill() {
        this.isAlive = false;
        return true;
    }


    public boolean useDefence() {
        if (isDefenced) {
            isDefenced = false;
            return true;
        }
        return false;
    }

    public int getHP() {
        return HP;
    }

    public int getResource() {
        return resources;
    }

    public int getAttackPower() {
        return attackPower;
    }

    private boolean recoverHP(int amount) {
        if (this.HP == this.maxHP) {
            System.out.printf("Already at max HP (%d)", this.maxHP);
            return false;
        }
        this.takeDamage(-amount);
        return true;
    }

    public boolean recoverHP() {
        return this.recoverHP(25);
    }

    public boolean powerUp(int amount) {
        if (this.attackPower + amount < 0) return false;
        this.attackPower += amount;
        return true;
    }

    public boolean powerUp() {
        return this.powerUp(2);
    }


    public record SelfAction(String name, Function<Combatant, Boolean> action) implements Named {};

    public final SelfAction[] selfActions = {
        new SelfAction("defend" , Combatant::defend ),
        new SelfAction("recover", Combatant::recover)
    };


    public record EnemyAction(String name, BiFunction<Combatant, Combatant, Boolean> action) implements Named {};

    public final EnemyAction[] enemyActions = {
        new EnemyAction("basic attack"  , Combatant::attack ),
        new EnemyAction("special attack", Combatant::special)
    };


    private boolean takeDamage(int damage) {
        return takeDamage(damage, null);
    }


    private boolean takeDamage(int damage, Combatant conflicter) {
        this.HP = Math.max(0, Math.min(this.HP - damage, maxHP));
        if (damage >= 0) {
            if (conflicter == null) System.out.printf("%d damage done to %s\n", damage, this.name());
            else if (damage >= 0) System.out.printf("%d damage done to %s by %s\n", damage, this.name(), conflicter.name());
        }
        else {
            if (conflicter == null) System.out.printf("%s healed %d HP\n", this.name(), -damage);
            else if (damage >= 0) System.out.printf("%s healed %d HP by %s\n", this.name(), -damage, conflicter.name());
        }
        if (this.HP <= 0) {
            this.kill();
            System.out.printf("%s is dead\n", this.name());
        }
        return true;
    }


    public boolean attack(Combatant enemy) {
        if (enemy.useDefence()) {
            enemy.takeDamage(this.attackPower - 5, this);
        }
        else enemy.takeDamage(this.attackPower, this);
        return true;
    }


    public boolean defend() {
        this.isDefenced = true;
        System.out.printf("%s is now defended\n", this.name);
        return true;
    }

    public boolean special(Combatant enemy) {
        if (this.resources < 3) {
            System.out.println("You cannot afford this action");
            return false;
        }
        if (enemy.useDefence())
            enemy.takeDamage(10);
        else enemy.takeDamage(15);
        this.resources -= 3;
        return true;
    }

    public boolean recover() {
        if (this.resources == this.maxResources) {
            System.out.printf("%s is already at max resources (%d)\n", this.name(), this.getResource());
            return false;
        }
        this.resources = Math.min(resources + 3, 10);
        System.out.printf("%s is now at %d resources\n", this.name(), this.getResource());
        return true;
    }
}
