package nts.uk.ctx.at.schedule.app.export.shift.specificdayset;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Specific Day Setting Repository
 * 
 * @author HiepTH
 *
 */
public interface SpecificdaySetReportRepository {
 
	Optional<Map<String, List<SpecificdaySetReportData>>> findAllSpecificdaySetCompany(String companyId);
   
	Optional<Map<String, List<SpecificdaySetReportData>>> findAllSpecificdaySetWorkplace(String companyId);
}
