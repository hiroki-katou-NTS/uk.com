package nts.uk.ctx.workflow.dom.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class RemandImpl implements RemandService {
	
	@Inject
	private ReleaseAllAtOnceService releaseAllAtOnceService;

	@Override
	public void doRemandForApprover() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doRemandForApplicant(String companyID, String rootStateID, String employeeID) {
		releaseAllAtOnceService.doReleaseAllAtOnce(companyID, rootStateID);
	}

}
