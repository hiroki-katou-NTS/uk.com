package nts.uk.ctx.sys.gateway.dom.login;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Test;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import nts.arc.error.BusinessException;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.AccountLockPolicy;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.ErrorCount;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.LockInterval;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.LockOutMessage;
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

public class CheckUserAvailabilityTest {
	@Mocked IdentifiedEmployeeInfo identifiedEmployeeInfo;
	@Mocked User user;
	@Mocked AccountLockPolicy accountLockPolicy;
	
	@Injectable
	private CheckUserAvailability.Require require;
	
	
	private static class Dummy {
		private static String companyId = "comcom";
		private static String tenantCd = "tenten";
		private static String userId = "useruser";
		private static EmployeeDataMngInfoImport employee = new EmployeeDataMngInfoImport(companyId, "perper", "empemp", "empemp", SDelAtr.NOTDELETED, null, null, null);
		private static User user = new User("useruser", false, new HashPassword("passpass"), new LoginID("loginlogin"), new ContractCode(tenantCd), GeneralDate.ymd(9999, 12, 31), DisabledSegment.False, DisabledSegment.False, Optional.of(new MailAddress("mailmail")), Optional.of(new UserName("useruser")), Optional.of("perper"), PassStatus.Official);
		private static IdentifiedEmployeeInfo identified = new IdentifiedEmployeeInfo(employee, user);
		private static GeneralDate today = GeneralDate.today();
		private static LockOutMessage message = new LockOutMessage("messemesse");
		private static AccountLockPolicy policy = new AccountLockPolicy(new ContractCode(tenantCd), new ErrorCount(new BigDecimal(1)), new LockInterval(1), message, true);
	}

	@Test
	public void available_Test() {
		new Expectations() {{
			identifiedEmployeeInfo.getUser();
			result = Dummy.user;
			
			user.isAvailableAt(Dummy.today);
			result = false;
		}};
		NtsAssert.businessException("Msg_316", () -> {
			CheckUserAvailability.check(require, Dummy.identified);
		});
	}

	@Test
	public void systemStop_Test() {
		new Expectations() {{
			identifiedEmployeeInfo.getUser();
			result = Dummy.user;
			
			user.isAvailableAt(Dummy.today);
			result = true;
			
			identifiedEmployeeInfo.getTenantCode();
			result = Dummy.tenantCd;
			
			identifiedEmployeeInfo.getUserId();
			result = Dummy.userId;
			
			require.getAccountLockPolicy(Dummy.tenantCd);
			result = Optional.of(Dummy.policy);
			
			accountLockPolicy.isLocked(require, Dummy.userId);
			result = true;
			
			accountLockPolicy.getLockOutMessage();
			result = Dummy.message;
		}};
		
		assertThatThrownBy(() -> CheckUserAvailability.check(require, Dummy.identified))
		.isInstanceOfSatisfying(BusinessException.class, e -> {
			assertThat(e.getMessage()).isEqualTo(Dummy.message.v());
		});
	}
}
