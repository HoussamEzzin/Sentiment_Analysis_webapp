package ma.ac.emi.ginfo.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
public class DatasetServiceImpl implements DatasetService {
	
	ResourceLoader loader;
	protected ArrayList<String> highPos = new ArrayList<String>();
	protected ArrayList<String> mediumPos = new ArrayList<String>();
	protected ArrayList<String> lowPos= new ArrayList<String>();
	protected ArrayList<String> highNeg= new ArrayList<String>();
	protected ArrayList<String> mediumNeg= new ArrayList<String>();
	protected ArrayList<String> lowNeg = new ArrayList<String>();
	protected ArrayList<String> negationWords= new ArrayList<String>();
	                                         
	

	
	private ArrayList<String> convertFileToArrayList(File file){
        ArrayList<String> array = new ArrayList<String>();
        //Read Positive File
        try {

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file), "UTF-8"));

            String str;

            while ((str = in.readLine()) != null) {
//                System.out.println(str);
                array.add(str.strip());
            }

            in.close();

        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        return array;

    }
	
	@Override
	public void populateDataservice() throws IOException {
		
			File highPosFile = new ClassPathResource("high_pos.txt").getFile();
			File mediumPosFile = new ClassPathResource("medium_pos.txt").getFile();
			File lowPosFile = new ClassPathResource("low_pos.txt").getFile();
			File highNegFile = new ClassPathResource("high_neg.txt").getFile();
			File mediumNegFile = new ClassPathResource("medium_neg.txt").getFile();
			File lowNegFile = new ClassPathResource("low_neg.txt").getFile();
			File negationWordsFile = new ClassPathResource("negationWords.txt").getFile();
		
			this.highPos = convertFileToArrayList(highPosFile);
			this.mediumPos = convertFileToArrayList(mediumPosFile);
			this.lowPos =convertFileToArrayList(lowPosFile);
			this.highNeg = convertFileToArrayList(highNegFile);
			this.mediumNeg = convertFileToArrayList(mediumNegFile);
			this.lowNeg = convertFileToArrayList(lowNegFile);
			this.negationWords = convertFileToArrayList(negationWordsFile);
			
			
		
		
	}
	

	
	
	@Override
	public ArrayList<String> getHighPos() {
		return highPos;
	}

	public void setHighPos(ArrayList<String> highPos) {
		this.highPos = highPos;
	}
	@Override
	public ArrayList<String> getMediumPos() {
		return mediumPos;
	}

	public void setMediumPos(ArrayList<String> mediumPos) {
		this.mediumPos = mediumPos;
	}
	@Override
	public ArrayList<String> getLowPos() {
		return lowPos;
	}

	public void setLowPos(ArrayList<String> lowPos) {
		this.lowPos = lowPos;
	}
	@Override
	public ArrayList<String> getHighNeg() {
		return highNeg;
	}

	public void setHighNeg(ArrayList<String> highNeg) {
		this.highNeg = highNeg;
	}
	@Override
	public ArrayList<String> getMediumNeg() {
		return mediumNeg;
	}

	public void setMediumNeg(ArrayList<String> mediumNeg) {
		this.mediumNeg = mediumNeg;
	}
	@Override
	public ArrayList<String> getLowNeg() {
		return lowNeg;
	}
	
	public void setLowNeg(ArrayList<String> lowNeg) {
		this.lowNeg = lowNeg;
	}
	@Override
	public ArrayList<String> getNegationWords() {
		return negationWords;
	}

	public void setNegationWords(ArrayList<String> negationWords) {
		this.negationWords = negationWords;
	}
	
	
	
	

	
	
	

}
