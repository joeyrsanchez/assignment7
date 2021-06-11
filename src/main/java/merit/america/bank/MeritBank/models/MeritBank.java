package merit.america.bank.MeritBank.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MeritBank {
	
	private static List<CDOfferings> cdos = new ArrayList<>();
	public MeritBank() {
		
	}
	
	public static List <CDOfferings> getCDOfferingsDummy() {
		return Arrays.asList(new CDOfferings(0.025,2),
				new CDOfferings(0.5,5));
	}
	public static void addCDO(CDOfferings cdo) {
		cdos.add(cdo);
	}
	public static List<CDOfferings> getCDOfferings(){
		return cdos;
	}

}
