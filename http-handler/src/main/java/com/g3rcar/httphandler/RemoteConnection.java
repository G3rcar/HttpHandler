package com.g3rcar.httphandler;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gerardo on 20/07/15.
 * Class to help create remote connections to a server
 * It support GET, POST and DELETE request as HTTP to work with RESTful APIs
 */
public class RemoteConnection extends AsyncTask<Void, Void, String> {

    private static final String TAG = "http-handler Library";

    private String url;                             // Request URL
    private String urlParams = "";                  // Request URL params
    private boolean isPost = false;                 // Is the request a POST one?
    private boolean isDelete = false;               // Is the request a DELETE one?
    private List<PostValue> postValues = null;      // List of POST values, if is POST
    private List<PostValue> headerValues = null;    // List of Header values
    private int requestId = 0;                      // Request ID
    private Context context = null;
    private OnRemoteConnectionListener listener;

    private int statusCode = 200;

    public RemoteConnection(){}

    public RemoteConnection(Context context){
        this.context = context;
    }

    /**
     * Set the request id as int to use in the interface
     * @param id int as an id
     */
    public void setRequestId(int id){
        this.requestId = id;
    }

    /**
     * Set the request base url as String (Without GET params)
     * @param url String as the URL
     */
    public void setURL(String url){
        this.url = url;
    }

    /**
     * Set the url GET params added at the end of the base URL
     * @param params String as the URL params
     */
    public void setURLParams(String params){
        this.urlParams = params;
    }

    /**
     * Used to set the request as POST
     * @param isPost boolean
     */
    public void isPost(boolean isPost){
        this.isPost = isPost;
    }

    /**
     * Used to set the request as DELETE
     * @param isDelete boolean
     */
    public void isDelete(boolean isDelete){
        this.isPost = !isDelete;
        this.isDelete = isDelete;
    }

    /**
     * Set the post params to send. This automatically set the request as a Post one
     * @param postValues array list for values
     */
    public void setPostParams(List<PostValue> postValues){
        this.isPost(true);
        this.postValues = postValues;
    }

    /**
     * Get the post params to send
     * @return List<PostValue> array list for values
     */
    public List<PostValue> getPostParams(){
        return this.postValues;
    }

    /**
     * Set the header params to send
     * @param headerValues array list for values
     */
    public void setHeaderParams(List<PostValue> headerValues){
        this.headerValues = headerValues;
    }

    /**
     * Get the header params to send
     * @return List<PostValue> array list for values
     */
    public List<PostValue> getHeaderParams(){
        return this.headerValues;
    }

    /**
     * Set the listener that has implemented the interface {@link OnRemoteConnectionListener}
     * @param listener OnRemoteConnectionListener implemented
     */
    public void setOnRemoteConnectionListener(OnRemoteConnectionListener listener) {
        this.listener = listener;
    }

    /**
     * Return the Context
     * @return Context
     */
    public Context getContext(){
        return this.context;
    }








    @Override
    protected String doInBackground(Void... params) {
        return executeRequest();
    }

    protected void onPostExecute(String content) {
        if(this.statusCode==200){
            listener.connectionSuccess(this.requestId,content);
        }else{
            listener.connectionFails(this.requestId,this.statusCode);
        }
    }

    public String executeRequest() {
        Log.d(TAG, this.url + this.urlParams);

        RequestBody body;                                   // Used if is post
        OkHttpClient client = new OkHttpClient();           // Modern HTTP client
        Request.Builder builder = new Request.Builder();    // Request builder


        // Starts building request
        builder.url(this.url + this.urlParams);
        if(this.headerValues!=null){
            for(PostValue value : this.headerValues){
                builder.addHeader(value.getKey(),value.getValue());
            }
        }
        if(this.isPost || this.isDelete){
            if(this.postValues == null){
                this.postValues = this.dummyPost();
            }

            FormEncodingBuilder formBuilder = new FormEncodingBuilder();
            for(int i=0; i<this.postValues.size(); i++){
                formBuilder.add(
                        postValues.get(i).getKey(),
                        postValues.get(i).getValue());
            }
            body = formBuilder.build();
            if(this.isPost) builder.post(body);
            if(this.isDelete) builder.delete(body);
        }

        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                this.statusCode = 500;
                return "";
            }

            this.statusCode = response.code();
            if (this.isOk(this.statusCode)) {
                return response.body().string();
            } else {
                return "";
            }
        } catch (SecurityException e){
            e.printStackTrace();
            this.statusCode = 503;
            Log.d(TAG, "Missing Internet Permission in AndroidManifest?");
            return "ERROR no internet permission";
        } catch (IOException e) {
            e.printStackTrace();
            this.statusCode = 500;
            return "";
        }

    }

    private boolean isOk(int statusCode) {
        return (statusCode<300 && statusCode >=200);
    }


    /**
     * Filling postValues to avoid restriction of post with null body
     * @return List<PostValue> with one dummy element
     */
    private List<PostValue> dummyPost() {
        List<PostValue> dummy = new ArrayList<PostValue>();
        dummy.add(new PostValue("-|-|-",""));
        return dummy;
    }

}
