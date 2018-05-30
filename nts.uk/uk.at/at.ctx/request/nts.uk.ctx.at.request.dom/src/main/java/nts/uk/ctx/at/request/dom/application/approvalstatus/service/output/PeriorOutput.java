package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import lombok.Value;
import nts.arc.time.GeneralDate;
/**
 * 
 * @author Anh.bd
 *
 */
@Value
public class PeriorOutput {
	private GeneralDate startDate;
	private GeneralDate endDate;
}
