package nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.output;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Value
@AllArgsConstructor
public class ApprovalRootPattern {
	
	private GeneralDate baseDate;
	
	private ApprovalRootContentImport_New ApprovalRootContentImport; 
	
}
