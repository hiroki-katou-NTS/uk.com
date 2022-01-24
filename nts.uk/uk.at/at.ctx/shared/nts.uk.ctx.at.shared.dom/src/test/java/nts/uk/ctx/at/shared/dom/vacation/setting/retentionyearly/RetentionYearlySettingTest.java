package nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingHelper;

@RunWith(JMockit.class)
public class RetentionYearlySettingTest {
	@Injectable
	private RetentionYearlySetting.Require require;
	
	@Test
	public void getters() {
		UpperLimitSetting upperLimitSetting = RetentionYearlySettingHelper.createUpperLimitSetting();
		RetentionYearlySetting retentionYearlySetting = new RetentionYearlySetting("000000000003-0004",
				upperLimitSetting, ManageDistinct.YES);
		NtsAssert.invokeGetters(retentionYearlySetting);
	}

	/**
	 * Test [1] 積立年休に対応する日次の勤怠項目を取得する
	 */
	@Test
	public void testGetDailyAttendanceItemsRetentionYearly() {
		RetentionYearlySetting retentionYearlySetting = RetentionYearlySettingHelper.createRetentionYearlySetting();
		List<Integer> lstId = retentionYearlySetting.getDailyAttendanceItemsRetentionYearly();
		assertThat(lstId).extracting(d -> d).containsExactly(547);
	}
	
	/**
	 * Test [2] 積立年休に対応する月次の勤怠項目を取得する
	 */
	@Test
	public void testGetMonthlyAttendanceItemsRetentionYearly() {
		RetentionYearlySetting retentionYearlySetting = RetentionYearlySettingHelper.createRetentionYearlySetting();
		List<Integer> lstId = retentionYearlySetting.getMonthlyAttendanceItemsRetentionYearly();
		assertThat(lstId).extracting(d -> d).containsExactly(187, 830, 831, 832, 834, 835, 836, 837, 838, 1790, 1791);
	}
	
	/**
	 * Test [3] 利用できない月次の勤怠項目を取得する
	 */
	@Test
	public void testGetMonthlyAttendanceItems() {
		// ========== Case 1
		// 年休管理区分 = 管理しない
		AnnualPaidLeaveSetting leaveSetting = AnnualPaidLeaveSettingHelper.createAnnualPaidLeaveSetting(ManageDistinct.NO);
		// 管理区分  = 管理しない
		RetentionYearlySetting retentionYearlySetting = RetentionYearlySettingHelper.createRetentionYearlySetting(ManageDistinct.NO);
		new Expectations() {{
			require.findByCid("000000000003-0004");
			result = leaveSetting;
		}};
		List<Integer> lstId = retentionYearlySetting.getMonthlyAttendanceItems(require);
		
		// Case 年休管理区分 = 管理しない && 管理区分  = 管理しない
		assertThat(lstId).extracting(d -> d).containsExactly(187, 830, 831, 832, 834, 835, 836, 837, 838, 1790, 1791);
		
		
		// ========== Case 2
		// 年休管理区分 = 管理する
		AnnualPaidLeaveSetting leaveSetting2 = AnnualPaidLeaveSettingHelper.createAnnualPaidLeaveSetting(ManageDistinct.YES);
		// 管理区分  = 管理する
		retentionYearlySetting = RetentionYearlySettingHelper.createRetentionYearlySetting(ManageDistinct.YES);
		new Expectations() {{
			require.findByCid("000000000003-0004");
			result = leaveSetting2;
		}};
		lstId = retentionYearlySetting.getMonthlyAttendanceItems(require);
		
		// Case 年休管理区分 = 管理する && 管理区分  = 管理する
		assertThat(lstId.isEmpty()).isTrue();
	}
	
