/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.payment.contact.find;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.report.app.payment.contact.find.dto.ContactItemsSettingFindDto;
import nts.uk.ctx.pr.report.app.payment.contact.find.dto.ContactItemsSettingOut;
import nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsCode;
import nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsCodeGetMemento;
import nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsSetting;
import nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.primitive.sample.ProcessingNo;

/**
 * The Class ContactItemsSettingFinder.
 */
@Stateless
public class ContactItemsSettingFinder {

	/** The repository. */
	@Inject
	private ContactItemsSettingRepository repository;

	/**
	 * Finder.
	 *
	 * @param findDto
	 *            the find dto
	 * @return the contact items setting
	 */
	public ContactItemsSettingOut finder(ContactItemsSettingFindDto findDto) {

		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company Code

		String companyCode = loginUserContext.companyCode();

		List<String> empCds = findDto.getEmpCommentFinds().stream().map(item -> {
			return item.getEmployeeCode();
		}).collect(Collectors.toList());

		ContactItemsSetting contactItemsSetting = this.repository
			.findByCode(new ContactItemsCode(new ContactItemsCodeGetMemento() {

				@Override
				public YearMonth getProcessingYm() {
					return YearMonth.of(findDto.getProcessingYm());
				}

				@Override
				public ProcessingNo getProcessingNo() {
					return new ProcessingNo(findDto.getProcessingNo());
				}

				@Override
				public String getCompanyCode() {
					return companyCode;
				}
			}), empCds);

		ContactItemsSettingOut dto = new ContactItemsSettingOut();
		contactItemsSetting.saveToMemento(dto);
		dto.mergeData(findDto.getEmpCommentFinds());
		return dto;
	}

}
