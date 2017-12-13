package nts.uk.ctx.workflow.dom.approverstatemanagement;

import java.util.Optional;

public interface ApprovalRootStateRepository {
	
	public Optional<ApprovalRootState> findByID(String companyID, String rootStateID);
	
}
