package tweets.sentiment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import edu.stanford.nlp.util.CollectionUtils;
import edu.stanford.nlp.util.StringUtils;

public class NGrams {
	private HashMap<String, Integer> grams1Freq = new HashMap<String, Integer>();
	private HashMap<String, Integer> grams2Freq = new HashMap<String, Integer>();
	private HashMap<String, Integer> grams3Freq = new HashMap<String, Integer>();
	private HashMap<String, Integer> grams4Freq = new HashMap<String, Integer>();
	
	public void setnGramsFreq(ArrayList<String> tweets) {
		Report report = new Report();
		HashMap<String, Integer> grams = new HashMap<String, Integer>();
		grams1Freq = new HashMap<String, Integer>();
		grams2Freq = new HashMap<String, Integer>();
		grams3Freq = new HashMap<String, Integer>();
		grams4Freq = new HashMap<String, Integer>();
		for(String tweet: tweets) {
			Collection<String> nGrams = new ArrayList<String>();
			for(int i=1; i<=4; i++) {
				nGrams = this.getNgrams(tweet, i, i);
				for(String gram: nGrams) {
					switch(i) {
						case 1: this.grams1Freq = report.addFrequecy(this.grams1Freq, gram);
						break;
						case 2: this.grams2Freq = report.addFrequecy(this.grams2Freq, gram);
						break;
						case 3: this.grams3Freq = report.addFrequecy(this.grams3Freq, gram);
						break;
						case 4: this.grams4Freq = report.addFrequecy(this.grams4Freq, gram);
						break;
					}
				}
			}
		}
	}

	public Collection<String> getNgrams(String sentence, int minSize, int maxSize){
		List<String> words = new ArrayList<String>();
		words = this.getWords(sentence);
		List<List<String>> ng = CollectionUtils.getNGrams(words, minSize, maxSize);
		Collection<String> ngrams = new ArrayList<String>();
		for(List<String> n: ng) {
			ngrams.add(StringUtils.join(n," "));
		}
		return ngrams;
	}
	
	public HashMap<String, Integer> get1GramsFreq() {
		return grams1Freq;
	}

	public HashMap<String, Integer> get2GramsFreq() {
		return grams2Freq;
	}

	public HashMap<String, Integer> get3GramsFreq() {
		return grams3Freq;
	}

	public HashMap<String, Integer> get4GramsFreq() {
		return grams4Freq;
	}

	public List<String> getWords(String sentence){
		List<String> words = new ArrayList<String>();
		String[] tokens;
		tokens = sentence.split("[^\\S]+");
		for(String token: tokens) {
			words.add(token);
		}
		return words;
	}
}
