package nts.uk.ctx.hr.develop.dom.setting.datedisplay.service;

import nts.uk.ctx.hr.develop.dom.setting.datedisplay.dto.DateDisplaySettingPeriod;

/**
 * @author anhdt
 * 期間開始日、期間終了日の取得
 */
public interface IGetDatePeriod {
	DateDisplaySettingPeriod getDatePeriod(String companyId, String programId);
}
