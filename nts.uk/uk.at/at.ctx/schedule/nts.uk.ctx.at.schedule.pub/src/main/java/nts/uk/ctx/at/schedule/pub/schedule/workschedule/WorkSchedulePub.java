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
	
	public List<WorkScheduleBasicInforExport> get(List<String> lstSid , DatePeriod ymdPeriod);
	/**
	 * UKDesign.ドメインモッ�.NittsuSystem.UniversalK.就業.contexts.勤務予�勤務予�勤務予�Export.日別勤務予定を取得す�社員IDリスト、基準日から勤務予定を取得す�
	 * [1] 取得す�
	 * 
	 * @param sid      社員ID
	 * @param baseDate 基準日
	 * @return 勤務種類コー�
	 */
	public Optional<String> getWorkTypeCode(String sid, GeneralDate baseDate);
}
