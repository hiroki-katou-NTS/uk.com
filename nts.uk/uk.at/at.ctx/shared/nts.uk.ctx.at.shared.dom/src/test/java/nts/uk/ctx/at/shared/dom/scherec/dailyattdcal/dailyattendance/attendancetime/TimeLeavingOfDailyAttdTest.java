package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.assertj.core.groups.Tuple;
import org.junit.Test;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.WorkInfoAndTimeZone;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.EngravingMethod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.ReasonTimeChange;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class TimeLeavingOfDailyAttdTest {
	
	@Injectable
	TimeLeavingOfDailyAttd.Require require;
	
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
	 * 出退勤時刻と渡された範囲時間の重複していない部分の取得
	 * 1回勤務：範囲時間に出退勤が内包
	 */
	@Test
	public void getNotDuplicateSpan_1work_1(){
		// 出退勤 (8:00～17:00)
		TimeLeavingOfDailyAttd timeLeaving = new TimeLeavingOfDailyAttd(
				new ArrayList<>(Arrays.asList(Helper.createTimeLeavingWork(1, 480, 1020))),
				new WorkTimes(1));
		// 範囲時間 (7:00～18:00)
		TimeSpanForCalc timeSpan = Helper.createTimeSpan(420, 1080);
		// Execute
		List<TimeSpanForCalc> result = timeLeaving.getNotDuplicateSpan(timeSpan);
		// assertion
		assertThat(result.size()).isEqualTo(2);
		assertThat(result.get(0)).isEqualTo(Helper.createTimeSpan(420, 480));
		assertThat(result.get(1)).isEqualTo(Helper.createTimeSpan(1020, 1080));
	}
	
	/**
	 * 出退勤時刻と渡された範囲時間の重複していない部分の取得
	 * 1回勤務：範囲時間と出退勤が同一
	 */
	@Test
	public void getNotDuplicateSpan_1work_2(){
		// 出退勤 (8:00～17:00)
		TimeLeavingOfDailyAttd timeLeaving = new TimeLeavingOfDailyAttd(
				new ArrayList<>(Arrays.asList(Helper.createTimeLeavingWork(1, 480, 1020))),
				new WorkTimes(1));
		// 範囲時間 (8:00～17:00)
		TimeSpanForCalc timeSpan = Helper.createTimeSpan(480, 1020);
		// Execute
		List<TimeSpanForCalc> result = timeLeaving.getNotDuplicateSpan(timeSpan);
		// assertion
		assertThat(result).isEmpty();
	}
	
	/**
	 * 出退勤時刻と渡された範囲時間の重複していない部分の取得
	 * 1回勤務：範囲時間が出退勤に内包
	 */
	@Test
	public void getNotDuplicateSpan_1work_3(){
		// 出退勤 (8:00～17:00)
		TimeLeavingOfDailyAttd timeLeaving = new TimeLeavingOfDailyAttd(
				new ArrayList<>(Arrays.asList(Helper.createTimeLeavingWork(1, 480, 1020))),
				new WorkTimes(1));
		// 範囲時間 (9:00～16:00)
		TimeSpanForCalc timeSpan = Helper.createTimeSpan(540, 960);
		// Execute
		List<TimeSpanForCalc> result = timeLeaving.getNotDuplicateSpan(timeSpan);
		// assertion
		assertThat(result).isEmpty();
	}
	
	/**
	 * 出退勤時刻と渡された範囲時間の重複していない部分の取得
	 * 1回勤務：範囲時間に出退勤の開始を含む
	 */
	@Test
	public void getNotDuplicateSpan_1work_4(){
		// 出退勤 (8:00～17:00)
		TimeLeavingOfDailyAttd timeLeaving = new TimeLeavingOfDailyAttd(
				new ArrayList<>(Arrays.asList(Helper.createTimeLeavingWork(1, 480, 1020))),
				new WorkTimes(1));
		// 範囲時間 (7:00～9:00)
		TimeSpanForCalc timeSpan = Helper.createTimeSpan(420, 540);
		// Execute
		List<TimeSpanForCalc> result = timeLeaving.getNotDuplicateSpan(timeSpan);
		// assertion
		assertThat(result.size()).isEqualTo(1);
		assertThat(result.get(0)).isEqualTo(Helper.createTimeSpan(420, 480));
	}
	
	/**
	 * 出退勤時刻と渡された範囲時間の重複していない部分の取得
	 * 1回勤務：範囲時間に出退勤の終了を含む
	 */
	@Test
	public void getNotDuplicateSpan_1work_5(){
		// 出退勤 (8:00～17:00)
		TimeLeavingOfDailyAttd timeLeaving = new TimeLeavingOfDailyAttd(
				new ArrayList<>(Arrays.asList(Helper.createTimeLeavingWork(1, 480, 1020))),
				new WorkTimes(1));
		// 範囲時間 (16:00～18:00)
		TimeSpanForCalc timeSpan = Helper.createTimeSpan(960, 1080);
		// Execute
		List<TimeSpanForCalc> result = timeLeaving.getNotDuplicateSpan(timeSpan);
		// assertion
		assertThat(result.size()).isEqualTo(1);
		assertThat(result.get(0)).isEqualTo(Helper.createTimeSpan(1020, 1080));
	}
	
	/**
	 * 出退勤時刻と渡された範囲時間の重複していない部分の取得
	 * 2回勤務：範囲時間に出退勤が内包
	 */
	@Test
	public void getNotDuplicateSpan_2work_1(){
		// 出退勤 (8:00～12:00,13:00～17:00)
		TimeLeavingOfDailyAttd timeLeaving = new TimeLeavingOfDailyAttd(
				new ArrayList<>(Arrays.asList(
						Helper.createTimeLeavingWork(1, 480, 720),
						Helper.createTimeLeavingWork(2, 780, 1020))),
				new WorkTimes(2));
		// 範囲時間 (7:00～18:00)
		TimeSpanForCalc timeSpan = Helper.createTimeSpan(420, 1080);
		// Execute
		List<TimeSpanForCalc> result = timeLeaving.getNotDuplicateSpan(timeSpan);
		// assertion
		assertThat(result.size()).isEqualTo(3);
		assertThat(result.get(0)).isEqualTo(Helper.createTimeSpan(420, 480));
		assertThat(result.get(1)).isEqualTo(Helper.createTimeSpan(720, 780));
		assertThat(result.get(2)).isEqualTo(Helper.createTimeSpan(1020, 1080));
	}
	
	/**
	 * 出退勤時刻と渡された範囲時間の重複していない部分の取得
	 * 2回勤務：範囲時間が1回目の出退勤と同一
	 */
	@Test
	public void getNotDuplicateSpan_2work_2(){
		// 出退勤 (8:00～12:00,13:00～17:00)
		TimeLeavingOfDailyAttd timeLeaving = new TimeLeavingOfDailyAttd(
				new ArrayList<>(Arrays.asList(
						Helper.createTimeLeavingWork(1, 480, 720),
						Helper.createTimeLeavingWork(2, 780, 1020))),
				new WorkTimes(2));
		// 範囲時間 (8:00～12:00)
		TimeSpanForCalc timeSpan = Helper.createTimeSpan(480, 720);
		// Execute
		List<TimeSpanForCalc> result = timeLeaving.getNotDuplicateSpan(timeSpan);
		// assertion
		assertThat(result).isEmpty();
	}
	
	/**
	 * 出退勤時刻と渡された範囲時間の重複していない部分の取得
	 * 2回勤務：範囲時間が2回目の出退勤と同一
	 */
	@Test
	public void getNotDuplicateSpan_2work_3(){
		// 出退勤 (8:00～12:00,13:00～17:00)
		TimeLeavingOfDailyAttd timeLeaving = new TimeLeavingOfDailyAttd(
				new ArrayList<>(Arrays.asList(
						Helper.createTimeLeavingWork(1, 480, 720),
						Helper.createTimeLeavingWork(2, 780, 1020))),
				new WorkTimes(2));
		// 範囲時間 (8:00～12:00)
		TimeSpanForCalc timeSpan = Helper.createTimeSpan(780, 1020);
		// Execute
		List<TimeSpanForCalc> result = timeLeaving.getNotDuplicateSpan(timeSpan);
		// assertion
		assertThat(result).isEmpty();
	}
	
	/**
	 * 出退勤時刻と渡された範囲時間の重複していない部分の取得
	 * 2回勤務：範囲時間が1回目の出退勤と2回目の出退勤の間にまたがる
	 */
	@Test
	public void getNotDuplicateSpan_2work_4(){
		// 出退勤 (8:00～12:00,13:00～17:00)
		TimeLeavingOfDailyAttd timeLeaving = new TimeLeavingOfDailyAttd(
				new ArrayList<>(Arrays.asList(
						Helper.createTimeLeavingWork(1, 480, 720),
						Helper.createTimeLeavingWork(2, 780, 1020))),
				new WorkTimes(2));
		// 範囲時間 (11:00～14:00)
		TimeSpanForCalc timeSpan = Helper.createTimeSpan(660, 840);
		// Execute
		List<TimeSpanForCalc> result = timeLeaving.getNotDuplicateSpan(timeSpan);
		// assertion
		assertThat(result.size()).isEqualTo(1);
		assertThat(result.get(0)).isEqualTo(Helper.createTimeSpan(720, 780));
	}
	
	/**
	 * 出退勤時刻と渡された範囲時間の重複していない部分の取得
	 * 2回勤務：範囲時間が1回目の出退前～2回目の出勤
	 */
	@Test
	public void getNotDuplicateSpan_2work_5(){
		// 出退勤 (8:00～12:00,13:00～17:00)
		TimeLeavingOfDailyAttd timeLeaving = new TimeLeavingOfDailyAttd(
				new ArrayList<>(Arrays.asList(
						Helper.createTimeLeavingWork(1, 480, 720),
						Helper.createTimeLeavingWork(2, 780, 1020))),
				new WorkTimes(2));
		// 範囲時間 (7:00～13:00)
		TimeSpanForCalc timeSpan = Helper.createTimeSpan(420, 780);
		// Execute
		List<TimeSpanForCalc> result = timeLeaving.getNotDuplicateSpan(timeSpan);
		// assertion
		assertThat(result.size()).isEqualTo(2);
		assertThat(result.get(0)).isEqualTo(Helper.createTimeSpan(420, 480));
		assertThat(result.get(1)).isEqualTo(Helper.createTimeSpan(720, 780));
	}
	
	/**
	 * 出退勤時刻と渡された範囲時間の重複していない部分の取得
	 * 2回勤務：範囲時間が1回目の退勤～2回目の退勤後
	 */
	@Test
	public void getNotDuplicateSpan_2work_6(){
		// 出退勤 (8:00～12:00,13:00～17:00)
		TimeLeavingOfDailyAttd timeLeaving = new TimeLeavingOfDailyAttd(
				new ArrayList<>(Arrays.asList(
						Helper.createTimeLeavingWork(1, 480, 720),
						Helper.createTimeLeavingWork(2, 780, 1020))),
				new WorkTimes(2));
		// 範囲時間 (12:00～18:00)
		TimeSpanForCalc timeSpan = Helper.createTimeSpan(720, 1080);
		// Execute
		List<TimeSpanForCalc> result = timeLeaving.getNotDuplicateSpan(timeSpan);
		// assertion
		assertThat(result.size()).isEqualTo(2);
		assertThat(result.get(0)).isEqualTo(Helper.createTimeSpan(720, 780));
		assertThat(result.get(1)).isEqualTo(Helper.createTimeSpan(1020, 1080));
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
		assertThat(actual).containsOnly(new TimeSpanForCalc(  timeLeavingWork.getAttendanceTime().get()
				                                            , timeLeavingWork.getLeaveTime().get()));
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
		val vacation = new TimeSpanForCalc(new TimeWithDayAttr(1100), new TimeWithDayAttr(1200));
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
		TimeSpanForCalc vacation = null;
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
        val vacation  = new TimeSpanForCalc(new TimeWithDayAttr(1200), new TimeWithDayAttr(1300));
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
        val vacation = new TimeSpanForCalc(new TimeWithDayAttr(1200), new TimeWithDayAttr(1300));
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
		TimeSpanForCalc vacation = null;
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
        val vacation = new TimeSpanForCalc(new TimeWithDayAttr(1200), new TimeWithDayAttr(1300));
        val timeLeavingDaily = Helper.createTimeLeavingOfDailyAttd(new WorkNo(1), vacation);
		
		val actual = timeLeavingDaily.getEndTimeVacations(new WorkNo(1));
		assertThat(actual.get().getStart()).isEqualTo(vacation.getStart());
		assertThat(actual.get().getEnd()).isEqualTo(vacation.getEnd());
	}
	
	@Test
	public void testCreateByPredetermineZone_exception( @Injectable WorkInformation workInformation ) {
		
		// Arrange
		new Expectations() { {
			
			workInformation.getWorkInfoAndTimeZone(require, anyString);
			// result = empty
			
		}};
		
		NtsAssert.systemError( () -> TimeLeavingOfDailyAttd.createByPredetermineZone(require, "cid", workInformation));
	}
	
	@Test
	public void testCreateByPredetermineZone_success( 
			@Injectable WorkInformation workInformation,
			@Injectable WorkType workType,
			@Injectable WorkTimeSetting workTime,
			@Injectable TimeWithDayAttr start1,
			@Injectable TimeWithDayAttr end1,
			@Injectable TimeWithDayAttr start2,
			@Injectable TimeWithDayAttr end2) {
		
		// Arrange
		List<TimeZone> timeZoneList = Arrays.asList( 
				new TimeZone(start1, end1), 
				new TimeZone(start2, end2));
		WorkInfoAndTimeZone workInforAndTimezone = WorkInfoAndTimeZone.create(workType, workTime, timeZoneList);
		
		TimeLeavingWork timeLeavingWork1 = TimeLeavingWork.createFromTimeSpan(new WorkNo(1), new TimeSpanForCalc(start1, end1));
		TimeLeavingWork timeLeavingWork2 = TimeLeavingWork.createFromTimeSpan(new WorkNo(2), new TimeSpanForCalc(start2, end2));
		
		// Expectation
		new Expectations(TimeLeavingWork.class) { {
			
			workInformation.getWorkInfoAndTimeZone(require, anyString);
			result = Optional.of(workInforAndTimezone);
			
			TimeLeavingWork.createFromTimeSpan(new WorkNo(1), (TimeSpanForCalc) any);
			result = timeLeavingWork1;
			
			TimeLeavingWork.createFromTimeSpan(new WorkNo(2), (TimeSpanForCalc) any);
			result = timeLeavingWork2;
		}};
		
		// Action
		TimeLeavingOfDailyAttd target = TimeLeavingOfDailyAttd.createByPredetermineZone(require, "cid", workInformation);
		
		// Assert
		assertThat(target.getWorkTimes().v()).isEqualTo( timeZoneList.size() );
		assertThat(target.getTimeLeavingWorks()).containsExactly( timeLeavingWork1, timeLeavingWork2 );
	}
	
	@Test
	public void testIsIncludeInWorkTimeSpan_notDuplicatedTime1() {
		TimeSpanForCalc target = new TimeSpanForCalc( 
				TimeWithDayAttr.hourMinute(6, 0), 
				TimeWithDayAttr.hourMinute(7, 0));
		
		List<TimeSpanForCalc> timeOfTimeLeavingList = Arrays.asList(
				new TimeSpanForCalc( 
					TimeWithDayAttr.hourMinute(8, 0), 
					TimeWithDayAttr.hourMinute(12, 0)));
		
		TimeLeavingOfDailyAttd timeLeavingOfDailyAttd = new TimeLeavingOfDailyAttd();
		
		new Expectations(timeLeavingOfDailyAttd) {{
			timeLeavingOfDailyAttd.getTimeOfTimeLeavingAtt();
			result = timeOfTimeLeavingList;
		}};
		
		boolean result = timeLeavingOfDailyAttd.isIncludeInWorkTimeSpan(target);
		assertThat( result ).isFalse();
	}
	
	@Test
	public void testIsIncludeInWorkTimeSpan_connotateBeginTime1() {
		TimeSpanForCalc target = new TimeSpanForCalc( 
				TimeWithDayAttr.hourMinute(7, 0), 
				TimeWithDayAttr.hourMinute(9, 0));
		
		List<TimeSpanForCalc> timeOfTimeLeavingList = Arrays.asList(
				new TimeSpanForCalc( 
					TimeWithDayAttr.hourMinute(8, 0), 
					TimeWithDayAttr.hourMinute(12, 0)));
		
		TimeLeavingOfDailyAttd timeLeavingOfDailyAttd = new TimeLeavingOfDailyAttd();
		
		new Expectations(timeLeavingOfDailyAttd) {{
			timeLeavingOfDailyAttd.getTimeOfTimeLeavingAtt();
			result = timeOfTimeLeavingList;
		}};
		
		boolean result = timeLeavingOfDailyAttd.isIncludeInWorkTimeSpan(target);
		assertThat( result ).isFalse();
	}
	
	@Test
	public void testIsIncludeInWorkTimeSpan_connotateEndTime1() {
		TimeSpanForCalc target = new TimeSpanForCalc( 
				TimeWithDayAttr.hourMinute(9, 0), 
				TimeWithDayAttr.hourMinute(12, 30));
		
		List<TimeSpanForCalc> timeOfTimeLeavingList = Arrays.asList(
				new TimeSpanForCalc( 
					TimeWithDayAttr.hourMinute(8, 0), 
					TimeWithDayAttr.hourMinute(12, 0)));
		
		TimeLeavingOfDailyAttd timeLeavingOfDailyAttd = new TimeLeavingOfDailyAttd();
		
		new Expectations(timeLeavingOfDailyAttd) {{
			timeLeavingOfDailyAttd.getTimeOfTimeLeavingAtt();
			result = timeOfTimeLeavingList;
		}};
		
		boolean result = timeLeavingOfDailyAttd.isIncludeInWorkTimeSpan(target);
		assertThat( result ).isFalse();
	}
	
	@Test
	public void testIsIncludeInWorkTimeSpan_includedInWorkTime1() {
		TimeSpanForCalc target = new TimeSpanForCalc( 
				TimeWithDayAttr.hourMinute(8, 0), 
				TimeWithDayAttr.hourMinute(10, 0));
		
		List<TimeSpanForCalc> timeOfTimeLeavingList = Arrays.asList(
				new TimeSpanForCalc( 
					TimeWithDayAttr.hourMinute(8, 0), 
					TimeWithDayAttr.hourMinute(12, 0)),
				new TimeSpanForCalc( 
					TimeWithDayAttr.hourMinute(13, 0), 
					TimeWithDayAttr.hourMinute(17, 0)) );
		
		TimeLeavingOfDailyAttd timeLeavingOfDailyAttd = new TimeLeavingOfDailyAttd();
		
		new Expectations(timeLeavingOfDailyAttd) {{
			timeLeavingOfDailyAttd.getTimeOfTimeLeavingAtt();
			result = timeOfTimeLeavingList;
		}};
		
		boolean result = timeLeavingOfDailyAttd.isIncludeInWorkTimeSpan(target);
		assertThat( result ).isTrue();
		
	}
	
	@Test
	public void testIsIncludeInWorkTimeSpan_includedInWorkTime2() {
		TimeSpanForCalc target = new TimeSpanForCalc( 
				TimeWithDayAttr.hourMinute(14, 0), 
				TimeWithDayAttr.hourMinute(15, 0));
		
		List<TimeSpanForCalc> timeOfTimeLeavingList = Arrays.asList(
				new TimeSpanForCalc( 
					TimeWithDayAttr.hourMinute(8, 0), 
					TimeWithDayAttr.hourMinute(12, 0)),
				new TimeSpanForCalc( 
					TimeWithDayAttr.hourMinute(13, 0), 
					TimeWithDayAttr.hourMinute(17, 0)) );
		
		TimeLeavingOfDailyAttd timeLeavingOfDailyAttd = new TimeLeavingOfDailyAttd();
		
		new Expectations(timeLeavingOfDailyAttd) {{
			timeLeavingOfDailyAttd.getTimeOfTimeLeavingAtt();
			result = timeOfTimeLeavingList;
		}};
		
		boolean result = timeLeavingOfDailyAttd.isIncludeInWorkTimeSpan(target);
		assertThat( result ).isTrue();
		
	}
	
	static class Helper {
		
		public static TimeLeavingWork createTimeLeavingWork(WorkNo workNo) {
			val vacations  = new TimeSpanForCalc(new TimeWithDayAttr(1100), new TimeWithDayAttr(1200));
			return new TimeLeavingWork(
					    new WorkNo(1), Optional.of(Helper.createTimeStampforVacation(new TimeWithDayAttr(480), vacations))
					  , Optional.of(Helper.createTimeStampforVacation(new TimeWithDayAttr(1020), vacations)), true, true);
			
		}

		public static TimeLeavingWork createTimeLeavingWork(int workNo, int start, int end) {
			return new TimeLeavingWork(
					new WorkNo(workNo),
					Optional.of(Helper.createTimeStamp(new TimeWithDayAttr(start))),
					Optional.of(Helper.createTimeStamp(new TimeWithDayAttr(end))),
					false,
					false);
		}
		
		public static TimeSpanForCalc createTimeSpan(int start, int end){
			return new TimeSpanForCalc(new TimeWithDayAttr(start), new TimeWithDayAttr(end));
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
		public static TimeLeavingOfDailyAttd createTimeLeavingOfDailyAttd(WorkNo workNo, TimeSpanForCalc vacation) {
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
		private static TimeActualStamp createTimeStampforVacation(TimeWithDayAttr time, TimeSpanForCalc vacation) {
			TimeActualStamp timeStamp = Helper.createTimeStamp(time);
			timeStamp.setTimeVacation(Optional.ofNullable(vacation));
			return timeStamp;
		}
		
		private static ReasonTimeChange dummyReasonTimeChange() {
			return new ReasonTimeChange(TimeChangeMeans.AUTOMATIC_SET, Optional.of(EngravingMethod.WEB_STAMP_INPUT));
		}
		
		private static OvertimeDeclaration dummyOvertimeDeclaration() {
			return new OvertimeDeclaration(new AttendanceTime(0), new AttendanceTime(0));
		}
	}
	
}
