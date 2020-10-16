package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.lateleaveearly.record;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.application.common.ApplicationTypeShare;
import nts.uk.ctx.at.shared.dom.application.common.PrePostAtrShare;
import nts.uk.ctx.at.shared.dom.application.lateleaveearly.ArrivedLateLeaveEarlyShare;
import nts.uk.ctx.at.shared.dom.application.lateleaveearly.LateCancelationShare;
import nts.uk.ctx.at.shared.dom.application.lateleaveearly.LateOrEarlyAtrShare;
import nts.uk.ctx.at.shared.dom.application.lateleaveearly.TimeReportShare;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.lateleaveearly.ReflectArrivedLateLeaveEarly;
import nts.uk.ctx.at.shared.dom.application.stamp.AppStampShare;
import nts.uk.ctx.at.shared.dom.application.stamp.StartEndClassificationShare;
import nts.uk.ctx.at.shared.dom.application.stamp.TimeStampAppShare;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.earlyleavetime.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.latetime.LateTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.IntervalExemptionTime;

@RunWith(JMockit.class)
public class RCReflectArrivedLateLeaveEarlyAppTest {

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
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeav(ScheduleRecordClassifi.RECORD,
				1, true);// no = 1
		ReflectArrivedLateLeaveEarly reflectApp = new ReflectArrivedLateLeaveEarly("", false);

		List<Integer> actualResult = RCReflectArrivedLateLeaveEarlyApp.reflect(appWorkChange, dailyApp, reflectApp);

		assertThat(actualResult).isEqualTo(Arrays.asList(592, 861));

		// すべての値が０
		// 遅刻時間をクリア
		assertAriveLateEmpty(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getLateTimeOfDaily().get(0));
		assertThat(dailyApp.getAttendanceLeave().get().getTimeLeavingWorks().get(0).isCanceledLate()).isTrue();
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
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeav(ScheduleRecordClassifi.RECORD,
				1, true);// no = 1
		ReflectArrivedLateLeaveEarly reflectApp = new ReflectArrivedLateLeaveEarly("", false);

		List<Integer> actualResult = RCReflectArrivedLateLeaveEarlyApp.reflect(appWorkChange, dailyApp, reflectApp);

		assertThat(actualResult).isEqualTo(Arrays.asList(604, 863));

		// すべての値が０
		// 早退時間をクリア
		assertLeavEarlyEmpty(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getLeaveEarlyTimeNo(1).get());

		// 出退勤の遅刻取消
		assertThat(dailyApp.getAttendanceLeave().get().getTimeLeavingWorks().get(0).isCanceledEarlyLeave()).isTrue();
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
		return new ArrivedLateLeaveEarlyShare(lateCancelation, lateOrLeaveEarlies, application);
	}

}
