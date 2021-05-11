package nts.uk.ctx.sys.gateway.dom.login.password.identification;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import lombok.val;
import nts.arc.time.GeneralDateTime;

public class PasswordAuthIdentificationFailureLogTest {
	
	private static class Dummy{
		final static GeneralDateTime dateTime = GeneralDateTime.now(); 
		final static String companyId = "comcom";
	}
	
	@Test
	public void stringCut() {
		val longEmployeeCode 	= "12345678901234567890"
								+ "12345678901234567890"
								+ "12345678901234567890"
								+ "12345678901234567890"
								+ "12345678901234567890"
								+ "xxxxx";
		
		val result = new PasswordAuthIdentificationFailureLog(Dummy.dateTime, Dummy.companyId, longEmployeeCode);
		
		assertThat(result.getTriedEmployeeCode().length() <= 100).isTrue();
	}
}
