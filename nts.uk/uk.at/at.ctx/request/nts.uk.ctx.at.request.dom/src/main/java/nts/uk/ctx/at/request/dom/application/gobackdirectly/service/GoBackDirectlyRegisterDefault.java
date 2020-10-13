package nts.uk.ctx.at.request.dom.application.gobackdirectly.service;
import java.util.ArrayList;
/*
import java.util.Optional;
import java.util.List;
import java.util.Optional;*/
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationApprovalService;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime.DailyAttendanceTimeCaculation;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.schedule.basicschedule.ScBasicScheduleAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.schedule.basicschedule.ScBasicScheduleImport_Old;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.RegisterAtApproveReflectionInfoService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.after.NewAfterRegister;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.NewBeforeRegister;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.CollectAchievement;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementDetail;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ActualContentDisplay;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.common.service.setting.CommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository_Old;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly_Old;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.InforGoBackCommonDirectOutput;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.primitive.WorkTimeGoBack;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppTypeSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.GoBackDirectlyCommonSetting;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.GoBackDirectlyCommonSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.CheckAtr;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.WorkChangeFlg;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainDataMngRegisterDateChange;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.directgoback.ApplicationStatus;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.directgoback.GoBackReflect;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingService;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 直行直帰登録
 * 
 * @author ducpm
 */
@Stateless
public class GoBackDirectlyRegisterDefault implements GoBackDirectlyRegisterService {
	@Inject
	RegisterAtApproveReflectionInfoService registerAppReplection;
	@Inject
	GoBackDirectlyRepository_Old goBackDirectRepo;
	@Inject
	ApplicationApprovalService appRepo;
	@Inject
	NewBeforeRegister processBeforeRegister;
	@Inject
	GoBackDirectlyCommonSettingRepository goBackDirectCommonSetRepo;
	@Inject 
	NewAfterRegister newAfterRegister;
	@Inject
	ApplicationSettingRepository applicationSettingRepository;
	@Inject
	private InterimRemainDataMngRegisterDateChange interimRemainDataMngRegisterDateChange;
	@Inject
	DailyAttendanceTimeCaculation dailyAttendanceTimeCaculation;
	@Inject
	ScBasicScheduleAdapter scBasicScheduleAdapter;
	
	@Inject
	private WorkTimeSettingService workTimeSettingService;
	
	@Inject
	private CollectAchievement collectAchievement;
	
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;
	
	@Inject
	private GoBackDirectlyUpdateService goBackDirectlyUpdateService;
	
	@Inject
	private DetailBeforeUpdate detailBeforeUpdate;
	
	@Inject
	private GoBackDirectlyRepository goBackDirectlyRepository;
	
	@Inject
	private RegisterAtApproveReflectionInfoService registerAtApprove;
	
	@Inject
	private DetailAfterUpdate detailAfterUpdate;
	
	@Inject
	ApplicationApprovalService appRepository;
	
	@Inject
	ApplicationRepository appRe;
	
