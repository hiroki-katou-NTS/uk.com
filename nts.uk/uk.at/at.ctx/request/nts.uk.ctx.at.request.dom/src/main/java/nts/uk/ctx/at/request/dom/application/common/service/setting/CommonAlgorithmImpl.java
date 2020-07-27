package nts.uk.ctx.at.request.dom.application.common.service.setting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.AtEmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.SEmpHistImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.sys.EnvAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.sys.dto.MailServerSetImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ErrorFlagImport;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.CollectApprovalRootPatternService;
import nts.uk.ctx.at.request.dom.application.common.service.other.CollectAchievement;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.PreAppContentDisplay;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementDetail;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ActualContentDisplay;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoNoDateOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoRelatedDateOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoWithDateOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.ApplyWorkTypeOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.InitWkTypeWkTimeOutput;
import nts.uk.ctx.at.request.dom.application.common.service.smartphone.CommonAlgorithmMobile;
import nts.uk.ctx.at.request.dom.application.common.service.smartphone.output.AppReasonOutput;
import nts.uk.ctx.at.request.dom.application.common.service.smartphone.output.DeadlineLimitCurrentMonth;
import nts.uk.ctx.at.request.dom.application.holidayshipment.HolidayShipmentService;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;
import nts.uk.ctx.at.request.dom.application.overtime.service.CheckWorkingInfoResult;
import nts.uk.ctx.at.request.dom.setting.DisplayAtr;
import nts.uk.ctx.at.request.dom.setting.UseDivision;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.RecordDate;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.PrePostInitAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.service.checkpreappaccept.PreAppAcceptLimit;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.service.AppDeadlineSettingGet;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSet;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetRepository;
import nts.uk.ctx.at.request.dom.setting.workplace.appuseset.ApprovalFunctionSet;
import nts.uk.ctx.at.request.dom.setting.workplace.requestbycompany.RequestByCompanyRepository;
import nts.uk.ctx.at.request.dom.setting.workplace.requestbyworkplace.RequestByWorkplaceRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.service.WorkingConditionService;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultiple;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultipleRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Stateless
public class CommonAlgorithmImpl implements CommonAlgorithm {
	
	@Inject
	private AtEmployeeAdapter atEmployeeAdapter;
	
	@Inject
	private EmployeeRequestAdapter employeeAdaptor;
	
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;
	
	@Inject
	private CollectApprovalRootPatternService collectApprovalRootPatternService;
	
	@Inject
	private CollectAchievement collectAchievement;
	
	@Inject
	private WorkTypeRepository wkTypeRepo;
	
	@Inject
	private RequestByCompanyRepository requestByCompanyRepository;
	
	@Inject
	private RequestByWorkplaceRepository requestByWorkplaceRepository;
	
	@Inject
	private ApplicationSettingRepository applicationSettingRepository;
	
	@Inject
	private CommonAlgorithmMobile commonAlgorithmMobile; 
	
	@Inject
	private EnvAdapter envAdapter;
	
	@Inject
	private WorkManagementMultipleRepository workManagementMultipleRepository;
	
	@Inject
	private HolidayShipmentService holidayShipmentService;
	
	@Inject
	private AppEmploymentSetRepository appEmploymentSetRepository;
	
	@Inject
	private ClosureService closureService;
	
	@Inject
	private AppDeadlineSettingGet appDeadlineSettingGet;
	
	@Inject
	private WorkingConditionService workingConditionService;

