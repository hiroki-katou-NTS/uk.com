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
import nts.uk.ctx.sys.gateway.app.command.loginold.LocalContractFormCommand;
import nts.uk.ctx.sys.gateway.app.command.loginold.LocalContractFormCommandHandler;
import nts.uk.ctx.sys.gateway.app.command.loginold.MobileLoginCommand;
import nts.uk.ctx.sys.gateway.app.command.loginold.MobileLoginCommandHandler;
import nts.uk.ctx.sys.gateway.app.command.loginold.MobileLoginWithNoChangePassCommandHandler;
import nts.uk.ctx.sys.gateway.app.command.loginold.SubmitContractFormCommand;
import nts.uk.ctx.sys.gateway.app.command.loginold.SubmitContractFormCommandHandler;
import nts.uk.ctx.sys.gateway.app.command.loginold.SubmitLoginFormOneCommand;
import nts.uk.ctx.sys.gateway.app.command.loginold.SubmitLoginFormOneCommandHandler;
import nts.uk.ctx.sys.gateway.app.command.loginold.SubmitLoginFormThreeCommand;
import nts.uk.ctx.sys.gateway.app.command.loginold.SubmitLoginFormThreeCommandHandler;
import nts.uk.ctx.sys.gateway.app.command.loginold.SubmitLoginFormTwoCommand;
import nts.uk.ctx.sys.gateway.app.command.loginold.SubmitLoginFormTwoCommandHandler;
import nts.uk.ctx.sys.gateway.app.command.loginold.dto.CheckContractDto;
import nts.uk.ctx.sys.gateway.app.find.login.CompanyInformationFinder;
import nts.uk.ctx.sys.gateway.app.find.login.EmployeeLoginSettingFinder;
import nts.uk.ctx.sys.gateway.app.find.login.dto.EmployeeLoginSettingDto;
import nts.uk.ctx.sys.shared.dom.company.CompanyInformationImport;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.application.auth.WindowsAccount;

/**
 * The Class LoginWs.
 */
@Path("ctx/sys/gateway/login")
@Produces("application/json")
public class LoginWs extends WebService {

	/** The local contract form command handler. */
	@Inject
	private LocalContractFormCommandHandler localContractFormCommandHandler;

	/** The employee login setting finder. */
	@Inject
	private EmployeeLoginSettingFinder employeeLoginSettingFinder;

	/** The submit contract. */
	@Inject
	private SubmitContractFormCommandHandler submitContract;

	/** The submit form 1. */
	@Inject
	private SubmitLoginFormOneCommandHandler submitForm1;

	/** The submit form 2. */
	@Inject
	private SubmitLoginFormTwoCommandHandler submitForm2;

	/** The company information finder. */
	@Inject
	private CompanyInformationFinder companyInformationFinder;

	/** The submit form 3. */
	@Inject
	private SubmitLoginFormThreeCommandHandler submitForm3;
	
	@Inject
	private MobileLoginCommandHandler mobileLoginHandler;
	
	@Inject
	private MobileLoginWithNoChangePassCommandHandler mobileLoginNoChangePassHandler;

	@Inject
	private PasswordAuthenticateCommandHandler passwordAuthenticateCommandHandler;
	
	/** The Constant SIGN_ON. */
	private static final String SIGN_ON = "on";
	/**
	 * Check contract form 1.
	 *
	 * @param command the command
	 * @return the check contract dto
	 */
	@POST
	@Path("checkcontract")
	public CheckContractDto checkContractForm1(LocalContractFormCommand command) {
		return this.localContractFormCommandHandler.handle(command);
	}

	/**
	 * Gets the employee login setting form 2.
	 *
	 * @param contractCode the contract code
	 * @return the employee login setting form 2
	 */
	@POST
	@Path("emlogsettingform2/{contractCode}")
	public EmployeeLoginSettingDto getEmployeeLoginSettingForm2(@PathParam("contractCode") String contractCode) {
		return this.employeeLoginSettingFinder.findByContractCodeForm2(contractCode);
	}

	/**
	 * Gets the employee login setting form 3.
	 *
	 * @param contractCode the contract code
	 * @return the employee login setting form 3
	 */
	@POST
	@Path("emlogsettingform3/{contractCode}")
	public EmployeeLoginSettingDto getEmployeeLoginSettingForm3(@PathParam("contractCode") String contractCode) {
		return this.employeeLoginSettingFinder.findByContractCodeForm3(contractCode);
	}

	/**
	 * Submit contract.
	 *
	 * @param command the command
	 */
	@POST
	@Path("submitcontract")
	public void submitContract(SubmitContractFormCommand command) {
		this.submitContract.handle(command);
	}

	/**
	 * Submit login form 1.
	 *
	 * @param request the request
	 * @param command the command
	 * @return the java type result
	 */
	@POST
	@Path("submit/form1")
	public CheckChangePassDto submitLoginForm1(@Context HttpServletRequest request,SubmitLoginFormOneCommand command) {
		if (request.getParameter("signon") != null){
			command.setSignOn(request.getParameter("signon").toLowerCase().equals(SIGN_ON));
		}
		command.setRequest(request);
		return this.submitForm1.handle(command);
	}

	/**
	 * Submit login form 2.
	 *
	 * @param request the request
	 * @param command the command
	 * @return the java type result
	 */
	@POST
	@Path("submit/form2")
	public CheckChangePassDto submitLoginForm2(@Context HttpServletRequest request,SubmitLoginFormTwoCommand command) {
		if (request.getParameter("signon") != null){
			command.setSignOn(request.getParameter("signon").toLowerCase().equals(SIGN_ON));
		}
		command.setRequest(request);
		return this.submitForm2.handle(command);
	}

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
	 * Submit login form 3.
	 *
	 * @param request the request
	 * @param command the command
	 * @return the java type result
	 */
	@POST
	@Path("submit/form3")
	public CheckChangePassDto submitLoginForm3(@Context HttpServletRequest request,SubmitLoginFormThreeCommand command) {
		if (request.getParameter("signon") != null){
			command.setSignOn(request.getParameter("signon").toLowerCase().equals(SIGN_ON));
		}
		command.setRequest(request);
		return this.submitForm3.handle(command);
	}
	
	@GET
	@Path("mobile/token")
	public String getToken() {
		return CsrfToken.getFromSession();
	}
	
	@POST
	@Path("submit/mobile")
	public CheckChangePassDto submitLoginMobile(@Context HttpServletRequest request, MobileLoginCommand command) {
		command.setSignOn(false);
		command.setRequest(request);
		return this.mobileLoginHandler.handle(command);
	}
	
	@POST
	@Path("submit/mobile/nochangepass")
	public CheckChangePassDto submitLoginWithNoChangePassMobile(@Context HttpServletRequest request, MobileLoginCommand command) {
		command.setSignOn(false);
		command.setRequest(request);
		return this.mobileLoginNoChangePassHandler.handle(command);
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
	
	/**
	 * Gets the company infor by code.
	 *
	 * @param companyId the company id
	 * @return the company infor by code
	 */
	@POST
	@Path("password")
	public CheckChangePassDto loginOnPasswordAuthenticate(@Context HttpServletRequest request,PasswordAuthenticateCommand command) {
		return passwordAuthenticateCommandHandler.handle(command);
	}

}
