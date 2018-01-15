package nts.uk.ctx.workflow.dom.service;
/**
 * 差し戻しする
 * @author Doan Duy Hung
 *
 */
public interface RemandService {
	
	public void doRemandForApprover();
	
	public void doRemandForApplicant(String companyID, String rootStateID, String employeeID);
	
}
