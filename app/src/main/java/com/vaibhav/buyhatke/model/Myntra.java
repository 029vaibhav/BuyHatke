package com.vaibhav.buyhatke.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by vaibhav on 22/4/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Myntra {
    private Session session;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}