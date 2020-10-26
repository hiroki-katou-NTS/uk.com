package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.assertj.core.groups.Tuple;
import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
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
		val timeLeavingWork = Helper.createTimeLeavingWork();
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
		val stamp = new WorkStamp(new TimeWithDayAttr(1050),new TimeWithDayAttr(1050),new WorkLocationCD(null), null, null);
		val actualStamp = new WorkStamp(new TimeWithDayAttr(510),new TimeWithDayAttr(510),new WorkLocationCD("workLocationCS"), null, null);
		val timeLeavingWork = new TimeLeavingWork(
				  new WorkNo(1)
				, Optional.of(new TimeActualStamp(actualStamp, stamp, 1))
				, Optional.of(new TimeActualStamp(actualStamp, stamp, 1))
				, true, true
				);
		
		val timeLeavingDaily = new TimeLeavingOfDailyAttd(Arrays.asList(timeLeavingWork), new WorkTimes(4));
		val actual = timeLeavingDaily.getTimeOfTimeLeavingAtt();
		
		assertThat(actual).containsOnly(new TimeSpanForCalc(  stamp.getTimeDay().getTimeWithDay().get()
				                                            , stamp.getAfterRoundingTime()));
	}
	
	/**
	 * 勤務開始の休暇時間帯を取得する
	 * 日別勤怠の出退勤の出退勤中にパラメータ勤務NOがない。
	 * 勤務開始の休暇時間帯を取得する = empty
	 */
	@Test
	public void getStartTimeVacations_not_existed_work_no() {
		val stamp = new WorkStamp(new TimeWithDayAttr(1050),new TimeWithDayAttr(1050),new WorkLocationCD(null), null, null);
		val actualStamp = new WorkStamp(new TimeWithDayAttr(510),new TimeWithDayAttr(510),new WorkLocationCD("workLocationCS"), null, null);
		val timeLeavingWork = new TimeLeavingWork(
				  new WorkNo(1)
				, Optional.of(new TimeActualStamp(actualStamp, stamp, 1))
				, Optional.of(new TimeActualStamp(actualStamp, stamp, 1))
				, true, true
				);
		
		val timeLeavingDaily = new TimeLeavingOfDailyAttd(Arrays.asList(timeLeavingWork), new WorkTimes(4));
		Optional<TimeSpanForCalc> actual = timeLeavingDaily.getStartTimeVacations(new WorkNo(3));
		assertThat(actual).isEmpty();
	}
	
	/**
	 * 
	 * 勤務開始の休暇時間帯を取得する
	 * 日別勤怠の出退勤の出退勤中にパラメータ勤務NOがある、でも時間休暇時間帯 empty
	 * 勤務開始の休暇時間帯を取得する =  empty
	 */
	@Test
	public void getStartTimeVacations_vacation_is_empty() {
		val actualStamp = new WorkStamp(new TimeWithDayAttr(510),new TimeWithDayAttr(510),new WorkLocationCD("workLocationCS"), null, null);
		val stamp = new WorkStamp(new TimeWithDayAttr(1050),new TimeWithDayAttr(1050),new WorkLocationCD(null), null, null);
		val timeLeavingWork = new TimeLeavingWork(
				  new WorkNo(1)
				, Optional.of(new TimeActualStamp(actualStamp, stamp, 1, null, null))
				, Optional.of(new TimeActualStamp(actualStamp, stamp, 1, null, null))
				, true, true
				);
		
		val timeLeavingDaily = new TimeLeavingOfDailyAttd(Arrays.asList(timeLeavingWork), new WorkTimes(4));
		Optional<TimeSpanForCalc> actual = timeLeavingDaily.getStartTimeVacations(new WorkNo(1));
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
		val actualStamp = new WorkStamp(new TimeWithDayAttr(510),new TimeWithDayAttr(510),new WorkLocationCD("workLocationCS"), null, null);
		val stamp = new WorkStamp(new TimeWithDayAttr(1050),new TimeWithDayAttr(1050),new WorkLocationCD(null), null, null);
		val vacations  = new TimeZone(new TimeWithDayAttr(510), new TimeWithDayAttr(510));
		val timeLeavingWork = new TimeLeavingWork(
				  new WorkNo(1)
				, Optional.of(new TimeActualStamp(actualStamp, stamp, 1, null, vacations))
				, Optional.of(new TimeActualStamp(actualStamp, stamp, 1, null, vacations))
				, true, true
				);
		
		val timeLeavingDaily = new TimeLeavingOfDailyAttd(Arrays.asList(timeLeavingWork), new WorkTimes(4));
		val actual = timeLeavingDaily.getStartTimeVacations(new WorkNo(1));
		
		assertThat(actual.get()).isEqualTo(new TimeSpanForCalc(vacations.getStart(), vacations.getEnd()));
	}
	
	/**
	 * 勤務終了の休暇時間帯を取得する
	 * 日別勤怠の出退勤の出退勤中にパラメータ勤務NOがない。
	 * 勤務終了の休暇時間帯を取得する = empty
	 */
	@Test
	public void getEndTimeVacations_not_existed_work_no() {
		val stamp = new WorkStamp(new TimeWithDayAttr(1050),new TimeWithDayAttr(1050),new WorkLocationCD(null), null, null);
		val actualStamp = new WorkStamp(new TimeWithDayAttr(510),new TimeWithDayAttr(510),new WorkLocationCD("workLocationCS"), null, null);
		val vacations  = new TimeZone(new TimeWithDayAttr(510), new TimeWithDayAttr(510));
		val timeLeavingWork = new TimeLeavingWork(
				  new WorkNo(1)
				, Optional.of(new TimeActualStamp(Optional.of(actualStamp), Optional.of(stamp), 1, Optional.empty(), Optional.of(vacations)))
				, Optional.of(new TimeActualStamp(actualStamp, stamp, 1))
				, true, true
				);
		
		val timeLeavingDaily = new TimeLeavingOfDailyAttd(Arrays.asList(timeLeavingWork), new WorkTimes(4));
		val actual = timeLeavingDaily.getEndTimeVacations(new WorkNo(3));
		assertThat(actual).isEmpty();
	}
	
	/**
	 * 
	 * 勤務終了の休暇時間帯を取得する
	 * 日別勤怠の出退勤の出退勤中にパラメータ勤務NOがある、でも時間休暇時間帯 empty
	 * 勤務終了の休暇時間帯を取得する empty
	 * 
	 */
	@Test
	public void getEndTimeVacations_vacation_empty() {
		val stamp = new WorkStamp(new TimeWithDayAttr(1050),new TimeWithDayAttr(1050),new WorkLocationCD(null), null, null);
		val actualStamp = new WorkStamp(new TimeWithDayAttr(510),new TimeWithDayAttr(510),new WorkLocationCD("workLocationCS"), null, null);
		val timeLeavingWork = new TimeLeavingWork(
				  new WorkNo(1)
				, Optional.of(new TimeActualStamp(actualStamp, stamp, 1, null, null))
				, Optional.of(new TimeActualStamp(actualStamp, stamp, 1, null, null))
				, true, true
				);
		
		val timeLeavingDaily = new TimeLeavingOfDailyAttd(Arrays.asList(timeLeavingWork), new WorkTimes(4));
		Optional<TimeSpanForCalc> actual = timeLeavingDaily.getEndTimeVacations(new WorkNo(1));
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
		val stamp = new WorkStamp(new TimeWithDayAttr(1050),new TimeWithDayAttr(1050),new WorkLocationCD(null), null, null);
		val actualStamp = new WorkStamp(new TimeWithDayAttr(510),new TimeWithDayAttr(510),new WorkLocationCD("workLocationCS"), null, null);
		val vacations  = new TimeZone(new TimeWithDayAttr(510), new TimeWithDayAttr(510));
		val timeLeavingWork = new TimeLeavingWork(
				  new WorkNo(1)
				, Optional.of(new TimeActualStamp(actualStamp, stamp, 1, null, null))
				, Optional.of(new TimeActualStamp(actualStamp, stamp, 1, null, vacations))
				, true, true
				);
		
		val timeLeavingDaily = new TimeLeavingOfDailyAttd(Arrays.asList(timeLeavingWork), new WorkTimes(4));
		val actual = timeLeavingDaily.getEndTimeVacations(new WorkNo(1));
		assertThat(actual.get()).isEqualTo(new TimeSpanForCalc(vacations.getStart(), vacations.getEnd()));
	}
	
	
	static class Helper {

		public static TimeLeavingWork createTimeLeavingWork() {
			WorkStamp actualStamp = new WorkStamp(new TimeWithDayAttr(510),new TimeWithDayAttr(510),new WorkLocationCD("workLocationCS"), null, null);
			WorkStamp stamp = new WorkStamp(new TimeWithDayAttr(1050),new TimeWithDayAttr(1050),new WorkLocationCD(null), null, null);
			
			val timeLeaving = new TimeLeavingWork(
					  new WorkNo(1)
					, Optional.of(new TimeActualStamp(actualStamp, stamp, 1))
					, Optional.of(new TimeActualStamp(actualStamp, stamp, 1))
					, true, true
					);
			
			return timeLeaving;
		} 

	}
	
}
