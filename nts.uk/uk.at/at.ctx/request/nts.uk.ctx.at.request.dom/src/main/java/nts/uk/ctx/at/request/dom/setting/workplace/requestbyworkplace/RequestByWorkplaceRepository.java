package nts.uk.ctx.at.request.dom.setting.workplace.requestbyworkplace;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.setting.workplace.appuseset.ApprovalFunctionSet;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
public interface RequestByWorkplaceRepository {
	
	Optional<ApprovalFunctionSet> findByWkpAndAppType(String companyID, String workplaceID, ApplicationType appType);

	List<RequestByWorkplace> findByCompany(String companyId);

	Optional<RequestByWorkplace> findByCompanyAndWorkplace(String companyId, String workplaceId);

	void save(RequestByWorkplace domain);

	void delete(String companyId, String workplaceId);
	
}
