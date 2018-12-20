package nts.uk.ctx.at.schedule.app.export.shift.specificdayset;

import nts.uk.ctx.at.shared.app.find.pattern.monthly.setting.Period;
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
public interface SpecificdaySetReportRepository {
 
	Optional<Map<String, List<SpecificdaySetCompanyReportData>>> findAllSpecificdaySetCompany(String companyId,GeneralDate startDate, GeneralDate endDate);
   
	Optional<Map<String, List<SpecificdaySetWorkplaceReportData>>> findAllSpecificdaySetWorkplace(String companyId,GeneralDate startDate, GeneralDate endDate);
	
	Period getBaseDateByCompany(String companyId, GeneralDate startExportDate, GeneralDate endExportDate);
}
