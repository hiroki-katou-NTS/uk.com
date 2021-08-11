package nts.uk.ctx.sys.gateway.ws.url;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.logging.log4j.util.Strings;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.gateway.app.command.login.password.CheckChangePassDto;
import nts.uk.ctx.sys.gateway.app.command.loginold.LoginRecordRegistService;
import nts.uk.ctx.sys.gateway.app.command.loginold.SubmitLoginFormOneCommandHandler;
import nts.uk.ctx.sys.gateway.app.command.loginold.SubmitLoginFormTwoCommandHandler;
import nts.uk.ctx.sys.gateway.app.command.loginold.dto.LoginRecordInput;
import nts.uk.ctx.sys.gateway.app.command.systemsuspend.SystemSuspendOutput;
import nts.uk.ctx.sys.gateway.app.command.systemsuspend.SystemSuspendService;
import nts.uk.ctx.sys.gateway.dom.adapter.company.CompanyBsAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.company.CompanyBsImport;
import nts.uk.ctx.sys.gateway.dom.adapter.employee.EmployeeInfoAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.employee.EmployeeInfoDtoImport;
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserImportNew;
import nts.uk.ctx.sys.gateway.dom.loginold.Contract;
import nts.uk.ctx.sys.gateway.dom.loginold.ContractRepository;
import nts.uk.ctx.sys.gateway.dom.mail.UrlExecInfoRepository;
import nts.uk.ctx.sys.shared.dom.employee.EmployeeDataMngInfoImport;
import nts.uk.ctx.sys.shared.dom.employee.EmployeeImport;
import nts.uk.ctx.sys.shared.dom.employee.SDelAtr;
import nts.uk.ctx.sys.shared.dom.employee.SysEmployeeAdapter;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.DeviceInfo;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.program.ProgramsManager;
import nts.uk.shr.com.url.UrlExecInfo;

@Path("ctx/sys/gateway/url")
public class UrlWebService {
	
	@Inject
	private UrlExecInfoRepository urlExecInfoRepository;
	
	@Inject
	private ContractRepository contractRepository;
	
	@Inject
	private UserAdapter userAdapter;
	
	@Inject
	private EmployeeInfoAdapter employeeInfoAdapter;
	
	@Inject
	private SysEmployeeAdapter sysEmployeeAdapter;
	
	@Inject
	private CompanyBsAdapter companyBsAdapter;
	
	@Inject
	private SubmitLoginFormOneCommandHandler submitLoginFormOneCommandHandler;
	
	@Inject
	private SubmitLoginFormTwoCommandHandler submitLoginFormTwoCommandHandler;
	
	@Inject
	private LoginRecordRegistService loginRecordRegistService;
	
	@Inject
	private SystemSuspendService systemSuspendService;
	
