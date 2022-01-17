package ma.ac.emi.ginfo.service;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalyzeServiceImpl implements AnalyzeService{
	

	
	


	

	private int checkForPos(String token,ArrayList<String> highPos,ArrayList<String> mediumPos,ArrayList<String> lowPos) {
		int score = 0;
		token = token.strip();
		if( token.equals("")) {
			return 0;
		}
		for(String highPosString: highPos) {
			
			if(highPosString.equals(token)) {
				score += 3;
			}
		}
		for(String mediumPosString: mediumPos) {
			if(mediumPosString.equals(token)) {
				score += 2;
			}
		}
		for(String lowPosString: lowPos) {
			if(lowPosString.equals(token)) {
				score += 1;
			}
		}
		
		return score;
	}
	
	private int checkForNeg(String token,ArrayList<String> highNeg,ArrayList<String> mediumNeg,ArrayList<String> lowNeg) {
		int score = 0;
//		System.out.println("***************");
		token  = token.strip();
		if( token.equals("")) {
			return 0;
		}
		for(String highNegString: highNeg) {
			if(highNegString.equals(token)) {
			
				score -= 1;
			}
		}
		for(String mediumNegString: mediumNeg) {
			if(mediumNegString.equals(token)) {
		
				score -= 4;
			}
		}
		for(String lowNegString: lowNeg) {
			if(lowNegString.equals(token)) {
			
				score -= 7;
			}
		}
		return score;
	}
	
	private int negationWordsChecker(String nextToken,
			ArrayList<String> highPos,ArrayList<String> mediumPos,ArrayList<String> lowPos,
			ArrayList<String> highNeg,ArrayList<String> mediumNeg,ArrayList<String> lowNeg
			
			) {
		int score = 0;
		if (checkForPos(nextToken,highPos,mediumPos,lowPos) != 0) {
			score -= 1;
		}else if(checkForNeg(nextToken,highNeg,mediumNeg,lowNeg) != 0) {
			score += 1;
		}else {
			score -= 1;
		}
		return score;
	}
	
	@Override
	public int determineScore(ArrayList<String> tokens,
			ArrayList<String> highPos,ArrayList<String> mediumPos,ArrayList<String> lowPos,
			ArrayList<String> highNeg,ArrayList<String> mediumNeg,ArrayList<String> lowNeg,
			ArrayList<String> negationWords
			){
       int score = 0;

	       for(int j=0; j<tokens.size();j++) {
	    	   if(negationWords.contains(tokens.get(j))) {
	    		   score += negationWordsChecker(tokens.get(j+1),highPos,mediumPos,lowPos,
	    				   highNeg,mediumNeg,lowNeg); 
	    	   }
	    	   score += checkForPos(tokens.get(j),highPos,mediumPos,lowPos);
	    	   score += checkForNeg(tokens.get(j),highNeg,mediumNeg,lowNeg);
//	    	   System.out.println("NEGATIVES : "+checkForNeg(tokens.get(j),highNeg,mediumNeg,lowNeg));
	    	   	   
	       }
        return score;
    }
	
	
	@Override
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
