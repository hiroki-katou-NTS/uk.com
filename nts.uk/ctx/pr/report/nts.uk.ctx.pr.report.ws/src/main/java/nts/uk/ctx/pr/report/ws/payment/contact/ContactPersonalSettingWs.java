/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.ws.payment.contact;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.report.app.payment.contact.command.ContactPersonalSettingSaveCommand;
import nts.uk.ctx.pr.report.app.payment.contact.command.ContactPersonalSettingSaveCommandHandler;
import nts.uk.ctx.pr.report.app.payment.contact.find.ContactPersonalSettingFinder;
import nts.uk.ctx.pr.report.app.payment.contact.find.dto.ContactItemsSettingFindDto;
import nts.uk.ctx.pr.report.app.payment.contact.find.dto.ContactPersonalSettingDto;

/**
 * The Class ContactPersonalSettingWs.
 */
@Path("ctx/pr/report/payment/contact/personalsetting")
@Produces("application/json")
public class ContactPersonalSettingWs extends WebService {

	/** The finder. */
	@Inject
	private ContactPersonalSettingFinder finder;
	
	/** The save handler. */
	@Inject
	private ContactPersonalSettingSaveCommandHandler saveHandler;

	/**
	 * Save.
	 *
	 * @param command the command
	 */
	@POST
	@Path("save")
	public void save(ContactPersonalSettingSaveCommand command) {
		this.saveHandler.handle(command);
	}

	/**
	 * Find all.
	 *
	 * @param dto the dto
	 * @return the list
	 */
	@POST
	@Path("findall")
	public List<ContactPersonalSettingDto> findAll(ContactItemsSettingFindDto dto) {
		return this.finder.findAll(dto.getProcessingYm(), dto.getProcessingNo());
	}

}
