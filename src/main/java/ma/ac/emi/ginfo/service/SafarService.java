package ma.ac.emi.ginfo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import safar.modern_standard_arabic.basic.morphology.lemmatizer.factory.LemmatizerFactory;
import safar.modern_standard_arabic.basic.morphology.lemmatizer.interfaces.ILemmatizer;
import safar.modern_standard_arabic.basic.morphology.lemmatizer.model.WordLemmatizerAnalysis;
import safar.modern_standard_arabic.util.normalization.impl.SAFARNormalizer;
import safar.modern_standard_arabic.util.stop_words.factory.StopWordFactory;
import safar.modern_standard_arabic.util.stop_words.interfaces.ISWsService;
import safar.modern_standard_arabic.util.tokenization.impl.SAFARTokenizer;

@Service
public class SafarService {
	protected ILemmatizer lemmatizerFactory;
	protected SAFARNormalizer normalizer;
	protected ISWsService sWFactory;
	protected SAFARTokenizer tokenizer;
	
	public SafarService() {
		this.lemmatizerFactory = LemmatizerFactory.getSAFARImplementation();
		this.normalizer = new SAFARNormalizer();
		this.sWFactory = StopWordFactory.getSWsImplementation();
		this.tokenizer = new SAFARTokenizer();
	}
	
	
	
	public List<WordLemmatizerAnalysis> lemmatize(String text){
        return this.lemmatizerFactory.lemmatize(text) ;}
	
	public String normalize(String text){
        text = normalizer.normalizeNonArabic(text);
        text = normalizer.normalizePunctuation(text);
        text = normalizer.normalizeDiacritics(text);
        text = normalizer.normalizeWordsContainingDigits(text);
        text = normalizer.normalizeNonArabic(text);
        text = normalizer.normalizeSingleChar(text);
        return normalizer.normalize(text);
    }
	
	public String removeStepWords(String text){
        return this.sWFactory.removeDomainIndependantStopWordsFromString(text);
    }
	
	public String[] tokenize(String text){
        return tokenizer.tokenize(text);
    }

}
