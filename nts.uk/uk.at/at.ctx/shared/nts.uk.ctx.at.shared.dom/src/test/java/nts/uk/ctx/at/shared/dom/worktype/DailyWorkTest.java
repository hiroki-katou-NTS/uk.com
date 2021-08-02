package nts.uk.ctx.at.shared.dom.worktype;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;

/**
 * Test for DailyWork
 * @author kumiko_otake
 *
 */
@RunWith(JMockit.class)
public class DailyWorkTest {

	@Test
	public void testGetters() {
		val instance = new DailyWork(
									WorkTypeUnit.OneDay
								,	WorkTypeClassification.Holiday
								,	WorkTypeClassification.Holiday
								,	WorkTypeClassification.Holiday
							);
		NtsAssert.invokeGetters(instance);
	}


	/**
	 * Target	: chechAttendanceDay
	 * Pattern	:
	 * 		勤務の単位 -> 1日
	 * 		勤務の分類(1日) -> 休日系( WorkTypeClassification#isHolidayType() is true )
	 * Output	: AttendanceDayAttr.HOLIDAY
	 */
	@Test
	public void testChechAttendanceDay_WholeDay_HolidayType() {

		// Execute
		val result = Helper.getClasses( e -> e.isHolidayType() ).stream()
							.map( e -> Helper.getDailyWorkAsWholeDay( e ).chechAttendanceDay() )
							.collect(Collectors.toList());

		// Assertion
		assertThat( result ).containsOnly( AttendanceDayAttr.HOLIDAY );

	}

	/**
	 * Target	: chechAttendanceDay
	 * Pattern	:
	 * 		勤務の単位 -> 1日
	 * 		勤務の分類(1日) -> 休出( WorkTypeClassification#isHolidayWork() is true )
	 * Output	: AttendanceDayAttr.HOLIDAY_WORK
	 */
	@Test
	public void testChechAttendanceDay_WholeDay_HolidayWork() {

		// Execute
		val result = Helper.getClasses( e -> e.isHolidayWork() ).stream()
							.map( e -> Helper.getDailyWorkAsWholeDay( e ).chechAttendanceDay() )
							.collect(Collectors.toList());

		// Assertion
		assertThat( result ).containsOnly( AttendanceDayAttr.HOLIDAY_WORK );

	}

	/**
	 * Target	: chechAttendanceDay
	 * Pattern	:
	 * 		勤務の単位 -> 1日
	 * 		勤務の分類(1日) -> 休日系/休出 以外
	 * Output	: AttendanceDayAttr.FULL_TIME
	 */
	@Test
	public void testChechAttendanceDay_WholeDay_WholeDayWorking() {

		// Execute
		val result = Helper.getClasses( e -> !e.isHolidayType() && !e.isHolidayWork() ).stream()
							.map( e -> Helper.getDailyWorkAsWholeDay( e ).chechAttendanceDay() )
							.collect(Collectors.toList());

		// Assertion
		assertThat( result ).containsOnly( AttendanceDayAttr.FULL_TIME );

	}

	/**
	 * Target	: chechAttendanceDay
	 * Pattern	:
	 * 		勤務の単位 -> 午前と午後
	 * 		勤務の分類(午前) -> 休日系
	 * 		勤務の分類(午後) -> 休日系
	 * Output	: AttendanceDayAttr.HOLIDAY
	 */
	@Test
	public void testChechAttendanceDay_HalfDay_AMisHolidayType_PMisHolidayType() {

		// 午前 -> 休日系 && 午後 -> 休日系
		val holidayTypes = Helper.getClasses( e -> e.isHolidayType() );
		val dailyWork = Helper.getDailyWorkAsHalfDay( holidayTypes.get(0), holidayTypes.get(holidayTypes.size()-1) );

		// Execute
		val result = dailyWork.chechAttendanceDay();

		// Assertion
		assertThat( result ).isEqualTo( AttendanceDayAttr.HOLIDAY );

	}

	/**
	 * Target	: chechAttendanceDay
	 * Pattern	:
	 * 		勤務の単位 -> 午前と午後
	 * 		勤務の分類(午前) -> 休日系 以外
	 * 		勤務の分類(午後) -> 休日系 以外
	 * Output	: AttendanceDayAttr.FULL_TIME
	 */
	@Test
	public void testChechAttendanceDay_HalfDay_AMisNotHolidayType_PMisNotHolidayType() {

		// 午前 -> 休日系 以外 && 午後 -> 休日系 以外
		val workingTypes = Helper.getClasses( e -> !e.isHolidayType() );
		val dailyWork = Helper.getDailyWorkAsHalfDay( workingTypes.get(0), workingTypes.get(workingTypes.size()-1) );

		// Execute
		val result = dailyWork.chechAttendanceDay();

		// Assertion
		assertThat( result ).isEqualTo( AttendanceDayAttr.FULL_TIME );

	}

