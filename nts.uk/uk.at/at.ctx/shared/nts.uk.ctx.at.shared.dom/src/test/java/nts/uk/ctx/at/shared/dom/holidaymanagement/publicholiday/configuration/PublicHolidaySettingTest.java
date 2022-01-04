package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

public class PublicHolidaySettingTest {
	/**
	 * Test [7] 公休に対応する月次の勤怠項目を取得する
	 */
	@Test
	public void testGetMonthlyAttendanceItemsPublicHolidays() {
		PublicHolidaySetting holidaySetting = PublicHolidaySettingHelper.createPublicHolidaySetting();
		List<Integer> lstId = holidaySetting.getMonthlyAttendanceItemsPublicHolidays();
		assertThat(lstId).extracting(d -> d).containsExactly(2256, 2257, 2258, 2259, 2260);
	}
	
	/**
	 * Test [8] 利用できない月次の勤怠項目を取得する
	 */
	@Test
	public void testGetMonthlyAttendanceItems() {
		// 公休を管理する == しない
		PublicHolidaySetting holidaySetting = PublicHolidaySettingHelper.createPublicHolidaySetting(0);
		List<Integer> lstId = holidaySetting.getMonthlyAttendanceItems();
		assertThat(lstId).extracting(d -> d).containsExactly(2256, 2257, 2258, 2259, 2260);
		
		// 公休を管理する == する
		holidaySetting = PublicHolidaySettingHelper.createPublicHolidaySetting(1);
		lstId = holidaySetting.getMonthlyAttendanceItems();
		assertThat(lstId.isEmpty()).isTrue();
	}
}
