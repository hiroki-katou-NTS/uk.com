/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.payment.contact.command;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.report.app.payment.contact.command.dto.ContactItemsSettingDto;
import nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsSetting;

/**
 * The Class ContactItemsSettingSaveCommand.
 */
@Getter
@Setter
public class ContactItemsSettingSaveCommand implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The labor insurance office. */
	private ContactItemsSettingDto dto;

	/**
	 * To domain.
	 *
	 * @param companyCode
	 *            the company code
	 * @return the contact items setting
	 */
	public ContactItemsSetting toDomain(String companyCode) {
		return this.dto.toDomain(companyCode);
	}
}
