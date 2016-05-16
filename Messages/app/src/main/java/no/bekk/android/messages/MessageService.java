package no.bekk.android.messages;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MessageService {
    @GET("/message")
    Call<List<Message>> fetchMessages();

    @POST("/message")
    Call<Message> send(@Body Message message);

    @DELETE("/message/{id}")
    Call<String> delete(@Path("id") String id);

    @PUT("/message/{id}")
    Call<Message> update(@Body Message message, @Path("id") String id);
}
