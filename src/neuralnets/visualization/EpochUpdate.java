package neuralnets.visualization;

public interface EpochUpdate<E> {

    void update(int currentEpoch, E data);

}
