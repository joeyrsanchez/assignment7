package merit.america.bank.MeritBank.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import merit.america.bank.MeritBank.models.User;
import merit.america.bank.MeritBank.security.AuthenticationRequest;
import merit.america.bank.MeritBank.security.AuthenticationResponse;
import merit.america.bank.MeritBank.security.JwtUtil;
import merit.america.bank.MeritBank.security.MyUserDetailsService;

@RestController
public class Authenticate {

		@Autowired
		private AuthenticationManager authenticationManager;

		@Autowired
		private JwtUtil jwtTokenUtil;

		@Autowired
		private MyUserDetailsService userDetailsService;

		@RequestMapping(value = "/Authenticate", method = RequestMethod.POST)
		public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

			try {
				authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
				);
			}
			catch (BadCredentialsException e) {
				System.out.print("Fail");
				throw new Exception("Incorrect username or password", e);
			}

			final UserDetails userDetails = userDetailsService
					.loadUserByUsername(authenticationRequest.getUsername());

			final String jwt = jwtTokenUtil.generateToken(userDetails);

			return ResponseEntity.ok(new AuthenticationResponse(jwt));
		}
		

		@RequestMapping(value = "/Authenticate/CreateUser", method = RequestMethod.POST)
		public ResponseEntity<?> createUser(@RequestBody @Valid User user){
			user = userDetailsService.save(user);
			
			return ResponseEntity.ok(user);
		}

}
