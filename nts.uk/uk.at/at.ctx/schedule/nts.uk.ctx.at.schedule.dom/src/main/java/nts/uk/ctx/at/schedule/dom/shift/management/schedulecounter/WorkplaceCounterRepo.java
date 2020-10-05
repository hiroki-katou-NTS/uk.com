package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter;

import java.util.Optional;

public interface WorkplaceCounterRepo {
	
	/**
	 * insert(職場計)
	 * @param companyId
	 * @param domain
	 */
	public void insert(String companyId, WorkplaceCounter domain);
	
	/**
	 * update(職場計)
	 * @param companyId
	 * @param domain
	 */
	public void update(String companyId, WorkplaceCounter domain);
	
	/**
	 * get
	 * @param companyId
	 * @return
	 */
	public Optional<WorkplaceCounter> get(String companyId);
	
	/**
	 * exists
	 * @param companyId
	 * @return
	 */
	public boolean exists(String companyId);

}
