package nts.uk.ctx.sys.gateway.dom.login;

import java.util.Optional;

import org.junit.Test;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.gateway.dom.outage.CheckSystemAvailability;
import nts.uk.ctx.sys.gateway.dom.outage.PlannedOutage;
import nts.uk.ctx.sys.shared.dom.company.CompanyInforImport;
import nts.uk.ctx.sys.shared.dom.employee.EmployeeDataMngInfoImport;
import nts.uk.ctx.sys.shared.dom.employee.SDelAtr;
import nts.uk.ctx.sys.shared.dom.user.ContractCode;
import nts.uk.ctx.sys.shared.dom.user.DisabledSegment;
import nts.uk.ctx.sys.shared.dom.user.LoginID;
import nts.uk.ctx.sys.shared.dom.user.MailAddress;
import nts.uk.ctx.sys.shared.dom.user.User;
import nts.uk.ctx.sys.shared.dom.user.UserName;
import nts.uk.ctx.sys.shared.dom.user.password.HashPassword;
import nts.uk.ctx.sys.shared.dom.user.password.PassStatus;

public class CheckIfCanLoginTest {
	@Mocked IdentifiedEmployeeInfo identifiedEmployeeInfo;
	@Mocked CompanyInforImport companyInforImport;
	@Mocked PlannedOutage.Status status;
	
	@Injectable
	private CheckIfCanLogin.Require require;
	
	private static class Dummy{
		private static String companyId = "comcom";
		private static String companyCd = "comcom";
		private static String companyNm = "comcom";
		private static String tenantCd = "tenten";
		private static String userId = "useruser";
		private static EmployeeDataMngInfoImport employee = new EmployeeDataMngInfoImport(companyId, "perper", "empemp", "empemp", SDelAtr.NOTDELETED, null, null, null);
		private static User user = new User("useruser", false, new HashPassword("passpass"), new LoginID("loginlogin"), new ContractCode(tenantCd), GeneralDate.ymd(9999, 12, 31), DisabledSegment.False, DisabledSegment.False, Optional.of(new MailAddress("mailmail")), Optional.of(new UserName("useruser")), Optional.of("perper"), PassStatus.Official);
		private static IdentifiedEmployeeInfo identified = new IdentifiedEmployeeInfo(employee, user);
		private static CompanyInforImport companyInfo = new CompanyInforImport(companyId, companyCd, companyNm, 0);
	}

	@Test
	public void abolish_Test() {
		
		new Expectations() {{
			identifiedEmployeeInfo.getCompanyId();
			result = Dummy.companyId;
			
			require.getCompanyInforImport(Dummy.companyId);
			result = Dummy.companyInfo;
			
			companyInforImport.isAbolished();
			result = true;
		}};
		
		NtsAssert.businessException("Msg_281", () -> {
			CheckIfCanLogin.check(require, Dummy.identified);
		});
	}

	@Test
	public void notAvailable_Test() {
		
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
			result = Dummy.companyInfo;
			
			companyInforImport.isAbolished();
			result = false;
			
			identifiedEmployeeInfo.getTenantCode();
			result = Dummy.tenantCd;
			
			identifiedEmployeeInfo.getUserId();
			result = Dummy.userId;
		}};
		
		NtsAssert.businessException("Msg_285", () -> {
			CheckIfCanLogin.check(require, Dummy.identified);
		});
	}
}
