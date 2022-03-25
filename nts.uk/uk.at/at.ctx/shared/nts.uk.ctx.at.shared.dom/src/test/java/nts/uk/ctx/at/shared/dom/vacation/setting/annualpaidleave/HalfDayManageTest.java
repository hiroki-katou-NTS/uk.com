package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

public class HalfDayManageTest {
	/**
	 * Test [1] 半日回数上限に対応する月次の勤怠項目を取得する
	 */
	@Test
	public void testGetMonthlyAttendanceItemsCorresHalfDayLimit() {
		HalfDayManage dayManage = HalfDayManageHelper.createHalfDayManage(ManageDistinct.NO);
		List<Integer> lstId = dayManage.getMonthlyAttendanceItemsCorresHalfDayLimit();
		assertThat(lstId).extracting(d -> d).containsExactly(1434, 1435, 1436, 1437, 1438, 1439, 1440, 1441);
	}

	/**
	 * Test [2] 利用できない月次の勤怠項目を取得する
	 */
	@Test
	public void testGetMonthlyAttendanceItems() {
		// ========== Case 1
		// 管理区分 = 管理しない
		HalfDayManage dayManage = HalfDayManageHelper.createHalfDayManage(ManageDistinct.NO);
		List<Integer> lstId = dayManage.getMonthlyAttendanceItems(ManageDistinct.NO);
		assertThat(lstId).extracting(d -> d).containsExactly(1434, 1435, 1436, 1437, 1438, 1439, 1440, 1441);

		// ========== Case 2
		// 管理区分 = 管理する
		dayManage = HalfDayManageHelper.createHalfDayManage(ManageDistinct.YES);
		lstId = dayManage.getMonthlyAttendanceItems(ManageDistinct.YES);
		assertThat(lstId.isEmpty()).isTrue();
	}
	

	/**
	 * Test [4] 半日年休を管理するか
	 */
	@Test
	public void testIsManageHalfDayAnnualLeave() {
		// ========== Case 1
		// 半日年休管理.管理区分 = 管理しない && 管理区分 = 管理しない
		HalfDayManage dayManage = HalfDayManageHelper.createHalfDayManage(ManageDistinct.NO);
		boolean result = dayManage.isManageHalfDayAnnualLeave(ManageDistinct.NO);
		assertThat(result).isFalse();

		// ========== Case 2
		// 半日年休管理.管理区分 = 管理する && 管理区分 = 管理する
		dayManage = HalfDayManageHelper.createHalfDayManage(ManageDistinct.YES);
		result = dayManage.isManageHalfDayAnnualLeave(ManageDistinct.YES);
		assertThat(result).isTrue();
		
		// ========== Case 2
		// 半日年休管理.管理区分 = 管理する && 管理区分 = 管理しない
		dayManage = HalfDayManageHelper.createHalfDayManage(ManageDistinct.YES);
		result = dayManage.isManageHalfDayAnnualLeave(ManageDistinct.NO);
		assertThat(result).isFalse();
		
		// ========== Case 2
		// 半日年休管理.管理区分 = 管理する && 管理区分 = 管理する
		dayManage = HalfDayManageHelper.createHalfDayManage(ManageDistinct.NO);
		result = dayManage.isManageHalfDayAnnualLeave(ManageDistinct.YES);
		assertThat(result).isFalse();
	}

}
