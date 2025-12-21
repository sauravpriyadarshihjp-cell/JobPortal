package com.Api.ApiGateway.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class JwtRbacFilter implements GlobalFilter {
    @Value("${jwt.secret}")
    private String secret;

//    @Value("${jwt.expiration-ms}")
//    private String
    private final List<String> publicPaths = List.of("/party/login","/party/register");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain){
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        //byPass
        for(String p :publicPaths){
            if(path.startsWith(p)){
                return chain.filter(exchange);
            }
        }

        List<String> auth = request.getHeaders().getOrEmpty("Authorization");
        if(auth.isEmpty()){
            return unauthorized(exchange);
        }
        String header = auth.get(0);
        if(!header.startsWith("Bearer ")){
            return unauthorized(exchange);
        }
        String token = header.substring(7);

        try{
            Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(secret.getBytes()).build().parseClaimsJws(token);
            Claims claims = jws.getBody();
            String userId = claims.get("userId").toString();
            Object rolesobj = claims.get("roles");
            String roles = rolesobj == null ? "" : rolesobj.toString();

            ServerHttpRequest modified = request.mutate().header("x-user-id", userId)
                    .header("x-user-email", claims.getSubject())
                    .header("x-user-roles", roles)
                    .build();
            return chain.filter(exchange.mutate().request(modified).build());
        } catch (Exception e) {
            return unauthorized(exchange);
        }
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange){
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

}
