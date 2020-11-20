package nts.uk.ctx.at.request.dom.application.overtime.service;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppTypeSetting;

public interface OverTimeRegisterService {
	/**
	 * Refactor5 残業申請の新規登録
	 * UKDesign.UniversalK.就業.KAF_申請.KAF005_残業申請.A：残業申請（新規登録）.アルゴリズム.残業申請の新規登録
	 * @param companyId
	 * @param appOverTime
	 * @param lstApproval
	 * @param mailServerSet
	 */
	public ProcessResult register(
			String companyId,
			AppOverTime appOverTime,
			List<ApprovalPhaseStateImport_New> lstApproval,
			Boolean mailServerSet,
			AppTypeSetting appTypeSetting);
	/**
	 * Refactor5 02_更新登録
	 * UKDesign.UniversalK.就業.KAF_申請.KAF005_残業申請.B：残業申請（詳細・照会）.アルゴリズム.02_更新登録
	 * @param companyId
	 * @param appOverTime
	 */
	public ProcessResult update(
			String companyId,
			AppOverTime appOverTime
			);
}
