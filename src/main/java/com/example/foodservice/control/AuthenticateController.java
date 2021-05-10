package com.example.foodservice.control;

import com.example.foodservice.auth.jwt.JwtUtil;
import com.example.foodservice.constants.Constants;
import com.example.foodservice.constants.Response;
import com.example.foodservice.ultis.form.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by NhanNguyen on 4/22/2021
 *
 * @author: NhanNguyen
 * @date: 4/22/2021
 */
@RestController
public class AuthenticateController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;

    @PostMapping("/authenticate")
    public @ResponseBody
    Response createAuthenticationTokenAndRole(@Validated @RequestBody UserForm form) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(form.getUsername(), form.getPassword())
            );
        } catch (AuthenticationException e) {
            return Response.warning(Constants.RESPONSE_CODE.WARNING,Constants.MESSAGE.LOGIN_FAIL);
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(form.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        final List<String> roles = new ArrayList<>();
        userDetails.getAuthorities().stream().forEach(grantedAuthority -> {
            roles.add(grantedAuthority.getAuthority());
        });
        Map<String, Object> data = new HashMap<>();
        data.put("jwt", jwt);
        data.put("roles", roles);
        return Response.success(Constants.RESPONSE_CODE.SUCCESS).withData(data);
    }

}
