/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.dom.mailnoticeset.employee;

import lombok.Getter;

/**
 * The Class UseContactSetting.
 */
// 連絡先使用設定
@Getter
public class UseContactSetting {

	/** The Employee ID. */
	// 社員ID
	// TODO change Stirng to Primitive value
	private String EmployeeID;

	/** The setting item. */
	// 設定項目
	private UserInfoItem settingItem;

	/** The use mail setting. */
	// メール利用設定
	private boolean useMailSetting;
}