	@Inject 
	CommonAlgorithm commonAlgorith;
	/**
	 * 
	 */
	// insert ref
	@Override
	public ProcessResult register(GoBackDirectly_Old goBackDirectly, Application application) {
//		String employeeID = application.getEmployeeID();
//		String workTimeCD = "";
//		String workTypeCD = "";
//		// ドメインモデル「直行直帰申請共通設定」．勤務の変更をチェックする
//		GoBackDirectlyCommonSetting setting = goBackDirectCommonSetRepo.findByCompanyID(application.getCompanyID()).get();
//		if(setting.getWorkChangeFlg()==WorkChangeFlg.DECIDECHANGE){
//			workTypeCD = goBackDirectly.getWorkTypeCD().map(x -> x.v()).orElse("");
//			workTimeCD = goBackDirectly.getSiftCD().map(x -> x.v()).orElse("");
//		} else {
//			// 実績の取得
//			/*AchievementOutput achievementOutput = collectAchievement.getAchievement(application.getCompanyID(), application.getEmployeeID(), application.getAppDate());*/
//			AchievementOutput achievementOutput = null;
//			workTimeCD = achievementOutput.getWorkTime().getWorkTimeCD();
//			workTypeCD = achievementOutput.getWorkType().getWorkTypeCode();
//		}
//		// 取得した「勤務種類コード」「就業時間帯コード」をチェックする
//		Optional<TimezoneUse> opTimezoneUse = Optional.empty();
//		if(Strings.isNotBlank(workTypeCD) && Strings.isNotBlank(workTimeCD)){
//			// 所定時間帯を取得する
//			PredetermineTimeSetForCalc predetermineTimeSetForCalc = workTimeSettingService.getPredeterminedTimezone(application.getCompanyID(), workTimeCD, workTypeCD, null); 
//			opTimezoneUse = predetermineTimeSetForCalc.getTimezones().stream().filter(x -> x.getWorkNo() == 1).findAny();
//		}
//		// 勤務開始1に時刻が入力されたか
//		if(!goBackDirectly.getWorkTimeStart1().isPresent()){
//			// 取得した「勤務種類コード」「就業時間帯コード」をチェックする
//			if(Strings.isNotBlank(workTypeCD) && Strings.isNotBlank(workTimeCD)){
//				// 勤務開始1に(勤務NO=1)の「計算用所定時間設定」．時間帯．開始を入れる
//				goBackDirectly.setWorkTimeStart1(opTimezoneUse.map(x -> new WorkTimeGoBack(x.getStart().v())));
//			}
//		}
//		// 勤務終了1に時刻が入力されたか
//		if(!goBackDirectly.getWorkTimeEnd1().isPresent()){
//			// 取得した「勤務種類コード」「就業時間帯コード」をチェックする
//			if(Strings.isNotBlank(workTypeCD) && Strings.isNotBlank(workTimeCD)){
//				// 勤務終了1に(勤務NO=1)の「計算用所定時間設定」．時間帯．終了を入れる
//				goBackDirectly.setWorkTimeEnd1(opTimezoneUse.map(x -> new WorkTimeGoBack(x.getEnd().v())));
//			}
//		}
//		//アルゴリズム「直行直帰登録」を実行する		
//		goBackDirectRepo.insert(goBackDirectly);
//		// error EA refactor 4
//		/*appRepo.insert(application);*/
//		// 2-2.新規画面登録時承認反映情報の整理
//		// error EA refactor 4
//		/*registerAppReplection.newScreenRegisterAtApproveInfoReflect(employeeID, application);*/
//		
//		// 暫定データの登録
//		interimRemainDataMngRegisterDateChange.registerDateChange(
//				application.getCompanyID(), 
//				employeeID, 
//				Arrays.asList(application.getAppDate()));
//		
//		//アルゴリズム「2-3.新規画面登録後の処理」を実行する 
//		/*return newAfterRegister.processAfterRegister(application);*/
		return null;
	}
	/**Refactor 4
	 * 	直行直帰登録前チェック
	 * @param companyId
	 * @param agenAtr
	 * @param at
	 * @param goBackDirectly
	 * @param inforGoBackCommonDirectOutput
	 * @return 確認メッセージリスト
	 */
	public List<ConfirmMsgOutput> getBeforeRegisterMessageList(String companyId, boolean agenAtr,
			Application application, GoBackDirectly goBackDirectly,
			InforGoBackCommonDirectOutput inforGoBackCommonDirectOutput) {
		// String employeeID = AppContexts.user().employeeId();
		// this.inconsistencyCheck(companyId, employeeID, GeneralDate.today());
		EmployeeInfoImport employeeInfo = inforGoBackCommonDirectOutput.getAppDispInfoStartup().getAppDispInfoNoDateOutput().getEmployeeInfoLst().get(0);
		GeneralDate appDate = application.getAppDate().getApplicationDate();
		List<GeneralDate> dateLst = new ArrayList<GeneralDate>();
		dateLst.add(appDate);
		List<String> workTypeLst = new ArrayList<String>();
		if (goBackDirectly != null) {
			Optional<WorkInformation> dataWork = goBackDirectly.getDataWork();
			if (dataWork.isPresent()) {
				if (dataWork.get().getWorkTypeCode() != null) {
					if (StringUtils.isNotBlank(dataWork.get().getWorkTypeCode().v())) {
						workTypeLst.add(dataWork.get().getWorkTypeCode().v());
					}
				}
			}
		}
		List<ActualContentDisplay> actualContentDisplayLst = inforGoBackCommonDirectOutput.getAppDispInfoStartup().getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().orElse(Collections.emptyList());
		// 申請の矛盾チェック
		commonAlgorith.appConflictCheck(companyId, employeeInfo, dateLst, workTypeLst, actualContentDisplayLst);
		// アルゴリズム「2-1.新規画面登録前の処理」を実行する 
		List<ConfirmMsgOutput> listResult = processBeforeRegister.processBeforeRegister_New(companyId,
				EmploymentRootAtr.APPLICATION, agenAtr, application, null, 
				inforGoBackCommonDirectOutput
				.getAppDispInfoStartup().getAppDispInfoWithDateOutput().getOpErrorFlag().isPresent() ? inforGoBackCommonDirectOutput
						.getAppDispInfoStartup().getAppDispInfoWithDateOutput().getOpErrorFlag().get() : null,
				Collections.emptyList(),
				inforGoBackCommonDirectOutput
				.getAppDispInfoStartup());
		return listResult;
	}
	/**Refactor4
	 * 直行直帰更新前チェック
	 * UKDesign.UniversalK.就業.KAF_申請.KAF009_直行直帰の申請.B:直行直帰の申請（更新）.アルゴリズム.直行直帰更新前チェック
	 * * @param companyId
	 * @param agenAtr
	 * @param at
	 * @param goBackDirectly
	 * @param inforGoBackCommonDirectOutput
	 * @return 確認メッセージリスト
	 */
	public void getBeforeUpdateRegisterMessageList(String companyId, boolean agenAtr,
			Application application, GoBackDirectly goBackDirectly,
			InforGoBackCommonDirectOutput inforGoBackCommonDirectOutput) {
		
			EmployeeInfoImport employeeInfo = inforGoBackCommonDirectOutput.getAppDispInfoStartup().getAppDispInfoNoDateOutput().getEmployeeInfoLst().get(0);
			GeneralDate appDate = application.getAppDate().getApplicationDate();
			List<GeneralDate> dateLst = new ArrayList<GeneralDate>();
			dateLst.add(appDate);
			List<String> workTypeLst = new ArrayList<String>();
			if (goBackDirectly != null) {
				Optional<WorkInformation> dataWork = goBackDirectly.getDataWork();
				if (dataWork.isPresent()) {
					if (dataWork.get().getWorkTypeCode() != null) {
						if (StringUtils.isNotBlank(dataWork.get().getWorkTypeCode().v())) {
							workTypeLst.add(dataWork.get().getWorkTypeCode().v());
						}
					}
				}
			}
			List<ActualContentDisplay> actualContentDisplayLst = inforGoBackCommonDirectOutput.getAppDispInfoStartup().getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().orElse(Collections.emptyList());
			// 申請の矛盾チェック
			commonAlgorith.appConflictCheck(companyId, employeeInfo, dateLst, workTypeLst, actualContentDisplayLst);
			
			// アルゴリズム「4-1.詳細画面登録前の処理」を実行する
			detailBeforeUpdate.processBeforeDetailScreenRegistration(
				companyId,
				application.getEmployeeID(),
				application.getAppDate().getApplicationDate(),
				EmploymentRootAtr.APPLICATION.value,
				application.getAppID(),
				application.getPrePostAtr(),
				application.getVersion(),
				goBackDirectly.getDataWork().isPresent() ? goBackDirectly.getDataWork().get().getWorkTypeCode().v() : null,
				goBackDirectly.getDataWork().isPresent() ? (goBackDirectly.getDataWork().get().getWorkTimeCode() != null ? goBackDirectly.getDataWork().get().getWorkTimeCode().v() : null) : null,
				inforGoBackCommonDirectOutput.getAppDispInfoStartup());
	}
	/**
	 * 共通登録前のエラーチェック処理
	 * @param companyId 会社ID
	 * @param agentAtr 代行申請区分
	 * @param application 申請
	 * @param goBackDirectly 直行直帰申請
	 * @param inforGoBackCommonDirectOutput 直行直帰申請起動時の表示情報
	 * @param mode モード＝　新規
	 * @return 確認メッセージリスト
	 */
	public List<ConfirmMsgOutput> checkBeforRegisterNew(String companyId, boolean agentAtr, Application application,
			GoBackDirectly goBackDirectly, InforGoBackCommonDirectOutput inforGoBackCommonDirectOutput, boolean mode) {
		// #110892
		if (goBackDirectly.getStraightDistinction() == NotUseAtr.NOT_USE && goBackDirectly.getStraightLine() == NotUseAtr.NOT_USE) {
			throw new BusinessException("Msg_1808");
		}
		
		// 確認メッセージリスト＝Empty
		List<ConfirmMsgOutput> lstConfirm = new ArrayList<ConfirmMsgOutput>();
		// mode new
		// モードチェックする
		if (mode) {
			// 直行直帰登録前チェック
			lstConfirm = this.getBeforeRegisterMessageList(companyId, agentAtr, application, goBackDirectly,
					inforGoBackCommonDirectOutput);

		} else {
			// 直行直帰更新前チェック
			this.getBeforeUpdateRegisterMessageList(companyId, agentAtr, application, goBackDirectly, inforGoBackCommonDirectOutput);
		}
//		確認メッセージリスト＝取得した確認メッセージリスト 
		GoBackDirectAtr check = goBackDirectCheckNew(goBackDirectly);
		
//		if (check == GoBackDirectAtr.NOT) {
////			確認メッセージリストに（Msg_338）を追加する
//			lstConfirm.add(new ConfirmMsgOutput("Msg_338", Collections.emptyList()));
//		}
		return lstConfirm;
	}
	
