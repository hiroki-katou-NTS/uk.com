package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import lombok.Value;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRoot;
@Value
public class ComApprovalRootDto {
	/**会社ID*/
	private String companyId;
	/**承認ID*/
	public String approvalId;
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
	
	public static ComApprovalRootDto fromDomain(CompanyApprovalRoot domain){
		return new ComApprovalRootDto(domain.getCompanyId(),
					domain.getApprovalId(),
					domain.getHistoryId(),
					domain.getApplicationType() == null ? null : domain.getApplicationType().value,
					domain.getPeriod().getStartDate().toString(),
					domain.getPeriod().getEndDate().toString(),
					domain.getBranchId(),
					domain.getAnyItemApplicationId(),
					domain.getConfirmationRootType() == null ? null : domain.getConfirmationRootType().value,
					domain.getEmploymentRootAtr().value);
	}
}
