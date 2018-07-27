package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
@Data
@AllArgsConstructor
public class PsApprovalRootDto {
	/**会社ID*/
	private String companyId;
	/**承認ID*/
	public String approvalId;
	/**社員ID*/
	private String employeeId;
	/**履歴ID*/
	private String historyId;
	/**申請種類*/
	private Integer applicationType;
	/**開始日*/
	private String startDate;
	/**終了日*/
	private String endDate;
	/**分岐ID*/
	private String branchId;
	/**任意項目申請ID*/
	private String anyItemApplicationId;
	/**確認ルート種類*/
	private Integer confirmationRootType;
	/**就業ルート区分*/
	private int employmentRootAtr;
	
	public static PsApprovalRootDto fromDomain(PersonApprovalRoot domain){
		return new PsApprovalRootDto(domain.getCompanyId(),
					domain.getApprovalId(),
					domain.getEmployeeId(),
					domain.getEmploymentAppHistoryItems().get(0).getHistoryId(),
					domain.getApplicationType() == null ? null : domain.getApplicationType().value,
					domain.getEmploymentAppHistoryItems().get(0).start().toString("yyyy/MM/dd"),
					domain.getEmploymentAppHistoryItems().get(0).end().toString("yyyy/MM/dd"),
					domain.getBranchId(),
					domain.getAnyItemApplicationId(),
					domain.getConfirmationRootType() == null ? null : domain.getConfirmationRootType().value,
					domain.getEmploymentRootAtr().value);
	}
}