	/**
	 * Test [4] 積立年休を管理するか
	 */
	@Test
	public void testIsManageRetentionYearly() {
		// ========== Case 1
		// 年休管理区分 = 管理しない
		AnnualPaidLeaveSetting leaveSetting = AnnualPaidLeaveSettingHelper.createAnnualPaidLeaveSetting(ManageDistinct.NO);
		// 管理区分  = 管理しない
		RetentionYearlySetting retentionYearlySetting = RetentionYearlySettingHelper.createRetentionYearlySetting(ManageDistinct.NO);
		new Expectations() {{
			require.findByCid("000000000003-0004");
			result = leaveSetting;
		}};
		boolean result = retentionYearlySetting.isManageRetentionYearly(require);
		
		// Case 年休設定 != null && 年休管理区分 = 管理しない && 管理区分  = 管理しない
		assertThat(result).isFalse();
		
		
		// ========== Case 2
		// 年休管理区分 = 管理する
		AnnualPaidLeaveSetting leaveSetting2 = AnnualPaidLeaveSettingHelper.createAnnualPaidLeaveSetting(ManageDistinct.YES);
		// 管理区分  = 管理する
		retentionYearlySetting = RetentionYearlySettingHelper.createRetentionYearlySetting(ManageDistinct.YES);
		new Expectations() {{
			require.findByCid("000000000003-0004");
			result = leaveSetting2;
		}};
		result = retentionYearlySetting.isManageRetentionYearly(require);
		
		// Case 年休設定 != null && 年休管理区分 = 管理する && 管理区分  = 管理する
		assertThat(result).isTrue();
		
		
		// ========== Case 3
		// 年休管理区分 = 管理する
		AnnualPaidLeaveSetting leaveSetting3 = AnnualPaidLeaveSettingHelper.createAnnualPaidLeaveSetting(ManageDistinct.YES);
		// 管理区分  = 管理しない
		retentionYearlySetting = RetentionYearlySettingHelper.createRetentionYearlySetting(ManageDistinct.NO);
		new Expectations() {{
			require.findByCid("000000000003-0004");
			result = leaveSetting3;
		}};
		result = retentionYearlySetting.isManageRetentionYearly(require);
		
		// Case 年休設定 != null && 年休管理区分 = 管理する && 管理区分  = 管理しない
		assertThat(result).isFalse();
		
		
		// ========== Case 4
		// 年休管理区分 = 管理しない
		AnnualPaidLeaveSetting leaveSetting4 = AnnualPaidLeaveSettingHelper.createAnnualPaidLeaveSetting(ManageDistinct.NO);
		// 管理区分  = 管理する
		retentionYearlySetting = RetentionYearlySettingHelper.createRetentionYearlySetting(ManageDistinct.YES);
		new Expectations() {{
			require.findByCid("000000000003-0004");
			result = leaveSetting4;
		}};
		result = retentionYearlySetting.isManageRetentionYearly(require);
		
		// Case 年休設定 != null && 年休管理区分 = 管理しない && 管理区分  = 管理する
		assertThat(result).isFalse();
		
		
		// ========== Case 5
		// 年休設定 = null
		AnnualPaidLeaveSetting leaveSetting5 = null;
		// 管理区分  = 管理する
		retentionYearlySetting = RetentionYearlySettingHelper.createRetentionYearlySetting(ManageDistinct.YES);
		new Expectations() {{
			require.findByCid("000000000003-0004");
			result = leaveSetting5;
		}};
		result = retentionYearlySetting.isManageRetentionYearly(require);
		
		// Case 年休設定 = null
		assertThat(result).isFalse();
		
		
		// ========== Case 6
		// 年休設定 = null
		AnnualPaidLeaveSetting leaveSetting6 = null;
		// 管理区分  = 管理しない
		retentionYearlySetting = RetentionYearlySettingHelper.createRetentionYearlySetting(ManageDistinct.NO);
		new Expectations() {{
			require.findByCid("000000000003-0004");
			result = leaveSetting6;
		}};
		result = retentionYearlySetting.isManageRetentionYearly(require);
		
		// Case 年休設定 = null
		assertThat(result).isFalse();
	}
}
