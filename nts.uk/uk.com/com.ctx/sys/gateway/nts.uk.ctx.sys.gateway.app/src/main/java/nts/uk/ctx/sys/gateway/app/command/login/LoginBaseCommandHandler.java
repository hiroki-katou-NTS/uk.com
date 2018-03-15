/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.command.login;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.gul.security.hash.password.PasswordHash;
import nts.gul.text.StringUtil;
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserImport;
import nts.uk.ctx.sys.gateway.dom.login.Contract;
import nts.uk.ctx.sys.gateway.dom.login.ContractCode;
import nts.uk.ctx.sys.gateway.dom.login.ContractRepository;
import nts.uk.ctx.sys.gateway.dom.login.InstallForm;
import nts.uk.ctx.sys.gateway.dom.login.SystemConfig;
import nts.uk.ctx.sys.gateway.dom.login.SystemConfigRepository;
import nts.uk.ctx.sys.gateway.dom.login.adapter.CompanyInformationAdapter;
import nts.uk.ctx.sys.gateway.dom.login.adapter.ListCompanyAdapter;
import nts.uk.ctx.sys.gateway.dom.login.adapter.RoleFromUserIdAdapter;
import nts.uk.ctx.sys.gateway.dom.login.adapter.RoleType;
import nts.uk.ctx.sys.gateway.dom.login.adapter.SysEmployeeAdapter;
import nts.uk.ctx.sys.gateway.dom.login.dto.CompanyInformationImport;
import nts.uk.ctx.sys.gateway.dom.login.dto.EmployeeDataMngInfoImport;
import nts.uk.ctx.sys.gateway.dom.login.dto.EmployeeImport;
import nts.uk.ctx.sys.gateway.dom.login.dto.SDelAtr;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.AccountLockPolicy;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.AccountLockPolicyRepository;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.logoutdata.LogType;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.logoutdata.LogoutData;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.logoutdata.LogoutDataDto;
import nts.uk.ctx.sys.gateway.dom.singlesignon.UseAtr;
import nts.uk.ctx.sys.gateway.dom.singlesignon.WindowAccount;
import nts.uk.ctx.sys.gateway.dom.singlesignon.WindowAccountRepository;
import nts.uk.shr.com.context.loginuser.LoginUserContextManager;

/**
 * The Class LoginBaseCommandHandler.
 *
 * @param <T>
 *            the generic type
 */
@Stateless
public abstract class LoginBaseCommandHandler<T> extends CommandHandler<T> {

	/** The employee adapter. */
	@Inject
	private SysEmployeeAdapter employeeAdapter;

	/** The company information adapter. */
	@Inject
	private CompanyInformationAdapter companyInformationAdapter;

	/** The list company adapter. */
	@Inject
	private ListCompanyAdapter listCompanyAdapter;

	/** The manager. */
	@Inject
	private LoginUserContextManager manager;

	/** The system config repository. */
	@Inject
	private SystemConfigRepository systemConfigRepository;

	/** The contract repository. */
	@Inject
	private ContractRepository contractRepository;

	/** The role from user id adapter. */
	@Inject
	private RoleFromUserIdAdapter roleFromUserIdAdapter;

	@Inject
	private WindowAccountRepository windowAccountRepository;
	
	@Inject
	private UserAdapter userAdapter;
	
	@Inject
	private AccountLockPolicyRepository accountLockPolicyRepository;
	
//	@Inject
//	private LogoutDataRepository logoutDataRepository;
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

	protected void reCheckContract(String contractCode, String contractPassword) {
		SystemConfig systemConfig = this.getSystemConfig();
		// case Cloud
		if (systemConfig.getInstallForm().value == InstallForm.Cloud.value) {
			// reCheck contract
			// pre check contract
			this.checkContractInput(contractCode, contractPassword);
			// contract auth
			this.contractAccAuth(contractCode, contractPassword);
		}
	}

	/**
	 * Check contract input.
	 *
	 * @param command
	 *            the command
	 */
	private void checkContractInput(String contractCode, String contractPassword) {
		if (StringUtil.isNullOrEmpty(contractCode, true)) {
			throw new RuntimeException();
		}
		if (StringUtil.isNullOrEmpty(contractPassword, true)) {
			throw new RuntimeException();
		}
	}

