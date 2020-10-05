package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter;

import java.util.Optional;

public interface OnePersonCounterRepo {
	
	/**
	 * insert(個人計)
	 * @param companyId
	 * @param domain
	 */
	public void insert(String companyId, OnePersonCounter domain);
	
	/**
	 * update(個人計)
	 * @param companyId
	 * @param domain
	 */
	public void update(String companyId, OnePersonCounter domain);
	
	/**
	 * get
	 * @param companyId
	 * @return
	 */
	public Optional<OnePersonCounter> get(String companyId);
	
	/**
	 * exists
	 * @param companyId
	 * @return
	 */
	public boolean exists(String companyId);
	

}
