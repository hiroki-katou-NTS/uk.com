package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
/**
 * 職場別就業承認ルート
 * @author hoatt
 *
 */
@Getter
@AllArgsConstructor
public class WorkplaceApprovalRoot extends AggregateRoot{
	/**会社ID*/
	private String companyId;
	/**履歴ID*/
	private String workplaceId;
	/**履歴ID*/
	private String historyId;
	/**期間*/
	private ApprovalPeriod period;
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
	
	public static WorkplaceApprovalRoot createSimpleFromJavaType(String companyId,
			String workplaceId,
			String historyId,
			String startDate,
			String endDate,
			int branchId,
			String anyItemApplicationId,
			int confirmationRootType,
			int employmentRootAtr,
			int applicationType){
		return new WorkplaceApprovalRoot(companyId, 
			workplaceId,
			historyId, 
			ApprovalPeriod.createSimpleFromJavaType(startDate, endDate),
			branchId,
			anyItemApplicationId,
			EnumAdaptor.valueOf(confirmationRootType, ConfirmationRootType.class),
			EnumAdaptor.valueOf(employmentRootAtr, EmploymentRootAtr.class),
			EnumAdaptor.valueOf(applicationType, ApplicationType.class));
	}
}
