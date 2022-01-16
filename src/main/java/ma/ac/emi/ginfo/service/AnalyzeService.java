package ma.ac.emi.ginfo.service;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

@Service
public interface AnalyzeService {
	
	int determineScore(ArrayList<String> tokens,
			ArrayList<String> highPos,ArrayList<String> mediumPos,ArrayList<String> lowPos,
			ArrayList<String> highNeg,ArrayList<String> mediumNeg,ArrayList<String> lowNeg,
			ArrayList<String> negationWords
			);
	String getSentiment(int score);

	

}
