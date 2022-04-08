package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.unregisterapproval.ErrorContent;

@Data
@AllArgsConstructor
public class EmployeeUnregisterOutput {
	private String employeeId;
	private String appTarget;
	private int endStatus; // 1 => エラーあり/ 0 => 承認ルートなし
	private Integer route; // （個人（1）、職場（2）、会社（3））
	private Boolean common;
	private Optional<String> workplaceId;
	private List<ErrorContent> errorContents;
}
