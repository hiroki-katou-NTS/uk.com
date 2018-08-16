package nts.uk.ctx.workflow.pub.service.export;

import lombok.Getter;
import nts.arc.time.GeneralDate;
@Getter
public class ApproverStateParam {
	private String rootStateID;
	
	private Integer phaseOrder;
	
	private Integer frameOrder;
	
	private String approverID;
	
	private String companyID;
	
	private GeneralDate date;
}
