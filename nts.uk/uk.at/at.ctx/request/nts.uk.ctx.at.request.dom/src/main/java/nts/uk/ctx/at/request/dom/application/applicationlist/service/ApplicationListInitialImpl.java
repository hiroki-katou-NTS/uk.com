package nts.uk.ctx.at.request.dom.application.applicationlist.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReflectedState_New;
import nts.uk.ctx.at.request.dom.application.applicationlist.extractcondition.AppListExtractCondition;
import nts.uk.ctx.at.request.dom.application.applicationlist.extractcondition.ApplicationDisplayAtr;
import nts.uk.ctx.at.request.dom.application.applicationlist.extractcondition.ApplicationListAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.SEmpHistImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.RecordWorkInfoAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.RecordWorkInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.schedule.basicschedule.ScBasicScheduleAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.schedule.basicschedule.ScBasicScheduleImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalBehaviorAtrImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalFrameImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WkpHistImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.other.CollectAchievement;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementOutput;
import nts.uk.ctx.at.request.dom.application.stamp.AppStamp;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampRepository;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationcommonsetting.AppCommonSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationcommonsetting.AppCommonSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HdAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HdAppSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.displayname.AppDispName;
import nts.uk.ctx.at.request.dom.setting.company.displayname.AppDispNameRepository;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.deadlinesetting.AppDeadlineSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.deadlinesetting.AppDeadlineSettingRepository;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.displaysetting.DisplayAtr;
import nts.uk.ctx.at.request.dom.setting.company.request.approvallistsetting.ApprovalListDisplaySetting;
import nts.uk.ctx.at.request.dom.setting.workplace.ApprovalFunctionSetting;
import nts.uk.ctx.at.request.dom.setting.workplace.RequestOfEachCompanyRepository;
import nts.uk.ctx.at.request.dom.setting.workplace.RequestOfEachWorkplaceRepository;
import nts.uk.ctx.at.request.dom.setting.workplace.SettingFlg;
import nts.uk.ctx.at.shared.dom.relationship.repository.RelationshipRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.CurrentMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;
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
	@Inject
	private AppCommonSetRepository repoAppCommonSet;
	@Inject
	private AppDispNameRepository repoAppDispName;
	@Inject
	private HdAppSetRepository repoHdAppSet;
	@Inject
	private RelationshipRepository repoRelationship;
	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;
	@Inject
	private AppDeadlineSettingRepository repoDeadlineSet;
	@Inject
	private EmployeeRequestAdapter employeeAdaptor;
	@Inject
	private ClosureEmploymentRepository closureEmpRepository;
	
	/**
	 * 12 - 申請一覧初期日付期間
	 */
	@Override
	public DatePeriod getInitialPeriod(String companyId) {
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		//ドメイン「締め」を取得する
		List<Closure> lstClosure = repoClosure.findAllActive(companyID, UseClassification.UseClass_Use);
		List<ClosureHistory> lstClosureHist = new ArrayList<>();
		for (Closure closure : lstClosure) {
			ClosureHistory closureHist = this.findHistClosure(closure.getClosureHistories(), closure.getClosureMonth());
			if(closureHist != null){
				lstClosureHist.add(closureHist);
			}
		}
		
		
		
		
		GeneralDate baseDate = GeneralDate.today();
		//find employment by employeeId
		SEmpHistImport empHistImport = employeeAdaptor.getEmpHist(companyID, employeeID, baseDate);
		//アルゴリズム「会社の締め日を取得する」を実行する
		//アルゴリズム「締めIDを取得する」を実行する
		Optional<ClosureEmployment> closureEmployment = closureEmpRepository.findByEmploymentCD(companyID, empHistImport.getEmploymentCode());
		//ドメインモデル「申請締切設定」を取得する (get domain deadline setting)
		Optional<AppDeadlineSetting> deadlinSet =  repoDeadlineSet.getDeadlineByClosureId(companyId, closureEmployment.get().getClosureId());
		
		//締め日より開始日付を取得
		// TODO Auto-generated method stub
		GeneralDate start = GeneralDate.fromString("", "yyyy-MM-dd");
		GeneralDate end = GeneralDate.fromString("", "yyyy-MM-dd");
		//開始日付の4か月後を終了日付として取得
		// TODO Auto-generated method stub
		return new DatePeriod(start,end);
	}
	private ClosureHistory findHistClosure(List<ClosureHistory> closureHistories, CurrentMonth closureMonth){
		for (ClosureHistory closureHist : closureHistories) {
			if(closureHist.getStartYearMonth().lessThanOrEqualTo(closureMonth.getProcessingYm()) &&
					closureHist.getEndYearMonth().greaterThanOrEqualTo(closureMonth.getProcessingYm())){
				return closureHist;
			}
		}
		return null;
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
		//取得した一覧の申請種類(単一化）でリストを作成する - create list for drop-down list (A4_4_1)
		// TODO Auto-generated method stub
		//ドメインモデル「休暇申請設定」を取得する (Vacation application setting)- YenNTH
		Optional<HdAppSet> lstHdAppSet = repoHdAppSet.getAll();
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
			//アルゴリズム「申請一覧リスト取得出張」を実行する
			// TODO Auto-generated method stub
		}
		//imported(申請承認）「稟議書」を取得する - wait request : return list app - tam thoi bo qua
		// TODO Auto-generated method stub
		//アルゴリズム「申請一覧リスト取得マスタ情報」を実行する(get List App Master Info): 9 - 申請一覧リスト取得マスタ情報
		List<Application_New> lstMaster = this.getListAppMasterInfo(lstApp);
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
	public ApplicationStatus countAppListApproval(List<Application_New> lstApp) {
		ApplicationStatus appStatus = new ApplicationStatus(0,0,0,0,0,0,0);
		List<ApplicationFullOutput> lstAppFull = mergeAppAndPhase(lstApp);
		for (ApplicationFullOutput appFull : lstAppFull) {
			PhaseFrameStatus status = this.findPhaseFrameStatus(appFull.getLstPhaseState(), AppContexts.user().employeeId());
			int statusApp = this.calApplication(appFull.getApplication().getReflectionInformation().getStateReflection(), status, status.getAgentId());
			appFull.setStatus(statusApp);
			appFull.setAgentId(status.getAgentId());
		}
		
		for (ApplicationFullOutput appFull : lstAppFull) {
			switch(appFull.getStatus()){
				case 0:
					appStatus.setNotReflectNumber(appStatus.getNotReflectNumber() + 1);
					break;
				case 1:
					//k lam gi ca
//					appStatus.setWaitReflectNumber(appStatus.getWaitReflectNumber() + 1);
					break;
				case 2:
					//check
					if(true){
						
					}else{
						
					}
					appStatus.setReflectNumber(appStatus.getReflectNumber() + 1);
					break;
				case 3:
//					appStatus.setWaitCancelNumber(appStatus.getWaitCancelNumber() + 1);
					break;
				case 4:
					appStatus.setCancelNumber(appStatus.getCancelNumber() + 1);
					break;
				case 5:
					appStatus.setRemandNumner(appStatus.getRemandNumner() + 1);
					break;
				case 6:
					appStatus.setDenialNumber(appStatus.getDenialNumber() + 1);
					break;
				default:
					break;
			}
		}
		return appStatus;
	}

	/**
	 * 5 - 申請一覧リスト取得実績
	 */
	@Override
	public AppListAtrOutput getAppListAchievement(List<Application_New> lstApp, ApprovalListDisplaySetting displaySet) {
		List<Application_New> lstPost = lstApp;
		List<Application_New> lstDif = lstApp;
		lstPost.stream().filter(c -> c.getAppType().equals(ApplicationType.OVER_TIME_APPLICATION))
			.filter(c -> c.getAppType().equals(ApplicationType.BREAK_TIME_APPLICATION)).filter(c->c.getPrePostAtr().equals(PrePostAtr.POSTERIOR));
		lstDif.stream().filter(s -> !lstPost.contains(s));
		//事後申請で且申請種類が「残業申請」または「休出時間申請」の場合 (Xin sau của xin làm thêm hoặc làm ngày nghỉ)
		for (Application_New appPost : lstPost) {
			if(appPost.getAppType().equals(ApplicationType.OVER_TIME_APPLICATION)){//残業申請の場合
				//承認一覧表示設定.残業の事前申請
				if(displaySet.getOtAdvanceDisAtr().equals(DisplayAtr.DISPLAY)){//表示する
					//ドメインモデル「申請」を取得する
					// TODO Auto-generated method stub
				}
				//承認一覧表示設定.残業の実績
				if(displaySet.getOtActualDisAtr().equals(DisplayAtr.DISPLAY)){//表示する
					//アルゴリズム「申請一覧リスト取得実績残業申請」を実行する-(5.2)
					// TODO Auto-generated method stub
				}
			}else{//休出時間申請の場合
				//承認一覧表示設定.休出の事前申請
				if(displaySet.getHwAdvanceDisAtr().equals(DisplayAtr.DISPLAY)){//表示する
					//ドメインモデル「申請」を取得する
					// TODO Auto-generated method stub
				}
				//承認一覧表示設定.休出の実績
				if(displaySet.getHwActualDisAtr().equals(DisplayAtr.DISPLAY)){//表示する
					//アルゴリズム「申請一覧リスト取得実績休出申請」を実行する-(5.1))
					// TODO Auto-generated method stub
				}
			}
		}
		//アルゴリズム「申請一覧リスト取得承認件数」を実行する(countAppListApproval): 4 -   申請一覧リスト取得承認件数
		this.countAppListApproval(lstApp);
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
	 * 5.2 - 申請一覧リスト取得実績残業申請
	 */
	@Override
	public Boolean getAppListAchievementOverTime(String sID, GeneralDate date) {
		//Imported(申請承認)「勤務実績」を取得する - req #5
		RecordWorkInfoImport record = recordWkpInfoAdapter.getRecordWorkInfo(sID, date);
		
		//Imported(申請承認)「計算残業時間」を取得する - req #23
		// TODO Auto-generated method stub
		//Imported(申請承認)「計算休出時間」を取得する - req #23
		// TODO Auto-generated method stub
		//Imported(申請承認)「計算加給時間」を取得する - req #23
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 6 - 申請一覧リスト取得振休振出
	 * wait HungDD - kaf011
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
		AchievementOutput achievement = collectAchievement.getAchievement(companyID, applicantID, application.getAppDate());
		//アルゴリズム「勤務実績の取得」を実行する
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * 8 - 申請一覧リスト取得休暇
	 * wait - kaf006
	 */
	@Override
	public List<Application_New> getListAppAbsence(Application_New application) {
		String companyId = AppContexts.user().companyId();
		//申請種類 - check app type
		if(application.getAppType().equals(ApplicationType.ABSENCE_APPLICATION)){//休暇申請以外の場合
			
		}
		//休暇申請の場合
		String relationshipCd = "";
		// TODO Auto-generated method stub
		//imported(就業.Shared)「続柄」を取得する
		repoRelationship.findByCode(companyId, relationshipCd);
		return null;
	}

	/**
	 * 9 - 申請一覧リスト取得マスタ情報
	 */
	@Override
	public List<Application_New> getListAppMasterInfo(List<Application_New> lstApp) {
		//ドメインモデル「申請一覧共通設定」を取得する-(Lấy domain Application list common settings) - wait YenNTH
		Optional<AppCommonSet> appCommonSet = repoAppCommonSet.find();
		//申請一覧リスト　繰返し実行
		for (Application_New app : lstApp) {
			//ドメインモデル「申請表示名」より申請表示名称を取得する-(Lấy Application display name) - wait YenNTH
			Optional<AppDispName> appDispName = repoAppDispName.getDisplay(app.getAppType().value);
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
	/**
	 * merge App And Phase
	 * @param lstApp
	 * @return
	 */
	private List<ApplicationFullOutput> mergeAppAndPhase(List<Application_New> lstApp){
		String companyID = AppContexts.user().companyId();
		List<ApplicationFullOutput> lstAppFull = new ArrayList<>();
		for (Application_New app : lstApp) {
			List<ApprovalPhaseStateImport_New> lstPhase = approvalRootStateAdapter.getApprovalRootContent(companyID, 
					app.getEmployeeID(), app.getAppType().value, app.getAppDate(), app.getAppID(), false).getApprovalRootState().getListApprovalPhaseState();
			lstAppFull.add(new ApplicationFullOutput(app, lstPhase,-1,""));
		}
		return lstAppFull;
	}
	/**
	 * find status phase and frame
	 * @param lstPhase
	 * @param sID
	 * @return
	 */
	private PhaseFrameStatus findPhaseFrameStatus(List<ApprovalPhaseStateImport_New> lstPhase, String sID){
		PhaseFrameStatus status = new PhaseFrameStatus();
		for (ApprovalPhaseStateImport_New appPhase : lstPhase) {
			FrameOutput frame = this.checkPhaseCurrent(appPhase, sID);
			if(frame.getFrameStatus() != null){
				status.setFrameStatus(EnumAdaptor.valueOf(frame.getFrameStatus(), ApprovalBehaviorAtrImport_New.class));
				status.setPhaseStatus(appPhase.getApprovalAtr_Enum());
				status.setAgentId(frame.getAgentId());
				break;
			}
		}
		return status;
		
	}
	/**
	 * check phase current (login)
	 * @param phase
	 * @param sID
	 * @return null: not phase
	 * @return not null: phase current and status frame
	 */
	private FrameOutput checkPhaseCurrent(ApprovalPhaseStateImport_New phase, String sID){
		List<ApprovalFrameImport_New> lstFrame = phase.getListApprovalFrame();
		FrameOutput statusFrame = new FrameOutput();
		for (ApprovalFrameImport_New frame : lstFrame) {
			if(this.checkExistEmp(frame.getListApprover(), sID)){
				statusFrame.setFrameStatus(frame.getApprovalAtr_Enum().value);
				statusFrame.setAgentId(frame.getRepresenterID());
				break;
			}
		}
		return statusFrame;
	}
	/**
	 * check frame exist employee?
	 * @param listApprover
	 * @param sID
	 * @return
	 */
	private boolean checkExistEmp(List<ApproverStateImport_New> listApprover, String sID){
		boolean check = false;
		for (ApproverStateImport_New approver : listApprover) {
			if(approver.getApprover().equals(sID)){
				check = true;
				break;
			}
		}
		return check;
	}
	private int calApplication(ReflectedState_New appStatus, PhaseFrameStatus status, String agentId){
		String sIdLogin = AppContexts.user().employeeId();
		if(agentId != null & agentId.equals(sIdLogin)){//※ログイン者　　＝代行者の場合
			return this.calAppAgent(appStatus, status);
		}else{//※ログイン者　　＝承認者の場合
			return this.calAppAproval(appStatus, status);
		}
	}
	
	/**
	 * calculate status TH ログイン者　　＝承認者の場合
	 * @param appStatus
	 * @param status
	 * @return
	 */
	private int calAppAproval(ReflectedState_New appStatus, PhaseFrameStatus status){
		switch(appStatus.value){
			case 0://APP: 未反映 - NOTREFLECTED
				if(status.getPhaseStatus().equals(ApprovalBehaviorAtrImport_New.UNAPPROVED)){//Phase: 未承認
					if(status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.UNAPPROVED)){//Frame: 未承認
						return 5;
					}
					if(status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.APPROVED)){//Frame: 承認済  
						return 4;
					}
					return 0;
				}
				if(status.getPhaseStatus().equals(ApprovalBehaviorAtrImport_New.REMAND)){//差し戻し 
					if(status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.UNAPPROVED)){
						return 2;
					}
					return 0;
				}
				if(status.getPhaseStatus().equals(ApprovalBehaviorAtrImport_New.APPROVED)){//Phase: 承認済   
					if(status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.UNAPPROVED) ||
							status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.APPROVED)){//Frame: 未承認/承認済 
						return 4;
					}
					return 0;
				}
				return 0;
			case 1://APP: 反映待ち - WAITREFLECTION
				if(status.getPhaseStatus().equals(ApprovalBehaviorAtrImport_New.APPROVED)){//Phase: 承認済 
					if(status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.UNAPPROVED) ||
							status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.APPROVED)){//Frame: 未承認/承認済 
						return 4;
					}
					return 0;
				}
				return 0;
			case 2://APP: 反映済 - REFLECTED
				if(status.getPhaseStatus().equals(ApprovalBehaviorAtrImport_New.APPROVED)){//Phase: 承認済 
					if(status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.UNAPPROVED) ||
							status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.APPROVED)){//Frame: 未承認/承認済 
						return 4;
					}
					return 0;
				}
				return 0;
			case 5://APP: 差し戻し - REMAND
				return 0;
			case 6://APP: 否認 - DENIAL
				if(status.getPhaseStatus().equals(ApprovalBehaviorAtrImport_New.UNAPPROVED)){//Phase: 未承認
					if(status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.UNAPPROVED) ||
							status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.APPROVED) ||
							status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.DENIAL)){//Frame: 未承認/承認済/否認
						return 1;
					}
					return 0;
				}
				if(status.getPhaseStatus().equals(ApprovalBehaviorAtrImport_New.APPROVED)){//Phase: 承認済
					if(status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.UNAPPROVED) ||
							status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.APPROVED) ||
							status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.DENIAL)){//Frame: 未承認/承認済/否認
						return 1;
					}
					return 0;
				}
				if(status.getPhaseStatus().equals(ApprovalBehaviorAtrImport_New.DENIAL)){//Phase: 否認
					if(status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.UNAPPROVED) ||
							status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.APPROVED) ||
							status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.DENIAL)){//Frame: 未承認/承認済/否認
						return 1;
					}
					return 0;
				}
				return 0;
			default://APP: 取消待ち - WAITCANCEL/取消済  - CANCELED
				if(status.getPhaseStatus().equals(ApprovalBehaviorAtrImport_New.UNAPPROVED)){//Phase: 未承認
					if(status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.UNAPPROVED) ||
							status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.APPROVED) ||
							status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.DENIAL)){//Frame: 未承認/承認済/否認
						return 3;
					}
					return 0;
				}
				if(status.getPhaseStatus().equals(ApprovalBehaviorAtrImport_New.APPROVED)){//Phase: 承認済
					if(status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.UNAPPROVED) ||
							status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.APPROVED) ||
							status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.DENIAL)){//Frame: 未承認/承認済/否認
						return 3;
					}
					return 0;
				}
				if(status.getPhaseStatus().equals(ApprovalBehaviorAtrImport_New.DENIAL)){//Phase: 否認
					if(status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.UNAPPROVED) ||
							status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.APPROVED) ||
							status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.DENIAL)){//Frame: 未承認/承認済/否認
						return 3;
					}
					return 0;
				}
				return 0;
		}
	}
	
	/**
	 * calculate status TH ログイン者　　＝代行者の場合
	 * @param appStatus
	 * @param status
	 * @return
	 */
	private int calAppAgent(ReflectedState_New appStatus, PhaseFrameStatus status){
		switch(appStatus.value){
			case 0://APP: 未反映 - NOTREFLECTED
				//Phase: 未承認
				if(status.getPhaseStatus().equals(ApprovalBehaviorAtrImport_New.UNAPPROVED)){
					if(status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.UNAPPROVED)){//Frame: 未承認
						return 5;
					}
					if(status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.APPROVED)){//Frame: 承認済  
						return 4;
					}
					return 0;
				}
				//差し戻し 
				if(status.getPhaseStatus().equals(ApprovalBehaviorAtrImport_New.REMAND)){
					if(status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.UNAPPROVED)){
						return 2;
					}
					return 0;
				}
				//Phase: 承認済   
				if(status.getPhaseStatus().equals(ApprovalBehaviorAtrImport_New.APPROVED)){
					if(status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.APPROVED)){//Frame: 承認済 
						return 4;
					}
					return 0;
				}
				return 0;
			case 1://APP: 反映待ち - WAITREFLECTION
				//Phase: 承認済 
				if(status.getPhaseStatus().equals(ApprovalBehaviorAtrImport_New.APPROVED)){
					if(status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.APPROVED)){//Frame: 承認済 
						return 4;
					}
					return 0;
				}
				return 0;
			case 2://APP: 反映済 - REFLECTED
				//Phase: 承認済 
				if(status.getPhaseStatus().equals(ApprovalBehaviorAtrImport_New.APPROVED)){
					if(status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.APPROVED)){//Frame: 承認済 
						return 4;
					}
					return 0;
				}
				return 0;
			case 5://APP: 差し戻し - REMAND
				return 0;
			case 6://APP: 否認 - DENIAL
				//Phase: 未承認
				if(status.getPhaseStatus().equals(ApprovalBehaviorAtrImport_New.UNAPPROVED)){
					if(status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.APPROVED) ||
							status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.DENIAL)){//Frame: 承認済/否認
						return 1;
					}
					return 0;
				}
				//Phase: 承認済
				if(status.getPhaseStatus().equals(ApprovalBehaviorAtrImport_New.APPROVED)){
					if(status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.APPROVED) ||
							status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.DENIAL)){//Frame: 承認済/否認
						return 1;
					}
					return 0;
				}
				//Phase: 否認
				if(status.getPhaseStatus().equals(ApprovalBehaviorAtrImport_New.DENIAL)){
					if(status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.APPROVED) ||
							status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.DENIAL)){//Frame: 承認済/否認
						return 1;
					}
					return 0;
				}
				return 0;
			default://APP: 取消待ち - WAITCANCEL/取消済  - CANCELED
				////Phase: 未承認
				if(status.getPhaseStatus().equals(ApprovalBehaviorAtrImport_New.UNAPPROVED)){
					if(status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.APPROVED) ||
							status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.DENIAL)){//Frame: 承認済/否認
						return 3;
					}
					return 0;
				}
				//Phase: 承認済
				if(status.getPhaseStatus().equals(ApprovalBehaviorAtrImport_New.APPROVED)){
					if(status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.APPROVED) ||
							status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.DENIAL)){//Frame: 承認済/否認
						return 3;
					}
					return 0;
				}
				//Phase: 否認
				if(status.getPhaseStatus().equals(ApprovalBehaviorAtrImport_New.DENIAL)){
					if(status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.APPROVED) ||
							status.getFrameStatus().equals(ApprovalBehaviorAtrImport_New.DENIAL)){//Frame: 承認済/否認
						return 3;
					}
					return 0;
				}
				return 0;
		}
	}
}