	@Override
	public List<ConfirmMsgOutput> checkBeforRegister(GoBackDirectly_Old goBackDirectly, Application application, boolean checkOver1Year) {
		String companyID = AppContexts.user().companyId();
//		確認メッセージリスト＝Empty
		List<ConfirmMsgOutput> lstConfirm = new ArrayList<ConfirmMsgOutput>();

		
//		モードチェックする(check mode)
		if(checkOver1Year) {
//			直行直帰更新前チェック
			// return list message
//			goBackDirectlyUpdateService.checkErrorBeforeUpdate(goBackDirectly, companyID, application.getAppID(), null);
		}else {
//			直行直帰登録前チェック
			//アルゴリズム「2-1.新規画面登録前の処理」を実行する
//			processBeforeRegister.processBeforeRegister_New(companyID, employmentRootAtr, agentAtr, application, overTimeAtr, errorFlg, lstDateHd);
			
		}
		
//		モードチェックする(check mode)
		
		// mode new 
		if(checkOver1Year) {
//			直行直帰登録前チェック
			
//			アルゴリズム「直行直帰申請日の矛盾チェック」を実行する
			
			
//			アルゴリズム「2-1.新規画面登録前の処理」を実行する
//			lstConfirm = processBeforeRegister.processBeforeRegister_New(companyID, EmploymentRootAtr.APPLICATION, checkOver1Year, application, null, , Collections.emptyList());
			
			
		}else {
//			mode change
			
			
		}
		

		GoBackDirectlyCommonSetting goBackCommonSet = goBackDirectCommonSetRepo.findByCompanyID(companyID).get();
		//アルゴリズム「2-1.新規画面登録前の処理」を実行する
		// processBeforeRegister.processBeforeRegister(application, OverTimeAtr.ALL, checkOver1Year, Collections.emptyList());
		// アルゴリズム「直行直帰するチェック」を実行する - client da duoc check
		
		// アルゴリズム「直行直帰遅刻早退のチェック」を実行する
		GoBackDirectLateEarlyOuput goBackLateEarly = this.goBackDirectLateEarlyCheck(goBackDirectly, application);
		//直行直帰遅刻早退のチェック
		//TODO: chua the thuc hien duoc nen mac dinh luc nao cung co loi エラーあり
		if(goBackLateEarly.isError) {
			//直行直帰申請共通設定.早退遅刻設定がチェックする
			if(goBackCommonSet.getLateLeaveEarlySettingAtr() == CheckAtr.CHECKREGISTER) {
				this.createThrowMsg("Msg_297", goBackLateEarly.msgLst);
			}else if(goBackCommonSet.getLateLeaveEarlySettingAtr() == CheckAtr.CHECKNOTREGISTER) {
				this.createThrowMsg("Msg_298", goBackLateEarly.msgLst);
			}
		}
		return lstConfirm;
	}
	
