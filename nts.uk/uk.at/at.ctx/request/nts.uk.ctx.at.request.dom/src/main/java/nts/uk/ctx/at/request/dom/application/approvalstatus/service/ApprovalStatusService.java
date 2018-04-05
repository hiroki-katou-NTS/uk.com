package nts.uk.ctx.at.request.dom.application.approvalstatus.service;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprovalStatusEmployeeOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprovalSttAppOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.EmployeeEmailOutput;

public interface ApprovalStatusService {
	/**
	 * アルゴリズム「承認状況取得社員」を実行する
	 * 
	 * @param wkpId
	 *            職場ID
	 * @param closurePeriod
	 *            期間(開始日～終了日)
	 * @param listEmpCd
	 *            雇用コード(リスト)
	 * @return 社員ID＜社員ID、期間＞(リスト)
	 */
	List<ApprovalStatusEmployeeOutput> getApprovalStatusEmployee(String wkpId, GeneralDate closureStart,
			GeneralDate closureEnd, List<String> listEmpCd);
	
	/**
	 * アルゴリズム「承認状況取得申請承認」を実行する
	 * @param wkpInfoDto
	 * @return ApprovalSttAppDto
	 */
	ApprovalSttAppOutput getApprovalSttApp(String wkpId, List<ApprovalStatusEmployeeOutput> listAppStatusEmp);
	
	/**
	 * アルゴリズム「承認状況社員メールアドレス取得」を実行する RequestList #126
	 * 
	 * @return 取得社員ID＜社員ID、社員名、メールアドレス＞
	 */
	EmployeeEmailOutput findEmpMailAddr();
}
