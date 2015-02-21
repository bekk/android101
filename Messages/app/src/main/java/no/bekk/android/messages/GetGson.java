package no.bekk.android.messages;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GetGson {
    private static Gson gson;

    public static Gson getInstance() {
        if (gson == null) {
            gson = Converters.registerDateTime(new GsonBuilder()).create();
        }
        return gson;
    }
}
