package nts.uk.ctx.at.request.app.find.application.approvalstatus;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprovalStatusEmployeeOutput;

@Value
@AllArgsConstructor
public class ApprovalSttRequestContentDis {
	private List<ApprovalStatusEmployeeOutput> listStatusEmp;
	private String selectedEmpId;
}
