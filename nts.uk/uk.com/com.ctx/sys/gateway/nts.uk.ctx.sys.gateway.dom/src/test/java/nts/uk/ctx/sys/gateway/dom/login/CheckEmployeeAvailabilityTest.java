package nts.uk.ctx.sys.gateway.dom.login;

import java.util.Optional;

import org.junit.Test;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.sys.shared.dom.employee.EmployeeDataMngInfoImport;
import nts.uk.ctx.sys.shared.dom.employee.SDelAtr;
import nts.uk.ctx.sys.shared.dom.employee.employment.SyaEmpHistImport;
import nts.uk.ctx.sys.shared.dom.employee.sycompany.SyaCompanyHistImport;
import nts.uk.ctx.sys.shared.dom.employee.syjobtitle.SyaJobHistImport;
import nts.uk.ctx.sys.shared.dom.employee.syworkplace.SyaWkpHistImport;
import nts.uk.ctx.sys.shared.dom.user.ContractCode;
import nts.uk.ctx.sys.shared.dom.user.DisabledSegment;
import nts.uk.ctx.sys.shared.dom.user.LoginID;
import nts.uk.ctx.sys.shared.dom.user.MailAddress;
import nts.uk.ctx.sys.shared.dom.user.User;
import nts.uk.ctx.sys.shared.dom.user.UserName;
import nts.uk.ctx.sys.shared.dom.user.password.HashPassword;
import nts.uk.ctx.sys.shared.dom.user.password.PassStatus;

public class CheckEmployeeAvailabilityTest {
	@Mocked EmployeeDataMngInfoImport employeeDataMngInfoImport;
	
	@Injectable
	private CheckEmployeeAvailability.Require require;
	
	private static class Dummy{
		private static String companyId = "comcom";
		private static String companyCd = "comcom";
		private static String tenantCd = "tenten";
		private static String employeeId = "empemp";
		private static EmployeeDataMngInfoImport employee = new EmployeeDataMngInfoImport(companyId, "perper", "empemp", "empemp", SDelAtr.NOTDELETED, null, null, null);
		private static User user = new User("useruser", false, new HashPassword("passpass"), new LoginID("loginlogin"), new ContractCode(tenantCd), GeneralDate.ymd(9999, 12, 31), DisabledSegment.False, DisabledSegment.False, Optional.of(new MailAddress("mailmail")), Optional.of(new UserName("useruser")), Optional.of("perper"), PassStatus.Official);
		private static IdentifiedEmployeeInfo identified = new IdentifiedEmployeeInfo(employee, user);
		private static GeneralDate today = GeneralDate.today();
		private static SyaCompanyHistImport comHistImp = new SyaCompanyHistImport(employeeId, companyCd, new DatePeriod(GeneralDate.ymd(1900, 1, 1), GeneralDate.ymd(9999, 12, 31)));
		private static SyaEmpHistImport empHistImp = new SyaEmpHistImport(employeeId, "idid", "namename", new DatePeriod(GeneralDate.ymd(1900, 1, 1), GeneralDate.ymd(9999, 12, 31)));
		private static SyaJobHistImport jobHistImp = new SyaJobHistImport(employeeId, "idid", "namename", new DatePeriod(GeneralDate.ymd(1900, 1, 1), GeneralDate.ymd(9999, 12, 31)));
		private static SyaWkpHistImport wkpHistImp = new SyaWkpHistImport(employeeId, "idid", "namename", new DatePeriod(GeneralDate.ymd(1900, 1, 1), GeneralDate.ymd(9999, 12, 31)));
	}

	@Test
	public void not_affCom_Test() {
		
		new Expectations() {{
			employeeDataMngInfoImport.getEmployeeId();
			result = Dummy.employeeId;
			
			require.getCompanyHist(Dummy.employeeId, Dummy.today);
			result = Optional.empty();
		}};
		
		NtsAssert.businessException("Msg_2169", () ->{
			CheckEmployeeAvailability.check(require, Dummy.identified);
		});
	}

	@Test
	public void not_affEmp_Test() {
		
		new Expectations() {{
			employeeDataMngInfoImport.getEmployeeId();
			result = Dummy.employeeId;
			
			require.getCompanyHist(Dummy.employeeId, Dummy.today);
			result = Optional.of(Dummy.comHistImp);
			
			require.getEmploymentHist(Dummy.employeeId, Dummy.today);
			result = Optional.empty();
			
			require.getJobtitleHist(Dummy.employeeId, Dummy.today);
			result = Optional.of(Dummy.jobHistImp);
			
			require.getWorkplaceHist(Dummy.employeeId, Dummy.today);
			result = Optional.of(Dummy.wkpHistImp);
		}};
		
		NtsAssert.businessException("Msg_1420", () ->{
			CheckEmployeeAvailability.check(require, Dummy.identified);
		});
	}

	@Test
	public void not_affJob_Test() {
		
		new Expectations() {{
			employeeDataMngInfoImport.getEmployeeId();
			result = Dummy.employeeId;
			
			require.getCompanyHist(Dummy.employeeId, Dummy.today);
			result = Optional.of(Dummy.comHistImp);
			
			require.getEmploymentHist(Dummy.employeeId, Dummy.today);
			result = Optional.of(Dummy.empHistImp);
			
			require.getJobtitleHist(Dummy.employeeId, Dummy.today);
			result = Optional.empty();
			
			require.getWorkplaceHist(Dummy.employeeId, Dummy.today);
			result = Optional.of(Dummy.wkpHistImp);
		}};
		
		NtsAssert.businessException("Msg_1420", () ->{
			CheckEmployeeAvailability.check(require, Dummy.identified);
		});
	}

	@Test
	public void not_affWkp_Test() {
		
		new Expectations() {{
			employeeDataMngInfoImport.getEmployeeId();
			result = Dummy.employeeId;
			
			require.getCompanyHist(Dummy.employeeId, Dummy.today);
			result = Optional.of(Dummy.comHistImp);
			
			require.getEmploymentHist(Dummy.employeeId, Dummy.today);
			result = Optional.of(Dummy.empHistImp);
			
			require.getJobtitleHist(Dummy.employeeId, Dummy.today);
			result = Optional.of(Dummy.jobHistImp);
			
			require.getWorkplaceHist(Dummy.employeeId, Dummy.today);
			result = Optional.empty();
		}};
		
		NtsAssert.businessException("Msg_1420", () ->{
			CheckEmployeeAvailability.check(require, Dummy.identified);
		});
	}

}
