package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
/**
 * 会社別就業承認ルート
 * @author hoatt
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class CompanyApprovalRoot extends AggregateRoot {
	/**会社ID*/
	private String companyId;
	/**承認ID*/
	public String approvalId;
	/**履歴ID*/
	private String historyId;
	/**申請種類*/
	private ApplicationType applicationType;
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
	
	public static CompanyApprovalRoot createSimpleFromJavaType(String companyId,
			String approvalId,
			String historyId,
			Integer applicationType,
			String startDate,
			String endDate,
			String branchId,
			String anyItemApplicationId,
			Integer confirmationRootType,
			int employmentRootAtr){
		return new CompanyApprovalRoot(companyId,
			approvalId,
			historyId,
			applicationType == null ? null : EnumAdaptor.valueOf(applicationType, ApplicationType.class), 
			ApprovalPeriod.createSimpleFromJavaType(startDate, endDate),
			branchId,
			anyItemApplicationId,
			confirmationRootType == null ? null : EnumAdaptor.valueOf(confirmationRootType, ConfirmationRootType.class),
			EnumAdaptor.valueOf(employmentRootAtr, EmploymentRootAtr.class));
	}
	public static CompanyApprovalRoot convert(String companyId,
			String approvalId,
			String historyId,
			Integer applicationType,
			GeneralDate startDate,
			GeneralDate endDate,
			String branchId,
			String anyItemApplicationId,
			Integer confirmationRootType,
			int employmentRootAtr){
		return new CompanyApprovalRoot(companyId,
			approvalId,
			historyId,
			applicationType == null ? null : EnumAdaptor.valueOf(applicationType, ApplicationType.class), 
			new ApprovalPeriod(startDate, endDate),
			branchId,
			anyItemApplicationId,
			confirmationRootType == null ? null : EnumAdaptor.valueOf(confirmationRootType, ConfirmationRootType.class),
			EnumAdaptor.valueOf(employmentRootAtr, EmploymentRootAtr.class));
	}
	public static CompanyApprovalRoot updateEdate(CompanyApprovalRoot comApprovalRoot, String eDate){
		CompanyApprovalRoot com = comApprovalRoot;
		com.period.updateEndate(eDate);
		return com;
	}
	public static CompanyApprovalRoot updateSdate(CompanyApprovalRoot comApprovalRoot, String startDate){
		CompanyApprovalRoot com = comApprovalRoot;
		com.period.updateStrartDate(startDate);
		return com;
	}
}
