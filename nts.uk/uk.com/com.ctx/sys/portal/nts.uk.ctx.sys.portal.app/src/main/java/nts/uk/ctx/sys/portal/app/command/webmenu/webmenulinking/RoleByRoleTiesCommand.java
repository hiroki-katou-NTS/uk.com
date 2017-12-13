package nts.uk.ctx.sys.portal.app.command.webmenu.webmenulinking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleByRoleTiesCommand {
	/** メニューコードリスト :WebMenuCode*/
    private String webMenuCd;
    
    /**ロールID */
    private String roleId;
}
