package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleofovertimework;

import java.util.List;

/**
 * The Interface RoleOvertimeWorkRepository.
 */
public interface RoleOvertimeWorkRepository {
	
	/**
	 * Find by CID.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	List<RoleOvertimeWork> findByCID(String companyId);
	
	
	/**
	 * Update.
	 *
	 * @param lstRoleOvertimeWork the lst role overtime work
	 */
	void update(List<RoleOvertimeWork> lstRoleOvertimeWork);
	
	
	/**
	 * Adds the.
	 *
	 * @param lstRoleOvertimeWork the lst role overtime work
	 */
	void add(List<RoleOvertimeWork> lstRoleOvertimeWork);
}
