package nts.uk.ctx.at.request.dom.setting.requestofearch;

import java.util.Optional;

public interface RequestOfEarchWorkplaceRepository {
	/**
	 * get request by company id and workplace
	 * @param appType
	 * @return
	 */
	Optional<RequestOfEarchWorkplace> getRequest(String companyId,String workplaceId);
	/**
	 * get workplace setting detail by app type
	 * @param companyId
	 * @param workplaceId
	 * @param appType
	 * @return
	 */
	Optional<RequestAppDetailSetting> getRequestDetail(String companyId, String workplaceId, int appType);
}
