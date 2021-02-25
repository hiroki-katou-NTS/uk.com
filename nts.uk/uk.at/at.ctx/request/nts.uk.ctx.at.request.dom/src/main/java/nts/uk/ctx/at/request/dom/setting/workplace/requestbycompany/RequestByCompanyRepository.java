package nts.uk.ctx.at.request.dom.setting.workplace.requestbycompany;

import java.util.Optional;

import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.setting.workplace.appuseset.ApprovalFunctionSet;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
public interface RequestByCompanyRepository {
	
	Optional<ApprovalFunctionSet> findByAppType(String companyID, ApplicationType appType);
	
	Optional<ApprovalFunctionSet> findByCompanyID(String companyID);

	Optional<RequestByCompany> findByCompanyId(String companyId);

	void save(RequestByCompany domain);
	
}
