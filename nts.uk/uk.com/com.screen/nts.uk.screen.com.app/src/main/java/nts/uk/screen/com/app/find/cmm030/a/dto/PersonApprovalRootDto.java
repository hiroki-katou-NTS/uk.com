package nts.uk.screen.com.app.find.cmm030.a.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;

@Data
@AllArgsConstructor
public class PersonApprovalRootDto {

	/** 会社ID */
	private String companyId;

	/** 承認ID */
	private String approvalId;

	/** 社員ID */
	private String employeeId;

	/** 承認ルート */
	public ApprovalRootDto apprRoot;

	/** 運用モード */
	private int operationMode;

	public static PersonApprovalRootDto fromDomain(PersonApprovalRoot domain) {
		return new PersonApprovalRootDto(domain.getCompanyId(), domain.getApprovalId(), domain.getEmployeeId(),
				ApprovalRootDto.fromDomain(domain.getApprRoot()), domain.getOperationMode().value);
	}
}
