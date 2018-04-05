package nts.uk.ctx.at.record.dom.realitystatus.service;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.request.service.RealityStatusEmployeeImport;
import nts.uk.ctx.at.record.dom.realitystatus.service.output.EmpUnconfirmResultOutput;
import nts.uk.ctx.at.record.dom.realitystatus.service.output.SumCountData;
import nts.uk.ctx.at.record.dom.realitystatus.service.output.UseSetingData;

public interface RealityStatusService {
	UseSetingData getUseSetting();

	SumCountData getApprovalSttConfirmWkpResults(List<RealityStatusEmployeeImport> listEmp, String wkpId,
			UseSetingData useSeting);

	/**
	 * アルゴリズム「承認状況未確認メール送信社員取得」を実行する
	 * 
	 * @param type
	 *            送信区分（本人/日次/月次）
	 * @param listWkp
	 * @return
	 */
	EmpUnconfirmResultOutput getListEmpUnconfirm(TransmissionAttr type, List<WkpIdMailCheck> listWkp,
			GeneralDate closureStart, GeneralDate closureEnd, List<String> listEmpCd);
}
