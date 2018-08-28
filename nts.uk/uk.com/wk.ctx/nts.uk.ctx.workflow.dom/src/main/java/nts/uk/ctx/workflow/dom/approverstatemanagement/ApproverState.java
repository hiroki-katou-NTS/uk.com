package nts.uk.ctx.workflow.dom.approverstatemanagement;

import org.apache.logging.log4j.util.Strings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;
/**
 * 承認枠 : 承認者
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ApproverState extends DomainObject {
	
	private String rootStateID;
	
	private Integer phaseOrder;
	
	private Integer frameOrder;
	
	private String approverID;
	
	private String companyID;
	
	private GeneralDate date;
	
	public static ApproverState createFromFirst(String companyID, GeneralDate date, String rootStateID, ApproverState approverState){
		if(Strings.isBlank(approverState.getRootStateID())){
			return ApproverState.builder()
					.rootStateID(rootStateID)
					.phaseOrder(approverState.getPhaseOrder())
					.frameOrder(approverState.getFrameOrder())
					.approverID(approverState.getApproverID())
					.companyID(companyID)
					.date(date)
					.build();
		}
		return approverState;
	}
}
