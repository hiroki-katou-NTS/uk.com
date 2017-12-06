package nts.uk.ctx.sys.portal.dom.webmenu.webmenulinking;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.sys.portal.dom.webmenu.WebMenuCode;

@Getter
public class RoleByRoleTies extends AggregateRoot  {
	/** メニューコードリスト */
    private WebMenuCode webMenuCd;
    
    /**ロールID */
    private String roleId;

	public RoleByRoleTies(WebMenuCode webMenuCd, String roleId) {
		super();
		this.webMenuCd = webMenuCd;
		this.roleId = roleId;
	}
}
