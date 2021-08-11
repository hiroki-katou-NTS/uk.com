package nts.uk.ctx.sys.gateway.dom.tenantlogin;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

public class TenantAuthenticationTest {
	
	static class Dummy{
		
		private static String tenantCode = "000000000000";
		private static String hashedPassword = "1$13f572fec81943170c490f960d41a0a1eb85a77026d4c2e141ab447aa90db23e";
		private static GeneralDate startDate = GeneralDate.ymd(2020, 4, 1);
		private static GeneralDate endDate = GeneralDate.ymd(2020, 4, 30);
		private static DatePeriod period = new DatePeriod(startDate, endDate);
	}


	@Test
	public void authentication_verify_success() {
		String tenantCode = "000000000000";
		String hashedPassword = "1$13f572fec81943170c490f960d41a0a1eb85a77026d4c2e141ab447aa90db23e";
		
		TenantAuthentication ta = new TenantAuthentication(
				tenantCode, 
				hashedPassword, 
				Dummy.period);
		
		val result = ta.verify("123");
		assertThat(result).isEqualTo(true);
	}
	
	@Test
	public void authentication_verify_failed() {
		String tenantCode = "000000000000";
		String hashedPassword = "1$13f572fec81943170c490f960d41a0a1eb85a77026d4c2e141ab447aa90db23e";
		
		TenantAuthentication ta = new TenantAuthentication(
				tenantCode, 
				hashedPassword, 
				Dummy.period);
		
		val result = ta.verify("456");
		assertThat(result).isEqualTo(false);
	}
	
	@Test
	public void authentication_available_success() {
		
		DatePeriod period = new DatePeriod(GeneralDate.ymd(2020, 4, 1), GeneralDate.ymd(2020, 4, 30));
		
		TenantAuthentication ta = new TenantAuthentication(Dummy.tenantCode, Dummy.hashedPassword, period);
		
		val result = ta.isAvailableAt(GeneralDate.ymd(2020, 4, 15));
		assertThat(result).isEqualTo(true);
	}
	
	@Test
	public void authentication_available_failed() {
		
		DatePeriod period = new DatePeriod(GeneralDate.ymd(2020, 4, 1), GeneralDate.ymd(2020, 4, 30));
		
		TenantAuthentication ta = new TenantAuthentication(Dummy.tenantCode, Dummy.hashedPassword, period);
		
		val result = ta.isAvailableAt(GeneralDate.ymd(2020, 5, 15));
		assertThat(result).isEqualTo(false);
	}
}
