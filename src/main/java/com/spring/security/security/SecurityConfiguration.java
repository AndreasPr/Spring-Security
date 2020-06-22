package com.spring.security.security;

import com.spring.security.authentication.services.UserServiceAuthentication;
import com.spring.security.jsonwebtoken.ConfigurationOfJWT;
import com.spring.security.jsonwebtoken.UsernameAndPasswordAuthFilter;
import com.spring.security.jsonwebtoken.VerificationOfToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.crypto.SecretKey;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserServiceAuthentication userServiceAuthentication;
    private final SecretKey secretKey;
    private final ConfigurationOfJWT configurationOfJWT;

    @Autowired
    public SecurityConfiguration(PasswordEncoder passwordEncoder, UserServiceAuthentication userServiceAuthentication, SecretKey secretKey, ConfigurationOfJWT configurationOfJWT) {
        this.passwordEncoder = passwordEncoder;
        this.userServiceAuthentication = userServiceAuthentication;
        this.secretKey = secretKey;
        this.configurationOfJWT = configurationOfJWT;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//                //--- Cross-Site Request Forgery----
//                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .and()
//                //--- Cross-Site Request Forgery----
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
//                .antMatchers("/api/**").hasRole(UserRoles.EMPLOYEE.name())
//                .anyRequest()
//                .authenticated()
//                .and()
//                //--- Basic Authentication----
////              .httpBasic()
//                //--- Basic Authentication----
//                //--- Form based Authentication and Database Authentication----
//                .formLogin()
//                .loginPage("/login")
//                .permitAll()
//                .defaultSuccessUrl("/employee", true)
//                .passwordParameter("password")
//                .usernameParameter("username")
//                .and()
//                .rememberMe()
//                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
//                .key("somethingWithHighSecurity")
//                .rememberMeParameter("remember-me")
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
//                .clearAuthentication(true)
//                .invalidateHttpSession(true)
//                .deleteCookies("JSESSIONID", "remember-me")
//                .logoutSuccessUrl("/login");
//                //--- Form based Authentication and Database Authentication----

                  // JSON Web Token
                http
                        .csrf().disable()
                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .and()
                        .addFilter(new UsernameAndPasswordAuthFilter(authenticationManager(), configurationOfJWT, secretKey))
                        .addFilterAfter(new VerificationOfToken(secretKey, configurationOfJWT), UsernameAndPasswordAuthFilter.class)
                        .authorizeRequests()
                        .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                        .antMatchers("/api/**").hasRole(UserRoles.EMPLOYEE.name())
                        .anyRequest()
                        .authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userServiceAuthentication);
        return provider;
    }
}
