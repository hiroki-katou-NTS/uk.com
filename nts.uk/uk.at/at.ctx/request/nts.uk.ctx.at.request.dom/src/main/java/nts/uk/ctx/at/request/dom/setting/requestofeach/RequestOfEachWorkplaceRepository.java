package nts.uk.ctx.at.request.dom.setting.requestofeach;

import java.util.Optional;

public interface RequestOfEachWorkplaceRepository {
	/**
	 * get request by company id and workplace
	 * @param appType
	 * @return
	 */
	Optional<RequestOfEachWorkplace> getRequest(String companyId,String workplaceId);
	/**
	 * get workplace setting detail by app type
	 * @param companyId
	 * @param workplaceId
	 * @param appType
	 * @return
	 */
	Optional<RequestAppDetailSetting> getRequestDetail(String companyId, String workplaceId, int appType);
}
