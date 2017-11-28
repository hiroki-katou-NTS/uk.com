package nts.uk.ctx.sys.auth.dom.roleset.webmenu.webmenulinking;

import java.util.List;

/**
 * The Interface WebMenuAdapter.
 * @author HieuNV
 */
public interface RoleSetLinkWebMenuAdapter {

	/**
	 * Get all Web Menu that link by RoleSet. 
	 * CompanyId is companyId of login user
	 * @return
	 */
	// RequestList #???
	List<RoleSetLinkWebMenuImport> findAllWebMenuByRoleSetCd(String roleSetCd);
	
	/**
	 * add Role Set and Web menu link
	 * @param roleSetLinkWebMenus
	 */
	void addRoleSetLinkWebMenu(RoleSetLinkWebMenuImport roleSetLinkWebMenu);

	void addAllRoleSetLinkWebMenu(List<RoleSetLinkWebMenuImport> listRoleSetLinkWebMenu);
	
	/**
	 * update Role Set and Web menu link
	 * @param roleSetLinkWebMenus
	 */	
	void updateRoleSetLinkWebMenu(RoleSetLinkWebMenuImport roleSetLinkWebMenu);

	/**
	 * delete by roleSetCd 
	 * @param companyId: CompanyId is companyId of login user
	 * @param roleSetCd
	 */
	void deleteAllRoleSetLinkWebMenu(String roleSetCd);
}
