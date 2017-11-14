package nts.uk.ctx.sys.auth.app.find.roleset;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.sys.auth.dom.roleset.DefaultRoleSet;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSet;

@Data
public class DefaultRoleSetDto {

	/** ロールセットコード. */
	private String roleSetCd;

	/** 会社ID */
	private String companyId;
	
	/**
	 * Transfer data from Domain into Dto to response to client
	 * @param roleSet
	 * @return
	 */
	public static DefaultRoleSetDto build(DefaultRoleSet defaultRoleSet) {
		DefaultRoleSetDto result = new DefaultRoleSetDto();
		result.setCompanyId(defaultRoleSet.getCompanyId());
		result.setRoleSetCd(defaultRoleSet.getRoleSetCd().v());
		return result;
	}
}
