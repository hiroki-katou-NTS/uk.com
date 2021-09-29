package nts.uk.ctx.sys.portal.ws.webmenu.cas005;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.sys.portal.app.screenquery.webmenu.DtoRole;
import nts.uk.ctx.sys.portal.app.screenquery.webmenu.RoleByRoleTiesDto;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoleAndMenuDto {
    private RoleByRoleTiesDto roleByRoleTies;
    private DtoRole role;
}
