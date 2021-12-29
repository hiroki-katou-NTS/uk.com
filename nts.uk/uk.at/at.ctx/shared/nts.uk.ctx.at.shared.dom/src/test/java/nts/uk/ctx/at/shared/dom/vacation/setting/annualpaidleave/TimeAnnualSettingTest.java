package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeAnnualRoundProcesCla;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestUnit;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LimitedTimeHdDays;
import nts.uk.shr.com.license.option.OptionLicense;

@RunWith(JMockit.class)
public class TimeAnnualSettingTest {
	@Injectable
	private TimeVacationDigestUnit.Require require;
	
	@Test
	public void getters() {
		TimeAnnualMaxDay annualMaxDay = TimeAnnualMaxDayHelper.createTimeAnnualMaxDay();
		
		TimeAnnualSetting annualSetting = new TimeAnnualSetting(
				annualMaxDay, TimeAnnualRoundProcesCla.RoundUpToTheDay, new TimeAnnualLeaveTimeDay(),
				new TimeVacationDigestUnit(ManageDistinct.YES, TimeDigestiveUnit.OneHour));
		NtsAssert.invokeGetters(annualSetting);
	}
	
	
	/**
     * Test [1] 時間年休に対応する日次の勤怠項目を取得する
     */
	@Test
    public void testDailyAttdItemsCorrespondAnnualLeave(){
		TimeAnnualSetting setting = TimeAnnualSettingHelper.createTimeAnnualSetting();
		List<Integer> attendanceItemIds = setting.getDailyAttdItemsCorrespondAnnualLeave();
		assertThat( attendanceItemIds ).extracting( d -> d)
									   .containsExactly(502,514,595,601,607,613);
    }
	
	/**
     * Test [2] 時間年休に対応する月次の勤怠項目を取得する
     */
	@Test
    public void acquireMonthAttdItemsHourlyAnnualLeave(){
		TimeAnnualSetting setting = TimeAnnualSettingHelper.createTimeAnnualSetting();
    	List<Integer> annualLeaveItems = setting.acquireMonthAttdItemsHourlyAnnualLeave();
    	assertThat( annualLeaveItems ).extracting( d -> d)
									  .containsExactly(1424,1425,1426,1429,1430,1431,1861,1862,1424,1425,1426,1429);
    }
	
	 /**
     * Test [3] 利用できない日次の勤怠項目を取得する
     */
	@Test
    public void testDailyAttendItemsNotAvailable_ManageDistinct_Yes(){
    	TimeAnnualSetting setting = TimeAnnualSettingHelper.createTimeAnnualSetting();
    	new Expectations() {
    		{
    			require.getOptionLicense();
    			result = new OptionLicense() {};
    		}
		};
    	List<Integer> annualLeaveItems = setting.getDailyAttendItemsNotAvailable(require, ManageDistinct.YES);
    	assertThat( annualLeaveItems ).extracting( d -> d)
		  .containsExactly(502,514,595,601,607,613);
    }
    
	@Test
    public void testDailyAttendItemsNotAvailable_ManageDistinct_No(){
    	TimeAnnualSetting setting = TimeAnnualSettingHelper.createTimeAnnualSetting();
    	new Expectations() {
    		{
    			require.getOptionLicense();
    			result = new OptionLicense() {};
    		}
		};
    	List<Integer> annualLeaveItems = setting.getDailyAttendItemsNotAvailable(require, ManageDistinct.NO);
    	assertThat( annualLeaveItems.isEmpty() );
    }
	
	 /**
     * [4] 利用できない月次の勤怠項目を取得する
     */
	@Test
    public void testMonthlyAttendItemsNotAvailable_notEmpty(){
    	TimeAnnualSetting setting = TimeAnnualSettingHelper.createTimeAnnualSetting_ManageDistinct_YES(ManageDistinct.YES);
    	new Expectations() {
    		{
    			require.getOptionLicense();
    			result = new OptionLicense() {};
    		}
		};
    	List<Integer> annualLeaveItems = setting.getMonthlyAttendItemsNotAvailable(require, ManageDistinct.YES);
    	assertThat( annualLeaveItems ).extracting( d -> d)
		  .containsExactly(1424,1425,1426,1429,1430,1431,1861,1862,1424,1425,1426,1429);
    }
	// $時間年休項目 is empty, $上限項目 is empty
	@Test
    public void testMonthlyAttendItemsNotAvailable_isEmpty_1(){
    	TimeAnnualSetting setting = TimeAnnualSettingHelper.createTimeAnnualSetting_ManageDistinct_YES(ManageDistinct.YES);
    	new Expectations() {
    		{
    			require.getOptionLicense();
    			result = new OptionLicense() {};
    		}
		};
    	List<Integer> annualLeaveItems = setting.getMonthlyAttendItemsNotAvailable(require, ManageDistinct.NO);
    	assertThat( annualLeaveItems.isEmpty() );
    }
	
