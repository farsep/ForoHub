package com.far.ora.ForoHub.models.dto.show;

import com.far.ora.ForoHub.models.Profile;
public record ShowProfile(
    String name
) {
    public ShowProfile(Profile profile) {
        this(profile.getUsername());
    }
}
