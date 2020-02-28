package nts.uk.ctx.hr.shared.dom.approval.rootstate;

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
	
}
