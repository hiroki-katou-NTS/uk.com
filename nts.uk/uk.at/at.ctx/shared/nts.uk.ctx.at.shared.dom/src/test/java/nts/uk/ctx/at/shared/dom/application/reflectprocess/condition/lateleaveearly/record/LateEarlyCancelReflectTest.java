package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.lateleaveearly.record;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.groups.Tuple;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ApplicationTypeShare;
import nts.uk.ctx.at.shared.dom.scherec.application.common.PrePostAtrShare;
import nts.uk.ctx.at.shared.dom.scherec.application.lateleaveearly.ArrivedLateLeaveEarlyShare;
import nts.uk.ctx.at.shared.dom.scherec.application.lateleaveearly.LateCancelationShare;
import nts.uk.ctx.at.shared.dom.scherec.application.lateleaveearly.LateOrEarlyAtrShare;
import nts.uk.ctx.at.shared.dom.scherec.application.lateleaveearly.TimeReportShare;
import nts.uk.ctx.at.shared.dom.scherec.application.stamp.AppStampShare;
import nts.uk.ctx.at.shared.dom.scherec.application.stamp.StartEndClassificationShare;
import nts.uk.ctx.at.shared.dom.scherec.application.stamp.TimeStampAppShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.lateearlycancellation.LateEarlyCancelReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.earlyleavetime.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.latetime.LateTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.IntervalExemptionTime;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * @author thanh_nx
 *
 *         取消の反映
 */
@RunWith(JMockit.class)
public class LateEarlyCancelReflectTest {

