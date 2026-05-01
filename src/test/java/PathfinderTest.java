
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import ca.sfu.cmpt276.dungeon.Pathfinder;
import ca.sfu.cmpt276.dungeon.Position;
import ca.sfu.cmpt276.dungeon.gameobject.Monster;
import ca.sfu.cmpt276.dungeon.gameobject.Player;

@SuppressWarnings("unused")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PathfinderTest extends TestEnvironment {

    Stream<Object[]> playerIsReachableProvider() {
        return Stream.of(
            new Object[]{new Player(1,1, round), new Monster(14,9, round)},
            new Object[]{new Player(11,7, round), new Monster(4,12, round)}
        );
    }

    @ParameterizedTest
    @MethodSource("playerIsReachableProvider")
    public void playerIsReachable_returnsPlayerPath(Player player, Monster monster) {
        Pathfinder pathfinder = new Pathfinder(round);
        pathfinder.setStartingPosition(monster.getX(), monster.getY());
        pathfinder.setGoalPosition(player.getX(), player.getY());
        List<Position> playerPath = pathfinder.getPath();
        assertEquals(playerPath.getLast().x,player.getX());
        assertEquals(playerPath.getLast().y,player.getY());
    }

    @Test
    public void playerIsUnreachable_returnsEmptyList() {
        Player player = new Player(0, 0, round);
        Monster monster = new Monster(4,12,round);
        Pathfinder pathfinder = new Pathfinder(round);
        pathfinder.setStartingPosition(monster.getX(), monster.getY());
        pathfinder.setGoalPosition(player.getX(), player.getY());
        List<Position> playerPath = pathfinder.getPath();
        assertEquals(playerPath.size(), 0);
    }
}
