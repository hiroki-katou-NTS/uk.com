package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output;

import lombok.Data;
import nts.arc.time.GeneralDate;
@Data
public class ApprovalRootCommonOutput {
	/**会社ID*/
	private String companyId;
	/**承認ID*/
	public String approvalId;
	/**社員ID*/
	private String employeeId;
	/**職場ID*/
	private String workpalceId;
	/**履歴ID*/
	private String historyId;
	/**申請種類*/
	private Integer appType;
	/**開始日*/
	private GeneralDate startDate;
	/**完了日*/
	private GeneralDate endDate;
//	/**分岐ID*/
//	private String branchId;
//	/**任意項目申請ID*/
//	private String anyItemId;
	/**確認ルート種類*/
	private Integer confType;
	/**就業ルート区分*/
	private int employmentRootAtr;
	
	private Integer noticeId;
	
	private String eventId;
	
	public ApprovalRootCommonOutput(String companyId, String approvalId, String employeeId, String workpalceId,
			String historyId, Integer appType, GeneralDate startDate, GeneralDate endDate, 
			// String branchId,
			// String anyItemId,
			Integer confType, int employmentRootAtr, Integer noticeId, String eventId) {
		super();
		this.companyId = companyId;
		this.approvalId = approvalId;
		this.employeeId = employeeId;
		this.workpalceId = workpalceId;
		this.historyId = historyId;
		this.appType = appType;
		this.startDate = startDate;
		this.endDate = endDate;
		// this.branchId = branchId;
		// this.anyItemId = anyItemId;
		this.confType = confType;
		this.employmentRootAtr = employmentRootAtr;
		this.noticeId = noticeId;
		this.eventId = eventId;
	}
	
}
