package ir.faraji.hoosh3;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Tester {
	private String testName;

	public Tester(String name) {
		testName = name;
	}

	public double test(Model model1, Model model2, Model model3) {
		List<String> testLines = getLines();

		int success = 0;
		for (String line : testLines) {

			String[] temp = line.split("\t");
			String shaer = temp[0].trim();
			String mesra = temp[1].trim();

			Double ehtemal1 = model1.getTextProbabilityWithBackOff(mesra);
			Double ehtemal2 = model2.getTextProbabilityWithBackOff(mesra);
			Double ehtemal3 = model3.getTextProbabilityWithBackOff(mesra);

			// maximum
			Double maximum = ehtemal1;
			String shaerID = model1.getModelName();
			if (maximum < ehtemal2) {
				maximum = ehtemal2;
				shaerID = model2.getModelName();
			}
			if (maximum < ehtemal3) {
				shaerID = model3.getModelName();
			}

			if (shaerID.equals(shaer)) {
				success++;
				System.out.println(testName + ":: successful ::line " + mesra + " is for " + shaerID);
			} else {
				System.out.println(testName + ":: failed ::line " + mesra + " is not for " + shaerID);
			}

		}

		double degat = success / (double) testLines.size();
		System.out.println(String.format("Degat dar Test <<%s>> = %f ", testName, degat));

		return degat;
	}

	private List<String> getLines() {

		List<String> lines = new ArrayList<>();
		try (InputStream inputStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("data/test_set/testcase.txt")) {

			try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
				String line;

				while ((line = bufferedReader.readLine()) != null) {
					lines.add(line);
				}

			}

		} catch (Exception e) {
			System.err.println(e);
		}
		return lines;
	}

}
