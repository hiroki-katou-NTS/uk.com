package nts.uk.ctx.at.request.dom.application.holidayworktime.service;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.AppHdWorkDispInfoOutput;

public interface HolidayWorkRegisterService {

	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF010_休日出勤時間申請.アルゴリズム.2.休出申請（新規）登録処理.2.休出申請（新規）登録処理
	 * @param companyId
	 * @param appHolidayWork
	 * @param appHdWorkDispInfoOutput
	 * @param lstApproval
	 * @return
	 */
	public ProcessResult register(
			String companyId,
			AppHolidayWork appHolidayWork,
			AppHdWorkDispInfoOutput appHdWorkDispInfoOutput,
			List<ApprovalPhaseStateImport_New> lstApproval
			);
}
