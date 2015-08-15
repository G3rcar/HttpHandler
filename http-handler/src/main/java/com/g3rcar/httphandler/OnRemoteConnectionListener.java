package com.g3rcar.httphandler;

/**
 * Created by gerardo on 20/07/15.
 */
public interface OnRemoteConnectionListener {

    /**
     * Called when the request is completed and parsed to a String
     * @param requestId The id specified in the instance creation
     * @param requestContent The request content as String
     */
    void connectionSuccess(int requestId, String requestContent);

    /**
     * When the connections fails (different from 200) will trigger this method
     * @param requestId The id specified in the instance creation
     * @param errorCode The HTTP status code (401, 500, 403, etc)
     */
    void connectionFails(int requestId, int errorCode);
}
