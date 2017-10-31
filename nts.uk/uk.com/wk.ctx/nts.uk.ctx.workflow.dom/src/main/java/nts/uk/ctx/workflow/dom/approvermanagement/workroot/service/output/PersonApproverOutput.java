package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PersonApproverOutput {
	/**
	 * 社員の情報
	 */
	EmployeeApproverOutput employeeInfo;
	/**
	 * 個人別就業承認ルート 情報
	 */
	List<ApprovalForApplication> psRootInfo;
}
