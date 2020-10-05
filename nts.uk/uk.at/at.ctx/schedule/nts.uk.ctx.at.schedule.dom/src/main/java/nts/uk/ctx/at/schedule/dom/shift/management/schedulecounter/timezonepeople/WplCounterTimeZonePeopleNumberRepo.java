package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.timezonepeople;

import java.util.Optional;

public interface WplCounterTimeZonePeopleNumberRepo {
	
	/**
	 * insert(職場計の時間帯人数)
	 * @param companyId
	 * @param domain
	 */
	public void insert(String companyId, WplCounterTimeZonePeopleNumber domain);
	
	/**
	 * update(職場計の時間帯人数)
	 * @param companyId
	 * @param domain
	 */
	public void update(String companyId, WplCounterTimeZonePeopleNumber domain);
	
	/**
	 * get
	 * @param companyId
	 * @return
	 */
	public Optional<WplCounterTimeZonePeopleNumber> get(String companyId);
	
	/**
	 * exists
	 * @param companyId
	 * @return
	 */
	public boolean exists(String companyId);

}
