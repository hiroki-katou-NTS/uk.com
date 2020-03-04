package nts.uk.ctx.at.request.dom.application.common.service.setting;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.AtEmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.SEmpHistImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ErrorFlagImport;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.CollectApprovalRootPatternService;
import nts.uk.ctx.at.request.dom.application.common.service.other.AppDetailContent;
import nts.uk.ctx.at.request.dom.application.common.service.other.CollectAchievement;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoNoDateOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoWithDateOutput;
import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReason;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.service.BaseDateGet;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSettingRepository;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.AppTypeSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.PrePostInitialAtr;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.displaysetting.DisplayAtr;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSettingRepository;
import nts.uk.ctx.at.request.dom.setting.workplace.ApprovalFunctionSetting;
import nts.uk.ctx.at.request.dom.setting.workplace.RequestOfEachCompanyRepository;
import nts.uk.ctx.at.request.dom.setting.workplace.RequestOfEachWorkplaceRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CommonAlgorithmImpl implements CommonAlgorithm {
	
	@Inject
	private AtEmployeeAdapter atEmployeeAdapter;
	
	@Inject
	private RequestSettingRepository requestSettingRepository;
	
	@Inject
	private BaseDateGet baseDateGet;
	
	@Inject
	private EmployeeRequestAdapter employeeAdaptor;
	
	@Inject
	private RequestOfEachWorkplaceRepository requestOfEachWorkplaceRepository;
	
	@Inject
	private RequestOfEachCompanyRepository requestOfEachCompanyRepository;
	
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;
	
	@Inject
	private AppEmploymentSettingRepository appEmploymentSetting;
	
	@Inject
	private CollectApprovalRootPatternService collectApprovalRootPatternService;
	
	@Inject
	private CollectAchievement collectAchievement;

	@Override
	public AppDispInfoNoDateOutput getAppDispInfo(String companyID, List<String> applicantLst, ApplicationType appType) {
		// 申請者情報を取得する
		List<EmployeeInfoImport> employeeInfoLst = this.getEmployeeInfoLst(applicantLst);
		// 申請承認設定を取得する
		RequestSetting requestSetting = requestSettingRepository.findByCompany(companyID).get();
		// 01-05_申請定型理由を取得
		AppTypeSetting appTypeSetting = requestSetting.getApplicationSetting()
				.getListAppTypeSetting().stream().filter(x -> x.getAppType()==appType).findAny().get();
		List<ApplicationReason> appReasonLst = otherCommonAlgorithm.getApplicationReasonType(
				companyID, 
				appTypeSetting.getDisplayFixedReason(), 
				appType);
		// OUTPUT「申請表示情報(基準日関係なし)」にセットする
		AppDispInfoNoDateOutput output = new AppDispInfoNoDateOutput(employeeInfoLst, requestSetting, appReasonLst);
		// 「申請表示情報(基準日関係なし)」を返す
		return output;
	}

	@Override
	public List<EmployeeInfoImport> getEmployeeInfoLst(List<String> applicantLst) {
		// Input．申請者リストをチェック
		if(CollectionUtil.isEmpty(applicantLst)) {
			// Input．申請者リストにログイン者IDを追加
			applicantLst.add(AppContexts.user().employeeId());
		}
		// 申請者情報を取得
		return atEmployeeAdapter.getByListSID(applicantLst);
	}

	@Override
	public AppDispInfoWithDateOutput getAppDispInfoWithDate(String companyID, ApplicationType appType,
			List<GeneralDate> dateLst, AppDispInfoNoDateOutput appDispInfoNoDateOutput, boolean mode) {
		AppDispInfoWithDateOutput output = new AppDispInfoWithDateOutput();
		// 基準日として扱う日の取得
		GeneralDate baseDate = baseDateGet.getBaseDate(dateLst.stream().findFirst());
		// 社員IDから申請承認設定情報の取得
		ApprovalFunctionSetting approvalFunctionSet = this.getApprovalFunctionSet(companyID, "", baseDate);
		// 取得したドメインモデル「申請承認機能設定．申請利用設定．利用区分」をチェックする
		if (approvalFunctionSet.getAppUseSetting().getUserAtr()==UseAtr.NOTUSE) {
			// エラーメッセージ(Msg_323)を返す
			throw new BusinessException("Msg_323");
		}
		// 使用可能な就業時間帯を取得する
		List<WorkTimeSetting> workTimeLst = otherCommonAlgorithm.getWorkingHoursByWorkplace(companyID, "", baseDate);
		// 社員所属雇用履歴を取得する
		SEmpHistImport empHistImport = employeeAdaptor.getEmpHist(companyID, "", baseDate);
		if(empHistImport==null || empHistImport.getEmploymentCode()==null){
			// エラーメッセージ(Msg_426)を返す
			throw new BusinessException("Msg_426");
		}
		// 雇用別申請承認設定を取得する
		List<AppEmploymentSetting> lstEmploymentSet = appEmploymentSetting.getEmploymentSetting(companyID, empHistImport.getEmploymentCode(), appType.value);
		// INPUT．「新規詳細モード」を確認する
		ApprovalRootContentImport_New approvalRootContentImport = new ApprovalRootContentImport_New(null, ErrorFlagImport.NO_APPROVER);
		if(mode) {
			// 承認ルートを取得
			approvalRootContentImport = this.getApprovalRoot(companyID, "", EmploymentRootAtr.APPLICATION, appType, baseDate);
		}
		// 申請表示情報(申請対象日関係あり)を取得する
		AppTypeSetting appTypeSetting = appDispInfoNoDateOutput.getRequestSetting().getApplicationSetting()
				.getListAppTypeSetting().stream().filter(x -> x.getAppType()==appType).findAny().get();
		AppDispInfoWithDateOutput appDispInfoWithDateOutput = this.getAppDispInfoRelatedDate(
				companyID, 
				"", 
				dateLst, 
				appType, 
				appDispInfoNoDateOutput.getRequestSetting().getApplicationSetting().getAppDisplaySetting().getPrePostAtrDisp(), 
				appTypeSetting.getDisplayInitialSegment());
		// 取得したした情報をOUTPUT「申請表示情報(基準日関係あり)」にセットする
		output.setApprovalFunctionSet(approvalFunctionSet);
		output.setLstEmploymentSet(lstEmploymentSet);
		output.setWorkTimeLst(workTimeLst);
		output.setApprovalRootState(approvalRootContentImport.approvalRootState);
		output.setErrorFlag(approvalRootContentImport.getErrorFlag());
		output.setPrePostAtr(appDispInfoWithDateOutput.getPrePostAtr());
		output.setBaseDate(baseDate);
		output.setAchievementOutputLst(appDispInfoWithDateOutput.getAchievementOutputLst());
		output.setAppDetailContentLst(appDispInfoWithDateOutput.getAppDetailContentLst());
		return output;
	}

	@Override
	public ApprovalFunctionSetting getApprovalFunctionSet(String companyID, String employeeID, GeneralDate date) {
		ApplicationType targetApp = ApplicationType.ABSENCE_APPLICATION;
		// [No.571]職場の上位職場を基準職場を含めて取得する
		List<String> workPlaceIDs = employeeAdaptor.findWpkIdsBySid(companyID, employeeID, date);
		for(String workPlaceID : workPlaceIDs) {
			// 職場別申請承認設定の取得
			Optional<ApprovalFunctionSetting> settingOfEarchWorkplaceOp = requestOfEachWorkplaceRepository.getFunctionSetting(companyID, workPlaceID, targetApp.value);
			// 取得した「申請承認機能設定」をチェック
			if(settingOfEarchWorkplaceOp.isPresent()) {
				return settingOfEarchWorkplaceOp.get();
			}
		}
		// 会社別申請承認設定の取得
		Optional<ApprovalFunctionSetting> rqOptional = requestOfEachCompanyRepository.getFunctionSetting(companyID, targetApp.value);
		return rqOptional.get();
	}

	@Override
	public ApprovalRootContentImport_New getApprovalRoot(String companyID, String employeeID, EmploymentRootAtr rootAtr,
			ApplicationType appType, GeneralDate appDate) {
		// 1-4.新規画面起動時の承認ルート取得パターン
		return collectApprovalRootPatternService.getgetApprovalRootPatternNew(companyID, employeeID, rootAtr, appType, appDate);
	}

	@Override
	public AppDispInfoWithDateOutput getAppDispInfoRelatedDate(String companyID, String employeeID,
			List<GeneralDate> dateLst, ApplicationType appType, DisplayAtr prePostAtrDisp, PrePostInitialAtr initValueAtr) {
		AppDispInfoWithDateOutput output = new AppDispInfoWithDateOutput();
		// INPUT．事前事後区分表示をチェックする
		if(prePostAtrDisp == DisplayAtr.NOT_DISPLAY) {
			// 3.事前事後の判断処理(事前事後非表示する場合)
			PrePostAtr prePostAtrJudgment = otherCommonAlgorithm.preliminaryJudgmentProcessing(appType, dateLst.stream().findFirst().get(), 0);
			output.setPrePostAtr(prePostAtrJudgment);
		} else {
			// 申請表示情報(基準日関係あり)．事前事後区分=INPUT．事前事後区分の初期表示
			output.setPrePostAtr(EnumAdaptor.valueOf(initValueAtr.value, PrePostAtr.class));
		}
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
		// 申請表示情報(基準日関係なし)を取得する
		AppDispInfoNoDateOutput appDispInfoNoDateOutput = this.getAppDispInfo(companyID, applicantLst, appType);
		// 申請表示情報(基準日関係あり)を取得する
		AppDispInfoWithDateOutput appDispInfoWithDateOutput = this.getAppDispInfoWithDate(companyID, appType, dateLst, appDispInfoNoDateOutput, mode);
		// OUTPUT「申請表示情報」にセットする
		AppDispInfoStartupOutput output = new AppDispInfoStartupOutput(appDispInfoNoDateOutput, appDispInfoWithDateOutput);
		// OUTPUT「申請表示情報」を返す
		return output;
	}

}
