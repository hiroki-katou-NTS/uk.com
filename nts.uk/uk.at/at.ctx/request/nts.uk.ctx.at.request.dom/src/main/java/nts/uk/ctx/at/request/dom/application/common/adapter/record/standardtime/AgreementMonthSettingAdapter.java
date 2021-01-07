package nts.uk.ctx.at.request.dom.application.common.adapter.record.standardtime;

import nts.arc.time.YearMonth;
/**
 * 
 * @author hoangnd
 *
 */
public interface AgreementMonthSettingAdapter {
	/**
	 * Refactor5
	 * rql708
	 * @param employeeId
	 * @param yearMonth
	 * @return
	 */
	AgreementMonthSettingOutput getData(String employeeId, YearMonth yearMonth);
}
