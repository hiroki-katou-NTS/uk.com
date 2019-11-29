package nts.uk.ctx.hr.develop.dom.setting.datedisplay.service;

import nts.uk.ctx.hr.develop.dom.setting.datedisplay.dto.DateDisplaySettingPeriod;

/**
 * @author anhdt
 *
 */
public interface IGetDatePeriod {
	DateDisplaySettingPeriod getDatePeriod(String companyId, String programId);
}
