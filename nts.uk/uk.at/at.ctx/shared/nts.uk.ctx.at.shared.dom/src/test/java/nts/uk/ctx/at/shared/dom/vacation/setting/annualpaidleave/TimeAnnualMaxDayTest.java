package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LimitedTimeHdDays;

public class TimeAnnualMaxDayTest {
	@Test
	public void getters() {
		TimeAnnualMaxDay maxDay = new TimeAnnualMaxDay(ManageDistinct.YES, MaxDayReference.CompanyUniform,
				new MaxTimeDay(1));
		NtsAssert.invokeGetters(maxDay);
	}

	/**
	 * [1] 時間年休の上限日数に対応する月次の勤怠項目を取得する
	 */
	@Test
	public void testAcquiremonthAttendItemMaximumNumberDaysAnnualLeave() {
		TimeAnnualMaxDay maxDay = TimeAnnualMaxDayHelper.createTimeAnnualMaxDay();
		List<Integer> lstResult = maxDay.acquiremonthAttendItemMaximumNumberDaysAnnualLeave();
		assertThat(lstResult.containsAll(Arrays.asList(1442, 1443)));
	}

	/**
	 * Test [2] 利用できない月次の勤怠項目を取得する
	 */
	@Test
	public void getMonthAttendItemsNotAvailable_isEmpty_1() {
		// 時間年休の上限日数.管理区分 = 管理しない, 年休設定.年休管理区分 = 管理しない, 時間年休管理設定.時間年休管理区分 = 管理しない
		TimeAnnualMaxDay maxDay = TimeAnnualMaxDayHelper.createTimeAnnualMaxDay_ManageDistinct_NO(ManageDistinct.NO);
		List<Integer> lstResult = maxDay.getMonthAttendItemsNotAvailable(ManageDistinct.NO, ManageDistinct.NO);
		assertThat(lstResult.containsAll(Arrays.asList(1442, 1443, 1444, 1445)));

		// 時間年休の上限日数.管理区分 = 管理しない, 年休設定.年休管理区分 = 管理する, 時間年休管理設定.時間年休管理区分 = 管理しない
		maxDay = TimeAnnualMaxDayHelper.createTimeAnnualMaxDay_ManageDistinct_NO(ManageDistinct.NO);
		lstResult = maxDay.getMonthAttendItemsNotAvailable(ManageDistinct.YES, ManageDistinct.NO);
		assertThat(lstResult.containsAll(Arrays.asList(1442, 1443, 1444, 1445)));

		// 時間年休の上限日数.管理区分 = 管理しない, 年休設定.年休管理区分 = 管理しない, 時間年休管理設定.時間年休管理区分 = 管理する
		maxDay = TimeAnnualMaxDayHelper.createTimeAnnualMaxDay_ManageDistinct_NO(ManageDistinct.NO);
		lstResult = maxDay.getMonthAttendItemsNotAvailable(ManageDistinct.NO, ManageDistinct.YES);
		assertThat(lstResult.containsAll(Arrays.asList(1442, 1443, 1444, 1445)));

		// 時間年休の上限日数.管理区分 = 管理する, 年休設定.年休管理区分 = 管理しない, 時間年休管理設定.時間年休管理区分 = 管理する
		maxDay = TimeAnnualMaxDayHelper.createTimeAnnualMaxDay_ManageDistinct_YES(ManageDistinct.YES);
		lstResult = maxDay.getMonthAttendItemsNotAvailable(ManageDistinct.NO, ManageDistinct.YES);
		assertThat(lstResult.containsAll(Arrays.asList(1442, 1443, 1444, 1445)));

		// 時間年休の上限日数.管理区分 = 管理する, 年休設定.年休管理区分 = 管理する, 時間年休管理設定.時間年休管理区分 = 管理しない
		maxDay = TimeAnnualMaxDayHelper.createTimeAnnualMaxDay_ManageDistinct_YES(ManageDistinct.YES);
		lstResult = maxDay.getMonthAttendItemsNotAvailable(ManageDistinct.YES, ManageDistinct.NO);
		assertThat(lstResult.containsAll(Arrays.asList(1442, 1443, 1444, 1445)));

		// 時間年休の上限日数.管理区分 = 管理する, 年休設定.年休管理区分 = 管理する, 時間年休管理設定.時間年休管理区分 = 管理する
		maxDay = TimeAnnualMaxDayHelper.createTimeAnnualMaxDay_ManageDistinct_YES(ManageDistinct.YES);
		lstResult = maxDay.getMonthAttendItemsNotAvailable(ManageDistinct.YES, ManageDistinct.YES);
		assertThat(lstResult.isEmpty()).isTrue();
	}

