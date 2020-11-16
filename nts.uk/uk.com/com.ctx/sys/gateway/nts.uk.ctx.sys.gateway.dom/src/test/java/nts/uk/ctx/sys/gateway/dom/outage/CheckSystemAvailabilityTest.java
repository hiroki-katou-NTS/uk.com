package nts.uk.ctx.sys.gateway.dom.outage;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.Test;

import lombok.val;
import mockit.Expectations;
import mockit.Mocked;
import nts.uk.shr.com.context.loginuser.role.LoginUserRoles;

public class CheckSystemAvailabilityTest {

	private static final String TENANT_CODE = "000000000099";
	private static final String COMPANY_ID = "000000000099-0001";
	private static final String USER_ID = "user";
	private static final String MESSAGE = "message";

	@Mocked CheckSystemAvailability.Require require;
	@Mocked PlannedOutageByTenant outageByTenant;
	@Mocked PlannedOutageByCompany outageByCompany;

	// システム利用停止なし
	@Test
	public void no_outage() {
		
		new Expectations() {{
			require.getPlannedOutageByTenant(TENANT_CODE);
			require.getPlannedOutageByCompany(COMPANY_ID);
		}};
		
		val actual = CheckSystemAvailability.isAvailable(require, TENANT_CODE, COMPANY_ID, USER_ID);
		assertStatusAvailable(actual);
	}

	// システム利用停止のデータはあるが「通常運用中」ステータス
	@Test
	public void available_outage() {
		
		new Expectations() {{
			outageByTenant.statusFor((LoginUserRoles) any);
			result = PlannedOutage.Status.available();
			
			outageByCompany.statusFor((LoginUserRoles) any);
			result = PlannedOutage.Status.available();
			
			require.getPlannedOutageByTenant(TENANT_CODE);
			result = Optional.of(outageByTenant);
			
			require.getPlannedOutageByCompany(COMPANY_ID);
			result = Optional.of(outageByCompany);
		}};
		
		val actual = CheckSystemAvailability.isAvailable(require, TENANT_CODE, COMPANY_ID, USER_ID);
		assertStatusAvailable(actual);
	}

	// テナントで利用停止だが利用可能
	@Test
	public void outage_tenant_available() {
		
		new Expectations() {{
			outageByTenant.statusFor((LoginUserRoles) any);
			result = PlannedOutage.Status.availableBut(MESSAGE);

			require.getPlannedOutageByTenant(TENANT_CODE);
			result = Optional.of(outageByTenant);
		}};

		val actual = CheckSystemAvailability.isAvailable(require, TENANT_CODE, COMPANY_ID, USER_ID);
		assertStatus(actual, true, MESSAGE);
	}

	// テナントで利用停止により利用不可
	@Test
	public void outage_tenant_reject() {
		
		new Expectations() {{
			outageByTenant.statusFor((LoginUserRoles) any);
			result = PlannedOutage.Status.notAvailable(MESSAGE);

			require.getPlannedOutageByTenant(TENANT_CODE);
			result = Optional.of(outageByTenant);
		}};

		val actual = CheckSystemAvailability.isAvailable(require, TENANT_CODE, COMPANY_ID, USER_ID);
		assertStatus(actual, false, MESSAGE);
	}

	// 会社で利用停止だが利用可能
	@Test
	public void outage_company_available() {
		
		new Expectations() {{
			outageByCompany.statusFor((LoginUserRoles) any);
			result = PlannedOutage.Status.availableBut(MESSAGE);
			
			require.getPlannedOutageByCompany(COMPANY_ID);
			result = Optional.of(outageByCompany);
		}};

		val actual = CheckSystemAvailability.isAvailable(require, TENANT_CODE, COMPANY_ID, USER_ID);
		assertStatus(actual, true, MESSAGE);
	}

	// 会社で利用停止により利用不可
	@Test
	public void outage_company_reject() {
		
		new Expectations() {{
			outageByCompany.statusFor((LoginUserRoles) any);
			result = PlannedOutage.Status.notAvailable(MESSAGE);
			
			require.getPlannedOutageByCompany(COMPANY_ID);
			result = Optional.of(outageByCompany);
		}};

		val actual = CheckSystemAvailability.isAvailable(require, TENANT_CODE, COMPANY_ID, USER_ID);
		assertStatus(actual, false, MESSAGE);
	}

	private static void assertStatusAvailable(PlannedOutage.Status actual) {
		assertThat(actual.isAvailable()).isTrue();
		assertThat(actual.getMessage().isPresent()).isFalse();
	}
	
	private static void assertStatus(PlannedOutage.Status actual, boolean isAvailable, String message) {
		assertThat(actual.isAvailable()).isEqualTo(isAvailable);
		assertThat(actual.getMessage().get()).isEqualTo(message);
	}
}
