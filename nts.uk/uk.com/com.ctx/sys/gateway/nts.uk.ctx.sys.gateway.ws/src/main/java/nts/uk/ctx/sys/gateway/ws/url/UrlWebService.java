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
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.gateway.app.command.login.SubmitLoginFormOneCommandHandler;
import nts.uk.ctx.sys.gateway.app.command.login.SubmitLoginFormTwoCommandHandler;
import nts.uk.ctx.sys.gateway.dom.adapter.company.CompanyBsAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.company.CompanyBsImport;
import nts.uk.ctx.sys.gateway.dom.adapter.employee.EmployeeInfoAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.employee.EmployeeInfoDtoImport;
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserImportNew;
import nts.uk.ctx.sys.gateway.dom.login.Contract;
import nts.uk.ctx.sys.gateway.dom.login.ContractRepository;
import nts.uk.ctx.sys.gateway.dom.login.adapter.SysEmployeeAdapter;
import nts.uk.ctx.sys.gateway.dom.login.dto.EmployeeDataMngInfoImport;
import nts.uk.ctx.sys.gateway.dom.login.dto.EmployeeImport;
import nts.uk.ctx.sys.gateway.dom.login.dto.SDelAtr;
import nts.uk.ctx.sys.gateway.dom.mail.UrlExecInfoRepository;
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
		
		// URLパラメータの存在チェック
		if(Strings.isBlank(urlID)){
			throw new BusinessException("");
		}
		
		// ドメインモデル「埋込URL実行情報」を取得する
		Optional<UrlExecInfo> opUrlExecInfo = urlExecInfoRepository.getUrlExecInfoByUrlID(urlID);
		if(!opUrlExecInfo.isPresent()){
			throw new BusinessException("");
		}
		
		// システム日時が「埋込URL実行情報.有効期限」を超えていないことを確認する
		UrlExecInfo urlExecInfoExport = opUrlExecInfo.get();
		if(urlExecInfoExport.getExpiredDate().before(systemDateTime)){
			// record login
			// to do
			throw new BusinessException("Msg_1095");
		}
		
		// アルゴリズム「埋込URL実行契約セット」を実行する
		String contractCD = urlExecInfoExport.getContractCd();
		if(Strings.isBlank(contractCD)){
			contractCD = "000000000000";
		}
		// アルゴリズム「埋込URL実行契約セット」を実行する
		Contract contract = this.executionContractSet(contractCD);
		
		// アルゴリズム「埋込URL実行ログイン」を実行する
		this.executionURLLogin(urlExecInfoExport.getScd(), urlExecInfoExport.getLoginId(), urlExecInfoExport.getCid(), contract);
		
		// アルゴリズム「ログイン記録」を実行する１ Thực thi thuật toán "Login record"
		// to do
		
		// ドメインモデル「埋込URL実行情報」の「プログラムID」及び「遷移先の画面ID」に該当する画面へ遷移する
		urlExecInfoExport.getTaskIncre().forEach(x -> {
			result.put(x.getTaskIncreKey(), x.getTaskIncreValue());
		});
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
				result);
	}
	
	private Contract executionContractSet(String contractCD){
		GeneralDate systemDate = GeneralDate.today(); 
		// ドメインモデル「契約」を取得する
		Optional<Contract> opContract = contractRepository.getContract(contractCD);
		// 契約期間切れチェックする
		if(!opContract.isPresent() || 
			(systemDate.before(opContract.get().getContractPeriod().start())|| systemDate.after(opContract.get().getContractPeriod().end()))){
			// アルゴリズム「契約認証する_アクティビティ(基本)」を実行する
			throw new BusinessException("Msg_1317");
		}
		// LocalStorage上に「契約認証情報」を保存する
		// to do
		
		return opContract.get();
	}
	
	private void executionURLLogin(String employeeCD, String loginID, String companyID, Contract contract){
		// アルゴリズム「埋込URL実行ログインアカウント承認」を実行する
		URLAccApprovalOutput urlAccApprovalOutput = this.executionURLAccApproval(employeeCD, companyID, loginID, contract);
		
		// アルゴリズム「埋込URL実行ログインチェック」を実行する
		executionURLLoginCheck(companyID, loginID, employeeCD, contract, urlAccApprovalOutput.getEmployeeInfoDtoImport());
		
		// 社員コードの存在チェック
		if(Strings.isBlank(employeeCD)){
			submitLoginFormOneCommandHandler.initSession(urlAccApprovalOutput.getUserImport(), false);
		} else {
			String companyCD = companyBsAdapter.getCompanyByCid(companyID).getCompanyCode();
			submitLoginFormTwoCommandHandler.setLoggedInfo(
					urlAccApprovalOutput.getUserImport(), 
					new EmployeeImport(
							urlAccApprovalOutput.getEmployeeInfoDtoImport().getCompanyId(), 
							urlAccApprovalOutput.getEmployeeInfoDtoImport().getPersonId(), 
							urlAccApprovalOutput.getEmployeeInfoDtoImport().getEmployeeId(), 
							urlAccApprovalOutput.getEmployeeInfoDtoImport().getEmployeeCode()), 
					companyCD);
			submitLoginFormTwoCommandHandler.setRoleId(urlAccApprovalOutput.getUserImport().getUserId());
		}
	}
	
	private URLAccApprovalOutput executionURLAccApproval(String employeeCD, String companyID, String loginID, Contract contract){
		 
		// 社員コードの存在チェック
		if(Strings.isBlank(employeeCD)){
			// imported（ゲートウェイ）「ユーザ」を取得する requestList222
			Optional<UserImportNew> opUserImportNew = userAdapter.findUserByContractAndLoginIdNew(contract.getContractCode().toString(), loginID);
			this.executeUserExport(opUserImportNew);
			return new URLAccApprovalOutput(null, opUserImportNew.get());
		}
		
		// Imported（GateWay）「社員」を取得する
		EmployeeInfoDtoImport employeeInfoDtoImport = employeeInfoAdapter.getEmployeeInfo(companyID, employeeCD);
		if(employeeInfoDtoImport==null){
			this.failUserExport();
		}
		
		// アルゴリズム「社員が削除されたかを取得」を実行する
		Optional<EmployeeDataMngInfoImport> opEmployeeDataMngInfoImport = sysEmployeeAdapter.getSdataMngInfo(employeeInfoDtoImport.getEmployeeId());
		if(!opEmployeeDataMngInfoImport.isPresent() || 
				opEmployeeDataMngInfoImport.get().getDeletedStatus()==SDelAtr.DELETED){
			this.failUserExport();
		}
		
		// imported（ゲートウェイ）「ユーザ」を取得する requestList220
		Optional<UserImportNew> opUserImportNew = userAdapter.findUserByAssociateId(employeeInfoDtoImport.getPersonId());
		this.executeUserExport(opUserImportNew);
		return new URLAccApprovalOutput(employeeInfoDtoImport, opUserImportNew.get());
	}
	
	private void executionURLLoginCheck(String companyID, String loginID, String employeeCD, Contract contract, EmployeeInfoDtoImport employeeInfoDtoImport){
		GeneralDate systemDate = GeneralDate.today();
		// ドメインモデル「契約」の契約期間をチェックする
		if(systemDate.before(contract.getContractPeriod().start()) || systemDate.after(contract.getContractPeriod().end())){
			throw new BusinessException("Msg_1096");
			// アルゴリズム「ログイン記録」を実行する１
		}
		
		// ドメインモデル「会社情報」の使用区分をチェックする
		CompanyBsImport companyBsImport = companyBsAdapter.getCompanyByCid(companyID);
		if(companyBsImport.getIsAbolition()==1){
			throw new BusinessException("Msg_1096");
			// アルゴリズム「ログイン記録」を実行する１
		}
		
		// 社員コードの存在を確認
		if(Strings.isBlank(employeeCD)){
			throw new BusinessException("");
		}
		
		// アルゴリズム「アカウントロックチェック」を実行する
		// to do
	}
	
	private void executeUserExport(Optional<UserImportNew> opUserImportNew){
		GeneralDate systemDate = GeneralDate.today();
		if(opUserImportNew.isPresent()){
			// ユーザーの有効期限チェック
			if(systemDate.after(opUserImportNew.get().getExpirationDate())){
				throw new BusinessException("Msg_316");
				// アルゴリズム「ログイン記録」を実行する２
				// to do
			}
			return;
		} else {
			this.failUserExport();
		}
	}
	
	private void failUserExport(){
		throw new BusinessException("Msg_301");
		// アルゴリズム「ログイン記録」を実行する１
		// to do
		
		// アルゴリズム「ロックアウト」を実行する　※２次対応
		// to do
	}
}

