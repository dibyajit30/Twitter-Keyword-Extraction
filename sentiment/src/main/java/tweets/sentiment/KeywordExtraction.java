package tweets.sentiment;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.parser.nndep.DependencyParser;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreEntityMention;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.GrammaticalRelation;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.WordStemmer;

public class KeywordExtraction {
	private HashMap<String, Integer> nounFreqencies = new HashMap<String, Integer>();
	private HashMap<String, Integer> adjectiveNounFreqencies = new HashMap<String, Integer>();
	private HashMap<String, Integer> adverbVerbFreqencies = new HashMap<String, Integer>();
	private HashMap<String, Integer> prepositionPronounFreqencies = new HashMap<String, Integer>();
	private HashMap<String, Integer> subjectVerbObjectFreqencies = new HashMap<String, Integer>();
	private HashMap<String, Integer> nerFreqencies = new HashMap<String, Integer>();
	
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
		nounFreqencies = new HashMap<String, Integer>();
		for(String freq: allFreqencies.keySet()) {
			if(isNoun(freq)) {
				nounFreqencies.put(freq, allFreqencies.get(freq));
			}
		}
	}
	
	public HashMap<String, Integer> getAdjectiveNounFreqencies() {
		return adjectiveNounFreqencies;
	}

	public void setAdjectiveNounFreqencies(HashMap<String, Integer> allFreqencies) {
		adjectiveNounFreqencies = new HashMap<String, Integer>();
		for(String freq: allFreqencies.keySet()) {
			String[] tokens;
			tokens = freq.split("[^\\S]+");
			if(isAdj(tokens[0]) && isNoun(tokens[1])) {
				adjectiveNounFreqencies.put(freq, allFreqencies.get(freq));
			}
		}
	}

	public HashMap<String, Integer> getAdverbVerbFreqencies() {
		return adverbVerbFreqencies;
	}

	public void setAdverbVerbFreqencies(HashMap<String, Integer> allFreqencies) {
		adverbVerbFreqencies = new HashMap<String, Integer>(); 
		for(String freq: allFreqencies.keySet()) {
			String[] tokens;
			tokens = freq.split("[^\\S]+");
			if(isAdverb(tokens[0]) && isVerb(tokens[1])) {
				adverbVerbFreqencies.put(freq, allFreqencies.get(freq));
			}
		}
	}

	public HashMap<String, Integer> getPrepositionPronounFreqencies() {
		return prepositionPronounFreqencies;
	}

	public void setPrepositionPronounFreqencies(HashMap<String, Integer> allFreqencies) {
		prepositionPronounFreqencies = new HashMap<String, Integer>();
		for(String freq: allFreqencies.keySet()) {
			String[] tokens;
			tokens = freq.split("[^\\S]+");
			if(isPreposition(tokens[0]) && isPronoun(tokens[1])) {
				prepositionPronounFreqencies.put(freq, allFreqencies.get(freq));
			}
		}
	}
	
	public void dependencyParser(ArrayList<String> tweets) {
		subjectVerbObjectFreqencies = new HashMap<String, Integer>();
		String modelPath = DependencyParser.DEFAULT_MODEL;
		MaxentTagger tagger = new MaxentTagger("..\\sentiment\\resources\\wsj-0-18-bidirectional-nodistsim.tagger");
		DependencyParser parser = DependencyParser.loadFromModelFile(modelPath);
		String parsedTweet = new String();
		DocumentPreprocessor tokenizer;
		for(String tweet: tweets) {
			tokenizer = new DocumentPreprocessor(new StringReader(tweet));
			for (List<HasWord> token : tokenizer){
			    List<TaggedWord> tagged = tagger.tagSentence(token); 
			    GrammaticalStructure gs = parser.predict(tagged);
			    parsedTweet = gs.typedDependencies().toString();
			    //System.out.println("Only Typed dependencies => "+parsedTweet); 
			    extractSubjectVerbObject(parsedTweet);
			   }
		}
	}
	
	public void extractSubjectVerbObject(String parsedTweet) {
		String tokens[];
		String nSubj = new String();
		int nSubjPos = -1;
		String root = new String();
		int rootPos = -1;
		String dobj = new String();
		int dobjPos = -1;
		Pattern pattern = Pattern.compile("nsubj\\([a-zA-Z-0-9, )]+");
		Matcher matcher = pattern.matcher(parsedTweet);
		if(matcher.find()) {
			nSubj = matcher.group();
			tokens = extractWordPosition(nSubj);
			if(tokens.length >= 2) {
				nSubj = tokens[0];
				nSubjPos = Integer.parseInt(tokens[1]);
			}
		}
		pattern = Pattern.compile("root\\([a-zA-Z-0-9, )]+");
		matcher = pattern.matcher(parsedTweet);
		if(matcher.find()) {
			root = matcher.group();
			tokens = extractWordPosition(root);
			if(tokens.length >= 2) {
				root = tokens[0];
				rootPos = Integer.parseInt(tokens[1]);
			}
		}
		pattern = Pattern.compile("dobj\\([a-zA-Z-0-9, )]+");
		matcher = pattern.matcher(parsedTweet);
		if(matcher.find()) {
			dobj = matcher.group();
			tokens = extractWordPosition(dobj);
			if(tokens.length >= 2) {
				dobj = tokens[0];
				dobjPos = Integer.parseInt(tokens[1]);
			}
		}
		if(((nSubjPos + 1) == rootPos) && ((rootPos + 1) == dobjPos)) {
			addSubjectVerbObjectFreqencies(nSubj + " " + root + " " + dobj);
		}
	}
	
	public String[] extractWordPosition(String wordPositon) {
		String tokens[];
		Pattern pattern = Pattern.compile(" [a-zA-Z]+-[0-9]+");
		Matcher matcher = pattern.matcher(wordPositon);
		if(matcher.find()) {
			wordPositon = matcher.group();
		}
		else {
			wordPositon = "-";
		}
		tokens = wordPositon.trim().split("-"); 
		return tokens;
	}
	
	public HashMap<String, Integer> getSubjectVerbObjectFreqencies() {
		return subjectVerbObjectFreqencies;
	}

	public void addSubjectVerbObjectFreqencies(String subjectVerbObject) {
		if(subjectVerbObjectFreqencies.containsKey(subjectVerbObject)) {
			subjectVerbObjectFreqencies.put(subjectVerbObject, subjectVerbObjectFreqencies.get(subjectVerbObject) + 1);
		}
		else {
			subjectVerbObjectFreqencies.put(subjectVerbObject, 1);
		}
	}
	
	public void setNer(ArrayList<String> tweets) {
		for(String tweet:tweets) {
		  Properties props = new Properties();
		  props.put("annotators", "tokenize, ssplit, pos, lemma, ner");
		  StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		  CoreDocument document  = new CoreDocument(tweet);
		  pipeline.annotate(document);
		  List<CoreEntityMention> coreEntities = document.sentences().get(0).entityMentions();
		  for(CoreEntityMention coreEntitie:coreEntities) {
			  String entity = coreEntitie.text();
			  if(nerFreqencies.containsKey(entity)) {
				  nerFreqencies.put(entity, nerFreqencies.get(entity)+1);
			 }
			  else {
				  nerFreqencies.put(entity, 1);
			  }
		  }
		}
	}

	public HashMap<String, Integer> getNerFreqencies() {
		return nerFreqencies;
	}
}
