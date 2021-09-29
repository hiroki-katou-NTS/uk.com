package nts.uk.ctx.sys.portal.app.screenquery.webmenu;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RoleByRoleTiesDto {
    /**
     * ロールID
     */
    private String roleId;
    /**
     * メニューコードリスト
     */
    private String webMenuCd;

    private String companyId;
}
