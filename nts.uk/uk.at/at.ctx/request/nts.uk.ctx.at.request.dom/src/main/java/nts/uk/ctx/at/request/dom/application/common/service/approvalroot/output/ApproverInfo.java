package nts.uk.ctx.at.request.dom.application.common.service.approvalroot.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 承認者IDリスト
 * 
 * @author vunv
 *
 */
@Getter
@AllArgsConstructor
public class ApproverInfo {
	/**
	 * 社員ID
	 */
	private String sid;
	/**承認フェーズID*/
	private String approvalPhaseId;
	/**確定者*/
	private boolean isConfirmPerson;
	/**順序*/
	private int orderNumber;
	
	private String name;
}
