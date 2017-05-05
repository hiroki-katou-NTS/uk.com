/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.payment.contact.command;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.report.app.payment.contact.command.dto.ContactPersonalSettingSaveDto;

/**
 * The Class ContactPersonalSettingSaveCommand.
 */
@Setter
@Getter
public class ContactPersonalSettingSaveCommand {
	List<ContactPersonalSettingSaveDto> listContactPersonalSetting;

}
