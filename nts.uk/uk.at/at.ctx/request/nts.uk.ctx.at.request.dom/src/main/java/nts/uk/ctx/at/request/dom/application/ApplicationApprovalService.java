package nts.uk.ctx.at.request.dom.application;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;

/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface ApplicationApprovalService {
	
	public void insert(Application application);
	
	public void delete(String companyID, String appID, Long version, ApplicationType_Old appType);
	
	/**
	 * refactor 4
	 */
	public void insertApp(Application application, List<ApprovalPhaseStateImport_New> listApprovalPhaseState);
	
}
