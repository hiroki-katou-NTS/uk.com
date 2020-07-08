package nts.uk.ctx.at.request.dom.setting.company.appreasonstandard;

import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
public interface AppReasonStandardRepository {
	
	public AppReasonStandard findByAppType(String companyID, ApplicationType appType);
	
	public AppReasonStandard findByHolidayAppType(String companyID, HolidayAppType holidayAppType);
	
}
