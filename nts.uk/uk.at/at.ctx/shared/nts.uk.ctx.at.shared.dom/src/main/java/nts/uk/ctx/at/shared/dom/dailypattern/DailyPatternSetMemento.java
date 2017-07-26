package nts.uk.ctx.at.shared.dom.dailypattern;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Interface DailyPatternSetMemento.
 */
public interface DailyPatternSetMemento {
	
	/**
	 * Sets the company id.
	 *
	 * @param setCompanyId the new company id
	 */
	void  setCompanyId(String setCompanyId);
	
	/**
	 * Sets the pattern code.
	 *
	 * @param setPatternCode the new pattern code
	 */
	void  setPatternCode(String setPatternCode);
	
	/**
	 * Sets the pattern name.
	 *
	 * @param setPatternName the new pattern name
	 */
	void  setPatternName(String setPatternName);
	
	/**
	 * Sets the list daily pattern val.
	 *
	 * @param setListDailyPatternVal the new list daily pattern val
	 */
	void  setListDailyPatternVal(List<DailyPatternVal> setListDailyPatternVal);
	
	
}
