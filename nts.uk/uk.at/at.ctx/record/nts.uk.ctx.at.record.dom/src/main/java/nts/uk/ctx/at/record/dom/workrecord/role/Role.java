package nts.uk.ctx.at.record.dom.workrecord.role;

import lombok.Getter;

@Getter
public class Role {

	private String companyId;

	private String roleId;

	private String roleName;

	public Role(String companyId, String roleId, String roleName) {
		super();
		this.companyId = companyId;
		this.roleId = roleId;
		this.roleName = roleName;
	}

}
