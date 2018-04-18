package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
/**
 * 
 * @author Anh.Bd
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ApproverOutput {
	//フェーズ
	ApprovalPhaseStateImport_New phase;
	//社員名
	String empName;
	//人数
	Integer numOfPeople;
}
