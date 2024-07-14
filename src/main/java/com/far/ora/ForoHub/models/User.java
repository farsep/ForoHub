package com.far.ora.ForoHub.models;

import com.far.ora.ForoHub.models.dto.register.RegisterUser;
import com.far.ora.ForoHub.models.dto.update.UpdateUser;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String email;
    private String password;
    @Embedded
    private Profile profile;
    @OneToMany(mappedBy = "user")
    private List<Topic> topic;
    @OneToMany(mappedBy = "user")
    private List<Answer> answer;
    private String Authority = "ROLE_USER";

    public User(RegisterUser user) {
        this.name = user.name();
        this.email = user.email();
        this.password = user.password();
        this.profile = new Profile(user.profile());
    }

    public void Update(UpdateUser user) {
        if (user.name() != null && !user.name().isBlank()) {
            this.name = user.name();
        }
        if (user.email() != null && !user.email().isBlank()) {
            this.email = user.email();
        }
        if (user.password() != null && !user.password().isBlank()) {
            this.password = user.password();
        }
        if (user.profile() != null) {
            this.profile.Update(user.profile());
        }
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(Authority));
    }

    @Override
    public String getUsername() {
        return profile.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