	@Override
	public AppDispInfoNoDateOutput getAppDispInfo(String companyID, List<String> applicantLst, ApplicationType appType, 
			Optional<HolidayAppType> opHolidayAppType, Optional<OvertimeAppAtr> opOvertimeAppAtr) {
		// 申請者情報を取得する(Lấy thông tin người làm đơn/Applicant)
		List<EmployeeInfoImport> employeeInfoLst = this.getEmployeeInfoLst(applicantLst);
		// 申請別の申請設定を取得する
		ApplicationSetting applicationSetting = applicationSettingRepository.findByAppType(companyID, appType);
		// 申請理由表示区分を取得する
		AppReasonOutput appReasonOutput = commonAlgorithmMobile.getAppReasonDisplay(companyID, appType, opHolidayAppType);
		// メールサーバを設定したかチェックする
		MailServerSetImport mailServerSetImport = envAdapter.checkMailServerSet(companyID);
		// 複数回勤務を取得
		Optional<WorkManagementMultiple> opWorkManagementMultiple = workManagementMultipleRepository.findByCode(companyID);
		// 事前申請がいつから受付可能か確認する
		PreAppAcceptLimit preAppAcceptLimit = applicationSetting.getReceptionRestrictionSetting().checkWhenPreAppCanBeAccepted(opOvertimeAppAtr);
		// OUTPUT「申請表示情報(基準日関係なし)」にセットする(Set vào  OUTPUT "application display information (kg liên quan base date)")
		AppDispInfoNoDateOutput appDispInfoNoDateOutput = new AppDispInfoNoDateOutput(
				mailServerSetImport.isMailServerSet(), 
				preAppAcceptLimit.isUseReceptionRestriction() ? NotUseAtr.USE : NotUseAtr.NOT_USE, 
				employeeInfoLst, 
				applicationSetting, 
				Collections.emptyList(), 
				appReasonOutput.getDisplayAppReason(), 
				appReasonOutput.getDisplayStandardReason(), 
				appReasonOutput.getReasonTypeItemLst(), 
				opWorkManagementMultiple.isPresent());
		if(preAppAcceptLimit.isUseReceptionRestriction()) {
			appDispInfoNoDateOutput.setOpAdvanceReceptionDate(preAppAcceptLimit.getOpAcceptableDate());
			appDispInfoNoDateOutput.setOpAdvanceReceptionHours(preAppAcceptLimit.getOpAvailableTime());
		}
		// 「申請表示情報(基準日関係なし)」を返す (Trả về 「Thông tin hiển thị application(kg liên quan base date)」)
		return appDispInfoNoDateOutput;
	}

	@Override
	public List<EmployeeInfoImport> getEmployeeInfoLst(List<String> applicantLst) {
		List<String> queryLst = new ArrayList<>();
		// Input．申請者リストをチェック
		if(CollectionUtil.isEmpty(applicantLst)) {
			// Input．申請者リストにログイン者IDを追加
			queryLst.add(AppContexts.user().employeeId());
		} else {
			queryLst = applicantLst;
		}
		// 申請者情報を取得
		return atEmployeeAdapter.getByListSID(queryLst);
	}

