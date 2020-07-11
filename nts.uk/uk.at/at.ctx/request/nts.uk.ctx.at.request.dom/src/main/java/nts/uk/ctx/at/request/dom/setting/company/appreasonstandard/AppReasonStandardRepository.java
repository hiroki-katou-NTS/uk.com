package nts.uk.ctx.at.request.dom.setting.company.appreasonstandard;

import java.util.Optional;

import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
public interface AppReasonStandardRepository {
	
	public Optional<AppReasonStandard> findByAppType(String companyID, ApplicationType appType);
	
	public Optional<AppReasonStandard> findByHolidayAppType(String companyID, HolidayAppType holidayAppType);
	
}
