package nts.uk.ctx.at.shared.dom.workrule.vacation.specialvacation.timespecialvacation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
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
		assertThat(attendanceItemIds).extracting(d -> d).containsExactly(543, 504, 516, 1123, 1124, 1127, 1128, 1131,
		1132, 1135, 1136, 1145, 1146);
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
		
		assertThat(attendanceItemIds).extracting(d -> d).containsExactly(504, 516, 1123, 1124, 1127, 1128, 1131, 1132,
				1135, 1136, 1145, 1146);

		// 管理区分 = 管理する
		setting = TimeSpecialLeaveManagementHelper.createManagementSettingManageDistinctIsYes(ManageDistinct.YES);
		attendanceItemIds = setting.getDailyAttdItemsNotAvailable(require);
		assertThat(attendanceItemIds.isEmpty()).isTrue();
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
	 * Test [3]時間休暇が管理するか
	 * Case 1: $Option.就業.時間休暇 = true
	 */
	@Test
	public void testIsManageTimeVacation1() {
		TimeSpecialLeaveManagementSetting domain = new TimeSpecialLeaveManagementSetting("000000000003-0004",
				new TimeVacationDigestUnit(ManageDistinct.YES, TimeDigestiveUnit.OneHour));
		new Expectations() {
			{
				require.getOptionLicense();
				result = TimeSpecialLeaveManagementHelper.getOptionLicense(true);
			}
		};
		boolean isManageTimeVacation = domain.isManageTimeVacation(require);
		assertThat(isManageTimeVacation).isTrue();
	}
	
	/**
	 * Test [3]時間休暇が管理するか
	 * Case 2: $Option.就業.時間休暇 = false
	 */
	@Test
	public void testIsManageTimeVacation2() {
		TimeSpecialLeaveManagementSetting domain = new TimeSpecialLeaveManagementSetting("000000000003-0004",
				new TimeVacationDigestUnit(ManageDistinct.YES, TimeDigestiveUnit.OneHour));
		new Expectations() {
			{
				require.getOptionLicense();
				result = TimeSpecialLeaveManagementHelper.getOptionLicense(false);
			}
		};
		boolean isManageTimeVacation = domain.isManageTimeVacation(require);
		assertThat(isManageTimeVacation).isFalse();
	}
	
	/**
	 * Test [4]利用する休暇時間の消化単位をチェックする
	 * Case 1: $Option.就業.時間休暇 = true && 「休暇使用時間」 % 「@消化単位」 = 0
	 */
	@Test
	public void testCheckVacationTimeUnitUsed1() {
		TimeSpecialLeaveManagementSetting managementSetting = new TimeSpecialLeaveManagementSetting("000000000003-0004",
				new TimeVacationDigestUnit(ManageDistinct.NO, TimeDigestiveUnit.OneHour));
		new Expectations() {
			{
				require.getOptionLicense();
				result = TimeSpecialLeaveManagementHelper.getOptionLicense(true);
			}
		};
		boolean checkVacationTimeUnitUsed = managementSetting.checkVacationTimeUnitUsed(require, new AttendanceTime(600));
		assertThat(checkVacationTimeUnitUsed).isTrue();
	}
	
	/**
	 * Test [4]利用する休暇時間の消化単位をチェックする
	 * Case 2: $Option.就業.時間休暇 = true && 「休暇使用時間」 % 「@消化単位」 != 0
	 */
	@Test
	public void testCheckVacationTimeUnitUsed2() {
		TimeSpecialLeaveManagementSetting managementSetting = new TimeSpecialLeaveManagementSetting("000000000003-0004",
				new TimeVacationDigestUnit(ManageDistinct.YES, TimeDigestiveUnit.OneHour));
		new Expectations() {
			{
				require.getOptionLicense();
				result = TimeSpecialLeaveManagementHelper.getOptionLicense(true);
			}
		};
		boolean checkVacationTimeUnitUsed = managementSetting.checkVacationTimeUnitUsed(require, new AttendanceTime(11));
		assertThat(checkVacationTimeUnitUsed).isFalse();
	}
}
