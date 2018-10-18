package nts.uk.ctx.at.record.pub.workrecord.approvalmanagement;


/**
 * 
 * @author thuongtv
 *
 */

public interface ApprovalProcessPub {

	/**
	 * Get Usage setting of approval processing
	 * @param cid
	 * @return ApprovalProcessExport
	 */
	ApprovalProcessExport getApprovalProcess(String cid);
}
