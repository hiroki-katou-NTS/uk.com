package nts.uk.ctx.at.request.app.find.application.approvalstatus;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.app.command.application.approvalstatus.ApprovalStatusMailTempCommand;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprSttWkpEmpMailOutput;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApprSttMailDestParam {
	private ApprovalStatusMailTempCommand command;
	
	private List<ApprSttWkpEmpMailOutput> wkpEmpMailLst;
}
