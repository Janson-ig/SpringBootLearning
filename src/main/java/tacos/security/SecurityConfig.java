package tacos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import tacos.data.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    /**
     * 网传第六版此处有错误
     */
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder encoder) {
        List<UserDetails> usersList = new ArrayList<>();
        usersList.add(new User(
                "buzz", encoder.encode("password"),
                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))));
        usersList.add(new User(
                "woody", encoder.encode("password"),
                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))));
        return new InMemoryUserDetailsManager(usersList);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /**
        authorizeRequests()返回(ExpressionUrlAuthorizationConfigurer.ExpressionInterceptUrlRegistry)对象，
        允许指定URL路径和模式，以及路径对应的安全要求：
        1. /design 和 /orders的请求应该是针对具有ROLE_USER授权的用户。不要在hasRole()中添加前缀ROLE_，前缀会被hasRole()假定。
        2. 所有的请求被允许给所有用户。

        .antMatchers("/design", "/orders").hasRole("USER")
        .antMatchers("/", "/**").permitAll()
        以上等价于：
        .antMatchers("/design", "/orders").access("hasRole('USER')")
        .antMatchers("/", "/**").access("permitAll()")
         */
        return http
                .authorizeRequests()
                .antMatchers("/design", "/orders").hasRole("USER")
                .antMatchers("/", "/**").permitAll()

                /**
                 * 1.and()方法表明完成认证配置，并且准备应用一些额外的HTTP配置（在配置新的部分时，会使用多次and()方法）
                 * 2.调用formLogin()使用自定的login界面
                 * 3.loginPage()指定提供的page路径：当用户没有通过认证并且需要登录时，会重定向到这个路径
                 * 4.Spring Security监听来自/login路径的请求
                 * 5.defaultSuccessUrl()：登入成功后，重定向到“/design”界面;加入第二个参数true，用户登入后强制跳转到指定界面，即使在浏览别的界面
                 */
                .and()
                    .formLogin()
                        .loginPage("/login")
                        .loginProcessingUrl("/authenticate")
                        .defaultSuccessUrl("/login", true)
                        .usernameParameter("user")
                        .passwordParameter("pwd")

                .and()
                .build();
    }
}