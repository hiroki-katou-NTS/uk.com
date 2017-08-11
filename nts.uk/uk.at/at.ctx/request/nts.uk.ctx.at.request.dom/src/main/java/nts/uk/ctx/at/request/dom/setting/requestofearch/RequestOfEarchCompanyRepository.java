package nts.uk.ctx.at.request.dom.setting.requestofearch;

import java.util.Optional;

public interface RequestOfEarchCompanyRepository {
	/**
	 * get request by app type
	 * @param appType
	 * @return
	 */
	Optional<RequestOfEarchCompany> getRequestByAppType(String companyId, int appType);
}
