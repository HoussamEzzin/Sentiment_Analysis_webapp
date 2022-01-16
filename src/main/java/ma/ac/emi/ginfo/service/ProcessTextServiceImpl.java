package ma.ac.emi.ginfo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import safar.modern_standard_arabic.basic.morphology.lemmatizer.model.LemmatizerAnalysis;
import safar.modern_standard_arabic.basic.morphology.lemmatizer.model.WordLemmatizerAnalysis;

@Service
public class ProcessTextServiceImpl implements ProcessTextService {
	@Autowired
	SafarService safarService;
	
	@Autowired
	@Override
	public void setSafarService(SafarService safarService) {
		this.safarService = safarService;
	}
	@Override
	public ArrayList<String> processText(String input){
        ArrayList<String> result = new ArrayList<String>();
        
      
//        Normalizer normalizer = new Normalizer();
//        Lemmatizer lemmatizer = new Lemmatizer();
//        RemoveStepWords removeStepWords = new RemoveStepWords();

        input = safarService.normalize(input);
        input = safarService.removeStepWords(input);

        if(input.isEmpty()){
            input += ',';
        }try {
            try{
                List<WordLemmatizerAnalysis> lemmas = safarService.lemmatize(input);
                for(WordLemmatizerAnalysis wordAnalysis : lemmas){
                    for(LemmatizerAnalysis analysis : wordAnalysis.getStandardAnalysisList()){
                        String token = safarService.normalize(analysis.getLemma());
                        result.add(token);
                    }
                }
            }catch (NullPointerException e){
                System.out.println("Null Pointer Exception , Comment : "+input);
            }

        }catch (IndexOutOfBoundsException e){
            System.out.println("Comment : "+input);
        }


        return result;
    }
	

}
