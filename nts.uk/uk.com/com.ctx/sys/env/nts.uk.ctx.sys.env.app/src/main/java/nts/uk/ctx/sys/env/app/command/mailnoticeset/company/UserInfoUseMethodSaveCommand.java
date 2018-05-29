/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.app.command.mailnoticeset.company;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.sys.env.app.command.mailnoticeset.company.dto.UserInfoUseMethodDto;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethod;

/**
 * The Class UserInfoUseMethodCommand.
 */
@Getter
@Setter
public class UserInfoUseMethodSaveCommand {

	/** The fixed work setting. */
	private List<UserInfoUseMethodDto> lstUserInfoUseMethodDto;

	/**
	 * To domain fixed work setting.
	 *
	 * @return the fixed work setting
	 */
	public List<UserInfoUseMethod> toDomain() {
		return this.lstUserInfoUseMethodDto.stream().map(dto -> {
			return new UserInfoUseMethod(dto);
		}).collect(Collectors.toList());
	}

	public UserInfoUseMethodSaveCommand() {
		super();
	}
	
}
