package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.application.common.PrePostAtrShare;
import nts.uk.ctx.at.shared.dom.scherec.application.holidayworktime.AppHolidayWorkShare;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.AppOverTimeShare;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.ApplicationTimeShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.hdworkapply.HdWorkAppReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.otworkapply.OtWorkAppReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.DailyAfterAppReflectResult;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.UpdateEditSttCreateBeforeAppReflect;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayMidnightWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayWorkMidNightTime;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.StaturoryAtrOfHolidayWork;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * refactor4 refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared(勤務予定、勤務実績).申請反映処理.申請反映条件.残業・休日出勤.残業休日出勤申請の反映
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AppReflectOtHdWork extends AggregateRoot {
	/**
	 * 会社ID
	 */
	private String companyId;

	/**
	 * 休日出勤申請
	 */
	private HdWorkAppReflect holidayWorkAppReflect;

	/**
	 * 残業申請
	 */
	private OtWorkAppReflect overtimeWorkAppReflect;

	/**
	 * 時間外深夜時間を反映する
	 */
	private NotUseAtr nightOvertimeReflectAtr;

	/**
	 * @author thanh_nx
	 *
	 *         残業申請を反映する（勤務実績）
	 */

	public List<Integer> processOverRc(RequireRC require, AppOverTimeShare overTimeApp,
			DailyRecordOfApplication dailyApp) {

		List<Integer> lstId = new ArrayList<Integer>();
		// 残業申請の反映
		lstId.addAll(this.getOvertimeWorkAppReflect().process(require, companyId, overTimeApp, dailyApp));

		// [時間外深夜時間を反映する]をチェック
		if (this.getNightOvertimeReflectAtr() == NotUseAtr.USE) {
			// 時間外深夜時間の反映
			processLateNightOvertime(dailyApp, overTimeApp.getApplicationTime(), overTimeApp.getPrePostAtr());
		}

		return lstId;

	}

	public static interface RequireRC extends OtWorkAppReflect.RequireRC {

	}
	
	/**
	 * @author thanh_nx
	 *
	 *         休日出勤申請を反映する（勤務予定）
	 */

	public DailyAfterAppReflectResult processHolSc(RequireHolSC require, AppHolidayWorkShare holidayApp,
			DailyRecordOfApplication dailyApp) {

		// 休日出勤申請の反映（勤務予定）
		return this.getHolidayWorkAppReflect().process(require, companyId, holidayApp, dailyApp);
	}

	public static interface RequireHolSC extends HdWorkAppReflect.RequireSC {

	}

	/**
	 * @author thanh_nx
	 *
	 *         休日出勤申請を反映する（勤務実績）
	 */
	public DailyAfterAppReflectResult process(Require require, AppHolidayWorkShare holidayApp,
			DailyRecordOfApplication dailyApp) {
		List<Integer> lstId = new ArrayList<Integer>();
		// 休日出勤申請の反映
		lstId.addAll(
				this.getHolidayWorkAppReflect().process(require, companyId, holidayApp, dailyApp).getLstItemId());

		// [時間外深夜時間を反映する]をチェック
		if (this.getNightOvertimeReflectAtr() == NotUseAtr.USE) {
			// 時間外深夜時間の反映
			processLateNightHolidayWork(dailyApp, holidayApp.getApplicationTime(), holidayApp.getPrePostAtr());
		}

		return new DailyAfterAppReflectResult(dailyApp, lstId);
	}

	/**
	 * 時間外深夜時間の反映（休日出勤）
	 */
	public static void processLateNightHolidayWork(DailyRecordOfApplication dailyApp, ApplicationTimeShare applicationTime,
			PrePostAtrShare prePostAtr) {

		// [input. 申請時間. 就業時間外深夜時間]をチェック
		if (!applicationTime.getOverTimeShiftNight().isPresent())
			return;
		// [input. 事前事後区分]をチェック
		if (prePostAtr == PrePostAtrShare.PREDICT) {
			// [input. 申請時間. 就業時間外深夜時間]を日別勤怠(work）にセットする
			dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
					.getExcessOfStatutoryTimeOfDaily().getExcessOfStatutoryMidNightTime()
					.setBeforeApplicationTime(applicationTime.getOverTimeShiftNight().map(x -> x.getMidNightOutSide())
							.orElse(new AttendanceTime(0)));
			// 申請反映状態にする
			// 申請反映状態にする
			UpdateEditSttCreateBeforeAppReflect.update(dailyApp, Arrays.asList(565));
		} else {
			// [input. 申請時間. 就業時間外深夜時間]を[所定外深夜時間]にセットする
			dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
					.getExcessOfStatutoryTimeOfDaily().getExcessOfStatutoryMidNightTime().getTime()
					.setTime(applicationTime.getOverTimeShiftNight().map(x -> x.getMidNightOutSide())
							.orElse(new AttendanceTime(0)));

			// [input.申請時間.就業外深夜時間]休出深夜時間（List）をループする
			applicationTime.getOverTimeShiftNight().get().getMidNightHolidayTimes().forEach(overMn -> {
				// 日別勤怠(work）の該当する[休出深夜時間]をチェック
				dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
						.getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().ifPresent(wh -> {

							// 休出深夜 = empty
							if (!wh.getHolidayMidNightWork().isPresent()) {
								List<HolidayWorkMidNightTime> lstHolidayWorkMid = new ArrayList<>();
								wh.getHolidayMidNightWork().set(new HolidayMidnightWork(lstHolidayWorkMid));
							}

							val dataSet = wh.getHolidayMidNightWork().get().getHolidayWorkMidNightTime().stream()
									.filter(y -> y.getStatutoryAtr() == overMn.getLegalClf()).findFirst();

							if (dataSet.isPresent()) {
								// [input. 申請時間. 就業時間外深夜時間]を[休出深夜時間]にセットする
								dataSet.get().getTime().setTime(overMn.getAttendanceTime());
							} else {
								val valueDefault = HolidayWorkMidNightTime.createDefaultWithAtr(overMn.getLegalClf());
								// 該当の法定区分をキーにした[休出深夜時間]を作成する
								valueDefault.getTime().setTime(overMn.getAttendanceTime());
								// [input. 申請時間. 就業時間外深夜時間]を[休出深夜時間]にセットする
								wh.getHolidayMidNightWork().get().getHolidayWorkMidNightTime().add(valueDefault);
							}
							// 申請反映状態にする
							UpdateEditSttCreateBeforeAppReflect.update(dailyApp,
									Arrays.asList(563, findId(overMn.getLegalClf())));
						});

			});

		}
		return;
	}

	private static Integer findId(StaturoryAtrOfHolidayWork statutoryAtr) {
		switch (statutoryAtr) {
		case WithinPrescribedHolidayWork:
			// 法内休出外深夜
			return 568;
		case ExcessOfStatutoryHolidayWork:
			// 法外休出外深夜
			return 570;
		case PublicHolidayWork:
			// 祝日休出深夜
			return 572;
		default:
			return null;
		}
	}

	/**
	 * @author thanh_nx
	 *
	 *         時間外深夜時間の反映（残業）
	 */
	public static void processLateNightOvertime(DailyRecordOfApplication dailyApp, ApplicationTimeShare applicationTimeShare,
			PrePostAtrShare prePostAtr) {

		// [input. 申請時間. 就業時間外深夜時間]をチェック
		if (!applicationTimeShare.getOverTimeShiftNight().isPresent()
				|| !dailyApp.getAttendanceTimeOfDailyPerformance().isPresent())
			return;

		// [input. 事前事後区分]をチェック
		if (prePostAtr == PrePostAtrShare.PREDICT) {
			// [input.申請時間.就業時間外深夜時間]を日別勤怠(work）にセットする
			dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
					.getExcessOfStatutoryTimeOfDaily().getExcessOfStatutoryMidNightTime()
					.setBeforeApplicationTime(applicationTimeShare.getOverTimeShiftNight().get().getMidNightOutSide());

			// 申請反映状態にする
			UpdateEditSttCreateBeforeAppReflect.update(dailyApp, Arrays.asList(565));

		}

		if (prePostAtr == PrePostAtrShare.POSTERIOR) {
			// [input.申請時間.就業時間外深夜時間]を日別勤怠(work）にセットする
			dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
					.getExcessOfStatutoryTimeOfDaily().getExcessOfStatutoryMidNightTime().getTime()
					.setTime(applicationTimeShare.getOverTimeShiftNight().get().getMidNightOutSide());

			// [input.申請時間.就業時間外深夜時間.残業深夜時間]を日別勤怠(work）にセットする
			dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
					.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().ifPresent(x -> {
						if (x.getExcessOverTimeWorkMidNightTime().isPresent()) {
							x.getExcessOverTimeWorkMidNightTime().get().getTime()
									.setTime(applicationTimeShare.getOverTimeShiftNight().get().getOverTimeMidNight());
						}
					});

			// 申請反映状態にする
			UpdateEditSttCreateBeforeAppReflect.update(dailyApp, Arrays.asList(563, 566));
		}
	}
		
	public static interface Require extends HdWorkAppReflect.Require {

	}

}
