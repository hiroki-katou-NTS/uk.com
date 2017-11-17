package nts.uk.ctx.sys.auth.app.find.roleset;

import lombok.Data;

@Data
public class DefaultRoleSetDto {

	/** ロールセットコード. */
	private String roleSetCd;

	/** ロールセット名称*/
	private String roleSetName;
	
	public DefaultRoleSetDto(String roleSetCd, String roleSetName) {
		this.roleSetCd = roleSetCd;
		this.roleSetName = roleSetName;
	}

}
