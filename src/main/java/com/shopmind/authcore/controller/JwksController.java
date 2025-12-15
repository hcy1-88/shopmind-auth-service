package com.shopmind.authcore.controller;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.shopmind.util.JwtUtils.loadPublicKeyFromBase64;

/**
 * JWKS (JSON Web Key Set) 端点
 * 提供 JWT 公钥，供其他服务验证 JWT
 */
@RestController
public class JwksController {

    /**
     * JWT 公钥（从配置文件读取）
     */
    @Value("${shopmind.auth.jwt-public-key:}")
    private String jwtPublicKey;

    /**
     * JWKS 端点（符合 RFC 7517 标准）
     * 其他服务会访问此端点获取公钥
     */
    @GetMapping("/.well-known/jwks.json")
    public Map<String, Object> getJwks() throws Exception {
        PublicKey pubKey = loadPublicKeyFromBase64(jwtPublicKey);

        RSAKey jwk = new RSAKey.Builder((java.security.interfaces.RSAPublicKey) pubKey)
                .keyID("demo-key-001")
                .algorithm(JWSAlgorithm.RS256)
                .keyUse(com.nimbusds.jose.jwk.KeyUse.SIGNATURE)
                .build();
        return Map.of("keys", List.of(jwk.toJSONObject()));
    }

}
