/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.command.loginold;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.security.hash.password.PasswordHash;
import nts.gul.text.StringUtil;
import nts.uk.ctx.sys.gateway.app.command.login.password.CheckChangePassDto;
import nts.uk.ctx.sys.gateway.app.command.loginold.dto.ParamLoginRecord;
import nts.uk.ctx.sys.gateway.app.command.loginold.dto.SignonEmployeeInfoData;
import nts.uk.ctx.sys.gateway.dom.adapter.company.CompanyBsAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.company.CompanyBsImport;
import nts.uk.ctx.sys.gateway.dom.adapter.employee.EmployeeInfoAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.employee.EmployeeInfoDtoImport;
import nts.uk.ctx.sys.gateway.dom.adapter.employee.StatusOfEmployment;
import nts.uk.ctx.sys.gateway.dom.adapter.employment.GwSyEmploymentAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.employment.SEmpHistImport;
import nts.uk.ctx.sys.gateway.dom.adapter.status.employment.StatusEmploymentAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.status.employment.StatusOfEmploymentImport;
import nts.uk.ctx.sys.gateway.dom.adapter.syjobtitle.EmployeeJobHistImport;
import nts.uk.ctx.sys.gateway.dom.adapter.syjobtitle.GwSyJobTitleAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.syworkplace.GwSyWorkplaceAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.syworkplace.SWkpHistImport;
import nts.uk.ctx.sys.gateway.dom.adapter.user.CheckBeforeChangePass;
import nts.uk.ctx.sys.gateway.dom.adapter.user.PassStatus;
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserImportNew;
import nts.uk.ctx.sys.gateway.dom.loginold.Contract;
import nts.uk.ctx.sys.gateway.dom.loginold.ContractCode;
import nts.uk.ctx.sys.gateway.dom.loginold.ContractRepository;
import nts.uk.ctx.sys.gateway.dom.loginold.LoginStatus;
import nts.uk.ctx.sys.gateway.dom.loginold.adapter.RoleAdapter;
import nts.uk.ctx.sys.gateway.dom.loginold.adapter.RoleIndividualGrantAdapter;
import nts.uk.ctx.sys.gateway.dom.loginold.dto.EmployeeGeneralInfoAdapter;
import nts.uk.ctx.sys.gateway.dom.loginold.dto.EmployeeGeneralInfoImport;
import nts.uk.ctx.sys.gateway.dom.loginold.dto.RoleImport;
import nts.uk.ctx.sys.gateway.dom.loginold.dto.RoleIndividualGrantImport;
import nts.uk.ctx.sys.gateway.dom.loginold.dto.SDelAtr;
import nts.uk.ctx.sys.gateway.dom.loginold.service.CollectCompanyList;
import nts.uk.ctx.sys.gateway.dom.role.RoleFromUserIdAdapter;
import nts.uk.ctx.sys.gateway.dom.role.RoleType;
import nts.uk.ctx.sys.gateway.dom.role.RoleFromUserIdAdapter.RoleInfoImport;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.AccountLockPolicy;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.AccountLockPolicyRepository;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.LockInterval;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.locked.LockOutData;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.locked.LockOutDataDto;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.locked.LockOutDataRepository;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.locked.LockType;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.locked.LoginMethod;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.loginlog.LoginLog;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.loginlog.LoginLogDto;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.loginlog.LoginLogRepository;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.loginlog.OperationSection;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.loginlog.SuccessFailureClassification;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.PasswordPolicy;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.PasswordPolicyRepository;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.service.NotifyResult;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.service.PassWordPolicyAlgorithm;
import nts.uk.ctx.sys.gateway.dom.singlesignon.UseAtr;
import nts.uk.ctx.sys.gateway.dom.singlesignon.WindowsAccount;
import nts.uk.ctx.sys.gateway.dom.singlesignon.WindowsAccountInfo;
import nts.uk.ctx.sys.gateway.dom.singlesignon.WindowsAccountRepository;
import nts.uk.ctx.sys.shared.dom.company.CompanyInforImport;
import nts.uk.ctx.sys.shared.dom.company.CompanyInformationAdapter;
import nts.uk.ctx.sys.shared.dom.company.CompanyInformationImport;
import nts.uk.ctx.sys.shared.dom.employee.EmployeeDataMngInfoImport;
import nts.uk.ctx.sys.shared.dom.employee.EmployeeImport;
import nts.uk.ctx.sys.shared.dom.employee.EmployeeImportNew;
import nts.uk.ctx.sys.shared.dom.employee.SysEmployeeAdapter;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.loginuser.LoginUserContextManager;
import nts.uk.shr.com.context.loginuser.role.DefaultLoginUserRoles;
import nts.uk.shr.com.context.loginuser.role.LoginUserRoles;
import nts.uk.shr.com.enumcommon.Abolition;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.system.config.InstallationType;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * The Class TimeStampLoginCommonHandler.
 *
 * @param <T>
 *            the generic type
 */
@Stateless
public abstract class LoginBaseCommandHandler<T> extends CommandHandlerWithResult<T, CheckChangePassDto> {

	/** The employee adapter. */
	@Inject
	private SysEmployeeAdapter employeeAdapter;

	/** The company information adapter. */
	@Inject
	private CompanyInformationAdapter companyInformationAdapter;

	/** The manager. */
	@Inject
	private LoginUserContextManager manager;

	/** The contract repository. */
	@Inject
	private ContractRepository contractRepository;

	/** The role from user id adapter. */
	@Inject
	private RoleFromUserIdAdapter roleFromUserIdAdapter;

