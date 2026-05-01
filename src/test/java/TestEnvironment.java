import java.lang.reflect.Method;

import ca.sfu.cmpt276.dungeon.CollisionHandler;
import ca.sfu.cmpt276.dungeon.Round;

/**
 * Class to simulate aspects of the game environment and provide necessary tools for testing (MODIFY IF NEEDED)
 * @author Alex Tran
 */
public abstract class TestEnvironment {
    public Round round;
    public CollisionHandler collisionHandler;

    public TestEnvironment() {
        round = new Round();
        collisionHandler = new CollisionHandler(round);
        round.startRound();
    }

    public static Method getPrivateMethod(Object object, String methodName, Class<?> ... parameterTypes) {
        try {
            Method privateMethod = object.getClass().getDeclaredMethod(methodName, parameterTypes);
            privateMethod.setAccessible(true);
            return privateMethod;
        } catch (NoSuchMethodException e) {
            System.out.println("No such method found.");
            return null;
        }
    }
}