package tweets.sentiment;

import java.util.HashMap;
import java.util.Map.Entry;

public class Report {
	private HashMap<String, Integer> frequentHashtags = new HashMap<String, Integer>();
	private HashMap<String, Integer> frequentAtMentions = new HashMap<String, Integer>();
	
	public HashMap<String, Integer> getTopTwenty(HashMap<String, Integer> allContents) {
		HashMap<String, Integer> topTwenty = new HashMap<String, Integer>();
		for(int i=0; i<20; i++) {
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

	public HashMap<String, Integer> getFrequentHashtags(HashMap<String, Integer> hashtags) {
		setFrequentHashtags(hashtags);
		return frequentHashtags;
	}

	public void setFrequentHashtags(HashMap<String, Integer> hashtags) {
		this.frequentHashtags = this.getTopTwenty(hashtags);
	}

	public HashMap<String, Integer> getFrequentAtMentions(HashMap<String, Integer> atMentions) {
		setFrequentAtMentions(atMentions);
		return frequentAtMentions;
	}

	public void setFrequentAtMentions(HashMap<String, Integer> atMentions) {
		this.frequentAtMentions = this.getTopTwenty(atMentions);
	}
}
