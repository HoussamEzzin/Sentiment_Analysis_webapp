package ma.ac.emi.ginfo.service;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

@Service
public interface DatasetService {
	
	void populateDataservice() throws IOException;
	
	ArrayList<String> getHighPos();
	ArrayList<String> getMediumPos();
	ArrayList<String> getLowPos();
	ArrayList<String> getHighNeg();
	ArrayList<String> getMediumNeg();
	ArrayList<String> getLowNeg();
	ArrayList<String> getNegationWords();
}
