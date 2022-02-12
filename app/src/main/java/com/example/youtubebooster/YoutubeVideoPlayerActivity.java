package com.example.youtubebooster;



import android.accounts.AccountManager;
import android.app.Dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.youtube.YouTubeScopes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import java.util.concurrent.TimeUnit;

import pub.devrel.easypermissions.EasyPermissions;

public class YoutubeVideoPlayerActivity extends YouTubeBaseActivity implements EasyPermissions.PermissionCallbacks,YouTubeActivityView{

    // if you are using YouTubePlayerView in xml then activity must extend YouTubeBaseActivity

    private static final int RECOVERY_DIALOG_REQUEST = 1;
    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;
    private static final int RC_SIGN_IN = 12311;

    private static final String PREF_ACCOUNT_NAME = "accountName";;
    private GoogleAccountCredential mCredential;
    private ProgressDialog pDialog;
    private YouTubeActivityPresenter presenter;
    private int counter = 0;


    Bundle bundle;
    TextView y_tube_title;
    YouTubePlayerView ytPlayer;
   public YouTubePlayer youTubePlayer1;
   DatabaseReference insertMoneyRef;
    String getUserId;
    double total_money;
    String finalMoney;
    String getEmailsignedin;
    YoutubeConfig youtubeConfig;
    String channel_id_1;
    Gettitle gettitle1;
    //GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_video_player);
         youtubeConfig = new YoutubeConfig();
        // initialize presenter
        presenter = new YouTubeActivityPresenter(this,this);

