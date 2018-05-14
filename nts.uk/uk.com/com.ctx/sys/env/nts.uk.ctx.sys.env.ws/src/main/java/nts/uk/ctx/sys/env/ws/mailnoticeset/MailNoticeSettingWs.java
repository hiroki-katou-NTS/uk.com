/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.ws.mailnoticeset;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.env.app.command.mailnoticeset.MailNoticeSetSaveCommand;

/**
 * The Class MailNoticeSettingWs.
 */
@Path("sys/env/mailnoticeset")
@Produces(MediaType.APPLICATION_JSON)
public class MailNoticeSettingWs extends WebService {
	
	@Path("save")
	@POST
	public void save(MailNoticeSetSaveCommand command) {
		//this.functionSettingFinder.findByUserInfoItem(UserInfoItem.COMPANY_PC_MAIL);
	}
	
}
