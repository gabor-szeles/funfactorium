package org.funfactorium.security;

import org.funfactorium.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private UserService userService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/**/*.{js,html}");
        web.debug(false);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http

                .formLogin().loginProcessingUrl("/api/authenticate")
                .successHandler(authenticationSuccessHandler())
                .failureHandler(authenticationFailureHandler())
                .permitAll()

                .and()

                .logout().deleteCookies("remember-me")
                .logoutUrl("/api/logout")
                .logoutSuccessHandler(logoutSuccessHandler())
                .deleteCookies("JSESSIONID")
                .permitAll()

                .and()

                .authorizeRequests().antMatchers("/api/**").permitAll()
                .and().authorizeRequests().antMatchers("/add-funfact").hasAuthority("ADD_FUNFACT")
                .and().authorizeRequests().antMatchers("/api-docs").permitAll()
                .and().authorizeRequests().antMatchers("/register").permitAll()
                .and().authorizeRequests().antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                .rememberMe()
                .tokenValiditySeconds(86400)
                .and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());

        if ("true".equals(System.getProperty("httpsOnly"))) {
            log.info("launching the application in HTTPS-only mode");
            http.requiresChannel().anyRequest().requiresSecure();
        }
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Creates a custom authentication success handler.
     * It does redirect to '/api/users/username' after successful login.
     * Username is taken from the Authentication object.
     *
     * @return AuthenticationSuccessHandler
     */
    @Bean
    AuthenticationSuccessHandler authenticationSuccessHandler() {

        return new SimpleUrlAuthenticationSuccessHandler() {

            @Override
            public void onAuthenticationSuccess(HttpServletRequest request,
                                                HttpServletResponse response,
                                                Authentication authentication) throws IOException, ServletException {

                String targetUrl = "/";

                if (response.isCommitted()) {
                    log.debug("Response has already been committed. Unable to redirect to {}", targetUrl);
                    return;
                }

                getRedirectStrategy().sendRedirect(request, response, targetUrl);
            }
        };
    }

    /**
     * Creates a custom AuthenticationFailureHandler
     * It returns custom errors with {@link HttpServletResponse#SC_UNAUTHORIZED} (401) HTTP Status.
     *
     * @return AuthenticationFailureHandler
     */
    @Bean
    AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            if (exception instanceof DisabledException) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User account suspended");
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed");
            }
        };
    }

    @Bean
    LogoutSuccessHandler logoutSuccessHandler() {
        return (request, response, authentication) ->
                response.sendRedirect("/");

    }

    @Bean
    AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            log.trace("Pre-authenticated entry point called ({}). Rejecting access.", request.getRequestURI(), authException);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            PrintWriter writer = response.getWriter();
            writer.println("HTTP Status 401 - " + authException.getMessage());
        };
    }

}