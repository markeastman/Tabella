package uk.me.eastmans.tabella.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            //.csrf()//requireCsrfProtectionMatcher(new AllExceptUrlStartedWith("/console"))
            //.and()
            .authorizeRequests()
            .antMatchers("/", "/console/**", "/dist/**", "/plugins/**", "/bootstrap/**", "/public/**", "/webjars/**", "/websocketHandler/**").permitAll()
            .antMatchers("/users/**").hasAuthority("ADMIN")
            .anyRequest().fullyAuthenticated()
            .and()
            .formLogin()
            .loginPage("/login")
            .failureUrl("/login?error")
            .usernameParameter("email")
            .permitAll()
            .and()
            .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .deleteCookies("remember-me")
            .invalidateHttpSession(true)
            .logoutSuccessUrl("/")
            .permitAll()
            .and()
            .rememberMe()
        ;

        httpSecurity.csrf().disable();
        httpSecurity.headers().frameOptions().disable();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    private static class AllExceptUrlStartedWith implements RequestMatcher {

        private static final String[] ALLOWED_METHODS =
                new String[] {"GET","POST"};

        private final String[] allowedUrls;

        public AllExceptUrlStartedWith(String... allowedUrls) {
            this.allowedUrls = allowedUrls;
        }

        @Override
        public boolean matches(HttpServletRequest request) {
            String method = request.getMethod();
            for(String allowedMethod : ALLOWED_METHODS) {
                if (allowedMethod.equals(method)) {
                    return false;
                }
            }

            String uri = request.getRequestURI();
            for (String allowedUrl : allowedUrls) {
                if (uri.startsWith(allowedUrl)) {
                    return false;
                }
            }
            return true;
        }

    }

}