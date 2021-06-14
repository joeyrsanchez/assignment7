package merit.america.bank.MeritBank.controllers;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import merit.america.bank.MeritBank.models.CDOffering;
import merit.america.bank.MeritBank.models.MeritBank;
import org.springframework.http.HttpStatus;

@RestController
public class MeritBankController {

	@RequestMapping("/greet")
	public String greetMe() { 
		return "<html><h1>Hello Humans. Welcome to Spring framework</h1></html>";
	}
	
	@RequestMapping("/CDOfferings")
	public static CDOffering[] getAllCdos(){
		
		return MeritBank.getCDOfferings();
	}
	
	@PostMapping(value = "/CDOfferings")
	@ResponseStatus(HttpStatus.CREATED)
	public CDOffering addCDO(@RequestBody @Valid CDOffering cdo) {
		MeritBank.addCDO(cdo);
		return cdo;
		
	} 
}
