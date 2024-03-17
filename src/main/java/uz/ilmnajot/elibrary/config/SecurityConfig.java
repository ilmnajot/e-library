package uz.ilmnajot.elibrary.config;

import org.springframework.context.annotation.Configuration;

/*@EnableWebSecurity*/
@Configuration
public class SecurityConfig {

//
//    private final JwtFilter jwtFilter;
//
//
//    private final static String FREE_WAY = "/auth/**";
//
//    public SecurityConfig(JwtFilter jwtFilter) {
//        this.jwtFilter = jwtFilter;
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf()
//                .disable()
//                .authorizeHttpRequests()
//                .requestMatchers(FREE_WAY)
//                .permitAll()
//                .requestMatchers(AUTH_WHITELIST)
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
//
//                .formLogin(login -> {
//                    login.loginPage("/auth/login");
//                    login.defaultSuccessUrl("/");
//                    login.failureUrl("/login-error");
//                })
//                .logout(logout -> {
//                    logout.logoutRequestMatcher(new AntPathRequestMatcher("logout"));
//                    logout.logoutSuccessUrl("/");
//                    logout.deleteCookies("JSESSIONID");
//                    logout.invalidateHttpSession(true);
//                });
//
//        return http.build();
//    }
//
//    private static final String[] AUTH_WHITELIST = {
//            "/api/v1/auth/**",
//            "/v2/api-docs",
//            "/v3/api-docs/**",
//            "/swagger-resources",
//            "/swagger-resources/**",
//            "/configuration/ui",
//            "/configuration/security",
//            "/swagger-ui/**",
//            "/webjars/**",
//            "swagger-ui.html"
//    };

}
