package nts.uk.screen.com.app.command.sys.auth.role;

import lombok.Value;

@Value
public class WorkPlaceAuthorityCommand {
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
	/**
	 * 利用できる
	 */
	private boolean availability;
}