	/** The window account repository. */
	@Inject
	private WindowsAccountRepository windowAccountRepository;

	/** The user adapter. */
	@Inject
	private UserAdapter userAdapter;

	/** The account lock policy repository. */
	@Inject
	private AccountLockPolicyRepository accountLockPolicyRepository;

	/** The login log repository. */
	@Inject
	private LoginLogRepository loginLogRepository;

	/** The lock out data repository. */
	@Inject
	private LockOutDataRepository lockOutDataRepository;

	/** The Constant FIST_COMPANY. */
	private static final Integer FIST_COMPANY = 0;

	/** The role individual grant adapter. */
	@Inject
	private RoleIndividualGrantAdapter roleIndividualGrantAdapter;

	/** The Password policy repo. */
	@Inject
	private PasswordPolicyRepository pwPolicyRepo;

	/** The role adapter. */
	@Inject
	private RoleAdapter roleAdapter;

	/** The employee info adapter. */
	@Inject
	private EmployeeInfoAdapter employeeInfoAdapter;

	/** The company bs adapter. */
	@Inject
	private CompanyBsAdapter companyBsAdapter;
	
	/** The service. */
	@Inject
	private LoginRecordRegistService service;
	
	@Inject
	private EmployeeGeneralInfoAdapter employeeGeneralInfoAdapter;
	
	@Inject
	private StatusEmploymentAdapter statusEmploymentAdapter;
	@Inject
	private CollectCompanyList collectComList;
	
	/** The sy job title adapter. */
	@Inject
	private GwSyJobTitleAdapter syJobTitleAdapter;
	
	/** The sy workplace adapter. */
	@Inject
	private GwSyWorkplaceAdapter syWorkplaceAdapter;
	
	/** The sy employment adapter. */
	@Inject
	private GwSyEmploymentAdapter syEmploymentAdapter;
	
	@Inject
	private PassWordPolicyAlgorithm pWPolicyAlg;
	@Inject
	private LoginLogRepository loginLogRepo;
	
	private static final boolean IS_EMPLOYMENT = true;
	private static final boolean IS_CLASSIFICATION = false;
	private static final boolean IS_JOBTITLE = true;
	private static final boolean IS_WORKPLACE = true;
	private static final boolean IS_DEPARTMENT = false;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected CheckChangePassDto handle(CommandHandlerContext<T> context) {
		return this.internalHanler(context);
	}

	/**
	 * Internal hanler.
	 *
	 * @param context the context
	 * @return the check change pass dto
	 */
	protected abstract CheckChangePassDto internalHanler(CommandHandlerContext<T> context);

	/**
	 * Re check contract.
	 *
	 * @param contractCode
	 *            the contract code
	 * @param contractPassword
	 *            the contract password
	 */
	protected boolean reCheckContract(String contractCode, String contractPassword) {
		InstallationType systemConfig = AppContexts.system().getInstallationType();
		// case Cloud
		if (systemConfig.value == InstallationType.CLOUD.value) {
			// reCheck contract
			// pre check contract
			if (!this.checkContractInput(contractCode, contractPassword)) {
				return false;
			}
			// contract auth
			if (!this.contractAccAuth(contractCode, contractPassword)) {
				return false;
			}
			return true;
		}
		return true;
	}

	/**
	 * Check contract input.
	 *
	 * @param contractCode
	 *            the contract code
	 * @param contractPassword
	 *            the contract password
	 */
	private boolean checkContractInput(String contractCode, String contractPassword) {
		if (StringUtil.isNullOrEmpty(contractCode, true)) {
			return false;
		}
		if (StringUtil.isNullOrEmpty(contractPassword, true)) {
			return false;
		}
		return true;
	}

	/**
	 * Gets the employee info case signon.
	 *
	 * @param companyId the company id
	 * @param employeeId the employee id
	 * @param isSignOn the is sign on
	 * @return the employee info case signon
	 */
	//EA修正履歴 No.3067、3068、3069
	protected SignonEmployeeInfoData getEmployeeInfoCaseSignon(WindowsAccount windowAcc, Boolean isSignOn) {
		// imported（GateWay）「会社情報」を取得する
		CompanyInformationImport companyInformationImport = this.companyInformationAdapter.findById(windowAcc.getCompanyId());

		// Imported（GateWay）「社員」を取得する
		Optional<EmployeeImportNew> opEm = this.employeeAdapter.getEmployeeBySid(windowAcc.getEmployeeId());

		// 社員が削除されたかを取得
		this.checkEmployeeDelStatus(windowAcc.getEmployeeId(), isSignOn);
		
		return new SignonEmployeeInfoData(companyInformationImport, opEm.get());
	}
	
	/**
	 * 社員が削除されたかを取得
	 *
	 * @param sid
	 *            the sid
	 * @return the check change pass dto
	 */
	protected CheckChangePassDto checkEmployeeDelStatus(String sid, boolean isSignon) {
		// get Employee status
		Optional<EmployeeDataMngInfoImport> optMngInfo = this.employeeAdapter.getSdataMngInfo(sid);
		
		Integer loginMethod = LoginMethod.NORMAL_LOGIN.value;
		if (isSignon){
			loginMethod = LoginMethod.SINGLE_SIGN_ON.value;
		}

		if (!optMngInfo.isPresent() || !SDelAtr.NOTDELETED.equals(optMngInfo.get().getDeletedStatus())) {
			ParamLoginRecord param = new ParamLoginRecord(" ", loginMethod, LoginStatus.Fail.value,
					TextResource.localize("Msg_301"), null);
			
			// アルゴリズム「ログイン記録」を実行する１
			this.service.callLoginRecord(param);
			
			throw new BusinessException("Msg_301");
		}

		return new CheckChangePassDto(false, null, false);
	}

