package nts.uk.ctx.at.request.dom.application.common.approvalroot.subjectrequest.services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 承認者IDリスト
 * 
 * @author vunv
 *
 */
@Data
@AllArgsConstructor
public class ApproverInfo {
	/**
	 * 社員ID
	 */
	private String sid;
	
	private boolean isConfirmPerson;
	
	private int orderNumber;
}
