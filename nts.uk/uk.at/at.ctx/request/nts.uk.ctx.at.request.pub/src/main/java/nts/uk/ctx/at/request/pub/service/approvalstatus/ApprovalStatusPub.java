package nts.uk.ctx.at.request.pub.service.approvalstatus;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * 
 * @author dat.lh
 *
 */
public interface ApprovalStatusPub {
	/**
	 * アルゴリズム「承認状況取得職場社員」を実行する
	 * 
	 * @param wkpId
	 *            職場ID
	 * @param closureStart
	 *            開始日
	 * @param closureEnd
	 *            終了日
	 * @param listEmpCd
	 *            雇用コード(リスト)
	 * @return 社員ID＜社員ID、期間＞(リスト)
	 */
	List<ApprovalStatusEmployeeExport> getApprovalStatusEmployee(String wkpId, GeneralDate closureStart,
			GeneralDate closureEnd, List<String> listEmpCd);
}
