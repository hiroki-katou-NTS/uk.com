package nts.uk.ctx.at.request.dom.application.common.datawork;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;

public interface IDataWorkService {
	/**
	 * 勤務就業ダイアログ用データ取得
	 * @param companyId
	 * @param sId
	 * @param appDate
	 * @param appCommonSetting
	 * @param appType 
	 * @return DataWork
	 */
	DataWork getDataWork(String companyId, String sId, GeneralDate appDate, AppCommonSettingOutput appCommonSetting, int appType);
}