	/*
	 * テストしたい内容
	 * 
	 * 遅刻早退取消申請の反映
	 * 
	 * →①「遅刻早退報告を行った場合はアラームとしない」＝ true ： 時刻報告の反映
	 * 
	 * →② 遅刻早退報告を行った場合はアラームとしない」＝ false ： 時刻報告を反映しない
	 * 
	 * 準備するデータ
	 * 
	 * → 「遅刻早退報告を行った場合はアラームとしない」の設定
	 * 
	 */
	@Test
	public void testReflect() {
		ArrivedLateLeaveEarlyShare appWorkChange = appWorkChange(1, LateOrEarlyAtrShare.LATE);
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD, 1);// no = 1
		// ①「遅刻早退報告を行った場合はアラームとしない」＝ true ： 時刻報告の反映
		LateEarlyCancelReflect reflectApp = new LateEarlyCancelReflect("", true);
		reflectApp.reflect(appWorkChange, dailyApp);
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getLateTimeOfDaily())
						.extracting(x -> x.getWorkNo().v(), x -> x.isDoNotSetAlarm()).contains(Tuple.tuple(1, true));

		// 「② 遅刻早退報告を行った場合はアラームとしない」＝ false ： 時刻報告を反映しない
		dailyApp = ReflectApplicationHelper.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD, 1);// no = 1
		reflectApp = new LateEarlyCancelReflect("", false);
		reflectApp.reflect(appWorkChange, dailyApp);
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getLateTimeOfDaily())
						.extracting(x -> x.getWorkNo().v(), x -> x.isDoNotSetAlarm()).contains(Tuple.tuple(1, false));

	}

	/*
	 * テストしたい内容
	 * 
	 * →遅刻時間をクリア
	 * 
	 * →出退勤の遅刻取消
	 * 
	 * 準備するデータ
	 * 
	 * → 取消.区分 = 遅刻
	 * 
	 */
	@Test
	public void testArivedLate() {

		ArrivedLateLeaveEarlyShare appWorkChange = appWorkChange(1, LateOrEarlyAtrShare.LATE);
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD, 1);// no = 1
		LateEarlyCancelReflect reflectApp = new LateEarlyCancelReflect("", false);

		List<Integer> actualResult = reflectApp.reflectCancel(appWorkChange, dailyApp).getLstItemId();

		assertThat(actualResult).isEqualTo(Arrays.asList(592, 861));

		// すべての値が０
		// 遅刻時間をクリア
		assertAriveLateEmpty(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getLateTimeOfDaily().get(0));
		assertThat(dailyApp.getAttendanceLeave().get().getTimeLeavingWorks().get(0).isCanceledLate()).isTrue();
		assertThat(dailyApp.getAttendanceLeave().get().getTimeLeavingWorks().get(0).isCanceledEarlyLeave()).isFalse();//「早退を取り消した」が固定
	}

	/*
	 * テストしたい内容
	 * 
	 * →早退時間をクリア
	 * 
	 * →出退勤の早退取消
	 * 
	 * 準備するデータ
	 * 
	 * → 取消.区分 = 早退
	 * 
	 */
	@Test
	public void testLeaveEarly() {

		ArrivedLateLeaveEarlyShare appWorkChange = appWorkChange(1, LateOrEarlyAtrShare.EARLY);
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD, 1);// no = 1
		LateEarlyCancelReflect reflectApp = new LateEarlyCancelReflect("", false);

		List<Integer> actualResult = reflectApp.reflectCancel(appWorkChange, dailyApp).getLstItemId();

		assertThat(actualResult).isEqualTo(Arrays.asList(604, 863));

		// すべての値が０
		// 早退時間をクリア
		assertLeavEarlyEmpty(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getLeaveEarlyTimeNo(1).get());

		// 出退勤の遅刻取消
		assertThat(dailyApp.getAttendanceLeave().get().getTimeLeavingWorks().get(0).isCanceledEarlyLeave()).isTrue();
		
		assertThat(dailyApp.getAttendanceLeave().get().getTimeLeavingWorks().get(0).isCanceledLate()).isFalse();//「遅刻を取り消した」が固定
	}

	/*
	 * テストしたい内容
	 * 
	 * 遅刻早退取消申請の反映
	 * 
	 * →日別勤怠の遅刻時間に勤務NOを保存するとき時刻報告を反映するだけ
	 * 
	 * →日別勤怠の早退時間は値を変わりません
	 * 
	 * 準備するデータ
	 * 
	 * → 「遅刻早退取消申請.取消.区分」が遅刻
	 * 
	 */
	@Test
	public void testReportLate() {
		ArrivedLateLeaveEarlyShare appWorkChange = appWorkChange(LateOrEarlyAtrShare.LATE,
				Arrays.asList(new TimeReportShare(1, LateOrEarlyAtrShare.LATE, new TimeWithDayAttr(888)), // 遅刻早退時刻報告
						new TimeReportShare(2, LateOrEarlyAtrShare.LATE, new TimeWithDayAttr(999))));

		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD, 1);// no = 1
		LateEarlyCancelReflect reflectApp = new LateEarlyCancelReflect("", true);
		reflectApp.reflectTimeReport(appWorkChange, dailyApp);
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getLateTimeOfDaily())
						.extracting(x -> x.getWorkNo().v(), x -> x.isDoNotSetAlarm()).contains(Tuple.tuple(1, true));
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getLeaveEarlyTimeOfDaily())
						.extracting(x -> x.getWorkNo().v(), x -> x.isDoNotSetAlarm()).contains(Tuple.tuple(1, false));
	}

	/*
	 * テストしたい内容
	 * 
	 * 遅刻早退取消申請の反映
	 * 
	 * →日別勤怠の早退時間に勤務NOを保存するとき時刻報告を反映するだけ
	 * 
	 * →日別勤怠の遅刻時間は値を変わりません
	 * 
	 * 準備するデータ
	 * 
	 * → 「遅刻早退取消申請.取消.区分」が早退
	 * 
	 */
	@Test
	public void testReportEarly() {
		ArrivedLateLeaveEarlyShare appWorkChange = appWorkChange(LateOrEarlyAtrShare.EARLY,
				Arrays.asList(new TimeReportShare(1, LateOrEarlyAtrShare.EARLY, new TimeWithDayAttr(888)), // 遅刻早退時刻報告
						new TimeReportShare(2, LateOrEarlyAtrShare.EARLY, new TimeWithDayAttr(999))));

		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD, 1);// no = 1
		LateEarlyCancelReflect reflectApp = new LateEarlyCancelReflect("", true);
		reflectApp.reflectTimeReport(appWorkChange, dailyApp);
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getLeaveEarlyTimeOfDaily())
						.extracting(x -> x.getWorkNo().v(), x -> x.isDoNotSetAlarm()).contains(Tuple.tuple(1, true));
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getLateTimeOfDaily())
						.extracting(x -> x.getWorkNo().v(), x -> x.isDoNotSetAlarm()).contains(Tuple.tuple(1, false));
	}

	private void assertAriveLateEmpty(LateTimeOfDaily lateDaily) {
		// 遅刻時間
		timeWithCalculation(lateDaily.getLateTime());

		// 遅刻控除時間
		timeWithCalculation(lateDaily.getLateDeductionTime());

		// 休暇使用時間
		timePaidUseTime(lateDaily.getTimePaidUseTime());

		// インターバル時間
		exemptionTime(lateDaily.getExemptionTime());

	}

	private void assertLeavEarlyEmpty(LeaveEarlyTimeOfDaily leavEarly) {
		// 早退時間
		timeWithCalculation(leavEarly.getLeaveEarlyTime());

		// 早退控除時間
		timeWithCalculation(leavEarly.getLeaveEarlyDeductionTime());

		// 休暇使用時間
		timePaidUseTime(leavEarly.getTimePaidUseTime());

		// インターバル時間
		exemptionTime(leavEarly.getIntervalTime());
	}

	private void timeWithCalculation(TimeWithCalculation time) {
		assertThat(time.getCalcTime().v()).isEqualTo(0);
		assertThat(time.getTime().v()).isEqualTo(0);
	}

	private void timePaidUseTime(TimevacationUseTimeOfDaily time) {
		assertThat(time.getSixtyHourExcessHolidayUseTime().v()).isEqualTo(0);
		assertThat(time.getTimeAnnualLeaveUseTime().v()).isEqualTo(0);
		assertThat(time.getTimeCompensatoryLeaveUseTime().v()).isEqualTo(0);
		assertThat(time.getTimeSpecialHolidayUseTime().v()).isEqualTo(0);
	}

	private void exemptionTime(IntervalExemptionTime time) {
		assertThat(time.getExemptionTime().v()).isEqualTo(0);
	}

	private ArrivedLateLeaveEarlyShare appWorkChange(int workNo, LateOrEarlyAtrShare atr) {
		List<TimeReportShare> lateOrLeaveEarlies = new ArrayList<>();
		lateOrLeaveEarlies.add(new TimeReportShare(workNo, atr, new TimeWithDayAttr(999)));
		return appWorkChange(atr, lateOrLeaveEarlies);
	}

	private ArrivedLateLeaveEarlyShare appWorkChange(LateOrEarlyAtrShare atr,
			List<TimeReportShare> lateOrLeaveEarlies) {

		List<TimeStampAppShare> lstStampAppShare = ReflectApplicationHelper.createlstTimeStamp(
				StartEndClassificationShare.START, // 開始終了区分
				666, // 時刻
				1, // 応援勤務枠No
				"lo");// 勤務場所

		AppStampShare application = new AppStampShare(lstStampAppShare, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), ReflectApplicationHelper.createAppShare(ApplicationTypeShare.STAMP_APPLICATION,
						PrePostAtrShare.PREDICT));

		List<LateCancelationShare> lateCancelation = new ArrayList<>();
		lateCancelation.add(new LateCancelationShare(1, atr));
		return new ArrivedLateLeaveEarlyShare(lateCancelation, lateOrLeaveEarlies, application);
	}

}
