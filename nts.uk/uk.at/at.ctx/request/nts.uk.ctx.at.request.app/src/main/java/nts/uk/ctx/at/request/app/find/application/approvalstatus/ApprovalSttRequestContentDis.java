package nts.uk.ctx.at.request.app.find.application.approvalstatus;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprovalStatusEmployeeOutput;

@Setter
@Getter
@AllArgsConstructor
public class ApprovalSttRequestContentDis {
	private List<ApprovalStatusEmployeeOutput> listStatusEmp;
	private String selectedEmpId;
}
