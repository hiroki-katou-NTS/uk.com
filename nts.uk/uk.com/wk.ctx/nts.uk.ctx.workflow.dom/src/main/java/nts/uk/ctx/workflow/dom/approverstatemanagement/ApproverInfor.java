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
public class ApproverInfor extends DomainObject {
	
	private String rootStateID;
	
	private String companyID;
	/**承認者*/
	private String approverID;
	/**承認区分*/
	private ApprovalBehaviorAtr approvalAtr;
	/**代行者*/
	private String agentID;
	/**承認日*/
	private GeneralDate approvalDate;
	/**理由*/
	private String approvalReason;
	
	public static ApproverInfor createFromFirst(String companyID, GeneralDate date, String rootStateID, ApproverInfor approverState){
		if(Strings.isBlank(approverState.getRootStateID())){
			return ApproverInfor.builder()
					.rootStateID(rootStateID)
					.approverID(approverState.getApproverID())
					.companyID(companyID)
//					.approvalDate(date)
					.build();
		}
		return approverState;
	}
}
