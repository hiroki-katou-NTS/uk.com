package nts.uk.ctx.at.shared.dom.scherec.application.reflectprocess.condition.lateleaveearly.record;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.groups.Tuple;
import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ApplicationTypeShare;
import nts.uk.ctx.at.shared.dom.scherec.application.common.PrePostAtrShare;
import nts.uk.ctx.at.shared.dom.scherec.application.lateleaveearly.ArrivedLateLeaveEarlyShare;
import nts.uk.ctx.at.shared.dom.scherec.application.lateleaveearly.LateCancelationShare;
import nts.uk.ctx.at.shared.dom.scherec.application.lateleaveearly.LateOrEarlyAtrShare;
import nts.uk.ctx.at.shared.dom.scherec.application.lateleaveearly.TimeReportShare;
import nts.uk.ctx.at.shared.dom.scherec.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.scherec.application.stamp.AppStampShare;
import nts.uk.ctx.at.shared.dom.scherec.application.stamp.StartEndClassificationShare;
import nts.uk.ctx.at.shared.dom.scherec.application.stamp.TimeStampAppShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.lateearlycancellation.LateEarlyCancelReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.DailyAfterAppReflectResult;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.earlyleavetime.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.latetime.LateTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.IntervalExemptionTime;
import nts.uk.shr.com.time.TimeWithDayAttr;

@RunWith(JMockit.class)
public class LateEarlyCancelReflectTest {

	/*
	 * テストしたい内容
	 * 
	 * 取消の反映
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
		val dailyAppBefore = ReflectApplicationHelper
				.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD, 1).getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getLateTimeOfDaily().get(0);
		LateEarlyCancelReflect reflectApp = new LateEarlyCancelReflect("", false);

		DailyAfterAppReflectResult actualResult = reflectApp.reflectCancel(appWorkChange, dailyApp);

		assertThat(actualResult.getLstItemId()).isEqualTo(Arrays.asList(592, 861));

		// すべての値が０
		// 遅刻時間をクリア
		assertAriveLateEmpty(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getLateTimeOfDaily().get(0), dailyAppBefore.getLateTime().getCalcTime().v());
		// 遅刻控除時間
		timeWithCalculationDeduct(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getLateTimeOfDaily().get(0).getLateDeductionTime(), dailyAppBefore.getLateDeductionTime());
		assertThat(dailyApp.getAttendanceLeave().get().getTimeLeavingWorks().get(0).isCanceledLate()).isTrue();
	}

	/*
	 * テストしたい内容
	 * 
	 * 取消の反映
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
		val dailyAppBefore = ReflectApplicationHelper.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD, 1)
				.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
				.getLeaveEarlyTimeNo(1).get();
		
		LateEarlyCancelReflect reflectApp = new LateEarlyCancelReflect("", false);

		DailyAfterAppReflectResult actualResult = reflectApp.reflectCancel(appWorkChange, dailyApp);

		assertThat(actualResult.getLstItemId()).isEqualTo(Arrays.asList(604, 863));

		// すべての値が０
		// 早退時間をクリア
		assertLeavEarlyEmpty(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getLeaveEarlyTimeNo(1).get(), dailyAppBefore.getLeaveEarlyTime().getCalcTime().v());

		timeWithCalculationDeduct(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getLeaveEarlyTimeNo(1).get().getLeaveEarlyDeductionTime(), dailyAppBefore.getLeaveEarlyDeductionTime());
		// 出退勤の遅刻取消
		assertThat(dailyApp.getAttendanceLeave().get().getTimeLeavingWorks().get(0).isCanceledEarlyLeave()).isTrue();
	}

	// ****************************************************************************************//
	// *******遅刻早退取消申請の反映*******//
	/*
	 * テストしたい内容
	 * 
	 * 時刻報告を反映しない
	 * 
	 * 準備するデータ
	 * 
	 * → [遅刻早退報告を行った場合はアラームとしない]をチェック = false
	 * 
	 */
	@Test
	public void testNotClearAlarm() {

		ArrivedLateLeaveEarlyShare appWorkChange = appWorkChange(1, LateOrEarlyAtrShare.EARLY);
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD, 1);// no = 1
		LateEarlyCancelReflect reflectApp = new LateEarlyCancelReflect("", false);

		DailyAfterAppReflectResult actualResult = reflectApp.reflect(appWorkChange, dailyApp);