	/**
	 * Target	: chechAttendanceDay
	 * Pattern	:
	 * 		勤務の単位 -> 午前と午後
	 * 		勤務の分類(午前) -> 休日系 以外
	 * 		勤務の分類(午後) -> 休日系
	 * Output	: AttendanceDayAttr.HALF_TIME_AM
	 */
	@Test
	public void testChechAttendanceDay_HalfDay_AMisNotHolidayType_PMisHolidayType() {

		// 午前 -> 休日系 以外 && 午後 -> 休日系
		val workingTypes = Helper.getClasses( e -> !e.isHolidayType() );
		val holidayTypes = Helper.getClasses( e -> e.isHolidayType() );
		val dailyWork = Helper.getDailyWorkAsHalfDay( workingTypes.get(0), holidayTypes.get(0) );

		// Execute
		val result = dailyWork.chechAttendanceDay();

		// Assertion
		assertThat( result ).isEqualTo( AttendanceDayAttr.HALF_TIME_AM );

	}

	/**
	 * Target	: chechAttendanceDay
	 * Pattern	:
	 * 		勤務の単位 -> 午前と午後
	 * 		勤務の分類(午前) -> 休日系
	 * 		勤務の分類(午後) -> 休日系 以外
	 * Output	: AttendanceDayAttr.HALF_TIME_PM
	 */
	@Test
	public void testChechAttendanceDay_HalfDay_AMisHolidayType_PMisNotHolidayType() {

		// 午前 -> 休日系 && 午後 -> 休日系 以外
		val workingTypes = Helper.getClasses( e -> !e.isHolidayType() );
		val holidayTypes = Helper.getClasses( e -> e.isHolidayType() );
		val dailyWork = Helper.getDailyWorkAsHalfDay( holidayTypes.get(holidayTypes.size()-1), workingTypes.get(workingTypes.size()-1) );

		// Execute
		val result = dailyWork.chechAttendanceDay();

		// Assertion
		assertThat( result ).isEqualTo( AttendanceDayAttr.HALF_TIME_PM );

	}
	
	@SuppressWarnings("static-access")
	public static class TestGetHalfDayWorkTypeClassification {
		
		@Test
		public void oneDayCase(@Mocked HalfDayWorkTypeClassification halfDay) {
			
			DailyWork target = Helper.createDailyByWorkTypeUnit(WorkTypeUnit.OneDay);
		
			new Expectations() {
				{
					halfDay.createByWholeDay((WorkTypeClassification) any);
					times = 1;
					
					halfDay.createByAmAndPm((WorkTypeClassification) any, (WorkTypeClassification) any);
					times = 0;
				}
			};
			
			target.getHalfDayWorkTypeClassification();
		}
		
		@Test
		public void notOneDayCase(@Mocked HalfDayWorkTypeClassification halfDay) {
			
			DailyWork target = Helper.createDailyByWorkTypeUnit(WorkTypeUnit.MonringAndAfternoon);
			
			new Expectations() {
				{
					halfDay.createByWholeDay((WorkTypeClassification) any);
					times = 0;
					
					halfDay.createByAmAndPm((WorkTypeClassification) any, (WorkTypeClassification) any);
					times = 1;
				}
			};
			
			target.getHalfDayWorkTypeClassification();
		}
		
	}
	

    
    /**
     * Target: determineHolidayByWorkType
     * Pattern: 
     *      勤務の単位 -> 1日
     *      1日 -> 勤務種類の分類 in (休業, 休職, 休日出勤       休出, 連続勤務, 振出, 欠勤, 出勤)
     *      1日の勤務.勤務種類からどんな休暇種類を含むか判断する
     * Ouput:
     *      休暇種類 -> all false
     */
    @Test
    public void testDetermineHolidayByWorkType_isOneDay_asNoVacation() {
        List<WorkTypeClassification> noVacationClassifications = Arrays.asList(WorkTypeClassification.Closure, WorkTypeClassification.LeaveOfAbsence, 
                WorkTypeClassification.HolidayWork, WorkTypeClassification.ContinuousWork, WorkTypeClassification.Shooting, 
                WorkTypeClassification.Absence, WorkTypeClassification.Attendance);
        
        val targets = Helper.getClasses( e -> noVacationClassifications.contains(e) ).stream()
                .map( e -> Helper.getDailyWorkAsWholeDay( e ).determineHolidayByWorkType() )
                .map( e -> !e.isAnnualHoliday() && !e.isYearlyReserved() && !e.isSubstituteHoliday() && !e.isPause() && !e.isHoliday() && !e.isSpecialHoliday() && !e.isTimeDigestVacation())
                .collect(Collectors.toList());
        
        assertThat(targets).containsOnly(true);
    }
    
