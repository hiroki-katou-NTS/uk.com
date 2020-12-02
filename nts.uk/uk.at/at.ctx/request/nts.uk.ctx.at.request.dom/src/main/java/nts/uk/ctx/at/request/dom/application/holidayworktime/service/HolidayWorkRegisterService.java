package nts.uk.ctx.at.request.dom.application.holidayworktime.service;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.AppHdWorkDispInfoOutput;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppTypeSetting;

/**
 * Refactor5
 * @author huylq
 *
 */
public interface HolidayWorkRegisterService {

	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF010_休日出勤時間申請.アルゴリズム.2.休出申請（新規）登録処理.2.休出申請（新規）登録処理
	 * @param companyId
	 * @param appHolidayWork
	 * @param appTypeSetting
	 * @param appHdWorkDispInfoOutput
	 * @param lstApproval
	 * @return
	 */
	public ProcessResult register(
			String companyId,
			AppHolidayWork appHolidayWork,
			AppTypeSetting appTypeSetting, 
			AppHdWorkDispInfoOutput appHdWorkDispInfoOutput,
			List<ApprovalPhaseStateImport_New> lstApproval
			);

	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF010_休日出勤時間申請.アルゴリズム.8.休出申請（詳細）登録処理.8.休出申請（詳細）登録処理
	 * @param companyId
	 * @param appHolidayWork
	 * @return
	 */
	public ProcessResult update(String companyId, AppHolidayWork appHolidayWork);
}
