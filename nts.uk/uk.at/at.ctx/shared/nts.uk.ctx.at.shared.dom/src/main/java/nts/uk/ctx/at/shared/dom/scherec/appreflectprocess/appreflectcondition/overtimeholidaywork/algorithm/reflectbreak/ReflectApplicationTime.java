package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.algorithm.reflectbreak;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import nts.gul.util.value.Finally;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.OvertimeApplicationSettingShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.ReflectAppDestination;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.UpdateEditSttCreateBeforeAppReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.stampapplication.algorithm.CancelAppStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayWorkFrameTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.BonusPayTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.outsideworktime.OverTimeFrameTime;

/**
 * @author thanh_nx
 *
 *         申請時間の反映
 */
public class ReflectApplicationTime {

	public static void process(List<OvertimeApplicationSettingShare> applicationTimes,
			DailyRecordOfApplication dailyApp, Optional<ReflectAppDestination> reflectAppDes) {

		List<Integer> lstId = new ArrayList<Integer>();

		// 勤怠種類ごとの申請時間(work）に、申請時間をセットする
		applicationTimes.forEach(overtime -> {

			// [申請時間(work）. 勤怠種類]をチェック
			switch (overtime.getAttendanceType()) {
			case NORMALOVERTIME:
				// 残業時間の反映
				if (reflectAppDes.isPresent())
					lstId.addAll(processOvertimeDetail(overtime, dailyApp, reflectAppDes.get()));
				break;

			case BREAKTIME:
				// 休出時間の反映
				if (reflectAppDes.isPresent())
					lstId.addAll(processHolidayDetail(overtime, dailyApp, reflectAppDes.get()));
				break;

			case BONUSPAYTIME:
				// 加給時間の反映
				lstId.addAll(processSalaryTime(overtime, dailyApp));
				break;

			case BONUSSPECIALDAYTIME:
				// 特定日加給時間の反映
				lstId.addAll(processSpecialDaySalaryTime(overtime, dailyApp));
				break;

			default:
				break;
			}

		});

		// 申請反映状態にする
		UpdateEditSttCreateBeforeAppReflect.update(dailyApp, lstId);

	}

	/**
	 *         残業時間の反映
	 */
	private static List<Integer> processOvertimeDetail(OvertimeApplicationSettingShare applicationTime,
			DailyRecordOfApplication dailyApp, ReflectAppDestination reflectAppDes) {

		List<Integer> lstId = new ArrayList<Integer>();
		if (!dailyApp.getAttendanceTimeOfDailyPerformance().isPresent()
				|| !dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
						.getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork().isPresent()) {
			return lstId;

		}

		List<OverTimeFrameTime> overTimeWorkFrameTime = dailyApp.getAttendanceTimeOfDailyPerformance().get()
				.getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork()
				.get().getOverTimeWorkFrameTime();

		Consumer<OverTimeFrameTime> consumerOverTime = new Consumer<OverTimeFrameTime>() {
			@Override
			public void accept(OverTimeFrameTime t) {
				// [input. 申請の反映先]をチェック
				if (reflectAppDes == ReflectAppDestination.SCHEDULE) {
					// 残業枠NOをキーにして事前残業時間をセットする
					t.setBeforeApplicationTime(applicationTime.getApplicationTime());
					lstId.add(CancelAppStamp.createItemId(220, t.getOverWorkFrameNo().v(), 5));
				} else {
					// 残業枠NOをキーにして残業時間をセットする
					t.setOverTimeWork(TimeDivergenceWithCalculation.sameTime(applicationTime.getApplicationTime()));
					t.setTransferTime(TimeDivergenceWithCalculation.defaultValue());
					lstId.add(CancelAppStamp.createItemId(216, t.getOverWorkFrameNo().v(), 5));
					lstId.add(CancelAppStamp.createItemId(217, t.getOverWorkFrameNo().v(), 5));
				}

			}
		};

		// 該当の枠NOをキーにした[残業枠時間]を作成する
		Optional<OverTimeFrameTime> overTimeFrame = overTimeWorkFrameTime.stream()
				.filter(x -> x.getOverWorkFrameNo().v() == applicationTime.getFrameNo()).findFirst();

		if (!overTimeFrame.isPresent()) {
			OverTimeFrameTime overtimeTemp = OverTimeFrameTime.createDefaultWithNo(applicationTime.getFrameNo());
			consumerOverTime.accept(overtimeTemp);
			overTimeWorkFrameTime.add(overtimeTemp);
		} else {
			consumerOverTime.accept(overTimeFrame.get());
		}

		return lstId;
	}
	