	@Override
	public AppDispInfoWithDateOutput getAppDispInfoWithDate(String companyID, ApplicationType appType, List<GeneralDate> dateLst,
			AppDispInfoNoDateOutput appDispInfoNoDateOutput, boolean mode, Optional<OvertimeAppAtr> opOvertimeAppAtr) {
		// 基準日=INPUT．「申請対象日リスト」の1個目
		Optional<GeneralDate> targetDate = Optional.empty();
		if(!CollectionUtil.isEmpty(dateLst)) {
			GeneralDate firstDate = dateLst.get(0);
			if(firstDate == null) {
				targetDate = Optional.empty();
			} else {
				targetDate = Optional.of(firstDate);
			}
		}
		// INPUT．申請種類をチェックする
		if(appType == ApplicationType.COMPLEMENT_LEAVE_APPLICATION && !CollectionUtil.isEmpty(dateLst)) {
			// 基準申請日の決定
			GeneralDate recDate = dateLst.size() >= 1 ? dateLst.get(0) : null;
			GeneralDate absDate = dateLst.size() >= 2 ? dateLst.get(1) : null;
			targetDate = Optional.of(holidayShipmentService.detRefDate(recDate, absDate));
		}
		// 基準日として扱う日の取得
		GeneralDate baseDate = appDispInfoNoDateOutput.getApplicationSetting().getBaseDate(targetDate);
		// 社員IDから申請承認設定情報の取得
		String employeeID = appDispInfoNoDateOutput.getEmployeeInfoLst().stream().findFirst().get().getSid();
		ApprovalFunctionSet approvalFunctionSet = this.getApprovalFunctionSet(companyID, employeeID, baseDate, appType);
		// 取得したドメインモデル「申請承認機能設定．申請利用設定．利用区分」をチェックする
		if (mode && approvalFunctionSet.getAppUseSetLst().get(0).getUseDivision() == UseDivision.NOT_USE) {
			// エラーメッセージ(Msg_323)を返す
			throw new BusinessException("Msg_323", String.valueOf(appDispInfoNoDateOutput.getApplicationSetting().getRecordDate().value));
		}
		// 使用可能な就業時間帯を取得する
		List<WorkTimeSetting> workTimeLst = otherCommonAlgorithm.getWorkingHoursByWorkplace(companyID, employeeID, baseDate);
		// 社員所属雇用履歴を取得する
		SEmpHistImport empHistImport = employeeAdaptor.getEmpHist(companyID, employeeID, baseDate);
		if(empHistImport==null || empHistImport.getEmploymentCode()==null){
			// エラーメッセージ(Msg_426)を返す
			throw new BusinessException("Msg_426");
		}
		// 雇用別申請承認設定を取得する
		Optional<AppEmploymentSet> opAppEmploymentSet = appEmploymentSetRepository.findByCompanyIDAndEmploymentCD(companyID, empHistImport.getEmploymentCode());
	
		// INPUT．「新規詳細モード」を確認する
		Optional<List<ApprovalPhaseStateImport_New>> opListApprovalPhaseState = Optional.empty();
		Optional<ErrorFlagImport> opErrorFlag = Optional.empty();
		if(mode) {
			// 承認ルートを取得
			ApprovalRootContentImport_New approvalRootContentImport = this.getApprovalRoot(companyID, employeeID, EmploymentRootAtr.APPLICATION, appType, baseDate);
			opListApprovalPhaseState = Optional.of(approvalRootContentImport.getApprovalRootState().getListApprovalPhaseState());
			opErrorFlag = Optional.of(approvalRootContentImport.getErrorFlag());
		}
		// 申請表示情報(申請対象日関係あり)を取得する
		AppDispInfoRelatedDateOutput appDispInfoRelatedDateOutput = this.getAppDispInfoRelatedDate(
				companyID, 
				employeeID, 
				dateLst, 
				appType, 
				appDispInfoNoDateOutput.getApplicationSetting().getAppDisplaySetting().getPrePostDisplayAtr(), 
				appDispInfoNoDateOutput.getApplicationSetting().getAppTypeSetting().getDisplayInitialSegment(),
				opOvertimeAppAtr);
		// 雇用に紐づく締めを取得する
		int closureID = closureService.getClosureIDByEmploymentCD(empHistImport.getEmploymentCode());
		// 申請締切設定を取得する
		DeadlineLimitCurrentMonth deadlineLimitCurrentMonth = appDeadlineSettingGet.getApplicationDeadline(companyID, employeeID, closureID);
		// 取得したした情報をOUTPUT「申請表示情報(基準日関係あり)」にセットする
		AppDispInfoWithDateOutput appDispInfoWithDateOutput = new AppDispInfoWithDateOutput(
				approvalFunctionSet, 
				appDispInfoRelatedDateOutput.getPrePostAtr(), 
				baseDate, 
				empHistImport, 
				deadlineLimitCurrentMonth.isUseAtr() ? NotUseAtr.USE : NotUseAtr.NOT_USE);
		appDispInfoWithDateOutput.setOpEmploymentSet(opAppEmploymentSet);
		appDispInfoWithDateOutput.setOpListApprovalPhaseState(opListApprovalPhaseState);
		appDispInfoWithDateOutput.setOpErrorFlag(opErrorFlag);
		appDispInfoWithDateOutput.setOpActualContentDisplayLst(
				CollectionUtil.isEmpty(appDispInfoRelatedDateOutput.getActualContentDisplayLst()) ? Optional.empty() : Optional.of(appDispInfoRelatedDateOutput.getActualContentDisplayLst()));
		appDispInfoWithDateOutput.setOpPreAppContentDisplayLst(
				CollectionUtil.isEmpty(appDispInfoRelatedDateOutput.getPreAppContentDisplayLst()) ? Optional.empty() : Optional.of(appDispInfoRelatedDateOutput.getPreAppContentDisplayLst()));
		appDispInfoWithDateOutput.setOpAppDeadline(deadlineLimitCurrentMonth.getOpAppDeadline());
		appDispInfoWithDateOutput.setOpWorkTimeLst(Optional.of(workTimeLst));
		// 「申請表示情報(基準日関係あり)」を返す
		return appDispInfoWithDateOutput;
	}

