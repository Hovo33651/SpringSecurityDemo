package com.example.demo.auth;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.example.demo.security.ApplicationUserRole.*;

@Repository("fake")
@RequiredArgsConstructor
public class FakeApplicationUserDaoService implements ApplicationUserDao{

    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getApplicationUsers()
                .stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }

    private List<ApplicationUser> getApplicationUsers(){
        return Lists.newArrayList(
                new ApplicationUser(STUDENT.grantedAuthorities(),
                        passwordEncoder.encode("root"),
                        "poxos",
                        true,
                        true,
                        true,
                        true),
                new ApplicationUser(ADMIN.grantedAuthorities(),
                        passwordEncoder.encode("root"),
                        "petros",
                        true,
                        true,
                        true,
                        true),
                new ApplicationUser(ADMINTRAINEE.grantedAuthorities(),
                        passwordEncoder.encode("root"),
                        "armen",
                        true,
                        true,
                        true,
                        true));
    }
}
