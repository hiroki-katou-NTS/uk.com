package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.algorithm;

import javax.ejb.Stateless;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthly;

/*
 * 予定時間＋総労働時間をチェック
 */
@Stateless
public class CheckScheTimeService {
	public void check(AttendanceTimeOfMonthly attendanceTimeOfMonthly) {
		// 当月より前の月かチェック
		// TODO
		if (true) {
			// 総労働　を計算
			attendanceTimeOfMonthly.
		}
	}
}
