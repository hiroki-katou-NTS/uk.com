package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRoot;
@Data
@AllArgsConstructor
public class WpApprovalRootDto {
	/**会社ID*/
	private String companyId;
	/**承認ID*/
	public String approvalId;
	/**履歴ID*/
	private String workplaceId;
	/**履歴ID*/
	private String historyId;
	/**申請種類*/
	private Integer applicationType;
	/**開始日*/
	private String startDate;
	/**終了日*/
	private String endDate;
//	/**分岐ID*/
//	private String branchId;
//	/**任意項目申請ID*/
//	private String anyItemApplicationId;
	/**確認ルート種類*/
	private Integer confirmationRootType;
	/**就業ルート区分*/
	private int employmentRootAtr;
	/**届出ID*/
	private Integer noticeId;
	/**各業務エベントID*/
	private String busEventId;
	
	public static WpApprovalRootDto fromDomain(WorkplaceApprovalRoot domain){
		return new WpApprovalRootDto(domain.getCompanyId(),
					domain.getApprovalId(),
					domain.getWorkplaceId(),
					domain.getApprRoot().getHistoryItems().get(0).getHistoryId(),
					domain.getApprRoot().getApplicationType() == null ? null : domain.getApprRoot().getApplicationType().value,
					domain.getApprRoot().getHistoryItems().get(0).start().toString("yyyy/MM/dd"),
					domain.getApprRoot().getHistoryItems().get(0).end().toString("yyyy/MM/dd"),
					// domain.getApprRoot().getBranchId(),
					// domain.getApprRoot().getAnyItemApplicationId(),
					domain.getApprRoot().getConfirmationRootType() == null ? null : domain.getApprRoot().getConfirmationRootType().value,
					domain.getApprRoot().getEmploymentRootAtr().value,
					domain.getApprRoot().getNoticeId(),
					domain.getApprRoot().getBusEventId());
	}
}
