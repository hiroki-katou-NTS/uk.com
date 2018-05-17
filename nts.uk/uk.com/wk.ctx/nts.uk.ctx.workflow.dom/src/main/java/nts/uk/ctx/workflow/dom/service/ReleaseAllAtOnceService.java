package nts.uk.ctx.workflow.dom.service;

import nts.uk.ctx.workflow.dom.service.output.ApproverApprovedOutput;

/**
 * 一括解除する
 * @author Doan Duy Hung
 *
 */
public interface ReleaseAllAtOnceService {
	
	/**
	 * 一括解除する 
	 * @param companyID 会社ID
	 * @param rootStateID インスタンスID
	 */
	public void doReleaseAllAtOnce(String companyID, String rootStateID, Integer rootType);
	
	/**
	 * 1.承認を行った承認者を取得する
	 * @param rootStateID
	 * @return
	 */
	public ApproverApprovedOutput getApproverApproved(String rootStateID, Integer rootType); 
	
}