	public void createThrowMsg(String msgConfirm, List<String> msgLst){
		if(msgLst.size() >= 4){
			throw new BusinessException(msgConfirm, msgLst.get(0), msgLst.get(1), msgLst.get(2), msgLst.get(3));
		} else if (msgLst.size() >= 2){
			throw new BusinessException(msgConfirm, msgLst.get(0), msgLst.get(1));
		} else {
			throw new BusinessException(msgConfirm);
		}
	}
	
	/**
	 *  アルゴリズム「直行直帰するチェック」を実行する
	 */
	@Override
	public GoBackDirectAtr goBackDirectCheck(GoBackDirectly_Old goBackDirectly) {
		if (goBackDirectly.getGoWorkAtr1() == UseAtr.NOTUSE && goBackDirectly.getBackHomeAtr1() == UseAtr.NOTUSE
				&& goBackDirectly.getGoWorkAtr2().get() == UseAtr.NOTUSE
				&& goBackDirectly.getBackHomeAtr2().get() == UseAtr.NOTUSE) {
			return GoBackDirectAtr.NOT;
		} else {
			return GoBackDirectAtr.IS;
		}
	}

	/**
	 * アルゴリズム「直行直帰遅刻早退のチェック」を実行する
	 */
	@Override
	public GoBackDirectLateEarlyOuput goBackDirectLateEarlyCheck(GoBackDirectly_Old goBackDirectly, Application application) {
		
		String companyID = AppContexts.user().companyId();
		// ドメインモデル「直行直帰申請共通設定」を取得する
		GoBackDirectLateEarlyOuput output = new GoBackDirectLateEarlyOuput();
		output.isError = false;
		//ドメインモデル「直行直帰申請共通設定」を取得する 
		GoBackDirectlyCommonSetting goBackCommonSet = goBackDirectCommonSetRepo.findByCompanyID(companyID).get();
		// 設定：直行直帰申請共通設定.早退遅刻設定		
		if (goBackCommonSet.getLateLeaveEarlySettingAtr() != CheckAtr.NOTCHECK) {//チェックする
//			ScBasicScheduleImport scBasicScheduleImport = scBasicScheduleAdapter.findByID(application.getEmployeeID(), application.getAppDate()).orElse(null);
//			// check Valid 1
//			CheckValidOutput validOut1 = this.goBackLateEarlyCheckValidity(goBackDirectly, goBackCommonSet, 1, scBasicScheduleImport);
//			// check Valid 2
////			CheckValidOutput validOut2 = this.goBackLateEarlyCheckValidity(goBackDirectly, goBackCommonSet, 2);
//			// チェック対象１またはチェック対象２がTrueの場合
//			if (validOut1.isCheckValid) {
//				// アルゴリズム「1日分の勤怠時間を仮計算」を実行する
//				//Mac Dinh tra ve 0
//				
//				//日別実績の勤怠時間.実働時間.総労働時間.早退時間.時間 
//				//TODO: chua the lam duoc do chua co 日別実績
//				
//				DailyAttenTimeParam dailyAttenTimeParam = new DailyAttenTimeParam(
//						application.getEmployeeID(), 
//						application.getAppDate(), 
//						validOut1.workTypeCD, 
//						validOut1.siftCd, 
//						validOut1.workTimeStart == null ? null : new AttendanceTime(validOut1.workTimeStart.v()), 
//						validOut1.workTimeEnd == null ? null : new AttendanceTime(validOut1.workTimeEnd.v()), 
//						goBackDirectly.getWorkTimeStart2().map(x -> new AttendanceTime(x.v())).orElse(null), 
//						goBackDirectly.getWorkTimeEnd2().map(x -> new AttendanceTime(x.v())).orElse(null));
//				DailyAttenTimeLateLeaveImport dailyAttenTimeLateLeaveImport = dailyAttendanceTimeCaculation.calcDailyLateLeave(dailyAttenTimeParam);
//				
//				// So sách tới 日別実績 để biết đi sớm về muộn, nếu  
//				
//				if (dailyAttenTimeLateLeaveImport.getLeaveEarlyTime().v() > 0) {
//					output.isError = true;
//					output.msgLst.add("s=Msg_296");
//					output.msgLst.add(goBackDirectly.getWorkTimeEnd1().map(x -> x.v().toString()).orElse(""));
//				} 
//				if (dailyAttenTimeLateLeaveImport.getLateTime().v() > 0) {
//					output.isError = true;
//					output.msgLst.add("s=Msg_295");
//					output.msgLst.add(goBackDirectly.getWorkTimeStart1().map(x -> x.v().toString()).orElse(""));
//				}
//			}
		}
		return output;
	}

