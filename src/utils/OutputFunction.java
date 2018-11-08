package utils;

public interface OutputFunction<I, O> {
	O result(I neuralNetOutput);
}
