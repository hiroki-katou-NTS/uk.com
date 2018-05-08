/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.app.command.mailnoticeset.company;

import lombok.Data;
import nts.uk.ctx.sys.env.app.command.mailnoticeset.company.dto.UserInfoUseMethodDto;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethod;

/**
 * The Class UserInfoUseMethodCommand.
 */
@Data
public class UserInfoUseMethodSaveCommand {

	/** The fixed work setting. */
	private UserInfoUseMethodDto userInfoUseMethodDto;

	/**
	 * To domain fixed work setting.
	 *
	 * @return the fixed work setting
	 */
	public UserInfoUseMethod toDomain() {
		return new UserInfoUseMethod(this.userInfoUseMethodDto);
	}
}
