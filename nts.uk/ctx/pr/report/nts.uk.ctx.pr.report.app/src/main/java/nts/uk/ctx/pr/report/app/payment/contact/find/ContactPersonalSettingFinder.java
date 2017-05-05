/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.payment.contact.find;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.report.app.payment.contact.find.dto.ContactPersonalSettingDto;
import nts.uk.ctx.pr.report.dom.payment.contact.ContactPersonalSetting;
import nts.uk.ctx.pr.report.dom.payment.contact.ContactPersonalSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ContactPersonalSettingFinder {
	@Inject
	private ContactPersonalSettingRepository repository;

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<ContactPersonalSettingDto> findAll() {
		List<ContactPersonalSetting> listSetting = repository.findAll(AppContexts.user().companyCode());
		List<ContactPersonalSettingDto> listDto = listSetting.stream().map(setting -> {
			return ContactPersonalSettingDto.builder().employeeId(setting.getEmployeeCode())
					.comment(setting.getComment().v()).build();
		}).collect(Collectors.toList());
		return listDto;
	}
}
