package nts.uk.ctx.workflow.pub.spr.export;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 個人別就業承認ルート
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApprovalPersonSprExport {
	/**会社ID*/
	private String companyId;
	/**承認ID*/
	public String approvalId;
	/**社員ID*/
	private String employeeId;
	/**申請種類*/
	private Integer applicationType;
//	/**分岐ID*/
//	private String branchId;
//	/**任意項目申請ID*/
//	private String anyItemApplicationId;
	/**確認ルート種類*/
	private Integer confirmationRootType;
	/**就業ルート区分*/
	private Integer employmentRootAtr;
}
