package com.capgemini.gymapp.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.capgemini.gymapp.entities.Permission.*;

@RequiredArgsConstructor
public enum Role {
    USER(Collections.emptySet()),
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_CREATE,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    CLIENT_READ,
                    CLIENT_CREATE,
                    CLIENT_UPDATE,
                    CLIENT_DELETE

            )
    ),
    CLIENT(
            Set.of(
            CLIENT_READ,
            CLIENT_CREATE,
            CLIENT_UPDATE,
            CLIENT_DELETE

            )
    ),
    COACH(
            Set.of(
                    COACH_READ,
                    COACH_CREATE,
                    COACH_UPDATE,
                    COACH_DELETE

            )
    )
    ;

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }

}