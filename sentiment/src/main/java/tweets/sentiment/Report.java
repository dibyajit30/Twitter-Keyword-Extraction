package tweets.sentiment;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

public class Report {
	private HashMap<String, Integer> frequentHashtags = new HashMap<String, Integer>();
	private HashMap<String, Integer> frequentAtMentions = new HashMap<String, Integer>();
	private HashMap<String, Integer> freqent1Grams = new HashMap<String, Integer>();
	private HashMap<String, Integer> freqent2Grams = new HashMap<String, Integer>();
	private HashMap<String, Integer> freqent3Grams = new HashMap<String, Integer>();
	private HashMap<String, Integer> freqent4Grams = new HashMap<String, Integer>();
	private HashMap<String, Integer> freqentNouns = new HashMap<String, Integer>();
	private HashMap<String, Integer> freqentadjectiveNoun = new HashMap<String, Integer>();
	private HashMap<String, Integer> freqentadverbVerb = new HashMap<String, Integer>();
	private HashMap<String, Integer> freqentprepositionPronoun = new HashMap<String, Integer>();
	private HashMap<String, Integer> freqentSubjectVerbObject = new HashMap<String, Integer>();
	private HashMap<String, Integer> freqentNER = new HashMap<String, Integer>();
	
	private FileWriter fstream;
	
	public Report() {
		
	}
	
	public Report(FileWriter fstream) {
		this.fstream = fstream;
	}
	
	public HashMap<String, Integer> getTopTwenty(HashMap<String, Integer> allContents, int n) {
		HashMap<String, Integer> topTwenty = new HashMap<String, Integer>();
		for(int i=0; i<n; i++) {
			String key = new String();
			int maxVal = 0;
			for(Entry<String, Integer> item: allContents.entrySet()) {
				if(item.getValue() > maxVal) {
					maxVal = item.getValue();
					key = item.getKey();
				}
			}
			topTwenty.put(key, maxVal);
			allContents.remove(key);
		}
		return topTwenty;
	}
	
	public HashMap<String, Integer> addFrequecy(HashMap<String, Integer> hashMap, String key){
		if(hashMap.containsKey(key)) {
			hashMap.put(key, hashMap.get(key) + 1);
		}
		else {
			hashMap.put(key, 1);
		}
		return hashMap;
	}

	public HashMap<String, Integer> getFrequentHashtags(HashMap<String, Integer> hashtags) {
		setFrequentHashtags(hashtags);
		return frequentHashtags;
	}

	public void setFrequentHashtags(HashMap<String, Integer> hashtags) {
		this.frequentHashtags = this.getTopTwenty(hashtags, 20);
	}

	public HashMap<String, Integer> getFrequentAtMentions(HashMap<String, Integer> atMentions) {
		setFrequentAtMentions(atMentions);
		return frequentAtMentions;
	}

	public void setFrequentAtMentions(HashMap<String, Integer> atMentions) {
		this.frequentAtMentions = this.getTopTwenty(atMentions, 20);
	}
	
	public HashMap<String, Integer> getFreqent1Grams(HashMap<String, Integer> freqNGrams) {
		setFreqent1Grams(freqNGrams);
		return freqent1Grams;
	}

	public void setFreqent1Grams(HashMap<String, Integer> freqentNGrams) {
		this.freqent1Grams = this.getTopTwenty(freqentNGrams, 20);
	}
	
	public HashMap<String, Integer> getFreqent2Grams(HashMap<String, Integer> freqNGrams) {
		setFreqent2Grams(freqNGrams);
		return freqent2Grams;
	}

	public void setFreqent2Grams(HashMap<String, Integer> freqentNGrams) {
		this.freqent2Grams = this.getTopTwenty(freqentNGrams, 20);
	}
	
	public HashMap<String, Integer> getFreqent3Grams(HashMap<String, Integer> freqNGrams) {
		setFreqent3Grams(freqNGrams);
		return freqent3Grams;
	}

	public void setFreqent3Grams(HashMap<String, Integer> freqentNGrams) {
		this.freqent3Grams = this.getTopTwenty(freqentNGrams, 20);
	}
	
	public HashMap<String, Integer> getFreqent4Grams(HashMap<String, Integer> freqNGrams) {
		setFreqent4Grams(freqNGrams);
		return freqent4Grams;
	}

	public void setFreqent4Grams(HashMap<String, Integer> freqentNGrams) {
		this.freqent4Grams = this.getTopTwenty(freqentNGrams, 20);
	}
		
	public HashMap<String, Integer> getFreqentNouns(HashMap<String, Integer> freqentNouns) {
		setFreqentNouns(freqentNouns);
		return this.freqentNouns;
	}
	
	public void setFreqentNouns(HashMap<String, Integer> freqentNouns) {
		this.freqentNouns = this.getTopTwenty(freqentNouns, 20);
	}
	
	public HashMap<String, Integer> getFreqentAdjectiveNoun(HashMap<String, Integer> freqentAdjectiveNoun) {
		setFreqentAdjectiveNoun(freqentAdjectiveNoun);
		return this.freqentadjectiveNoun;
	}

	public void setFreqentAdjectiveNoun(HashMap<String, Integer> freqentadjectiveNoun) {
		this.freqentadjectiveNoun = this.getTopTwenty(freqentadjectiveNoun,10);
	}

	public HashMap<String, Integer> getFreqentAdverbVerb(HashMap<String, Integer> freqentadverbVerb) {
		setFreqentAdverbVerb(freqentadverbVerb);
		return this.freqentadverbVerb;
	}

	public void setFreqentAdverbVerb(HashMap<String, Integer> freqentadverbVerb) {
		this.freqentadverbVerb = this.getTopTwenty(freqentadverbVerb,10);
	}

	public HashMap<String, Integer> getFreqentPrepositionPronoun(HashMap<String, Integer> freqentprepositionPronoun) {
		setFreqentPrepositionPronoun(freqentprepositionPronoun);
		return this.freqentprepositionPronoun;
	}

	public void setFreqentPrepositionPronoun(HashMap<String, Integer> freqentprepositionPronoun) {
		this.freqentprepositionPronoun = this.getTopTwenty(freqentprepositionPronoun,10);
	}
	
	public HashMap<String, Integer> getFreqentSubjectVerbObject(HashMap<String, Integer> freqentSubjectVerbObject) {
		setFreqentSubjectVerbObject(freqentSubjectVerbObject);
		return this.freqentSubjectVerbObject;
	}

	public void setFreqentSubjectVerbObject(HashMap<String, Integer> freqentSubjectVerbObject) {
		this.freqentSubjectVerbObject = this.getTopTwenty(freqentSubjectVerbObject,10);
	}
	
	public HashMap<String, Integer> getFreqentNER(HashMap<String, Integer> freqentNER) {
		setFreqentNER(freqentNER);
		return this.freqentNER;
	}

	public void setFreqentNER(HashMap<String, Integer> freqentNER) {
		this.freqentNER = this.getTopTwenty(freqentNER,10);
	}


	public void reportFrequencies(String context, HashMap<String, Integer> freqencyMap) throws IOException {
	  System.out.println("Most frequent " + context + ":-");
	  fstream.write("\n" + "Most frequent " + context + ":-\n");
      for(String key: freqencyMap.keySet()) {
      	System.out.println(key + " " + ": " + freqencyMap.get(key));
      	fstream.write("\n" + key + " " + ": " + freqencyMap.get(key));
      }
      System.out.println();
      fstream.write("\n");
	}
}
