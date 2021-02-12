package ir.faraji.hoosh3;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model {

	private final String modelName;
	private Map<String, Double> unigram;
	private Map<Pair, Double> bigram;

	private List<String> wordsList;
	private Map<String, Integer> wordsFrequncy;

	private Double lamda1 = 0.4;
	private Double lamda2 = 0.3;
	private Double lamda3 = 0.4;
	private Double epsilon = 0.5;

	public Model(String modelName, List<String> wordsList) {
		this.modelName = modelName;
		this.wordsList = wordsList;
	}

	public String getModelName() {
		return modelName;
	}

	public void setLamda1(Double lamda) {
		this.lamda1 = lamda;
	}

	public void setLamda2(Double lamda) {
		this.lamda2 = lamda;
	}

	public void setLamda3(Double lamda) {
		this.lamda3 = lamda;
	}

	public void setEpsilon(Double epsilon) {
		this.epsilon = epsilon;
	}

	public void prepareFrequencies() {
		this.wordsFrequncy = WordsProcessUtils.getWordsFrequency(wordsList);
	}

	public void removeLowFrequencies(Integer limit) {
		FilteringUtils.filterLowFrequentWords(wordsFrequncy, limit);
	}

	public void calculateUnigrams() {

		Integer totalRealWordsSize = wordsFrequncy.values().stream().reduce(0, (a, b) -> a + b);

		unigram = new HashMap<>();

		for (String word : wordsFrequncy.keySet()) {
			unigram.put(word, wordsFrequncy.get(word) / (double) totalRealWordsSize);
		}

	}

	public void calculateBigrams() {
		List<Pair> pairsList = WordsProcessUtils.getPairs(wordsList);
		Map<Pair, Integer> pairsFrequency = WordsProcessUtils.getPairsFrequency(pairsList);

		bigram = new HashMap<>();

		for (Pair pair : pairsFrequency.keySet()) {
			bigram.put(pair, pairsFrequency.get(pair) / (double) Collections.frequency(wordsList, pair.getFirstWord()));
		}
	}

	public Double getTextProbabilityWithBackOff(String text) {
		List<Pair> paris = WordsProcessUtils.getPairs(WordsProcessUtils.getWords(text));

		Map<Pair, Double> textBackOffs = new HashMap<>();
		for (Pair pair : paris) {

			Double bigramVal = (bigram.get(pair) == null) ? 0 : bigram.get(pair);

			Double unigramVal = (unigram.get(pair.getSecondWord()) == null)
					? (Collections.frequency(wordsList, pair.getSecondWord()) / (double) wordsList.size())
					: unigram.get(pair.getSecondWord());

			textBackOffs.put(pair, (lamda1 * bigramVal) + (lamda2 * unigramVal) + (lamda3 * epsilon));
		}

		double probability = 1;
		for (Pair pair : paris) {
			probability = probability * textBackOffs.get(pair);
		}
		return probability;
	}

	public void setBackOffParameters(double lamda1, double lamda2, double lamda3, double epsilon) {
		setLamda1(lamda1);
		setLamda2(lamda2);
		setLamda3(lamda3);
		setEpsilon(epsilon);

	}

}
