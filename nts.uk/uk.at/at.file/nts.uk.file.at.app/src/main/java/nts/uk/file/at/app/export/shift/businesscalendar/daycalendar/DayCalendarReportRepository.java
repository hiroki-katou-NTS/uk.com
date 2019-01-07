package nts.uk.file.at.app.export.shift.businesscalendar.daycalendar;

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
public interface DayCalendarReportRepository {
//	Period getBaseDateByCompany(String companyId, GeneralDate startExportDate, GeneralDate endExportDate);
	Optional<Map<String, List<CompanyCalendarReportData>>> findCalendarCompanyByDate(String companyId, GeneralDate startDate, GeneralDate endDate);
	Optional<Map<String, List<WorkplaceCalendarReportData>>> findCalendarWorkplaceByDate(String companyId, GeneralDate startDate, GeneralDate endDate);
	Optional<Map<String, List<ClassCalendarReportData>>> findCalendarClassByDate(String companyId, GeneralDate startDate, GeneralDate endDate);
}
