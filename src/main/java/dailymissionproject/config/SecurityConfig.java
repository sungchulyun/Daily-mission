package dailymissionproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService){
        this.customOAuth2UserService = customOAuth2UserService;
    }
    @Bean
    protected SecurityFilterChain config(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception{
       http

               .csrf(AbstractHttpConfigurer::disable)
               .sessionManagement((sessionMangement) ->
                       sessionMangement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                       )
               .formLogin(AbstractHttpConfigurer::disable)
               .httpBasic(AbstractHttpConfigurer::disable)
               .authorizeHttpRequests((authorizeReqeusts) -> authorizeReqeusts
                       .requestMatchers(new MvcRequestMatcher(introspector, "/api/user")).permitAll()
               )
               .oauth2Login(oauth2Login ->
                       oauth2Login.userInfoEndpoint(userInfoEndpointConfig ->
                               userInfoEndpointConfig.userService(customOAuth2UserService)))
               ;
       return http.build();
    }
}