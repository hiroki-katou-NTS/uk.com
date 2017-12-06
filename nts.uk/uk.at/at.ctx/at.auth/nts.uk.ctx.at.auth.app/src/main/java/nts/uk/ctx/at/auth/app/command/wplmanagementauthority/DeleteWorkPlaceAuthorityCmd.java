package nts.uk.ctx.at.auth.app.command.wplmanagementauthority;

import lombok.Data;

@Data
public class DeleteWorkPlaceAuthorityCmd {
	/**
	 * ロールID
	 */
	private String roleId;
	/**
	 * 会社ID
	 */
	private String companyId;
	/**
	 * NO
	 */
	private int functionNo; 
}
