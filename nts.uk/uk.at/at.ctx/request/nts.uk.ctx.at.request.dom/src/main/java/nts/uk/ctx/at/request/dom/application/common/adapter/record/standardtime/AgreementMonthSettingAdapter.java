package nts.uk.ctx.at.request.dom.application.common.adapter.record.standardtime;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement.AgreementExcessInfoImport;
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
	
	/**
	 * refactor 5
	 * request list 605
	 * @param employeeID
	 * @param ym
	 * @return
	 */
	AgreementExcessInfoImport getDataRq605(String employeeID, YearMonth ym);
}
