package nts.uk.ctx.at.auth.pub.wplmanagementauthority;

import lombok.Value;

@Value
public class WorkPlaceAuthorityExport {
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
