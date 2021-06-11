package merit.america.bank.MeritBank.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MeritBank {
	
	private static List<CDOffering> cdos = new ArrayList<>();
	public MeritBank() {
		
	}
	
	public static List <CDOffering> getCDOfferingDummy() {
		return Arrays.asList(new CDOffering(0.025,2),
				new CDOffering(0.5,5));
	}
	public static void addCDO(CDOffering cdo) {
		cdos.add(cdo);
	}
	public static List<CDOffering> getCDOffering(){
		return cdos;
	}
}