	/**
	 * Check employee del status.
	 *
	 * @param sId the s id
	 */
	protected void checkEmployeeDelStatus(String sid) {
		// get Employee status
		Optional<EmployeeDataMngInfoImport> optMngInfo = this.employeeAdapter.getSdataMngInfo(sid);

		if (!optMngInfo.isPresent()
				|| !SDelAtr.NOTDELETED.equals(optMngInfo.get().getDeletedStatus())) {
			throw new BusinessException("Msg_301");
		}
	}

	/**
	 * Contract acc auth.
	 *
	 * @param command
	 *            the command
	 */
	private void contractAccAuth(String contractCode, String contractPassword) {
		Optional<Contract> contract = contractRepository.getContract(contractCode);
		if (contract.isPresent()) {
			// check contract pass
			if (!PasswordHash.verifyThat(contractPassword, contract.get().getContractCode().v())
					.isEqualTo(contract.get().getPassword().v())) {
				throw new RuntimeException();
			}
			// check contract time
			if (contract.get().getContractPeriod().start().after(GeneralDate.today())
					|| contract.get().getContractPeriod().end().before(GeneralDate.today())) {
				throw new RuntimeException();
			}
		} else {
			throw new RuntimeException();
		}
	}

	/**
	 * Sets the logged info.
	 *
	 * @param user
	 *            the user
	 * @param em
	 *            the em
	 * @param companyCode
	 *            the company code
	 */
	protected void setLoggedInfo(UserImport user, EmployeeImport em, String companyCode) {
		// set info to session
		manager.loggedInAsEmployee(user.getUserId(), em.getPersonalId(), user.getContractCode(),
				em.getCompanyId(), companyCode, em.getEmployeeId(), em.getEmployeeCode());
	}

	/**
	 * Inits the session.
	 *
	 * @param user
	 *            the user
	 */
	// init session
	protected void initSession(UserImport user) {
		List<String> lstCompanyId = listCompanyAdapter.getListCompanyId(user.getUserId(),
				user.getAssociatePersonId());
		if (lstCompanyId.isEmpty()) {
			manager.loggedInAsEmployee(user.getUserId(), user.getAssociatePersonId(),
					user.getContractCode(), null, null, null, null);
		} else {
			// get employee
			Optional<EmployeeImport> opEm = this.employeeAdapter.getByPid(lstCompanyId.get(FIST_COMPANY),
					user.getAssociatePersonId());

			if (opEm.isPresent()) {
				// Check employee deleted status.
				this.checkEmployeeDelStatus(opEm.get().getEmployeeId());
			}

			// save to session
			CompanyInformationImport companyInformation = this.companyInformationAdapter
					.findById(lstCompanyId.get(FIST_COMPANY));
			if (opEm.isPresent()) {
				// set info to session if em # null
				manager.loggedInAsEmployee(user.getUserId(), user.getAssociatePersonId(), user.getContractCode(),
						companyInformation.getCompanyId(), companyInformation.getCompanyCode(),
						opEm.get().getEmployeeId(), opEm.get().getEmployeeCode());
			} else {
				// set info to session
				manager.loggedInAsEmployee(user.getUserId(), user.getAssociatePersonId(), user.getContractCode(),
						companyInformation.getCompanyId(), companyInformation.getCompanyCode(), null, null);
			}
		}
		this.setRoleId(user.getUserId());
	}

