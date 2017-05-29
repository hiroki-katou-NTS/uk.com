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

/**
 * The Class ContactPersonalSettingFinder.
 */
@Stateless
public class ContactPersonalSettingFinder {

	/** The repository. */
	@Inject
	private ContactPersonalSettingRepository repository;

	/**
	 * Find all.
	 *
	 * @param processingYm the processing ym
	 * @param processingNo the processing no
	 * @return the list
	 */
	public List<ContactPersonalSettingDto> findAll(int processingYm, int processingNo) {
		String companyCode = AppContexts.user().companyCode();
		List<ContactPersonalSetting> listSetting = repository.findAll(companyCode, processingYm, processingNo);
		return listSetting.stream().map(setting -> {
			return ContactPersonalSettingDto.builder().employeeId(setting.getEmployeeCode())
					.comment(setting.getComment().v()).build();
		}).collect(Collectors.toList());
	}
}
