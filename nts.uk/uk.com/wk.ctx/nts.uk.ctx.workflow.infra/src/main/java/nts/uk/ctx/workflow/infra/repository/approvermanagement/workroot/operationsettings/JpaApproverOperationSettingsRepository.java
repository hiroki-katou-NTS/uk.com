package nts.uk.ctx.workflow.infra.repository.approvermanagement.workroot.operationsettings;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.ApproverOperationSettings;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.ApproverOperationSettingsRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.OperationMode;

@Stateless
public class JpaApproverOperationSettingsRepository extends JpaRepository implements ApproverOperationSettingsRepository {

	@Override
	public void insert(ApproverOperationSettings domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(ApproverOperationSettings domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<ApproverOperationSettings> get(String cid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<OperationMode> getOperationOfApproverRegis(String cid) {
		// TODO Auto-generated method stub
		return null;
	}

}
