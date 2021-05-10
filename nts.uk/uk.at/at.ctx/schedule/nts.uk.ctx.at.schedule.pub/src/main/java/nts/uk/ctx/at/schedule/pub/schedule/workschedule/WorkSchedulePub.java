package nts.uk.ctx.at.schedule.pub.schedule.workschedule;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 
 * @author tutk
 *
 */
public interface WorkSchedulePub {
	public Optional<WorkScheduleExport> get(String employeeID , GeneralDate ymd);

	/**
	 * 社員ID(List)、期間を設定して勤務予定を取得する
	 */
	public List<WorkScheduleExport> getList(List<String> sids, DatePeriod period);
	
	public List<WorkScheduleBasicInforExport> get(List<String> lstSid , DatePeriod ymdPeriod);
	
	/**
	 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定.Export.日別勤務予定を取得する.社員IDリスト、基準日から勤務予定を取得する
	 * [1] 取得する
	 * 
	 * @param sid      社員ID
	 * @param baseDate 基準日
	 * @return 勤務種類コード
	 */
	public Optional<String> getWorkTypeCode(String sid, GeneralDate baseDate);
	
	/**
	 * 最も未来の勤務予定の年月日を取得する
	 * 
	 * RequestList439
	 * 
	 * 異動者、勤務種別変更者の作成期間の計算
	 * 社員ID（List）を条件に、存在するスケジュール期間の中で最も大きい年月日を取得する
	 * 
	 * @param sIds
	 * @return GeneralDate
	 */
	public Optional<GeneralDate> acquireMaxDateBasicSchedule(List<String> sIds);
}