	@Override
	public ApprovalFunctionSet getApprovalFunctionSet(String companyID, String employeeID, GeneralDate date, ApplicationType targetApp) {
		// [No.571]職場の上位職場を基準職場を含めて取得する
		List<String> workPlaceIDs = employeeAdaptor.findWpkIdsBySid(companyID, employeeID, date);
		for(String workPlaceID : workPlaceIDs) {
			// 職場別申請承認設定の取得
			Optional<ApprovalFunctionSet> opApprovalFunctionSet = requestByWorkplaceRepository.findByWkpAndAppType(companyID, workPlaceID, targetApp);
			// 取得した「申請承認機能設定」をチェック
			if(opApprovalFunctionSet.isPresent()) {
				return opApprovalFunctionSet.get();
			}
		}
		// 会社別申請承認設定の取得
		Optional<ApprovalFunctionSet> opApprovalFunctionSet = requestByCompanyRepository.findByAppType(companyID, targetApp);
		return opApprovalFunctionSet.get();
	}

	@Override
	public ApprovalRootContentImport_New getApprovalRoot(String companyID, String employeeID, EmploymentRootAtr rootAtr,
			ApplicationType appType, GeneralDate appDate) {
		// 1-4.新規画面起動時の承認ルート取得パターン
		return collectApprovalRootPatternService.getApprovalRootPatternNew(companyID, employeeID, rootAtr, appType, appDate);
	}

	@Override
	public AppDispInfoRelatedDateOutput getAppDispInfoRelatedDate(String companyID, String employeeID, List<GeneralDate> dateLst, 
			ApplicationType appType, DisplayAtr prePostAtrDisp, PrePostInitAtr initValueAtr, Optional<OvertimeAppAtr> opOvertimeAppAtr) {
		AppDispInfoRelatedDateOutput output = new AppDispInfoRelatedDateOutput();
		// INPUT．事前事後区分表示をチェックする
		if(prePostAtrDisp == DisplayAtr.NOT_DISPLAY) {
			// INPUT．申請対象日リストをチェックする
			if(CollectionUtil.isEmpty(dateLst)) {
				// OUTPUT．「事前事後区分」=事前
				output.setPrePostAtr(PrePostInitAtr.PREDICT);
			} else  {
				// 3.事前事後の判断処理(事前事後非表示する場合)
				PrePostAtr prePostAtrJudgment = otherCommonAlgorithm.preliminaryJudgmentProcessing(appType, dateLst.get(0), opOvertimeAppAtr.get());
				output.setPrePostAtr(EnumAdaptor.valueOf(prePostAtrJudgment.value, PrePostInitAtr.class));
			}
		} else {
			// 申請表示情報(基準日関係あり)．事前事後区分=INPUT．事前事後区分の初期表示
			output.setPrePostAtr(initValueAtr);
		}
		dateLst = dateLst.stream().filter(x -> x != null).collect(Collectors.toList());
		// 実績内容の取得
		/*List<ActualContentDisplay> actualContentDisplayLst = collectAchievement.getAchievementContents(companyID, employeeID, dateLst, appType);*/
		List<ActualContentDisplay> actualContentDisplayLst = Collections.emptyList();
		output.setActualContentDisplayLst(actualContentDisplayLst);
		// 事前内容の取得
		List<PreAppContentDisplay> preAppContentDisplayLst = collectAchievement.getPreAppContents(companyID, employeeID, dateLst, appType);
		output.setPreAppContentDisplayLst(preAppContentDisplayLst);
		return output;
	}

