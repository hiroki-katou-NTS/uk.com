/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.app.find.mailnoticeset.company;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.sys.env.app.find.mailnoticeset.company.dto.UserInfoUseMethodDto;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethod;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethodRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class UseContactSettingFinder.
 */
@Stateless
public class UserInfoUseMethodFinder {

	/** The user info use method repository. */
	@Inject
	private UserInfoUseMethodRepository userInfoUseMethodRepository;

	/**
	 * Find by employee id.
	 *
	 * @param employeeId
	 *            the employee id
	 * @return the use contact setting dto
	 */
	public List<UserInfoUseMethodDto> findByEmployeeId() {
		if (AppContexts.user().roles().forCompanyAdmin() == null) {
			throw new BusinessException("Msg_1103");
		} else {
			String companyId = AppContexts.user().companyId();
			List<UserInfoUseMethod> lstUserInfoUseMethod = userInfoUseMethodRepository.findByCompanyId(companyId);
			return lstUserInfoUseMethod.stream().map(item -> {
				UserInfoUseMethodDto dto = new UserInfoUseMethodDto();
				item.saveToMemento(dto);
				return dto;
			}).collect(Collectors.toList());
		}
	}
}
