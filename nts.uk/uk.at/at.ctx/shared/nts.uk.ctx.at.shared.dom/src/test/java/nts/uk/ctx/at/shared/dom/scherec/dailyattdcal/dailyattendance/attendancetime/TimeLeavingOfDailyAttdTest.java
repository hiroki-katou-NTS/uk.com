package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.assertj.core.groups.Tuple;
import org.junit.Test;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.EngravingMethod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.ReasonTimeChange;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class TimeLeavingOfDailyAttdTest {
	
	@Test
	public void getters() {

		val timeLeaving = new TimeLeavingOfDailyAttd(new ArrayList<>(), new WorkTimes(4));
		NtsAssert.invokeGetters(timeLeaving);
	}
	
	@Test
	public void create_success() {
		val timeLeavingWork = Helper.createTimeLeavingWork(new WorkNo(1));
		val timeLeavingDaily = new TimeLeavingOfDailyAttd(Arrays.asList(timeLeavingWork), new WorkTimes(4));
		
		assertThat(timeLeavingDaily.getTimeLeavingWorks())
		.extracting(
				d -> d.getWorkNo(),
				d -> d.getAttendanceStamp().get(),
				d -> d.getLeaveStamp().get(),
				d -> d.isCanceledLate(),
				d -> d.isCanceledEarlyLeave())
		.containsExactly(
				Tuple.tuple(timeLeavingWork.getWorkNo()
					  , timeLeavingWork.getAttendanceStamp().get()
					  , timeLeavingWork.getLeaveStamp().get()
					  , true, true));
	}
	
	/**
	 * 出退勤の時間帯を返す
	 * 
	 */
	@Test
	public void getTimeOfTimeLeavingAtt() {
		val startTimeWork = Helper.createTimeStamp(new TimeWithDayAttr(480));
		val endTimeWork = Helper.createTimeStamp(new TimeWithDayAttr(1020));
		val timeLeavingWork = new TimeLeavingWork( new WorkNo(1), Optional.of(startTimeWork), Optional.of(endTimeWork), true, true);
		val timeLeavingDaily = new TimeLeavingOfDailyAttd(Arrays.asList(timeLeavingWork), new WorkTimes(4));
		
		val actual = timeLeavingDaily.getTimeOfTimeLeavingAtt();
		assertThat(actual).containsOnly(new TimeSpanForCalc(  timeLeavingWork.getAttendanceStampTimeWithDay().get()
				                                            , timeLeavingWork.getleaveStampTimeWithDay().get()));
	}
	
	/**
	 * 勤務開始の休暇時間帯を取得する
	 * 出退勤 = empty。
	 * 勤務開始の休暇時間帯を取得する = empty
	 */
	@Test
	public void getStartTimeVacations_timeLeavingDaily_empty() {
		val timeLeavingDaily = new TimeLeavingOfDailyAttd(Collections.emptyList(), new WorkTimes(4));
		val actual = timeLeavingDaily.getStartTimeVacations(new WorkNo(3));
		assertThat(actual).isEmpty();
	}
	
	/**
	 * 勤務開始の休暇時間帯を取得する
	 * 日別勤怠の出退勤の出退勤中にパラメータ勤務NOがない。
	 * 勤務開始の休暇時間帯を取得する = empty
	 */
	@Test
	public void getStartTimeVacations_not_existed_work_no() {
		val vacation = new TimeZone(new TimeWithDayAttr(1100), new TimeWithDayAttr(1200));
		val timeLeavingDaily = Helper.createTimeLeavingOfDailyAttd(new WorkNo(1), vacation);
		
		val actual = timeLeavingDaily.getStartTimeVacations(new WorkNo(3));
		assertThat(actual).isEmpty();
	}
	
	/**
	 * 
	 * 勤務開始の休暇時間帯を取得する
	 * 日別勤怠の出退勤の出退勤中にパラメータ勤務NOがある、でも時間休暇時間帯 empty(vacation = empty)
	 * 勤務開始の休暇時間帯を取得する =  empty
	 */
	@Test
	public void getStartTimeVacations_vacation_is_empty() {
		TimeZone vacation = null;
		val timeLeavingDaily = Helper.createTimeLeavingOfDailyAttd(new WorkNo(1), vacation);
		
		val actual = timeLeavingDaily.getStartTimeVacations(new WorkNo(1));
		assertThat(actual).isEmpty();
	}
	
	/**
	 * 
	 * 勤務開始の休暇時間帯を取得する
	 * 日別勤怠の出退勤の出退勤中にパラメータ勤務NOがある、時間休暇時間帯 not empty
	 * 勤務開始の休暇時間帯を取得する not empty
	 */
	@Test
	public void getStartTimeVacations_vacation_not_empty() {
        val vacation  = new TimeZone(new TimeWithDayAttr(1200), new TimeWithDayAttr(1300));
        val timeLeavingDaily = Helper.createTimeLeavingOfDailyAttd(new WorkNo(1), vacation);
		val actual = timeLeavingDaily.getStartTimeVacations(new WorkNo(1));
		
		assertThat(actual.get().getStart()).isEqualTo(vacation.getStart());
		assertThat(actual.get().getEnd()).isEqualTo(vacation.getEnd());
		
	}
	
	/**
	 * 勤務終了の休暇時間帯を取得する
	 * 出退勤 = empty。
	 * 勤務終了の休暇時間帯を取得する = empty
	 */
	@Test
	public void getEndTimeVacations_get_timeLeavingWorks_empty() {
		val timeLeavingDaily = new TimeLeavingOfDailyAttd(Collections.emptyList(), new WorkTimes(4));
		val actual = timeLeavingDaily.getEndTimeVacations(new WorkNo(3));
		assertThat(actual).isEmpty();
	}
	
	/**
	 * 勤務終了の休暇時間帯を取得する (WorkNo =1)
	 * 日別勤怠の出退勤の出退勤中にパラメータ勤務NOがない  (WorkNo =3)
	 * 勤務開始の休暇時間帯を取得する =  empty
	 */
	@Test
	public void getEndTimeVacations_not_existed_work_no() {
        val vacation = new TimeZone(new TimeWithDayAttr(1200), new TimeWithDayAttr(1300));
        val timeLeavingDaily = Helper.createTimeLeavingOfDailyAttd(new WorkNo(1), vacation);
        
		val actual = timeLeavingDaily.getEndTimeVacations(new WorkNo(3));
		assertThat(actual).isEmpty();
	}
	
	/**
	 * 
	 * 勤務終了の休暇時間帯を取得する
	 * 日別勤怠の出退勤の出退勤中にパラメータ勤務NOがある、でも時間休暇時間帯 empty (vacation = empty)
	 * 勤務終了の休暇時間帯を取得する empty
	 * 
	 */
	@Test
	public void getEndTimeVacations_vacation_empty() {
		TimeZone vacation = null;
		val timeLeavingDaily = Helper.createTimeLeavingOfDailyAttd(new WorkNo(1), vacation);
		
		val actual = timeLeavingDaily.getEndTimeVacations(new WorkNo(1));
		assertThat(actual).isEmpty();
	}
	
	/**
	 * 
	 * 勤務終了の休暇時間帯を取得する
	 * 日別勤怠の出退勤の出退勤中にパラメータ勤務NOがある、時間休暇時間帯 not empty
	 * 勤務終了の休暇時間帯を取得する not empty
	 */
	@Test
	public void getEndTimeVacations_vacation_not_empty() {
        val vacation = new TimeZone(new TimeWithDayAttr(1200), new TimeWithDayAttr(1300));
        val timeLeavingDaily = Helper.createTimeLeavingOfDailyAttd(new WorkNo(1), vacation);
		
		val actual = timeLeavingDaily.getEndTimeVacations(new WorkNo(1));
		assertThat(actual.get().getStart()).isEqualTo(vacation.getStart());
		assertThat(actual.get().getEnd()).isEqualTo(vacation.getEnd());
	}
	
	
	static class Helper {
		
		public static TimeLeavingWork createTimeLeavingWork(WorkNo workNo) {
			val vacations  = new TimeZone(new TimeWithDayAttr(1100), new TimeWithDayAttr(1200));
			return new TimeLeavingWork(
					    new WorkNo(1), Optional.of(Helper.createTimeStampforVacation(new TimeWithDayAttr(480), vacations))
					  , Optional.of(Helper.createTimeStampforVacation(new TimeWithDayAttr(1020), vacations)), true, true);
			
		}
		
		/**
		 * 時刻を指定して勤怠打刻を作る
		 * @param time　時刻（分）
		 * @return
		 */
		public static TimeActualStamp createTimeStamp(TimeWithDayAttr time) {
			WorkTimeInformation workTimeInfo = new WorkTimeInformation(Helper.dummyReasonTimeChange(), time);
			// 打刻, 実打刻
			val stamp = new WorkStamp(workTimeInfo,	Optional.of(new WorkLocationCD("001")));
			
			return new TimeActualStamp(
					stamp
					, stamp
					, 1
					, Helper.dummyOvertimeDeclaration()
					, null);
		}
		
		/**
		 * 勤務Noと休暇時間を指定して日別勤怠の出退勤を作る
		 * @param workNo　勤務No
		 * @param vacation　休憩時間
		 * @return
		 */
		public static TimeLeavingOfDailyAttd createTimeLeavingOfDailyAttd(WorkNo workNo, TimeZone vacation) {
			val startTimeWork = Helper.createTimeStampforVacation(new TimeWithDayAttr(480), vacation);
			val endTimeWork = Helper.createTimeStampforVacation(new TimeWithDayAttr(1020), vacation);
			val timeLeavingWork = new TimeLeavingWork(
					workNo
					, Optional.of(startTimeWork)
					, Optional.of(endTimeWork)
					, true
					, true);
			
			return new TimeLeavingOfDailyAttd(Arrays.asList(timeLeavingWork), new WorkTimes(4));
		}
		
		/**
		 * 時刻と休暇時間を指定して勤怠打刻を作る
		 * @param time　時刻
		 * @param vacation　休暇時間
		 * @return
		 */
		private static TimeActualStamp createTimeStampforVacation(TimeWithDayAttr time, TimeZone vacation) {
			TimeActualStamp timeStamp = Helper.createTimeStamp(time);
			timeStamp.setTimeVacation(Optional.ofNullable(vacation));
			return timeStamp;
		}
		
		private static ReasonTimeChange dummyReasonTimeChange() {
			return new ReasonTimeChange(TimeChangeMeans.AUTOMATIC_SET, EngravingMethod.WEB_STAMP_INPUT);
		}
		
		private static OvertimeDeclaration dummyOvertimeDeclaration() {
			return new OvertimeDeclaration(new AttendanceTime(0), new AttendanceTime(0));
		}
	}
	
}
