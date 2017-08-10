package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
/**
 * 会社別就業承認ルート
 * @author hoatt
 *
 */
@Getter
@AllArgsConstructor
public class CompanyApprovalRoot extends AggregateRoot {
	/**会社ID*/
	private String companyId;
	/**履歴ID*/
	private String historyId;
	/**開始日*/
	private GeneralDate startDate;
	/**終了日*/
	private GeneralDate endDate;
	/**分岐ID*/
	private int branchId;
	/**任意項目申請ID*/
	private String anyItemApplicationId;
	/**確認ルート種類*/
	private ConfirmationRootType confirmationRootType;
	/**就業ルート区分*/
	private EmploymentRootAtr employmentRootAtr;
	/**申請種類*/
	private ApplicationType applicationType;
	
	public static CompanyApprovalRoot createSimpleFromJavaType(String companyId,
			String historyId,
			String startDate,
			String endDate,
			int branchId,
			String anyItemApplicationId,
			int confirmationRootType,
			int employmentRootAtr,
			int applicationType){
		return new CompanyApprovalRoot(companyId, 
			historyId, 
			GeneralDate.localDate(LocalDate.parse(startDate)),
			GeneralDate.localDate(LocalDate.parse(endDate)),
			branchId,
			anyItemApplicationId,
			EnumAdaptor.valueOf(confirmationRootType, ConfirmationRootType.class),
			EnumAdaptor.valueOf(employmentRootAtr, EmploymentRootAtr.class),
			EnumAdaptor.valueOf(applicationType, ApplicationType.class));
	}
}
