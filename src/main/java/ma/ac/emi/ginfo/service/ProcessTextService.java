package ma.ac.emi.ginfo.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

@Service
public interface ProcessTextService {
	

	void setSafarService(SafarService safarService);
	ArrayList<String> processTextInput(String text) throws StringIndexOutOfBoundsException;

}
