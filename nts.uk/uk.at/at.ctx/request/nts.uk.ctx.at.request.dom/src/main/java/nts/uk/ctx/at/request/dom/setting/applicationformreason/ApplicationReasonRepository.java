package nts.uk.ctx.at.request.dom.setting.applicationformreason;

import java.util.List;

public interface ApplicationReasonRepository {
	/**
	 * get application by company id
	 * @param companyId
	 * @return
	 */
	List<ApplicationReason> getReasonByCompanyId(String companyId);
	/**
	 * get application by company id and application type
	 * @param companyId
	 * @param appType
	 * @return
	 */
	List<ApplicationReason> getReasonByAppType(String companyId, int appType);
}
