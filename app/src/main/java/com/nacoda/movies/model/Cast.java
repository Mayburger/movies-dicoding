package com.nacoda.movies.model;

import org.json.JSONObject;

import lombok.Data;

/**
 * Created by Mayburger on 1/11/18.
 */

@Data
public class Cast {

    private String character;
    private String name;
    private String profile_path;

    public Cast(JSONObject object) {

        try {

            String character = object.getString("character");
            String name = object.getString("name");
            String profile_path = object.getString("profile_path");

            this.character = character;
            this.name = name;
            this.profile_path = profile_path;

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}
