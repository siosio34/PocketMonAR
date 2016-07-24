package com.ar.siosi.swmaestrobackendproject;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Demonstrate Firebase Authentication using a Google ID Token.
 */
public class GoogleSignInActivity extends BaseActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    private PoketMonTrainer poketMonTrainer = new PoketMonTrainer();
    public static Activity firstActivity;

    private DatabaseReference mDatabase;

    // [END declare_auth]

    private FirebaseUser user;

    //  private FirebaseDatabase database;
    //private DatabaseReference mDatabase;

    // [START declare_auth_listener]
    private FirebaseAuth.AuthStateListener mAuthListener;
    // [END declare_auth_listener]

    private GoogleApiClient mGoogleApiClient;
    private TextView mStatusTextView;
    private TextView mDetailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google);

        firstActivity = GoogleSignInActivity.this;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        // [START auth_state_listener]
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();

                if (user != null) { // 로그인 성공시
                    checkBeforeUserData(); // 이전 데이터 있는지 여부를 판단한다.
                } else { // 로그인 실패시 또는 로그아웃
                    // User is signed out
                    signIn();
                    //Toast.makeText(getApplicationContext(),"로그인 실패",Toast.LENGTH_LONG ).show();
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // [START_EXCLUDE]
                updateUI(user);
                // [END_EXCLUDE]
            }
        };
        // [END auth_state_listener]
    }

    public void checkBeforeUserData() {
        final String userId = user.getUid();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() { // 이 리스너 기능 잘 알아두자
                                                               @Override
                                                               public void onDataChange(DataSnapshot dataSnapshot) {

                                                                   PoketMonTrainer checkUser = null;
                                                                   checkUser = dataSnapshot.getValue(PoketMonTrainer.class);

                                                                   // [START_EXCLUDE]
                                                                   if (checkUser == null) { // 새로운 유저정보 생성, 듀토리얼 페이지로 이동

                                                                       Intent i = new Intent(GoogleSignInActivity.this, Tutorial.class);
                                                                       startActivityForResult(i, 0);

                                                                   } else { // 기존 정보에서 불러오기 , id 가 있으니 여기서 무조건 되야 할텐데
                                                                       // Write new post
                                                                       Log.i("기존 정보 호출함수 열람", "User " + userId + " is unexpectedly null");
                                                                       loadBeforeUserData(userId,dataSnapshot.getChildrenCount()); // userid 에 있는 자식들의 숫자
                                                                       startActivity(new Intent(GoogleSignInActivity.this,GameMain.class));
                                                                   }

                                                               }

                                                               @Override
                                                               public void onCancelled(DatabaseError databaseError) {

                                                               }
                                                           }
        );
    }

    // [START write_fan_out]
    private void loadBeforeUserData(String userId,Long countNum) { // 이전데이터 불러오기

        Query queryRef = mDatabase.child("users").child(userId).orderByChild("totalGamePlayTime").limitToLast(1); // 유저정봊중 제일 최신정보들고오기
        queryRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) { // 데이터 반환 및 데이터 추가되었을때
                PoketMonTrainer.currentTrainer = dataSnapshot.getValue(PoketMonTrainer.class);
                Log.i("기존 포켓몬 정보 반환", PoketMonTrainer.currentTrainer.nickName);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { // 유저 정보가 변환되었을때

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { // 정보가 삭제되었을때

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { // 정보가 이동 되었을때

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    // [END write_fan_out]


    // [START on_start_add_listener]
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    // [END on_start_add_listener]

    // [START on_stop_remove_listener]
    @Override
    public void onStop() {
        super.onStop();

        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    // [END on_stop_remove_listener]

    // [START onactivityresult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }
        } else if (requestCode == 0) { // 듀토리얼 끝나고 돌아왔을때

            if (data != null) { // 정보를 성공적으로 받을시, 듀토리얼에서 생성
                String nickName = data.getStringExtra("nickName");
                int pocketCode = Integer.parseInt(data.getStringExtra("code"));

                PoketMonTrainer.currentTrainer = poketMonTrainer.makeTrainerData(user, nickName, pocketCode); // 유저정보, 닉네임, 포켓몬 코드로 트레이너 정보를 생성
                Toast.makeText(getApplicationContext(), PoketMonTrainer.currentTrainer.nickName + " 유저님 환영합니다.", Toast.LENGTH_SHORT).show();

                Intent gameMainIntent = new Intent(GoogleSignInActivity.this, GameMain.class); // 게임 메인 액티비티로 전환.
                startActivity(gameMainIntent);
                finish();

            } else {

            }

        }

    }

    public void checkAuthUser() {
        if (user != null) {
            Intent i = new Intent(GoogleSignInActivity.this, Tutorial.class);
            startActivityForResult(i, 0);
        } else {

        }
    }


    // [END onactivityresult]

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
        showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(GoogleSignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END auth_with_google]

    // [START signin]
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }
    // [END signin]

    private void signOut() {

        mAuth.signOut();

        // Google sign out
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        updateUI(null);
                    }
                });
    }

    private void revokeAccess() {
        // Firebase sign out
        mAuth.signOut();

        // Google revoke access
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        updateUI(null);
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {

            for (UserInfo profile : user.getProviderData()) {
                // Id of the provider (ex: google.com)
                String providerId = profile.getProviderId();

                // UID specific to the provider
                String uid = profile.getUid();

                // TODO: 2016-07-13
                // 디자인도 바꾸자.

                // Name, email address, and profile photo Url
                String name = profile.getDisplayName(); // 사용자의 이름을 보여준다.
                String email = profile.getEmail(); // 이건왜 null값이 ...?.
                Uri photoUrl = profile.getPhotoUrl(); // 사용자의 프로필 이미지 같다.
                // TODO: 2016-07-13
                // 나중에 이미지 띄우는거처리

                //database = FirebaseDatabase.getInstance();
                //mDatabase = database.getReference("users");
            }
            ;

        } else {

        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(getApplicationContext(), "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }


}