	/**
	 * Test [3] 時間年休上限日数を取得
	 * 
	 * @param fromGrantTableDays
	 * @return
	 */
	@Test
	public void testLimitedTimeHdDays_isEmpty() {
		// // 時間年休の上限日数.管理区分 = 管理しない
		TimeAnnualMaxDay maxDay = TimeAnnualMaxDayHelper.createTimeAnnualMaxDay_ManageDistinct_NO(ManageDistinct.NO);
		Optional<LimitedTimeHdDays> limitedTimeHdDay = maxDay
				.getLimitedTimeHdDays(Optional.of(new LimitedTimeHdDays(1)));
		assertThat(limitedTimeHdDay.isPresent()).isFalse();

		// 時間年休の上限日数.管理区分 = 管理する,上限参照先 = 会社一律, 時間年休上限日数 = 1, LimitedTimeHdDays = 2
		maxDay = TimeAnnualMaxDayHelper.createTimeAnnualMaxDay_ManageDistinct_YES_CompanyUniform(ManageDistinct.YES,
				MaxDayReference.CompanyUniform, new MaxTimeDay(1));
		limitedTimeHdDay = maxDay.getLimitedTimeHdDays(Optional.of(new LimitedTimeHdDays(2)));
		assertThat(limitedTimeHdDay.get().v().equals(new MaxTimeDay(1).v())).isTrue();

		// 時間年休の上限日数.管理区分 = 管理する,上限参照先 = 年休付与テーブルを参照, 時間年休上限日数 = 1, LimitedTimeHdDays = 2
		maxDay = TimeAnnualMaxDayHelper.createTimeAnnualMaxDay_ManageDistinct_YES_CompanyUniform(ManageDistinct.YES,
				MaxDayReference.ReferAnnualGrantTable, new MaxTimeDay(1));
		limitedTimeHdDay = maxDay.getLimitedTimeHdDays(Optional.of(new LimitedTimeHdDays(2)));
		assertThat(limitedTimeHdDay.get().v().equals(new LimitedTimeHdDays(2).v())).isTrue();
	}

	/**
	 * Test [4] 上限日数を管理するか
	 */
	@Test
	public void testIsManageMaximumNumberDays_False() {
		// 時間年休の上限日数.管理区分 = 管理しない, 年休設定.年休管理区分 = 管理する, 時間年休管理設定.時間年休管理区分 = 管理する
		TimeAnnualMaxDay maxDay = TimeAnnualMaxDayHelper.createTimeAnnualMaxDay_ManageDistinct_NO(ManageDistinct.NO);
		boolean checkIsManageMaximum = maxDay.isManageMaximumNumberDays(ManageDistinct.YES, ManageDistinct.YES);
		assertThat(checkIsManageMaximum).isFalse();
		
		// 時間年休の上限日数.管理区分 = 管理しない, 年休設定.年休管理区分 = 管理しない, 時間年休管理設定.時間年休管理区分 = 管理しない
		maxDay = TimeAnnualMaxDayHelper.createTimeAnnualMaxDay_ManageDistinct_NO(ManageDistinct.NO);
		checkIsManageMaximum = maxDay.isManageMaximumNumberDays(ManageDistinct.NO, ManageDistinct.NO);
		assertThat(checkIsManageMaximum).isFalse();

		// 時間年休の上限日数.管理区分 = 管理しない, 年休設定.年休管理区分 = 管理する, 時間年休管理設定.時間年休管理区分 = 管理しない
		maxDay = TimeAnnualMaxDayHelper.createTimeAnnualMaxDay_ManageDistinct_NO(ManageDistinct.NO);
		checkIsManageMaximum = maxDay.isManageMaximumNumberDays(ManageDistinct.YES, ManageDistinct.NO);
		assertThat(checkIsManageMaximum).isFalse();

		// 時間年休の上限日数.管理区分 = 管理しない, 年休設定.年休管理区分 = 管理しない, 時間年休管理設定.時間年休管理区分 = 管理する
		maxDay = TimeAnnualMaxDayHelper.createTimeAnnualMaxDay_ManageDistinct_NO(ManageDistinct.NO);
		checkIsManageMaximum = maxDay.isManageMaximumNumberDays(ManageDistinct.NO, ManageDistinct.YES);
		assertThat(checkIsManageMaximum).isFalse();

		// 時間年休の上限日数.管理区分 = 管理する, 年休設定.年休管理区分 = 管理しない, 時間年休管理設定.時間年休管理区分 = 管理しない
		maxDay = TimeAnnualMaxDayHelper.createTimeAnnualMaxDay_ManageDistinct_NO(ManageDistinct.YES);
		checkIsManageMaximum = maxDay.isManageMaximumNumberDays(ManageDistinct.NO, ManageDistinct.NO);
		assertThat(checkIsManageMaximum).isFalse();

		// 時間年休の上限日数.管理区分 = 管理する, 年休設定.年休管理区分 = 管理しない, 時間年休管理設定.時間年休管理区分 = 管理する
		maxDay = TimeAnnualMaxDayHelper.createTimeAnnualMaxDay_ManageDistinct_NO(ManageDistinct.YES);
		checkIsManageMaximum = maxDay.isManageMaximumNumberDays(ManageDistinct.NO, ManageDistinct.YES);
		assertThat(checkIsManageMaximum).isFalse();

		// 時間年休の上限日数.管理区分 = 管理する, 年休設定.年休管理区分 = 管理する, 時間年休管理設定.時間年休管理区分 = 管理しない
		maxDay = TimeAnnualMaxDayHelper.createTimeAnnualMaxDay_ManageDistinct_NO(ManageDistinct.YES);
		checkIsManageMaximum = maxDay.isManageMaximumNumberDays(ManageDistinct.YES, ManageDistinct.NO);
		assertThat(checkIsManageMaximum).isFalse();

		// 時間年休の上限日数.管理区分 = 管理する, 年休設定.年休管理区分 = 管理する, 時間年休管理設定.時間年休管理区分 = 管理する
		maxDay = TimeAnnualMaxDayHelper.createTimeAnnualMaxDay_ManageDistinct_YES(ManageDistinct.YES);
		checkIsManageMaximum = maxDay.isManageMaximumNumberDays(ManageDistinct.YES, ManageDistinct.YES);
		assertThat(checkIsManageMaximum).isTrue();
	}
}
