package com.g3rcar.httphandler;

/**
 * Created by gerardo on 20/07/15.
 * Used to encapsulate the key-value pair to the FormEncodingBuilder in POST requests
 */
public class PostValue {
    private String key;
    private String value;

    /**
     * Constructor
     * @param key String as the key value of the POST param
     * @param value String as the value of the POST param
     */
    public PostValue(String key, String value){
        this.key = key;
        this.value = value;
    }

    /**
     * @return key value
     */
    public String getKey() {
        return key;
    }

    /**
     * @return value
     */
    public String getValue() {
        return value;
    }
}
