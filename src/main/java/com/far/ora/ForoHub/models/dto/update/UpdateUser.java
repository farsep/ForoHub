package com.far.ora.ForoHub.models.dto.update;

public record UpdateUser(
    String name,
    String email,
    String password,
    UpdateProfile profile
) {
}
