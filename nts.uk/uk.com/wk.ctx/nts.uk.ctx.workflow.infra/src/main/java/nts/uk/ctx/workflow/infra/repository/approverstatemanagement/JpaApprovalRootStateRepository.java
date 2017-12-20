package nts.uk.ctx.workflow.infra.repository.approverstatemanagement;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootStateRepository;
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
		// TODO Auto-generated method stub
		
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