	/**
	 * 埋込URL実行
	 * @param screeenPath
	 * @param urlID 埋込URLID
	 * @return
	 */
	@POST
	@Path("execution/{urlID}")
	@Produces("application/json")
	public UrlResult executionURL(@PathParam("urlID") String urlID) {
		Map<String, String> result = new HashMap<>();
		GeneralDateTime systemDateTime = GeneralDateTime.now();
		
		//URLパラメータの存在チェック(Check param URL)
		if(Strings.isBlank(urlID)){
			throw new BusinessException("");
		}
		
		// ドメインモデル「埋込URL実行情報」を取得する
		Optional<UrlExecInfo> opUrlExecInfo = urlExecInfoRepository.getUrlExecInfoByUrlID(urlID);
		if(!opUrlExecInfo.isPresent()){
			throw new BusinessException("");
		}
		DeviceInfo device = AppContexts.deviceInfo();
		//システム日時が「埋込URL実行情報.有効期限」を超えていないことを確認する (So sánh sysDate với EXpirationDate)
		UrlExecInfo urlExecInfoExport = opUrlExecInfo.get();
		if(urlExecInfoExport.getExpiredDate().before(systemDateTime)){
			//アルゴリズム「ログイン記録」を実行する２ (Ghi log)
			String employeeCD = Strings.isNotBlank(urlExecInfoExport.getScd()) ? urlExecInfoExport.getScd() + " " : "";
			String loginID = Strings.isNotBlank(urlExecInfoExport.getLoginId()) ? urlExecInfoExport.getLoginId() + " " : "";
			loginRecordRegistService.loginRecord(
					new LoginRecordInput(
							urlExecInfoExport.getProgramId(), 
							urlExecInfoExport.getScreenId(), 
							"", 
							1, 
							2, 
							"", 
							employeeCD + loginID + TextResource.localize("Msg_1474") + " " + TextResource.localize("Msg_1095"), 
							urlExecInfoExport.getSid()), 
					urlExecInfoExport.getCid());
			//メッセージ（#Msg_1095）を表示する（有効期間エラー）(Msg_1095: Lỗi kỳ hạn hiệu lực)
			throw new BusinessException("Msg_1095");
		}
		
		//アルゴリズム「埋込URL実行契約セット」を実行する (set contract)
		String contractCD = urlExecInfoExport.getContractCd();
		if(Strings.isBlank(contractCD)){
			contractCD = "000000000000";
		}
		// アルゴリズム「埋込URL実行契約セット」を実行する
		Contract contract = this.executionContractSet(contractCD, urlExecInfoExport);
		// アルゴリズム「埋込URL実行ログイン」を実行する
		CheckChangePassDto changePw = this.executionURLLogin(urlExecInfoExport.getScd(), 
				urlExecInfoExport.getLoginId(), urlExecInfoExport.getCid(), contract, urlExecInfoExport);
		// ドメインモデル「埋込URL実行情報」の「プログラムID」及び「遷移先の画面ID」に該当する画面へ遷移する
		urlExecInfoExport.getTaskIncre().forEach(x -> {
			result.put(x.getTaskIncreKey(), x.getTaskIncreValue());
		});
		String webAppID = ProgramsManager.findById(urlExecInfoExport.getProgramId()+urlExecInfoExport.getScreenId())
				.map(x -> x.getAppId().toString().toLowerCase()).orElse("");
		// cho KAF màn B nếu không khai báo trong ProgramsManager
		if(Strings.isBlank(webAppID)){
			webAppID = ProgramsManager.findById(urlExecInfoExport.getProgramId()+"A")
					.map(x -> x.getAppId().toString().toLowerCase()).orElse("");
		}
		return new UrlResult(
				urlExecInfoExport.getProgramId().toLowerCase(), 
				urlExecInfoExport.getScreenId().toLowerCase(), 
				urlExecInfoExport.getEmbeddedId(),
			    urlExecInfoExport.getCid(),
			    urlExecInfoExport.getLoginId(),
			    urlExecInfoExport.getContractCd(),
			    urlExecInfoExport.getExpiredDate(),
			    urlExecInfoExport.getIssueDate(),
			    urlExecInfoExport.getSid(),
			    urlExecInfoExport.getScd(),
				result,
				webAppID,
				changePw,
				device.isSmartPhone());
	}
	
