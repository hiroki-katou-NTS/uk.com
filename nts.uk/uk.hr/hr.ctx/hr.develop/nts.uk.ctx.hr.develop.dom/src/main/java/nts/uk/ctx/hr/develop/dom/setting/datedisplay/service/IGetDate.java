package nts.uk.ctx.hr.develop.dom.setting.datedisplay.service;

import nts.arc.time.GeneralDate;

/**
 * @author anhdt
 *
 */
public interface IGetDate {
	GeneralDate getDate(String companyId, String programId);
}
