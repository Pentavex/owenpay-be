package com.pentavex.owenpay.config;

import com.pentavex.owenpay.controller.FacebookSignInAdapter;
import com.pentavex.owenpay.controller.FacebookSignUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.util.Assert;

@Configuration
@EnableSocial
public class FacebookSocialConfigurer extends SocialConfigurerAdapter {

    @Autowired
    ConnectionFactoryLocator connectionFactoryLocator;

    @Autowired
    FacebookSignInAdapter facebookSignInAdapter;

    @Autowired
    FacebookSignUp facebookSignUp;

    @Autowired
    UsersConnectionRepository usersConnectionRepository;

    @Override
    public UserIdSource getUserIdSource() {
        return () -> {
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = context.getAuthentication();
            Assert.state(authentication != null, "Cannot find the authenticated user");
            return authentication.getName();
        };
    }

    @Bean
    public ProviderSignInController providerSignInController() {
        ((InMemoryUsersConnectionRepository) usersConnectionRepository)
                .setConnectionSignUp(facebookSignUp);

        return new ProviderSignInController(
                connectionFactoryLocator,
                usersConnectionRepository,
                new FacebookSignInAdapter());
    }

}
