/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.app.find.mailnoticeset.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.sys.env.app.find.mailnoticeset.company.dto.UserInfoUseMethodDto;
import nts.uk.ctx.sys.env.app.find.mailnoticeset.employee.UseContactSettingDto;

/**
 * The Class MailNoticeSettingDto.
 */
@Getter
@Setter
public class MailNoticeSettingDto {

	/** The edit password. */
	private Boolean editPassword;

	/** The not special user. */
	private Boolean notSpecialUser;

	/** The employee. */
	private EmployeeBasicDto employee;

	/** The employee info contact. */
	private EmployeeInfoContactDto employeeInfoContact;

	/** The person contact. */
	private PersonContactDto personContact;

	/** The password policy. */
	private PasswordPolicyDto passwordPolicy;

	/** The list user info use method. */
	private List<UserInfoUseMethodDto> listUserInfoUseMethod;

	/** The list use contact setting. */
	private List<UseContactSettingDto> listUseContactSetting;

}
