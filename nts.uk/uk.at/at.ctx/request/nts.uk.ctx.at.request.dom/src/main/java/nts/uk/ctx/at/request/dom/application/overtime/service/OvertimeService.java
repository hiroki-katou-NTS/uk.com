package nts.uk.ctx.at.request.dom.application.overtime.service;

import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.overtimeinstruct.OverTimeInstruct;

public interface OvertimeService {
	/**
	 * 02_残業区分チェック 
	 * @param url
	 * @return
	 */
	public int checkOvertime(String url);
	/**
	 * 01-01_残業通知情報を取得
	 * @param appCommonSettingOutput
	 * @return
	 */
	public OverTimeInstruct getOvertimeInstruct(AppCommonSettingOutput appCommonSettingOutput,String appDate,String employeeID);
}
