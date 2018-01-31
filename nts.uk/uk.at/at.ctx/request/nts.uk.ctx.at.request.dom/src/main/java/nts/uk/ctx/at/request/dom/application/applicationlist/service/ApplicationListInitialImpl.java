package nts.uk.ctx.at.request.dom.application.applicationlist.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.applicationlist.extractcondition.AppListExtractCondition;
import nts.uk.ctx.at.request.dom.application.applicationlist.extractcondition.ApplicationDisplayAtr;
import nts.uk.ctx.at.request.dom.application.applicationlist.extractcondition.ApplicationListAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.RecordWorkInfoAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.RecordWorkInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.schedule.basicschedule.ScBasicScheduleAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.schedule.basicschedule.ScBasicScheduleImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WkpHistImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.other.CollectAchievement;
import nts.uk.ctx.at.request.dom.application.stamp.AppStamp;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampRepository;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.dom.setting.company.request.approvallistsetting.ApprovalListDisplaySetting;
import nts.uk.ctx.at.request.dom.setting.workplace.ApprovalFunctionSetting;
import nts.uk.ctx.at.request.dom.setting.workplace.RequestOfEachCompanyRepository;
import nts.uk.ctx.at.request.dom.setting.workplace.RequestOfEachWorkplaceRepository;
import nts.uk.ctx.at.request.dom.setting.workplace.SettingFlg;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
@Stateless
public class ApplicationListInitialImpl implements ApplicationListInitialRepository{

	@Inject
	private ClosureRepository repoClosure;
	@Inject
	private CollectAchievement collectAchievement;
	@Inject
	private AppStampRepository repoAppStamp;
	@Inject
	private RequestOfEachWorkplaceRepository repoRequestWkp;
	@Inject
	private RequestOfEachCompanyRepository repoRequestCompany;
	@Inject
	private ApplicationRepository_New repoApp;
	@Inject
	private ScBasicScheduleAdapter scBasicScheduleAdapter;
	@Inject
	private RecordWorkInfoAdapter recordWkpInfoAdapter;
	@Inject
	private EmployeeRequestAdapter empRequestAdapter;
	@Inject
	private WorkplaceAdapter wkpAdapter;
	
