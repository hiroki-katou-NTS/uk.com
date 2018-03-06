/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.find.singlesignon;

import lombok.Getter;

/**
 * Gets the use atr.
 *
 * @return the use atr
 */
@Getter
public class WindowsAccountFinderDto {

	// ユーザID
	/** The user id. */
	private String userId;

	// ホスト名
	/** The hot name. */
	private String hostName;

	// ユーザ名
	/** The user name. */
	private String userName;

	// NO
	/** The no. */
	private Integer no;

	/** The use atr. */
	// 利用区分
	private Integer useAtr;

	public WindowsAccountFinderDto(String userId, String hostName, String userName, Integer no,
			Integer useAtr) {
		super();
		this.userId = userId;
		this.hostName = hostName;
		this.userName = userName;
		this.no = no;
		this.useAtr = useAtr;
	}
}
