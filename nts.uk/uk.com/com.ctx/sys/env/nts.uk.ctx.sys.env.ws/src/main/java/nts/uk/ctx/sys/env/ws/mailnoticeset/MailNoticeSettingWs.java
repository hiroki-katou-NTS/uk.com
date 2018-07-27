/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.ws.mailnoticeset;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.env.app.command.mailnoticeset.MailNoticeSetSaveCommand;
import nts.uk.ctx.sys.env.app.command.mailnoticeset.MailNoticeSetSaveCommandHandler;
import nts.uk.ctx.sys.env.app.find.mailnoticeset.MailNoticeSettingFinder;
import nts.uk.ctx.sys.env.app.find.mailnoticeset.dto.MailNoticeSettingDto;

/**
 * The Class MailNoticeSettingWs.
 */
@Path("sys/env/mailnoticeset")
@Produces(MediaType.APPLICATION_JSON)
public class MailNoticeSettingWs extends WebService {

	/** The mail notice setting finder. */
	@Inject
	private MailNoticeSettingFinder mailNoticeSettingFinder;

	/** The mail notice set save command handler. */
	@Inject
	private MailNoticeSetSaveCommandHandler mailNoticeSetSaveCommandHandler;

	/**
	 * Find.
	 *
	 * @return the mail notice setting dto
	 */
	@Path("find")
	@POST
	public MailNoticeSettingDto find() {
		return this.mailNoticeSettingFinder.findAll();
	}

	/**
	 * Save.
	 *
	 * @param command
	 *            the command
	 */
	@Path("save")
	@POST
	public void save(MailNoticeSetSaveCommand command) {
		this.mailNoticeSetSaveCommandHandler.handle(command);
	}

}
