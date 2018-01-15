/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.ws.login;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.gateway.app.command.login.LocalContractFormCommand;
import nts.uk.ctx.sys.gateway.app.command.login.LocalContractFormCommandHandler;
import nts.uk.ctx.sys.gateway.app.command.login.SubmitContractFormCommand;
import nts.uk.ctx.sys.gateway.app.command.login.SubmitContractFormCommandHandler;
import nts.uk.ctx.sys.gateway.app.command.login.SubmitLoginFormOneCommand;
import nts.uk.ctx.sys.gateway.app.command.login.SubmitLoginFormOneCommandHandler;
import nts.uk.ctx.sys.gateway.app.command.login.SubmitLoginFormThreeCommand;
import nts.uk.ctx.sys.gateway.app.command.login.SubmitLoginFormThreeCommandHandler;
import nts.uk.ctx.sys.gateway.app.command.login.SubmitLoginFormTwoCommand;
import nts.uk.ctx.sys.gateway.app.command.login.SubmitLoginFormTwoCommandHandler;
import nts.uk.ctx.sys.gateway.app.command.login.dto.CheckContractDto;
import nts.uk.ctx.sys.gateway.app.find.login.CompanyInformationFinder;
import nts.uk.ctx.sys.gateway.app.find.login.EmployeeLoginSettingFinder;
import nts.uk.ctx.sys.gateway.app.find.login.dto.EmployeeLoginSettingDto;
import nts.uk.ctx.sys.gateway.dom.login.dto.CompanyInformationImport;

/**
 * The Class LoginWs.
 */
@Path("ctx/sys/gateway/login")
@Produces("application/json")
@Stateless
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
	 * @param command the command
	 */
	@POST
	@Path("submit/form1")
	public void submitLoginForm1(SubmitLoginFormOneCommand command) {
		this.submitForm1.handle(command);
	}

	/**
	 * Submit login form 2.
	 *
	 * @param command the command
	 */
	@POST
	@Path("submit/form2")
	public void submitLoginForm2(SubmitLoginFormTwoCommand command) {
		this.submitForm2.handle(command);
	}

	/**
	 * Gets the all company.
	 *
	 * @return the all company
	 */
	@POST
	@Path("getcompany")
	public List<CompanyInformationImport> getAllCompany() {
		return companyInformationFinder.findAll();
	}

	/**
	 * Submit login form 3.
	 *
	 * @param command the command
	 */
	@POST
	@Path("submit/form3")
	public void submitLoginForm3(SubmitLoginFormThreeCommand command) {
		this.submitForm3.handle(command);
	}
}
