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

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.ApplicationType_Old;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.PrePostAtr_Old;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.AtEmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.sys.EnvAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.sys.dto.MailServerSetImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.CollectApprovalRootPatternService;
import nts.uk.ctx.at.request.dom.application.common.service.other.AppDetailContent;
import nts.uk.ctx.at.request.dom.application.common.service.other.CollectAchievement;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoNoDateOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoNoDateOutput_Old;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoWithDateOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoWithDateOutput_Old;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.ApplyWorkTypeOutput;
import nts.uk.ctx.at.request.dom.application.common.service.smartphone.CommonAlgorithmMobile;
import nts.uk.ctx.at.request.dom.application.common.service.smartphone.output.AppReasonOutput;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.service.checkpreappaccept.PreAppAcceptLimit;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.PrePostInitialAtr;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.displaysetting.DisplayAtr;
import nts.uk.ctx.at.request.dom.setting.workplace.appuseset.ApprovalFunctionSet;
import nts.uk.ctx.at.request.dom.setting.workplace.requestbycompany.RequestByCompanyRepository;
import nts.uk.ctx.at.request.dom.setting.workplace.requestbyworkplace.RequestByWorkplaceRepository;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultiple;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultipleRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
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
	public AppDispInfoWithDateOutput_Old getAppDispInfoWithDate(String companyID, ApplicationType_Old appType,
			List<GeneralDate> dateLst, AppDispInfoNoDateOutput_Old appDispInfoNoDateOutput, boolean mode) {
		AppDispInfoWithDateOutput_Old output = new AppDispInfoWithDateOutput_Old();
		// error EA refactor 4
		/*// 基準日=INPUT．「申請対象日リスト」の1個目
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
		if(appType == ApplicationType_Old.COMPLEMENT_LEAVE_APPLICATION && !CollectionUtil.isEmpty(dateLst)) {
			// 基準申請日の決定
			GeneralDate recDate = dateLst.size() >= 1 ? dateLst.get(0) : null;
			GeneralDate absDate = dateLst.size() >= 2 ? dateLst.get(1) : null;
			targetDate = Optional.of(holidayShipmentService.detRefDate(recDate, absDate));
		}
		// 基準日として扱う日の取得
		RecordDate recordDate = appDispInfoNoDateOutput.getRequestSetting().getApplicationSetting().getRecordDate();
		GeneralDate baseDate = baseDateGet.getBaseDate(targetDate, recordDate);
		// 社員IDから申請承認設定情報の取得
		String employeeID = appDispInfoNoDateOutput.getEmployeeInfoLst().stream().findFirst().get().getSid();
		ApprovalFunctionSetting approvalFunctionSet = this.getApprovalFunctionSet(companyID, employeeID, baseDate, appType);
		// 取得したドメインモデル「申請承認機能設定．申請利用設定．利用区分」をチェックする
		if (approvalFunctionSet.getAppUseSetting().getUseDivision() == UseDivision.TO_USE) {
			// エラーメッセージ(Msg_323)を返す
			throw new BusinessException("Msg_323");
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
		Optional<AppEmploymentSetting>  employmentSetLst = appEmploymentSetting.getEmploymentSetting(companyID, empHistImport.getEmploymentCode(), appType.value);
		if(employmentSetLst.isPresent()) {
			// just have 1 record
			output.setEmploymentSet(employmentSetLst.get());
		}
	
		// INPUT．「新規詳細モード」を確認する
		ApprovalRootContentImport_New approvalRootContentImport = new ApprovalRootContentImport_New(null, ErrorFlagImport.NO_APPROVER);
		if(mode) {
			// 承認ルートを取得
			approvalRootContentImport = this.getApprovalRoot(companyID, employeeID, EmploymentRootAtr.APPLICATION, appType, baseDate);
		}
		// 申請表示情報(申請対象日関係あり)を取得する
		AppTypeSetting appTypeSetting = appDispInfoNoDateOutput.getRequestSetting().getApplicationSetting()
				.getListAppTypeSetting().stream().filter(x -> x.getAppType()==appType).findAny().get();
		AppDispInfoWithDateOutput_Old appDispInfoWithDateOutput = this.getAppDispInfoRelatedDate(
				companyID, 
				employeeID, 
				dateLst, 
				appType, 
				appDispInfoNoDateOutput.getRequestSetting().getApplicationSetting().getAppDisplaySetting().getPrePostAtrDisp(), 
				appTypeSetting.getDisplayInitialSegment());
		// 取得したした情報をOUTPUT「申請表示情報(基準日関係あり)」にセットする
		output.setApprovalFunctionSet(approvalFunctionSet);
		output.setWorkTimeLst(workTimeLst);
		output.setApprovalRootState(approvalRootContentImport.approvalRootState);
		output.setErrorFlag(approvalRootContentImport.getErrorFlag());
		output.setPrePostAtr(appDispInfoWithDateOutput.getPrePostAtr());
		output.setBaseDate(baseDate);
		output.setAchievementOutputLst(appDispInfoWithDateOutput.getAchievementOutputLst());
		output.setAppDetailContentLst(appDispInfoWithDateOutput.getAppDetailContentLst());
		output.setEmpHistImport(empHistImport);*/
		// 「申請表示情報(基準日関係あり)」を返す
		return output;
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
	public AppDispInfoWithDateOutput_Old getAppDispInfoRelatedDate(String companyID, String employeeID,
			List<GeneralDate> dateLst, ApplicationType_Old appType, DisplayAtr prePostAtrDisp, PrePostInitialAtr initValueAtr) {
		AppDispInfoWithDateOutput_Old output = new AppDispInfoWithDateOutput_Old();
		// INPUT．事前事後区分表示をチェックする
		if(prePostAtrDisp == DisplayAtr.NOT_DISPLAY) {
			// INPUT．申請対象日リストをチェックする
			if(CollectionUtil.isEmpty(dateLst)) {
				// OUTPUT．「事前事後区分」=事前
				output.setPrePostAtr(PrePostAtr_Old.PREDICT);
			} else  {
				// error EA refactor 4
				// 3.事前事後の判断処理(事前事後非表示する場合)
				/*PrePostAtr_Old prePostAtrJudgment = otherCommonAlgorithm.preliminaryJudgmentProcessing(appType, dateLst.get(0), 0);
				output.setPrePostAtr(prePostAtrJudgment);*/
			}
		} else {
			// 申請表示情報(基準日関係あり)．事前事後区分=INPUT．事前事後区分の初期表示
			output.setPrePostAtr(EnumAdaptor.valueOf(initValueAtr.value, PrePostAtr_Old.class));
		}
		dateLst = dateLst.stream().filter(x -> x != null).collect(Collectors.toList());
		// 実績内容の取得
		List<AchievementOutput> achievementOutputLst = collectAchievement.getAchievementContents(companyID, employeeID, dateLst, appType);
		output.setAchievementOutputLst(achievementOutputLst);
		// 事前内容の取得
		List<AppDetailContent> appDetailContentLst = collectAchievement.getPreAppContents(companyID, employeeID, dateLst, appType);
		output.setAppDetailContentLst(appDetailContentLst);
		return output;
	}

	@Override
	public AppDispInfoStartupOutput getAppDispInfoStart(String companyID, ApplicationType appType,
			List<String> applicantLst, List<GeneralDate> dateLst, boolean mode) {
		// error EA refactor 4
		/*// 申請表示情報(基準日関係なし)を取得する
		AppDispInfoNoDateOutput appDispInfoNoDateOutput = this.getAppDispInfo(companyID, applicantLst, appType);
		// 申請表示情報(基準日関係あり)を取得する
		AppDispInfoWithDateOutput appDispInfoWithDateOutput = this.getAppDispInfoWithDate(companyID, appType, dateLst, appDispInfoNoDateOutput, mode);
		// OUTPUT「申請表示情報」にセットする
		AppDispInfoStartupOutput output = new AppDispInfoStartupOutput(appDispInfoNoDateOutput, appDispInfoWithDateOutput);
		// OUTPUT「申請表示情報」を返す
		return output;*/
		return null;
	}

	@Override
	public AppDispInfoWithDateOutput changeAppDateProcess(String companyID, List<GeneralDate> dateLst,
			ApplicationType appType, AppDispInfoNoDateOutput appDispInfoNoDateOutput, AppDispInfoWithDateOutput appDispInfoWithDateOutput) {
		// error EA refactor 4
		/*// INPUT．「申請表示情報(基準日関係なし) ．申請承認設定．申請設定」．承認ルートの基準日をチェックする
		if(appDispInfoNoDateOutput.getRequestSetting().getApplicationSetting().getRecordDate() == RecordDate.SYSTEM_DATE) {
			// 申請表示情報(申請対象日関係あり)を取得する
			AppTypeSetting appTypeSetting = appDispInfoNoDateOutput.getRequestSetting().getApplicationSetting()
					.getListAppTypeSetting().stream().filter(x -> x.getAppType()==appType).findAny().get();
			AppDispInfoWithDateOutput_Old result = this.getAppDispInfoRelatedDate(
					companyID, appDispInfoNoDateOutput.getEmployeeInfoLst().stream().findFirst().get().getSid(), dateLst, appType, 
					appDispInfoNoDateOutput.getRequestSetting().getApplicationSetting().getAppDisplaySetting().getPrePostAtrDisp(), 
					appTypeSetting.getDisplayInitialSegment());
			appDispInfoWithDateOutput.setPrePostAtr(result.getPrePostAtr());
			appDispInfoWithDateOutput.setAchievementOutputLst(result.getAchievementOutputLst());
			appDispInfoWithDateOutput.setAppDetailContentLst(result.getAppDetailContentLst());
			return appDispInfoWithDateOutput;
		} else {
			// 申請表示情報(基準日関係あり)を取得する
			return this.getAppDispInfoWithDate(companyID, appType, dateLst, appDispInfoNoDateOutput, true);
		}*/
		return null;
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

}
