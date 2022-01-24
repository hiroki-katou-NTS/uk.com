package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

public class CompensatoryLeaveComSettingTest {
	@Test
	public void getters() {
		CompensatoryLeaveComSetting comSetting = new CompensatoryLeaveComSetting("000000000003-0006", ManageDistinct.NO,
				CompensatoryLeaveComSettingHelper.createCompensatoryAcquisitionUse(),
				CompensatoryLeaveComSettingHelper.createSubstituteHolidaySetting(),
				CompensatoryLeaveComSettingHelper.createCompensatoryDigestiveTimeUnit(), ManageDistinct.NO);
		NtsAssert.invokeGetters(comSetting);
	}

	/**
	 * Test [3] 代休に対応する日次の勤怠項目を取得する
	 */
	@Test
	public void testGetDailyAttendanceItemsSubHolidays() {
		CompensatoryLeaveComSetting comSetting = CompensatoryLeaveComSettingHelper.createCompensatoryLeaveComSetting();
		List<Integer> lstId = comSetting.getDailyAttendanceItemsSubHolidays();
		assertThat(lstId).extracting(d -> d).containsExactly(541, 542, 505, 517, 597, 603, 609, 615);
	}

	/**
	 * Test [4] 代休に対応する月次の勤怠項目を取得する
	 */
	@Test
	public void testGetMonthlyAttendanceItemsSubHolidays() {
		CompensatoryLeaveComSetting comSetting = CompensatoryLeaveComSettingHelper.createCompensatoryLeaveComSetting();
		List<Integer> lstId = comSetting.getMonthlyAttendanceItemsSubHolidays();
		assertThat(lstId).extracting(d -> d).containsExactly(1260, 1262, 1264, 1266, 1268, 188, 1666, 1667, 1668, 1669,
				1670);
	}

	/**
	 * Test [5] 利用できない日次の勤怠項目を取得する
	 */
	@Test
	public void testGetDailyAttendanceItems() {
		// ========== CASE 1
		// 時間代休の消化単位.管理区分 = 管理しない
		CompensatoryDigestiveTimeUnit timeUnit = CompensatoryLeaveComSettingHelper
				.createCompensatoryDigestiveTimeUnit(ManageDistinct.NO);
		// 管理区分 = 管理しない
		CompensatoryLeaveComSetting comSetting = CompensatoryLeaveComSettingHelper
				.createCompensatoryLeaveComSetting(ManageDistinct.NO, timeUnit);
		List<Integer> lstId = comSetting.getDailyAttendanceItems();

		// Case 時間代休の消化単位.管理区分 = 管理しない && 管理区分 = 管理しない
		assertThat(lstId).extracting(d -> d).containsExactly(541, 542, 505, 517, 597, 603, 609, 615);

		// ========== CASE 2
		// 時間代休の消化単位.管理区分 = 管理する
		timeUnit = CompensatoryLeaveComSettingHelper.createCompensatoryDigestiveTimeUnit(ManageDistinct.YES);
		// 管理区分 = 管理する
		comSetting = CompensatoryLeaveComSettingHelper.createCompensatoryLeaveComSetting(ManageDistinct.YES, timeUnit);
		lstId = comSetting.getDailyAttendanceItems();

		// Case 時間代休の消化単位.管理区分 = 管理する  && 管理区分 = 管理する
		assertThat(lstId.isEmpty()).isTrue();
		
		// ========== CASE 3
		// 時間代休の消化単位.管理区分 = 管理しない
		timeUnit = CompensatoryLeaveComSettingHelper.createCompensatoryDigestiveTimeUnit(ManageDistinct.NO);
		// 管理区分 = 管理する
		comSetting = CompensatoryLeaveComSettingHelper.createCompensatoryLeaveComSetting(ManageDistinct.YES, timeUnit);
		lstId = comSetting.getDailyAttendanceItems();

		// Case 時間代休の消化単位.管理区分 = 管理しない && 管理区分 = 管理する
		assertThat(lstId).extracting(d -> d).containsExactly(505, 517, 597, 603, 609, 615);
	}
	
	/**
	 * Test [6] 利用できない日次の勤怠項目を取得する
	 */
	@Test
	public void testGetMonthlylyAttendanceItems() {
		// ========== CASE 1
		// 時間代休の消化単位.管理区分 = 管理しない
		CompensatoryDigestiveTimeUnit timeUnit = CompensatoryLeaveComSettingHelper
				.createCompensatoryDigestiveTimeUnit(ManageDistinct.NO);
		// 管理区分 = 管理しない
		CompensatoryLeaveComSetting comSetting = CompensatoryLeaveComSettingHelper
				.createCompensatoryLeaveComSetting(ManageDistinct.NO, timeUnit);
		List<Integer> lstId = comSetting.getMonthlyAttendanceItems();

		// Case 時間代休の消化単位.管理区分 = 管理しない && 管理区分 = 管理しない
		assertThat(lstId).extracting(d -> d).containsExactly(1260, 1262, 1264, 1266, 1268, 188, 1666, 1667, 1668, 1669, 1670);

		// ========== CASE 2
		// 時間代休の消化単位.管理区分 = 管理する
		timeUnit = CompensatoryLeaveComSettingHelper.createCompensatoryDigestiveTimeUnit(ManageDistinct.YES);
		// 管理区分 = 管理する
		comSetting = CompensatoryLeaveComSettingHelper.createCompensatoryLeaveComSetting(ManageDistinct.YES, timeUnit);
		lstId = comSetting.getMonthlyAttendanceItems();

		// Case 時間代休の消化単位.管理区分 = 管理する  && 管理区分 = 管理する
		assertThat(lstId.isEmpty()).isTrue();
		
		// ========== CASE 3
		// 時間代休の消化単位.管理区分 = 管理しない
		timeUnit = CompensatoryLeaveComSettingHelper.createCompensatoryDigestiveTimeUnit(ManageDistinct.NO);
		// 管理区分 = 管理する
		comSetting = CompensatoryLeaveComSettingHelper.createCompensatoryLeaveComSetting(ManageDistinct.YES, timeUnit);
		lstId = comSetting.getMonthlyAttendanceItems();

		// Case 時間代休の消化単位.管理区分 = 管理しない && 管理区分 = 管理する
		assertThat(lstId).extracting(d -> d).containsExactly(188, 1666, 1667, 1668, 1669, 1670);
	}
}