	private Contract executionContractSet(String contractCD, UrlExecInfo urlExecInfoExport){
		GeneralDate systemDate = GeneralDate.today(); 
		// ドメインモデル「契約」を取得する
		Optional<Contract> opContract = contractRepository.getContract(contractCD);
		// 契約期間切れチェックする
		if(!opContract.isPresent() || 
			(systemDate.before(opContract.get().getContractPeriod().start())|| systemDate.after(opContract.get().getContractPeriod().end()))){
			String employeeCD = Strings.isNotBlank(urlExecInfoExport.getScd()) ? urlExecInfoExport.getScd() + " " : "";
			String loginID = Strings.isNotBlank(urlExecInfoExport.getLoginId()) ? urlExecInfoExport.getLoginId() + " " : "";
			loginRecordRegistService.loginRecord(
					new LoginRecordInput(
							urlExecInfoExport.getProgramId(), 
							urlExecInfoExport.getScreenId(), 
							"", 
							1, 
							2, 
							"", 
							employeeCD + loginID + TextResource.localize("Msg_1474") + " " + TextResource.localize("Msg_1317"), 
							urlExecInfoExport.getSid()), 
					urlExecInfoExport.getCid());
			// アルゴリズム「契約認証する_アクティビティ(基本)」を実行する
			throw new BusinessException("Msg_1317");
		}
		// LocalStorage上に「契約認証情報」を保存する
		// to do
		
		return opContract.get();
	}
	/**
	 * 埋込URL実行ログイン
	 * @param employeeCD
	 * @param loginID
	 * @param companyID
	 * @param contract
	 * @param urlExecInfoExport
	 * @return
	 */
	private CheckChangePassDto executionURLLogin(String employeeCD, String loginID, String companyID, Contract contract, UrlExecInfo urlExecInfoExport){
		// アルゴリズム「埋込URL実行ログインアカウント承認」を実行する
		URLAccApprovalOutput urlAccApprovalOutput = this.executionURLAccApproval(employeeCD, companyID, loginID, contract, urlExecInfoExport);
		
		// アルゴリズム「埋込URL実行ログインチェック」を実行する
		executionURLLoginCheck(companyID, loginID, employeeCD, contract, urlAccApprovalOutput.getEmployeeInfoDtoImport(), urlExecInfoExport);
		
		String companyCD = AppContexts.user().companyCode();
		// 社員コードの存在チェック
		if(Strings.isBlank(employeeCD)){
			//アルゴリズム「セッション生成」を実行する　※ログイン形式１
			submitLoginFormOneCommandHandler.initSession(urlAccApprovalOutput.getUserImport());
		} else {
			//ドメインモデル「会社情報」を取得する
			companyCD = companyBsAdapter.getCompanyByCid(companyID).getCompanyCode();
			//アルゴリズム「埋込URLセッション生成」を実行する
			submitLoginFormTwoCommandHandler.setLoggedInfo(
					urlAccApprovalOutput.getUserImport(), 
					new EmployeeImport(
							urlAccApprovalOutput.getEmployeeInfoDtoImport().getCompanyId(), 
							urlAccApprovalOutput.getEmployeeInfoDtoImport().getPersonId(), 
							urlAccApprovalOutput.getEmployeeInfoDtoImport().getEmployeeId(), 
							urlAccApprovalOutput.getEmployeeInfoDtoImport().getEmployeeCode()), 
					companyCD);
			submitLoginFormTwoCommandHandler.setRoleInfo(urlAccApprovalOutput.getUserImport().getUserId());
		}
		
		// アルゴリズム「システム利用停止の確認」を実行する
		SystemSuspendOutput systemSuspendOutput = systemSuspendService.confirmSystemSuspend(
				contract.getContractCode().v(), 
				companyCD,
				2,
				urlExecInfoExport.getProgramId(),
				urlExecInfoExport.getScreenId());
		if(systemSuspendOutput.isError()){
			throw new BusinessException(new RawErrorMessage(systemSuspendOutput.getMsgContent()));
		}
		// アルゴリズム「ログイン記録」を実行する１ Thực thi thuật toán "Login record"
		loginRecordRegistService.loginRecord(
				new LoginRecordInput(
						urlExecInfoExport.getProgramId(), 
						urlExecInfoExport.getScreenId(), 
						"", 
						0, 
						2, 
						"", 
						TextResource.localize("Msg_1474"), 
						null), 
				"");
		//アルゴリズム「ログイン後チェック」を実行する
		CheckChangePassDto changPw = submitLoginFormOneCommandHandler.checkAfterLogin(urlAccApprovalOutput.getUserImport(), urlAccApprovalOutput.getUserImport().getPassword(), false);
		changPw.setSuccessMsg(systemSuspendOutput.getMsgContent());
		return changPw;
	}
	
