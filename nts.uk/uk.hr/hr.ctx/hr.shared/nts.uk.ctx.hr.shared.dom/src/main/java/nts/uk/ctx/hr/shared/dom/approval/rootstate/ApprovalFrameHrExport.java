package nts.uk.ctx.hr.shared.dom.approval.rootstate;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
/**
 * 
 * @author Laitv
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApprovalFrameHrExport {
	
	private Integer frameOrder;
	
	private List<ApproverStateHrExport> listApprover;
	
	private int confirmAtr;
	
	private GeneralDate appDate;
	
	public static ApprovalFrameHrExport fixData(Integer order) {
		List<ApproverStateHrExport> listApprover = new ArrayList<>();
		listApprover.add(ApproverStateHrExport.fixData(order));
		int confirmAtr = 0;
		GeneralDate appDate = GeneralDate.today();
		return new ApprovalFrameHrExport(order, listApprover, confirmAtr, appDate);
	}
}
