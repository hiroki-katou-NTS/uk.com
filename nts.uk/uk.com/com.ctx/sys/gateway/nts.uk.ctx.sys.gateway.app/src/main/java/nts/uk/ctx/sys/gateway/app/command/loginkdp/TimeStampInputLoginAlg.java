/**
 * 
 */
package nts.uk.ctx.sys.gateway.app.command.loginkdp;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.security.hash.password.PasswordHash;
import nts.uk.ctx.sys.gateway.app.command.login.LoginRecordRegistService;
import nts.uk.ctx.sys.gateway.app.command.login.dto.ParamLoginRecord;
import nts.uk.ctx.sys.gateway.app.command.systemsuspend.SystemSuspendOutput;
import nts.uk.ctx.sys.gateway.app.service.login.LoginService;
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserImportNew;
import nts.uk.ctx.sys.gateway.dom.login.ContractCode;
import nts.uk.ctx.sys.gateway.dom.login.LoginStatus;
import nts.uk.ctx.sys.gateway.dom.login.adapter.SysEmployeeAdapter;
import nts.uk.ctx.sys.gateway.dom.login.dto.EmployeeDataMngInfoImport;
import nts.uk.ctx.sys.gateway.dom.login.dto.EmployeeImport;
import nts.uk.ctx.sys.gateway.dom.login.dto.SDelAtr;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.AccountLockPolicy;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.AccountLockPolicyRepository;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.LockInterval;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LockOutData;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LockOutDataDto;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LockOutDataRepository;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LockType;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LoginMethod;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.loginlog.LoginLog;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.loginlog.LoginLogDto;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.loginlog.LoginLogRepository;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.loginlog.OperationSection;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.loginlog.SuccessFailureClassification;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

/**
 * @author laitv 打刻入力ログイン path:UKDesign.UniversalK.共通.CCG_メニュートップページ.CCG007_ログイン
 *         (Login).打刻入力ログイン.アルゴリズム.打刻入力ログイン
 */
@Stateless
public class TimeStampInputLoginAlg extends LoginBaseWithTimeStampCommandHandler<TimeStampInputLoginCommand>{
	
	@Inject
	private LoginService employeeCodeSetting;
	
	@Inject
	private SysEmployeeAdapter employeeAdapter;
	
	/** The service. */
	@Inject
	private LoginRecordRegistService service;
	
	@Inject
	private AccountLockPolicyRepository accountLockPolicyRepository;
	
	@Inject
	private LockOutDataRepository lockOutDataRepository;
	
	@Inject
	private UserAdapter userAdapter;
	
	@Inject
	private LoginLogRepository loginLogRepository;
	
	
	@Override
	public TimeStampInputLoginDto internalHanler(CommandHandlerContext<TimeStampInputLoginCommand> context) {
		
		TimeStampInputLoginCommand command = context.getCommand();
		String cid = command.getCompanyId();
		String contractCode = command.getContractCode();
		
		String remarkMessage = "";
		EmployeeImport em = new EmployeeImport();
		UserImportNew user = new UserImportNew();

		if (command.getIsAdminMode()) {
			// 「備考メッセージ」は#Msg_1661をセット
			remarkMessage = TextResource.localize("Msg_1661");
		} else {
			// 「備考メッセージ」は#Msg_1660をセット
			remarkMessage = TextResource.localize("Msg_1660");
		}

		// Edit employee code
		String employeeCode = this.employeeCodeSetting.employeeCodeEdit(command.getEmployeeCode(), cid);
		
		// Imported（GateWay）「社員」を取得する
		em = this.getEmployee(cid, employeeCode, remarkMessage);
		
		// アルゴリズム「社員が削除されたかを取得」を実行する
		this.checkEmployeeDelStatus(em.getEmployeeId(), command.getEmployeeCode(), cid, remarkMessage);
		
		// imported（ゲートウェイ）「ユーザ」を取得する
		user = this.getUser(em.getPersonalId(), cid, employeeCode, em.getEmployeeId(), remarkMessage);
		
		// アルゴリズム「アカウントロックチェック」を実行する (Execute the algorithm "account lock check")
		this.checkAccoutLock(user.getLoginId(), contractCode, user.getUserId(), cid );
		
		// パラメータ：パスワード無効
		if (!command.getPasswordInvalid()) {
			String msgErrorId = this.compareHashPassword(user, command.getPassword());
			if (msgErrorId != null){
				String remarkText = employeeCode + " " + remarkMessage + " " + TextResource.localize("Msg_302");
				ParamLoginRecord param = new ParamLoginRecord(cid, LoginMethod.NORMAL_LOGIN.value,
						LoginStatus.Fail.value, remarkText, null);
				
				this.service.callLoginRecord(param);
				throw new BusinessException("Msg_302");
			} 
		}
		
		// ユーザーの有効期限チェック (Kiểm tra thời hạn hiệu lực của User)
		this.checkLimitTime(user, cid, employeeCode, em.getEmployeeId(), remarkMessage);
		
		// 実行時環境作成
		if(command.getRuntimeEnvironmentCreat()){
			//ログインセッション作成 (Create login session)
	        this.initSessionC(user, em, command.getCompanyCode());
		}
		
		// アルゴリズム「システム利用停止の確認」を実行する
        SystemSuspendOutput systemSuspendOutput = this.service.checkSystemStop(command);
		if(systemSuspendOutput.isError()){
			throw new BusinessException(new RawErrorMessage(systemSuspendOutput.getMsgContent()));
		}
		
		// アルゴリズム「ログイン記録」を実行する１
		ParamLoginRecord param = new ParamLoginRecord(cid, LoginMethod.NORMAL_LOGIN.value, LoginStatus.Success.value, remarkMessage, em.getEmployeeId());
		this.service.callLoginRecord(param);
		
		//アルゴリズム「ログインログを削除」を実行する
		this.deleteLoginLog(user.getUserId());
		
		//アルゴリズム「ログイン後チェック」を実行する
		this.checkAfterLogin(user, command.getPw().get(), false);
		
		return new TimeStampInputLoginDto(true, em, null);
	}
	
