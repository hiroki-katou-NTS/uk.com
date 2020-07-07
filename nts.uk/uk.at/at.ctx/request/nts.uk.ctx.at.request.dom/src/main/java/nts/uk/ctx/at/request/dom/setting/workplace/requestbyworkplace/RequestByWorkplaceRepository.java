package nts.uk.ctx.at.request.dom.setting.workplace.requestbyworkplace;

import java.util.Optional;

import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.setting.workplace.appuseset.ApprovalFunctionSet;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
public interface RequestByWorkplaceRepository {
	
	public Optional<ApprovalFunctionSet> findByWkpAndAppType(String companyID, String workplaceID, ApplicationType appType);
	
}
