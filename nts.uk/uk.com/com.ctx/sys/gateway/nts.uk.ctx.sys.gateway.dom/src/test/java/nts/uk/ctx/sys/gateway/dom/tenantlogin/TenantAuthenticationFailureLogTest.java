package nts.uk.ctx.sys.gateway.dom.tenantlogin;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import lombok.val;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.gateway.dom.login.LoginClient;

public class TenantAuthenticationFailureLogTest {
	
	private static class Dummy{
		static final GeneralDateTime DATETIME = null; 
		static final LoginClient LOGIN_CLIENT = null;
	}
	
	@Test
	public void stringCut_long() {
		val longString 	= "12345678901234567890123456789012345678901234567890"
						+ "12345678901234567890123456789012345678901234567890"
						+ "x";
		
		val result_long = new TenantAuthenticationFailureLog(Dummy.DATETIME, Dummy.LOGIN_CLIENT, longString, longString);
		assertThat(result_long.getTriedTenantCode().length() == 100).isTrue();
		assertThat(result_long.getTriedPassword().length() == 100).isTrue();
	}
	
	@Test
	public void stringCut_just() {
		val justString 	= "12345678901234567890123456789012345678901234567890"
						+ "12345678901234567890123456789012345678901234567890";
		
		val result_just = new TenantAuthenticationFailureLog(Dummy.DATETIME, Dummy.LOGIN_CLIENT, justString, justString);
		assertThat(result_just.getTriedTenantCode().length() == 100).isTrue();
		assertThat(result_just.getTriedPassword().length() == 100).isTrue();
	}
	
	@Test
	public void stringCut_short() {
		val shortString = "12345678901234567890123456789012345678901234567890"
						+ "1234567890123456789012345678901234567890123456789";
		
		val result_short = new TenantAuthenticationFailureLog(Dummy.DATETIME, Dummy.LOGIN_CLIENT, shortString, shortString);
		assertThat(result_short.getTriedTenantCode().length() < 100).isTrue();
		assertThat(result_short.getTriedPassword().length() < 100).isTrue();
	}
	
	@Test
	public void stringCut_null() {
		val nullString 	= "";
		
		val result_null = new TenantAuthenticationFailureLog(Dummy.DATETIME, Dummy.LOGIN_CLIENT, nullString, nullString);
		assertThat(result_null.getTriedTenantCode().length() < 100).isTrue();
		assertThat(result_null.getTriedPassword().length() < 100).isTrue();
	}
}
