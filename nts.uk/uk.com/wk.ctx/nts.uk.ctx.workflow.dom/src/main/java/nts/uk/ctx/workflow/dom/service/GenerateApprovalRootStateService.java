package nts.uk.ctx.workflow.dom.service;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.service.output.ApprovalRootContentOutput;
/**
 * 承認ルートインスタンスを生成する
 * @author Doan Duy Hung
 *
 */
public interface GenerateApprovalRootStateService {
	
	/**
	 * 承認ルートインスタンスを生成する
	 * @param companyID 会社ID
	 * @param employeeID 社員ID
	 * @param employmentRootAtr 就業ルート区分
	 * @param appType 対象申請
	 * @param date 基準日
	 * @return
	 */
	public ApprovalRootContentOutput getApprovalRootState(String companyID, String employeeID, ApplicationType appType, GeneralDate date);
	
}
