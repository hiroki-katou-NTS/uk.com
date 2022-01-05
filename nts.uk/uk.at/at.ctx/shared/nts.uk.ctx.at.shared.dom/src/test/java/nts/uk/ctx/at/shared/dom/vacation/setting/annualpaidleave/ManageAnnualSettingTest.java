package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

public class ManageAnnualSettingTest {
	
	@Test
	public void getters() {
		HalfDayManage dayManage = HalfDayManageHelper.createHalfDayManage(ManageDistinct.NO);
		RemainingNumberSetting numberSetting = new RemainingNumberSetting(new RetentionYear(1));
		ManageAnnualSetting annualSetting =  new ManageAnnualSetting(dayManage, numberSetting, new YearLyOfNumberDays(0.1));
		NtsAssert.invokeGetters(annualSetting);
	}
	
	/**
	 * Test [1] 半日回数上限に対応する月次の勤怠項目を取得する
	 */
	@Test
	public void testGetMonthlyAttendanceItemsCorresHalfDayLimit() {
		HalfDayManage dayManage = HalfDayManageHelper.createHalfDayManage(ManageDistinct.NO);
		ManageAnnualSetting annualSetting = ManageAnnualSettingHelper.createManageAnnualSetting(dayManage);
		List<Integer> lstId = annualSetting.getMonthlyAttendanceItemsHalfDayLimit();
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
		ManageAnnualSetting annualSetting = ManageAnnualSettingHelper.createManageAnnualSetting(dayManage);
		List<Integer> lstId = annualSetting.getMonthlyAttendanceItems(ManageDistinct.NO);
		assertThat(lstId).extracting(d -> d).containsExactly(1434, 1435, 1436, 1437, 1438, 1439, 1440, 1441);

		// ========== Case 2
		// 管理区分 = 管理する
		dayManage = HalfDayManageHelper.createHalfDayManage(ManageDistinct.YES);
		annualSetting = ManageAnnualSettingHelper.createManageAnnualSetting(dayManage);
		lstId = annualSetting.getMonthlyAttendanceItems(ManageDistinct.YES);
		assertThat(lstId.isEmpty()).isTrue();
	}
}
