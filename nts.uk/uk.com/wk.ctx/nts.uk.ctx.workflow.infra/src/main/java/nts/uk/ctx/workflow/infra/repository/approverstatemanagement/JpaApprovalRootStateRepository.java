package nts.uk.ctx.workflow.infra.repository.approverstatemanagement;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootStateRepository;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.WwfdtApprovalRootState;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class JpaApprovalRootStateRepository extends JpaRepository implements ApprovalRootStateRepository {

	@Override
	public Optional<ApprovalRootState> findEmploymentApp(String rootStateID) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void insert(ApprovalRootState approvalRootState) {
		this.commandProxy().insert(WwfdtApprovalRootState.fromDomain(approvalRootState));
	}

	@Override
	public void update(ApprovalRootState approvalRootState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String rootStateID) {
		// TODO Auto-generated method stub
		
	}

}
