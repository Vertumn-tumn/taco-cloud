package tacos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import tacos.User;
import tacos.data.UserRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            User user = userRepository.findByUsername(username);
            if (user != null) return user;
            throw new UsernameNotFoundException("User ‘" + username + "’ not found");
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests((authz) -> authz
                        .requestMatchers(HttpMethod.POST, "/api/ingredients/**", "/api/tacos/**", "/api/orders/**").hasAuthority("SCOPE_writeEntities")
                        .requestMatchers(HttpMethod.DELETE, "/api/ingredients/**", "/api/tacos/**", "/api/orders/**").hasAuthority("SCOPE_deleteEntities")
                        .requestMatchers(HttpMethod.PUT, "/api/ingredients/**", "/api/tacos/**", "/api/orders/**").hasAuthority("SCOPE_putEntities")
                        .requestMatchers(HttpMethod.PATCH, "/api/ingredients/**", "/api/tacos/**", "/api/orders/**").hasAuthority("SCOPE_patchEntities")
                        .requestMatchers(HttpMethod.GET, "/api/orders/**").hasAuthority("SCOPE_getEntities")
                        .requestMatchers("/design", "/orders").hasRole("USER")
                        .requestMatchers("/", "/**").permitAll())
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/design"))
                .oauth2Login((oauth2Login) -> oauth2Login
                        .userInfoEndpoint(userInfo -> userInfo
                                .userAuthoritiesMapper(authorities -> AuthorityUtils.createAuthorityList("ROLE_USER")))
                        .loginPage("/login"))
                .oauth2ResourceServer(oauthResServerConf -> oauthResServerConf.jwt(Customizer.withDefaults()))
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }
}
