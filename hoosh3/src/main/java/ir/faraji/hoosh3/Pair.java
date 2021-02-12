package ir.faraji.hoosh3;

public class Pair {
	private String firstWord;
	private String secondWord;

	public Pair(String first, String second) {
		this.firstWord = first;
		this.secondWord = second;
	}

	public String getFirstWord() {
		return firstWord;
	}

	public String getSecondWord() {
		return secondWord;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstWord == null) ? 0 : firstWord.hashCode());
		result = prime * result + ((secondWord == null) ? 0 : secondWord.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pair other = (Pair) obj;
		if (firstWord == null) {
			if (other.firstWord != null)
				return false;
		} else if (!firstWord.equals(other.firstWord))
			return false;
		if (secondWord == null) {
			if (other.secondWord != null)
				return false;
		} else if (!secondWord.equals(other.secondWord))
			return false;
		return true;
	}

	@Override
	public String toString() {

		return firstWord + " " + secondWord;
	}
}