	/**
	 * アルゴリズム「直行直帰遅刻早退有効チェック」を実行する
	 */
	@Override
	public CheckValidOutput goBackLateEarlyCheckValidity(GoBackDirectly_Old goBackDirectly,
			GoBackDirectlyCommonSetting goBackCommonSet, int line, ScBasicScheduleImport_Old scBasicScheduleImport) {
		WorkTypeCode bsWorkTypeCD = scBasicScheduleImport == null ? new WorkTypeCode("") : new WorkTypeCode(scBasicScheduleImport.getWorkTypeCode());
		WorkTimeCode bsSiftCd = scBasicScheduleImport == null ? new WorkTimeCode("") : new WorkTimeCode(scBasicScheduleImport.getWorkTimeCode());
		/*WorkTimeGoBack bsWorkTimeStart1 = scBasicScheduleImport == null ? null : new WorkTimeGoBack(scBasicScheduleImport.getScheduleStartClock1());
		WorkTimeGoBack bsWorkTimeEnd1 = scBasicScheduleImport == null ? null : new WorkTimeGoBack(scBasicScheduleImport.getScheduleEndClock1());*/
		WorkTimeGoBack bsWorkTimeStart1 = null;
		WorkTimeGoBack bsWorkTimeEnd1 = null;
		CheckValidOutput result = new CheckValidOutput();
		result.isCheckValid = false;
		// 直行直帰申請共通設定.勤務の変更 (Thay đổi 直行直帰申請共通設定.勤務)
		WorkChangeFlg workChangeFlg = goBackCommonSet.getWorkChangeFlg();
		if(workChangeFlg == WorkChangeFlg.NOTCHANGE){
			result.setWorkTypeCD(bsWorkTypeCD);
			result.setSiftCd(bsSiftCd);
		} else if(workChangeFlg == WorkChangeFlg.CHANGE){
			result.setWorkTypeCD(goBackDirectly.getWorkTypeCD().orElse(bsWorkTypeCD));
			result.setSiftCd(goBackDirectly.getSiftCD().orElse(bsSiftCd));
		} else {
			// 勤務を変更するのチェック状態 (Kiểm tra tình trạng thay đổi worktime)
			if(workChangeFlg == WorkChangeFlg.DECIDECHANGE){
				result.setWorkTypeCD(goBackDirectly.getWorkTypeCD().orElse(bsWorkTypeCD));
				result.setSiftCd(goBackDirectly.getSiftCD().orElse(bsSiftCd));
			} else {
				result.setWorkTypeCD(bsWorkTypeCD);
				result.setSiftCd(bsSiftCd);
			}
		}
		if (line == 1) {
			// MERGE NODE 1
			// 勤務直行の確認
			if (goBackDirectly.getGoWorkAtr1() == UseAtr.USE && goBackDirectly.getWorkTimeStart1() != null) {
				// 入力する
				result.setWorkTimeStart(goBackDirectly.getWorkTimeStart1().orElse(bsWorkTimeStart1));
				result.setCheckValid(true);
			} else {
				result.setWorkTimeStart(bsWorkTimeStart1);
			}
			// 勤務直帰の確認
			if (goBackDirectly.getBackHomeAtr1() == UseAtr.USE && goBackDirectly.getWorkTimeEnd1() != null) {
				result.setWorkTimeEnd(goBackDirectly.getWorkTimeEnd1().orElse(bsWorkTimeEnd1));
				result.setCheckValid(true);
			} else {
				result.setWorkTimeEnd(bsWorkTimeEnd1);
			}
		} else {
			// MERGE NODE 1
			// 勤務直行の確認
			if (goBackDirectly.getGoWorkAtr2().get() == UseAtr.USE && goBackDirectly.getWorkTimeStart2() != null) {
				// 入力する
				result.setCheckValid(true);
			} else {
				result.setWorkTimeStart(null);
			}
			// 勤務直帰の確認
			if (goBackDirectly.getBackHomeAtr2().get() == UseAtr.USE && goBackDirectly.getWorkTimeEnd2() != null) {
				result.setCheckValid(true);
			} else {
				result.setWorkTimeEnd(null);
			}
		}
		return result;
	}


