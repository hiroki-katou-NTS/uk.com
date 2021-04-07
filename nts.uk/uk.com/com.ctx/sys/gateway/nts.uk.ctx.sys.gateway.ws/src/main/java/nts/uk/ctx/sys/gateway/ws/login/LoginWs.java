/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.ws.login;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import nts.arc.layer.ws.WebService;
import nts.arc.security.csrf.CsrfToken;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.gateway.app.command.login.password.CheckChangePassDto;
import nts.uk.ctx.sys.gateway.app.command.login.password.PasswordAuthenticateCommand;
import nts.uk.ctx.sys.gateway.app.command.login.password.PasswordAuthenticateCommandHandler;
import nts.uk.ctx.sys.gateway.app.find.login.CompanyInformationFinder;
import nts.uk.ctx.sys.shared.dom.company.CompanyInformationImport;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.application.auth.WindowsAccount;

/**
 * The Class LoginWs.
 */
@Path("ctx/sys/gateway/login")
@Produces("application/json")
public class LoginWs extends WebService {

	/** The company information finder. */
	@Inject
	private CompanyInformationFinder companyInformationFinder;

	@Inject
	private PasswordAuthenticateCommandHandler passwordAuthenticateCommandHandler;
	
	/** The Constant SIGN_ON. */
	private static final String SIGN_ON = "on";


	/**
	 * Gets the all company.
	 *
	 * @return the all company
	 */
	@POST
	@Path("getcompany/{contractCode}")
	public List<CompanyInformationImport> getAllCompany(@PathParam("contractCode") String contractCode) {
		return companyInformationFinder.findAll(contractCode);
	}
	
	/**
	 * Gets the company infor by code.
	 *
	 * @param companyId the company id
	 * @return the company infor by code
	 */
	@POST
	@Path("getcompanybycode/{companyId}")
	public CompanyInformationImport getCompanyInforByCode(@PathParam("companyId") String companyId) {
		return companyInformationFinder.getCompanyInforByCode(companyId);
	}
	

	/**
	 * パスワード認証によるログイン
	 * @param request
	 * @param command
	 * @return
	 */
	@POST
	@Path("password")
	public CheckChangePassDto loginOnPasswordAuthenticate(@Context HttpServletRequest request, PasswordAuthenticateCommand command) {
		command.setRequest(request);
		return passwordAuthenticateCommandHandler.handle(command);
	}

	
	
	
	@GET
	@Path("mobile/token")
	public String getToken() {
		return CsrfToken.getFromSession();
	}
	
	/**
	 * Gets the windows account.
	 *
	 * @return the windows account
	 */
	@Path("account")
	@POST
	public WindowsAccountDto getWindowsAccount() {
		WindowsAccountDto dto = new WindowsAccountDto();
		WindowsAccount account = AppContexts.windowsAccount();
		dto.domain = account != null ? account.getDomain() : null;
		dto.userName = account != null ? account.getUserName() : null;
		return dto;
	}
	
	/**
	 * Gets the builds the time.
	 *
	 * @return the builds the time
	 */
	@POST
	@Path("build_info_time")
	public VerDto getBuildTime(@Context ServletContext context) {

		File file = new File(context.getRealPath("/view/ccg/007/a/ccg007.a.start.js"));

		if (!file.exists()) {
			return VerDto.builder().ver("Please build js file!").build();
		}

		return VerDto.builder().ver(GeneralDateTime.legacyDateTime(new Date(file.lastModified()))
				.toString("yyyy/MM/dd HH:mm")).build();
	}
	
	
//	こいつらちゃんと作らなアカンよ	（テナントロケータ込みで）
	/**
	 * Submit contract.
	 *
	 * @param command the command
	 */
//	@POST
//	@Path("submitcontract")
//	public void submitContract(SubmitContractFormCommand command) {
//		this.submitContract.handle(command);
//	}
//	
//	@POST
//	@Path("submit/mobile")
//	public CheckChangePassDto submitLoginMobile(@Context HttpServletRequest request, MobileLoginCommand command) {
//		command.setSignOn(false);
//		command.setRequest(request);
//		return this.mobileLoginHandler.handle(command);
//	}
//	
//	@POST
//	@Path("submit/mobile/nochangepass")
//	public CheckChangePassDto submitLoginWithNoChangePassMobile(@Context HttpServletRequest request, MobileLoginCommand command) {
//		command.setSignOn(false);
//		command.setRequest(request);
//		return this.mobileLoginNoChangePassHandler.handle(command);
//	}
}