	/**
	 * Gets the employee.
	 *
	 * @param companyId the company id
	 * @param employeeCode the employee code
	 * @return the employee
	 */
	private EmployeeImport getEmployee(String companyId, String employeeCode, String remarkMessage) {
		Optional<EmployeeImport> em = employeeAdapter.getCurrentInfoByScd(companyId, employeeCode);
		if (em.isPresent()) {
			return em.get();
		} else {
			String remarkText = employeeCode + " " + remarkMessage + " " + TextResource.localize("Msg_301");
			ParamLoginRecord param = new ParamLoginRecord(companyId, LoginMethod.NORMAL_LOGIN.value,
					LoginStatus.Fail.value, remarkText, null);
			
			// アルゴリズム「ログイン記録」を実行する１
			this.service.callLoginRecord(param);
			throw new BusinessException("Msg_301");
		}
	}
	
	// アルゴリズム「社員が削除されたかを取得」を実行する
	protected Boolean checkEmployeeDelStatus(String sid, String employeeCode, String companyId, String remarkMessage) {
		// get Employee status
		Optional<EmployeeDataMngInfoImport> optMngInfo = this.employeeAdapter.getSdataMngInfo(sid);
		
		if (!optMngInfo.isPresent() || !SDelAtr.NOTDELETED.equals(optMngInfo.get().getDeletedStatus())) {
			String remarkText = employeeCode + " " + remarkMessage + " " + TextResource.localize("Msg_301");
			
			ParamLoginRecord param = new ParamLoginRecord(companyId, LoginMethod.NORMAL_LOGIN.value, LoginStatus.Fail.value,
					remarkText, sid);
			
			// アルゴリズム「ログイン記録」を実行する１
			this.service.callLoginRecord(param);
			
			throw new BusinessException("Msg_301");
		}

		return false;
	}
	
	
	/**
	 * Gets the user.
	 *
	 * @param personalId the personal id
	 * @return the user
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public UserImportNew getUser(String personalId,String cid,String employeeCode,String employeeId,String remarkMessage) {
		Optional<UserImportNew> user = userAdapter.findUserByAssociateId(personalId);
		if (user.isPresent()) {
			return user.get();
		} else {
			String remarkText = employeeCode + " " + remarkMessage + " " + TextResource.localize("Msg_301");
			ParamLoginRecord param = new ParamLoginRecord(cid, LoginMethod.NORMAL_LOGIN.value,
					LoginStatus.Fail.value, remarkText, employeeId);
			
			// アルゴリズム「ログイン記録」を実行する１
			this.service.callLoginRecord(param);
			throw new BusinessException("Msg_301");
		}
	}
	
	protected void checkAccoutLock(String loginId, String contractCode, String userId, String companyId) {
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
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void checkLimitTime(UserImportNew user, String companyId, String employeeCode, String sid,  String remarkMessage) {
		if (user.getExpirationDate().before(GeneralDate.today())) {
			String remarkText = employeeCode + " " + remarkMessage + " " + TextResource.localize("Msg_316");
			ParamLoginRecord param = new ParamLoginRecord(companyId, LoginMethod.NORMAL_LOGIN.value,
					LoginStatus.Fail.value, remarkText, sid);
			
			// アルゴリズム「ログイン記録」を実行する１
			this.service.callLoginRecord(param);
			throw new BusinessException("Msg_316");
		}
	}
	
}
