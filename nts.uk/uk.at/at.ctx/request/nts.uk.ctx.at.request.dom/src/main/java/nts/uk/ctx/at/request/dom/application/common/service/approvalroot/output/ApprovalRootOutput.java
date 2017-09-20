package nts.uk.ctx.at.request.dom.application.common.service.approvalroot.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ComApprovalRootImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.PersonApprovalRootImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.WkpApprovalRootImport;

@Data
public class ApprovalRootOutput {
	/** 会社ID */
	private String companyId;
	/** 職場ID */
	private String workplaceId;
	/** 承認ID */
	public String approvalId;
	/** 社員ID */
	private String employeeId;
	/** 履歴ID */
	private String historyId;
	/** 申請種類 */
	private Integer applicationType;
	/** 開始日 */
	private GeneralDate startDate;
	/** 完了日 */
	private GeneralDate endDate;
	/** 分岐ID */
	private String branchId;
	/** 任意項目申請ID */
	private String anyItemApplicationId;
	/** 確認ルート種類 */
	private Integer confirmationRootType;
	/** 就業ルート区分 */
	private int employmentRootAtr;
	
	private List<ApprovalPhaseImport> beforeApprovers;
	
	private List<ApprovalPhaseOutput> afterApprovers;
	
	/** エラーフラグ*/
	private ErrorFlag errorFlag;
	
	public ApprovalRootOutput(String companyId, String workplaceId, String approvalId, String employeeId,
			String historyId, Integer applicationType, GeneralDate startDate, GeneralDate endDate, String branchId,
			String anyItemApplicationId, Integer confirmationRootType, int employmentRootAtr,
			List<ApprovalPhaseImport> beforeApprovers, List<ApprovalPhaseOutput> afterApprovers) {
		super();
		this.companyId = companyId;
		this.workplaceId = workplaceId;
		this.approvalId = approvalId;
		this.employeeId = employeeId;
		this.historyId = historyId;
		this.applicationType = applicationType;
		this.startDate = startDate;
		this.endDate = endDate;
		this.branchId = branchId;
		this.anyItemApplicationId = anyItemApplicationId;
		this.confirmationRootType = confirmationRootType;
		this.employmentRootAtr = employmentRootAtr;
		this.beforeApprovers = beforeApprovers;
		this.afterApprovers = afterApprovers;
	}
	
	public static ApprovalRootOutput convertFromPersonData(PersonApprovalRootImport x) {
		return new ApprovalRootOutput(
				x.getCompanyId(),
				null, 
				x.getApprovalId(), 
				x.getEmployeeId(), 
				x.getHistoryId(), 
				x.getApplicationType(), 
				x.getStartDate(), 
				x.getEndDate(), 
				x.getBranchId(), 
				x.getAnyItemApplicationId(), 
				x.getConfirmationRootType(), 
				x.getEmploymentRootAtr(), 
				null,
				null);
	}
	
	public static ApprovalRootOutput convertFromWkpData(WkpApprovalRootImport x) {
		return new ApprovalRootOutput(
				x.getCompanyId(),
				x.getWorkplaceId(), 
				x.getApprovalId(), 
				null, 
				x.getHistoryId(), 
				x.getApplicationType(), 
				x.getStartDate(), 
				x.getEndDate(), 
				x.getBranchId(), 
				x.getAnyItemApplicationId(), 
				x.getConfirmationRootType(), 
				x.getEmploymentRootAtr(), 
				null,
				null);
	}
	
	public static ApprovalRootOutput convertFromCompanyData(ComApprovalRootImport x) {
		return new ApprovalRootOutput(
				x.getCompanyId(),
				null, 
				x.getApprovalId(), 
				null, 
				x.getHistoryId(), 
				x.getApplicationType(), 
				x.getStartDate(), 
				x.getEndDate(), 
				x.getBranchId(), 
				x.getAnyItemApplicationId(), 
				x.getConfirmationRootType(), 
				x.getEmploymentRootAtr(), 
				null,
				null);
	}
}
