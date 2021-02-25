package com.jfb.productscatalog.components;

import java.util.HashMap;
import java.util.Map;

import com.jfb.productscatalog.entities.User;
import com.jfb.productscatalog.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenEnhancer implements TokenEnhancer {

  @Autowired
  private UserRepository userRepository;

  /**
   * Esse método vai entrar no ciclo de vida do token e na hora de geralo vai
   * acrescentar os objetos que eu passar.
   */
  @Override
  public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
    User user = userRepository.findByEmail(authentication.getName());
    Map<String, Object> map = new HashMap<>();
    map.put("userFirstName", user.getFirstName());
    map.put("userId", user.getId());

    DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;
    token.setAdditionalInformation(map);
    return accessToken;
  }

}
