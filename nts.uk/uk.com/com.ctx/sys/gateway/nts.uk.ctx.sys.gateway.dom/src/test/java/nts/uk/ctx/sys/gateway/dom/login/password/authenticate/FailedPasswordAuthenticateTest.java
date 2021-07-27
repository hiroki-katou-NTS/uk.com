package nts.uk.ctx.sys.gateway.dom.login.password.authenticate;

import java.util.Optional;

import org.junit.Test;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.Verifications;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.AccountLockPolicy;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.locked.LockoutData;

public class FailedPasswordAuthenticateTest {

//	@Injectable
//	private FailedPasswordAuthenticate.Require faildPassAuthRequire;
//	
//	@Injectable
//	private AccountLockPolicy.Require accountLockPolicyRequire;
//	
//	@Test
//	public void called_FailurLog_Save() {
//		
//		new MockUp<PasswordAuthenticationFailureLog>(){
//			@Mock
//			public PasswordAuthenticationFailureLog failedNow(String userId, String password) {
//				return new PasswordAuthenticationFailureLog(
//						FailedPasswordHelper.DUMMY.DATETIME, 
//						FailedPasswordHelper.DUMMY.USER_ID, 
//						FailedPasswordHelper.DUMMY.PASSWORD); 
//			}
//		};
//		
//		FailedAuthenticateTask result = FailedPasswordAuthenticate.failed(faildPassAuthRequire, FailedPasswordHelper.DUMMY.EMP_INFO, FailedPasswordHelper.DUMMY.PASSWORD);
//		new Verifications() {{
//			faildPassAuthRequire.save((PasswordAuthenticationFailureLog) any);
//			times = 0;
//		}};
//		result.getFailedAuthenticate().get().run();
//		new Verifications() {{
//			faildPassAuthRequire.save((PasswordAuthenticationFailureLog) any);
//			times = 1;
//		}};
//	}
//	
//	
//	@Test
//	public void called_LockoutData_Save() {
//		
//		new MockUp<PasswordAuthenticationFailureLog>(){
//			@Mock
//			public PasswordAuthenticationFailureLog failedNow(String userId, String password) {
//				return new PasswordAuthenticationFailureLog(
//						FailedPasswordHelper.DUMMY.DATETIME, 
//						FailedPasswordHelper.DUMMY.USER_ID, 
//						FailedPasswordHelper.DUMMY.PASSWORD); 
//			}
//		};
//		
//		new MockUp<AccountLockPolicy>(){
//			@Mock
//			public Optional<LockoutData> validateAuthenticate(AccountLockPolicy.Require require, String userId) {
//				return Optional.of(FailedPasswordHelper.DUMMY.LOCKOUT_DATA);
//			}
//		};
//		
//		new Expectations() {{
//			faildPassAuthRequire.getAccountLockPolicy(FailedPasswordHelper.DUMMY.EMP_INFO.getTenantCode());
//			result = Optional.of(FailedPasswordHelper.DUMMY.ACCOUNT_LOCK_POLICY);
//		}};
//		
//		FailedAuthenticateTask result = FailedPasswordAuthenticate.failed(faildPassAuthRequire, FailedPasswordHelper.DUMMY.EMP_INFO, FailedPasswordHelper.DUMMY.PASSWORD);
//		new Verifications() {{
//			faildPassAuthRequire.save((LockoutData) any);
//			times = 0;
//		}};
//		result.getLockoutData().get().run();
//		new Verifications() {{
//			faildPassAuthRequire.save((LockoutData) any);
//			times = 1;
//		}};
//	}
//	
//	
//	@Test
//	public void called_LockoutData_PolicyEmpty() {
//		
//		new MockUp<PasswordAuthenticationFailureLog>(){
//			@Mock
//			public PasswordAuthenticationFailureLog failedNow(String userId, String password) {
//				return new PasswordAuthenticationFailureLog(
//						FailedPasswordHelper.DUMMY.DATETIME, 
//						FailedPasswordHelper.DUMMY.USER_ID, 
//						FailedPasswordHelper.DUMMY.PASSWORD); 
//			}
//		};
//		
//		new MockUp<AccountLockPolicy>(){
//			@Mock
//			public Optional<LockoutData> validateAuthenticate(AccountLockPolicy.Require require, String userId) {
//				return Optional.of(FailedPasswordHelper.DUMMY.LOCKOUT_DATA);
//			}
//		};
//		
//		new Expectations() {{
//			faildPassAuthRequire.getAccountLockPolicy(FailedPasswordHelper.DUMMY.EMP_INFO.getTenantCode());
//			result = Optional.empty();
//		}};
//		
//		FailedAuthenticateTask result = FailedPasswordAuthenticate.failed(faildPassAuthRequire, FailedPasswordHelper.DUMMY.EMP_INFO, FailedPasswordHelper.DUMMY.PASSWORD);
//		new Verifications() {{
//			faildPassAuthRequire.save((LockoutData) any);
//			times = 0;
//		}};
//		result.getLockoutData().get().run();
//		new Verifications() {{
//			faildPassAuthRequire.save((LockoutData) any);
//			times = 1;
//		}};
//	}
}