	// $時間年休項目 not empty, $上限項目 is empty
	@Test
    public void testMonthlyAttendItemsNotAvailable_isEmpty_2(){
		TimeAnnualMaxDay annualMaxDay = TimeAnnualMaxDayHelper.createTimeAnnualMaxDay_ManageDistinct_NO(ManageDistinct.NO);
    	TimeAnnualSetting setting = TimeAnnualSettingHelper.createTimeAnnualSetting_UpperLimitItem(ManageDistinct.YES, annualMaxDay);
    	new Expectations() {
    		{
    			require.getOptionLicense();
    			result = new OptionLicense() {};
    		}
		};
    	List<Integer> annualLeaveItems = setting.getMonthlyAttendItemsNotAvailable(require, ManageDistinct.YES);
    	assertThat( annualLeaveItems ).extracting( d -> d)
		  .containsExactly(1424,1425,1426,1429,1430,1431,1861,1862);
    }

	 /**
     * Test [5] 時間年休を管理するか ManageDistinct NO
     */
	@Test
    public void testNotIsManageTimeAnnualLeave_fail() {
    	TimeAnnualSetting setting = TimeAnnualSettingHelper.createTimeAnnualSetting_ManageDistinct_NO(ManageDistinct.NO);
    	new Expectations() {
    		{
    			require.getOptionLicense();
    			result = new OptionLicense() {};
    		}
		};
    	boolean checkManageTime = setting.isManageTimeAnnualLeave(require, ManageDistinct.NO);
    	assertThat(checkManageTime).isFalse();
    }
	
	@Test
    public void testNotIsManageTimeAnnualLeave_fail_1() {
    	TimeAnnualSetting setting = TimeAnnualSettingHelper.createTimeAnnualSetting_ManageDistinct_NO(ManageDistinct.NO);
    	new Expectations() {
    		{
    			require.getOptionLicense();
    			result = new OptionLicense() {};
    		}
		};
    	boolean checkManageTime = setting.isManageTimeAnnualLeave(require, ManageDistinct.YES);
    	assertThat(checkManageTime).isFalse();
    }
	
	@Test
    public void testNotIsManageTimeAnnualLeave_fail_2() {
    	TimeAnnualSetting setting = TimeAnnualSettingHelper.createTimeAnnualSetting_ManageDistinct_YES(ManageDistinct.YES);
    	new Expectations() {
    		{
    			require.getOptionLicense();
    			result = new OptionLicense() {};
    		}
		};
    	boolean checkManageTime = setting.isManageTimeAnnualLeave(require, ManageDistinct.NO);
    	assertThat(checkManageTime).isFalse();
    }
    
    /**
     * Test [5] 時間年休を管理するか ManageDistinct Yes
     */
	@Test
    public void testIsManageTimeAnnualLeave() {
    	TimeAnnualSetting setting = TimeAnnualSettingHelper.createTimeAnnualSetting_ManageDistinct_YES(ManageDistinct.YES);
    	new Expectations() {
    		{
    			require.getOptionLicense();
    			result = new OptionLicense() {};
    		}
		};
    	boolean checkManageTime = setting.isManageTimeAnnualLeave(require, ManageDistinct.YES);
    	assertThat(checkManageTime).isTrue();
    }
	
	/**
     * Test [6] 時間年休上限日数を取得
     */
	@Test
    public void testLimitedTimeHdDays(){
    	TimeAnnualSetting setting = TimeAnnualSettingHelper.createTimeAnnualSetting();
    	Optional<LimitedTimeHdDays> fromGrantTableDays = Optional.of(new LimitedTimeHdDays(10));
    	Optional<LimitedTimeHdDays> limitedTimeHdDays = setting.getLimitedTimeHdDays(fromGrantTableDays);
    	assertThat(limitedTimeHdDays.get().equals(fromGrantTableDays.get()));
    }
	
	/**
	 * Test [7] 消化単位をチェックする
	 * Case 1: $Option.就業.時間休暇 = true && 「休暇使用時間」 % 「@消化単位」 = 0
	 */
	@Test
	public void testCheckDigestUnits1() {
		TimeAnnualSetting setting = TimeAnnualSettingHelper.createTimeAnnualSetting();
		new Expectations() {
    		{
    			require.getOptionLicense();
    			result = TimeAnnualSettingHelper.getOptionLicense(true);
    		}
		};
    	boolean checkDigestUnits = setting.checkDigestUnits(require, new AttendanceTime(600), ManageDistinct.YES);
    	assertThat(checkDigestUnits).isTrue();
	}
	
	/**
	 * Test [7] 消化単位をチェックする
	 * Case 2: $Option.就業.時間休暇 = true && 「休暇使用時間」 % 「@消化単位」 != 0
	 */
	@Test
	public void testCheckDigestUnits2() {
		TimeAnnualSetting setting = TimeAnnualSettingHelper.createTimeAnnualSetting();
		new Expectations() {
    		{
    			require.getOptionLicense();
    			result = TimeAnnualSettingHelper.getOptionLicense(true);
    		}
		};
    	boolean checkDigestUnits = setting.checkDigestUnits(require, new AttendanceTime(11), ManageDistinct.YES);
    	assertThat(checkDigestUnits).isFalse();
	}
}
