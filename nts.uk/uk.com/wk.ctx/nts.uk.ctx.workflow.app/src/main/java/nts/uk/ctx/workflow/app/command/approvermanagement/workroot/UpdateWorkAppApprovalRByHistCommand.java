package nts.uk.ctx.workflow.app.command.approvermanagement.workroot;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateWorkAppApprovalRByHistCommand {

	/**承認ID*/
	public String approvalId;
	/**履歴ID*/
	private String historyId;
	/**履歴ID*/
	private String workplaceId;
	/**社員ID*/
	private String employeeId;
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
	/**check 申請承認の種類区分*/
	private int check;
	/**「履歴を削除する」を選択する か、「履歴を修正する」を選択する か。*/
	private int editOrDelete;
	/**開始日 previous*/
	private String sDatePrevious;
}
