package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
/**
 * 個人別就業承認ルート
 * @author hoatt
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class PersonApprovalRoot extends AggregateRoot{
	/**会社ID*/
	private String companyId;
	/**承認ID*/
	public String approvalId;
	/**社員ID*/
	private String employeeId;
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
	
	public static PersonApprovalRoot createSimpleFromJavaType(String companyId,
			String approvalId,
			String employeeId,
			String historyId,
			int applicationType,
			String startDate,
			String endDate,
			String branchId,
			String anyItemApplicationId,
			int confirmationRootType,
			int employmentRootAtr){
		return new PersonApprovalRoot(companyId,
			approvalId,
			employeeId,
			historyId,
			EnumAdaptor.valueOf(applicationType, ApplicationType.class), 
			ApprovalPeriod.createSimpleFromJavaType(startDate, endDate),
			branchId,
			anyItemApplicationId,
			EnumAdaptor.valueOf(confirmationRootType, ConfirmationRootType.class),
			EnumAdaptor.valueOf(employmentRootAtr, EmploymentRootAtr.class));
	}
	public static PersonApprovalRoot updateSdateEdate(PersonApprovalRoot psApprovalRoot, String sDate, String eDate){
		PersonApprovalRoot ps = psApprovalRoot;
		ApprovalPeriod period = ApprovalPeriod.createSimpleFromJavaType(sDate, eDate);
		ps.setPeriod(period);
		return ps;
	}
}
