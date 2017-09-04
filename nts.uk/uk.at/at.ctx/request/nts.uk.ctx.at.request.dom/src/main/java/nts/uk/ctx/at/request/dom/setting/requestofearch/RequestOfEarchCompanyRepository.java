package nts.uk.ctx.at.request.dom.setting.requestofearch;

import java.util.Optional;

public interface RequestOfEarchCompanyRepository {
	/**
	 * get request by company id
	 * @param appType
	 * @return
	 */
	Optional<RequestOfEarchCompany> getRequestByCompany(String companyId);
	/**
	 * get company  setting detail by apptype 
	 * @param companyId
	 * @param appType
	 * @return
	 */
	Optional<RequestAppDetailSetting> getRequestDetail(String companyId, int appType);
	
}
