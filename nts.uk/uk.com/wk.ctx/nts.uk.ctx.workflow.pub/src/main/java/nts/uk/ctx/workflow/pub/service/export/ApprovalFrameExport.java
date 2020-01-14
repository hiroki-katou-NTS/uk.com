package nts.uk.ctx.workflow.pub.service.export;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Value
public class ApprovalFrameExport {
	
	private Integer frameOrder;
	
	private List<ApproverStateExport> listApprover;
	
	private int confirmAtr;
	
	private GeneralDate appDate;
}
