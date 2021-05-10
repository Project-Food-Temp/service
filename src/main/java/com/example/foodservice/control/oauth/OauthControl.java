package com.example.foodservice.control.oauth;

import com.example.foodservice.auth.UserPrincipal;
import com.example.foodservice.auth.jwt.JwtUtil;
import com.example.foodservice.constants.Constants;
import com.example.foodservice.constants.Response;
import com.example.foodservice.constants.StatusRegisterUserEnum;
import com.example.foodservice.data.entity.User;
import com.example.foodservice.data.repository.UserRepository;
import com.example.foodservice.data.service.UserService;
import com.example.foodservice.ultis.bean.PayloadBean;
import com.example.foodservice.ultis.bo.TokenBean;
import com.example.foodservice.ultis.form.FormInfoGoogle;
import com.example.foodservice.ultis.form.UserForm;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

/**
 * Created by NhanNguyen on 4/28/2021
 *
 * @author: NhanNguyen
 * @date: 4/28/2021
 */
@RestController
@RequestMapping("/oauth")
public class OauthControl {
    @Value("${google.clientId}")
    private String googleClientId;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("${google.secretPsw}")
    private String secretGoogle;

    @Autowired
    private JwtUtil jwtUtil;

//    @Autowired
//    private JwtProvider jwtProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/google")
    public @ResponseBody
    Response google(@RequestBody FormInfoGoogle form) throws IOException {
        ModelMapper mapper = new ModelMapper();
        final NetHttpTransport transport = new NetHttpTransport();
        final JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();
        GoogleIdTokenVerifier.Builder verifier = new GoogleIdTokenVerifier.Builder(transport, jacksonFactory)
                .setAudience(Collections.singletonList(googleClientId));

        final GoogleIdToken googleIdToken = GoogleIdToken.parse(verifier.getJsonFactory(), form.getToken());
        final GoogleIdToken.Payload payload = googleIdToken.getPayload();
        PayloadBean payloadBean = mapper.map(payload, PayloadBean.class);
        User user = new User();
        if (userRepository.existsUserByEmail(payload.getEmail())) {
            user = userRepository.findUserByEmail(payload.getEmail()).get();
        } else {
            user = userService.registerUserForGoogle(payloadBean);
        }
        if (Objects.nonNull(user)) {
            TokenBean token = login(user);
            return Response.success().withData(token);
        }
        return Response.error(Constants.RESPONSE_CODE.ERROR);
    }

    @PostMapping("/create-user")
    public @ResponseBody
    Response createUser(@RequestBody UserForm form) {
        String result = String.valueOf(userService.registerUser(form));
        if (result.equalsIgnoreCase(StatusRegisterUserEnum.Success.toString())) {
            return Response.success(Constants.RESPONSE_CODE.SUCCESS, result);
        } else {
            return Response.warning(Constants.RESPONSE_CODE.WARNING, result);
        }
    }

    @PostMapping("/authenticate")
    public @ResponseBody
    Response createAuthenticateToken(@RequestBody UserForm user) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
            UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
            String jwt = jwtUtil.generateToken(userPrincipal);
            TokenBean token = new TokenBean();
            token.setJwt(jwt);
            return  Response.success(Constants.RESPONSE_CODE.SUCCESS).withData(token);
        } catch (AuthenticationException e) {
            return Response.error(Constants.RESPONSE_CODE.ERROR, "Incorrect username or password");
        }
    }

    private TokenBean login(User user) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), secretGoogle));
        SecurityContextHolder.getContext().setAuthentication(auth);
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        String jwt = jwtUtil.generateToken(userPrincipal);
        TokenBean token = new TokenBean();
        token.setJwt(jwt);
        return token;
    }


}
