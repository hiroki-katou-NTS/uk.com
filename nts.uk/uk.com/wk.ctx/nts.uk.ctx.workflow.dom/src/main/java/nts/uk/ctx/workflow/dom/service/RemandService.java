package nts.uk.ctx.workflow.dom.service;

import java.util.List;

/**
 * 差し戻しする
 * @author Doan Duy Hung
 *
 */
public interface RemandService {
	
	/**
	 * 差し戻しする(承認者まで)
	 * @param companyID
	 * @param rootStateID
	 * @param order
	 */
	public List<String> doRemandForApprover(String companyID, String rootStateID, Integer order, Integer rootType);
	
	/**
	 * 差し戻しする(本人まで)
	 * @param companyID
	 * @param rootStateID
	 */
	public void doRemandForApplicant(String companyID, String rootStateID, Integer rootType);
	
	public Integer getCurrentApprovePhase(String rootStateID, Integer rootType);
	
}
