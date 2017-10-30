package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
/**
 * 職場別就業承認ルート
 * @author hoatt
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class WorkplaceApprovalRoot extends AggregateRoot{
	/**会社ID*/
	private String companyId;
	/**承認ID*/
	public String approvalId;
	/**履歴ID*/
	private String workplaceId;
	/**履歴ID*/
	private String historyId;
	/**期間*/
	private ApprovalPeriod period;
	/**分岐ID*/
	private String branchId;
	/**任意項目申請ID*/
	private String anyItemApplicationId;
	/**確認ルート種類*/
	private ConfirmationRootType confirmationRootType;
	/**就業ルート区分*/
	private EmploymentRootAtr employmentRootAtr;
	/**申請種類*/
	private ApplicationType applicationType;
	
	public static WorkplaceApprovalRoot createSimpleFromJavaType(String companyId,
			String approvalId,
			String workplaceId,
			String historyId,
			Integer applicationType,
			String startDate,
			String endDate,
			String branchId,
			String anyItemApplicationId,
			Integer confirmationRootType,
			int employmentRootAtr){
		return new WorkplaceApprovalRoot(companyId,
			approvalId,
			workplaceId,
			historyId, 
			ApprovalPeriod.createSimpleFromJavaType(startDate, endDate),
			branchId,
			anyItemApplicationId,
			confirmationRootType == null ? null : EnumAdaptor.valueOf(confirmationRootType, ConfirmationRootType.class),
			EnumAdaptor.valueOf(employmentRootAtr, EmploymentRootAtr.class),
			applicationType == null ? null: EnumAdaptor.valueOf(applicationType, ApplicationType.class));
	}
	public static WorkplaceApprovalRoot convert(String companyId,
			String approvalId,
			String workplaceId,
			String historyId,
			Integer applicationType,
			GeneralDate startDate,
			GeneralDate endDate,
			String branchId,
			String anyItemApplicationId,
			Integer confirmationRootType,
			int employmentRootAtr){
		return new WorkplaceApprovalRoot(companyId,
			approvalId,
			workplaceId,
			historyId, 
			new ApprovalPeriod(startDate, endDate),
			branchId,
			anyItemApplicationId,
			confirmationRootType == null ? null : EnumAdaptor.valueOf(confirmationRootType, ConfirmationRootType.class),
			EnumAdaptor.valueOf(employmentRootAtr, EmploymentRootAtr.class),
			applicationType == null ? null: EnumAdaptor.valueOf(applicationType, ApplicationType.class));
	}
	public static WorkplaceApprovalRoot updateEdate(WorkplaceApprovalRoot wpApprovalRoot, String eDate){
		WorkplaceApprovalRoot wp = wpApprovalRoot;
		wp.period.updateEndate(eDate);
		return wp;
	}
	public static WorkplaceApprovalRoot updateSdate(WorkplaceApprovalRoot wpApprovalRoot, String sDate){
		WorkplaceApprovalRoot wp = wpApprovalRoot;
		wp.period.updateStrartDate(sDate);
		return wp;
	}
	public static boolean checkValidate(String startDate, String endDate){
		if(startDate.compareTo(endDate) <= 0){
			return true;
		}
		return false;
	}
}