    /**
     * Target: determineHolidayByWorkType
     * Pattern: 
     *      勤務の単位 -> 1日
     *      1日 -> 勤務種類の分類 = 年休
     *      1日の勤務.勤務種類からどんな休暇種類を含むか判断する
     * Output: 
     *      休暇種類.年休 = true
     */
    @Test
    public void testDetermineHolidayByWorkType_isOneDay_asAnnualHoliday() {
        val target = Helper.getDailyWorkAsWholeDay(WorkTypeClassification.AnnualHoliday).determineHolidayByWorkType();
        
        assertThat(target).isEqualToComparingFieldByField(HolidayTest.Helper.createHoliday(true, false, false, false, false, false, false));
    }
    
    /**
     * Target: determineHolidayByWorkType
     * Pattern: 
     *      勤務の単位 -> 1日
     *      1日 -> 勤務種類の分類 = 積立年休
     *      1日の勤務.勤務種類からどんな休暇種類を含むか判断する
     * Output: 
     *      休暇種類.積立年休 = true
     */
    @Test
    public void testDetermineHolidayByWorkType_isOneDay_asYearlyReserved() {
        val target = Helper.getDailyWorkAsWholeDay(WorkTypeClassification.YearlyReserved).determineHolidayByWorkType();
        
        assertThat(target).isEqualToComparingFieldByField(HolidayTest.Helper.createHoliday(false, true, false, false, false, false, false));
    }
    
    /**
     * Target: determineHolidayByWorkType
     * Pattern: 
     *      勤務の単位 -> 1日
     *      1日 -> 勤務種類の分類 = 代休
     *      1日の勤務.勤務種類からどんな休暇種類を含むか判断する
     * Output: 
     *      休暇種類.代休 = true
     */
    @Test
    public void testDetermineHolidayByWorkType_isOneDay_asSubstituteHoliday() {
        val target = Helper.getDailyWorkAsWholeDay(WorkTypeClassification.SubstituteHoliday).determineHolidayByWorkType();
        
        assertThat(target).isEqualToComparingFieldByField(HolidayTest.Helper.createHoliday(false, false, true, false, false, false, false));
    }
    
    /**
     * Target: determineHolidayByWorkType
     * Pattern: 
     *      勤務の単位 -> 1日
     *      1日 -> 勤務種類の分類 = 振休
     *      1日の勤務.勤務種類からどんな休暇種類を含むか判断する
     * Output: 
     *      休暇種類.振休 = true
     */
    @Test
    public void testDetermineHolidayByWorkType_isOneDay_asPause() {
        val target = Helper.getDailyWorkAsWholeDay(WorkTypeClassification.Pause).determineHolidayByWorkType();
        
        assertThat(target).isEqualToComparingFieldByField(HolidayTest.Helper.createHoliday(false, false, false, true, false, false, false));
    }
    
    /**
     * Target: determineHolidayByWorkType
     * Pattern: 
     *      勤務の単位 -> 1日
     *      1日 -> 勤務種類の分類 = 休日
     *      1日の勤務.勤務種類からどんな休暇種類を含むか判断する
     * Output: 
     *      休暇種類.休日 = true
     */
    @Test
    public void testDetermineHolidayByWorkType_isOneDay_asHoliday() {
        val target = Helper.getDailyWorkAsWholeDay(WorkTypeClassification.Holiday).determineHolidayByWorkType();
        
        assertThat(target).isEqualToComparingFieldByField(HolidayTest.Helper.createHoliday(false, false, false, false, true, false, false));
    }
    
    /**
     * Target: determineHolidayByWorkType
     * Pattern: 
     *      勤務の単位 -> 1日
     *      1日 -> 勤務種類の分類 = 特別休暇
     *      1日の勤務.勤務種類からどんな休暇種類を含むか判断する
     * Output: 
     *      休暇種類.特別休暇 = true
     */
    @Test
    public void testDetermineHolidayByWorkType_isOneDay_asSpecialHoliday() {
        val target = Helper.getDailyWorkAsWholeDay(WorkTypeClassification.SpecialHoliday).determineHolidayByWorkType();
        
        assertThat(target).isEqualToComparingFieldByField(HolidayTest.Helper.createHoliday(false, false, false, false, false, true, false));
    }
    
