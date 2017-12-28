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

	private static final String SELECT_BY_ID;
	
	private static final String SELECT_TYPE_APP;
	
	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM WwfdtApprovalRootState e");
		builderString.append(" WHERE e.wwfdpApprovalRootStatePK.rootStateID = :rootStateID");	
		SELECT_BY_ID = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append(SELECT_BY_ID);
		builderString.append(" AND e.rootType = 0");
		SELECT_TYPE_APP = builderString.toString();
	}
	
	@Override
	public Optional<ApprovalRootState> findEmploymentApp(String rootStateID) {
		return this.queryProxy().query(SELECT_TYPE_APP, WwfdtApprovalRootState.class)
					.setParameter("rootStateID", rootStateID)
					.getSingle(x -> x.toDomain());
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
