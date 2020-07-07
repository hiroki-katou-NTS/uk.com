package nts.uk.ctx.at.request.infra.repository.setting.workplace.requestbyworkplace;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.setting.workplace.appuseset.ApprovalFunctionSet;
import nts.uk.ctx.at.request.dom.setting.workplace.requestbyworkplace.RequestByWorkplaceRepository;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Stateless
public class JpaRequestByWorkplaceRepository extends JpaRepository implements RequestByWorkplaceRepository {

	@Override
	public Optional<ApprovalFunctionSet> findByWkpAndAppType(String companyID, String workplaceID,
			ApplicationType appType) {
		// TODO Auto-generated method stub
		return null;
	}

}
