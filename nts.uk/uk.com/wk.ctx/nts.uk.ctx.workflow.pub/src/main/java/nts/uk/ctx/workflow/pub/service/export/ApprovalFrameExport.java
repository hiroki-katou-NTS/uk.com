package nts.uk.ctx.workflow.pub.service.export;

import java.util.ArrayList;
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
	
	public static ApprovalFrameExport fixData(Integer order) {
		List<ApproverStateExport> listApprover = new ArrayList<>();
		listApprover.add(ApproverStateExport.fixData(order));
		int confirmAtr = 0;
		GeneralDate appDate = GeneralDate.today();
		return new ApprovalFrameExport(order, listApprover, confirmAtr, appDate);
	}
}
