package ca.mylambton.c0695372.recipesapp.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by moxdroid on 2017-07-31.
 */

public class ApiClient
{
    private static String key = "9d5eca20a103462c1fc2c3653ac6072a";
    private static final String BASE_URL = "http://food2fork.com";
    private static Retrofit retrofit = null;

    public static Retrofit getClient()
    {
        if (retrofit==null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
