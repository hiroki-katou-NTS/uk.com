package nts.uk.ctx.sys.gateway.dom.tenantlogin;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

public class TenantAuthenticationTest {
	
	private static class Dummy{
		static final String TENANT_CODE = "dummy";
		static final String HASHED_PASSWORD = "dummy";
		static final GeneralDate START_DATE = null;
		static final GeneralDate END_DATE = null;
		static final DatePeriod PERIOD = new DatePeriod(START_DATE, END_DATE);
	}
	
	@Test
	public void authentication_verify() {
		String tenantCode = "000000000000";
		String hashedPassword = "1$13f572fec81943170c490f960d41a0a1eb85a77026d4c2e141ab447aa90db23e";
		
		TenantAuthentication target = new TenantAuthentication(tenantCode, hashedPassword, Dummy.PERIOD);
		
		val result_success = target.verifyPassword("123");
		assertThat(result_success).isEqualTo(true);
		val result_failer = target.verifyPassword("456");
		assertThat(result_failer).isEqualTo(false);
	}
	
	@Test
	public void authentication_available_success() {
		DatePeriod period = new DatePeriod(GeneralDate.ymd(2020, 4, 1), GeneralDate.ymd(2020, 4, 30));
		
		TenantAuthentication target = new TenantAuthentication(Dummy.TENANT_CODE, Dummy.HASHED_PASSWORD, period);
		
		val result_success = target.isAvailableAt(GeneralDate.ymd(2020, 4, 15));
		assertThat(result_success).isEqualTo(true);
		val result_failer = target.isAvailableAt(GeneralDate.ymd(2020, 5, 15));
		assertThat(result_failer).isEqualTo(false);
	}
}
