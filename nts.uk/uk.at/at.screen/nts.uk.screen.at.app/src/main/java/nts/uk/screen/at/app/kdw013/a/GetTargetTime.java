package nts.uk.screen.at.app.kdw013.a;

import java.util.ArrayList;
import java.util.List;

import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.OvertimeLeaveTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayWorkTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.overtimehours.clearovertime.OverTimeOfDaily;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KDW013_工数入力.A:工数入力.メニュー別OCD.残業申請・休出時間申請の対象時間を取得する
 * 
 * @author tutt
 *
 */
public class GetTargetTime {
	/**
	 * 
	 * @param sid                    対象者
	 * @param mode                   画面モード（入力モード(1)/確認モード(2)）
	 * @param integrationOfDailyList List<日別勤怠(Work)>
	 * @return List<残業休出時間>
	 */
	public List<OvertimeLeaveTime> get(String sid, int mode, List<IntegrationOfDaily> integrationOfDailyList) {
		List<OvertimeLeaveTime> overtimeLeaveTimes = new ArrayList<>();

		// 【条件】
		// 日別勤怠(Work)．勤怠時間．勤務時間．総労働時間．所定外時間．残業時間.isPresent AND 画面モード = 入力モード
		if (!integrationOfDailyList.isEmpty()) {
			for (IntegrationOfDaily i : integrationOfDailyList) {

				// 残業時間
				// 1: <call>()
				if (i.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
						.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().isPresent() && mode == 1) {
					// 1.1: 残業合計時間の計算する(): 勤怠時間
					OverTimeOfDaily overTimeWork = i.getAttendanceTimeOfDailyPerformance().get()
							.getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily()
							.getOverTimeWork().get();

					// 勤怠時間
					int calOvertime = overTimeWork.calcTotalFrameTime();

					// 1.2: [残業合計時間 > 0]:get(対象者,日別勤怠(Work).年月日)
					if (calOvertime > 0) {

					}

					// 1.3: [残業申請.isEmpty]:create(日別勤怠(Work).年月日,残業休出区分.残業申請,残業合計時間)
				}

				// 休出時間
				// 2: <call>()
				if (i.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
						.getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().isPresent() && mode == 1) {
					// 2.1: 休出合計時間():勤怠時間
					HolidayWorkTimeOfDaily holidayTime = i.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
							.getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().get();
					
					//勤怠時間
					int calHolidayTime = holidayTime.calcTotalFrameTime();

					// 2.2: [休出合計時間 > 0]:get(対象者,日別勤怠(Work).年月日)
					if (calHolidayTime > 0) {
						
					}

					// 2.3: [休日出勤申請.isEmpty]:create(日別勤怠(Work).年月日,残業休出区分.休日出勤申請,休出合計時間)
				}
			}
		}

		return overtimeLeaveTimes;
	}

}
