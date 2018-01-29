package nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface AggregateSettingRepository.
 */
public interface AggregateSettingRepository {
	
	/**
	 * Find by CID.
	 *
	 * @param companyId the company id
	 * @return the optional
	 */
	Optional<AggregateSetting> findByCID(CompanyId companyId);
	
	/**
	 * Update.
	 *
	 * @param domain the domain
	 */
	void update(AggregateSetting domain);
}
