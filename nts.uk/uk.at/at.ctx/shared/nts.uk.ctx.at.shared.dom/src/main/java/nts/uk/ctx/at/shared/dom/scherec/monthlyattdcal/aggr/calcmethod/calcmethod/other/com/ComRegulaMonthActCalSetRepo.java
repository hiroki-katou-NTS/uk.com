package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.com;

import java.util.Optional;

/**
 * The Interface CompanyLaborRegSetMonthlyActualRepository.
 */
public interface ComRegulaMonthActCalSetRepo {

	/**
	 * Gets the company labor reg set monthly actual by cid.
	 *
	 * @param companyId the company id
	 * @return the company labor reg set monthly actual by cid
	 */
	Optional<ComRegulaMonthActCalSet> find(String companyId);

	/**
	 * Adds the.
	 *
	 * @param companyLaborRegSetMonthlyActual the company labor reg set monthly actual
	 */
	void add(ComRegulaMonthActCalSet companyLaborRegSetMonthlyActual);

	/**
	 * Update.
	 *
	 * @param companyLaborRegSetMonthlyActual the company labor reg set monthly actual
	 */
	void update(ComRegulaMonthActCalSet companyLaborRegSetMonthlyActual);

	/**
	 * Removes the.
	 *
	 * @param cId the c id
	 */
	void remove(String cId);
}
