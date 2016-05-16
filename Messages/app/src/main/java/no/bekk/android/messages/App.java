package no.bekk.android.messages;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

public class App {

    private static MessageService messageService;

    public static MessageService getMessageService() {
        if (messageService == null) {
            final Gson gson = Converters.registerDateTime(new GsonBuilder()).create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://mobile-course.herokuapp.com")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            messageService = retrofit.create(MessageService.class);
        }
        return messageService;
    }
}
