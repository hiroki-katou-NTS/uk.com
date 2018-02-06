package nts.uk.ctx.at.request.dom.application.holidayworktime.service;

import java.util.Optional;

import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.HolidayWorkInstruction;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetting;

public interface HolidayPreProcess {
	/**
	 * 01-01_休出通知情報を取得
	 * @param appCommonSettingOutput
	 * @param appDate
	 * @param employeeID
	 * @return
	 */
	public HolidayWorkInstruction getHolidayInstructionInformation(AppCommonSettingOutput appCommonSettingOutput,String appDate,String employeeID);
	/**
	 * 01-09_事前申請を取得
	 * @param companyID
	 * @param employeeId
	 * @param overtimeRestAppCommonSet
	 * @param appDate
	 * @param prePostAtr
	 * @return
	 */
	public AppHolidayWork getPreApplicationHoliday(String companyID, String employeeId, Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSet,String appDate, int prePostAtr);
}
