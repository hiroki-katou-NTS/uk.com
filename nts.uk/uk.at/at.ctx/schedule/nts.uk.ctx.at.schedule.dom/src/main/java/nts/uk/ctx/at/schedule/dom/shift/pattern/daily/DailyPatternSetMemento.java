package nts.uk.ctx.at.schedule.dom.shift.pattern.daily;

import java.util.List;

import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface DailyPatternSetMemento.
 */
public interface DailyPatternSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param setCompanyId the new company id
	 */
	void  setCompanyId(CompanyId setCompanyId);

	/**
	 * Sets the pattern code.
	 *
	 * @param setPatternCode the new pattern code
	 */
	void  setPatternCode(PatternCode setPatternCode);

	/**
	 * Sets the pattern name.
	 *
	 * @param setPatternName the new pattern name
	 */
	void  setPatternName(PatternName setPatternName);

	/**
	 * Sets the list daily pattern val.
	 *
	 * @param setListDailyPatternVal the new list daily pattern val
	 */
	void  setListDailyPatternVal(List<DailyPatternVal> setListDailyPatternVal);

}
