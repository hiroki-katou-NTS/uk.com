package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LimitedTimeHdDays;

public class TimeAnnualMaxDayTest {
	@Test
	public void getters() {
		TimeAnnualMaxDay maxDay = new TimeAnnualMaxDay(ManageDistinct.YES, MaxDayReference.CompanyUniform, new MaxTimeDay(1));
		NtsAssert.invokeGetters(maxDay);
	}
	
	/**
	 * [1] 時間年休の上限日数に対応する月次の勤怠項目を取得する
	 */
	@Test
	public void testAcquiremonthAttendItemMaximumNumberDaysAnnualLeave() {
		TimeAnnualMaxDay maxDay = TimeAnnualMaxDayHelper.createTimeAnnualMaxDay();
		List<Integer> lstResult = maxDay.acquiremonthAttendItemMaximumNumberDaysAnnualLeave();
		assertThat( lstResult ).extracting( d -> d)
		   .containsExactly(1424,1425,1426,1429);
	}
	
	/**
	 * Test [2] 利用できない月次の勤怠項目を取得する
	 */
	@Test
	public void getMonthAttendItemsNotAvailable_isEmpty_1() {
		TimeAnnualMaxDay maxDay = TimeAnnualMaxDayHelper.createTimeAnnualMaxDay_ManageDistinct_NO(ManageDistinct.NO);
		List<Integer> lstResult = maxDay.getMonthAttendItemsNotAvailable(ManageDistinct.NO , ManageDistinct.NO);
		
		assertThat(lstResult.isEmpty());
	}
	
	@Test
	public void getMonthAttendItemsNotAvailable_isEmpty_2() {
		TimeAnnualMaxDay maxDay = TimeAnnualMaxDayHelper.createTimeAnnualMaxDay_ManageDistinct_NO(ManageDistinct.NO);
		List<Integer> lstResult = maxDay.getMonthAttendItemsNotAvailable(ManageDistinct.YES , ManageDistinct.NO);
		
		assertThat(lstResult.isEmpty());
	}
	
	@Test
	public void getMonthAttendItemsNotAvailable_isEmpty_3() {
		TimeAnnualMaxDay maxDay = TimeAnnualMaxDayHelper.createTimeAnnualMaxDay_ManageDistinct_NO(ManageDistinct.NO);
		List<Integer> lstResult = maxDay.getMonthAttendItemsNotAvailable(ManageDistinct.NO , ManageDistinct.YES);
		
		assertThat(lstResult.isEmpty());
	}
	
	@Test
	public void getMonthAttendItemsNotAvailable_isEmpty_4() {
		TimeAnnualMaxDay maxDay = TimeAnnualMaxDayHelper.createTimeAnnualMaxDay_ManageDistinct_YES(ManageDistinct.YES);
		List<Integer> lstResult = maxDay.getMonthAttendItemsNotAvailable(ManageDistinct.NO , ManageDistinct.YES);
		
		assertThat(lstResult.isEmpty());
	}
	
	@Test
	public void getMonthAttendItemsNotAvailable_isEmpty_5() {
		TimeAnnualMaxDay maxDay = TimeAnnualMaxDayHelper.createTimeAnnualMaxDay_ManageDistinct_YES(ManageDistinct.YES);
		List<Integer> lstResult = maxDay.getMonthAttendItemsNotAvailable(ManageDistinct.YES , ManageDistinct.NO);
		
		assertThat(lstResult.isEmpty());
	}
	
	@Test
	public void getMonthAttendItemsNotAvailable_notEmpty() {
		TimeAnnualMaxDay maxDay = TimeAnnualMaxDayHelper.createTimeAnnualMaxDay_ManageDistinct_YES(ManageDistinct.YES);
		List<Integer> lstResult = maxDay.getMonthAttendItemsNotAvailable(ManageDistinct.YES , ManageDistinct.YES);
		
		assertThat( lstResult ).extracting( d -> d)
		   					   .containsExactly(1424,1425,1426,1429);
	}
	
	/**
	 * Test [3] 時間年休上限日数を取得
	 * @param fromGrantTableDays
	 * @return
	 */
	@Test
	public void testLimitedTimeHdDays_isEmpty() {
		TimeAnnualMaxDay maxDay = TimeAnnualMaxDayHelper.createTimeAnnualMaxDay_ManageDistinct_NO(ManageDistinct.NO);
		Optional<LimitedTimeHdDays>  limitedTimeHdDay = maxDay.getLimitedTimeHdDays(Optional.of(new LimitedTimeHdDays(1)));
		assertThat(limitedTimeHdDay).isEmpty();
	}
	
