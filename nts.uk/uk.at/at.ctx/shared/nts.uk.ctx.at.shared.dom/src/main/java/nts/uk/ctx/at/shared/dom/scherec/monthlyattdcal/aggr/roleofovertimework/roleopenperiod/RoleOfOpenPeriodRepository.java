package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleopenperiod;

import java.util.List;

/**
 * The Interface RoleOfOpenPeriodRepository.
 */
public interface RoleOfOpenPeriodRepository {
	
	/**
	 * Find by CID.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	List<RoleOfOpenPeriod> findByCID(String companyId);
	
	
	/**
	 * Update.
	 *
	 * @param lstRoleOfOpenPeriod the lst role of open period
	 */
	void update(List<RoleOfOpenPeriod> lstRoleOfOpenPeriod);
	
	
	/**
	 * Adds the.
	 *
	 * @param lstRoleOfOpenPeriod the lst role of open period
	 */
	void add(List<RoleOfOpenPeriod> lstRoleOfOpenPeriod);
}
