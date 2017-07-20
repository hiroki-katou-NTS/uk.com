package nts.uk.ctx.at.shared.dom.dailypattern;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Interface PatternCalendarSetMemento.
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
	 * Sets the work type codes.
	 *
	 * @param setWorkTypeCodes the new work type codes
	 */
	void  setWorkTypeCodes(List<String> setWorkTypeCodes);
	
	/**
	 * Sets the work house codes.
	 *
	 * @param setWorkHouseCodes the new work house codes
	 */
	void  setWorkHouseCodes(List<String> setWorkHouseCodes);
	
	/**
	 * Sets the calendar setting.
	 *
	 * @param setCalendarSetting the new calendar setting
	 */
	void  setCalendarSetting(DailyPatternSetting setCalendarSetting);
}