	@Test
	public void testLimitedTimeHdDays_notEmpty_CompanyUniform() {
		TimeAnnualMaxDay maxDay = TimeAnnualMaxDayHelper.
				createTimeAnnualMaxDay_ManageDistinct_YES_CompanyUniform(
						ManageDistinct.YES, MaxDayReference.CompanyUniform, new MaxTimeDay(1));
		Optional<LimitedTimeHdDays>  limitedTimeHdDay = maxDay.getLimitedTimeHdDays(Optional.of(new LimitedTimeHdDays(2)));
		assertThat(limitedTimeHdDay.get().v().equals(new MaxTimeDay(1).v()));
	}
	
	@Test
	public void testLimitedTimeHdDays_notEmpty_notCompanyUniform() {
		TimeAnnualMaxDay maxDay = TimeAnnualMaxDayHelper.
				createTimeAnnualMaxDay_ManageDistinct_YES_CompanyUniform(
						ManageDistinct.YES, MaxDayReference.ReferAnnualGrantTable, new MaxTimeDay(1));
		Optional<LimitedTimeHdDays>  limitedTimeHdDay = maxDay.getLimitedTimeHdDays(Optional.of(new LimitedTimeHdDays(2)));
		assertThat(limitedTimeHdDay.get().v().equals(new LimitedTimeHdDays(2).v()));
	}
	
	/**
	 * Test [4] 上限日数を管理するか 
	 */
	@Test
	public void testIsManageMaximumNumberDays_False() {
		TimeAnnualMaxDay maxDay = TimeAnnualMaxDayHelper.createTimeAnnualMaxDay_ManageDistinct_NO(ManageDistinct.NO);
		boolean checkIsManageMaximum = maxDay.isManageMaximumNumberDays(ManageDistinct.YES , ManageDistinct.YES);
		assertThat(checkIsManageMaximum).isFalse();
	}
	
	@Test
	public void testIsManageMaximumNumberDays_False_1() {
		TimeAnnualMaxDay maxDay = TimeAnnualMaxDayHelper.createTimeAnnualMaxDay_ManageDistinct_NO(ManageDistinct.NO);
		boolean checkIsManageMaximum = maxDay.isManageMaximumNumberDays(ManageDistinct.NO , ManageDistinct.NO);
		assertThat(checkIsManageMaximum).isFalse();
	}
	
	@Test
	public void testIsManageMaximumNumberDays_False_2() {
		TimeAnnualMaxDay maxDay = TimeAnnualMaxDayHelper.createTimeAnnualMaxDay_ManageDistinct_NO(ManageDistinct.NO);
		boolean checkIsManageMaximum = maxDay.isManageMaximumNumberDays(ManageDistinct.YES , ManageDistinct.NO);
		assertThat(checkIsManageMaximum).isFalse();
	}
	
	@Test
	public void testIsManageMaximumNumberDays_False_3() {
		TimeAnnualMaxDay maxDay = TimeAnnualMaxDayHelper.createTimeAnnualMaxDay_ManageDistinct_NO(ManageDistinct.NO);
		boolean checkIsManageMaximum = maxDay.isManageMaximumNumberDays(ManageDistinct.NO , ManageDistinct.YES);
		assertThat(checkIsManageMaximum).isFalse();
	}
	
	@Test
	public void testIsManageMaximumNumberDays_False_4() {
		TimeAnnualMaxDay maxDay = TimeAnnualMaxDayHelper.createTimeAnnualMaxDay_ManageDistinct_NO(ManageDistinct.YES);
		boolean checkIsManageMaximum = maxDay.isManageMaximumNumberDays(ManageDistinct.NO , ManageDistinct.NO);
		assertThat(checkIsManageMaximum).isFalse();
	}
	
	@Test
	public void testIsManageMaximumNumberDays_False_5() {
		TimeAnnualMaxDay maxDay = TimeAnnualMaxDayHelper.createTimeAnnualMaxDay_ManageDistinct_NO(ManageDistinct.YES);
		boolean checkIsManageMaximum = maxDay.isManageMaximumNumberDays(ManageDistinct.NO , ManageDistinct.YES);
		assertThat(checkIsManageMaximum).isFalse();
	}
	
	@Test
	public void testIsManageMaximumNumberDays_False_6() {
		TimeAnnualMaxDay maxDay = TimeAnnualMaxDayHelper.createTimeAnnualMaxDay_ManageDistinct_NO(ManageDistinct.YES);
		boolean checkIsManageMaximum = maxDay.isManageMaximumNumberDays(ManageDistinct.YES , ManageDistinct.NO);
		assertThat(checkIsManageMaximum).isFalse();
	}
	
	@Test
	public void testIsManageMaximumNumberDays_True() {
		TimeAnnualMaxDay maxDay = TimeAnnualMaxDayHelper.createTimeAnnualMaxDay_ManageDistinct_YES(ManageDistinct.YES);
		boolean checkIsManageMaximum = maxDay.isManageMaximumNumberDays(ManageDistinct.YES , ManageDistinct.YES);
		assertThat(checkIsManageMaximum).isTrue();
	}
}
