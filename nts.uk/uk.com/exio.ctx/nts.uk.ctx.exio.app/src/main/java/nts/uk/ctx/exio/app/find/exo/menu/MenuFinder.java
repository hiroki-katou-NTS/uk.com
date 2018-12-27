package nts.uk.ctx.exio.app.find.exo.menu;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.menu.MenuService;
import nts.uk.ctx.exio.dom.exo.menu.RoleAuthority;

@Stateless
public class MenuFinder {
	@Inject MenuService menuService;

	public RoleAuthorityDto startMenu() {
		RoleAuthority role = menuService.getRoleAuthority();
		return new RoleAuthorityDto(role.getInChargeRole(), role.getEmpRole());
	}
}
