package nts.uk.ctx.exio.dom.exo.adapter.sys.auth;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;

@Getter
public class RoleImport {
	/** The role id. */
	public String roleId;

	/** The role code. */
	public String roleCode;

	/** The role name. */
	public String roleName;

	/** The assign atr. */
	// 担当区分
	private RoleAtrImport assignAtr;

	/**
	 * @param roleId
	 * @param roleCode
	 * @param roleName
	 */
	public RoleImport(String roleId, String roleCode, String roleName, Integer assignAtr) {
		super();
		this.roleId = roleId;
		this.roleCode = roleCode;
		this.roleName = roleName;
		this.assignAtr = EnumAdaptor.valueOf(assignAtr, RoleAtrImport.class);
	}
}
