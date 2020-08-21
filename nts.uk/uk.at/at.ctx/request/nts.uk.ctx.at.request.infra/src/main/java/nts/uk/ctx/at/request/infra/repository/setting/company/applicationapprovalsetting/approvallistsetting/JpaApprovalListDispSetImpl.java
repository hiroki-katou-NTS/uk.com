package nts.uk.ctx.at.request.infra.repository.setting.company.applicationapprovalsetting.approvallistsetting;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.approvallistsetting.ApprovalListDispSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.approvallistsetting.ApprovalListDisplaySetting;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Stateless
public class JpaApprovalListDispSetImpl extends JpaRepository implements ApprovalListDispSetRepository {

	@Override
	public Optional<ApprovalListDisplaySetting> findByCID(String companyID) {
		return Optional.empty();
	}

}
