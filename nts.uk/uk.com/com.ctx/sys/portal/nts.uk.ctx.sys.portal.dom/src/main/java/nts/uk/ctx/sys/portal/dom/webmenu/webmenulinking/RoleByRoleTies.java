package nts.uk.ctx.sys.portal.dom.webmenu.webmenulinking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.sys.portal.dom.webmenu.WebMenuCode;
import nts.uk.shr.com.constants.DefaultSettingKeys;

/**
 * 担当ロール別紐付け
 * @author tutk
 *
 */
@Getter
@AllArgsConstructor
public class RoleByRoleTies extends AggregateRoot  {
	
	/** ロールID	*/
	private String roleId;
	
	/** 会社ID	*/
	private String companyId;
	
	/** メニューコードリスト */
	private WebMenuCode webMenuCd;

	/** システム固定メニューか */
	private boolean isSystemMenu;

	public RoleByRoleTies(String roleId, String companyId, WebMenuCode webMenuCd) {
		this.roleId = roleId;
		this.companyId = companyId;
		this.webMenuCd = webMenuCd;
		this.isSystemMenu = false;
	}

	/**
	 * システム固定メニューの場合はゼロ会社のIDを返す
	 * @return
	 */
	public String getMenuCompanyId() {
		return isSystemMenu ? DefaultSettingKeys.COMPANY_ID : companyId;
	}
}
