package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.laborcostandtime;

import java.util.Optional;

public interface WplCounterLaborCostAndTimeRepo {

	/**
	 * insert(職場計の人件費・時間)
	 * @param companyId
	 * @param domain
	 */
	public void insert(String companyId, WplCounterLaborCostAndTime domain);
	
	/**
	 * update(職場計の人件費・時間)
	 * @param companyId
	 * @param domain
	 */
	public void update(String companyId, WplCounterLaborCostAndTime domain);
	
	/**
	 * get
	 * @param companyId
	 * @return
	 */
	public Optional<WplCounterLaborCostAndTime> get(String companyId);
	
	/**
	 * exists
	 * @param companyId
	 * @return
	 */
	public boolean exists(String companyId);
	
}
