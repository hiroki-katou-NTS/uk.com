package nts.uk.ctx.sys.gateway.dom.login.password.authenticate;

import java.util.Optional;

import org.junit.Test;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.Verifications;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.gateway.dom.login.IdentifiedEmployeeInfo;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.AccountLockPolicy;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.locked.LockoutData;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.locked.LoginMethod;
import nts.uk.ctx.sys.shared.dom.employee.EmployeeDataMngInfoImport;
import nts.uk.ctx.sys.shared.dom.user.ContractCode;
import nts.uk.ctx.sys.shared.dom.user.User;

public class FailedPasswordAuthenticateTest {

	@Injectable
	private FailedPasswordAuthenticate.Require faildPassAuthRequire;
	
	@Injectable
	private AccountLockPolicy.Require accountLockPolicyRequire;
	
	@Test
	public void called_FailurLog_Save() {
		
		new MockUp<PasswordAuthenticateFailureLog>(){
			@Mock
			public PasswordAuthenticateFailureLog failedNow(String userId, String password) {
				return new PasswordAuthenticateFailureLog(
						GeneralDateTime.ymdhms(2000, 1, 1, 1, 0, 0), 
						"user", 
						"password"); 
			}
		};
		
		EmployeeDataMngInfoImport imp = PasswordAuthenticateWithEmployeeCodeTestHelper.DUMMY_IMPORTED;
		User user = PasswordAuthenticateWithEmployeeCodeTestHelper.USER;
		
		IdentifiedEmployeeInfo empInfo = new IdentifiedEmployeeInfo(imp, user);
		
		AtomTask persist = FailedPasswordAuthenticate.failed(faildPassAuthRequire, empInfo, "password").getFailedAuthenticate().get();
		new Verifications() {{
			faildPassAuthRequire.save((PasswordAuthenticateFailureLog) any);
			times = 0;
		}};
		persist.run();
		new Verifications() {{
			faildPassAuthRequire.save((PasswordAuthenticateFailureLog) any);
			times = 1;
		}};
	}
	
	@Test
	public void called_LockoutData_Save() {
		EmployeeDataMngInfoImport imp = PasswordAuthenticateWithEmployeeCodeTestHelper.DUMMY_IMPORTED;
		User user = PasswordAuthenticateWithEmployeeCodeTestHelper.USER;
		
		IdentifiedEmployeeInfo empInfo = new IdentifiedEmployeeInfo(imp, user);
		
		Optional<LockoutData> lockoutData = Optional.of(
				LockoutData.autoLock(
						new ContractCode(""), 
						"", 
						LoginMethod.NORMAL_LOGIN));
		
		new MockUp<PasswordAuthenticateFailureLog>(){
			@Mock
			public PasswordAuthenticateFailureLog failedNow(String userId, String password) {
				return new PasswordAuthenticateFailureLog(
						GeneralDateTime.ymdhms(2000, 1, 1, 1, 0, 0), 
						"user", 
						"password"); 
			}
		};
		
		new MockUp<AccountLockPolicy>(){
			@Mock
			public Optional<LockoutData> validateAuthenticate(AccountLockPolicy.Require require, String userId) {
				return lockoutData;
			}
		};
		Optional<AccountLockPolicy> policy = 
				Optional.of(AccountLockPolicy.createFromJavaType("", 0, 0, "", true));
		new Expectations() {{
			faildPassAuthRequire.getAccountLockPolicy(empInfo.getTenantCode());
			result = policy;
		}};

		
		AtomTask persist = FailedPasswordAuthenticate.failed(faildPassAuthRequire, empInfo, "password").getLockoutData().get();
		new Verifications() {{
			faildPassAuthRequire.save((LockoutData) any);
			times = 0;
		}};
		persist.run();
		new Verifications() {{
			faildPassAuthRequire.save((LockoutData) any);
			times = 1;
		}};
	}

}
