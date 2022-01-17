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
	public ArrayList<String> processTextInput(String text) throws  StringIndexOutOfBoundsException{

        // simulation : this is out input comment and now we gonna process it
        //in order to provide the right token


        ArrayList<String> all_tokens = new ArrayList<String>();


     

        text = safarService.normalize(text);

        text = safarService.removeStepWords(text);



        if(text.isEmpty() ){
            text += ",";
        }
        try{
            List<WordLemmatizerAnalysis> lemmas = safarService.lemmatize(text);
            for(WordLemmatizerAnalysis wordAnalysis : lemmas){
            	
//                all_tokens.add(safarService.normalize(wordAnalysis.getStandardAnalysisList().get(0).getLemma()));
            	all_tokens.add(wordAnalysis.getInputWord());

            }
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Comment is "+text);
        }catch (IndexOutOfBoundsException e){
            System.out.println("Comment is "+text);
        }




//        System.out.println("ALL TOKENS : "+all_tokens);
        return all_tokens;
    }
	

}
