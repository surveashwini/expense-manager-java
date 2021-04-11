package in.ashwinisurve.expenseManager.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.access.ExceptionTranslationFilter;

import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

import in.ashwinisurve.expenseManager.service.UserService;


@Configuration
@EnableWebSecurity
@PropertySource("classpath:application.properties")
@Order(1)
public class AuthTokenSecurityConfig extends WebSecurityConfigurerAdapter {
	@Value("Authorization")
	private String authHeaderName;
	
	private String authHeaderValue;
	
	@Autowired
	UserService userService;
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		PreAuthTokenHeaderFilter filter = new PreAuthTokenHeaderFilter(authHeaderName);
		
		filter.setAuthenticationManager(new AuthenticationManager() {
			@Override
			public Authentication authenticate(Authentication authentication) throws AuthenticationException {
				String principal = (String) authentication.getPrincipal();
				authHeaderValue = userService.getToken(principal.split(" ")[1]);
				if(authHeaderValue == null) {
					throw new BadCredentialsException("The API key was not found");
				}
				authentication.setAuthenticated(true);
				return authentication;
			}
		});
		
		httpSecurity.
			antMatcher("/api/v1/**")
			.antMatcher("api/v1/authenticateUser").authorizeRequests().and()
			.csrf()
				.disable()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.cors().and()
				.addFilter(filter)
				.addFilterBefore(new ExceptionTranslationFilter(
						new Http403ForbiddenEntryPoint()),
						filter.getClass()
				)
				.authorizeRequests()
					.anyRequest()
					.authenticated();
	}
	
}
