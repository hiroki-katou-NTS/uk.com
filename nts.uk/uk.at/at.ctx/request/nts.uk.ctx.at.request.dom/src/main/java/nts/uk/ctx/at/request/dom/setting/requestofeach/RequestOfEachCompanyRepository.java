package nts.uk.ctx.at.request.dom.setting.requestofeach;

import java.util.Optional;

public interface RequestOfEachCompanyRepository {
	/**
	 * get request by company id
	 * @param appType
	 * @return
	 */
	Optional<RequestOfEachCompany> getRequestByCompany(String companyId);
	/**
	 * get company  setting detail by apptype 
	 * @param companyId
	 * @param appType
	 * @return
	 */
	Optional<RequestAppDetailSetting> getRequestDetail(String companyId, int appType);
	
}
