package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
/**
 * 個人別就業承認ルート
 * @author hoatt
 *
 */
@Getter
@AllArgsConstructor
public class PersonApprovalRoot extends AggregateRoot{
	/**会社ID*/
	private String companyId;
	/**社員ID*/
	private String employeeId;
	/**履歴ID*/
	private String historyId;
	/**申請種類*/
	private ApplicationType applicationType;
	/**開始日*/
	private GeneralDate startDate;
	/**終了日*/
	private GeneralDate endDate;
	/**分岐ID*/
	private String branchId;
	/**任意項目申請ID*/
	private String anyItemApplicationId;
	/**確認ルート種類*/
	private ConfirmationRootType confirmationRootType;
	/**就業ルート区分*/
	private EmploymentRootAtr employmentRootAtr;
	
	public static PersonApprovalRoot createSimpleFromJavaType(String companyId,
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
			employeeId,
			historyId,
			EnumAdaptor.valueOf(applicationType, ApplicationType.class), 
			GeneralDate.localDate(LocalDate.parse(startDate)),
			GeneralDate.localDate(LocalDate.parse(endDate)),
			branchId,
			anyItemApplicationId,
			EnumAdaptor.valueOf(confirmationRootType, ConfirmationRootType.class),
			EnumAdaptor.valueOf(employmentRootAtr, EmploymentRootAtr.class));
	}
}
