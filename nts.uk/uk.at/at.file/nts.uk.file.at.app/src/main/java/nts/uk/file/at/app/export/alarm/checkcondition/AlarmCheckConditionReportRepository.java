package nts.uk.file.at.app.export.alarm.checkcondition;

import java.util.List;

/**
 * Specific Day Setting Repository
 * 
 * @author HiepTH
 *
 */
public interface AlarmCheckConditionReportRepository {
	public List<Schedule4WeekReportData> getSchedule4WeekConditions(String companyId);
	public List<DailyReportData> getDailyConditions(String companyId);
	public List<MulMonthReportData> getMulMonthConditions(String companyId);
	public List<MonthReportData> getMonthConditions(String companyId);
	public List<Agree36ReportData> getAgree36Conditions(String companyId);
}
