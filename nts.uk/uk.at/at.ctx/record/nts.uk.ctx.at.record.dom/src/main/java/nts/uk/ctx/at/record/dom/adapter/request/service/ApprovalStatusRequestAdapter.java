package nts.uk.ctx.at.record.dom.adapter.request.service;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface ApprovalStatusRequestAdapter {
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
	 * @return
	 */
	public List<RealityStatusEmployeeImport> getApprovalStatusEmployee(String wkpId, GeneralDate closureStart,
			GeneralDate closureEnd, List<String> listEmpCd);
}
