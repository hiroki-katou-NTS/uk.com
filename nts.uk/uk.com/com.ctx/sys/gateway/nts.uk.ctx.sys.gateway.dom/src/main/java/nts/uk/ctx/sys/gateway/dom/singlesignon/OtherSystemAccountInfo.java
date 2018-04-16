/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.singlesignon;

import lombok.Getter;

// 他システムアカウント情報
@Getter
public class OtherSystemAccountInfo {

	//利用区分
	/** The use division. */
	private UseAtr useAtr;

	/** The company code. */
	//会社コード
	private CompanyCode companyCode;

	//ユーザ名
	/** The user name. */
	private UserName userName;

	/**
	 * Instantiates a new other system account info.
	 *
	 * @param useAtr the use atr
	 * @param companyCode the company code
	 * @param userName the user name
	 */
	public OtherSystemAccountInfo(UseAtr useAtr, CompanyCode companyCode, UserName userName) {
		super();
		this.useAtr = useAtr;
		this.companyCode = companyCode;
		this.userName = userName;
	}

}
