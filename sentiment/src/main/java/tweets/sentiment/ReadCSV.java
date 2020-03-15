package tweets.sentiment;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.opencsv.CSVReader;

public class ReadCSV {
	private ArrayList<String> tweets = new ArrayList<String>();
	private ArrayList<String> positiveTweets = new ArrayList<String>();
	private ArrayList<String> negativeTweets = new ArrayList<String>();
	private HashMap<String, Integer> hashtagFreq = new HashMap<String, Integer>();
	private HashMap<String, Integer> atMentionFreq = new HashMap<String, Integer>();
	
	public HashMap<String, Integer> getHashtagFreq() {
		return hashtagFreq;
	}

	public void setHashtagFreq(HashMap<String, Integer> hashtagFreq) {
		this.hashtagFreq = hashtagFreq;
	}
	
	public void addHashtagFreq(String hashtag) {
		Report report = new Report();
		this.hashtagFreq = report.addFrequecy(this.hashtagFreq, hashtag);
	}

	public HashMap<String, Integer> getAtMentionFreq() {
		return atMentionFreq;
	}

	public void setAtMentionFreq(HashMap<String, Integer> atMentionFreq) {
		this.atMentionFreq = atMentionFreq;
	}
	
	public void addAtMentionFreq(String atMention) {
		Report report = new Report();
		this.atMentionFreq = report.addFrequecy(this.atMentionFreq, atMention);
	}

	public	ReadCSV(String file) throws IOException {
		FileReader filereader = new FileReader(file); 
		CSVReader csvReader = new CSVReader(filereader);
        String[] nextRecord; 
  
        while ((nextRecord = csvReader.readNext()) != null) { 
        	String tweet = nextRecord[5];
        	ArrayList<String> contents = new ArrayList<String>(); 
        	ExtractTweetContents extractContents = new ExtractTweetContents();
        	contents = extractContents.extractHashtags(tweet);
        	for(String content: contents) {
        		this.addHashtagFreq(content);
        	}
        	contents = extractContents.extractAtMentions(tweet);
        	for(String content: contents) {
        		this.addAtMentionFreq(content);
        	}
        	tweet = extractContents.extractUsefulWords(tweet);
        	tweets.add(tweet);
        	if(nextRecord[0].equals("4")) {
        		positiveTweets.add(tweet);
        	}
        	else {
        		negativeTweets.add(tweet);
        	}
        }
        
        csvReader.close();
	}
	
	public ArrayList<String> getTweets() throws IOException{
		return this.tweets;
	}
	
	public ArrayList<String> getPositiveTweets() {
		return this.positiveTweets;
	}

	public ArrayList<String> getNegativeTweets() {
		return this.negativeTweets;
	}

}
