package ma.ac.emi.ginfo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import safar.modern_standard_arabic.basic.morphology.lemmatizer.model.WordLemmatizerAnalysis;

@Service
public interface SafarService {
	
	List<WordLemmatizerAnalysis> lemmatize(String text);
	String normalize(String text);
	String removeStepWords(String text);
	String[] tokenize(String text);
}
