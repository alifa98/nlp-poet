package ir.faraji.hoosh3;

import java.util.Map;

public class FilteringUtils {
	public static void filterLowFrequentWords(Map<String, Integer> words, Integer minValue) {
		words.entrySet().removeIf(entry -> entry.getValue() < minValue);
	}
}
