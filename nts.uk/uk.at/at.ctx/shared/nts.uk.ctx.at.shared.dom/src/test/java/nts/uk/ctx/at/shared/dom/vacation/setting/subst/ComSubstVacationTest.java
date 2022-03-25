package nts.uk.ctx.at.shared.dom.vacation.setting.subst;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

public class ComSubstVacationTest {
	@Test
	public void getters() {
		SubstVacationSetting vacationSetting = new SubstVacationSetting(ManageDeadline.MANAGE_BY_BASE_DATE,
				ExpirationTime.EIGHT_MONTH, ApplyPermission.ALLOW);
		ComSubstVacation comSubstVacation = new ComSubstVacation("000000000003-0004", vacationSetting,
				ManageDistinct.NO, ManageDistinct.NO);
		NtsAssert.invokeGetters(comSubstVacation);
	}

	/**
	 * Test [1] 振休に対応する日次の勤怠項目を取得する
	 */
	@Test
	public void tetsGetDailyAttendanceItemsHolidays() {
		ComSubstVacation comSubstVacation = ComSubstVacationHelper.createComSubstVacation();
		List<Integer> lstId = comSubstVacation.getDailyAttendanceItemsHolidays();
		assertThat(lstId).extracting(d -> d).containsExactly(1144);
	}
	
	/**
	 * 	[2] 振休に対応する月次の勤怠項目を取得する
	 */
	@Test
	public void testGetMonthlyAttendanceItemsHolidays() {
		ComSubstVacation comSubstVacation = ComSubstVacationHelper.createComSubstVacation();
		List<Integer> lstId = comSubstVacation.getMonthlyAttendanceItemsHolidays();
		assertThat(lstId).extracting(d -> d).containsExactly(1270, 1271, 1272, 1273, 1274, 1801, 2194);
	}
	
	/**
	 * 	Test [3] 利用できない日次の勤怠項目を取得する
	 */
	@Test
	public void testGetDailyAttendanceItems() {
		// @管理区分 == 管理しない
		ComSubstVacation comSubstVacation = ComSubstVacationHelper.createComSubstVacation(ManageDistinct.NO);
		List<Integer> lstId = comSubstVacation.getDailyAttendanceItems();
		assertThat(lstId).extracting(d -> d).containsExactly(1144);
		
		// @管理区分 == 管理する
		comSubstVacation = ComSubstVacationHelper.createComSubstVacation(ManageDistinct.YES);
		lstId = comSubstVacation.getDailyAttendanceItems();
		assertThat(lstId.isEmpty()).isTrue();
	}
	
	/**
	 * 	Test [4] 利用できない月次の勤怠項目を取得する
	 */
	@Test
	public void testGetMonthlyAttendanceItems() {
		// @管理区分 == 管理しない
		ComSubstVacation comSubstVacation = ComSubstVacationHelper.createComSubstVacation(ManageDistinct.NO);
		List<Integer> lstId = comSubstVacation.getMonthlyAttendanceItems();
		assertThat(lstId).extracting(d -> d).containsExactly(1270, 1271, 1272, 1273, 1274, 1801, 2194);
		
		// @管理区分 == 管理する
		comSubstVacation = ComSubstVacationHelper.createComSubstVacation(ManageDistinct.YES);
		lstId = comSubstVacation.getMonthlyAttendanceItems();
		assertThat(lstId.isEmpty()).isTrue();
	}
}
