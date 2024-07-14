package com.far.ora.ForoHub.models.dto.show;

import com.far.ora.ForoHub.models.User;

public record ShowUser(
    String name,
    String email,
    ShowProfile profile
) {
    public ShowUser(User user) {
        this(user.getName(), user.getEmail(), new ShowProfile(user.getProfile()));
    }
}
