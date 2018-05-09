package nts.uk.ctx.at.record.dom.workrecord.role;

import lombok.Getter;
/**
 * ロール
 * @author yennth
 *
 */
@Getter
public class Role {
	/**
	 * 契約コード
	 */
	private String roleCode;
	/**
	 * ロールID
	 */
	private String roleId;
	/**
	 * ロール名称
	 */
	private String roleName;

	public Role(String roleId, String roleCode, String roleName) {
		super();
		this.roleCode = roleCode;
		this.roleId = roleId;
		this.roleName = roleName;
	}

}
