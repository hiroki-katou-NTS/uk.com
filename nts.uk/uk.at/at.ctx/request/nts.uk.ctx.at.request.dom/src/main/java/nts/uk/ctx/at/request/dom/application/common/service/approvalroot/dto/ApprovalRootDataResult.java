package nts.uk.ctx.at.request.dom.application.common.service.approvalroot.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.CompanyAppRootAdaptorDto;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.PersonAppRootAdaptorDto;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.WkpAppRootAdaptorDto;

@Data
@AllArgsConstructor
public class ApprovalRootDataResult {
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
	private int applicationType;
	/** 開始日 */
	private GeneralDate startDate;
	/** 完了日 */
	private GeneralDate endDate;
	/** 分岐ID */
	private String branchId;
	/** 任意項目申請ID */
	private String anyItemApplicationId;
	/** 確認ルート種類 */
	private int confirmationRootType;
	/** 就業ルート区分 */
	private int employmentRootAtr;
	
	private List<ApprovalPhaseAdaptorResult> approvers;
	
	public static ApprovalRootDataResult convertFromPersonData(PersonAppRootAdaptorDto x) {
		return new ApprovalRootDataResult(
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
				null);
	}
	
	public static ApprovalRootDataResult convertFromWkpData(WkpAppRootAdaptorDto x) {
		return new ApprovalRootDataResult(
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
				null);
	}
	
	public static ApprovalRootDataResult convertFromCompanyData(CompanyAppRootAdaptorDto x) {
		return new ApprovalRootDataResult(
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
				null);
	}
}
