package nts.uk.ctx.sys.gateway.dom.login;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.Test;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import nts.arc.error.BusinessException;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.AccountLockPolicy;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.LockOutMessage;
import nts.uk.ctx.sys.shared.dom.user.User;

public class CheckUserAvailabilityTest {
//	@Mocked IdentifiedEmployeeInfo identifiedEmployeeInfo;
//	@Mocked User user;
//	@Mocked AccountLockPolicy accountLockPolicy;
//	
//	@Injectable
//	private CheckUserAvailability.Require require;
//	
//	
//	private static class Dummy {
//		private static String tenantCd = "tenten";
//		private static String userId = "useruser";
//		private static GeneralDate today = GeneralDate.today();
//		private static LockOutMessage message = new LockOutMessage("messemesse");
//	}
//
//	@Test
//	public void available() {
//		new Expectations() {{
//			identifiedEmployeeInfo.getUser();
//			result = user;
//			
//			user.isAvailableAt(Dummy.today);
//			result = false;
//		}};
//		NtsAssert.businessException("Msg_316", () -> {
//			CheckUserAvailability.check(require, identifiedEmployeeInfo);
//		});
//	}
//
//	@Test
//	public void systemStop() {
//		new Expectations() {{
//			identifiedEmployeeInfo.getUser();
//			result = user;
//			
//			user.isAvailableAt(Dummy.today);
//			result = true;
//			
//			identifiedEmployeeInfo.getTenantCode();
//			result = Dummy.tenantCd;
//			
//			identifiedEmployeeInfo.getUserId();
//			result = Dummy.userId;
//			
//			require.getAccountLockPolicy(Dummy.tenantCd);
//			result = Optional.of(accountLockPolicy);
//			
//			accountLockPolicy.isLocked(require, Dummy.userId);
//			result = true;
//			
//			accountLockPolicy.getLockOutMessage();
//			result = Dummy.message;
//		}};
//		
//		assertThatThrownBy(() -> CheckUserAvailability.check(require, identifiedEmployeeInfo))
//		.isInstanceOfSatisfying(BusinessException.class, e -> {
//			assertThat(e.getMessage()).isEqualTo(Dummy.message.v());
//		});
//	}
}