	private URLAccApprovalOutput executionURLAccApproval(String employeeCD, String companyID, String loginID, Contract contract, UrlExecInfo urlExecInfoExport){
		 
		// 社員コードの存在チェック
		if(Strings.isBlank(employeeCD)){
			// imported（ゲートウェイ）「ユーザ」を取得する requestList222
			Optional<UserImportNew> opUserImportNew = userAdapter.findUserByContractAndLoginIdNew(contract.getContractCode().toString(), loginID);
			this.executeUserExport(opUserImportNew, urlExecInfoExport);
			return new URLAccApprovalOutput(null, opUserImportNew.get());
		}
		
		// Imported（GateWay）「社員」を取得する
		EmployeeInfoDtoImport employeeInfoDtoImport = employeeInfoAdapter.getEmployeeInfo(companyID, employeeCD);
		if(employeeInfoDtoImport==null){
			this.failUserExport(urlExecInfoExport);
		}
		
		// アルゴリズム「社員が削除されたかを取得」を実行する
		Optional<EmployeeDataMngInfoImport> opEmployeeDataMngInfoImport = sysEmployeeAdapter.getSdataMngInfo(employeeInfoDtoImport.getEmployeeId());
		if(!opEmployeeDataMngInfoImport.isPresent() || 
				opEmployeeDataMngInfoImport.get().getDeletedStatus()==SDelAtr.DELETED){
			this.failUserExport(urlExecInfoExport);
		}
		
		// imported（ゲートウェイ）「ユーザ」を取得する requestList220
		Optional<UserImportNew> opUserImportNew = userAdapter.findUserByAssociateId(employeeInfoDtoImport.getPersonId());
		this.executeUserExport(opUserImportNew, urlExecInfoExport);
		return new URLAccApprovalOutput(employeeInfoDtoImport, opUserImportNew.get());
	}
	
	private void executionURLLoginCheck(String companyID, String loginID, String employeeCD, Contract contract, 
			EmployeeInfoDtoImport employeeInfoDtoImport, UrlExecInfo urlExecInfoExport){
		GeneralDate systemDate = GeneralDate.today();
		// ドメインモデル「契約」の契約期間をチェックする
		if(systemDate.before(contract.getContractPeriod().start()) || systemDate.after(contract.getContractPeriod().end())){
			// アルゴリズム「ログイン記録」を実行する１
			loginRecordRegistService.loginRecord(
					new LoginRecordInput(
							urlExecInfoExport.getProgramId(), 
							urlExecInfoExport.getScreenId(), 
							"", 
							0, 
							2, 
							"", 
							"Msg_1474", 
							null), 
					urlExecInfoExport.getCid());
			
			throw new BusinessException("Msg_1096");
		}
		
		// ドメインモデル「会社情報」の使用区分をチェックする
		CompanyBsImport companyBsImport = companyBsAdapter.getCompanyByCid(companyID);
		if(companyBsImport.getIsAbolition()==1){
			// アルゴリズム「ログイン記録」を実行する１
			loginRecordRegistService.loginRecord(
					new LoginRecordInput(
							urlExecInfoExport.getProgramId(), 
							urlExecInfoExport.getScreenId(), 
							"", 
							0, 
							2, 
							"", 
							"Msg_1474", 
							null), 
					urlExecInfoExport.getCid());
			
			throw new BusinessException("Msg_1096");
		}
		
		// 社員コードの存在を確認
		if(Strings.isBlank(employeeCD)){
			throw new BusinessException("");
		}
		
		// アルゴリズム「アカウントロックチェック」を実行する
		// to do
	}
	
	private void executeUserExport(Optional<UserImportNew> opUserImportNew, UrlExecInfo urlExecInfoExport){
		GeneralDate systemDate = GeneralDate.today();
		if(opUserImportNew.isPresent()){
			// ユーザーの有効期限チェック
			if(systemDate.after(opUserImportNew.get().getExpirationDate())){
				// アルゴリズム「ログイン記録」を実行する２
				loginRecordRegistService.loginRecord(
					new LoginRecordInput(
							urlExecInfoExport.getProgramId(), 
							urlExecInfoExport.getScreenId(), 
							"", 
							1, 
							2, 
							"", 
							"#Msg_1474 "+TextResource.localize("Msg_1474"), 
							null), 
					urlExecInfoExport.getCid());
				
				throw new BusinessException("Msg_316");
			}
			return;
		} else {
			this.failUserExport(urlExecInfoExport);
		}
	}
	
	private void failUserExport(UrlExecInfo urlExecInfoExport){
		// アルゴリズム「ログイン記録」を実行する１
		loginRecordRegistService.loginRecord(
			new LoginRecordInput(
					urlExecInfoExport.getProgramId(), 
					urlExecInfoExport.getScreenId(), 
					"", 
					1, 
					2, 
					"", 
					"#Msg_1474 "+TextResource.localize("Msg_1474"), 
					null), 
			urlExecInfoExport.getCid());
		
		// アルゴリズム「ロックアウト」を実行する　※２次対応
		// submitLoginFormOneCommandHandler.lockOutExecuted(user);
		
		throw new BusinessException("Msg_301");
	}
}

