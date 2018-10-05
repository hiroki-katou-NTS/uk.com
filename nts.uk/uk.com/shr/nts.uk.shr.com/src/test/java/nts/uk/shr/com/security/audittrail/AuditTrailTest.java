package nts.uk.shr.com.security.audittrail;

import static mockit.Deencapsulation.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.shr.com.security.audittrail.correction.content.UserInfo;
import nts.uk.shr.com.security.audittrail.correction.content.CorrectionAttr;
import nts.uk.shr.com.security.audittrail.correction.content.DataValueAttribute;
import nts.uk.shr.com.security.audittrail.correction.content.ItemInfo;
import nts.uk.shr.com.security.audittrail.correction.content.DataCorrectionLog;
import nts.uk.shr.com.security.audittrail.correction.content.TargetDataKey;
import nts.uk.shr.com.security.audittrail.correction.content.TargetDataType;

public class AuditTrailTest {

	private UserInfoAdaptorForLog userInfoAdaptor = new UserInfoAdaptorForLog() {
		
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

		@Override
		public List<UserInfo> findByEmployeeId(List<String> employeeIds) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<UserInfo> findByUserId(List<String> userIds) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public UserInfo findByEmployeeIdAndCompanyId(String employeeId, String companyId) {
			// TODO Auto-generated method stub
			return null;
		}
	};

	@Test
	public void testName() {
		
		String employeeId = "a";
		val date = GeneralDate.ymd(2018, 4, 1);
		String itemName = "遅刻回数";
		
		val targetInfo = new DataCorrectionLog(
				"opid1",
				userInfoAdaptor.findByEmployeeId(employeeId),
				TargetDataType.DAILY_RECORD,
				TargetDataKey.of(date),
				CorrectionAttr.EDIT,
				ItemInfo.create(IdentifierUtil.randomUniqueId(), "遅刻回数", DataValueAttribute.COUNT, 1, 2),
				100);
	}

}
