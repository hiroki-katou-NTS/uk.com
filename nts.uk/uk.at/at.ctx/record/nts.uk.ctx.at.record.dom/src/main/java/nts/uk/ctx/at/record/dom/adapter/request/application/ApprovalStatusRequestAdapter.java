package nts.uk.ctx.at.record.dom.adapter.request.application;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.request.application.dto.ApprovalStatusMailTempImport;
import nts.uk.ctx.at.record.dom.adapter.request.application.dto.EmployeeUnconfirmImport;
import nts.uk.ctx.at.record.dom.adapter.request.application.dto.RealityStatusEmployeeImport;

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

	public ApprovalStatusMailTempImport getApprovalStatusMailTemp(int type);

	public List<EmployeeUnconfirmImport> getListEmployeeEmail(List<String> listSId);
}
