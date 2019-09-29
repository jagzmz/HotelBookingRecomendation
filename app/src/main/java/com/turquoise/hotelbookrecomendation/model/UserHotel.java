
package com.turquoise.hotelbookrecomendation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserHotel {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("completed")
    @Expose
    private Boolean completed;

    private String favTags;

    public String getName() {
        return name;
    }

    public void setTags(String tag){
        this.favTags+=tag+"\n";
    }

    public String getTags(){
        return favTags;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

}
