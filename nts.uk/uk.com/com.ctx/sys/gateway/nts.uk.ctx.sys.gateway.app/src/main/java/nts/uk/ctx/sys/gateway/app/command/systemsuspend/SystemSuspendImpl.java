package nts.uk.ctx.sys.gateway.app.command.systemsuspend;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.uk.ctx.sys.gateway.app.command.login.LoginRecordRegistService;
import nts.uk.ctx.sys.gateway.app.command.login.dto.LoginRecordInput;
import nts.uk.ctx.sys.gateway.dom.login.adapter.RoleAdapter;
import nts.uk.ctx.sys.gateway.dom.login.service.CollectCompanyList;
import nts.uk.ctx.sys.gateway.dom.stopbycompany.StopByCompany;
import nts.uk.ctx.sys.gateway.dom.stopbycompany.StopByCompanyRepository;
import nts.uk.ctx.sys.gateway.dom.stopbycompany.StopModeType;
import nts.uk.ctx.sys.gateway.dom.stopbycompany.SystemStatusType;
import nts.uk.ctx.sys.gateway.dom.stopbysystem.StopBySystem;
import nts.uk.ctx.sys.gateway.dom.stopbysystem.StopBySystemRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.loginuser.role.LoginUserRoles;

@Stateless
public class SystemSuspendImpl implements SystemSuspendService {
	
	@Inject
	private StopBySystemRepository stopBySystemRepository;
	
	@Inject
	private StopByCompanyRepository stopByCompanyRepository;
	
	@Inject
	private RoleAdapter roleAdapter;
	
	@Inject
	private LoginRecordRegistService loginRecordRegistService;
	@Inject
	private CollectCompanyList colComList;

	@Override
	public SystemSuspendOutput confirmSystemSuspend(String contractCD, String companyCD, int loginMethod, String programID, String screenID) {
		// 「利用停止するしない」をチェックする
		UsageStopOutput usageStopOutput = this.checkUsageStop(contractCD, companyCD);
		if(!usageStopOutput.isUsageStop()){
			return new SystemSuspendOutput(false, "", "");
		}
		LoginUserRoles loginUserRoles = AppContexts.user().roles();
		// [利用停止モード]を判別
		if(usageStopOutput.getStopMode()==StopModeType.ADMIN_MODE){
			// システム管理者ロールの設定があるか判別
			if(Strings.isNotBlank(loginUserRoles.forSystemAdmin())){
				return new SystemSuspendOutput(false, "Msg_1475", "");
			} 
			// 会社管理者ロールの設定があるか判別
			if(Strings.isNotBlank(loginUserRoles.forCompanyAdmin())){
				return new SystemSuspendOutput(false, "Msg_1475", "");
			}
		} else {
			// システム管理者ロールの設定があるか判別
			if(Strings.isNotBlank(loginUserRoles.forSystemAdmin())){
				return new SystemSuspendOutput(false, "Msg_1475", "");
			} 
			// 会社管理者ロールの設定があるか判別
			if(Strings.isNotBlank(loginUserRoles.forCompanyAdmin())){
				return new SystemSuspendOutput(false, "Msg_1475", "");
			}
			// リクエストリスト497を呼ぶ。：「ログイン者が担当者か判断する」で担当者ロールが存在するかを判別
			if(roleAdapter.isEmpWhetherLoginerCharge()){
				return new SystemSuspendOutput(false, "Msg_1475", "");
			}
		}
		// エラーメッセージダイアログを表示して、処理をエラー状態とする
		String msg = usageStopOutput.getStopMessage();
		// アルゴリズム「ログイン記録」を実行する１
		loginRecordRegistService.loginRecord(
				new LoginRecordInput(
						programID, 
						screenID, 
						"", 
						1, 
						loginMethod, 
						"", 
						"システム利用停止状態", 
						null), 
				"");
		return new SystemSuspendOutput(true, "", msg);
	}

	private UsageStopOutput checkUsageStop(String contractCD, String companyCD){
		// ドメインモデル「システム全体の利用停止」を取得する
		Optional<StopBySystem> opStopBySystem = stopBySystemRepository.findByKey(contractCD);
		if(opStopBySystem.isPresent()){
			// ドメインモデル「システム全体の利用停止.システム利用状態」をチェックする
			if(opStopBySystem.get().getSystemStatus()==SystemStatusType.STOP){
				// 「利用停止する」、　【システム全体の利用停止.利用停止モード】を返す
				return new UsageStopOutput(true, opStopBySystem.get().getStopMode(), opStopBySystem.get().getStopMessage().v());
			}
		}
		// ドメインモデル「会社単位の利用停止」を取得する
		Optional<StopByCompany> opStopByCompany = stopByCompanyRepository.findByKey(contractCD, companyCD);
		if(!opStopByCompany.isPresent()){
			// 「利用停止しない」を返す
			return new UsageStopOutput(false, StopModeType.ADMIN_MODE, "");
		}
		if(opStopByCompany.get().getSystemStatus()==SystemStatusType.STOP){
			// 「利用停止する」、　【会社単位の利用停止.利用停止モード】を返す
			return new UsageStopOutput(true, opStopByCompany.get().getStopMode(), opStopByCompany.get().getStopMessage().v()); 
		} 
		// 「利用停止しない」を返す
		return new UsageStopOutput(false, StopModeType.ADMIN_MODE, "");
	}

	/**
	 * システム利用停止の確認_ログイン前
	 */
	@Override
	public SystemSuspendOutput confirmSystemSuspend_BefLog(String contractCD, String companyCD, int loginMethod,
			String programID, String screenID) {
		// 「利用停止するしない」をチェックする
		UsageStopOutput usageStopOutput = this.checkUsageStop(contractCD, companyCD);
		if(!usageStopOutput.isUsageStop()){//利用停止しないの場合
			return new SystemSuspendOutput(false, "", "");
		}
		//利用停止するの場合
		String companyIdNew = contractCD + "-" + companyCD;
		//利用停止のチェック
		List<String> lstFil = colComList.checkStopUse(contractCD, Arrays.asList(companyIdNew), AppContexts.user().userId());
		if(!lstFil.isEmpty()){
			return new SystemSuspendOutput(false, "Msg_1475", "");
		}
		//エラーメッセージダイアログを表示して、処理をエラー状態とする
		String msg = usageStopOutput.getStopMessage();
		// アルゴリズム「ログイン記録」を実行する１
		loginRecordRegistService.loginRecord(
				new LoginRecordInput(
						programID, 
						screenID, 
						"", 
						1, 
						loginMethod, 
						"", 
						"システム利用停止状態", 
						null), 
				companyIdNew);
		return new SystemSuspendOutput(true, "", msg);
	}
	
}
