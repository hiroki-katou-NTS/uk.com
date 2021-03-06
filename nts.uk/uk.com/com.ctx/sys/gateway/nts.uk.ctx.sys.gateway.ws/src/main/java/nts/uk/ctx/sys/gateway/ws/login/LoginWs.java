/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.ws.login;

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

import lombok.val;
import nts.arc.layer.ws.WebService;
import nts.arc.security.csrf.CsrfToken;
import nts.uk.ctx.sys.gateway.app.command.login.password.CheckChangePassDto;
import nts.uk.ctx.sys.gateway.app.command.login.password.MobileLoginCommand;
import nts.uk.ctx.sys.gateway.app.command.login.password.PasswordAuthenticateCommand;
import nts.uk.ctx.sys.gateway.app.command.login.password.PasswordAuthenticateCommandHandler;
import nts.uk.ctx.sys.gateway.app.command.login.url.UrlLoginCommand;
import nts.uk.ctx.sys.gateway.app.command.login.url.UrlLoginCommandHandler;
import nts.uk.ctx.sys.gateway.app.command.tenantlogin.TenantAuthenticateCommand;
import nts.uk.ctx.sys.gateway.app.command.tenantlogin.TenantAuthenticateCommandHandler;
import nts.uk.ctx.sys.gateway.app.command.tenantlogin.TenantCheckCommandHandler;
import nts.uk.ctx.sys.gateway.app.find.login.CompanyInformationFinder;
import nts.uk.ctx.sys.gateway.app.find.login.EmployeeLoginSettingFinder;
import nts.uk.ctx.sys.gateway.app.find.login.dto.CheckContractDto;
import nts.uk.ctx.sys.gateway.app.find.login.dto.EmployeeLoginSettingDto;
import nts.uk.ctx.sys.gateway.dom.login.password.authenticate.InspectionResultDto;
import nts.uk.ctx.sys.gateway.dom.login.password.authenticate.QrVerifyEmployeeCodeAndPassword;
import nts.uk.ctx.sys.gateway.app.command.login.url.UrlResult;
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
	private TenantCheckCommandHandler tenantCheckCommandHandler;
	
	@Inject
	private TenantAuthenticateCommandHandler tenantAuthenticateCommandHandler;

	@Inject
	private PasswordAuthenticateCommandHandler passwordAuthenticateCommandHandler;
	
	/** The employee login setting finder. */
	@Inject
	private EmployeeLoginSettingFinder employeeLoginSettingFinder;
	
	@Inject
	private QrVerifyEmployeeCodeAndPassword qrVerifyEmployeeCodeAndPassword;
	
	@Inject
	private UrlLoginCommandHandler urlLoginCommandHandler;
	
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
	 * ???????????????????????????
	 *
	 * @param command the command
	 * @return the check contract dto
	 */
	@POST
	@Path("checkcontract")
	public CheckContractDto checkContractForm1(@Context HttpServletRequest request, TenantAuthenticateCommand command) {
		command.setRequest(request);
		return this.tenantCheckCommandHandler.handle(command);
	}
	
	/**
	 * ??????????????????
	 *
	 * @param command the command
	 */
	@POST
	@Path("submitcontract")
	public void submitContract(@Context HttpServletRequest request, TenantAuthenticateCommand command) {
		command.setRequest(request);
		this.tenantAuthenticateCommandHandler.handle(command);
	}
	
	/**
	 * ??????????????????????????????????????????
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
	
	/**
	 * ??????????????????????????????????????????????????????
	 * @param request
	 * @param command
	 * @return
	 */
	@POST
	@Path("submit/mobile")
	public CheckChangePassDto submitLoginMobile(@Context HttpServletRequest request, MobileLoginCommand command) {
		command.setRequest(request);
		val result = passwordAuthenticateCommandHandler.handle(command);
		return result;
	}
	
	/**
	 * ???????????????????????????????????????????????????????????????
	 * @param request
	 * @param command
	 * @return
	 */
	@POST
	@Path("submit/mobile/nochangepass")
	public CheckChangePassDto submitLoginWithNoChangePassMobile(@Context HttpServletRequest request, MobileLoginCommand command) {
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
		// ?????????????????????????????????????????????????????????????????????????????????
		return VerDto.builder().ver("Ver.1.1.3-1").build();
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
	
	@POST
	@Path("verify_employeecode_and_password")
	public InspectionResultDto verifiLogin(VerifyEmployeeCodeAndPasswordInput param) {
		return this.qrVerifyEmployeeCodeAndPassword.verifyEmployeeCodeAndPassword(param.getCid(), param.getEmployeeCode(), param.getPassword());
	}
	
	/**
	 * URL????????????
	 * @param urlID
	 * @return
	 */
	@POST
	@Path("url/{tenantCode}/{urlID}")
	@Produces("application/json")
	public UrlResult loginByUrl(@Context HttpServletRequest request, @PathParam("tenantCode") String tenantCode, @PathParam("urlID") String urlID) {
		return urlLoginCommandHandler.handle(new UrlLoginCommand(urlID, tenantCode, "", request));
	}
}
