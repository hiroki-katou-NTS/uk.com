package nts.uk.ctx.at.shared.dom.worktype;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;

import lombok.val;

public class WorkTypeClassificationTest {
	
	@Test
	public void isWeekDayAttendance() {
		//
		//val target =  new 
		//取得した値
		val actual = WorkTypeClassification.Holiday.isAttendance();
		//予測値
		val expected = false;
		
		assertThat("A",actual,is(expected));	
		
		
		
		val actual2 = WorkTypeClassification.Absence.isAttendance();
		//予測値
		val expected2 = false;
		
		assertThat("B",actual2,is(expected2));
	
		
	}
	
	/**
	 * Target: determineHolidayType
	 * Patter: 
	 *     勤務種類の分類 in (休業, 休職, 休日出勤       休出, 連続勤務, 振出, 欠勤, 出勤)
	 *     勤務種類の分類.どんな休暇種類か判断する
	 * Output: 
	 *     休暇種類区分 = 休暇なし
	 */
	@Test
	public void testDetermineHolidayType_Novacation() {
	    val targets = Arrays.asList(WorkTypeClassification.Closure, WorkTypeClassification.LeaveOfAbsence, 
                WorkTypeClassification.HolidayWork, WorkTypeClassification.ContinuousWork, WorkTypeClassification.Shooting, 
                WorkTypeClassification.Absence, WorkTypeClassification.Attendance).stream().map(x -> x.determineHolidayType());
	    
	    org.assertj.core.api.Assertions.assertThat(targets).containsOnly(HolidayTypeClassification.NoVacation);
	}
	
	/**
     * Target: determineHolidayType
     * Patter: 
     *     勤務種類の分類  = 休日
     *     勤務種類の分類.どんな休暇種類か判断する
     * Output: 
     *     休暇種類区分 = 休日
     */
	@Test
	public void testDetermineHolidayType_Holiday() {
	    val target = WorkTypeClassification.Holiday.determineHolidayType();
	    
	    org.assertj.core.api.Assertions.assertThat(target).isEqualTo(HolidayTypeClassification.Holiday);
	}
	
	/**
     * Target: determineHolidayType
     * Patter: 
     *     勤務種類の分類  = 年休
     *     勤務種類の分類.どんな休暇種類か判断する
     * Output: 
     *     休暇種類区分 = 年休
     */
	@Test
    public void testDetermineHolidayType_AnnualHoliday() {
        val target = WorkTypeClassification.AnnualHoliday.determineHolidayType();
        
        org.assertj.core.api.Assertions.assertThat(target).isEqualTo(HolidayTypeClassification.AnnualHoliday);
    }
	
	/**
     * Target: determineHolidayType
     * Patter: 
     *     勤務種類の分類  = 積立年休
     *     勤務種類の分類.どんな休暇種類か判断する
     * Output: 
     *     休暇種類区分 = 積立年休
     */
	@Test
    public void testDetermineHolidayType_YearlyReserved() {
        val target = WorkTypeClassification.YearlyReserved.determineHolidayType();
        
        org.assertj.core.api.Assertions.assertThat(target).isEqualTo(HolidayTypeClassification.YearlyReserved);
    }
	
	/**
     * Target: determineHolidayType
     * Patter: 
     *     勤務種類の分類  = 特別休暇
     *     勤務種類の分類.どんな休暇種類か判断する
     * Output: 
     *     休暇種類区分 = 特別休暇
     */
	@Test
    public void testDetermineHolidayType_SpecialHoliday() {
        val target = WorkTypeClassification.SpecialHoliday.determineHolidayType();
        
        org.assertj.core.api.Assertions.assertThat(target).isEqualTo(HolidayTypeClassification.SpecialHoliday);
    }
	
	/**
     * Target: determineHolidayType
     * Patter: 
     *     勤務種類の分類  = 代休
     *     勤務種類の分類.どんな休暇種類か判断する
     * Output: 
     *     休暇種類区分 = 代休
     */
	@Test
    public void testDetermineHolidayType_SubstituteHoliday() {
        val target = WorkTypeClassification.SubstituteHoliday.determineHolidayType();
        
        org.assertj.core.api.Assertions.assertThat(target).isEqualTo(HolidayTypeClassification.SubstituteHoliday);
    }
	
	/**
     * Target: determineHolidayType
     * Patter: 
     *     勤務種類の分類  = 振休
     *     勤務種類の分類.どんな休暇種類か判断する
     * Output: 
     *     休暇種類区分 = 振休
     */
	@Test
    public void testDetermineHolidayType_Pause() {
        val target = WorkTypeClassification.Pause.determineHolidayType();
        
        org.assertj.core.api.Assertions.assertThat(target).isEqualTo(HolidayTypeClassification.Pause);
    }
	
	/**
     * Target: determineHolidayType
     * Patter: 
     *     勤務種類の分類  = 時間消化休暇
     *     勤務種類の分類.どんな休暇種類か判断する
     * Output: 
     *     休暇種類区分 = 時間消化休暇
     */
	@Test
    public void testDetermineHolidayType_TimeDigestVacation() {
        val target = WorkTypeClassification.TimeDigestVacation.determineHolidayType();
        
        org.assertj.core.api.Assertions.assertThat(target).isEqualTo(HolidayTypeClassification.TimeDigestVacation);
    }
}
