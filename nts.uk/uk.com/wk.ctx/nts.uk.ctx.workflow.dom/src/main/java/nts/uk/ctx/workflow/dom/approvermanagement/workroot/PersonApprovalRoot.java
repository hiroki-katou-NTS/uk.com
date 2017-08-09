package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
/**
 * 
 * @author hoatt
 *
 */
@Getter
@AllArgsConstructor
public class PersonApprovalRoot extends AggregateRoot{
	/*会社ID*/
	private String companyId;
	/*社員ID*/
	private String employeeId;
	/*履歴ID*/
	private String historyId;
	/*開始日*/
	private GeneralDate startDate;
	/*終了日*/
	private GeneralDate endDate;
	/*分岐番号*/
	private BranchNumber branchNumber;
	/*任意項目申請ID*/
	private String anyItemApplicationId;
	/*確認ルート種類*/
	private ConfirmationRootType confirmationRootType;
	/*就業ルート区分*/
	private EmploymentRootAtr employmentRootAtr;
	/*申請種類*/
	private ApplicationType applicationType;
	
	public static PersonApprovalRoot createSimpleFromJavaType(String companyId,
			String employeeId,
			String historyId,
			String startDate,
			String endDate,
			int branchNumber,
			String anyItemApplicationId,
			int confirmationRootType,
			int employmentRootAtr,
			int applicationType){
		return new PersonApprovalRoot(companyId, 
			employeeId,
			historyId, 
			GeneralDate.localDate(LocalDate.parse(startDate)),
			GeneralDate.localDate(LocalDate.parse(endDate)),
			EnumAdaptor.valueOf(branchNumber, BranchNumber.class),
			anyItemApplicationId,
			EnumAdaptor.valueOf(confirmationRootType, ConfirmationRootType.class),
			EnumAdaptor.valueOf(employmentRootAtr, EmploymentRootAtr.class),
			EnumAdaptor.valueOf(applicationType, ApplicationType.class));
	}
}
