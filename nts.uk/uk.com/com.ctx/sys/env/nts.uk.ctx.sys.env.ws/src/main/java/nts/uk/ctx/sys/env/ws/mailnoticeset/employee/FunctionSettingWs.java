/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.ws.mailnoticeset.employee;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.env.app.find.mailnoticeset.employee.FunctionSettingDto;
import nts.uk.ctx.sys.env.app.find.mailnoticeset.employee.FunctionSettingFinder;
import nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UserInfoItem;

/**
 * The Class FunctionSettingWs.
 */
@Path("sys/env/mailnoticeset/userinfo")
@Produces(MediaType.APPLICATION_JSON)
public class FunctionSettingWs extends WebService {

	/** The function setting finder. */
	@Inject
	private FunctionSettingFinder functionSettingFinder;

	/**
	 * Find company pc mail.
	 *
	 * @return the list
	 */
	@Path("find/companypcmail")
	@POST
	public List<FunctionSettingDto> findCompanyPcMail() {
		return this.functionSettingFinder.findByUserInfoItem(UserInfoItem.COMPANY_PC_MAIL);
	}

	/**
	 * Find personal pc mail.
	 *
	 * @return the list
	 */
	@Path("find/personalpcmail")
	@POST
	public List<FunctionSettingDto> findPersonalPcMail() {
		return this.functionSettingFinder.findByUserInfoItem(UserInfoItem.PERSONAL_PC_MAIL);
	}

	/**
	 * Find company mobile mail.
	 *
	 * @return the list
	 */
	@Path("find/companymobilemail")
	@POST
	public List<FunctionSettingDto> findCompanyMobileMail() {
		return this.functionSettingFinder.findByUserInfoItem(UserInfoItem.COMPANY_MOBILE_MAIL);
	}

	/**
	 * Find personal mobile mail.
	 *
	 * @return the list
	 */
	@Path("find/personalmobilemail")
	@POST
	public List<FunctionSettingDto> findPersonalMobileMail() {
		return this.functionSettingFinder.findByUserInfoItem(UserInfoItem.PERSONAL_MOBILE_MAIL);
	}

}
