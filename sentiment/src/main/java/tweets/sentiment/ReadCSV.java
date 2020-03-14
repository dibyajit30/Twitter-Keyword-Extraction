package tweets.sentiment;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.opencsv.CSVReader;

public class ReadCSV {
	private ArrayList<String> tweets = new ArrayList<String>();
	private HashMap<String, Integer> hashtagFreq = new HashMap<String, Integer>();
	private HashMap<String, Integer> atMentionFreq = new HashMap<String, Integer>();
	
	public HashMap<String, Integer> getHashtagFreq() {
		return hashtagFreq;
	}

	public void setHashtagFreq(HashMap<String, Integer> hashtagFreq) {
		this.hashtagFreq = hashtagFreq;
	}
	
	public void addHashtagFreq(String hashtag) {
		if(this.hashtagFreq.containsKey(hashtag)) {
			this.hashtagFreq.put(hashtag, this.hashtagFreq.get(hashtag) + 1);
		}
		else {
			this.hashtagFreq.put(hashtag, 1);
		}
	}

	public HashMap<String, Integer> getAtMentionFreq() {
		return atMentionFreq;
	}

	public void setAtMentionFreq(HashMap<String, Integer> atMentionFreq) {
		this.atMentionFreq = atMentionFreq;
	}
	
	public void addAtMentionFreq(String atMention) {
		if(this.atMentionFreq.containsKey(atMention)) {
			this.atMentionFreq.put(atMention, this.atMentionFreq.get(atMention) + 1);
		}
		else {
			this.atMentionFreq.put(atMention, 1);
		}
	}

	public	ReadCSV(String file) throws IOException {
		FileReader filereader = new FileReader(file); 
		CSVReader csvReader = new CSVReader(filereader); 
        String[] nextRecord; 
  
        while ((nextRecord = csvReader.readNext()) != null) { 
        	tweets.add(nextRecord[5]);
        	ArrayList<String> contents = new ArrayList<String>(); 
        	ExtractTweetContents extractContents = new ExtractTweetContents();
        	contents = extractContents.extractHashtags(nextRecord[5]);
        	for(String content: contents) {
        		this.addHashtagFreq(content);
        	}
        	contents = extractContents.extractAtMentions(nextRecord[5]);
        	for(String content: contents) {
        		this.addAtMentionFreq(content);
        	}
        }
        
        csvReader.close();
	}
	
	public ArrayList<String> getTweets() throws IOException{
		return this.tweets;
	}
}
