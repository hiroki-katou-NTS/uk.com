package nts.uk.ctx.sys.auth.dom.roleset.webmenu.webmenulinking;

import java.util.List;

/**
 * The Interface WebMenuAdapter.
 * @author HieuNV
 */
public interface RoleSetAndWebMenuAdapter {

	/**
	 * Get all Web Menu that link by RoleSet. 
	 * CompanyId is companyId of login user
	 * @return
	 */
	// RequestList #???
	List<RoleSetAndWebMenuImport> findAllWebMenuByRoleSetCd(String roleSetCd);
	
	/**
	 * add Role Set and Web menu link
	 * @param roleSetAndWebMenus
	 */
	void addRoleSetAndWebMenu(RoleSetAndWebMenuImport roleSetAndWebMenu);

	/**
	 * update Role Set and Web menu link
	 * @param roleSetAndWebMenus
	 */	
	void updateRoleSetAndWebMenu(RoleSetAndWebMenuImport roleSetAndWebMenu);

	/**
	 * delete by roleSetCd 
	 * @param companyId: CompanyId is companyId of login user
	 * @param roleSetCd
	 */
	void deleteAllRoleSetAndWebMenu(String roleSetCd);
}