	@Override
	public AppDispInfoStartupOutput getAppDispInfoStart(String companyID, ApplicationType appType, List<String> applicantLst, 
			List<GeneralDate> dateLst, boolean mode, Optional<HolidayAppType> opHolidayAppType, Optional<OvertimeAppAtr> opOvertimeAppAtr) {
		// 申請表示情報(基準日関係なし)を取得する
		AppDispInfoNoDateOutput appDispInfoNoDateOutput = this.getAppDispInfo(companyID, applicantLst, appType, opHolidayAppType, opOvertimeAppAtr);
		// 申請表示情報(基準日関係あり)を取得する
		AppDispInfoWithDateOutput appDispInfoWithDateOutput = this.getAppDispInfoWithDate(companyID, appType, dateLst, appDispInfoNoDateOutput, mode, opOvertimeAppAtr);
		// OUTPUT「申請表示情報」にセットする
		AppDispInfoStartupOutput output = new AppDispInfoStartupOutput(appDispInfoNoDateOutput, appDispInfoWithDateOutput);
		// OUTPUT「申請表示情報」を返す
		return output;
	}

	@Override
	public AppDispInfoWithDateOutput changeAppDateProcess(String companyID, List<GeneralDate> dateLst, ApplicationType appType, 
			AppDispInfoNoDateOutput appDispInfoNoDateOutput, AppDispInfoWithDateOutput appDispInfoWithDateOutput, Optional<OvertimeAppAtr> opOvertimeAppAtr) {
		// INPUT．「申請表示情報(基準日関係なし) ．申請承認設定．申請設定」．承認ルートの基準日をチェックする
		if(appDispInfoNoDateOutput.getApplicationSetting().getRecordDate() == RecordDate.SYSTEM_DATE) {
			// 申請表示情報(申請対象日関係あり)を取得する
			AppDispInfoRelatedDateOutput result = this.getAppDispInfoRelatedDate(
					companyID, appDispInfoNoDateOutput.getEmployeeInfoLst().stream().findFirst().get().getSid(), 
					dateLst, 
					appType, 
					appDispInfoNoDateOutput.getApplicationSetting().getAppDisplaySetting().getPrePostDisplayAtr(), 
					appDispInfoNoDateOutput.getApplicationSetting().getAppTypeSetting().getDisplayInitialSegment(),
					opOvertimeAppAtr);
			appDispInfoWithDateOutput.setPrePostAtr(result.getPrePostAtr());
			appDispInfoWithDateOutput.setOpActualContentDisplayLst(
					CollectionUtil.isEmpty(result.getActualContentDisplayLst()) ? Optional.empty() : Optional.of(result.getActualContentDisplayLst()));
			appDispInfoWithDateOutput.setOpPreAppContentDisplayLst(
					CollectionUtil.isEmpty(result.getPreAppContentDisplayLst()) ? Optional.empty() : Optional.of(result.getPreAppContentDisplayLst()));
			return appDispInfoWithDateOutput;
		} else {
			// 申請表示情報(基準日関係あり)を取得する
			return this.getAppDispInfoWithDate(companyID, appType, dateLst, appDispInfoNoDateOutput, true, opOvertimeAppAtr);
		}
	}

