package ma.ac.emi.ginfo.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
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
                    new InputStreamReader(new FileInputStream(file), "Windows-1256"));

            String str;

            while ((str = in.readLine()) != null) {
//                System.out.println(str);
                array.add(str);
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
		Resource highPosResource = loader.getResource("classpath:static/high_pos.txt");
		Resource mediumPosResource = loader.getResource("classpath:static/medium_pos.txt");
		Resource lowPosResource = loader.getResource("classpath:static/low_pos.txt");
		Resource highNegResource = loader.getResource("classpath:static/high_neg.txt");
		Resource mediumNegResource = loader.getResource("classpath:static/medium_neg.txt");
		Resource lowNegResource = loader.getResource("classpath:static/low_neg.txt");
		Resource negationWordsResource = loader.getResource("classpath:static/negationWords.txt");
		this.highPos = convertFileToArrayList(highPosResource.getFile());
		this.mediumPos = convertFileToArrayList(mediumPosResource.getFile());
		this.lowPos =convertFileToArrayList(lowPosResource.getFile());
		this.highNeg = convertFileToArrayList(highNegResource.getFile());
		this.mediumNeg = convertFileToArrayList(mediumNegResource.getFile());
		this.lowNeg = convertFileToArrayList(lowNegResource.getFile());
		this.negationWords = convertFileToArrayList(negationWordsResource.getFile());
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
