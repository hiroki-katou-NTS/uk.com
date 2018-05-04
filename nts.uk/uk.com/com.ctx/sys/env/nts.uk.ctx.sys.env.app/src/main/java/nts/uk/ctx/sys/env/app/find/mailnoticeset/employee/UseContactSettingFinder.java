/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.app.find.mailnoticeset.employee;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UseContactSetting;
import nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UseContactSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class UseContactSettingFinder.
 */
@Stateless
public class UseContactSettingFinder {

	/** The use contact setting repository. */
	@Inject
	private UseContactSettingRepository useContactSettingRepository;

	/**
	 * Find by employee id.
	 *
	 * @param employeeId the employee id
	 * @return the use contact setting dto
	 */
	public UseContactSettingDto findByEmployeeId(String employeeId) {
		String companyId = AppContexts.user().companyId();
		UseContactSetting useContactSetting = useContactSettingRepository.findByEmployeeId(employeeId, companyId);
		UseContactSettingDto dto = new UseContactSettingDto();
		useContactSetting.saveToMemento(dto);
		return dto;
	}
}