	/**
	 * Contract acc auth.
	 *
	 * @param contractCode
	 *            the contract code
	 * @param contractPassword
	 *            the contract password
	 */
	private boolean contractAccAuth(String contractCode, String contractPassword) {
		Optional<Contract> contract = contractRepository.getContract(contractCode);
		if (contract.isPresent()) {
			// check contract pass
			if (!PasswordHash.verifyThat(contractPassword, contract.get().getContractCode().v())
					.isEqualTo(contract.get().getPassword().v())) {
				return false;
			}
			// check contract time
			if (contract.get().getContractPeriod().start().after(GeneralDate.today())
					|| contract.get().getContractPeriod().end().before(GeneralDate.today())) {
				return false;
			}
		} else {
			return false;
		}
		return true;
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
	public void setLoggedInfo(UserImportNew user, EmployeeImport em, String companyCode) {
		// set info to session
		manager.loggedInAsEmployee(user.getUserId(), em.getPersonalId(), user.getContractCode(), em.getCompanyId(),
				companyCode, em.getEmployeeId(), em.getEmployeeCode());
	}
    /**
    * CCG007-C.セッション生成
    * @param user ドメインモデル「ユーザ」
    * @param em 社員
    * @param companyCode 会社コード
    */
   public void initSessionC(UserImportNew user, EmployeeImport em, String companyCode) {
       //「ログインユーザコンテキスト」を新規作成、セッションに格納
       manager.loggedInAsEmployee(user.getUserId(), em.getPersonalId(), user.getContractCode(), em.getCompanyId(),
               companyCode, em.getEmployeeId(), em.getEmployeeCode());
       //権限（ロール）情報を取得、設定する 
       this.setRoleInfo(user.getUserId());
   }
   
   public void initSessionC(UserImportNew user, EmployeeImport em, String companyCode, LoginUserRoles roles) {
       //「ログインユーザコンテキスト」を新規作成、セッションに格納
       manager.loggedInAsEmployee(user.getUserId(), em.getPersonalId(), user.getContractCode(), em.getCompanyId(),
               companyCode, em.getEmployeeId(), em.getEmployeeCode());
       //権限（ロール）情報を取得、設定する 
//       this.setRoleId(user.getUserId());
       manager.roleSet(roles);
   }
   
   /**

	/**
	 * CCG007-B.セッション生成
	 * @param user ユーザ
	 */
	//EA修正履歴 No.3033
	public void initSession(UserImportNew user) {
		//切替可能な会社一覧を取得する(Có được danh sách công ty có thể chuyển đổi)
		List<String> lstCID = collectComList.getCompanyList(user.getUserId(), user.getContractCode());
		String assePersonId = user.getAssociatePersonId().isPresent() ? user.getAssociatePersonId().get() : null;
		if (lstCID.isEmpty()) {//取得できた場合　会社Listの件数　=　０
			//「ログインユーザコンテキスト」に情報設定（会社情報・社員情報はセットしません。） 
			//個人ID　＝　「ユーザ.紐付け先個人ID」
			manager.loggedInAsUser(user.getUserId(), assePersonId, user.getContractCode(), null, null);
		} else {//取得できた場合　会社Listの件数　＞　０
			Optional<EmployeeImport> opEm = Optional.empty();
			if(assePersonId != null){//ユーザ．紐付先個人ID　≠　Null
				//Imported（GateWay）「社員」を取得する (Imported (GateWay) Acquire 'Employee')
				opEm = employeeAdapter.getByPid(lstCID.get(FIST_COMPANY), assePersonId);
			}
			//Imported（GateWay）「会社情報」を取得する(Imported (GateWay) Acquire "company information")
			CompanyInformationImport comInfo = this.companyInformationAdapter
					.findById(lstCID.get(FIST_COMPANY));
			//「ログインユーザコンテキスト」に会社情報・社員情報をセットします。
			String contractCD = AppContexts.system().isOnPremise() ? "000000000000" : user.getContractCode();
			if(opEm.isPresent()){
				manager.loggedInAsEmployee(user.getUserId(), assePersonId, contractCD, 
						comInfo.getCompanyId(), comInfo.getCompanyCode(), opEm.get().getEmployeeId(), opEm.get().getEmployeeCode());
			}else{
				manager.loggedInAsUser(user.getUserId(), assePersonId, contractCD, comInfo.getCompanyId(), comInfo.getCompanyCode());
			}
		}
		//権限（ロール）情報を取得、設定する(Acquire and set authority (role) information)
		this.setRoleInfo(user.getUserId());
	}
	/**
	 * ログイン後チェック
	 * @param user
	 * @param oldPassword
	 * @return
	 */
	public CheckChangePassDto checkAfterLogin(UserImportNew user, String oldPassword, boolean signon) {
		if(signon){
			return new CheckChangePassDto(false, null, false);
		}
		
		if (user.getPassStatus() != PassStatus.Reset.value) {
			// Get PasswordPolicy
			Optional<PasswordPolicy> pwPoliOp = this.pwPolicyRepo.getPasswordPolicy(new ContractCode(user.getContractCode()));

			if (pwPoliOp.isPresent()) {
				// Event Check
				return this.checkEvent(pwPoliOp.get(), user, oldPassword);

			}
			return new CheckChangePassDto(false, null, false);
		}
			
		return new CheckChangePassDto(true, "Msg_283", false);
	}

	/**
	 * Check event.
	 *
	 * @param passwordPolicy
	 *            the password policy
	 * @param user
	 *            the user
	 * @param oldPassword
	 *            the old password
	 * @return true, if successful
	 */
	protected CheckChangePassDto checkEvent(PasswordPolicy passwordPolicy, UserImportNew user, String oldPassword) {
		// Check passwordPolicy isUse
		if (passwordPolicy.isUse()) {
			// Check Change Password at first login
			if (passwordPolicy.isInitialPasswordChange()) {
				// Check state
				if (user.getPassStatus() == PassStatus.InitPassword.value) {
					// Math PassPolicy
					return new CheckChangePassDto(true, "Msg_282", false);
				}
			}

			CheckBeforeChangePass mess = this.userAdapter.passwordPolicyCheckForSubmit(user.getUserId(), oldPassword, user.getContractCode());

			if (mess.isError()) {
				if (passwordPolicy.isLoginCheck()) {
					return new CheckChangePassDto(true, "Msg_284", false);
				}
				return new CheckChangePassDto(false, null, false);
			}
			
			//パスワードポリシーを満たす場合(Thỏa mãn PasswordPolicy)
			//アルゴリズム「パスワードの期限切れチェック」を実行する (Thực hiện thuật toán 「Check hết hạn password」)
			//エラーあり：パスワードの有効期限が切れた場合
			if(pWPolicyAlg.checkExpiredPass(user.getUserId(), passwordPolicy.getValidityPeriod().v().intValue())){
				return new CheckChangePassDto("Msg_1516");
			}
			//エラーなし
			//アルゴリズム「パスワード変更通知チェック」を実行する (Thực hiện thuật toán 「Check thông báo thay đổi password」)
			//通知しない(ko thông báo)
			NotifyResult notifyResult = pWPolicyAlg.checkNotifyChangePass(user.getUserId(), passwordPolicy.getValidityPeriod().v().intValue(), passwordPolicy.getNotificationPasswordChange().v().intValue());
			if(!notifyResult.isNotifyFlg()){
				return new CheckChangePassDto(false, null, false);
			}
			//通知する(có  thông báo- có check vào mục thông báo)
			//確認メッセージ（Msg_1517）を表示する {0}【残り何日】
			return new CheckChangePassDto("Msg_1517", notifyResult.getSpanDays());
		}
		return new CheckChangePassDto(false, null, false);
	}

	/**
	 * Sets the role id.
	 *
	 * @param userId
	 *            the new role id
	 * @return the check change pass dto
	 */
	// set roll id into login user context
    public CheckChangePassDto setRoleInfo(String userId) {
        Optional<RoleInfoImport> employmentRole = this.getRoleInfo(userId, RoleType.EMPLOYMENT);
        Optional<RoleInfoImport> salaryRole = this.getRoleInfo(userId, RoleType.SALARY);
        Optional<RoleInfoImport> humanResourceRole = this.getRoleInfo(userId, RoleType.HUMAN_RESOURCE);
        Optional<RoleInfoImport> personalInfoRole = this.getRoleInfo(userId, RoleType.PERSONAL_INFO);
        String officeHelperRoleId = this.getRoleId(userId, RoleType.OFFICE_HELPER);
        String groupCompanyManagerRoleId = this.getRoleId(userId, RoleType.GROUP_COMAPNY_MANAGER);
		String companyManagerRoleId = this.getRoleId(userId, RoleType.COMPANY_MANAGER);
		String systemManagerRoleId = this.getRoleId(userId, RoleType.SYSTEM_MANAGER);
		// 就業
        if (employmentRole.isPresent()) {
            manager.roleIdSetter().forAttendance(employmentRole.get().getRoleId());
            manager.roleIdSetter().isInChargeAttendance(employmentRole.get().isInCharge());            
		}
		// 給与
        if (salaryRole.isPresent()) {
            manager.roleIdSetter().forPayroll(salaryRole.get().getRoleId());
            manager.roleIdSetter().isInChargePayroll(salaryRole.get().isInCharge());    
		}
		// 人事
        if (humanResourceRole.isPresent()) {
            manager.roleIdSetter().forPersonnel(humanResourceRole.get().getRoleId());
            manager.roleIdSetter().isInChargePersonnel(humanResourceRole.get().isInCharge());    
        }
        // 個人情報
        if (personalInfoRole.isPresent()) {
            manager.roleIdSetter().forPersonalInfo(personalInfoRole.get().getRoleId());
            manager.roleIdSetter().isInChargePersonalInfo(personalInfoRole.get().isInCharge());    
		}
		// オフィスヘルパー
		if (officeHelperRoleId != null) {
			manager.roleIdSetter().forOfficeHelper(officeHelperRoleId);
		}
		// 会計
		// マイナンバー
		// グループ会社管理
		if (groupCompanyManagerRoleId != null) {
			manager.roleIdSetter().forGroupCompaniesAdmin(groupCompanyManagerRoleId);
		}
		// 会社管理者
		if (companyManagerRoleId != null) {
			manager.roleIdSetter().forCompanyAdmin(companyManagerRoleId);
		}
		// システム管理者
		if (systemManagerRoleId != null) {
			manager.roleIdSetter().forSystemAdmin(systemManagerRoleId);
		}

		return new CheckChangePassDto(false, null, false);
	}
	
	public LoginUserRoles checkRole(String userId, String comId) {
        DefaultLoginUserRoles roles = new DefaultLoginUserRoles();
        Optional<RoleInfoImport> employmentRole = this.getRoleInfo(userId, RoleType.EMPLOYMENT);
        Optional<RoleInfoImport> salaryRole = this.getRoleInfo(userId, RoleType.SALARY);
        Optional<RoleInfoImport> humanResourceRole = this.getRoleInfo(userId, RoleType.HUMAN_RESOURCE);
        Optional<RoleInfoImport> personalInfoRole = this.getRoleInfo(userId, RoleType.PERSONAL_INFO);
        String officeHelperRoleId = this.getRoleId(userId, RoleType.OFFICE_HELPER, comId);
        String groupCompanyManagerRoleId = this.getRoleId(userId, RoleType.GROUP_COMAPNY_MANAGER, comId);
        String companyManagerRoleId = this.getRoleId(userId, RoleType.COMPANY_MANAGER, comId);
        String systemManagerRoleId = this.getRoleId(userId, RoleType.SYSTEM_MANAGER, comId);
		// 就業
		if (employmentRole.isPresent()) {
			roles.setRoleIdForAttendance(employmentRole.get().getRoleId());
			roles.setIsInChargeAttendance(employmentRole.get().isInCharge());
		}
		// 給与
		if (salaryRole.isPresent()) {
			roles.setRoleIdForPayroll(salaryRole.get().getRoleId());
			roles.setIsInChargePayroll(salaryRole.get().isInCharge());
		}
		// 人事
		if (humanResourceRole.isPresent()) {
			roles.setRoleIdForPersonnel(humanResourceRole.get().getRoleId());
			roles.setIsInChargePersonnel(humanResourceRole.get().isInCharge());
		}
		// 個人情報
		if (personalInfoRole.isPresent()) {
			roles.setRoleIdforPersonalInfo(personalInfoRole.get().getRoleId());
			roles.setIsInChargePersonalInfo(personalInfoRole.get().isInCharge());
		}
		// オフィスヘルパー
		if (officeHelperRoleId != null) {
			roles.setRoleIdforOfficeHelper(officeHelperRoleId);
		}
		// 会計
		// マイナンバー
		// グループ会社管理
		if (groupCompanyManagerRoleId != null) {
			roles.setRoleIdforGroupCompaniesAdmin(groupCompanyManagerRoleId);
		}
		// 会社管理者
		if (companyManagerRoleId != null) {
			roles.setRoleIdforCompanyAdmin(companyManagerRoleId);
		}
		// システム管理者
		if (systemManagerRoleId != null) {
			roles.setRoleIdforSystemAdmin(systemManagerRoleId);
		}

		return roles;
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
		String roleId = roleFromUserIdAdapter.getRoleFromUser(userId, roleType.value, GeneralDate.today());
		if (roleId == null || roleId.isEmpty()) {
			return null;
		}
		return roleId;
	}

	protected String getRoleId(String userId, RoleType roleType, String comId) {
		String roleId = roleFromUserIdAdapter.getRoleFromUser(userId, roleType.value, GeneralDate.today(), comId);
		if (roleId == null || roleId.isEmpty()) {
			return null;
		}
		return roleId;
	}
	
    /**
     * Gets the role info.
     *
     * @param userId
     *            the user id
     * @param roleType
     *            the role type
     * @return the role info
     */
    protected Optional<RoleInfoImport> getRoleInfo(String userId, RoleType roleType) {
        return roleFromUserIdAdapter.getRoleInfoFromUser(userId, roleType.value, GeneralDate.today(), AppContexts.user().companyId());
    }

    /**
	 * Compare hash password.
	 *
	 * @param user
	 *            the user
	 * @param password
	 *            the password
	 * @return the string
	 */
	protected String compareHashPassword(UserImportNew user, String password) {
		if (!PasswordHash.verifyThat(password, user.getUserId()).isEqualTo(user.getPassword())) {
			// アルゴリズム「ロックアウト」を実行する ※２次対応
			this.lockOutExecuted(user);
			return "Msg_302";
		}
		return null;
	}

	/**
	 * Lock out executed.
	 *
	 * @param user
	 *            the user
	 */
	@Transactional
	private void lockOutExecuted(UserImportNew user) {
		// ドメインモデル「アカウントロックポリシー」を取得する
		AccountLockPolicy accountLockPolicy = this.accountLockPolicyRepository
				.getAccountLockPolicy(new ContractCode(user.getContractCode())).get();
		if (accountLockPolicy.isUse()) {
			// ロックアウト条件に満たしているかをチェックする (Check whether the lockout condition is
			// satisfied)
			if (this.checkLoginLog(user.getUserId(), accountLockPolicy)) {
				// Add to domain model LockOutData
				LockOutDataDto dto = LockOutDataDto.builder().userId(user.getUserId())
						.contractCode(accountLockPolicy.getContractCode().v()).logoutDateTime(GeneralDateTime.now())
						.lockType(LockType.AUTO_LOCK.value).build();
				LockOutData lockOutData = new LockOutData(dto);
				this.lockOutDataRepository.add(lockOutData);
			}
		}
		// Add to the domain model LoginLog
		LoginLogDto dto = LoginLogDto.builder().userId(user.getUserId())
				.contractCode(accountLockPolicy.getContractCode().v()).processDateTime(GeneralDateTime.now())
				.successOrFail(SuccessFailureClassification.Failure.value).operation(OperationSection.Login.value)
				.programId(AppContexts.programId()).build();
		LoginLog loginLog = new LoginLog(dto);
		this.loginLogRepository.add(loginLog);
	}

	/**
	 * Check login log.
	 *
	 * @param userId
	 *            the user id
	 * @param accountLockPolicy
	 *            the account lock policy
	 * @return true, if successful
	 */
	private boolean checkLoginLog(String userId, AccountLockPolicy accountLockPolicy) {
		GeneralDateTime startTime = GeneralDateTime.now();
		// Check the domain model [Account lock policy. Error interval]
		if (accountLockPolicy.getLockInterval().lessThanOrEqualTo(new LockInterval(0))) {
			startTime = GeneralDateTime.fromString("1901/01/01 00:00:00", "yyyy/MM/dd HH:mm:ss");
		} else {
			startTime = startTime.addMinutes(-1 * accountLockPolicy.getLockInterval().minute());
		}
		// Search the domain model [LoginLog] and acquire [number of failed
		// logs] → [failed times]
		Integer countFailure = this.loginLogRepository.getLoginLogByConditions(userId, startTime);

		// Return LockOut
		if (countFailure < accountLockPolicy.getErrorCount().v().intValue()) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * アカウント照合
	 *
	 * @param context
	 *            the context
	 * @return the windows account
	 */
	protected WindowsAccount compareAccount(HttpServletRequest context) {
		// Windowsログイン時のアカウントを取得する
		// get UserName and HostName
		String username = AppContexts.windowsAccount().getUserName();
		String domain = AppContexts.windowsAccount().getDomain();

		// cut hostname
		String hostname = domain.substring(0, domain.lastIndexOf(";"));

		// ドメインモデル「Windowsアカウント情報」を取得する
		// ログイン時アカウントとドメインモデル「Windowsアカウント情報」を比較する - get 「Windowsアカウント情報」 from
		// 「Windowsアカウント」
		Optional<WindowsAccount> opWindowAccount = this.windowAccountRepository.findbyUserNameAndHostName(username,
				hostname);

		if (!opWindowAccount.isPresent()) {
			String remarkText = hostname + "\\" + username + " " + TextResource.localize("Msg_876");
			ParamLoginRecord param = new ParamLoginRecord(" ", LoginMethod.SINGLE_SIGN_ON.value, LoginStatus.Fail.value,
					remarkText, null);

			// アルゴリズム「ログイン記録」を実行する１
			this.service.callLoginRecord(param);

			// エラーメッセージ（#Msg_876）を表示する。
			throw new BusinessException("Msg_876");
		}

		// set List WindowsAccountInfor
		List<WindowsAccountInfo> windows = opWindowAccount.get().getAccountInfos()
				.stream().filter(item -> item.getHostName().v().equals(hostname)
						&& item.getUserName().v().equals(username) && item.getUseAtr().equals(UseAtr.Use))
				.collect(Collectors.toList());

		if (windows.isEmpty()? true : windows.get(0).getUseAtr() == UseAtr.NotUse ? true : false) {
			String remarkText = hostname + " " + username + " " + TextResource.localize("Msg_876");
			ParamLoginRecord param = new ParamLoginRecord(" ", LoginMethod.SINGLE_SIGN_ON.value, LoginStatus.Fail.value,
					remarkText, null);

			// アルゴリズム「ログイン記録」を実行する１
			this.service.callLoginRecord(param);
			throw new BusinessException("Msg_876");
		}
		return opWindowAccount.get();
	}

	/**
	 * ドメインモデル「ユーザ」を取得する
	 * ユーザーの有効期限チェック
	 * @param windowAccount
	 *            the window account
	 * @return the user and check limit time
	 */
	public UserImportNew getUserAndCheckLimitTime(WindowsAccount windowAccount) {
		String username = AppContexts.windowsAccount().getUserName();
		String domain = AppContexts.windowsAccount().getDomain();
		// cut hostname
		String hostname = domain.substring(0, domain.lastIndexOf(";"));
		
		//EA修正履歴 No.3067、3068、3069
		// get user
		Optional<UserImportNew> optUserImport = this.userAdapter.findUserByEmployeeId(windowAccount.getEmployeeId());

		//ユーザーの有効期限チェック(User expiration check)
		if (optUserImport.isPresent()) {
			if (optUserImport.get().getExpirationDate().before(GeneralDate.today())) {
				String remarkText = hostname + " " + username + " " + TextResource.localize("Msg_316");
				ParamLoginRecord param = new ParamLoginRecord(" ", LoginMethod.SINGLE_SIGN_ON.value,
						LoginStatus.Fail.value, remarkText, null);
				
				// アルゴリズム「ログイン記録」を実行する１
				this.service.callLoginRecord(param);

				throw new BusinessException("Msg_316");
			}
		}
		return optUserImport.get();
	}

	/**
	 * エラーチェック（形式１）
	 *
	 * @param userId
	 *            the user id
	 * @param roleType
	 *            the role type
	 * @param contractCode
	 *            the contract code
	 */
	public void errorCheck(String loginId, String userId, Integer roleType, String contractCode, boolean isSignOn) {

		GeneralDate date = GeneralDate.today();
		// 切替可能な会社一覧を取得する Get list company
		List<String> companyIds = this.getListCompany(userId, date, roleType, contractCode);

		if (companyIds.isEmpty()) {
			Integer loginMethod = LoginMethod.NORMAL_LOGIN.value;
			if(isSignOn){
				loginMethod = LoginMethod.SINGLE_SIGN_ON.value;
			}
			String remarkText = loginId + " " + TextResource.localize("Msg_1419");
			ParamLoginRecord param = new ParamLoginRecord(" ", loginMethod, LoginStatus.Fail.value, remarkText, null);
			// アルゴリズム「ログイン記録」を実行する１
			this.service.callLoginRecord(param);

			throw new BusinessException("Msg_1527");
		}
		//hoatt 2019.05.07  #107445
		//EA修正履歴No.3365
//		String message = this.checkAccoutLock(loginId,contractCode, userId, " ", isSignOn).v();
//		if (!message.isEmpty()) {
//			// return messageError
//			throw new BusinessException(message);
//		}
	}

	/**
	 * エラーチェック（形式２・３）
	 *
	 * @param companyId
	 *            the company id
	 */
	public void errorCheck2(String companyId, String contractCode, String userId, boolean isSignon, String employeeId) {

		// ドメインモデル「会社」の使用区分をチェックする (Check usage classification of domain model
		// "company")
		CompanyInforImport company = this.companyInformationAdapter.findComById(companyId);

		if (company.getIsAbolition() == Abolition.ABOLISH.value) {
			Integer loginMethod = LoginMethod.NORMAL_LOGIN.value;
			if (isSignon){
				loginMethod = LoginMethod.SINGLE_SIGN_ON.value;
			}
			ParamLoginRecord param = new ParamLoginRecord(companyId, loginMethod, LoginStatus.Fail.value,
					TextResource.localize("Msg_281"), employeeId);
			
			// アルゴリズム「ログイン記録」を実行する１
			this.service.callLoginRecord(param);

			throw new BusinessException("Msg_281");
		}
		if (employeeId != null) {
		//アルゴリズム「在職状態を取得」を実行する
		//Request No.280
		StatusOfEmploymentImport employmentStatus = this.statusEmploymentAdapter.getStatusOfEmployment(employeeId,
				GeneralDate.today());
			int empStatus = employmentStatus.getStatusOfEmployment();
		// check status = before_join
			if (employmentStatus == null || empStatus == StatusOfEmployment.BEFORE_JOINING.value
					|| empStatus == StatusOfEmployment.RETIREMENT.value) {
				
				String errorCode = "";
				if (employmentStatus == null || empStatus == StatusOfEmployment.BEFORE_JOINING.value) {
					errorCode = "Msg_286";
				}
				if (empStatus == StatusOfEmployment.RETIREMENT.value) {
					errorCode = "Msg_287";
				}
			Integer loginMethod = LoginMethod.NORMAL_LOGIN.value;
			if (isSignon) {
				loginMethod = LoginMethod.SINGLE_SIGN_ON.value;
			}
			ParamLoginRecord param = new ParamLoginRecord(companyId, loginMethod, LoginStatus.Fail.value,
					TextResource.localize(errorCode), employeeId);

			// アルゴリズム「ログイン記録」を実行する１
			this.service.callLoginRecord(param);
				
			throw new BusinessException(errorCode);
			}
		
		
		}
		//update EA修正履歴 No.3054
//		Optional<UserImportNew> opUserImport = userAdapter.findByUserId(userId);
//		String loginId = "";
//		if (opUserImport.isPresent()) {
//			loginId = opUserImport.get().getLoginId();
//		}
		//hoatt 2019.05.07 #107445
		//EA修正履歴No.3366
//		String message = this.checkAccoutLock(loginId, contractCode, userId, companyId, isSignon).v();
//		if (!message.isEmpty()) {
//			// return messageError
//			throw new BusinessException(new RawErrorMessage(message));
//		}
		
		List<String> lstEmployeeId = new ArrayList<>();
//		DatePeriod periodEmployee = new DatePeriod(GeneralDate.today(), GeneralDate.today());
		
		if (employeeId != null) { // employeeId = null when single sign on
			lstEmployeeId.add(employeeId);
			// 社員所属職位履歴を取得
			List<EmployeeJobHistImport> job = this.syJobTitleAdapter.findBySid(employeeId, GeneralDate.today());
			if (!job.isEmpty()) {

				// 社員所属職場履歴を取得
				Optional<SWkpHistImport> wkp = this.syWorkplaceAdapter.findBySid(companyId, employeeId,
						GeneralDate.today());
				if (wkp.isPresent()) {
					// 社員所属雇用履歴を取得
					Optional<SEmpHistImport> emp = this.syEmploymentAdapter.findSEmpHistBySid(companyId, employeeId,
							GeneralDate.today());
					if (!emp.isPresent()) {
						this.showError(isSignon, companyId, employeeId);
					}
				} else {
					this.showError(isSignon, companyId, employeeId);
				}
			} else {
				this.showError(isSignon, companyId, employeeId);
			}
		}
	}
	
	private void showError(boolean isSignon, String companyId, String employeeId) {
		Integer loginMethod = LoginMethod.NORMAL_LOGIN.value;
		if (isSignon) {
			loginMethod = LoginMethod.SINGLE_SIGN_ON.value;
		}
		ParamLoginRecord param = new ParamLoginRecord(companyId, loginMethod, LoginStatus.Fail.value,
				TextResource.localize("Msg_1420"), employeeId);

		// アルゴリズム「ログイン記録」を実行する２ (Execute algorithm "login recording")
		this.service.callLoginRecord(param);

		throw new BusinessException("Msg_1420");
	}
	/**
	 * Gets the list company.
	 *
	 * @param userId
	 *            the user id
	 * @param date
	 *            the date
	 * @param roleType
	 *            the role type
	 * @return the list company
	 */
	// 切替可能な会社一覧を取得する
	private List<String> getListCompany(String userId, GeneralDate date, Integer roleType, String contractCode) {

		// ドメインモデル「ロール個人別付与」を取得する (get List RoleIndividualGrant)
		List<RoleIndividualGrantImport> roles = this.roleIndividualGrantAdapter.getByUserIDDateRoleType(userId, date,
				roleType);

		List<RoleImport> roleImp = new ArrayList<>();

		if (!roles.isEmpty()) {
			// ドメインモデル「ロール」を取得する (Acquire domain model "role"
			roles.stream().map(roleItem -> {
				return roleImp.addAll(this.roleAdapter.getAllById(roleItem.getRoleId()));
			}).collect(Collectors.toList());
		}

		GeneralDate systemDate = GeneralDate.today();

		// ドメインモデル「ユーザ」を取得する get domain "User"
		Optional<UserImportNew> user = this.userAdapter.getByUserIDandDate(userId, systemDate);

		List<EmployeeInfoDtoImport> employees = new ArrayList<>();

		if (!user.get().getAssociatePersonId().get().isEmpty()) {
			employees.addAll(this.employeeInfoAdapter.getEmpInfoByPid(user.get().getAssociatePersonId().get()));
			
			List<String> lstEmployeeId = employees.stream().map(dto -> dto.getEmployeeId()).collect(Collectors.toList());
			DatePeriod periodEmployee = new DatePeriod(GeneralDate.today(), GeneralDate.today());
			
			employees = employees.stream()
				.filter(empItem -> {
					boolean isEmployeeHis; 
					// Imported「社員の履歴情報」 を取得する(get Imported「社員の履歴情報」)
					EmployeeGeneralInfoImport importPub = employeeGeneralInfoAdapter.getEmployeeGeneralInfo(lstEmployeeId, periodEmployee, IS_EMPLOYMENT, IS_CLASSIFICATION, IS_JOBTITLE, IS_WORKPLACE, IS_DEPARTMENT);
					// 職場履歴一覧がEmpty or 雇用履歴一覧がEmpty or 職位履歴一覧がEmpty
					isEmployeeHis = !importPub.isLstWorkplace() && !importPub.isLstEmployment() && !importPub.isLstJobTitle();
					return !this.employeeAdapter.getStatusOfEmployee(empItem.getEmployeeId()).isDeleted() && isEmployeeHis;
				})
				.collect(Collectors.toList());
		}

		// imported（権限管理）「会社」を取得する (imported (authority management) Acquire
		// "company") Request No.51
		List<CompanyBsImport> companys = this.companyBsAdapter.getAllCompany();

		List<String> companyIdAll = companys.stream().map(item -> {
			return item.getCompanyId();
		}).collect(Collectors.toList());

		List<String> lstCompanyId = new ArrayList<>();

		// merge duplicate companyId from lstRole and lstEm
		if (!roleImp.isEmpty()) {
			List<String> lstComp = new ArrayList<>();
			roleImp.forEach(role -> {
				if (role.getCompanyId() != null) {
					lstComp.add(role.getCompanyId());
				}
			});

			lstCompanyId.addAll(lstComp);
		}

		if (!employees.isEmpty()) {
			List<String> lstComp = new ArrayList<>();
			employees.forEach(emp -> {
				if (emp.getCompanyId() != null) {
					lstComp.add(emp.getCompanyId());
				}
			});

			lstCompanyId.addAll(lstComp);
		}

		lstCompanyId = lstCompanyId.stream().distinct().collect(Collectors.toList());

		// 取得した会社（List）から、会社IDのリストを抽出する (Extract the list of company IDs from
		// the acquired company (List))
		List<String> lstCompanyFinal = lstCompanyId.stream().filter(com -> companyIdAll.contains(com))
				.collect(Collectors.toList());
		//EA修正履歴 No.3031
		List<String> lstResult = collectComList.checkStopUse(contractCode, lstCompanyFinal, userId);
		return lstResult;
	}

	/**
	 * アカウントロックチェック
	 * @param 契約コード contractCode
	 * @param ユーザID userId
	 * @param ログインID loginId
	 * @return the lock out message
	 */
	protected void checkAccoutLock(String loginId, String contractCode, String userId, String companyId, boolean isSignOn) {
		// ドメインモデル「アカウントロックポリシー」を取得する (Acquire the domain model "account lock
		// policy")
		if (this.accountLockPolicyRepository.getAccountLockPolicy(new ContractCode(contractCode)).isPresent()) {
			AccountLockPolicy accountLockPolicy = this.accountLockPolicyRepository
					.getAccountLockPolicy(new ContractCode(contractCode)).get();
			if (accountLockPolicy.isUse()) {
				// ドメインモデル「ロックアウトデータ」を取得する (Acquire domain model "lockout data")
				Optional<LockOutData> lockoutData = this.lockOutDataRepository.findByUserId(userId);

				if (lockoutData.isPresent()) {
					Integer loginMethod = LoginMethod.NORMAL_LOGIN.value;
					if (isSignOn){
						loginMethod = LoginMethod.SINGLE_SIGN_ON.value;
					}
					String remarkText = loginId + " " + accountLockPolicy.getLockOutMessage().v();
					ParamLoginRecord param = new ParamLoginRecord(companyId, loginMethod, LoginStatus.Fail_Lock.value,
							remarkText, null);
					
					// アルゴリズム「ログイン記録」を実行する１
					this.service.callLoginRecord(param);

					// エラーメッセージ（ドメインモデル「アカウントロックポリシー.ロックアウトメッセージ」）を表示する
					throw new BusinessException(new RawErrorMessage(accountLockPolicy.getLockOutMessage()));
				}
			}
		}
	}
	/**
	 * ログインログを削除
	 * @param ユーザID userId
	 */
	protected void deleteLoginLog(String userId){
//		条件
//		　・ユーザID＝INPUT.ユーザID
//		　・成功失敗区分＝失敗
//		　・操作区分＝ログイン
		//ドメインモデル「ログインログ」を削除する
		loginLogRepo.deleteLoginLog(userId, SuccessFailureClassification.Failure.value, OperationSection.Login.value);
	};
}
