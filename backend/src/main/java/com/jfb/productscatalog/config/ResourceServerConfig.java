package com.jfb.productscatalog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

  private static final String[] PUBLIC = { "/oauth/tokem" };

  private static final String[] OPERATOR_OR_ADMIN = { "/products/**", "/categories/**" };

  private static final String[] ADMIN = {"/users/**"};

  @Autowired
  private JwtTokenStore tokenStore;

  /**
   * Com essa sobrescrita do método configure, o nosso Resource server vai ser
   * capaz de decodificar o tokem e analisar se ele esta batendo so o secret, se
   * esta expirado ou não, ou seja verificar se o tokem pe válido.
   **/
  @Override
  public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    resources.tokenStore(tokenStore);
  }

  // Abaixo a Configuração das rotas.
  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers(PUBLIC).permitAll()
        .antMatchers(HttpMethod.GET, OPERATOR_OR_ADMIN).permitAll()
        .antMatchers(OPERATOR_OR_ADMIN).hasAnyRole("OPERATOR", "ADMIN")
        .antMatchers(ADMIN).hasRole("ADMIN")
        .anyRequest().authenticated();
  }

}
