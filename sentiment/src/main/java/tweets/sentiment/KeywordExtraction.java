package tweets.sentiment;

import java.util.HashMap;

import edu.stanford.nlp.simple.Sentence;

public class KeywordExtraction {
	private HashMap<String, Integer> nounFreqencies = new HashMap<String, Integer>();
	private HashMap<String, Integer> adjectiveNounFreqencies = new HashMap<String, Integer>();
	private HashMap<String, Integer> adverbVerbFreqencies = new HashMap<String, Integer>();
	private HashMap<String, Integer> prepositionPronounFreqencies = new HashMap<String, Integer>();
	
	public String getPartOfSpeech(String text) {
		if(text.isEmpty()) {
			return "??";
		}
		Sentence sentence = new Sentence(text);
		return sentence.posTag(0);
	}
	
	public boolean isNoun(String text) {
		if(getPartOfSpeech(text).substring(0, 2).equals("NN")) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean isAdj(String text) {
		if(getPartOfSpeech(text).substring(0, 2).equals("JJ")) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean isVerb(String text) {
		if(getPartOfSpeech(text).substring(0, 2).equals("VB")) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean isAdverb(String text) {
		if(getPartOfSpeech(text).substring(0, 2).equals("RB")) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean isPreposition(String text) {
		if(getPartOfSpeech(text).substring(0, 2).equals("IN")) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean isPronoun(String text) {
		if(getPartOfSpeech(text).substring(0, 2).equals("WP") || getPartOfSpeech(text).substring(0, 2).equals("PR")) {
			return true;
		}
		else {
			return false;
		}
	}

	public HashMap<String, Integer> getNounFreqencies() {
		return nounFreqencies;
	}

	public void setNounFreqencies(HashMap<String, Integer> allFreqencies) {
		for(String freq: allFreqencies.keySet()) {
			if(isNoun(freq)) {
				if(nounFreqencies.containsKey(freq)) {
					nounFreqencies.put(freq, nounFreqencies.get(freq) + 1);
				}
				else {
					nounFreqencies.put(freq, 1);
				}
			}
		}
	}
	
	public HashMap<String, Integer> getAdjectiveNounFreqencies() {
		return adjectiveNounFreqencies;
	}

	public void setAdjectiveNounFreqencies(HashMap<String, Integer> allFreqencies) {
		for(String freq: allFreqencies.keySet()) {
			String[] tokens;
			tokens = freq.split("[^\\S]+");
			if(isAdj(tokens[0]) && isNoun(tokens[1])) {
				if(adjectiveNounFreqencies.containsKey(freq)) {
					adjectiveNounFreqencies.put(freq, adjectiveNounFreqencies.get(freq) + 1);
				}
				else {
					adjectiveNounFreqencies.put(freq, 1);
				}
			}
		}
	}

	public HashMap<String, Integer> getAdverbVerbFreqencies() {
		return adverbVerbFreqencies;
	}

	public void setAdverbVerbFreqencies(HashMap<String, Integer> allFreqencies) {
		for(String freq: allFreqencies.keySet()) {
			String[] tokens;
			tokens = freq.split("[^\\S]+");
			if(isAdverb(tokens[0]) && isVerb(tokens[1])) {
				if(adverbVerbFreqencies.containsKey(freq)) {
					adverbVerbFreqencies.put(freq, adverbVerbFreqencies.get(freq) + 1);
				}
				else {
					adverbVerbFreqencies.put(freq, 1);
				}
			}
		}
	}

	public HashMap<String, Integer> getPrepositionPronounFreqencies() {
		return prepositionPronounFreqencies;
	}

	public void setPrepositionPronounFreqencies(HashMap<String, Integer> allFreqencies) {
		for(String freq: allFreqencies.keySet()) {
			String[] tokens;
			tokens = freq.split("[^\\S]+");
			if(isPreposition(tokens[0]) && isPronoun(tokens[1])) {
				if(prepositionPronounFreqencies.containsKey(freq)) {
					prepositionPronounFreqencies.put(freq, prepositionPronounFreqencies.get(freq) + 1);
				}
				else {
					prepositionPronounFreqencies.put(freq, 1);
				}
			}
		}
	}	
}
