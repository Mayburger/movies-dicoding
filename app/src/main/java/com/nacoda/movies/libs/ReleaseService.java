package com.nacoda.movies.libs;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nacoda.movies.R;
import com.nacoda.movies.model.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

import static com.nacoda.movies.libs.Constants.API_KEY;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ReleaseService extends JobService {

    public static final String TAG = ReleaseService.class.getSimpleName();

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "onStartJob() Executed");
        getReleases(params);

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "onStopJob() Executed");
        return true;
    }

    private void getReleases(final JobParameters job) {
        Log.d(TAG, "Running");
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.themoviedb.org/3/movie/upcoming?api_key=" + API_KEY + "&language=" + getApplicationContext().getString(R.string.api_language);
        Log.e(TAG, "getReleases: " + url);
        client.get(url, new AsyncHttpResponseHandler() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                Log.d(TAG, result);

                ArrayList<Movie> data = new ArrayList<>();

                try {
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movies = list.getJSONObject(i);
                        Movie movie = new Movie(movies);
                        data.add(movie);
                    }

                    Date todayDate = Calendar.getInstance().getTime();
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    String date = df.format(todayDate);

                    for (int i = 0; i < data.size(); i++) {
                        if (date.equals(data.get(i).getRelease_date())) {
                            showNotification(getApplicationContext(), "Today's Releases", data.get(i).getTitle() + " is released today!", i + 1);
                        }
                    }


                    jobFinished(job, false);
                } catch (Exception e) {

                    jobFinished(job, true);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                jobFinished(job, true);
            }
        });
    }

    private void showNotification(Context context, String title, String message, int notifId) {
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.movieiconvector)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.black))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        notificationManagerCompat.notify(notifId, builder.build());
    }
}