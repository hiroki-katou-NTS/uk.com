/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.app.command.mailnoticeset.employee.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class UseContactSettingDto.
 */
@Getter
@Setter
public class UseContactSettingDto {
	
	/** The Employee ID. */
	// 社員ID
	public String employeeId;

	/** The setting item. */
	// 設定項目
	public Integer settingItem;

	/** The use mail setting. */
	// メール利用設定
	public Boolean useMailSetting;
	
}
