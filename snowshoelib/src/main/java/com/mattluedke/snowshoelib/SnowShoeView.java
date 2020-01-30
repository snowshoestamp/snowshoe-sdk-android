package com.mattluedke.snowshoelib;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION_CODES;
import android.util.AttributeSet;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

public class SnowShoeView extends View {

  private static final int NUMBER_OF_STAMP_PTS = 5;

  private OnStampListener mOnStampListener;
  private Boolean mStampBeingChecked = false;
  private String mApiKey;

  public SnowShoeView(Context context) {
    super(context);
  }

  public SnowShoeView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public SnowShoeView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @TargetApi(VERSION_CODES.LOLLIPOP)
  public SnowShoeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  public void setApiKey(String key) {
    mApiKey = key;
  }

  public void setOnStampListener(OnStampListener listener) {
    mOnStampListener = listener;
  }

  public boolean onTouchEvent(MotionEvent event) {

    int action = event.getActionMasked();
    int pointerCount = event.getPointerCount();
    if (action == MotionEvent.ACTION_POINTER_DOWN) {
      if (pointerCount == NUMBER_OF_STAMP_PTS) {
        if (!mStampBeingChecked) {
          mStampBeingChecked = true;

          List<List<Float>> requestData = new ArrayList<>(NUMBER_OF_STAMP_PTS);

          for (int i = 0; i < NUMBER_OF_STAMP_PTS; i++) {
            List<Float> pointData = new ArrayList<>(2);
            pointData.add(event.getX(i));
            pointData.add(event.getY(i));
            requestData.add(pointData);
          }

          Gson gson = new Gson();
          String stringData = gson.toJson(requestData);
          String base64data = Base64.encodeToString(stringData.getBytes(), Base64.DEFAULT);

          OnStampListener localStampListener = new OnStampListener() {
            @Override
            public void onStampRequestMade() {
              mOnStampListener.onStampRequestMade();
            }

            @Override
            public void onStampResult(StampResult result) {
              mOnStampListener.onStampResult(result);
              mStampBeingChecked = false;
            }
          };
          StampService stampService = new StampService(getContext(), mApiKey, localStampListener);
          stampService.getStampByTouchPoints(base64data);
        }
      }
    }
    return true;
  }

  //old v2 stuff, waiting to remove just in case.
//  private class DownloadFilesTask extends AsyncTask<List<List<Float>>, Void, String> {
//
//    @Override
//    protected void onPreExecute() {
//      super.onPreExecute();
//      if (mOnStampListener != null) {
//        mOnStampListener.onStampRequestMade();
//      }
//    }
//
//    @Override
//    protected String doInBackground(List<List<Float>>... requestData) {
//
//      Gson gson = new Gson();
//      String stringData = gson.toJson(requestData[0]);
//      String base64data = Base64.encodeToString(stringData.getBytes(), Base64.DEFAULT);

////      OAuth10aService service = new ServiceBuilder(mAppKey)
////          .apiSecret(mAppSecret)
////          .build(new SnowShoeApi());
////
////      //Make a blank token to sign the request with.
////      final OAuth1AccessToken accessToken = new OAuth1AccessToken("", "");
////
////      try {
////        OAuthRequest request = new OAuthRequest(Verb.POST, API_URL);
////        request.addBodyParameter("data", base64data);
////        service.signRequest(accessToken, request);
////        Response response = service.execute(request);
////        return response.getBody();
////      }
////      catch (Exception e) {
////        e.printStackTrace();
////        return "";
////      }
//    }
//
//    protected void onPostExecute(String result) {
////      Gson gson = new GsonBuilder()
////          .setDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS") //  "2015-10-11 12:15:18.741769"
////          .create();
////      StampResult stampResult;
////      try {
////        stampResult = gson.fromJson(result, StampResult.class);
////      } catch (JsonSyntaxException jsonException) {
////        stampResult = new StampResult();
////        stampResult.error = new SnowShoeError();
////        stampResult.error.message = result;
////      }
////      mOnStampListener.onStampResult(stampResult);
////      mStampBeingChecked = false;
//    }
//
//  }
}
