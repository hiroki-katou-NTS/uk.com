package nts.uk.ctx.at.request.dom.setting.workplace;

import java.util.List;
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
	/**
	 * Get all 
	 * @return all RequestOfEachWorkplace in company
	 */
	List<RequestOfEachWorkplace> getAll();
	/**
	 * Add RequestOfEachWorkplace
	 */
	void add(RequestOfEachWorkplace domain);
	/**
	 * Update RequestOfEachWorkplace
	 */
	void update(RequestOfEachWorkplace domain);
}
