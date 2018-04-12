package nts.uk.shr.com.security.audittrail;

import static mockit.Deencapsulation.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import lombok.val;
import nts.arc.time.GeneralDate;

public class AuditTrailTest {

	private UserInfoAdaptorForLog userInfoAdaptor = new UserInfoAdaptorForLog() {
		
		@Override
		public UserInfo fingByPersonalId(String personalId) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public UserInfo findByUserId(String userId) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public UserInfo findByEmployeeId(String employeeId) {
			// TODO Auto-generated method stub
			return null;
		}
	};

	@Test
	public void testName() {
		
		String employeeId = "a";
		val date = GeneralDate.ymd(2018, 4, 1);
		String itemName = "遅刻回数";
		
		val targetInfo = new LogTargetInfo(
				userInfoAdaptor.findByEmployeeId(employeeId),
				TargetDataType.DAILY_RECORD,
				TargetDataKey.of(date),
				CorrectionAttr.EDIT,
				ItemInfo.create("遅刻回数", DataValueAttribute.COUNT, 1, 2),
				100);
	}

}