	@Override
	public ApplyWorkTypeOutput appliedWorkType(String companyID, List<WorkType> wkTypes, String wkTypeCD) {
		Optional<WorkType> workTypeInLst = wkTypes.stream()
				.filter(wk -> wk.getWorkTypeCode().v().equals(wkTypeCD))
				.findAny();
		if(workTypeInLst.isPresent()) {
			// INPUT.勤務種類(List)内にINPUT.選択済勤務種類コードが含まれる((trong INPUT.workType(list) có chứa selectedWorkTypeCode)
			return new ApplyWorkTypeOutput(wkTypes, false);
		}
		
		// ドメインモデル「勤務種類」を取得する(Lấy domain [WorkType])
		Optional<WorkType> wkTypeOpt = wkTypeRepo.findByPK(companyID, wkTypeCD);
		if(!wkTypeOpt.isPresent()) {
			// マスタ未登録←true(master Unregistered ←true)
			return new ApplyWorkTypeOutput(wkTypes, true);
		}
		// INPUT.勤務種類(List)に勤務種類を追加する(Thêm workType vào INPUT.workType(list))
		wkTypes.add(wkTypeOpt.get());
		// INPUT.勤務種類(List)をソートする(Sort INPUT.workType(List))
		wkTypes.sort(Comparator.comparing(x -> x.getWorkTypeCode().v()));
		// マスタ未登録←false(master Unregistered ←false)
		return new ApplyWorkTypeOutput(wkTypes, false);
	}

	@Override
	public Optional<EmployeeInfoImport> getEnterPersonInfor(String employeeID, String enterPersonID) {
		List<EmployeeInfoImport> employeeInfoLst = new ArrayList<>();
		// INPUT．申請者とINPUT．入力者をチェックする (Check INPUT. Applicant and INPUT.người input/nhập)
		if(employeeID.equals(enterPersonID)) {
			employeeInfoLst = this.getEmployeeInfoLst(Arrays.asList(employeeID));
		} else {
			// 社員ID（List）から社員コードと表示名を取得 (Lấy tên hiển thị và employee code từ Employee ID (List))
			employeeInfoLst = this.getEmployeeInfoLst(Arrays.asList(enterPersonID));
		}
		// 取得した社員情報を返す (Returns employee information đã lấy )
		if(CollectionUtil.isEmpty(employeeInfoLst)) {
			return Optional.empty();
		}
		return employeeInfoLst.stream().findFirst();
	}

	@Override
	public void appConflictCheck(String companyID, EmployeeInfoImport employeeInfo, List<GeneralDate> dateLst,
			List<String> workTypeLst, List<AchievementOutput> achievementOutputLst) {
		for(GeneralDate loopDate : dateLst) {
			
		}
		
	}

