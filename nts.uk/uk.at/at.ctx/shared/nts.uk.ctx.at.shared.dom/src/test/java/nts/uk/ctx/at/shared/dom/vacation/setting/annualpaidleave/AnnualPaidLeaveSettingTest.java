package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

public class AnnualPaidLeaveSettingTest {
	@Test
	public void getters() {
		AcquisitionSetting acquisitionSetting = new AcquisitionSetting(AnnualPriority.FIFO);
		ManageAnnualSetting annualSetting = AnnualPaidLeaveSettingHelper.createManageAnnualSetting();
		TimeAnnualSetting timeSetting = AnnualPaidLeaveSettingHelper.createTimeAnnualSetting();
		AnnualPaidLeaveSetting leaveSetting = new AnnualPaidLeaveSetting("000000000003-0004", acquisitionSetting,
				ManageDistinct.NO, annualSetting, timeSetting);
		NtsAssert.invokeGetters(leaveSetting);
	}

	/**
	 * Test [1] 年休に対応する日次の勤怠項目を取得する
	 */
	@Test
	public void testGetDailyAttendanceItemsAnnualLeave() {
		AnnualPaidLeaveSetting leaveSetting = AnnualPaidLeaveSettingHelper.createAnnualPaidLeaveSetting();
		List<Integer> lstId = leaveSetting.getDailyAttendanceItemsAnnualLeave();
		assertThat(lstId).extracting(d -> d).containsExactly(539, 540, 502, 514, 595, 601, 607, 613);
	}

	/**
	 * Test [2] 年休に対応する月次の勤怠項目を取得する
	 */
	@Test
	public void testGetMonthlyAttendanceItemsAnnualLeave() {
		AnnualPaidLeaveSetting leaveSetting = AnnualPaidLeaveSettingHelper.createAnnualPaidLeaveSetting();
		List<Integer> lstId = leaveSetting.getMonthlyAttendanceItemsAnnualLeave();
		List<Integer> lstIdCanGet = Arrays.asList(189, 794, 798, 799, 790, 801, 805, 809, 1427, 1428, 1432, 1433, 1780,
				1781, 1782, 1783, 1784, 1785, 1786, 1787, 1788, 1789, 1434, 1435, 1436, 1437, 1438, 1439, 1440, 1441,
				1424, 1425, 1426, 1429, 1430, 1431, 1861, 1862, 1442, 1443, 1444, 1445, 185, 789, 800);
		assertThat(lstId.containsAll(lstIdCanGet)).isTrue();
	}

	/**
	 * Test [3] 利用できない日次の勤怠項目を取得する
	 */
	@Test
	public void testGetDailyAttendanceItemsNotAvailable() {
	// Case 1	
		// 時間年休の上限日数.管理区分 = 管理しない
		TimeAnnualMaxDay timeAnnualMaxDay = AnnualPaidLeaveSettingHelper.createTimeAnnualMaxDay(ManageDistinct.NO);
		// 時間年休管理設定.時間年休管理区分 = 管理しない
		TimeAnnualSetting timeAnnualSetting = AnnualPaidLeaveSettingHelper.createTimeAnnualSetting(timeAnnualMaxDay,
				ManageDistinct.NO);
		// 年休設定.年休管理区分 = 管理しない
		AnnualPaidLeaveSetting leaveSetting = AnnualPaidLeaveSettingHelper
				.createAnnualPaidLeaveSetting(timeAnnualSetting, ManageDistinct.NO);
		List<Integer> lstId = leaveSetting.getDailyAttendanceItemsNotAvailable();
		assertThat(lstId).extracting(d -> d).containsExactly(539, 540,502,514,595,601,607,613);
		
	// Case 2
		// 時間年休の上限日数.管理区分 = 管理する
		timeAnnualMaxDay = AnnualPaidLeaveSettingHelper.createTimeAnnualMaxDay(ManageDistinct.YES);
		// 時間年休管理設定.時間年休管理区分 = 管理する
		timeAnnualSetting = AnnualPaidLeaveSettingHelper.createTimeAnnualSetting(timeAnnualMaxDay,
				ManageDistinct.YES);
		// 年休設定.年休管理区分 = 管理する
		leaveSetting = AnnualPaidLeaveSettingHelper
				.createAnnualPaidLeaveSetting(timeAnnualSetting, ManageDistinct.YES);
		lstId = leaveSetting.getDailyAttendanceItemsNotAvailable();
		assertThat(lstId.isEmpty()).isTrue();
		
	// Case 3
		// 時間年休の上限日数.管理区分 = 管理しない
		timeAnnualMaxDay = AnnualPaidLeaveSettingHelper.createTimeAnnualMaxDay(ManageDistinct.NO);
		// 時間年休管理設定.時間年休管理区分 = 管理しない
		timeAnnualSetting = AnnualPaidLeaveSettingHelper.createTimeAnnualSetting(timeAnnualMaxDay,
				ManageDistinct.NO);
		// 年休設定.年休管理区分 = 管理する
		leaveSetting = AnnualPaidLeaveSettingHelper
				.createAnnualPaidLeaveSetting(timeAnnualSetting, ManageDistinct.YES);
		lstId = leaveSetting.getDailyAttendanceItemsNotAvailable();
		assertThat(lstId).extracting(d -> d).containsExactly(502, 514, 595, 601, 607, 613);
	}

