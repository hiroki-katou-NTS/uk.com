package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.laborcostandtime;

import java.util.Optional;

public interface WorkplaceCounterLaborCostAndTimeRepo {

	/**
	 * insert(職場計の人件費・時間)
	 * @param companyId
	 * @param domain
	 */
	public void insert(String companyId, WorkplaceCounterLaborCostAndTime domain);
	
	/**
	 * update(職場計の人件費・時間)
	 * @param companyId
	 * @param domain
	 */
	public void update(String companyId, WorkplaceCounterLaborCostAndTime domain);
	
	/**
	 * get
	 * @param companyId
	 * @return
	 */
	public Optional<WorkplaceCounterLaborCostAndTime> get(String companyId);
	
	/**
	 * exists
	 * @param companyId
	 * @return
	 */
	public boolean exists(String companyId);
	
}
