package nts.uk.ctx.at.auth.app.command.employmentrole;

import lombok.Data;

@Data
public class DeleteEmploymentRoleCmd {
	/**
	 * 会社ID
	 */
	private String companyId;
	/**
	 * ロールID
	 */
	private String roleId;
}
