package ma.ac.emi.ginfo.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Comment;
import com.google.api.services.youtube.model.CommentThread;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import com.google.api.services.youtube.model.CommentThreadReplies;
import com.kenai.jaffl.annotations.In;

import ma.ac.emi.ginfo.service.AnalyzeService;
import ma.ac.emi.ginfo.service.DatasetService;
import ma.ac.emi.ginfo.service.ProcessTextService;
import ma.ac.emi.ginfo.service.SafarService;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Lists;
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class HomeController {
	
	
	@Autowired
	DatasetService datasetService;
	
	@Autowired
	ProcessTextService processTextService;
	
	@Autowired
	AnalyzeService analyzeService;
	@Autowired
	SafarService safarService;
	private static final String DEVELOPER_KEY ="AIzaSyD2-CXZu9MJ04hX6-dYohzaFvCBzmsSpWo";
    private static final String APPLICATION_NAME = "Sentiment Analysis";

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static int counter = 0;
    /**
     * Build and return an authorized API client service.
     *
     * @return an authorized API client service
     * @throws GeneralSecurityException, IOException
     */
    public static YouTube getService() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new YouTube.Builder(httpTransport, JSON_FACTORY, null)
            .setApplicationName(APPLICATION_NAME)
            .build();
    }
    private static YouTube.CommentThreads.List prepareListRequest(String videoId) throws Exception {
    	YouTube youtubeService = getService();
    	List<String> part= new ArrayList<String>();
		part.add("snippet");
        return youtubeService.commentThreads()
                      .list(part)
                      .setKey(DEVELOPER_KEY)
                      .setVideoId(videoId)
                      .setMaxResults(100L)
                      .setTextFormat("plainText");
    }
    private static void handleCommentsThreads(List<CommentThread> commentThreads,List<String> allComments) {

        for (CommentThread commentThread : commentThreads) {
            List<Comment> comments = Lists.newArrayList();
            comments.add(commentThread.getSnippet().getTopLevelComment());

//            CommentThreadReplies replies = commentThread.getReplies();
//            if (replies != null)
//                comments.addAll(replies.getComments());

//            System.out.println("Found " + comments.size() + " comments.");

            // Do your comments logic here
            counter += comments.size();
            allComments.add(commentThread.getSnippet().getTopLevelComment().getSnippet().getTextDisplay());
            
        }
        
        }
    
	@GetMapping("/")
	public List<String> home(@RequestParam String videoId) throws Exception {
		Integer score = 0;
		int commentScore = 0;
		Integer countPos = 0;
		Integer countNeg = 0;
		Integer countNeutral = 0;
		List<Integer> results = new ArrayList<Integer>();
		String emotion = "none";
//		YouTube youtubeService = getService();
        // Define and execute the API request
//		String videoId = "PV58v_nj7xM";

		
		datasetService.populateDataservice();
		processTextService.setSafarService(safarService);
		
//		System.out.println(datasetService.getHighNeg());
		
		List<String>  allComments = new ArrayList<>();
		
		CommentThreadListResponse commentsPage = prepareListRequest(videoId).execute();
		
		List<String> allCommentsText = new ArrayList<>();
		
		
		while(true) {
			handleCommentsThreads(commentsPage.getItems(),allCommentsText);
			
			String nextPageToken = commentsPage.getNextPageToken();
			if(nextPageToken == null) {
				break;
			}
			
			commentsPage = prepareListRequest(videoId).setPageToken(nextPageToken).execute();
			
		}
		int k = 0;
		List<String> commentWithEmotion = new ArrayList<>();
		for(String comment_text: allCommentsText) {
			if( k == 7 ) {
				break;
			}
			System.out.println(k+"/"+allCommentsText.size());
			
			
			k++;
			if(k<6) {
				commentWithEmotion.add(comment_text);
			}
			
			ArrayList<String> tokens = processTextService.processTextInput(comment_text);
			if(k==3) {
				for(String token: tokens) {
					System.out.println(token);
				}
			}
//			for(String token : tokens) {
//				commentWithEmotion.add(token);
//			}
//			System.out.println("TOKENS :"+tokens+"// SCORE :"+analyzeService.determineScore(tokens, datasetService.getHighPos(), 
//					datasetService.getMediumPos(), datasetService.getLowPos(), 
//					datasetService.getHighNeg(), datasetService.getMediumNeg(), 
//					datasetService.getLowNeg(), datasetService.getNegationWords()));
			commentScore = analyzeService.determineScore(tokens, datasetService.getHighPos(), 
					datasetService.getMediumPos(), datasetService.getLowPos(), 
					datasetService.getHighNeg(), datasetService.getMediumNeg(), 
					datasetService.getLowNeg(), datasetService.getNegationWords());
			score += commentScore;
			if(k<6) {
				emotion = analyzeService.getSentiment(commentScore);
				commentWithEmotion.add(emotion);
			}
			
			if(commentScore > 0) {
				countPos++;
			}else if (commentScore < 0) {
				countNeg++;
			}else {
				countNeutral++;
			}
			
			
			System.out.println("ACTUAL SCORE :"+commentScore);
	}
		
		int posPourcentage = (countPos*100)/allCommentsText.size();
		int negPourcentage = (countNeg*100)/allCommentsText.size();
		int neutralPourcentage =(countNeutral*100)/allCommentsText.size() ;
//		results.add(posPourcentage);
//		results.add(negPourcentage);
//		results.add(neutralPourcentage);
//		allComments.put(commentWithEmotion, results);
		commentWithEmotion.add(String.valueOf(posPourcentage));
		commentWithEmotion.add(String.valueOf(negPourcentage));
		commentWithEmotion.add(String.valueOf(neutralPourcentage));
		

		return commentWithEmotion;}

}
