package nts.uk.ctx.at.request.app.find.application.approvalstatus;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprovalStatusEmployeeOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.DailyStatusOutput;

@Value
@AllArgsConstructor
public class ApprovalSttByEmpListOutput {
	List<DailyStatusOutput> listDailyStt;
	List<ApprovalStatusEmployeeOutput> listAppSttEmp;
}
