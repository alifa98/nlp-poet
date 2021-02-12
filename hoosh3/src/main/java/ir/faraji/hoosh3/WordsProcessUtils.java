package ir.faraji.hoosh3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordsProcessUtils {

	public static final String START_LINE_INDICATOR = "<S>";
	public static final String END_LINE_INDICATOR = "</S>";

	public static List<String> getStandardWordsList(InputStream inputStream) throws IOException {

		List<String> wordsList = new ArrayList<>();

		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
			String line;

			while ((line = bufferedReader.readLine()) != null) {

				wordsList.addAll(getWords(line));
			}

		}
		return wordsList;
	}

	public static List<String> getWords(String line) {

		List<String> lineWords = new ArrayList<>();

		String trimedLine = line.trim();
		if (!trimedLine.isBlank()) {
			lineWords.add(START_LINE_INDICATOR);
			for (String word : trimedLine.split("\\s+")) {
				lineWords.add(word);
			}
			lineWords.add(END_LINE_INDICATOR);

		}

		return lineWords;
	}

	public static Map<String, Integer> getWordsFrequency(List<String> wordsList) {

		Map<String, Integer> frequency = new HashMap<>();

		for (String word : wordsList) {

			if (!(START_LINE_INDICATOR.equals(word) || END_LINE_INDICATOR.equals(word))) {

				if (frequency.containsKey(word)) {
					frequency.put(word, frequency.get(word) + 1);
				} else {
					frequency.put(word, 1);
				}

			}

		}

		return frequency;
	}

	public static List<Pair> getPairs(List<String> wordsList) {
		List<Pair> pariList = new ArrayList<>();

		for (int i = 0; i < wordsList.size() - 1; i++) {
			String first = wordsList.get(i);
			String second = wordsList.get(i + 1);
			if (!(START_LINE_INDICATOR.equals(second) && END_LINE_INDICATOR.equals(first))) {
				pariList.add(new Pair(first, second));
			}
		}

		return pariList;
	}

	public static Map<Pair, Integer> getPairsFrequency(List<Pair> pairsList) {
		Map<Pair, Integer> frequency = new HashMap<>();

		for (Pair pair : pairsList) {

			if (frequency.containsKey(pair)) {
				frequency.put(pair, frequency.get(pair) + 1);
			} else {
				frequency.put(pair, 1);
			}
		}
		return frequency;
	}
}