        // Get reference to the view of Video player
        insertMoneyRef = FirebaseDatabase.getInstance().getReference().child("Users");
        String youVideoid = getIntent().getStringExtra("video_id1");
        channel_id_1 = getIntent().getStringExtra("channel_id_g");
        ytPlayer = (YouTubePlayerView) findViewById(R.id.you_tube_player);
        GoogleSignInAccount  signInAccount = GoogleSignIn.getLastSignedInAccount(YoutubeVideoPlayerActivity.this);
        getEmailsignedin = signInAccount.getEmail().toString();
        bundle = new Bundle();
        ytPlayer.initialize(youtubeConfig.getYOUTUBE_API_KEY(),
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(
                            YouTubePlayer.Provider provider,
                            YouTubePlayer youTubePlayer, boolean b) {

                        youTubePlayer1 = youTubePlayer;
                        youTubePlayer1.cueVideo(youVideoid);
                        youTubePlayer1.play();
                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult
                                                                youTubeInitializationResult) {
                        Toast.makeText(getApplicationContext(), "Video player Failed", Toast.LENGTH_SHORT).show();
                    }
                });

       // final String emailId = getIntent().getExtras().getString(RegisterActivity.USER_EMAIL);

        mCredential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(YouTubeScopes.YOUTUBE))
                .setBackOff(new ExponentialBackOff());


        findViewById(R.id.y_subscribe_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*              FIRST GOTO FOLLOWING LINK AND ENABLE THE YOUTUBE API ACCESS
                https://console.developers.google.com/apis/api/youtube.googleapis.com/overview?project=YOUR_PROJECT_ID**/
                SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(PREF_ACCOUNT_NAME, getEmailsignedin);
                editor.apply();
                getResultsFromApi();
            }
        });

    }



    @Override
    public void onBackPressed() {
        int secsOfthirty = 30;
        long timeMillis =TimeUnit.MILLISECONDS.toSeconds(youTubePlayer1.getCurrentTimeMillis());
        super.onBackPressed();
        getUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            insertMoneyRef.child(getUserId).child("money_to_paid").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String money_collected_from_database = dataSnapshot.getValue(String.class);
                    float money_Collected = Float.parseFloat(money_collected_from_database);
                    if(timeMillis >= secsOfthirty){
                        Intent intent = new Intent(YoutubeVideoPlayerActivity.this,HomePageActivity.class);
                         total_money = money_Collected + 0.50;
                         Locale locale = new Locale("en","GH");
                         NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
                         finalMoney = numberFormat.format(total_money);
                         insertMoneyRef.child(getUserId).child("money_to_paid").setValue(finalMoney);
                        //here we will get the
                        startActivity(intent);
                        finish();
                }
                    if(timeMillis <= secsOfthirty) {
                        Intent intent = new Intent(YoutubeVideoPlayerActivity.this,HomePageActivity.class);
                        total_money = money_Collected;
                        Locale locale = new Locale("en","GH");
                        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
                        finalMoney = numberFormat.format(total_money);
                        insertMoneyRef.child(getUserId).child("money_to_paid").setValue(finalMoney);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
    }

    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.you_tube_player);
    }


    private void getResultsFromApi() {

        if (! isGooglePlayServicesAvailable())
        {
            acquireGooglePlayServices();
        }
        else if (mCredential.getSelectedAccountName() == null)
        {
            chooseAccount();
        }
        else
        {
            pDialog = new ProgressDialog(YoutubeVideoPlayerActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.show();
            // handing subscribe task by presenter
            presenter.subscribeToYouTubeChannel(mCredential,channel_id_1); // pass youtube channelId as second parameter
        }

    }

    // checking google play service is available on phone or not
    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        final int connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(this);
        return connectionStatusCode == ConnectionResult.SUCCESS;
    }


    private void acquireGooglePlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        final int connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(this);

        if (apiAvailability.isUserResolvableError(connectionStatusCode))
        {
            Dialog dialog = apiAvailability.getErrorDialog(
                    YoutubeVideoPlayerActivity.this,  // showing dialog to user for getting google play service
                    connectionStatusCode,
                    REQUEST_GOOGLE_PLAY_SERVICES);
            dialog.show();
        }
    }


    private void chooseAccount() {

        if (EasyPermissions.hasPermissions(this, android.Manifest.permission.GET_ACCOUNTS))
        {
            String accountName = getPreferences(Context.MODE_PRIVATE).getString(PREF_ACCOUNT_NAME, null);
            if (accountName != null)
            {
                mCredential.setSelectedAccountName(accountName);
                getResultsFromApi();
            }
            else
            {
                // Start a dialog from which the user can choose an account
                startActivityForResult(mCredential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
            }
        }
        else
        {
            // Request the GET_ACCOUNTS permission via a user dialog
            EasyPermissions.requestPermissions(
                    this,
                    "This app needs to access your Google account for YouTube channel subscription.",
                    REQUEST_PERMISSION_GET_ACCOUNTS, android.Manifest.permission.GET_ACCOUNTS);
        }

    }
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case RECOVERY_DIALOG_REQUEST: // on YouTube player initialization error
                getYouTubePlayerProvider().initialize(youtubeConfig.getYOUTUBE_API_KEY(), (YouTubePlayer.OnInitializedListener) this);
                break;

            case REQUEST_GOOGLE_PLAY_SERVICES: // if user don't have google play service
                if (resultCode != RESULT_OK)
                {
                    Toast.makeText(this, "This app requires Google Play Services. Please " +
                            "install Google Play Services on your device and relaunch this app.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    getResultsFromApi();
                }
                break;

            case REQUEST_ACCOUNT_PICKER: // when user select google account
                if (resultCode == RESULT_OK && data != null && data.getExtras() != null)
                {
                    String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null)
                    {
                        SharedPreferences settings =
                                getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.apply();
                        mCredential.setSelectedAccountName(accountName);
                        getResultsFromApi();
                    }
                }
                break;


            case REQUEST_AUTHORIZATION: // when your grant account access permission
                if (resultCode == RESULT_OK)
                {
                    getResultsFromApi();
                }
                break;


            case RC_SIGN_IN: // if user do sign in
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                if (result.isSuccess())
                {
                    getResultsFromApi();
                }
                else
                {
                    Toast.makeText(this, "Permission Required if granted then check internet connection", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        getResultsFromApi(); // user have granted permission so continue
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Toast.makeText(this, "This app needs to access your Google account for YouTube channel subscription.", Toast.LENGTH_SHORT).show();

    }




    @Override  // responce from presenter on success
    public void onSubscribetionSuccess(String title) {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
        Toast.makeText(YoutubeVideoPlayerActivity.this, "Successfully subscribe to "+title, Toast.LENGTH_SHORT).show();
    }

    @Override // responce from presenter on failure
    public void onSubscribetionFail() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
        // user don't have youtube channel subscribe permission so grant it form him
        // as we have not taken at the time of sign in
        if(counter < 3)
        {
            counter++; // attempt three times on failure
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .requestScopes(new Scope("https://www.googleapis.com/auth/youtube")) // require this scope for youtube channel subscribe
                    .build();

            GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }
        else
        {
            Toast.makeText(this, "goto following link and enable the youtube api access\n" +
                            "https://console.developers.google.com/apis/api/youtube.googleapis.com/overview?project=YOUR_PROJECT_ID",
                    Toast.LENGTH_LONG).show();
        }


    }


}
