package ma.ac.emi.ginfo.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

@Service
public interface ProcessTextService {
	
	ArrayList<String> processText(String input);
	void setSafarService(SafarService safarService);

}