	/**
	 * Sets the role id.
	 *
	 * @param userId
	 *            the new role id
	 */
	// set roll id into login user context
	protected void setRoleId(String userId) {
		String humanResourceRoleId = this.getRoleId(userId, RoleType.HUMAN_RESOURCE);
		String employmentRoleId = this.getRoleId(userId, RoleType.EMPLOYMENT);
		String salaryRoleId = this.getRoleId(userId, RoleType.SALARY);
		String officeHelperRoleId = this.getRoleId(userId, RoleType.OFFICE_HELPER);
		String companyManagerRoleId = this.getRoleId(userId, RoleType.COMPANY_MANAGER);
		String systemManagerRoleId = this.getRoleId(userId, RoleType.SYSTEM_MANAGER);
		String personalInfoRoleId = this.getRoleId(userId, RoleType.PERSONAL_INFO);
		// 就業
		if (employmentRoleId != null) {
			manager.roleIdSetter().forAttendance(employmentRoleId);
		}
		// 給与
		if (salaryRoleId != null) {
			manager.roleIdSetter().forPayroll(salaryRoleId);
		}
		// 人事
		if (humanResourceRoleId != null) {
			manager.roleIdSetter().forPersonnel(humanResourceRoleId);
		}
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
	 * @param userId
	 *            the user id
	 * @param roleType
	 *            the role type
	 * @return the role id
	 */
	protected String getRoleId(String userId, RoleType roleType) {
		String roleId = roleFromUserIdAdapter.getRoleFromUser(userId, roleType.value,
				GeneralDate.today());
		if (roleId == null || roleId.isEmpty()) {
			return null;
		}
		return roleId;
	}

	/**
	 * Gets the system config.
	 *
	 * @return the system config
	 */
	private SystemConfig getSystemConfig() {
		Optional<SystemConfig> systemConfig = systemConfigRepository.getSystemConfig();
		if (systemConfig.isPresent()) {
			return systemConfig.get();
		}
		return null;
	}
	
	/**
	 * Compare hash password.
	 *
	 * @param user the user
	 * @param password the password
	 */
	protected void compareHashPassword(UserImport user, String password) {
		if (!PasswordHash.verifyThat(password, user.getUserId()).isEqualTo(user.getPassword())) {
			//アルゴリズム「ロックアウト」を実行する　※２次対応
			this.logoutExecuted(user);
			throw new BusinessException("Msg_302");
		}
	}
	
	private void logoutExecuted(UserImport user)
	{
		//TODO get count failure
		Integer countFailure = 0;
		
		//ドメインモデル「アカウントロックポリシー」を取得する
		AccountLockPolicy accountLockPolicy = this.accountLockPolicyRepository.getAccountLockPolicy(new ContractCode(user.getContractCode())).get();
		if (accountLockPolicy.isUse()) {
			// ロックアウト条件に満たしているかをチェックする
			if (countFailure < accountLockPolicy.getErrorCount().v().intValue()) {
				return;
			} else {
				// ドメインモデル「ロックアウトデータ」に１件レコードを追加する
				LogoutDataDto dto = LogoutDataDto.builder().contractCode(user.getContractCode())
						.userId(user.getUserId()).logoutDateTime(GeneralDate.today()).logType(LogType.AUTO_LOCK.value)
						.build();
				LogoutData logoutData = new LogoutData(dto);
//				this.logoutDataRepository.add(logoutData);

				// エラーメッセージ（ドメインモデル「アカウントロックポリシー.ロックアウトメッセージ」）を表示する
				throw new BusinessException(accountLockPolicy.getLockOutMessage().v());
			}

		}
	}
	
	//アルゴリズム「アカウント照合」を実行する
	protected void compareAccount()
	{
		// Windowsログイン時のアカウントを取得する
		//TODO get UserName and HostName
		//need confirm kiban
		
		String username = "";
		String hostname = "";
		String userId = "";
		
		//ドメインモデル「Windowsアカウント情報」を取得する
		
		//ログイン時アカウントとドメインモデル「Windowsアカウント情報」を比較する - get 「Windowsアカウント情報」 from 「Windowsアカウント」
		//TODO chuyen tu List<WindowAccount> sang 1 object WindowAccount sau khi update cmm021
		Optional<WindowAccount> opWindowAccount = this.windowAccountRepository.findbyUserNameAndHostName(username, hostname);
		
		if (opWindowAccount.isPresent()) {
			// エラーメッセージ（#Msg_876）を表示する。
			throw new BusinessException("Msg_876");
		} else {
			if (opWindowAccount.get().getUseAtr().equals(UseAtr.NotUse)) {
				throw new BusinessException("Msg_876");
			} else {
				this.getUserAndCheckLimitTime(opWindowAccount.get());
			}
		}
	}
	
	private void getUserAndCheckLimitTime(WindowAccount windowAccount) {
		// get user
		Optional<UserImport> optUserImport = this.userAdapter.findByUserId(windowAccount.getUserId());

		// Validate limit time
		if (optUserImport.isPresent()) {
			if (optUserImport.get().getExpirationDate().after(GeneralDate.today())) {
				throw new BusinessException("Msg_316");
			}
		}
		else
		{
			//TODO
		}
	}
}
