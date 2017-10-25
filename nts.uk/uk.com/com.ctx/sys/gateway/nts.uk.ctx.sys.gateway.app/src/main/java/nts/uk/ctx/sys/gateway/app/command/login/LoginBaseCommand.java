/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.command.login;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.gateway.dom.login.User;
import nts.uk.ctx.sys.gateway.dom.login.adapter.CompanyInformationAdapter;
import nts.uk.ctx.sys.gateway.dom.login.adapter.RoleIndividualGrantAdapter;
import nts.uk.ctx.sys.gateway.dom.login.adapter.RoleType;
import nts.uk.ctx.sys.gateway.dom.login.adapter.SysEmployeeAdapter;
import nts.uk.ctx.sys.gateway.dom.login.dto.CompanyInformationImport;
import nts.uk.ctx.sys.gateway.dom.login.dto.EmployeeImport;
import nts.uk.ctx.sys.gateway.dom.login.dto.RoleIndividualGrantImport;
import nts.uk.shr.com.context.loginuser.LoginUserContextManager;

/**
 * The Class BaseCommand.
 *
 * @param <T>
 *            the generic type
 */
@Stateless
public abstract class LoginBaseCommand<T> extends CommandHandler<T> {

	/** The employee adapter. */
	@Inject
	private SysEmployeeAdapter employeeAdapter;
	
	/** The company information adapter. */
	@Inject
	private CompanyInformationAdapter companyInformationAdapter;
	
	/** The role individual grant adapter. */
	@Inject
	private RoleIndividualGrantAdapter roleIndividualGrantAdapter;

	@Inject
	private LoginUserContextManager manager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<T> context) {
		this.internalHanler(context);
	}

	/**
	 * Internal hanler.
	 *
	 * @param context the context
	 */
	protected abstract void internalHanler(CommandHandlerContext<T> context);

	protected void setLoggedInfo(User user) {
		EmployeeImport em = this.getEmployeeInfo("", "");
		CompanyInformationImport com = this.getCompanyInfo("");
		if (em == null || com == null) {
			throw new BusinessException("");
		}
		//set info to session 
		manager.loggedInAsEmployee(user.getUserId(), em.getPersonalId(), user.getContractCode().v(), em.getCompanyId(),
				com.getCompanyCode(), em.getEmployeeId(), em.getEmployeeCode());
	}

	protected EmployeeImport getEmployeeInfo(String companyId, String employeeCode) {
		// TODO
		EmployeeImport em = employeeAdapter.getCurrentInfoByScd(companyId, employeeCode).get();
		return em;
	}
	
	protected CompanyInformationImport getCompanyInfo(String companyId) {
		// TODO
		CompanyInformationImport com = companyInformationAdapter.findAll().get(0);
		return com;
	}
	
	protected void setRoleId(String userId)
	{
		if (this.getRoleId(userId, RoleType.COMPANY_MANAGER) != null) {
			manager.roleIdSetter().forCompanyAdmin(this.getRoleId(userId, RoleType.COMPANY_MANAGER));
		}

		if (this.getRoleId(userId, RoleType.SYSTEM_MANAGER) != null) {
			manager.roleIdSetter().forSystemAdmin(this.getRoleId(userId, RoleType.SYSTEM_MANAGER));
		}

		if (this.getRoleId(userId, RoleType.OFFICE_HELPER) != null) {
			manager.roleIdSetter().forOfficeHelper(this.getRoleId(userId, RoleType.OFFICE_HELPER));
		}

		if (this.getRoleId(userId, RoleType.SALARY) != null) {
			manager.roleIdSetter().forPayroll(this.getRoleId(userId, RoleType.SALARY));
		}

		if (this.getRoleId(userId, RoleType.EMPLOYMENT) != null) {
			manager.roleIdSetter().forPersonnel(this.getRoleId(userId, RoleType.EMPLOYMENT));
		}

		if (this.getRoleId(userId, RoleType.PERSONAL_INFO) != null) {
			manager.roleIdSetter().forPersonalInfo(this.getRoleId(userId, RoleType.PERSONAL_INFO));
		}
	}

	protected String getRoleId(String userId, RoleType roleType) {
		RoleIndividualGrantImport roleImport = roleIndividualGrantAdapter.getByUserAndRole(userId, roleType);
		if (roleImport == null) {
			return null;
		}
		return roleImport.getRoleId();
	}
}
