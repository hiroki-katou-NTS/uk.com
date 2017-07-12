package nts.uk.ctx.sys.portal.dom.webmenu.personaltying;

import java.util.List;



public interface PersonalTyingRepository {

	
	/**
	 * add person type
	 * @param personalTying
	 */
	void add(PersonalTying personalTying);
	/**
	 * 
	 * @param companyId
	 */
	void delete(String companyId);
	
	/**
	 * 
	 * @param companyId
	 * @param webMenuCode
	 * @param employeeId
	 * @return
	 */
	List<PersonalTying> findAll(String companyId, String employeeId);
} 
