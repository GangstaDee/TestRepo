package command.impl;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import command.Command;

import java.io.IOException;
import java.util.List;

/**
 * Created on 20.12.2015.
 */
public class YoutubeSearchCommand extends Command {

    private static final String YOUTUBE_URL = "https://www.youtube.com/watch?v=";

    @Override
    public void execute() {

        YouTube youtube = new YouTube.Builder(new ApacheHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {

            @Override
            public void initialize(HttpRequest request) throws IOException {}
        }).setApplicationName("yesssss-its-my-first-youtube-search").build();

        try {
            YouTube.Search.List search = youtube.search().list("id");
            search.setKey(retrieveToken());
            search.setQ(text);
            search.setType("video");
            search.setMaxResults(Long.valueOf(10));

            SearchListResponse response = search.execute();
            List<SearchResult> items = response.getItems();
            if (items != null && items.size() > 0) {
                botApi.sendMessage(chatID, YOUTUBE_URL + items.get(0).getId().getVideoId());
            }

        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    private String retrieveToken() {
        //here should be some super secure reading instead
        return "###############";
    }


}