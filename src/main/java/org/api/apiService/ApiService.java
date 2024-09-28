package org.api.apiService;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.api.Main;
import org.api.movieDto.MovieDto;
import org.api.movieDto.MovieResponse;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ApiService {

    MovieResponse MovieResponse = new MovieResponse();
    MovieDto movieDto = new MovieDto();
   
    

    public MovieResponse getMovie(String movieName) throws IOException, InterruptedException {

        String encodedTitle = URLEncoder.encode(movieName, StandardCharsets.UTF_8.toString());

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://api.themoviedb.org/3/search/movie?query="+encodedTitle+"&api_key=###")).build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        MovieResponse = mapper.readValue(response.body(), MovieResponse.class);
        
        List<MovieDto> movies = MovieResponse.getResults();
        for (MovieDto movie: movies) {
            
            System.out.println("\nTittle: \n"+movie.getTitle());
            System.out.println("\nDescription: \n"+movie.getOverview());
            System.out.println("\nLanguage: \n"+movie.getOriginal_language());
            System.out.println("\nPoster path"+movie.getPoster_path());
            System.out.println("---------------------------------------------");
        }

        return MovieResponse ;
    }

}
