package nts.uk.pub.spr.approvalroot.output;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 会社別就業承認ルート
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApprovalComSpr {
	/**会社ID*/
	private String companyId;
	/**承認ID*/
	public String approvalId;
	/**申請種類*/
	private Integer applicationType;
	/**分岐ID*/
	private String branchId;
	/**任意項目申請ID*/
	private String anyItemApplicationId;
	/**確認ルート種類*/
	private Integer confirmationRootType;
	/**就業ルート区分*/
	private Integer employmentRootAtr;
}
