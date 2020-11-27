package nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Getter
@NoArgsConstructor
public class ApprovalRootImport {
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
//	/** 分岐ID */
//	private String branchId;
//	/** 任意項目申請ID */
//	private String anyItemApplicationId;
	/** 確認ルート種類 */
	private Integer confirmationRootType;
	/** 就業ルート区分 */
	private int employmentRootAtr;

	private List<ApprovalPhaseImport> beforeApprovers;

	private List<ApprovalPhaseImport> afterApprovers;

	/** エラーフラグ */
	private ErrorFlagImport errorFlag;

//	public ApprovalRootImport(String companyId, String workplaceId, String approvalId, String employeeId,
//			String historyId, GeneralDate startDate, GeneralDate endDate, String branchId,
//			String anyItemApplicationId) {
//		super();
//		this.companyId = companyId;
//		this.workplaceId = workplaceId;
//		this.approvalId = approvalId;
//		this.employeeId = employeeId;
//		this.historyId = historyId;
//		this.startDate = startDate;
//		this.endDate = endDate;
//		this.branchId = branchId;
//		this.anyItemApplicationId = anyItemApplicationId;
//	}

	public void addDataType(Integer applicationType, Integer confirmationRootType, int employmentRootAtr) {
		this.applicationType = applicationType;
		this.confirmationRootType = confirmationRootType;
		this.employmentRootAtr = employmentRootAtr;
	}

	public void addBeforeApprovers(List<ApprovalPhaseImport> beforeApprovers) {
		this.beforeApprovers = beforeApprovers;
	}

	public void addAfterApprovers(List<ApprovalPhaseImport> afterApprovers) {
		this.afterApprovers = afterApprovers;
	}

	public ApprovalRootImport(String companyId, String workplaceId, String approvalId, String employeeId,
			String historyId, Integer applicationType, GeneralDate startDate, GeneralDate endDate, 
			// String branchId,
			// String anyItemApplicationId, 
			Integer confirmationRootType, int employmentRootAtr,
			List<ApprovalPhaseImport> beforeApprovers, List<ApprovalPhaseImport> afterApprovers,
			ErrorFlagImport errorFlag) {
		super();
		this.companyId = companyId;
		this.workplaceId = workplaceId;
		this.approvalId = approvalId;
		this.employeeId = employeeId;
		this.historyId = historyId;
		this.applicationType = applicationType;
		this.startDate = startDate;
		this.endDate = endDate;
		// this.branchId = branchId;
		// this.anyItemApplicationId = anyItemApplicationId;
		this.confirmationRootType = confirmationRootType;
		this.employmentRootAtr = employmentRootAtr;
		this.beforeApprovers = beforeApprovers;
		this.afterApprovers = afterApprovers;
		this.errorFlag = errorFlag;
	}
}
