package nts.uk.ctx.sys.auth.dom;
/**
 * ユーザIDから個人社員情報を取得するのUTコード
 * @author lan_lt
 *
 */

import java.util.Optional;

import org.junit.Test;

import mockit.Expectations;
import mockit.Injectable;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.role.ContractCode;
import nts.uk.ctx.sys.shared.dom.user.DisabledSegment;
import nts.uk.ctx.sys.shared.dom.user.LoginID;
import nts.uk.ctx.sys.shared.dom.user.User;
import nts.uk.ctx.sys.shared.dom.user.password.HashPassword;
import nts.uk.ctx.sys.shared.dom.user.password.PassStatus;

public class GetPersonalEmployeeInfoByUserIdServiceTest {
	
	@Injectable
	private GetPersonalEmployeeInfoByUserIdService.Require require;
	
	/**
	 * ユーザを取得する = empty
	 */
	@Test
	public void testGet_user_empty() {
		
		String userId = "userId";
		
		new Expectations() {
			{
				require.getUser(userId);
			}
		};
		
		NtsAssert.systemError(() -> {
			GetPersonalEmployeeInfoByUserIdService.get(require, userId);
		});
	}
	
	@Test
	public void testGet_person_empty() {
		
		String userId = "userId";
		
		new Expectations() {
			{
				require.getUser(userId);
			}
		};
	}
	

	public static class Helper{
		
		public static User createUser(String userID, Optional<String> associatedPersonID) {

			return new User(userID, true, new HashPassword("password"), new LoginID("loginID"),
					new ContractCode("contractCode"), GeneralDate.ymd(9999, 12, 31), DisabledSegment.False,
					DisabledSegment.False, Optional.empty(), Optional.empty(), associatedPersonID, PassStatus.InitPassword);
		}

	}
}
