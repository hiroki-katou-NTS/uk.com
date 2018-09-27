package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public class MasterEmployeeOutput {
	//employee Id, approver of employee
	private Map<String, PersonApproverOutput> personRootInfor;
	//社員の情報一覧
	private List<EmployeeApproverOutput> lstEmployeeInfo;
}
