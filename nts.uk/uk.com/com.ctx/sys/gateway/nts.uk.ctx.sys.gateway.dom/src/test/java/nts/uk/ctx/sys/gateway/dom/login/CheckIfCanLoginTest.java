package nts.uk.ctx.sys.gateway.dom.login;

import org.junit.Test;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.sys.gateway.dom.outage.CheckSystemAvailability;
import nts.uk.ctx.sys.gateway.dom.outage.PlannedOutage;
import nts.uk.ctx.sys.shared.dom.company.CompanyInforImport;

public class CheckIfCanLoginTest {
	@Mocked IdentifiedEmployeeInfo identifiedEmployeeInfo;
	@Mocked CompanyInforImport companyInforImport;
	@Mocked PlannedOutage.Status status;
	
	@Injectable
	private CheckIfCanLogin.Require require;
	
	private static class Dummy{
		private static String companyId = "comcom";
		private static String tenantCd = "tenten";
		private static String userId = "useruser";
	}

	@Test
	public void abolish() {
		
		new Expectations() {{
			identifiedEmployeeInfo.getCompanyId();
			result = Dummy.companyId;
			
			require.getCompanyInforImport(Dummy.companyId);
			result = companyInforImport;
			
			companyInforImport.isAbolished();
			result = true;
		}};
		
		NtsAssert.businessException("Msg_281", () -> {
			CheckIfCanLogin.check(require, identifiedEmployeeInfo);
		});
	}

	@Test
	public void notAvailable() {
		
		new MockUp<CheckSystemAvailability>() {
			@Mock
			public PlannedOutage.Status isAvailable(CheckSystemAvailability.Require require, String tenantCode, String companyId, String userId) {
				return PlannedOutage.Status.notAvailable("dameyo");
			}
		};
		
		new Expectations() {{
			identifiedEmployeeInfo.getCompanyId();
			result = Dummy.companyId;
			
			require.getCompanyInforImport(Dummy.companyId);
			result = companyInforImport;
			
			companyInforImport.isAbolished();
			result = false;
			
			identifiedEmployeeInfo.getTenantCode();
			result = Dummy.tenantCd;
			
			identifiedEmployeeInfo.getUserId();
			result = Dummy.userId;
		}};
		
		NtsAssert.businessException("Msg_285", () -> {
			CheckIfCanLogin.check(require, identifiedEmployeeInfo);
		});
	}
}