	/**
	 * 12 - 申請一覧初期日付期間
	 */
	@Override
	public DatePeriod getInitialPeriod(String companyId) {
		//アルゴリズム「会社の締め日を取得する」を実行する
		List<ClosureHistory> lstHist = repoClosure.findByCompanyId(companyId);
		GeneralDate end = GeneralDate.fromString("", "yyyy-MM-dd");
		for (ClosureHistory closureHistory : lstHist) {
			//年月
			YearMonth ym = closureHistory.getEndYearMonth();
			//締め日
			ClosureDate date = closureHistory.getClosureDate();
		}
		//締め日より開始日付を取得
		GeneralDate start = GeneralDate.fromString("", "yyyy-MM-dd");
		
		//開始日付の4か月後を終了日付として取得
		
		return new DatePeriod(start,end);
	}
	/**
	 * 0 - 申請一覧事前必須チェック
	 */
	@Override
	public Boolean checkAppPredictRequire(int appType, String wkpID) {
		//申請種類-(Check AppType)
		if(appType !=0 && appType != 6){//「休出時間申請」又は「残業申請」以外の場合
			return null;
		}
		//「休出時間申請」又は「残業申請」の場合
		//ドメイン「職場別申請承認設定」を取得する-(lấy dữ liệu domain Application approval setting by workplace)
		String companyId = AppContexts.user().companyId();
		Optional<ApprovalFunctionSetting> appFuncSet = null;
		appFuncSet = repoRequestWkp.getFunctionSetting(companyId, wkpID, appType);
		//対象が存在しない場合 - TH doi tuong k ton tai
		if(!appFuncSet.isPresent()){
			//ドメイン「会社別申請承認設定」を取得する-(lấy dữ liệu domain Application approval setting by company)
			appFuncSet = repoRequestCompany.getFunctionSetting(companyId, appType);
		}
		//申請承認機能設定.残業申請の事前必須設定
		if(appFuncSet.get().getOvertimeAppSetting().equals(SettingFlg.SETTING)){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 1 - 申請一覧リスト取得
	 */
	@Override
	public List<Application_New> getApplicationList(AppListExtractCondition param, ApprovalListDisplaySetting displaySet) {
		//申請一覧区分が申請 OR 承認-(Check xem là application hay aprove)
		if(param.getAppListAtr().equals(ApplicationListAtr.APPLICATION)){//申請
			//アルゴリズム「申請一覧リスト取得申請」を実行する - 2
			this.getApplicationListByApp(param);
		}else{//承認
			//アルゴリズム「申請一覧リスト取得承認」を実行する - 3
			this.getAppListByApproval(param,displaySet);
		}
		//取得した一覧の申請種類(単一化）でリストを作成する
		// TODO Auto-generated method stub
		//ドメインモデル「休暇申請設定」を取得する (Vacation application setting)- wait YenNTH
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 2 - 申請一覧リスト取得申請
	 */
	@Override
	public List<Application_New> getApplicationListByApp(AppListExtractCondition param) {
		String companyId = AppContexts.user().companyId();
		String sID = AppContexts.user().employeeId();
		// TODO Auto-generated method stub
		//ドメインモデル「申請」を取得する-(Lấy dữ liệu domain Application) - get List Application By SID
		List<Application_New> lstApp = repoApp.getListAppBySID(companyId, sID, param.getStartDate(), param.getEndDate());
		for (Application_New application : lstApp) {
			//アルゴリズム「申請一覧リスト取得打刻取消」を実行する-(Cancel get list app stamp): 7 - 申請一覧リスト取得打刻取消
			this.getListAppStampIsCancel(application);
			//アルゴリズム「申請一覧リスト取得振休振出」を実行する-(get List App Complement Leave): 6 - 申請一覧リスト取得振休振出
			this.getListAppComplementLeave(application);
			//アルゴリズム「申請一覧リスト取得休暇」を実行する-(get List App Absence): 8 - 申請一覧リスト取得休暇
			this.getListAppAbsence(application);
		}
		//imported(申請承認）「稟議書」を取得する - wait
		//アルゴリズム「申請一覧リスト取得マスタ情報」を実行する(get List App Master Info): 9 - 申請一覧リスト取得マスタ情報
		this.getListAppMasterInfo(lstApp);
		//申請日付順でソートする
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 3 - 申請一覧リスト取得承認
	 */
	@Override
	public List<Application_New> getAppListByApproval(AppListExtractCondition param, ApprovalListDisplaySetting displaySet) {
		String companyId = AppContexts.user().companyId();
		//ドメインモデル「承認機能設定」を取得する-(Lấy dữ liệu domain 承認機能設定) - wait hoi lai ben nhat
		// TODO Auto-generated method stub
		//申請一覧抽出条件.申請表示対象が「部下の申請」が指定-(Check đk lọc)
		if(param.getAppDisplayAtr().equals(ApplicationDisplayAtr.APP_SUB)){//部下の申請の場合
			//アルゴリズム「自部門職場と配下の社員をすべて取得する」を実行する - wait request
			// TODO Auto-generated method stub
		}
		//申請一覧抽出条件.申請表示対象が「事前通知」または「検討指示」が指定
		List<Application_New> lstApp = new ArrayList<>();
		if(!param.getAppDisplayAtr().equals(ApplicationDisplayAtr.PRIOR_NOTICE) || !param.getAppDisplayAtr().equals(ApplicationDisplayAtr.CONSIDER_INSTRUCT)){//「事前通知」または「検討指示」以外
			//ドメインモデル「代行者管理」を取得する-(Lấy dữ liệu domain 代行者管理) - wait request
			// TODO Auto-generated method stub
			//ドメインモデル「申請」を取得する-(Lấy dữ liệu domain 申請) - get List App By Reflect
			lstApp = repoApp.getListAppByReflect(companyId, param.getStartDate(), param.getEndDate());
			// TODO Auto-generated method stub
		}
		//imported(申請承認）「稟議書」を取得する - wait request : return list app
		// TODO Auto-generated method stub
		//アルゴリズム「申請一覧リスト取得マスタ情報」を実行する(get List App Master Info): 9 - 申請一覧リスト取得マスタ情報
		this.getListAppMasterInfo(lstApp);
		//アルゴリズム「申請一覧リスト取得実績」を実行する-(get App List Achievement): 5 - 申請一覧リスト取得実績
		this.getAppListAchievement(lstApp, displaySet);
		//承認一覧に稟議書リスト追加し、申請日付順に整列する - phu thuoc vao request
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * lam o ui
	 * 4 - 申請一覧リスト取得承認件数
	 */
	@Override
	public List<ApplicationStatus> countAppListApproval(List<Application_New> lstApp) {
		return null;
	}

	/**
	 * 5 - 申請一覧リスト取得実績
	 */
	@Override
	public AppListAtrOutput getAppListAchievement(List<Application_New> lstApp, ApprovalListDisplaySetting displaySet) {
		//申請一覧抽出条件.申請表示対象が「部下の申請」が指定-(Check đk lọc)
		if(true){//部下の申請の場合
			//アルゴリズム「自部門職場と配下の社員をすべて取得する」を実行する - wait request
		}
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 5.1 - 申請一覧リスト取得実績休出申請
	 */
	@Override
	public Boolean getAppListAchievementBreak(String sID, GeneralDate date) {
		// TODO Auto-generated method stub
		//Imported(申請承認)「勤務実績」を取得する - req #5
		RecordWorkInfoImport record = recordWkpInfoAdapter.getRecordWorkInfo(sID, date);
		//Imported(申請承認)「勤務予定」を取得する - req #4
		Optional<ScBasicScheduleImport> scBsSchedule = scBasicScheduleAdapter.findByID(sID, date);
		return null;
	}
	/**
	 * 6 - 申請一覧リスト取得振休振出
	 * wait HungDD
	 */
	@Override
	public List<Application_New> getListAppComplementLeave(Application_New application) {
		// TODO Auto-generated method stub
		//Check 申請種類 - appType
		if(!application.getAppType().equals(ApplicationType.COMPLEMENT_LEAVE_APPLICATION)){
			return null;
		}
		
		return null;
	}

	/**
	 * 7 - 申請一覧リスト取得打刻取消
	 */
	@Override
	public Boolean getListAppStampIsCancel(Application_New application) {
		String companyID = AppContexts.user().companyId();
		String applicantID = "";
		GeneralDate appDate = GeneralDate.today();
		//申請種類-(Check AppType)
		if(!application.getAppType().equals(ApplicationType.STAMP_APPLICATION)){
			return null;
		}
		//打刻申請.打刻申請モード-(Check 打刻申請モード)
		//get domain 打刻申請
		AppStamp stamp = repoAppStamp.findByAppID(companyID, application.getAppID());
		if(!stamp.equals(StampRequestMode.STAMP_CANCEL)){
			return null;
		}
		//アルゴリズム「実績の取得」を実行する - 13/KAF
		collectAchievement.getAchievement(companyID, applicantID, appDate);
		//アルゴリズム「勤務実績の取得」を実行する
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * 8 - 申請一覧リスト取得休暇
	 * wait
	 */
	@Override
	public List<Application_New> getListAppAbsence(Application_New application) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 9 - 申請一覧リスト取得マスタ情報
	 */
	@Override
	public List<Application_New> getListAppMasterInfo(List<Application_New> lstApp) {
		//ドメインモデル「申請一覧共通設定」を取得する-(Lấy domain Application list common settings) - wait YenNTH
		// TODO Auto-generated method stub
		//申請一覧リスト　繰返し実行
		for (Application_New app : lstApp) {
			//ドメインモデル「申請表示名」より申請表示名称を取得する-(Lấy Application display name) - wait YenNTH
			// TODO Auto-generated method stub
			//アルゴリズム「社員IDから個人社員基本情報を取得」を実行する - req #1
			String empName = empRequestAdapter.getEmployeeName(app.getEmployeeID());
			// TODO Auto-generated method stub
			//アルゴリズム「社員から職場を取得する」を実行する - req #30
			WkpHistImport wkp = wkpAdapter.findWkpBySid(app.getEmployeeID(), app.getAppDate());
			String wkpID = wkp == null ? "" : wkp.getWorkplaceId();
			//アルゴリズム「申請一覧事前必須チェック」を実行する- (check App Predict Require): 0 - 申請一覧事前必須チェック
			Boolean check = this.checkAppPredictRequire(app.getAppType().value, wkpID);
			if(check == true){//必須(True)
				//事前、事後の後ろに#CMM045_101(※)を追加
				// TODO Auto-generated method stub
			}
		}
		return null;
	}

}
