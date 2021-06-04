package nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.algorithm;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly.AttendanceTimeOfWeekly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly.AttendanceTimeOfWeeklyRepository;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト.週次のアラームリストのチェック条件.アルゴリズム.週次の集計処理.週別実績の値を取得
 *
 */
@Stateless
public class GetWeeklyPerformanceService {
	@Inject
	private AttendanceTimeOfWeeklyRepository attendanceTimeOfWeeklyRepository;
	
	/**
	 * 週別実績の値を取得
	 * @return List 週別実績の勤怠時間
	 */
	public List<AttendanceTimeOfWeekly> getValues(List<String> lstSid, DatePeriod period) {
		// ドメインモデル「週別実績の勤怠時間」を取得する  QA#116337
		return attendanceTimeOfWeeklyRepository.findBySidsAndDatePeriod(lstSid, period);
	}
}
