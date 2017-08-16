package nts.uk.ctx.at.request.dom.setting.applicationformreason;

import java.util.List;

public interface ApplicationFormReasonRepository {
	/**
	 * get application by company id
	 * @param companyId
	 * @return
	 */
	List<ApplicationFormReason> getReasonByCompanyId(String companyId);
	/**
	 * get application by company id and application type
	 * @param companyId
	 * @param appType
	 * @return
	 */
	List<ApplicationFormReason> getReasonByAppType(String companyId, int appType);
}
