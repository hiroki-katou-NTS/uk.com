package nts.uk.ctx.sys.portal.app.find.webmenu.webmenulinking;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.sys.portal.dom.webmenu.WebMenuCode;
import nts.uk.ctx.sys.portal.dom.webmenu.webmenulinking.RoleByRoleTies;

@Getter
@Setter
@NoArgsConstructor
public class RoleByRoleTiesDto {
	/** メニューコードリスト */
    private String webMenuCd;
    
    /**ロールID */
    private String roleId;

	public RoleByRoleTiesDto(String webMenuCd, String roleId) {
		super();
		this.webMenuCd = webMenuCd;
		this.roleId = roleId;
	}
    public static RoleByRoleTiesDto fromDomain(RoleByRoleTies domain) {
    	return new RoleByRoleTiesDto(
    			domain.getWebMenuCd().v(),
    			domain.getRoleId()
    			);
    }
}
