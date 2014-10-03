package no.bekk.android.messages;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface MessageService {
    @GET("/message")
    void fetchMessages(Callback<List<Message>> callback);

    @POST("/message")
    void send(@Body Message message, Callback<Message> callback);

    @DELETE("/message/{id}")
    void delete(@Path("id") String id, Callback<String> callback);
}
