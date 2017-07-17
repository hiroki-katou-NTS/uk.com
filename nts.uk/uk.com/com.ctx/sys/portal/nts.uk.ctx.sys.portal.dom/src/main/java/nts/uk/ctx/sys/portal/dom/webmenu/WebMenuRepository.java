package nts.uk.ctx.sys.portal.dom.webmenu;

import java.util.List;
import java.util.Optional;


/**
 * 
 * @author sonnh
 *
 */
public interface WebMenuRepository {
	
	/**
	 * 
	 * @param companyId
	 * @return
	 */
	List<WebMenu> findAll(String companyId);
	
	/**
	 * Find a web menu
	 * @param companyId
	 * @return
	 */
	Optional<WebMenu> find(String companyId, String webMenuCode);
	
	/**
	 * add new a web menu
	 * @param webMenu
	 */
	void add(WebMenu webMenu);
	
	/**
	 * update information a web menu
	 * @param webMenu
	 */
	void update(WebMenu webMenu);
	
	/**
	 * remove a web menu
	 * @param companyId
	 * @param webMenuCode
	 */
	void remove(String companyId, String webMenuCode);

	
	/**
	 * Change all web menu to not default
	 * @param companyId
	 */
	void changeNotDefault(String companyId, String webMenuCode);
}
