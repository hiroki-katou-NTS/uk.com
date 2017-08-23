package nts.uk.ctx.workflow.app.command.approvermanagement.workroot;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class AddWorkAppApprovalRByHistCommand {

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
	/**任意項目申請ID*/
	private String anyItemApplicationId;
	/**確認ルート種類*/
	private Integer confirmationRootType;
	/**就業ルート区分*/
	private int employmentRootAtr;
	/**check 申請承認の種類区分*/
	private int check;
	/**履歴から引き継ぐか、初めから作成するかを選択する*/
	private int copyOrNew;
}
