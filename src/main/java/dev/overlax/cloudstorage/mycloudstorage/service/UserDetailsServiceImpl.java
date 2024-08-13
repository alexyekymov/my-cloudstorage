package dev.overlax.cloudstorage.mycloudstorage.service;

import dev.overlax.cloudstorage.mycloudstorage.model.SecurityUser;
import dev.overlax.cloudstorage.mycloudstorage.model.User;
import dev.overlax.cloudstorage.mycloudstorage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserDetailsServiceImpl implements UserDetailsService {

    public final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
                .findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return SecurityUser.fromUser(user);
    }
}
