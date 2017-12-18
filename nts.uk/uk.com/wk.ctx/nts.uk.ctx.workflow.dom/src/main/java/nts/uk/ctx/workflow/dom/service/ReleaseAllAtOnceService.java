package nts.uk.ctx.workflow.dom.service;
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
	public void doReleaseAllAtOnce(String companyID, String rootStateID);
	
}
