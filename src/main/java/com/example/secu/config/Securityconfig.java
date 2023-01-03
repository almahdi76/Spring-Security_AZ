package com.example.secu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@EnableWebSecurity
@EnableMethodSecurity
public class Securityconfig {
    private PasswordEncoder passwordEncoder;

    public Securityconfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return  http
                .csrf(csrf->csrf.ignoringAntMatchers("/h2-console/**"))
                .authorizeRequests(
                auth->auth.antMatchers("/h2-console/**").permitAll()
                        .anyRequest().authenticated()

                )
                .headers(headers->headers.frameOptions().sameOrigin())
                .formLogin()
                .and()
                .build();


    }
    /*

            .authorizeRequests()
                .antMatchers()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()

                .build();
     */

    /*
    @Bean
    public UserDetailsService userDetailsService() {
        var userDetailsService =
                new InMemoryUserDetailsManager();

        var userName = User.withUsername("user")
                .password(passwordEncoder.encode("pw22"))
                .authorities("USER_ROLE")
                .roles("USER")

                .build();

        userDetailsService.createUser(userName);

        return userDetailsService;
    }
*/
   // @Bean
    InMemoryUserDetailsManager users(){
        return new InMemoryUserDetailsManager(
                User.withUsername("abc")
                        .password(passwordEncoder.encode("111"))
                        .roles()
                        .build()
        );
    }
    @Bean
    public JdbcUserDetailsManager user(DataSource dataSource,PasswordEncoder encoder){
        UserDetails admin=User.builder()
                .password(encoder.encode("111"))
                .username("mon-nom")
                .roles("ADMIN")
                .build();
        JdbcUserDetailsManager jdbcUserDetailsManager=new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.createUser(admin);
        return jdbcUserDetailsManager;
    }

    @Bean
    EmbeddedDatabase deatasorce(){
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setName("dashboard")
                .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
                .build();
    }

}
