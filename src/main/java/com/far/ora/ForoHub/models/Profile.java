package com.far.ora.ForoHub.models;

import com.far.ora.ForoHub.models.dto.register.RegisterProfile;
import com.far.ora.ForoHub.models.dto.update.UpdateProfile;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@Setter
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode(of = "id")
public class Profile {
    private String username;

    public Profile(RegisterProfile profile) {
        this.username = profile.username();
    }

    public void Update(UpdateProfile profile) {
        if (profile.username() != null && !profile.username().isBlank()) {
            this.username = profile.username();
        }
    }

}
