package nts.uk.ctx.at.request.dom.application.holidayworktime.service;

import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.HolidayWorkInstruction;

public interface HolidayPreProcess {
	/**
	 * 01-01_休出通知情報を取得
	 * @param appCommonSettingOutput
	 * @param appDate
	 * @param employeeID
	 * @return
	 */
	public HolidayWorkInstruction getHolidayInstructionInformation(AppCommonSettingOutput appCommonSettingOutput,String appDate,String employeeID);
}
