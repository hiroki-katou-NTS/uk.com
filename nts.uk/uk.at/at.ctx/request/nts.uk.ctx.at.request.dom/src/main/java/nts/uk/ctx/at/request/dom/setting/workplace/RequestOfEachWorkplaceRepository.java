package nts.uk.ctx.at.request.dom.setting.workplace;

import java.util.Optional;

public interface RequestOfEachWorkplaceRepository {
	
	/**
	 * get request by company id
	 * @param appType
	 * @return
	 */
	Optional<RequestOfEachWorkplace> getRequestByWorkplace(String companyId, String workplaceID);
	/**
	 * get company  setting detail by apptype 
	 * @param companyId
	 * @param appType
	 * @return
	 */
	Optional<ApprovalFunctionSetting> getFunctionSetting(String companyId, String workplaceID, Integer appType);
	
}
