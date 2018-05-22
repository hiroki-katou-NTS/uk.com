package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output;

import lombok.Data;
import lombok.Value;
import nts.arc.time.GeneralDate;
@Data
public class ApprovalRootCommonOutput {
	/**会社ID*/
	private String companyId;
	/**承認ID*/
	public String approvalId;
	/**社員ID*/
	private String employeeId;
	/**職場ID*/
	private String workpalceId;
	/**履歴ID*/
	private String historyId;
	/**申請種類*/
	private Integer applicationType;
	/**開始日*/
	private GeneralDate startDate;
	/**完了日*/
	private GeneralDate endDate;
	/**分岐ID*/
	private String branchId;
	/**任意項目申請ID*/
	private String anyItemApplicationId;
	/**確認ルート種類*/
	private Integer confirmationRootType;
	/**就業ルート区分*/
	private int employmentRootAtr;
	public ApprovalRootCommonOutput(String companyId, String approvalId, String employeeId, String workpalceId,
			String historyId, Integer applicationType, GeneralDate startDate, GeneralDate endDate, String branchId,
			String anyItemApplicationId, Integer confirmationRootType, int employmentRootAtr) {
		super();
		this.companyId = companyId;
		this.approvalId = approvalId;
		this.employeeId = employeeId;
		this.workpalceId = workpalceId;
		this.historyId = historyId;
		this.applicationType = applicationType;
		this.startDate = startDate;
		this.endDate = endDate;
		this.branchId = branchId;
		this.anyItemApplicationId = anyItemApplicationId;
		this.confirmationRootType = confirmationRootType;
		this.employmentRootAtr = employmentRootAtr;
	}
	
}
