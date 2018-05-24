package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * 
 * @author Anh.BD
 *
 */
@Value
@AllArgsConstructor
public class UnApprovalSendMail {
	private String workplaceId;
	private boolean isChecked;
}
