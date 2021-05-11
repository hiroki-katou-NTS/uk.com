package nts.uk.ctx.sys.gateway.dom.login.password.authenticate;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import lombok.val;
import nts.arc.time.GeneralDateTime;

public class PasswordAuthenticationFailureLogTest {
	
	private static class Dummy{
		final static GeneralDateTime dateTime = GeneralDateTime.now(); 
		final static String userId = "useruser";
	}
	
	@Test
	public void stringCut() {
		val longPassword 	= "12345678901234567890"
							+ "12345678901234567890"
							+ "12345678901234567890"
							+ "12345678901234567890"
							+ "12345678901234567890"
							+ "xxxxx";
		
		val result = new PasswordAuthenticationFailureLog(Dummy.dateTime, Dummy.userId, longPassword);
		
		assertThat(result.getTriedPassword().length() <= 100).isTrue();
	}
}
