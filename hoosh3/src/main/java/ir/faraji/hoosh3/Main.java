package ir.faraji.hoosh3;

import java.io.InputStream;
import java.util.List;

public class Main {

	private static final Integer LIMIT_FREQUNCY = 2;

	public static void main(String[] args) {

		try (InputStream molaviInputStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("data/train_set/molavi_train.txt");
				InputStream ferdowsiInputStream = Thread.currentThread().getContextClassLoader()
						.getResourceAsStream("data/train_set/ferdowsi_train.txt");
				InputStream hafezInputStream = Thread.currentThread().getContextClassLoader()
						.getResourceAsStream("data/train_set/hafez_train.txt")) {

			List<String> moulaviWords = WordsProcessUtils.getStandardWordsList(molaviInputStream);
			List<String> ferdowsiWords = WordsProcessUtils.getStandardWordsList(ferdowsiInputStream);
			List<String> hafezWords = WordsProcessUtils.getStandardWordsList(hafezInputStream);

			System.out.println("Start To Modeling Moulavi ...");
			Model moulavei = new Model("3", moulaviWords);
			calculates(moulavei);
			System.out.println("End of Moulavi Modeling.");

			System.out.println("Start To Modeling Ferdowsi ...");
			Model ferdowsi = new Model("1", ferdowsiWords);
			calculates(ferdowsi);
			System.out.println("End of Ferdowsi Modeling.");

			System.out.println("Start To Modeling Hafez ...");
			Model hafez = new Model("2", hafezWords);
			calculates(hafez);
			System.out.println("End of Hafez Modeling.");

			moulavei.setBackOffParameters(0.9, 0.08, 0.02, 0.1);
			ferdowsi.setBackOffParameters(0.9, 0.08, 0.02, 0.1);
			hafez.setBackOffParameters(0.9, 0.08, 0.02, 0.1);
			double degat1 = new Tester("Test1").test(moulavei, ferdowsi, hafez);

			moulavei.setBackOffParameters(0.9, 0.08, 0.02, 0.9);
			ferdowsi.setBackOffParameters(00.9, 0.08, 0.02, 0.9);
			hafez.setBackOffParameters(0.9, 0.08, 0.02, 0.9);
			double degat2 = new Tester("Test2").test(moulavei, ferdowsi, hafez);

			moulavei.setBackOffParameters(0.3, 0.3, 0.4, 0.1);
			ferdowsi.setBackOffParameters(0.3, 0.3, 0.4, 0.1);
			hafez.setBackOffParameters(0.3, 0.3, 0.4, 0.3);
			double degat3 = new Tester("Test3").test(moulavei, ferdowsi, hafez);

			moulavei.setBackOffParameters(0.3, 0.3, 0.4, 0.9);
			ferdowsi.setBackOffParameters(0.3, 0.3, 0.4, 0.9);
			hafez.setBackOffParameters(0.3, 0.3, 0.4, 0.9);
			double degat4 = new Tester("Test4").test(moulavei, ferdowsi, hafez);

			System.out.println(String.format("%f, %f, %f, %f", degat1, degat2, degat3, degat4));

		} catch (Exception e) {
			System.err.println(e);
		}
	}

	private static void calculates(Model model) {
		model.prepareFrequencies();
		model.removeLowFrequencies(LIMIT_FREQUNCY);
		model.calculateUnigrams();
		model.calculateBigrams();
	}
}
