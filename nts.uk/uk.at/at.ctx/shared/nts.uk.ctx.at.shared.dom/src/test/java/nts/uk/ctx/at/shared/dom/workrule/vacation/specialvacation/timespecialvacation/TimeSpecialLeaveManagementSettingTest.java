package nts.uk.ctx.at.shared.dom.workrule.vacation.specialvacation.timespecialvacation;


import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;

import static org.assertj.core.api.Assertions.assertThat;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestUnit;
import nts.uk.shr.com.license.option.OptionLicense;

@RunWith(JMockit.class)
public class TimeSpecialLeaveManagementSettingTest {
	@Injectable
	private TimeVacationDigestUnit.Require require;
	
	@Test
	public void getters() {
		TimeSpecialLeaveManagementSetting managementSetting = new TimeSpecialLeaveManagementSetting("000000000003-0004",
				new TimeVacationDigestUnit(ManageDistinct.NO, TimeDigestiveUnit.OneHour));
		NtsAssert.invokeGetters(managementSetting);
	}
	
	/**
     * Test [1] 時間特別休暇に対応する日次の勤怠項目を取得する
     */
	@Test
	public void testDailyAttdItemsCorrespondSpecialLeave() {
		TimeSpecialLeaveManagementSetting setting = TimeSpecialLeaveManagementHelper.createManagementSettingManageDistinct();
		List<Integer> attendanceItemIds = setting.getDailyAttdItemsCorrespondSpecialLeave();
		assertThat( attendanceItemIds )
		.extracting( d -> d)
		.containsExactly(543,504,516,1123,1124,1127,1128,1131,1132,1135,1136,1145,1146);
	}
	
	/**
     * Test [2] 利用できない日次の勤怠項目を取得する ManageDistinct = NO
     */
	@Test
	public void testDailyAttdItemsNotAvailable() {
		TimeSpecialLeaveManagementSetting setting = TimeSpecialLeaveManagementHelper.createManagementSettingManageDistinctIsNo(ManageDistinct.NO);
		new Expectations() {
			{
				require.getOptionLicense();
				result = new OptionLicense() {};
			}
		};
		List<Integer> attendanceItemIds = setting.getDailyAttdItemsNotAvailable(require);
		assertThat( attendanceItemIds )
		.extracting( d -> d)
		.containsExactly(504,516,1123,1124,1127,1128,1131,1132,1135,1136,1145,1146);
	}
	
	/**
     * Test [2] 利用できない日次の勤怠項目を取得する ManageDistinct = YES
     */
	@Test
	public void testDailyAttdItemsNotAvailable_isEmpty() {
		TimeSpecialLeaveManagementSetting setting = TimeSpecialLeaveManagementHelper.createManagementSettingManageDistinctIsYes(ManageDistinct.YES);
		new Expectations() {
			{
				require.getOptionLicense();
				result = new OptionLicense() {};
			}
		};
		List<Integer> attendanceItemIds = setting.getDailyAttdItemsNotAvailable(require);
		assertThat( attendanceItemIds.isEmpty());
	}
	
	/**
	 * Test [4]利用する休暇時間の消化単位をチェックする
	 */
	@Test
	public void testCheckVacationTimeUnitUsed() {
		TimeSpecialLeaveManagementSetting managementSetting = new TimeSpecialLeaveManagementSetting("000000000003-0004",
				new TimeVacationDigestUnit(ManageDistinct.NO, TimeDigestiveUnit.OneHour));
		new Expectations() {
			{
				require.getOptionLicense();
				result = new OptionLicense() {};
			}
		};
		boolean checkVacationTimeUnitUsed = managementSetting.checkVacationTimeUnitUsed(require, AttendanceTime.ZERO);
		assertThat(checkVacationTimeUnitUsed);
	}
}
