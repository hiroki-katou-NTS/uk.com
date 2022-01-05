package nts.uk.ctx.at.shared.dom.workrule.vacation.specialvacation.timespecialvacation;

import java.util.List;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;

public class TimeSpecialLeaveManagementSettingTest {
	@Test
	public void getters() {
		TimeSpecialLeaveManagementSetting managementSetting = new TimeSpecialLeaveManagementSetting("000000000003-0004",
				TimeDigestiveUnit.OneHour, ManageDistinct.NO);
		NtsAssert.invokeGetters(managementSetting);
	}

	/**
	 * Test [1] 時間特別休暇に対応する日次の勤怠項目を取得する
	 */
	@Test
	public void testDailyAttdItemsCorrespondSpecialLeave() {
		TimeSpecialLeaveManagementSetting setting = TimeSpecialLeaveManagementHelper
				.createManagementSettingManageDistinct();
		List<Integer> attendanceItemIds = setting.getDailyAttdItemsCorrespondSpecialLeave();
		assertThat(attendanceItemIds).extracting(d -> d).containsExactly(543, 504, 516, 1123, 1124, 1127, 1128, 1131,
				1132, 1135, 1136, 1145, 1146);
	}

	/**
	 * Test [2] 利用できない日次の勤怠項目を取得する ManageDistinct = NO
	 */
	@Test
	public void testDailyAttdItemsNotAvailable() {
		// 管理区分 = 管理しない
		TimeSpecialLeaveManagementSetting setting = TimeSpecialLeaveManagementHelper
				.createManagementSettingManageDistinctIsNo(ManageDistinct.NO);
		List<Integer> attendanceItemIds = setting.getDailyAttdItemsNotAvailable();
		assertThat(attendanceItemIds).extracting(d -> d).containsExactly(504, 516, 1123, 1124, 1127, 1128, 1131, 1132,
				1135, 1136, 1145, 1146);

		// 管理区分 = 管理する
		setting = TimeSpecialLeaveManagementHelper.createManagementSettingManageDistinctIsYes(ManageDistinct.YES);
		attendanceItemIds = setting.getDailyAttdItemsNotAvailable();
		assertThat(attendanceItemIds.isEmpty()).isTrue();
	}
}
