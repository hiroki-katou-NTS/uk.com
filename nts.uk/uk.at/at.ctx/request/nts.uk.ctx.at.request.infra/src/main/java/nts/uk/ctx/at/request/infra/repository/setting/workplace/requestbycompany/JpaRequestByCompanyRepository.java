package nts.uk.ctx.at.request.infra.repository.setting.workplace.requestbycompany;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.setting.workplace.appuseset.ApprovalFunctionSet;
import nts.uk.ctx.at.request.dom.setting.workplace.requestbycompany.RequestByCompanyRepository;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Stateless
public class JpaRequestByCompanyRepository extends JpaRepository implements RequestByCompanyRepository {

	@Override
	public Optional<ApprovalFunctionSet> findByAppType(String companyID, ApplicationType appType) {
		// TODO Auto-generated method stub
		return null;
	}

}
