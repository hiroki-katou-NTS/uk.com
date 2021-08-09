package nts.uk.ctx.sys.portal.app.screenquery.roleset;

import lombok.Data;

import nts.uk.ctx.sys.auth.dom.roleset.DefaultRoleSet;
import nts.uk.ctx.sys.portal.dom.adapter.roleset.RoleSetDto;

import java.util.List;


@Data
public class RoleSetAndRoleDefaultDto {
    public List<RoleSetDto> roleSetDtos;
    public DefaultRoleSet defaultRoleSet;
}
