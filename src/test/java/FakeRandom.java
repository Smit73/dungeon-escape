
import java.util.Random;

public class FakeRandom extends Random {
    private int[] values;
    private int index = 0;

    public FakeRandom(int... values) {
        this.values = values;
    }

    @Override
    public int nextInt(int bound) {
        return values[index++ % values.length]%bound;
    }
}