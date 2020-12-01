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
}
