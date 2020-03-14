package tweets.sentiment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class App 
{
    public static void main( String[] args ) throws IOException
    {
        String fileName = "C:\\Users\\Dibyajit\\Documents\\Courses\\BDS\\HW\\sentiment140-mini.csv";
        ArrayList<String> tweets = new ArrayList<String>();
        HashMap<String, Integer> hashtagFreq = new HashMap<String, Integer>();
    	HashMap<String, Integer> atMentionFreq = new HashMap<String, Integer>();
        ReadCSV read = new ReadCSV(fileName);
        tweets = read.getTweets();
        hashtagFreq = read.getHashtagFreq();
        atMentionFreq = read.getAtMentionFreq();
        Report report = new Report();
        hashtagFreq = report.getFrequentHashtags(hashtagFreq);
        atMentionFreq = report.getFrequentAtMentions(atMentionFreq);
        System.out.println("Hashtag freqencies:-");
        System.out.println();
        for(String hashtag: hashtagFreq.keySet()) {
        	System.out.println(hashtag + " " + hashtagFreq.get(hashtag));
        }
        System.out.println();
        System.out.println("@Mention freqencies:-");
        System.out.println();
        for(String mention: atMentionFreq.keySet()) {
        	System.out.println(mention + " " + atMentionFreq.get(mention));
        }
    }
}
