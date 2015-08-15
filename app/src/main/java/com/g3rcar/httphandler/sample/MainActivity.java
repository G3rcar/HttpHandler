package com.g3rcar.httphandler.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.g3rcar.httphandler.OnRemoteConnectionListener;
import com.g3rcar.httphandler.PostValue;
import com.g3rcar.httphandler.RemoteConnection;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements OnRemoteConnectionListener {

    public static final String GET = "http://g3rcar.com/library/";
    public static final String POST = "http://g3rcar.com/library/";
    public static final String DELETE = "http://g3rcar.com/library/";

    TextView txvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txvResult = (TextView) findViewById(R.id.txvContent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            case R.id.action_get: doGet(); break;
            case R.id.action_post: doPost(); break;
            case R.id.action_delete: doDelete(); break;
        }

        return super.onOptionsItemSelected(item);
    }
    private void doGet(){
        RemoteConnection connection = new RemoteConnection();
        connection.setOnRemoteConnectionListener(this);
        List<PostValue> headers = new ArrayList<>();
        headers.add(new PostValue("token","dadadadadadad"));
        connection.setURL(GET);
        connection.setURLParams("?param1=get1&param2=get2");
        connection.setHeaderParams(headers);
        connection.execute();
    }

    private void doPost(){
        RemoteConnection connection = new RemoteConnection();
        connection.setOnRemoteConnectionListener(this);
        List<PostValue> headers = new ArrayList<>();
        headers.add(new PostValue("token", "dadadadadadad"));
        connection.setURL(POST);
        List<PostValue> params = new ArrayList<PostValue>();
        params.add(new PostValue("param1","post1"));
        params.add(new PostValue("param2","post2"));
        connection.setPostParams(params);
        connection.setHeaderParams(headers);
        connection.execute();
    }

    private void doDelete(){
        RemoteConnection connection = new RemoteConnection();
        connection.setOnRemoteConnectionListener(this);
        List<PostValue> headers = new ArrayList<>();
        headers.add(new PostValue("token", "dadadadadadad"));
        connection.setURL(DELETE);
        List<PostValue> params = new ArrayList<PostValue>();
        params.add(new PostValue("param1","post1"));
        params.add(new PostValue("param2","post2"));
        connection.setPostParams(params);
        connection.setHeaderParams(headers);
        connection.isDelete(true);
        connection.execute();
    }

    @Override
    public void connectionSuccess(int requestId, String requestContent) {
        txvResult.setText("RequestID: " + requestId + "; \n" + requestContent);
    }

    @Override
    public void connectionFails(int requestId, int errorCode) {
        txvResult.setText("RequestID: "+requestId+"; \nErrorCode: "+errorCode);
    }


}
