package tacos.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsService {
    //loadUserByUsername()方法接受用户名使用它查找UserDetails对象，找不到用户，则抛出错误
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
