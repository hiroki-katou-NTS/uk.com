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
	
//	employee: EmployeeDto;
//	employeeInfoContact: EmployeeInfoContactDto;
//	personContact: PersonContactDto;
//	private passwordPolicy: PasswordPolicyDto;
	
	private List<UserInfoUseMethodDto> listUserInfoUseMethod;

	private List<UseContactSettingDto> listUseContactSetting;
	
}