	/**
	 *         休出時間の反映
	 */
	private static List<Integer> processHolidayDetail(OvertimeApplicationSettingShare applicationTime,
			DailyRecordOfApplication dailyApp, ReflectAppDestination reflectAppDes) {
		List<Integer> lstId = new ArrayList<Integer>();

		// 日別勤怠(work）の該当する[休出枠時間をチェック
		if (!dailyApp.getAttendanceTimeOfDailyPerformance().isPresent()
				|| !dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
						.getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().isPresent()) {
			return lstId;

		}

		Consumer<HolidayWorkFrameTime> holidayOverTime = new Consumer<HolidayWorkFrameTime>() {
			@Override
			public void accept(HolidayWorkFrameTime t) {
				// [input. 申請の反映先]をチェック
				if (reflectAppDes == ReflectAppDestination.SCHEDULE) {
					// 休出枠NOをキーにして休出時間をセットする
					t.setBeforeApplicationTime(Finally.of(applicationTime.getApplicationTime()));
					lstId.add(CancelAppStamp.createItemId(270, t.getHolidayFrameNo().v(), 5));
				} else {
					// // 休出枠NOをキーにして休出時間をセットする
					t.setHolidayWorkTime(
							Finally.of(TimeDivergenceWithCalculation.sameTime(applicationTime.getApplicationTime())));
					t.setTransferTime(Finally.of(TimeDivergenceWithCalculation.defaultValue()));
					lstId.add(CancelAppStamp.createItemId(266, t.getHolidayFrameNo().v(), 5));
					lstId.add(CancelAppStamp.createItemId(267, t.getHolidayFrameNo().v(), 5));
				}

			}
		};

		// 該当の枠NOをキーにした[休出枠時間]を作成する
		List<HolidayWorkFrameTime> holidayTimeWorkFrameTime = dailyApp.getAttendanceTimeOfDailyPerformance().get()
				.getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily()
				.getWorkHolidayTime().get().getHolidayWorkFrameTime();

		Optional<HolidayWorkFrameTime> overTimeFrame = holidayTimeWorkFrameTime.stream()
				.filter(x -> x.getHolidayFrameNo().v() == applicationTime.getFrameNo()).findFirst();

		if (!overTimeFrame.isPresent()) {
			HolidayWorkFrameTime holidaytimeTemp = HolidayWorkFrameTime
					.createDefaultWithNo(applicationTime.getFrameNo());
			holidayOverTime.accept(holidaytimeTemp);
			holidayTimeWorkFrameTime.add(holidaytimeTemp);
		} else {
			holidayOverTime.accept(overTimeFrame.get());
		}

		// [input. 申請の反映先]をチェック

		// 休出枠NOをキーにして休出時間をセットする

		// 休出枠NOをキーにして休出時間をセットする

		return lstId;
	}
	
	/**
	 *         加給時間の反映
	 */
	private static List<Integer> processSalaryTime(OvertimeApplicationSettingShare applicationTime,
			DailyRecordOfApplication dailyApp) {

		List<Integer> lstId = new ArrayList<Integer>();

		// 日別勤怠(work）の該当する[加給時間]をチェック
		if (!dailyApp.getAttendanceTimeOfDailyPerformance().isPresent()) {
			return lstId;
		}

		List<BonusPayTime> salaryTime = dailyApp.getAttendanceTimeOfDailyPerformance().get()
				.getActualWorkingTimeOfDaily().getTotalWorkingTime().getRaiseSalaryTimeOfDailyPerfor()
				.getRaisingSalaryTimes();
		// 該当の枠NOをキーにした[加給時間]を作成する
		Optional<BonusPayTime> salaryTimeOpt = salaryTime.stream()
				.filter(x -> x.getBonusPayTimeItemNo() == applicationTime.getFrameNo()).findFirst();

		// 加給NOをキーにして加給時間をセットする
		if (salaryTimeOpt.isPresent()) {
			salaryTimeOpt.get().setBonusPayTime(applicationTime.getApplicationTime());
		} else {
			BonusPayTime bt = BonusPayTime.createDefaultWithNo(applicationTime.getFrameNo());
			bt.setBonusPayTime(applicationTime.getApplicationTime());
			salaryTime.add(bt);
		}
		lstId.add(CancelAppStamp.createItemId(316, applicationTime.getFrameNo(), 1));
		return lstId;
	}
	

	/**
	 * 特別日加給時間
	 */
	private static List<Integer> processSpecialDaySalaryTime(OvertimeApplicationSettingShare applicationTime,
			DailyRecordOfApplication dailyApp) {

		List<Integer> lstId = new ArrayList<Integer>();
		// 日別勤怠(work）の該当する[特定日加給時間]をチェック

		if (!dailyApp.getAttendanceTimeOfDailyPerformance().isPresent()) {
			return lstId;
		}

		List<BonusPayTime> salaryTime = dailyApp.getAttendanceTimeOfDailyPerformance().get()
				.getActualWorkingTimeOfDaily().getTotalWorkingTime().getRaiseSalaryTimeOfDailyPerfor()
				.getAutoCalRaisingSalarySettings();
		// // 該当の枠NOをキーにした[特定日加給時間]を作成する
		Optional<BonusPayTime> salaryTimeOpt = salaryTime.stream()
				.filter(x -> x.getBonusPayTimeItemNo() == applicationTime.getFrameNo()).findFirst();

		// 加給NOをキーにして特定日加給時間をセットする
		if (salaryTimeOpt.isPresent()) {
			salaryTimeOpt.get().setBonusPayTime(applicationTime.getApplicationTime());
		} else {
			BonusPayTime bt = BonusPayTime.createDefaultWithNo(applicationTime.getFrameNo());
			bt.setBonusPayTime(applicationTime.getApplicationTime());
			salaryTime.add(bt);
		}
		lstId.add(CancelAppStamp.createItemId(366, applicationTime.getFrameNo(), 1));
		return lstId;

	}
}
