package nts.uk.ctx.at.request.dom.setting.requestofearch;

import java.util.List;

public interface RequestOfEarchCompanyRepository {
	/**
	 * get request by app type
	 * @param appType
	 * @return
	 */
	List<RequestOfEarchCompany> getRequestByAppType(int appType);
}
