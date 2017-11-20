package nts.uk.ctx.sys.auth.dom.roleset.webmenu;

import java.util.List;

/**
 * The Interface WebMenuAdapter.
 * @author HieuNV
 */
public interface WebMenuAdapter {

	/**
	 * Find by company id.
	 * companyId is companyId of login user
	 * @return
	 */
	// RequestList #??
	List<WebMenu> findByCompanyId();
}
