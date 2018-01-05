package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

/**
 * 8-2.詳細画面承認後の処理
 * @author Doan Duy Hung
 *
 */
public interface DetailAfterApproval_New {
	
	public String doApproval(String companyID, String appID, String employeeID, String memo);
	
}
