package nts.uk.ctx.at.schedule.app.export.shift.pattern.work;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * Specific Day Setting Repository
 * 
 * @author HiepTH
 *
 */
public interface WorkMonthlySettingReportRepository {
	Optional<Map<String, List<WorkMonthlySettingReportData>>> findAllWorkMonthlySet(String companyId, GeneralDate startDate, GeneralDate endDate);
	Optional<List<PersionalWorkMonthlySettingReportData>> findAllPersionWorkMonthlySet(String companyId, GeneralDate baseDate);
}
