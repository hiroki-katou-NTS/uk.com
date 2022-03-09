package nts.uk.ctx.sys.portal.app.find.webmenu;

import lombok.Data;
import nts.uk.ctx.sys.portal.dom.webmenu.WebMenuCode;

@Data
public class MenuCodeDto {

	private String companyId;
	
	private String menuCode;

	public MenuCodeDto(String companyId, String menuCode) {
		this.companyId = companyId;
		this.menuCode = menuCode;
	}

	/**
	 * 会社管理者メニューか
	 * @return
	 */
	public boolean isCompanyAdminMenu() {
		return new WebMenuCode(menuCode).isCompanyAdmin();
	}
}
