package com.enterprise.knowledgehub.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /**
     * The physical Bouncer standing at the door.
     * Intercepts EVERY single HTTP request coming into the application.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            // 1. Look at the request and try to find the VIP Wristband (JWT token)
            String jwt = getJwtFromRequest(request);

            // 2. If they have a wristband, and the wristband is valid (not fake, not expired)
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                
                // 3. Read the email printed on the wristband
                String email = tokenProvider.getEmailFromJWT(jwt);

                // 4. Fetch the user details from our database
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
                
                // 5. Create a verified badge (Authentication Token) and put it on the user
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 6. Tell Spring Security: "This person is officially allowed inside."
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }

        // 7. Let the request continue to the next filter or the Controller
        filterChain.doFilter(request, response);
    }

    /**
     * Helper method to grab the JWT string out of the HTTP "Authorization" header.
     * The standard format is: "Authorization: Bearer <token>"
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Remove "Bearer " prefix
        }
        return null;
    }
}
