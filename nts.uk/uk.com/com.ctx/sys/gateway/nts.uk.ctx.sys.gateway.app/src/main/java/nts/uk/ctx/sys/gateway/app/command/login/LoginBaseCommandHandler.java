/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.command.login;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.gateway.dom.login.User;
import nts.uk.ctx.sys.gateway.dom.login.adapter.CompanyInformationAdapter;
import nts.uk.ctx.sys.gateway.dom.login.adapter.ListCompanyAdapter;
import nts.uk.ctx.sys.gateway.dom.login.adapter.RoleIndividualGrantAdapter;
import nts.uk.ctx.sys.gateway.dom.login.adapter.RoleType;
import nts.uk.ctx.sys.gateway.dom.login.adapter.SysEmployeeAdapter;
import nts.uk.ctx.sys.gateway.dom.login.dto.CompanyInformationImport;
import nts.uk.ctx.sys.gateway.dom.login.dto.EmployeeImport;
import nts.uk.ctx.sys.gateway.dom.login.dto.RoleIndividualGrantImport;
import nts.uk.shr.com.context.loginuser.LoginUserContextManager;

/**
 * The Class LoginBaseCommandHandler.
 *
 * @param <T> the generic type
 */
@Stateless
public abstract class LoginBaseCommandHandler<T> extends CommandHandler<T> {

	/** The employee adapter. */
	@Inject
	private SysEmployeeAdapter employeeAdapter;
	
	/** The company information adapter. */
	@Inject
	private CompanyInformationAdapter companyInformationAdapter;
	
	/** The role individual grant adapter. */
	@Inject
	private RoleIndividualGrantAdapter roleIndividualGrantAdapter;

	/** The list company adapter. */
	@Inject
	private ListCompanyAdapter listCompanyAdapter;
	
	/** The manager. */
	@Inject
	private LoginUserContextManager manager;
	
	/** The Constant FIST_COMPANY. */
	private static final Integer FIST_COMPANY = 0;
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

	/**
	 * Sets the logged info.
	 *
	 * @param user the user
	 * @param em the em
	 * @param companyCode the company code
	 */
	protected void setLoggedInfo(User user,EmployeeImport em,String companyCode) {
		//set info to session 
		manager.loggedInAsEmployee(user.getUserId(), em.getPersonalId(), user.getContractCode().v(), em.getCompanyId(),
				companyCode, em.getEmployeeId(), em.getEmployeeCode());
	}
	
	/**
	 * Inits the session.
	 *
	 * @param user the user
	 */
	//init session 
	protected void initSession(User user) {
		List<String> lstCompanyId = listCompanyAdapter.getListCompanyId(user.getUserId(), user.getAssociatedPersonId());
		if (lstCompanyId.isEmpty()) {
			manager.loggedInAsEmployee(user.getUserId(), user.getAssociatedPersonId(), user.getContractCode().v(), null,
					null, null, null);
		} else {
			// get employee
			Optional<EmployeeImport> opEm = this.employeeAdapter.getByPid(lstCompanyId.get(FIST_COMPANY),
					user.getAssociatedPersonId());

			// save to session
			CompanyInformationImport companyInformation = this.companyInformationAdapter
					.findById(opEm.get().getCompanyId());

			this.setLoggedInfo(user, opEm.get(), companyInformation.getCompanyCode());
		}
		this.setRoleId(user.getUserId());
	}
	
	/**
	 * Sets the role id.
	 *
	 * @param userId the new role id
	 */
	//set roll id into login user context 
	protected void setRoleId(String userId)
	{
		String employmentRoleId = this.getRoleId(userId, RoleType.EMPLOYMENT);
		String salaryRoleId = this.getRoleId(userId, RoleType.SALARY);
		String officeHelperRoleId = this.getRoleId(userId, RoleType.OFFICE_HELPER);
		String companyManagerRoleId = this.getRoleId(userId, RoleType.COMPANY_MANAGER);
		String systemManagerRoleId = this.getRoleId(userId, RoleType.SYSTEM_MANAGER);
		String personalInfoRoleId = this.getRoleId(userId, RoleType.PERSONAL_INFO);
		// 就業
		if (employmentRoleId != null) {
			manager.roleIdSetter().forPersonnel(employmentRoleId);
		}
		// 給与
		if (salaryRoleId != null) {
			manager.roleIdSetter().forPayroll(salaryRoleId);
		}
		// 人事

		// オフィスヘルパー
		if (officeHelperRoleId != null) {
			manager.roleIdSetter().forOfficeHelper(officeHelperRoleId);
		}
		// 会計
		// マイナンバー
		// グループ会社管理

		// 会社管理者
		if (companyManagerRoleId != null) {
			manager.roleIdSetter().forCompanyAdmin(companyManagerRoleId);
		}
		// システム管理者
		if (systemManagerRoleId != null) {
			manager.roleIdSetter().forSystemAdmin(systemManagerRoleId);
		}
		// 個人情報
		if (personalInfoRoleId != null) {
			manager.roleIdSetter().forPersonalInfo(personalInfoRoleId);
		}
	}

	/**
	 * Gets the role id.
	 *
	 * @param userId the user id
	 * @param roleType the role type
	 * @return the role id
	 */
	protected String getRoleId(String userId, RoleType roleType) {
		RoleIndividualGrantImport roleImport = roleIndividualGrantAdapter.getByUserAndRole(userId, roleType);
		if (roleImport == null) {
			return null;
		}
		return roleImport.getRoleId();
	}
}
