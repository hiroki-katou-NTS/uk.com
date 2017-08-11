package nts.uk.ctx.at.request.dom.setting.requestofearch;

import java.util.Optional;

public interface RequestOfEarchWorkplaceRepository {
	/**
	 * get request by app type
	 * @param appType
	 * @return
	 */
	Optional<RequestOfEarchWorkplace> getRequestByAppType(String companyId,String workplaceId, int appType);
}
