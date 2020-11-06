package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter;

import java.util.Optional;

public interface PersonalCounterRepo {
	
	/**
	 * insert(個人計)
	 * @param companyId
	 * @param domain
	 */
	public void insert(String companyId, PersonalCounter domain);
	
	/**
	 * update(個人計)
	 * @param companyId
	 * @param domain
	 */
	public void update(String companyId, PersonalCounter domain);
	
	/**
	 * get
	 * @param companyId
	 * @return
	 */
	public Optional<PersonalCounter> get(String companyId);
	
	/**
	 * exists
	 * @param companyId
	 * @return
	 */
	public boolean exists(String companyId);
	

}
