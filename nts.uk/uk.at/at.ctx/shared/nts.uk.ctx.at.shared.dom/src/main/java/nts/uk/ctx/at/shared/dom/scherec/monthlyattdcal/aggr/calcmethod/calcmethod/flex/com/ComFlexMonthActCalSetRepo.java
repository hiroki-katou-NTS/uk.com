package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.com;

import java.util.Optional;

/**
 * The Interface CompanyCalMonthlyFlexRepository.
 */
public interface ComFlexMonthActCalSetRepo {

	/**
	 * Gets the company cal monthly flex by cid.
	 *
	 * @param companyId the company id
	 * @return the company cal monthly flex by cid
	 */
	Optional<ComFlexMonthActCalSet> find(String companyId);

	/**
	 * Adds the.
	 *
	 * @param companyCalMonthlyFlex the company cal monthly flex
	 */
	void add(ComFlexMonthActCalSet companyCalMonthlyFlex);

	/**
	 * Update.
	 *
	 * @param companyCalMonthlyFlex the company cal monthly flex
	 */
	void update(ComFlexMonthActCalSet companyCalMonthlyFlex);

	/**
	 * Removes the.
	 *
	 * @param companyId the company id
	 */
	void remove(String companyId);
}
