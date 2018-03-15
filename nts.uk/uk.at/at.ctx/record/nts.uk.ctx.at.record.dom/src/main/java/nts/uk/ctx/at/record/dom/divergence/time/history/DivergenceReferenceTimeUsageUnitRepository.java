package nts.uk.ctx.at.record.dom.divergence.time.history;

public interface DivergenceReferenceTimeUsageUnitRepository {

	/**
	 * Adds the.
	 *
	 * @param domain
	 *            the domain
	 */
	void add(DivergenceReferenceTimeUsageUnit domain);

	/**
	 * Update.
	 *
	 * @param domain
	 *            the domain
	 */
	void update(DivergenceReferenceTimeUsageUnit domain);

	/**
	 * Find by id.
	 *
	 * @param companyId
	 *            the company id
	 * @return the divergence reference time usage unit
	 */
	DivergenceReferenceTimeUsageUnit findByCompanyId(String companyId);
}
