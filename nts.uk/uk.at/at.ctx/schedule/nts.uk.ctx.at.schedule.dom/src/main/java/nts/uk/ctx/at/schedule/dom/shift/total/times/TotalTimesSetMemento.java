package nts.uk.ctx.at.schedule.dom.shift.total.times;

import nts.uk.ctx.at.schedule.dom.shift.total.times.setting.CountAtr;
import nts.uk.ctx.at.schedule.dom.shift.total.times.setting.SummaryAtr;
import nts.uk.ctx.at.schedule.dom.shift.total.times.setting.TotalTimesABName;
import nts.uk.ctx.at.schedule.dom.shift.total.times.setting.TotalTimesName;
import nts.uk.ctx.at.schedule.dom.shift.total.times.setting.UseAtr;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface TotalTimesSetMemento.
 */
public interface TotalTimesSetMemento {
	
	/**
	 * Sets the company id.
	 *
	 * @param setCompanyId the new company id
	 */
	void  setCompanyId(CompanyId setCompanyId);
	
	/**
	 * Sets the total count no.
	 *
	 * @param setTotalCountNo the new total count no
	 */
	void  setTotalCountNo(Integer setTotalCountNo);
	
	/**
	 * Sets the count atr.
	 *
	 * @param setCountAtr the new count atr
	 */
	void  setCountAtr(CountAtr setCountAtr);
	
	/**
	 * Sets the use atr.
	 *
	 * @param setUseAtr the new use atr
	 */
	void  setUseAtr(UseAtr setUseAtr);
	
	/**
	 * Sets the total times name.
	 *
	 * @param setTotalTimesName the new total times name
	 */
	void  setTotalTimesName(TotalTimesName setTotalTimesName);
	
	/**
	 * Sets the total times AB name.
	 *
	 * @param setTotalTimesABName the new total times AB name
	 */
	void  setTotalTimesABName(TotalTimesABName setTotalTimesABName);
	
	/**
	 * Sets the summary atr.
	 *
	 * @param setSummaryAtr the new summary atr
	 */
	void  setSummaryAtr(SummaryAtr setSummaryAtr);
	
	/**
	 * Sets the total condition.
	 *
	 * @param setTotalCondition the new total condition
	 */
	void  setTotalCondition(TotalCondition setTotalCondition);
	
	/**
	 * Sets the summary list.
	 *
	 * @param setSummaryList the new summary list
	 */
	void  setTotalSubjects(TotalSubjects setSummaryList);
	
}
