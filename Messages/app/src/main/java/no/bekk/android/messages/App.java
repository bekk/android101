package no.bekk.android.messages;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class App {

    private static MessageService messageService;

    public static MessageService getMessageService() {
        if (messageService == null) {
            final Gson gson = Converters.registerDateTime(new GsonBuilder()).create();
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("http://ios-course.herokuapp.com")
                    .setConverter(new GsonConverter(gson))
                    .build();
            messageService = restAdapter.create(MessageService.class);
        }
        return messageService;
    }
}
