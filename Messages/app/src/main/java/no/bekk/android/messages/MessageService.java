package no.bekk.android.messages;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

public interface MessageService {
    @GET("/message")
    void fetchMessages(Callback<List<Message>> callback);

    @POST("/message")
    void send(@Body Message message, Callback<Message> callback);
}