	@Override
	public List<String> inconsistencyCheck(String companyID, String employeeID, GeneralDate appDate) {
		// ドメインモデル「直行直帰申請共通設定」を取得
		Optional<GoBackDirectlyCommonSetting> opGoBackDirectlyCommonSet = this.goBackDirectCommonSetRepo
				.findByCompanyID(companyID);
		if(!opGoBackDirectlyCommonSet.isPresent()){
			return Collections.emptyList();
		}
		GoBackDirectlyCommonSetting goBackDirectlyCommonSet = opGoBackDirectlyCommonSet.get();
		CheckAtr appDateContradictionAtr = goBackDirectlyCommonSet.getContraditionCheckAtr();
		if(appDateContradictionAtr==CheckAtr.NOTCHECK){
			return Collections.emptyList();
		}
		// アルゴリズム「11.指定日の勤務実績（予定）の勤務種類を取得」を実行する
		WorkType workType = otherCommonAlgorithm.getWorkTypeScheduleSpec(companyID, employeeID, appDate);
		if(workType==null){
			// 「申請日矛盾区分」をチェックする
			if(appDateContradictionAtr==CheckAtr.CHECKNOTREGISTER){
				throw new BusinessException("Msg_1519", appDate.toString("yyyy/MM/dd"));
			}
			return Arrays.asList("Msg_1520", appDate.toString("yyyy/MM/dd")); 
		}
		// アルゴリズム「01_直行直帰_勤務種類の分類チェック」を実行する
		boolean checked = this.workTypeInconsistencyCheck(workType);
		if(!checked){
			return Collections.emptyList();
		}
		String name = workType.getName().v();
		// 「申請日矛盾区分」をチェックする
		if(appDateContradictionAtr==CheckAtr.CHECKNOTREGISTER){
			throw new BusinessException("Msg_1521", appDate.toString("yyyy/MM/dd"), Strings.isNotBlank(name) ? name : "未登録のマスタ");
		}
		return Arrays.asList("Msg_1522", appDate.toString("yyyy/MM/dd"), Strings.isNotBlank(name) ? name : "未登録のマスタ"); 
	}
	
