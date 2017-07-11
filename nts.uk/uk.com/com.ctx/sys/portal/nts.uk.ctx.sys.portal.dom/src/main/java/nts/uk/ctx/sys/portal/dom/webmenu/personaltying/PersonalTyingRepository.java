package nts.uk.ctx.sys.portal.dom.webmenu.personaltying;

public interface PersonalTyingRepository {

	
	/**
	 * add person type
	 * @param personalTying
	 */
	void add(PersonalTying personalTying);
	
	void delete(String companyId);
}