		assertThat(actualResult.getDomainDaily().getAttendanceTimeOfDailyPerformance().get()
				.getActualWorkingTimeOfDaily().getTotalWorkingTime().getLateTimeOfDaily().get(0).isDoNotSetAlarm())
						.isFalse();
		assertThat(
				actualResult.getDomainDaily().getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
						.getTotalWorkingTime().getLeaveEarlyTimeOfDaily().get(0).isDoNotSetAlarm()).isFalse();
	}

	/*
	 * テストしたい内容
	 * 
	 * 時刻報告を反映する
	 * 
	 * 準備するデータ
	 * 
	 * → [遅刻早退報告を行った場合はアラームとしない]をチェック = true
	 * 
	 */
	@Test
	public void testClearAlarm() {

		ArrivedLateLeaveEarlyShare appWorkChange = appWorkChange(1, LateOrEarlyAtrShare.EARLY);
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD, 1);// no = 1
		LateEarlyCancelReflect reflectApp = new LateEarlyCancelReflect("", true);

		DailyAfterAppReflectResult actualResult = reflectApp.reflect(appWorkChange, dailyApp);

		assertThat(actualResult.getDomainDaily().getAttendanceTimeOfDailyPerformance().get()
				.getActualWorkingTimeOfDaily().getTotalWorkingTime().getLateTimeOfDaily().get(0).isDoNotSetAlarm())
						.isFalse();
		assertThat(
				actualResult.getDomainDaily().getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
						.getTotalWorkingTime().getLeaveEarlyTimeOfDaily().get(0).isDoNotSetAlarm()).isTrue();
	}

	// ****************************************************************************************//
	// *******時刻報告の反映*******//
	/*
	 * テストしたい内容
	 * 
	 * 時刻報告を反映
	 * 
	 * →　①[時刻報告.区分]＝遅刻場合：Noと日別実績の遅刻時間の時刻報告を反映する
	 * 
	 * →　②[時刻報告.区分]＝早退場合：Noと日別実績の早退時間の時刻報告を反映する
	 * 
	 * 準備するデータ
	 * 
	 * 
	 */
	//日別実績の遅刻時間
	
	//日別実績の早退時間
	@Test
	public void testReport() {
		ArrivedLateLeaveEarlyShare appWorkChange = addNoReport(Arrays.asList(1, 2), LateOrEarlyAtrShare.LATE);//no 1, 2
		
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD, 1);// no = 1
		LateEarlyCancelReflect reflectApp = new LateEarlyCancelReflect("", true);

		DailyAfterAppReflectResult actualResult = reflectApp.reflect(appWorkChange, dailyApp);

		//①[時刻報告.区分]＝遅刻場合：Noと日別実績の遅刻時間の時刻報告を反映する
		assertThat(actualResult.getDomainDaily().getAttendanceTimeOfDailyPerformance().get()
				.getActualWorkingTimeOfDaily().getTotalWorkingTime().getLateTimeOfDaily())
						.extracting(x -> x.getWorkNo().v(), x -> x.isDoNotSetAlarm())
						.containsExactly(Tuple.tuple(1, true));

		
		//②[時刻報告.区分]＝早退場合：Noと日別実績の早退時間の時刻報告を反映する
		appWorkChange = addNoReport(Arrays.asList(1, 2), LateOrEarlyAtrShare.EARLY);// no 1, 2
		dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD, 1);// no = 1
		 reflectApp = new LateEarlyCancelReflect("", true);
		actualResult = reflectApp.reflect(appWorkChange, dailyApp);
		assertThat(actualResult.getDomainDaily().getAttendanceTimeOfDailyPerformance().get()
				.getActualWorkingTimeOfDaily().getTotalWorkingTime().getLeaveEarlyTimeOfDaily())
						.extracting(x -> x.getWorkNo().v(), x -> x.isDoNotSetAlarm())
						.containsExactly(Tuple.tuple(1, true));
	}

	private void assertAriveLateEmpty(LateTimeOfDaily lateDaily, int timeBefore) {
		// 遅刻時間
		timeWithCalculation(lateDaily.getLateTime(), timeBefore);

		// 休暇使用時間
		timePaidUseTime(lateDaily.getTimePaidUseTime());

		// インターバル時間
		exemptionTime(lateDaily.getExemptionTime());

	}

	private void assertLeavEarlyEmpty(LeaveEarlyTimeOfDaily leavEarly, int timeBefore) {
		// 早退時間
		timeWithCalculation(leavEarly.getLeaveEarlyTime(), timeBefore);

		// 休暇使用時間
		timePaidUseTime(leavEarly.getTimePaidUseTime());

		// インターバル時間
		exemptionTime(leavEarly.getIntervalTime());
	}

	private void timeWithCalculation(TimeWithCalculation time, int timeBefore) {
		assertThat(time.getCalcTime().v()).isEqualTo(timeBefore);
		assertThat(time.getTime().v()).isEqualTo(0);
	}
	
	private void timeWithCalculationDeduct(TimeWithCalculation time, TimeWithCalculation timeBefore) {
		assertThat(time.getCalcTime().v()).isEqualTo(timeBefore.getCalcTime().v());
		assertThat(time.getTime().v()).isEqualTo(timeBefore.getTime().v());
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

		List<TimeStampAppShare> lstStampAppShare = ReflectApplicationHelper.createlstTimeStamp(
				StartEndClassificationShare.START, // 開始終了区分
				666, // 時刻
				1, // 応援勤務枠No
				"lo");// 勤務場所

		AppStampShare application = new AppStampShare(lstStampAppShare, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), ReflectApplicationHelper.createAppShare(ApplicationTypeShare.STAMP_APPLICATION,
						PrePostAtrShare.PREDICT));

		List<LateCancelationShare> lateCancelation = new ArrayList<>();
		lateCancelation.add(new LateCancelationShare(workNo, atr));
		List<TimeReportShare> lateOrLeaveEarlies = new ArrayList<>();
		lateOrLeaveEarlies.add(new TimeReportShare(workNo, atr, new TimeWithDayAttr(111)));
		return new ArrivedLateLeaveEarlyShare(lateCancelation, lateOrLeaveEarlies, application);
	}

	private ArrivedLateLeaveEarlyShare addNoReport(List<Integer> workNo, LateOrEarlyAtrShare atr) {
		AppStampShare application = new AppStampShare(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), ReflectApplicationHelper.createAppShare(ApplicationTypeShare.STAMP_APPLICATION,
						PrePostAtrShare.PREDICT));
		List<TimeReportShare> lateOrLeaveEarlies = new ArrayList<>();
		workNo.forEach(x -> {
			lateOrLeaveEarlies.add(new TimeReportShare(x, atr, new TimeWithDayAttr(111 + x)));
		});
		return new ArrivedLateLeaveEarlyShare(new ArrayList<>(), lateOrLeaveEarlies, application);
	}
}
