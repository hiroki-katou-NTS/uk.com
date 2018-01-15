package nts.uk.ctx.workflow.dom.approverstatemanagement;

import org.apache.logging.log4j.util.Strings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
/**
 * 承認枠 : 承認者
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
@Builder
public class ApproverState extends DomainObject {
	
	private String rootStateID;
	
	private Integer phaseOrder;
	
	private Integer frameOrder;
	
	private String approverID;
	
	public static ApproverState createFromFirst(String rootStateID, ApproverState approverState){
		if(Strings.isBlank(approverState.getRootStateID())){
			return ApproverState.builder()
					.rootStateID(rootStateID)
					.phaseOrder(approverState.getPhaseOrder())
					.frameOrder(approverState.getFrameOrder())
					.approverID(approverState.getApproverID())
					.build();
		}
		return approverState;
	}
}
