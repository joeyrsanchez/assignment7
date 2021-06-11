package merit.america.bank.MeritBank.controllers;

import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import merit.america.bank.MeritBank.models.CDOfferings;
import merit.america.bank.MeritBank.models.MeritBank;
import org.springframework.http.HttpStatus;

@RestController
public class MeritBankController {

	@RequestMapping("/greet")
	public String greetMe() { 
		return "<html><ht>Hello Humans. Welcome to Springboot</h1></html>";

	}
	
	@RequestMapping("/cdos")
	public static List <CDOfferings> getAllCdos(){
		
		return MeritBank.getCDOfferings();
	}
	
	@PostMapping(value = "/cdo")
	@ResponseStatus(HttpStatus.CREATED)
	public CDOfferings addCDO(@RequestBody @Valid CDOfferings cdo) {
		MeritBank.addCDO(cdo);
		return cdo;
		
	}
}
