package nts.uk.ctx.at.function.dom.adapter.workrecord.approvalmanagement;
/**
 * 
 * @author thuongtv
 *
 */
public interface ApprovalProcessAdapter {

	/**
	 * Get Usage setting of approval processing by cid
	 * @param cid
	 * @return ApprovalProcessImport
	 */
	public ApprovalProcessImport getApprovalProcess(String cid);
}
