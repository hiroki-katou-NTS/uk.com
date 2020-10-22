package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.timezonepeople;

import java.util.Optional;

public interface WorkplaceCounterTimeZonePeopleNumberRepo {
	
	/**
	 * insert(職場計の時間帯人数)
	 * @param companyId
	 * @param domain
	 */
	public void insert(String companyId, WorkplaceCounterTimeZonePeopleNumber domain);
	
	/**
	 * update(職場計の時間帯人数)
	 * @param companyId
	 * @param domain
	 */
	public void update(String companyId, WorkplaceCounterTimeZonePeopleNumber domain);
	
	/**
	 * get
	 * @param companyId
	 * @return
	 */
	public Optional<WorkplaceCounterTimeZonePeopleNumber> get(String companyId);
	
	/**
	 * exists
	 * @param companyId
	 * @return
	 */
	public boolean exists(String companyId);

}
