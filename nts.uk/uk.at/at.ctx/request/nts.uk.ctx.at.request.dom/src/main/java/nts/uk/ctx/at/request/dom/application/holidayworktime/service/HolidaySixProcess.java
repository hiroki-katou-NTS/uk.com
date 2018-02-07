package nts.uk.ctx.at.request.dom.application.holidayworktime.service;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.common.adapter.frame.OvertimeInputCaculation;
import nts.uk.ctx.at.request.dom.application.overtime.service.CaculationTime;
import nts.uk.ctx.at.request.dom.setting.workplace.ApprovalFunctionSetting;

public interface HolidaySixProcess {
	
	/**
	 * 06-01_色表示チェック
	 * @param breakTimeInputs
	 * @param overtimeInputCaculations
	 * @param prePostAtr
	 * @param inputDate
	 * @param appDate
	 * @param appType
	 * @param employeeID
	 * @param companyID
	 * @param approvalFunctionSetting
	 * @param siftCD
	 * @return
	 */
	public List<CaculationTime> checkDisplayColor(List<CaculationTime> breakTimeInputs,
			List<OvertimeInputCaculation> overtimeInputCaculations,int prePostAtr,GeneralDateTime inputDate,GeneralDate appDate,int appType,String employeeID,String companyID,ApprovalFunctionSetting approvalFunctionSetting,String siftCD);
}
