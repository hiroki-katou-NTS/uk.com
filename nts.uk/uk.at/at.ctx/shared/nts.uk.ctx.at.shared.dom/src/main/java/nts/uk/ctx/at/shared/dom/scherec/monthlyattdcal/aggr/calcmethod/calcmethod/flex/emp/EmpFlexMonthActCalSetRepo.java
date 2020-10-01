package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.emp;

import java.util.Optional;

/**
 * The Interface EmpFlexMonthActCalSetRepository.
 */
public interface EmpFlexMonthActCalSetRepo {

	/**
	 * Find by cid and empl code.
	 *
	 * @param cid the cid
	 * @param empCode the empl code
	 * @return the optional
	 */
	Optional<EmpFlexMonthActCalSet> find(String cid, String empCode);

	/**
	 * Adds the.
	 *
	 * @param EmplCalMonthlyFlex the empl cal monthly flex
	 */
	void add(EmpFlexMonthActCalSet EmplCalMonthlyFlex);

	/**
	 * Update.
	 *
	 * @param EmplCalMonthlyFlex the empl cal monthly flex
	 */
	void update(EmpFlexMonthActCalSet EmplCalMonthlyFlex);

	/**
	 * Delete.
	 *
	 * @param EmplCalMonthlyFlex the empl cal monthly flex
	 */
	void remove(String cid, String empCode);
}
