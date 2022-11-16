package tacos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
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
/**
 * 旧方法定义：
 * @EnableGlobalMethodSecurity
 * public class SecurityConfig extends WebSecurityConfigurerAdapter {}
 *
 * 新方法定义：
 * @Bean
 * public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {}
 * @Bean
 * public WebSecurityCustomizer webSecurityCustomizer() {}
 *
 * WebSecurityConfigurerAdapter在Spring Security 5.7.1以上或Spring Boot 2.7.0以上会出现已被弃用的警告
 * 这是因为Spring框架的开发者鼓励用户使用基于组件的（component-based）的安全配置。
 * 新旧对比：
 * 在以前的用法中，我们扩展WebSecurityConfigurerAdapter类并且覆盖配置HttpSecurity和WebSecurity的方法；
 * 在新的用法中，我们得分别声明类型为SecurityFilterChain和WebSecurityCustomizer的bean，
 */

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
                 * 只有授权的用户才能执行POST请求（防止任何未经授权的用户向/admin/deleteOrders发出POST请求，从而导致所有订单从数据库中消失）
                 */
                .antMatchers(HttpMethod.POST, "/admin/**")
                    .access("hasRole('ADMIN')")
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
                /**
                * 同时提供传统的用户名-密码登录和第三方登录:
                .and()
                    .oauth2Login()
                        .loginPage("/login")
                 */
                 /**
                 * 启用logout登出:点击logout时，session被清空，同时登出
                 * logoutSuccessUrl()：登出后，重定向到指定路径页面
                 */
                .and()
                    .logout()
                        .logoutSuccessUrl("/")

                .and()
                .build();
    }
}