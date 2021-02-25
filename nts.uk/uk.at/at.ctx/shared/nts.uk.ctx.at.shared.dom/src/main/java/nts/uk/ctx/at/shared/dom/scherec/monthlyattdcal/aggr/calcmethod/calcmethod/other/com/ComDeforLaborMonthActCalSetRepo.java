package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.com;

import java.util.Optional;

/**
 * The Interface CompanyLaborDeforSetMonthlyRepository.
 */
public interface ComDeforLaborMonthActCalSetRepo {

	/**
	 * Gets the company labor defor set monthly by cid.
	 *
	 * @param companyId the company id
	 * @return the company labor defor set monthly by cid
	 */
	Optional<ComDeforLaborMonthActCalSet> find(String companyId);

	/**
	 * Adds the.
	 *
	 * @param companyLaborDeforSetMonthly the company labor defor set monthly
	 */
	void add(ComDeforLaborMonthActCalSet companyLaborDeforSetMonthly);

	/**
	 * Update.
	 *
	 * @param companyLaborDeforSetMonthly the company labor defor set monthly
	 */
	void update(ComDeforLaborMonthActCalSet companyLaborDeforSetMonthly);

	/**
	 * Removes the.
	 *
	 * @param companyId the company id
	 */
	void remove(String companyId);
}