	@Override
	public InitWkTypeWkTimeOutput initWorkTypeWorkTime(String employeeID, GeneralDate date, List<WorkType> workTypeLst,
			List<WorkTimeSetting> workTimeLst, AchievementDetail achievementDetail) {
		String companyID = AppContexts.user().companyId();
		// 申請日付チェック
		if(date != null) {
			// INPUT．「実績詳細」をチェックする
			if(Strings.isNotBlank(achievementDetail.getWorkTypeCD()) && Strings.isNotBlank(achievementDetail.getWorkTimeCD())) {
				// 12.マスタ勤務種類、就業時間帯データをチェック
				CheckWorkingInfoResult checkWorkingInfoResult = otherCommonAlgorithm.checkWorkingInfo(
						companyID, 
						achievementDetail.getWorkTypeCD(), 
						achievementDetail.getWorkTimeCD());
				// 勤務種類エラーFlgをチェック
				String resultWorkType = Strings.EMPTY;
				if(checkWorkingInfoResult.isWkTypeError()) {
					// 先頭の勤務種類を選択する(chon cai dau tien trong list loai di lam)
					resultWorkType = workTypeLst.stream().findFirst().map(x -> x.getWorkTypeCode().v()).orElse(null);
				}
				// 就業時間帯エラーFlgをチェック
				String resultWorkTime = Strings.EMPTY;
				if(checkWorkingInfoResult.isWkTimeError()) {
					// 先頭の就業時間帯を選択する(chọn mui giờ làm đầu tiên)
					resultWorkTime = workTimeLst.stream().findFirst().map(x -> x.getWorktimeCode().v()).orElse(null);
				}
				return new InitWkTypeWkTimeOutput(resultWorkType, resultWorkTime);
			}
		}
		// 社員の労働条件を取得する(get điiều kiện lao đọng của employee)
		GeneralDate paramDate = date == null ? GeneralDate.today() : date;
		Optional<WorkingConditionItem> opWorkingConditionItem = workingConditionService.findWorkConditionByEmployee(employeeID, paramDate);
		String processWorkType = null;
		String processWorkTime = null; 
		if(opWorkingConditionItem.isPresent()) {
			// ドメインモデル「個人勤務日区分別勤務」．平日時．勤務種類コードが【07_勤務種類取得】取得した勤務種類Listにあるかをチェックする
			Optional<WorkTypeCode> opConditionWktypeCD = opWorkingConditionItem.get().getWorkCategory().getWeekdayTime().getWorkTypeCode();
			List<String> workTypeCDLst = workTypeLst.stream().map(x -> x.getWorkTypeCode().v()).collect(Collectors.toList());
			if(opConditionWktypeCD.isPresent() && workTypeCDLst.contains(opConditionWktypeCD.get().v())) {
				// ドメインモデル「個人勤務日区分別勤務」．平日時．勤務種類コードを選択する(chọn cai loai di lam)
				processWorkType = opConditionWktypeCD.get().v();
			} else {
				// 先頭の勤務種類を選択する(chon cai dau tien trong list loai di lam)
				processWorkType = workTypeLst.stream().findFirst().map(x -> x.getWorkTypeCode().v()).orElse(null);
			}
			// ドメインモデル「個人勤務日区分別勤務」．平日時．就業時間帯コードを選択する(chon cai mui gio lam trong domain trên)
			processWorkTime = opWorkingConditionItem.get().getWorkCategory().getWeekdayTime().getWorkTimeCode().map(x -> x.v()).orElse(null);
		} else {
			// 先頭の勤務種類を選択する(chon cai dau tien trong list loai di lam)
			processWorkType = workTypeLst.stream().findFirst().map(x -> x.getWorkTypeCode().v()).orElse(null);
			// Input．就業時間帯リストをチェック(Check Input. worktimeList)
			if(!CollectionUtil.isEmpty(workTimeLst)) {
				// 先頭の就業時間帯を選択する(chọn mui giờ làm đầu tiên)
				processWorkTime = workTimeLst.stream().findFirst().map(x -> x.getWorktimeCode().v()).orElse(null);
			}
		}
		// 12.マスタ勤務種類、就業時間帯データをチェック
		CheckWorkingInfoResult checkWorkingInfoResult = otherCommonAlgorithm.checkWorkingInfo(
				companyID, 
				processWorkType, 
				processWorkTime);
		// 勤務種類エラーFlgをチェック
		String resultWorkType = null;
		if(checkWorkingInfoResult.isWkTypeError()) {
			// 先頭の勤務種類を選択する(chon cai dau tien trong list loai di lam)
			resultWorkType = workTypeLst.stream().findFirst().map(x -> x.getWorkTypeCode().v()).orElse(null);
		}
		// 就業時間帯エラーFlgをチェック
		String resultWorkTime = null;
		if(checkWorkingInfoResult.isWkTimeError()) {
			// 先頭の就業時間帯を選択する(chọn mui giờ làm đầu tiên)
			resultWorkTime = workTimeLst.stream().findFirst().map(x -> x.getWorktimeCode().v()).orElse(null);
		}
		return new InitWkTypeWkTimeOutput(resultWorkType, resultWorkTime);
	}

}
