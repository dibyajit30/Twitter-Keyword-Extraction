package tweets.sentiment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class App 
{
    public static void main( String[] args ) throws IOException
    {
        String fileName = "C:\\Users\\Dibyajit\\Documents\\Courses\\BDS\\HW\\sentiment140-mini.csv";
        ArrayList<String> tweets = new ArrayList<String>();
        HashMap<String, Integer> hashtagFreq = new HashMap<String, Integer>();
    	HashMap<String, Integer> atMentionFreq = new HashMap<String, Integer>();
    	HashMap<String, Integer> n1GramFreq = new HashMap<String, Integer>();
    	HashMap<String, Integer> n2GramFreq = new HashMap<String, Integer>();
    	HashMap<String, Integer> n3GramFreq = new HashMap<String, Integer>();
    	HashMap<String, Integer> n4GramFreq = new HashMap<String, Integer>();
    	HashMap<String, Integer> nounFreq = new HashMap<String, Integer>();
    	HashMap<String, Integer> adjectiveNounFreq = new HashMap<String, Integer>();
    	HashMap<String, Integer> adverbVerbFreq = new HashMap<String, Integer>();
    	HashMap<String, Integer> prepositionPronounFreq = new HashMap<String, Integer>();
        ReadCSV read = new ReadCSV(fileName);
        tweets = read.getTweets();
        hashtagFreq = read.getHashtagFreq();
        atMentionFreq = read.getAtMentionFreq();
        Report report = new Report();
        hashtagFreq = report.getFrequentHashtags(hashtagFreq);
        atMentionFreq = report.getFrequentAtMentions(atMentionFreq);
        report.reportFrequencies("Hashtag", hashtagFreq);
        report.reportFrequencies("@Mention", atMentionFreq);
        NGrams ngram = new NGrams();
        ngram.setnGramsFreq(tweets);
        KeywordExtraction keyword = new KeywordExtraction();
        n1GramFreq = ngram.get1GramsFreq();
        keyword.setNounFreqencies(n1GramFreq);
        nounFreq = report.getFreqentNouns(keyword.getNounFreqencies());
        n1GramFreq = report.getFreqent1Grams(n1GramFreq);
        report.reportFrequencies("1Grams", n1GramFreq);
        n2GramFreq = ngram.get2GramsFreq();
        keyword.setAdjectiveNounFreqencies(n2GramFreq);
        keyword.setAdverbVerbFreqencies(n2GramFreq);
        keyword.setPrepositionPronounFreqencies(n2GramFreq);
        adjectiveNounFreq = report.getFreqentAdjectiveNoun(keyword.getAdjectiveNounFreqencies());
        adverbVerbFreq = report.getFreqentAdverbVerb(keyword.getAdverbVerbFreqencies());
        prepositionPronounFreq = report.getFreqentPrepositionPronoun(keyword.getPrepositionPronounFreqencies());
        n2GramFreq = report.getFreqent2Grams(n2GramFreq);
        report.reportFrequencies("2Grams", n2GramFreq);
        n3GramFreq = ngram.get3GramsFreq();
        n3GramFreq = report.getFreqent3Grams(n3GramFreq);
        report.reportFrequencies("3Grams", n3GramFreq);
        n4GramFreq = ngram.get4GramsFreq();
        n4GramFreq = report.getFreqent4Grams(n4GramFreq);
        report.reportFrequencies("4Grams", n4GramFreq);
        report.reportFrequencies("Nouns", nounFreq);
        report.reportFrequencies("Adjective-Noun", adjectiveNounFreq);
        report.reportFrequencies("Adverb-Verb", adverbVerbFreq);
        report.reportFrequencies("Preposition-Pronoun", prepositionPronounFreq);
    }
}
