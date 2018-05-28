/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.ws.mailnoticeset.company;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.env.app.command.mailnoticeset.company.MailDestinationFunctionSaveCommand;
import nts.uk.ctx.sys.env.app.command.mailnoticeset.company.MailDestinationFunctionSaveCommandHandler;
import nts.uk.ctx.sys.env.app.command.mailnoticeset.company.UserInfoUseMethodSaveCommand;
import nts.uk.ctx.sys.env.app.command.mailnoticeset.company.UserInfoUseMethodSaveCommandHandler;
import nts.uk.ctx.sys.env.app.find.mailnoticeset.MailFunctionAndDestinationFinder;
import nts.uk.ctx.sys.env.app.find.mailnoticeset.company.UserInfoUseMethodFinder;
import nts.uk.ctx.sys.env.app.find.mailnoticeset.company.dto.UserInfoUseMethodDto;
import nts.uk.ctx.sys.env.app.find.mailnoticeset.dto.SettingDataDto;
import nts.uk.ctx.sys.env.app.find.mailnoticeset.dto.UserInfoEnumDto;
import nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UserInfoItem;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;

/**
 * The Class UserInfoSettingWs.
 */
@Path("sys/env/userinfoset")
@Produces(MediaType.APPLICATION_JSON)
public class UserInfoSettingWs extends WebService {

	/** The find handler. */
	@Inject
	private UserInfoUseMethodFinder findHandler;

	/** The mail function and destination finder. */
	@Inject
	private MailFunctionAndDestinationFinder mailFunctionAndDestinationFinder;

	/** The mail destination function save command handler. */
	@Inject
	private MailDestinationFunctionSaveCommandHandler mailDestinationFunctionSaveCommandHandler;

	/** The user info use method save command handler. */
	@Inject
	private UserInfoUseMethodSaveCommandHandler userInfoUseMethodSaveCommandHandler;

	/** The i 18 n. */
	@Inject
	private I18NResourcesForUK i18n;

	/**
	 * Find.
	 *
	 * @return the list
	 */
	@Path("find")
	@POST
	public List<UserInfoUseMethodDto> find() {
		return this.findHandler.findByEmployeeId();
	}

	/**
	 * Pcmail company.
	 *
	 * @return the setting data dto
	 */
	@Path("pcmail/company")
	@POST
	public SettingDataDto pcmailCompany() {
		return this.mailFunctionAndDestinationFinder.getData(UserInfoItem.COMPANY_PC_MAIL);
	}

	/**
	 * Pcmail person.
	 *
	 * @return the setting data dto
	 */
	@Path("pcmail/person")
	@POST
	public SettingDataDto pcmailPerson() {
		return this.mailFunctionAndDestinationFinder.getData(UserInfoItem.PERSONAL_PC_MAIL);
	}

	/**
	 * Mobilemail company.
	 *
	 * @return the setting data dto
	 */
	@Path("mobilemail/company")
	@POST
	public SettingDataDto mobilemailCompany() {
		return this.mailFunctionAndDestinationFinder.getData(UserInfoItem.COMPANY_MOBILE_MAIL);
	}

	/**
	 * Mobilemail person.
	 *
	 * @return the setting data dto
	 */
	@Path("mobilemail/person")
	@POST
	public SettingDataDto mobilemailPerson() {
		return this.mailFunctionAndDestinationFinder.getData(UserInfoItem.PERSONAL_MOBILE_MAIL);
	}

	/**
	 * User info setting.
	 *
	 * @param command
	 *            the command
	 */
	@Path("save/userInfoSetting")
	@POST
	public void userInfoSetting(UserInfoUseMethodSaveCommand command) {
		this.userInfoUseMethodSaveCommandHandler.handle(command);
	}

	/**
	 * Sets the ting by menu.
	 *
	 * @param command
	 *            the new ting by menu
	 */
	@Path("save/settingByMenu")
	@POST
	public void settingByMenu(MailDestinationFunctionSaveCommand command) {
		this.mailDestinationFunctionSaveCommandHandler.handle(command);
	}

	/**
	 * Gets the all enum.
	 *
	 * @return the all enum
	 */
	@Path("getAllEnum")
	@POST
	public UserInfoEnumDto getAllEnum() {
		return UserInfoEnumDto.init(i18n);
	}
}
