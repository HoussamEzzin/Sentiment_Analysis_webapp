package ma.ac.emi.ginfo.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Comment;
import com.google.api.services.youtube.model.CommentThread;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import com.google.api.services.youtube.model.CommentThreadReplies;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Lists;
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class HomeController {
	
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
                      .setModerationStatus("published")
                      .setTextFormat("plainText");
    }
    private static void handleCommentsThreads(List<CommentThread> commentThreads) {

        for (CommentThread commentThread : commentThreads) {
            List<Comment> comments = Lists.newArrayList();
            comments.add(commentThread.getSnippet().getTopLevelComment());

            CommentThreadReplies replies = commentThread.getReplies();
            if (replies != null)
                comments.addAll(replies.getComments());

            System.out.println("Found " + comments.size() + " comments.");

            // Do your comments logic here
            counter += comments.size();}
        }
	@GetMapping("/")
	public List<CommentThread> home() throws Exception {
//		YouTube youtubeService = getService();
        // Define and execute the API request
		String videoId = "Udg0dNnpBMw";
		
		List<String> allComments = new ArrayList<>();
		
		CommentThreadListResponse commentsPage = prepareListRequest(videoId).execute();
		
		
		while(true) {
			handleCommentsThreads(commentsPage.getItems());
			
			String nextPageToken = commentsPage.getNextPageToken();
			if(nextPageToken == null) {
				break;
			}
			
			commentsPage = prepareListRequest(videoId).setPageToken(nextPageToken).execute();
		}
		
		return commentsPage.getItems();
	}

}
