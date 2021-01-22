package nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.personCounter;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.AttendanceTimesForAggregation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;


/**
 * UTコード
 * 個人計の労働時間カテゴリを集計する
 * @author lan_lt
 *
 */
@RunWith(JMockit.class)
public class CountWorkingTimeOfPerCtgServiceTest {
	
	private List<AttendanceTimesForAggregation> attendanceUnits = Arrays.asList(AttendanceTimesForAggregation.WORKING_TOTAL
			, AttendanceTimesForAggregation.WORKING_WITHIN
			, AttendanceTimesForAggregation.WORKING_EXTRA);
	
	@Test
	public void countWorkingTimeOfPerCase1() {
		
		
	}
	
	
	public static class Helper {
		public static IntegrationOfDaily createDailyWorks(String sid, GeneralDate ymd, AttendanceTimeOfDailyAttendance attendance) {
			return null;
		}
		
//		@Injectable 
//		static WorkScheduleTimeOfDaily timeOfDaily;
//		
//		@Injectable
//		static StayingTimeOfDaily stayingTimeOfDaily;
//		
//		@Injectable 
//		static AttendanceTimeOfExistMinus budget;
//		
//		@Injectable 
//		static AttendanceTimeOfExistMinus unEmploy;
//		
//		public static AttendanceTimeOfDailyAttendance createWithActualWorkingTimeOfDaily(
//				TotalWorkingTime totalTime ) {
//			
//			return new AttendanceTimeOfDailyAttendance(timeOfDaily, actualWorkingTimeOfDaily, stayingTimeOfDaily, budget, unEmploy);
//		}
	}
}
