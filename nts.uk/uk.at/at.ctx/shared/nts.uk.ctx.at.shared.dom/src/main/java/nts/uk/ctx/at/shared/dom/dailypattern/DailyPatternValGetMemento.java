package nts.uk.ctx.at.shared.dom.dailypattern;

public interface DailyPatternValGetMemento {
	
	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	String getCompanyId();
	
	/**
	 * Gets the pattern code.
	 *
	 * @return the pattern code
	 */
	String getPatternCode();
	
	/**
	 * Gets the disp order.
	 *
	 * @return the disp order
	 */
	Integer getDispOrder();

	/**
	 * Gets the work type set cd.
	 *
	 * @return the work type set cd
	 */
	String getWorkTypeSetCd();
	
	/**
	 * Gets the working hours cd.
	 *
	 * @return the working hours cd
	 */
	String getWorkingHoursCd();
	
	/**
	 * Gets the days.
	 *
	 * @return the days
	 */
	Integer getDays();
}
