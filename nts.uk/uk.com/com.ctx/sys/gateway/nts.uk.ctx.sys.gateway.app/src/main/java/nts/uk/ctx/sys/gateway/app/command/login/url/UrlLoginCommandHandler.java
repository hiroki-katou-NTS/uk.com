package nts.uk.ctx.sys.gateway.app.command.login.url;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.val;
import nts.arc.task.tran.TransactionService;
import nts.uk.ctx.sys.gateway.app.command.login.LoginCommandHandlerBase;
import nts.uk.ctx.sys.gateway.app.command.login.LoginRequire;
import nts.uk.ctx.sys.gateway.app.command.login.password.CheckChangePassDto;
import nts.uk.ctx.sys.gateway.app.command.loginold.LoginRecordRegistService;
import nts.uk.ctx.sys.gateway.app.command.tenantlogin.ConnectDataSourceOfTenant;
import nts.uk.ctx.sys.gateway.dom.login.LoginClient;
import nts.uk.ctx.sys.gateway.dom.login.password.identification.EmployeeIdentify;
import nts.uk.ctx.sys.gateway.dom.login.password.identification.IdentificationResult;
import nts.uk.ctx.sys.gateway.dom.login.password.identification.PasswordAuthIdentificationFailureLog;
import nts.uk.ctx.sys.gateway.dom.login.password.identification.PasswordAuthIdentificationFailureLogRepository;
import nts.uk.ctx.sys.gateway.dom.login.url.UrlLoginRequestValidator;
import nts.uk.ctx.sys.gateway.dom.mail.UrlExecInfoRepository;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthenticationResult;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.gateway.app.command.loginold.dto.LoginRecordInput;
import nts.uk.ctx.sys.shared.dom.employee.EmployeeDataManageInfoAdapter;
import nts.uk.ctx.sys.shared.dom.employee.EmployeeDataMngInfoImport;
import nts.uk.ctx.sys.shared.dom.user.User;
import nts.uk.ctx.sys.shared.dom.user.UserRepository;
import nts.uk.shr.com.context.DeviceInfo;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.url.UrlExecInfo;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class UrlLoginCommandHandler extends LoginCommandHandlerBase<
												UrlLoginCommand,
												UrlResult,
												UrlResult,
												UrlLoginCommandHandler.Require> {

	@Inject
	private LoginRequire loginRequire;

	@Inject
	UrlExecInfoRepository urlExecInfoRepository;

	@Inject
	LoginRecordRegistService loginRecordRegistService;

	@Inject
	private TransactionService transaction;

	@Inject
	private EmployeeDataManageInfoAdapter employeeDataManageInfoAdapter;

	@Inject
	private UserRepository userRepo;

	@Inject
	private PasswordAuthIdentificationFailureLogRepository passwordAuthIdentificateFailureLogRepo;

	@Override
	protected UrlLoginCommandHandler.Require getRequire(UrlLoginCommand urlLoginCommand) {
		return new RequireImpl();
	}

	@Override
	protected TenantAuthenticationResult tenanteAuthenticate(
			UrlLoginCommandHandler.Require require,
			UrlLoginCommand command,
			LoginClient loginClient){
		return ConnectDataSourceOfTenant.connectForUrlLogin(
				require, loginClient, command.getTenantCode());

	}

	@Override
	protected UrlResult tenantAuthencationFailed() {
		return UrlResult.failed(CheckChangePassDto.failedToAuthTenant());
	}

	@Override
	protected UrlResult authenticate(UrlLoginCommandHandler.Require require, UrlLoginCommand command) {
		String urlID = command.getUrlId();

		UrlLoginRequestValidator.validate(require, urlID);

		// 埋込URL実行情報を取得する
		val urlExecInfo = require.getUrlExecInfoByUrlID(urlID);
		if(!urlExecInfo.isPresent()){
			return UrlResult.failed(
					new CheckChangePassDto(false, null, true)
			);
		}

		Optional<DeviceInfo> device = DeviceInfo.detectDevice(command.getRequest());
		UrlResult result = UrlResult.create(urlExecInfo.get(), device);

		// 埋込URLの有効期間チェック
		if (urlExecInfo.get().getExpiredDate().before(GeneralDateTime.now())) {
			// 期限切れ
			result.setFailed("#Msg_1095");
			return result;
		}

		// ログイン社員の識別
		val companyId = urlExecInfo.get().getCid();
		val employeeCode = urlExecInfo.get().getScd();
		IdentificationResult idenResult = EmployeeIdentify.identifyByEmployeeCode(require, companyId, employeeCode);
		if(idenResult.isFailure()) {
			transaction.execute(idenResult.getAtomTask());
			result.setFailed("#Msg_1096");
			return result;
		}
		result.setIdentified(idenResult.getEmployeeInfo());

		return result;
	}

	/**
	 * 認証失敗時の処理
	 * @param require
	 * @param urlResult
	 * @return
	 */
	@Override
	protected UrlResult authenticationFailed(UrlLoginCommandHandler.Require require, UrlResult urlResult) {
		return urlResult;
	}

	@Override
	protected UrlResult loginCompleted(UrlLoginCommandHandler.Require require, UrlResult urlResult, Optional<String> msg) {
		msg.ifPresent(m -> urlResult.setMessageId(m));
		return urlResult;
	}

	public interface Require extends
			LoginCommandHandlerBase.Require,
			UrlLoginRequestValidator.Require,
			EmployeeIdentify.RequireByEmployeeCode {
		void loginRecord(LoginRecordInput loginRecord, String cid);
	}

	private class RequireImpl extends LoginRequire.BaseImpl implements Require {
		public RequireImpl(){
			loginRequire.setup(this);
		}

		@Override
		public Optional<UrlExecInfo> getUrlExecInfoByUrlID(String urlID) {
			return urlExecInfoRepository.getUrlExecInfoByUrlID(urlID);
		}

		@Override
		public void loginRecord(LoginRecordInput loginRecord, String cid) {
			loginRecordRegistService.loginRecord(loginRecord, cid);
		}

		@Override
		public Optional<EmployeeDataMngInfoImport> getEmployeeDataMngInfoImportByEmployeeCode(String companyId, String employeeCode) {
			return employeeDataManageInfoAdapter.findByEmployeeCode(companyId, employeeCode);
		}

		@Override
		public Optional<User> getUserByPersonId(String personId) {
			return userRepo.getByAssociatedPersonId(personId);
		}

		@Override
		public void addFailureLog(PasswordAuthIdentificationFailureLog failurLog) {
			passwordAuthIdentificateFailureLogRepo.insert(failurLog);
		}
	}
}