	/**
	 * Test [4] 利用できない月次の勤怠項目を取得する
	 */
	@Test
	public void testMonthlyAttendanceItemsNotAvailable() {
		// =============CASE 1
		// 管理区分 = 管理しない
		TimeAnnualMaxDay timeAnnualMaxDay = AnnualPaidLeaveSettingHelper.createTimeAnnualMaxDay(ManageDistinct.NO);
		// 管理区分 = 管理しない
		HalfDayManage halfDayManage = AnnualPaidLeaveSettingHelper.createHalfDayManage(ManageDistinct.NO);
		ManageAnnualSetting manageAnnualSetting = AnnualPaidLeaveSettingHelper.createManageAnnualSetting(halfDayManage);
		// 時間年休管理区分 = 管理しない
		TimeAnnualSetting timeAnnualSetting = AnnualPaidLeaveSettingHelper.createTimeAnnualSetting(timeAnnualMaxDay,
				ManageDistinct.NO);
		// 年休管理区分 = 管理しない
		AnnualPaidLeaveSetting leaveSetting = AnnualPaidLeaveSettingHelper
				.createAnnualPaidLeaveSetting(timeAnnualSetting, manageAnnualSetting, ManageDistinct.NO);

		List<Integer> lstId = leaveSetting.getMonthlyAttendanceItemsNotAvailable();
		List<Integer> lstIdCanGet = Arrays.asList(189, 794, 798, 799, 790, 801, 805, 809, 1427, 1428, 1432, 1433, 1780, 1781, 1782, 1783, 1784, 1785, 
				1786, 1787, 1788, 1789, 1434, 1435, 1436, 1437, 1438, 1439, 1440, 1441, 1424, 1425, 1426, 1429, 1430, 1431, 1861, 1862, 1442, 1443, 1444, 1445);
		
		// 管理区分 = 管理しない && 管理区分 = 管理しない && 時間年休管理区分 = 管理しない && 年休管理区分 = 管理しない
		assertThat(lstId.containsAll(lstIdCanGet)).isTrue();
		
		
		// =============CASE 2
		// 管理区分 = 管理する
		timeAnnualMaxDay = AnnualPaidLeaveSettingHelper.createTimeAnnualMaxDay(ManageDistinct.YES);
		// 管理区分 = 管理しない
		halfDayManage = AnnualPaidLeaveSettingHelper.createHalfDayManage(ManageDistinct.NO);
		manageAnnualSetting = AnnualPaidLeaveSettingHelper.createManageAnnualSetting(halfDayManage);
		// 時間年休管理区分 = 管理しない
		timeAnnualSetting = AnnualPaidLeaveSettingHelper.createTimeAnnualSetting(timeAnnualMaxDay, ManageDistinct.NO);
		// 年休管理区分 = 管理する
		leaveSetting = AnnualPaidLeaveSettingHelper.createAnnualPaidLeaveSetting(timeAnnualSetting, manageAnnualSetting,
				ManageDistinct.YES);

		lstId = leaveSetting.getMonthlyAttendanceItemsNotAvailable();
		lstIdCanGet = Arrays.asList(1434, 1435, 1436, 1437, 1438, 1439, 1440, 1441, 1442, 1443, 1444, 1445);
		assertThat(lstId.containsAll(lstIdCanGet)).isTrue();
		
		// =============CASE 3
		// 管理区分 = 管理する
		timeAnnualMaxDay = AnnualPaidLeaveSettingHelper.createTimeAnnualMaxDay(ManageDistinct.YES);
		// 管理区分 = 管理する
		halfDayManage = AnnualPaidLeaveSettingHelper.createHalfDayManage(ManageDistinct.YES);
		manageAnnualSetting = AnnualPaidLeaveSettingHelper.createManageAnnualSetting(halfDayManage);
		// 時間年休管理区分 = 管理しない
		timeAnnualSetting = AnnualPaidLeaveSettingHelper.createTimeAnnualSetting(timeAnnualMaxDay, ManageDistinct.NO);
		// 年休管理区分 = 管理する
		leaveSetting = AnnualPaidLeaveSettingHelper.createAnnualPaidLeaveSetting(timeAnnualSetting, manageAnnualSetting,
				ManageDistinct.YES);

		lstId = leaveSetting.getMonthlyAttendanceItemsNotAvailable();
		lstIdCanGet = Arrays.asList(1442, 1443, 1444, 1445);
		assertThat(lstId.containsAll(lstIdCanGet)).isTrue();
		
		// =============CASE 4
		// 管理区分 = 管理する
		timeAnnualMaxDay = AnnualPaidLeaveSettingHelper.createTimeAnnualMaxDay(ManageDistinct.YES);
		// 管理区分 = 管理しない
		halfDayManage = AnnualPaidLeaveSettingHelper.createHalfDayManage(ManageDistinct.NO);
		manageAnnualSetting = AnnualPaidLeaveSettingHelper.createManageAnnualSetting(halfDayManage);
		// 時間年休管理区分 = 管理しない
		timeAnnualSetting = AnnualPaidLeaveSettingHelper.createTimeAnnualSetting(timeAnnualMaxDay, ManageDistinct.NO);
		// 年休管理区分 = 管理する
		leaveSetting = AnnualPaidLeaveSettingHelper.createAnnualPaidLeaveSetting(timeAnnualSetting, manageAnnualSetting,
				ManageDistinct.NO);

		lstId = leaveSetting.getMonthlyAttendanceItemsNotAvailable();
		lstIdCanGet = Arrays.asList(189, 794, 798, 799, 790, 801, 805, 809, 1427, 1428, 1432, 1433, 1780, 1781, 1782, 
				1783, 1784, 1785, 1786, 1787, 1788, 1789, 1434, 1435, 1436, 1437, 1438, 1439, 1440, 1441, 1442, 1443, 1444, 1445);
		assertThat(lstId.containsAll(lstIdCanGet)).isTrue();
		
		
		// =============CASE 5
		// 管理区分 = 管理する
		timeAnnualMaxDay = AnnualPaidLeaveSettingHelper.createTimeAnnualMaxDay(ManageDistinct.YES);
		// 管理区分 = 管理しない
		halfDayManage = AnnualPaidLeaveSettingHelper.createHalfDayManage(ManageDistinct.NO);
		manageAnnualSetting = AnnualPaidLeaveSettingHelper.createManageAnnualSetting(halfDayManage);
		// 時間年休管理区分 = 管理する
		timeAnnualSetting = AnnualPaidLeaveSettingHelper.createTimeAnnualSetting(timeAnnualMaxDay, ManageDistinct.YES);
		// 年休管理区分 = 管理する
		leaveSetting = AnnualPaidLeaveSettingHelper.createAnnualPaidLeaveSetting(timeAnnualSetting, manageAnnualSetting,
				ManageDistinct.YES);

		lstId = leaveSetting.getMonthlyAttendanceItemsNotAvailable();
		lstIdCanGet = Arrays.asList(1434, 1435, 1436, 1437, 1438, 1439, 1440, 1441);

		// 管理区分 = 管理する && 管理区分 = 管理しない && 時間年休管理区分 = 管理する && 年休管理区分 = 管理する
		assertThat(lstId.containsAll(lstIdCanGet)).isTrue();

		// =============CASE 6
		// 管理区分 = 管理する
		timeAnnualMaxDay = AnnualPaidLeaveSettingHelper.createTimeAnnualMaxDay(ManageDistinct.YES);
		// 管理区分 = 管理する
		halfDayManage = AnnualPaidLeaveSettingHelper.createHalfDayManage(ManageDistinct.YES);
		manageAnnualSetting = AnnualPaidLeaveSettingHelper.createManageAnnualSetting(halfDayManage);
		// 時間年休管理区分 = 管理する
		timeAnnualSetting = AnnualPaidLeaveSettingHelper.createTimeAnnualSetting(timeAnnualMaxDay, ManageDistinct.YES);
		// 年休管理区分 = 管理する
		leaveSetting = AnnualPaidLeaveSettingHelper.createAnnualPaidLeaveSetting(timeAnnualSetting, manageAnnualSetting,
				ManageDistinct.YES);

		lstId = leaveSetting.getMonthlyAttendanceItemsNotAvailable();

		// 管理区分 = 管理する && 管理区分 = 管理する && 時間年休管理区分 = 管理する && 年休管理区分 = 管理する
		assertThat(lstId.isEmpty()).isTrue();
		
		// =============CASE 7
		// 管理区分 = 管理しない
		timeAnnualMaxDay = AnnualPaidLeaveSettingHelper.createTimeAnnualMaxDay(ManageDistinct.NO);
		// 管理区分 = 管理する
		halfDayManage = AnnualPaidLeaveSettingHelper.createHalfDayManage(ManageDistinct.YES);
		manageAnnualSetting = AnnualPaidLeaveSettingHelper.createManageAnnualSetting(halfDayManage);
		// 時間年休管理区分 = 管理する
		timeAnnualSetting = AnnualPaidLeaveSettingHelper.createTimeAnnualSetting(timeAnnualMaxDay, ManageDistinct.YES);
		// 年休管理区分 = 管理する
		leaveSetting = AnnualPaidLeaveSettingHelper.createAnnualPaidLeaveSetting(timeAnnualSetting, manageAnnualSetting,
				ManageDistinct.YES);

		lstId = leaveSetting.getMonthlyAttendanceItemsNotAvailable();
		lstIdCanGet = Arrays.asList(1442, 1443, 1444, 1445);

		// 管理区分 = 管理しない && 管理区分 = 管理する && 時間年休管理区分 = 管理する && 年休管理区分 = 管理する
		assertThat(lstId.containsAll(lstIdCanGet)).isTrue();
		
	}
}