    /**
     * Target: determineHolidayByWorkType
     * Pattern: 
     *      勤務の単位 -> 午前と午後
     *      午前 -> 勤務種類の分類 = 休暇なし
     *      午後 -> 勤務種類の分類 = 休暇なし
     *      1日の勤務.勤務種類からどんな休暇種類を含むか判断する
     * Output: 
     *      休暇種類 -> all false
     */
    @Test
    public void testDetermineHolidayByWorkType_isHalfDay_noVacation() {
        val target = Helper.getDailyWorkAsHalfDay(WorkTypeClassification.Closure, WorkTypeClassification.LeaveOfAbsence).determineHolidayByWorkType();
        
        assertThat(target).isEqualToComparingFieldByField(HolidayTest.Helper.createHoliday(false, false, false, false, false, false, false));
    }
    
    /**
     * Target: determineHolidayByWorkType
     * Pattern: 
     *      勤務の単位 -> 午前と午後
     *      午前 -> 勤務種類の分類 = 休日
     *      午後 -> 勤務種類の分類 = 年休
     *      1日の勤務.勤務種類からどんな休暇種類を含むか判断する
     * Output: 
     *      休暇種類.年休 = true && 休暇種類.休日 = true
     */
    @Test
    public void testDetermineHolidayByWorkType_isHalfDay_AmPmVacation() {
        val target = Helper.getDailyWorkAsHalfDay(WorkTypeClassification.Holiday, WorkTypeClassification.AnnualHoliday).determineHolidayByWorkType();
        
        assertThat(target).isEqualToComparingFieldByField(HolidayTest.Helper.createHoliday(true, false, false, false, true, false, false));
    }
    
    /**
     * Target: determineHolidayByWorkType
     * Pattern: 
     *      勤務の単位 -> 午前と午後
     *      午前 -> 勤務種類の分類 = 休日
     *      午後 -> 勤務種類の分類 = 休暇なし
     *      1日の勤務.勤務種類からどんな休暇種類を含むか判断する
     * Output: 
     *      休暇種類.休日 = true
     */
    @Test
    public void testDetermineHolidayByWorkType_isHalfDay_AmHoliday() {
        val target = Helper.getDailyWorkAsHalfDay(WorkTypeClassification.Holiday, WorkTypeClassification.Closure).determineHolidayByWorkType();
        
        assertThat(target).isEqualToComparingFieldByField(HolidayTest.Helper.createHoliday(false, false, false, false, true, false, false));
    }
    
    /**
     * Target: determineHolidayByWorkType
     * Pattern: 
     *      勤務の単位 -> 午前と午後
     *      午前 -> 勤務種類の分類 = 休暇なし
     *      午後 -> 勤務種類の分類 = 休日
     *      1日の勤務.勤務種類からどんな休暇種類を含むか判断する
     * Output: 
     *      休暇種類.休日 = true
     */
    @Test
    public void testDetermineHolidayByWorkType_isHalfDay_PmHoliday() {
        val target = Helper.getDailyWorkAsHalfDay(WorkTypeClassification.Closure, WorkTypeClassification.Holiday).determineHolidayByWorkType();
        
        assertThat(target).isEqualToComparingFieldByField(HolidayTest.Helper.createHoliday(false, false, false, false, true, false, false));
    }

	protected static class Helper {

		/**
		 * 1日単位の１日の勤務を取得する
		 * @param forOneDay 1日の勤務の分類
		 * @return １日の勤務(勤務の単位 -> 1日)
		 */
		public static DailyWork getDailyWorkAsWholeDay( WorkTypeClassification forOneDay ) {

			return new DailyWork( WorkTypeUnit.OneDay, forOneDay, WorkTypeClassification.Holiday, WorkTypeClassification.Holiday);

		}

		/**
		 * 半日単位の１日の勤務を取得する
		 * @param forAm 午前の勤務の分類
		 * @param forPm 午後の勤務の分類
		 * @return １日の勤務(勤務の単位 -> 午前と午後)
		 */
		public static DailyWork getDailyWorkAsHalfDay( WorkTypeClassification forAm, WorkTypeClassification forPm ) {

			return new DailyWork( WorkTypeUnit.MonringAndAfternoon, WorkTypeClassification.Holiday, forAm, forPm );

		}
		/**
		 * 1日の勤務を作る
		 * @param workTypeUnit 勤務区分
		 * @return
		 */
		public static DailyWork createDailyByWorkTypeUnit(WorkTypeUnit workTypeUnit) {
			return new DailyWork( workTypeUnit
								, WorkTypeClassification.Attendance
								, WorkTypeClassification.AnnualHoliday
								, WorkTypeClassification.Absence);
		}


		/**
		 * 条件を指定して勤務の分類を取得する
		 * @param func
		 * @return 勤務の分類のリスト
		 */
		public static List<WorkTypeClassification> getClasses( Predicate<WorkTypeClassification> func ) {

			return Stream.of( WorkTypeClassification.values() )
						.filter( e -> func.test( e ) )
						.collect(Collectors.toList());

		}

	}

}
