package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.assertj.core.groups.Tuple;
import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;
import nts.uk.shr.com.time.TimeWithDayAttr;

@RunWith(JMockit.class)
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
		val vacations  = new TimeZone(new TimeWithDayAttr(1200), new TimeWithDayAttr(1300));
		val startTimeWork = Helper.createStartTimeWork(vacations);
		val endTimeWork = Helper.createEndTimeWork(vacations);
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
		val vacations  = new TimeZone(new TimeWithDayAttr(1100), new TimeWithDayAttr(1200));
		val startTimeWork = Helper.createStartTimeWork(vacations);
		val endTimeWork = Helper.createEndTimeWork(vacations);
		val timeLeavingWork = new TimeLeavingWork( new WorkNo(1), Optional.of(startTimeWork), Optional.of(endTimeWork), true, true);
		val timeLeavingDaily = new TimeLeavingOfDailyAttd(Arrays.asList(timeLeavingWork), new WorkTimes(4));
		
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
		TimeZone vacations = null;
		val startTimeWork = Helper.createStartTimeWork(vacations);
		val endTimeWork = Helper.createEndTimeWork(vacations);
		val timeLeavingWork = new TimeLeavingWork( new WorkNo(1), Optional.of(startTimeWork), Optional.of(endTimeWork), true, true);
		val timeLeavingDaily = new TimeLeavingOfDailyAttd(Arrays.asList(timeLeavingWork), new WorkTimes(4));
		
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
        val vacations  = new TimeZone(new TimeWithDayAttr(1200), new TimeWithDayAttr(1300));
		val startTimeWork = Helper.createStartTimeWork(vacations);
		val endTimeWork = Helper.createEndTimeWork(vacations);
		val timeLeavingWork = new TimeLeavingWork( new WorkNo(1), Optional.of(startTimeWork), Optional.of(endTimeWork), true, true);
		val timeLeavingDaily = new TimeLeavingOfDailyAttd(Arrays.asList(timeLeavingWork), new WorkTimes(1));
		val actual = timeLeavingDaily.getStartTimeVacations(new WorkNo(1));
		
		assertThat(actual.get().getStart()).isEqualTo(vacations.getStart());
		assertThat(actual.get().getEnd()).isEqualTo(vacations.getEnd());
		
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
        val vacations  = new TimeZone(new TimeWithDayAttr(1200), new TimeWithDayAttr(1300));
		val startTimeWork = Helper.createStartTimeWork(vacations);
		val endTimeWork = Helper.createEndTimeWork(vacations);
		val timeLeavingWork = new TimeLeavingWork( new WorkNo(1), Optional.of(startTimeWork), Optional.of(endTimeWork), true, true);
		val timeLeavingDaily = new TimeLeavingOfDailyAttd(Arrays.asList(timeLeavingWork), new WorkTimes(1));
		
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
		TimeZone vacations = null;
		val startTimeWork = Helper.createStartTimeWork(vacations);
		val endTimeWork = Helper.createEndTimeWork(vacations);
		val timeLeavingWork = new TimeLeavingWork( new WorkNo(1), Optional.of(startTimeWork), Optional.of(endTimeWork), true, true);
		val timeLeavingDaily = new TimeLeavingOfDailyAttd(Arrays.asList(timeLeavingWork), new WorkTimes(1));
		
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
        val vacations  = new TimeZone(new TimeWithDayAttr(1200), new TimeWithDayAttr(1300));
		val startTimeWork = Helper.createStartTimeWork(vacations);
		val endTimeWork = Helper.createEndTimeWork(vacations);
		val timeLeavingWork = new TimeLeavingWork( new WorkNo(1), Optional.of(startTimeWork), Optional.of(endTimeWork), true, true);
		val timeLeavingDaily = new TimeLeavingOfDailyAttd(Arrays.asList(timeLeavingWork), new WorkTimes(1));
		
		val actual = timeLeavingDaily.getEndTimeVacations(new WorkNo(1));
		assertThat(actual.get().getStart()).isEqualTo(vacations.getStart());
		assertThat(actual.get().getEnd()).isEqualTo(vacations.getEnd());
	}
	
	
	static class Helper {
		
		public static TimeLeavingWork createTimeLeavingWork(WorkNo workNo) {
			val vacations  = new TimeZone(new TimeWithDayAttr(1100), new TimeWithDayAttr(1200));
			return new TimeLeavingWork(
					    new WorkNo(1), Optional.of(createStartTimeWork(vacations))
					  , Optional.of(createEndTimeWork(vacations)), true, true);
			
		}
		
		/**
		 * 出退勤の出勤を作る
		 * @param vacations
		 * @return
		 */
		public static TimeActualStamp createStartTimeWork(TimeZone vacations) {
			//val vacations  = new TimeZone(new TimeWithDayAttr(1100), new TimeWithDayAttr(1200));
			//実打刻
			val actStamp_check_in = new WorkStamp(new TimeWithDayAttr(15), new TimeWithDayAttr(830),
					new WorkLocationCD("001"), TimeChangeMeans.REAL_STAMP);
			//打刻
			val stamp_check_in = new WorkStamp(new TimeWithDayAttr(15), new TimeWithDayAttr(830),
					new WorkLocationCD("001"), TimeChangeMeans.REAL_STAMP);
			
			return new TimeActualStamp( actStamp_check_in, stamp_check_in, 1
                    , new OvertimeDeclaration(new AttendanceTime(100), new AttendanceTime(0))
                    , vacations);
			
		} 
		
		/**
		 * 出退勤の退勤を作る
		 * @param vacations
		 * @return
		 */
		public static TimeActualStamp createEndTimeWork(TimeZone vacations) {
			//実打刻
			val actStamp_check_out = new WorkStamp(  new TimeWithDayAttr(15)
					                               , new TimeWithDayAttr(15)
					                               , new WorkLocationCD("001")
					                               , TimeChangeMeans.REAL_STAMP);
			//打刻
			val stamp_check_out = new WorkStamp( new TimeWithDayAttr(1730)
					                           , new TimeWithDayAttr(1730)
					                           , new WorkLocationCD("001")
					                           , TimeChangeMeans.REAL_STAMP);

			return new TimeActualStamp( actStamp_check_out, stamp_check_out, 1
                    , new OvertimeDeclaration(new AttendanceTime(100), new AttendanceTime(0))
                    , vacations);
		} 

	}
	
}
