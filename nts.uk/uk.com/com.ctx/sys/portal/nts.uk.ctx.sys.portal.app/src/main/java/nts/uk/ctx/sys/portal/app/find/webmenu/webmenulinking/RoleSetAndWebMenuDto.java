package nts.uk.ctx.sys.portal.app.find.webmenu.webmenulinking;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.sys.auth.app.find.roleset.RoleSetDto;
import nts.uk.ctx.sys.portal.dom.webmenu.webmenulinking.RoleSetAndWebMenu;

@Data
public class RoleSetAndWebMenuDto {
	
	/** �?社ID */
	private String companyId;

	/** メニューコードリスト */
	private String webMenuCd;

	/** ロールセットコード. */
	private String roleSetCd;

	/**
	 * Transfer data from Domain into Dto to response to client
	 * @param roleSet
	 * @return
	 */
	public static RoleSetAndWebMenuDto build(RoleSetAndWebMenu roleSetAndWebMenu) {
		RoleSetAndWebMenuDto result = new RoleSetAndWebMenuDto();
		result.setCompanyId(roleSetAndWebMenu.getCompanyId());
		result.setRoleSetCd(roleSetAndWebMenu.getRoleSetCd().v());
		result.setWebMenuCd(roleSetAndWebMenu.getWebMenuCd().v());
		return result;
	}
}
