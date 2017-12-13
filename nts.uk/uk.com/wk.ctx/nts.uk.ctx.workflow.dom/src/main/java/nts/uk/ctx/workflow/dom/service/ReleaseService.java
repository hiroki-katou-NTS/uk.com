package nts.uk.ctx.workflow.dom.service;
/**
 * 一括解除する
 * @author Doan Duy Hung
 *
 */
public interface ReleaseService {
	
	/**
	 * 一括解除する 
	 * @param companyID 会社ID
	 * @param rootStateID インスタンスID
	 */
	public void doRelease(String companyID, String rootStateID);
	
}
