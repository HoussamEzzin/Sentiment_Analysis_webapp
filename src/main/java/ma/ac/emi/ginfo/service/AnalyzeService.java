package ma.ac.emi.ginfo.service;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

@Service
public class AnalyzeService {
	
	DatasetService datasetService;
	
	public AnalyzeService() {
		try {
			this.datasetService = new DatasetService();
		} catch (IOException e) {
			// TODO: handle exception
			System.out.println("ERROR");
		}
	}
	
	public int checkForPos(String token) {
		int score = 0;
		
		if (datasetService.getHighPos().contains(token)){
			score += 3;
		}
		else if(datasetService.getMediumPos().contains(token)) {
			score += 2;
		}
		else if(datasetService.getLowPos().contains(token)) {
			score += 1;
		}
		return score;
	}
	
	public int checkForNeg(String token) {
		int score = 0;
		
		if (datasetService.getHighNeg()().contains(token)){
			score -= 3;
		}
		else if(datasetService.getMediumNeg()().contains(token)) {
			score -= 2;
		}
		else if(datasetService.getLowNeg()().contains(token)) {
			score -= 1;
		}
		return score;
	}
	
	public int negationWordsChecker(String nextToken) {
		int score = 0;
		if (checkForPos(nextToken) != 0) {
			score -= 1;
		}else if(checkForNeg(nextToken) != 0) {
			score += 1;
		}else {
			score -= 1;
		}
		return score;
	}
	
	public int determineScore(ArrayList<String> tokens){
        int score = 0;

       for(int j=0; j<tokens.size();j++) {
    	   if(datasetService.getNegationWords().contains(tokens.get(j))) {
    		   score += negationWordsChecker(tokens.get(j+1)); 
    	   }else {
    		   score += checkForPos(tokens.get(j));
    		   score += checkForNeg(tokens.get(j));
    	   }	   
       }
        return score;
    }
	
	public String getSentiment(int score) {
		String emotion;
		if(score > 0) {
			emotion = "Positive";
		}else if (score < 0) {
			emotion = "Negative";
		}else {
			emotion = "Neutral";
		}
		return emotion;
	}
}
