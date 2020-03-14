package tweets.sentiment;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractTweetContents {
	public ArrayList<String> getContents(String tweet, Pattern pattern){
		ArrayList<String> contents = new ArrayList<String>();
		Matcher matcher = pattern.matcher(tweet);
		while(matcher.find()) {
			contents.add(matcher.group());
		}
		return contents;
	}
	
	public ArrayList<String> extractHashtags(String tweet){
		ArrayList<String> hashtags = new ArrayList<String>();
		Pattern pattern = Pattern.compile("#[a-zA-Z0-9]+");
		hashtags = this.getContents(tweet, pattern);
		return hashtags;
	}
	
	public ArrayList<String> extractAtMentions(String tweet){
		ArrayList<String> atMentions = new ArrayList<String>();
		Pattern pattern = Pattern.compile("@[a-zA-Z0-9]+");
		atMentions = this.getContents(tweet, pattern);
		return atMentions;
	}
}
