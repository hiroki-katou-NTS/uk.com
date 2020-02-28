package nts.uk.ctx.hr.develop.dom.setting.datedisplay.service;

import nts.arc.time.GeneralDate;

/**
 * @author anhdt
 * 日付の取得
 */
public interface IGetDate {
	GeneralDate getDate(String companyId, String programId);
}
