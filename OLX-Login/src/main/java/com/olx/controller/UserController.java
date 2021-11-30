package com.olx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.olx.dto.AuthenticationRequest;
import com.olx.entity.UserEntity;
import com.olx.exception.GlobalExceptionHandler;
import com.olx.exception.InvalidAuthTokenException;
import com.olx.security.JwtUtil;
import com.olx.service.BlackListService;
import com.olx.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("olx/login")
public class UserController {
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	UserService userDetailsService;
	@Autowired
	BlackListService blackListService;

	@PostMapping(value = "/user/authenticate", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "Autenticates the user by returning a token")
	public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest req) {
		try {
			this.authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));

		} catch (BadCredentialsException exception) {
			return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
		}
		UserDetails userDetails = userDetailsService.loadUserByUsername(req.getUsername());
		String token = jwtUtil.generateToken(userDetails);
		return new ResponseEntity(token, HttpStatus.OK);

	}

	// user/validate/token
	@GetMapping(value = "user/validate/token")
	@ApiOperation(value = "Validates the user by using a token passed to this method")
	public ResponseEntity<Boolean> validate(@RequestHeader("Authorization") String Authorization) {
		boolean isValid = false;
		try {
			String[] tokenArray = Authorization.split(" ");
			String token = tokenArray[1];
			UserDetails userDetails = userDetailsService.loadUserByUsername(jwtUtil.extractUsername(token));
			isValid = jwtUtil.validateToken(token, userDetails);
		} catch (Exception ex) {
			return new ResponseEntity(isValid, HttpStatus.BAD_REQUEST);
		}
		if(blackListService.checkBlackListUser(Authorization));{
			return new ResponseEntity(isValid, HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping(value = "/user", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "Creates a new user")
	public UserEntity createNewUser(@RequestBody UserEntity user) {
		return userDetailsService.createNewUser(user);
	}

	  @DeleteMapping(value = "/user/logout")
	  @ApiOperation(value="delete an user")
	  public ResponseEntity<Boolean> deleteUser(@RequestHeader("Authorization") String AuthToken) {
		  blackListService.logoutUser(AuthToken);
		return new ResponseEntity<Boolean>(true,HttpStatus.OK);
			
	  }

	@GetMapping(value = "/user")
	@ApiOperation(value = "fetches details of the user")
	public UserEntity getUserDetails(@RequestHeader("Authorization") String Authorization) {
		boolean isValid = false;
		String[] tokenArray = Authorization.split(" ");
		String token = tokenArray[1];
		UserDetails userDetails = userDetailsService.loadUserByUsername(jwtUtil.extractUsername(token));
		isValid = jwtUtil.validateToken(token, userDetails);
		if (isValid) {
			UserEntity userDetail = userDetailsService.getUserDetails(jwtUtil.extractUsername(token));
			return userDetail;
		} else {
			throw new InvalidAuthTokenException("invalid auth exception");
		}
	}

	@GetMapping(value = "/user/getUserName")
	@ApiOperation(value = "returns the user name in case eded in the inter micro service communication")
	public String getUserName(@RequestHeader("Authorization") String Authorization) {
		String[] tokenArray = Authorization.split(" ");
		String token = tokenArray[1];
		return jwtUtil.extractUsername(token);
	}

	@GetMapping(value = "/user/checkLoggedIn")
	@ApiOperation(value = "checks a user is loggedin/loggedout in case neded in the inter micro service communication")
	public Boolean isUserLoggedIn(@RequestHeader("Authorization") String Authorization) {
		boolean isValid = false;
		String[] tokenArray = Authorization.split(" ");
		String token = tokenArray[1];
		UserDetails userDetails = userDetailsService.loadUserByUsername(jwtUtil.extractUsername(token));
		isValid = jwtUtil.validateToken(token, userDetails);
		if (isValid) {
			UserEntity userDetail = userDetailsService.getUserDetails(jwtUtil.extractUsername(token));
			return userDetail.isActive();
		} else {
			throw new InvalidAuthTokenException("invalid auth exception");
		}

	}

}
