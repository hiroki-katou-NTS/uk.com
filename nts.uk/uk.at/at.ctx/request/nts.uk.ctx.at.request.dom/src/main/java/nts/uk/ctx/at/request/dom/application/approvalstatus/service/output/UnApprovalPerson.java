package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;

/**
 * @author dat.lh
 */
@AllArgsConstructor
@Value
public class UnApprovalPerson {
	private String sId;
	private GeneralDate startDate;
	private GeneralDate endDate;
}
