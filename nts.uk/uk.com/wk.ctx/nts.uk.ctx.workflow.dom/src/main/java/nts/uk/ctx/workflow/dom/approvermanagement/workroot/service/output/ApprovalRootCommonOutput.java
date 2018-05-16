package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output;

import lombok.Value;
import nts.arc.time.GeneralDate;
@Value
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
}
