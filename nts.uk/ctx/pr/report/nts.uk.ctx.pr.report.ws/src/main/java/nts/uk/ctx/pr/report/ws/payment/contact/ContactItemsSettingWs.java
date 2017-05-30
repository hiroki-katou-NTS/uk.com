/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.ws.payment.contact;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.report.app.payment.contact.command.ContactItemsSettingSaveCommand;
import nts.uk.ctx.pr.report.app.payment.contact.command.ContactItemsSettingSaveCommandHandler;
import nts.uk.ctx.pr.report.app.payment.contact.find.ContactItemsSettingFinder;
import nts.uk.ctx.pr.report.app.payment.contact.find.dto.ContactItemsSettingFindDto;
import nts.uk.ctx.pr.report.app.payment.contact.find.dto.ContactItemsSettingOut;

/**
 * The Class ContactItemsSettingWs.
 */
@Path("ctx/pr/report/payment/contact/item")
@Produces("application/json")
public class ContactItemsSettingWs extends WebService {

	/** The find. */
	@Inject
	private ContactItemsSettingFinder finder;

	/** The save. */
	@Inject
	private ContactItemsSettingSaveCommandHandler save;

	/**
	 * Find settings.
	 *
	 * @param dto the dto
	 * @return the contact items setting out
	 */
	@POST
	@Path("findSettings")
	public ContactItemsSettingOut findSettings(ContactItemsSettingFindDto dto) {
		return this.finder.finder(dto);
	}

	/**
	 * Save.
	 *
	 * @param dto
	 *            the dto
	 */
	@POST
	@Path("save")
	public void save(ContactItemsSettingSaveCommand command) {
		this.save.handle(command);
	}

}