	/**
	 * 01_直行直帰_勤務種類の分類チェック
	 * @param workType
	 * @return
	 */
	private boolean workTypeInconsistencyCheck(WorkType workType){
		// INPUT.ドメインモデル「勤務種類.勤務の単位(WORK_ATR)」をチェックする
		if(workType.getDailyWork().getWorkTypeUnit()==WorkTypeUnit.OneDay){
			// INPUT.ドメインモデル「勤務種類.1日勤務分類(ONE_DAY_CLS)」をチェックする
			WorkTypeClassification workTypeClassification = workType.getDailyWork().getOneDay();
			if(workTypeClassification==WorkTypeClassification.Attendance||
				workTypeClassification==WorkTypeClassification.Shooting||
				workTypeClassification==WorkTypeClassification.HolidayWork){
				return false;
			}
			return true;
		}
		// INPUT.ドメインモデル「勤務種類.午前の勤務分類(MORNING_CLS)」をチェックする
		WorkTypeClassification workTypeClassMorning = workType.getDailyWork().getMorning();
		if(workTypeClassMorning!=WorkTypeClassification.Holiday&&
			workTypeClassMorning!=WorkTypeClassification.AnnualHoliday&&
			workTypeClassMorning!=WorkTypeClassification.YearlyReserved&&
			workTypeClassMorning!=WorkTypeClassification.SpecialHoliday&&
			workTypeClassMorning!=WorkTypeClassification.Absence&&
			workTypeClassMorning!=WorkTypeClassification.SubstituteHoliday&&
			workTypeClassMorning!=WorkTypeClassification.Pause&&
			workTypeClassMorning!=WorkTypeClassification.TimeDigestVacation){
			return false;
		}
		// INPUT.ドメインモデル「勤務種類.午後の勤務分類(AFTERNOON_CLS)」をチェックする
		WorkTypeClassification workTypeClassAfternoon = workType.getDailyWork().getAfternoon();
		if(workTypeClassAfternoon!=WorkTypeClassification.Holiday&&
			workTypeClassAfternoon!=WorkTypeClassification.AnnualHoliday&&
			workTypeClassAfternoon!=WorkTypeClassification.YearlyReserved&&
			workTypeClassAfternoon!=WorkTypeClassification.SpecialHoliday&&
			workTypeClassAfternoon!=WorkTypeClassification.Absence&&
			workTypeClassAfternoon!=WorkTypeClassification.SubstituteHoliday&&
			workTypeClassAfternoon!=WorkTypeClassification.Pause&&
			workTypeClassAfternoon!=WorkTypeClassification.TimeDigestVacation){
			return false;
		}
		return true;
	}
	@Override
	public ProcessResult registerNew(String companyId, Application application_New, GoBackDirectly_Old goBackDirectly,
			InforGoBackCommonDirectOutput_Old inforGoBackCommonDirectOutput) {
//		INPUT.「直行直帰申請起動時の表示情報.直行直帰申請共通設定」．勤務の変更をチェックする
		WorkChangeFlg changeFlg = inforGoBackCommonDirectOutput.getGobackDirectCommon().getWorkChangeFlg();
		
		return null;
	}
	@Override
	public GoBackDirectAtr goBackDirectCheckNew(GoBackDirectly goBackDirectly) {
		if (goBackDirectly.getStraightDistinction().value == GoBackDirectAtr.IS.value && goBackDirectly.getStraightLine().value == GoBackDirectAtr.IS.value) {
			return GoBackDirectAtr.IS;
		}
		return GoBackDirectAtr.NOT;
	}
	@Override
	public ProcessResult register(GoBackDirectly goBackDirectly, Application application, InforGoBackCommonDirectOutput inforGoBackCommonDirectOutput) {
		

		GoBackReflect goBackReflect = inforGoBackCommonDirectOutput.getGoBackReflect();
		ApplicationStatus status = goBackReflect.getReflectApplication();
//		INPUT.「直行直帰申請起動時の表示情報.直行直帰申請の反映」．勤務情報を反映するをチェックする
		if (status == ApplicationStatus.DO_REFLECT || status == ApplicationStatus.DO_REFLECT_1) {
//			反映する
			AppDispInfoStartupOutput appDispInfoStartup = inforGoBackCommonDirectOutput.getAppDispInfoStartup();
			Optional<List<ActualContentDisplay>> opActualContentDisplayLst = appDispInfoStartup.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst();
			if (opActualContentDisplayLst.isPresent()) {
				List<ActualContentDisplay> listActualContentDisplay = opActualContentDisplayLst.get();
				if (!CollectionUtil.isEmpty(listActualContentDisplay)) {
					ActualContentDisplay actualContentDisplay = listActualContentDisplay.get(0);
					Optional<AchievementDetail> opAchievementDetail = actualContentDisplay.getOpAchievementDetail();
					if (opAchievementDetail.isPresent()) {
						AchievementDetail achievementDetail = opAchievementDetail.get();
						if (StringUtils.isNotBlank(achievementDetail.getWorkTypeCD())) {
							WorkTimeCode workTimeCode = null;
							if (StringUtils.isNotBlank(achievementDetail.getWorkTimeCD())) {
								workTimeCode = new WorkTimeCode(achievementDetail.getWorkTimeCD());
							}
							WorkInformation workInformation = new WorkInformation(new WorkTypeCode(achievementDetail.getWorkTypeCD()), workTimeCode);
							Optional<WorkInformation> dataWork = Optional.of(workInformation);
							goBackDirectly.setDataWork(dataWork);
						}
					}
				}
			}
		} 


		appRepository.insertApp(
				application,
				inforGoBackCommonDirectOutput.getAppDispInfoStartup().getAppDispInfoWithDateOutput().getOpListApprovalPhaseState().isPresent() ? inforGoBackCommonDirectOutput.getAppDispInfoStartup().getAppDispInfoWithDateOutput().getOpListApprovalPhaseState().get() : null);
		

		// ドメインモデル「直行直帰申請」の新規登録する
		goBackDirectlyRepository.add(goBackDirectly);
		// 2-2.新規画面登録時承認反映情報の整理
		registerAtApprove.newScreenRegisterAtApproveInfoReflect(application.getEmployeeID(), application);
		List<GeneralDate> listDates = new ArrayList<>();
		listDates.add(application.getAppDate().getApplicationDate());
		// 暫定データの登録
//		interimRemainDataMngRegisterDateChange.registerDateChange(AppContexts.user().companyId(),
//				application.getEmployeeID(), listDates);

		
		if (inforGoBackCommonDirectOutput.getAppDispInfoStartup().getAppDispInfoNoDateOutput().isMailServerSet()) {
			// アルゴリズム「2-3.新規画面登録後の処理」を実行する
			// TODO: 申請設定 domain has changed!
			AppTypeSetting appTypeSetting = inforGoBackCommonDirectOutput.getAppDispInfoStartup().getAppDispInfoNoDateOutput().getApplicationSetting().getAppTypeSettings()
					.stream().filter(x -> x.getAppType()==application.getAppType()).findAny().get();
			return newAfterRegister.processAfterRegister(
					application.getAppID(), 
					appTypeSetting,
					inforGoBackCommonDirectOutput.getAppDispInfoStartup().getAppDispInfoNoDateOutput().isMailServerSet());
		}	
		ProcessResult processResult = new ProcessResult();
		processResult.setProcessDone(true);
		processResult.setAppID(application.getAppID());
		return processResult;

	}
	@Override
	public ProcessResult update(String companyId, Application application, GoBackDirectly goBackDirectly,
			InforGoBackCommonDirectOutput inforGoBackCommonDirectOutput) {
		// INPUT.「直行直帰申請起動時の表示情報.直行直帰申請の反映」．勤務情報を反映するをチェックする
		GoBackReflect goBackReflect = inforGoBackCommonDirectOutput.getGoBackReflect();
		ApplicationStatus status = goBackReflect.getReflectApplication();
//		INPUT.「直行直帰申請起動時の表示情報.直行直帰申請の反映」．勤務情報を反映するをチェックする
		if (status == ApplicationStatus.DO_REFLECT || status == ApplicationStatus.DO_REFLECT_1) {
//			反映する
			AppDispInfoStartupOutput appDispInfoStartup = inforGoBackCommonDirectOutput.getAppDispInfoStartup();
			Optional<List<ActualContentDisplay>> opActualContentDisplayLst = appDispInfoStartup.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst();
			if (opActualContentDisplayLst.isPresent()) {
				List<ActualContentDisplay> listActualContentDisplay = opActualContentDisplayLst.get();
				if (!CollectionUtil.isEmpty(listActualContentDisplay)) {
					ActualContentDisplay actualContentDisplay = listActualContentDisplay.get(0);
					Optional<AchievementDetail> opAchievementDetail = actualContentDisplay.getOpAchievementDetail();
					if (opAchievementDetail.isPresent()) {
						AchievementDetail achievementDetail = opAchievementDetail.get();
						if (StringUtils.isNotBlank(achievementDetail.getWorkTypeCD())) {
							WorkTimeCode workTimeCode = null;
							if (StringUtils.isNotBlank(achievementDetail.getWorkTimeCD())) {
								workTimeCode = new WorkTimeCode(achievementDetail.getWorkTimeCD());
							}
							WorkInformation workInformation = new WorkInformation(new WorkTypeCode(achievementDetail.getWorkTypeCD()), workTimeCode);
							Optional<WorkInformation> dataWork = Optional.of(workInformation);
							goBackDirectly.setDataWork(dataWork);
						}
					}
				}
			}
		} 
		appRe.update(application);
		// ドメインモデル「直行直帰申請」の更新する
		// params is appId or application
		goBackDirectlyRepository.update(goBackDirectly);
		List<GeneralDate> listDates = new ArrayList<>();
		listDates.add(application.getAppDate().getApplicationDate());
		// 暫定データの登録
		// reflect application is not done
//		interimRemainDataMngRegisterDateChange.registerDateChange(AppContexts.user().companyId(),
//				application.getEmployeeID(), listDates);
//		アルゴリズム「4-2.詳細画面登録後の処理」を実行する
		return detailAfterUpdate.processAfterDetailScreenRegistration(companyId, application.getAppID(), inforGoBackCommonDirectOutput.getAppDispInfoStartup());
//		return null;

	}
	
}
