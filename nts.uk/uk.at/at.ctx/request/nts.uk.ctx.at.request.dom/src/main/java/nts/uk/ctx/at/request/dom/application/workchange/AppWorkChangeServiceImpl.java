package nts.uk.ctx.at.request.dom.application.workchange;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ErrorFlagImport;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.init.DetailAppCommonSetService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.NewBeforeRegister_New;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.setting.CommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoWithDateOutput;
import nts.uk.ctx.at.request.dom.application.overtime.service.CheckWorkingInfoResult;
import nts.uk.ctx.at.request.dom.application.workchange.output.AppWorkChangeDetailOutput;
import nts.uk.ctx.at.request.dom.application.workchange.output.AppWorkChangeDispInfo;
import nts.uk.ctx.at.request.dom.application.workchange.output.ChangeWkTypeTimeOutput;
import nts.uk.ctx.at.request.dom.application.workchange.output.WorkChangeCheckRegOutput;
import nts.uk.ctx.at.request.dom.application.workchange.output.WorkTypeWorkTimeSelect;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.WorkTypeObjAppHoliday;
import nts.uk.ctx.at.request.dom.setting.request.application.workchange.AppWorkChangeSet;
import nts.uk.ctx.at.request.dom.setting.request.application.workchange.IAppWorkChangeSetRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.workchange.InitDisplayWorktimeAtr;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AppWorkChangeServiceImpl implements AppWorkChangeService {
	
	@Inject
	private CommonAlgorithm commonAlgorithm;
	
	@Inject
	private IAppWorkChangeSetRepository appWorkChangeSetRepository;
	
	@Inject
	private WorkTypeRepository workTypeRepository;
	
	@Inject
	private WorkingConditionItemRepository workingConditionItemRepository;
	
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;
	
	@Inject 
	private BasicScheduleService basicScheduleService;
	
	@Inject
	private PredetemineTimeSettingRepository predetemineTimeSettingRepository;
	
	@Inject
	private NewBeforeRegister_New newBeforeRegister;
	
	@Inject
	private DetailAppCommonSetService detailAppCommonSetService;
	
	@Inject
	private IAppWorkChangeRepository appWorkChangeRepository;

	public WorkTypeObjAppHoliday geWorkTypeObjAppHoliday(AppEmploymentSetting x, ApplicationType hdType) {
		return x.getListWTOAH().stream().filter(y -> y.getAppType() == hdType).findFirst().get();
	}
	
	@Override
	public AppWorkChangeDispInfo getStartNew(String companyID, List<String> employeeIDLst, List<GeneralDate> dateLst) {
		AppWorkChangeDispInfo result = new AppWorkChangeDispInfo();
		// 共通申請
		AppDispInfoStartupOutput appDispInfoStartupOutput = commonAlgorithm.getAppDispInfoStart(
				companyID, 
				ApplicationType.WORK_CHANGE_APPLICATION, 
				employeeIDLst, 
				dateLst, 
				true);
		// ドメインモデル「勤務変更申請設定」を取得する
		AppWorkChangeSet appWorkChangeSet = appWorkChangeSetRepository.findWorkChangeSetByID(companyID).get();
		AppEmploymentSetting employmentSet = appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getEmploymentSet();
//		AppEmploymentSetting employmentSet = employmentSetLst.stream().filter(x -> x.getHolidayOrPauseType() == appAbsence.getHolidayAppType().value)
//				.findFirst().orElse(null);
//		Optional<AppEmploymentSetting> setting = employmentSetLst.stream().filter(x -> 
//		(CollectionUtil.isEmpty(x.getListWTOAH())) ? false : 
//			geWorkTypeObjAppHoliday(x,ApplicationType.WORK_CHANGE_APPLICATION).getAppType() == ApplicationType.WORK_CHANGE_APPLICATION
//				
//				).findFirst();
//		AppEmploymentSetting employmentSet = setting.get();
		// 勤務種類を取得
		List<WorkType> workTypeLst = this.getWorkTypeLst(employmentSet);
		// 勤務種類・就業時間帯の初期選択項目を取得する
		String employeeID = appDispInfoStartupOutput.getAppDispInfoNoDateOutput().getEmployeeInfoLst().get(0).getSid();
		GeneralDate date = appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getBaseDate();
		List<WorkTimeSetting> workTimeLst = appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getWorkTimeLst();
		WorkTypeWorkTimeSelect workTypeWorkTimeSelect = 
				this.initWorkTypeWorkTime(companyID, employeeID, date, workTypeLst, workTimeLst);
		// 勤務種類・就業時間帯を変更する時
		String workTypeCD = workTypeWorkTimeSelect.getWorkType().getWorkTypeCode().v();
		String workTimeCD = workTypeWorkTimeSelect.getWorkTime() == null ? null : workTypeWorkTimeSelect.getWorkTime().getWorktimeCode().v();
		ChangeWkTypeTimeOutput changeWkTypeTimeOutput =
				this.changeWorkTypeWorkTime(companyID, workTypeCD, Optional.ofNullable(workTimeCD), appWorkChangeSet);
		// 取得した情報をOUTPUT「勤務変更申請の表示情報」にセットしてを返す
		result.setAppDispInfoStartupOutput(appDispInfoStartupOutput);
		result.setAppWorkChangeSet(appWorkChangeSet);
		result.setWorkTypeLst(workTypeLst);
		result.setSetupType(changeWkTypeTimeOutput.getSetupType());
		result.setPredetemineTimeSetting(changeWkTypeTimeOutput.getOpPredetemineTimeSetting().orElse(null));
		result.setWorkTypeCD(workTypeCD);
		result.setWorkTimeCD(workTimeCD);
		return result;
	}

	@Override
	public List<WorkType> getWorkTypeLst(AppEmploymentSetting appEmploymentSetting) {
		String companyID = AppContexts.user().companyId();
		// INPUT．「雇用別申請承認設定．申請別対象勤務種類」を確認する
		if(appEmploymentSetting == null) {
			// ドメインモデル「勤務種類」を取得して返す
			return workTypeRepository.findNotDeprecated(companyID);
		}
		// INPUT．「雇用別申請承認設定．申請別対象勤務種類．勤務種類リスト」を返す
		List<String> workTypeCDLst = appEmploymentSetting.getListWTOAH().get(0).getWorkTypeList();
		return workTypeRepository.findNotDeprecatedByListCode(companyID, workTypeCDLst);
	}

	@Override
	public WorkTypeWorkTimeSelect initWorkTypeWorkTime(String companyID, String employeeID, GeneralDate date,
			List<WorkType> workTypeLst, List<WorkTimeSetting> workTimeLst) {
		WorkTypeWorkTimeSelect result = new WorkTypeWorkTimeSelect();
		// ドメインモデル「個人労働条件」を取得する(lay dieu kien lao dong ca nhan(個人労働条件))
		Optional<WorkingConditionItem> personalLablorCodition = workingConditionItemRepository
				.getBySidAndStandardDate(employeeID, date);
		if(!personalLablorCodition.isPresent()) {
			// OUTPUT「選択する勤務種類」と「選択する就業時間帯」をセットする
			result.setWorkType(workTypeLst.get(0));
			result.setWorkTime(workTimeLst.get(0));
			// OUTPUT「選択する勤務種類」と「選択する就業時間帯」を返す
			return result;
		}
		// ドメインモデル「個人勤務日区分別勤務」．平日時．勤務種類コードがINPUT．「勤務種類リスト」にあるかをチェックする
		WorkingConditionItem workingConditionItem = personalLablorCodition.get();
		Optional<WorkTypeCode> opConditionWktypeCD = workingConditionItem.getWorkCategory().getWeekdayTime().getWorkTypeCode();
		List<String> workTypeCDLst = workTypeLst.stream().map(x -> x.getWorkTypeCode().v()).collect(Collectors.toList());
		if(opConditionWktypeCD.isPresent() && workTypeCDLst.contains(opConditionWktypeCD.get().v())) {
			// ドメインモデル「個人勤務日区分別勤務」．平日時．勤務種類コードを選択する(chọn cai loai di lam)
			String conditionWktypeCD = opConditionWktypeCD.get().v();
			WorkType selectedWorkType = workTypeLst.stream().filter(x -> x.getWorkTypeCode().v().equals(conditionWktypeCD))
					.findFirst().orElse(null);
			result.setWorkType(selectedWorkType);
			// 12.マスタ勤務種類、就業時間帯データをチェック
			CheckWorkingInfoResult checkWorkingInfoResult = otherCommonAlgorithm
					.checkWorkingInfo(companyID, conditionWktypeCD, null);
			if(checkWorkingInfoResult.isWkTypeError()) {
				// OUTPUT「選択する勤務種類」と「選択する就業時間帯」をセットする
				result.setWorkType(workTypeLst.get(0));
				result.setWorkTime(workTimeLst.get(0));
				// OUTPUT「選択する勤務種類」と「選択する就業時間帯」を返す
				return result;
			}
		} else {
			// OUTPUT「選択する勤務種類」をセットする
			result.setWorkType(workTypeLst.get(0));
		}
		// ドメインモデル「個人勤務日区分別勤務」．平日時．就業時間帯コードがINPUT．「就業時間帯リスト」にあるかをチェックする
		Optional<WorkTimeCode> opConditionWktimeCD = workingConditionItem.getWorkCategory().getWeekdayTime().getWorkTimeCode();
		List<String> workTimeCDLst = workTimeLst.stream().map(x -> x.getWorktimeCode().v()).collect(Collectors.toList());
		if(!opConditionWktimeCD.isPresent() && !workTimeCDLst.contains(opConditionWktimeCD.get().v())) {
			// OUTPUT「選択する就業時間帯」をセットする
			result.setWorkTime(workTimeLst.get(0));
			// OUTPUT「選択する勤務種類」と「選択する就業時間帯」を返す
			return result;
		}
		// ドメインモデル「個人勤務日区分別勤務」．平日時．就業時間帯コードを選択する(chon cai mui gio lam trong domain trên)
		String conditionWktimeCD = opConditionWktimeCD.get().v();
		WorkTimeSetting selectedWorkTime = workTimeLst.stream().filter(x -> x.getWorktimeCode().v().equals(conditionWktimeCD))
				.findFirst().orElse(null);
		result.setWorkTime(selectedWorkTime);
		// 12.マスタ勤務種類、就業時間帯データをチェック
		CheckWorkingInfoResult checkWorkingInfoResult = otherCommonAlgorithm
				.checkWorkingInfo(companyID, null, conditionWktimeCD);
		if(checkWorkingInfoResult.isWkTimeError()) {
			// OUTPUT「選択する就業時間帯」をセットする
			result.setWorkTime(workTimeLst.get(0));
		}
		// OUTPUT「選択する勤務種類」と「選択する就業時間帯」を返す
		return result;
	}

	@Override
	public ChangeWkTypeTimeOutput changeWorkTypeWorkTime(String companyID, String workTypeCD, Optional<String> workTimeCD,
			AppWorkChangeSet appWorkChangeSet) {
		ChangeWkTypeTimeOutput result = new ChangeWkTypeTimeOutput();
		result.setOpPredetemineTimeSetting(Optional.empty());
		// 就業時間帯の必須チェック
		SetupType setupType = basicScheduleService.checkNeededOfWorkTimeSetting(workTypeCD);
		result.setSetupType(setupType);
		// INPUT．「勤務変更申請設定」をチェックする
		if(workTimeCD.isPresent() && appWorkChangeSet.getInitDisplayWorktime() == InitDisplayWorktimeAtr.FIXEDTIME) {
			// ドメインモデル所定時間設定」を取得する
			Optional<PredetemineTimeSetting> opPredetemineTimeSetting = 
					predetemineTimeSettingRepository.findByWorkTimeCode(companyID, workTimeCD.get());
			result.setOpPredetemineTimeSetting(opPredetemineTimeSetting);
		}
		// 取得した「必須任意不要区分」と「所定時間設定」を返す
		return result;
	}

	@Override
	public AppWorkChangeDispInfo changeAppDate(String companyID, List<GeneralDate> dateLst,
			AppWorkChangeDispInfo appWorkChangeDispInfo) {
		// 共通インタラクション「申請日を変更する」を実行する
		AppDispInfoWithDateOutput appDispInfoWithDateOutput = commonAlgorithm.changeAppDateProcess(
				companyID, 
				dateLst, 
				dateLst.stream().findFirst().get(),
				ApplicationType.WORK_CHANGE_APPLICATION, 
				appWorkChangeDispInfo.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput(),
				appWorkChangeDispInfo.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput());
		// 「勤務変更申請の表示情報」を更新する
		appWorkChangeDispInfo.getAppDispInfoStartupOutput().setAppDispInfoWithDateOutput(appDispInfoWithDateOutput);
		return appWorkChangeDispInfo;
	}

	@Override
	public WorkChangeCheckRegOutput checkBeforeRegister(String companyID, ErrorFlagImport errorFlag, Application_New application,
			AppWorkChange appWorkChange) {
		WorkChangeCheckRegOutput output = new WorkChangeCheckRegOutput();
		// 登録時チェック処理（勤務変更申請）
		this.checkRegisterWorkChange(application, appWorkChange);
		// 休日の申請日を取得する
		List<GeneralDate> lstDateHd = this.checkHoliday(
				application.getEmployeeID(), 
				new DatePeriod(application.getStartDate().get(), application.getEndDate().get()));
		// 登録時チェック処理（全申請共通）
		List<ConfirmMsgOutput> confirmMsgLst = newBeforeRegister.processBeforeRegister_New(
				companyID, 
				EmploymentRootAtr.APPLICATION, 
				false, 
				application, 
				null, 
				errorFlag, 
				lstDateHd);
		// 「確認メッセージリスト」を全てと取得した「休日の申請日<List>」を返す
		output.setConfirmMsgLst(confirmMsgLst);
		output.setHolidayDateLst(lstDateHd);
		return output;
	}

	@Override
	public void checkRegisterWorkChange(Application_New application, AppWorkChange appWorkChange) {
		BundledBusinessException bundledBusinessExceptions = BundledBusinessException.newInstance();
		// アルゴリズム「勤務変更申請就業時間チェックの内容」を実行する
		List<String> errorLst = this.detailWorkHoursCheck(application, appWorkChange);
		if(!CollectionUtil.isEmpty(errorLst)) {
			// 全てのエラーを「エラーリスト」で表示する
			for(String error : errorLst) {
				bundledBusinessExceptions.addMessage(error);
			}
			throw bundledBusinessExceptions;
		}
	}

	@Override
	public List<String> detailWorkHoursCheck(Application_New application, AppWorkChange appWorkChange) {
		List<String> errorLst = new ArrayList<>();
		// INPUT．「申請．申請開始日」と「INPUT．「申請．申請終了日」をチェックする
		if(application.getEndDate().isPresent() && 
				application.getStartDate().get().after(application.getEndDate().get())) {
			// エラーメッセージ(Msg_150)をOUTPUT「エラーリスト」にセットする
			errorLst.add("Msg_150");
		}
		// INPUT．「勤務変更申請．勤務時間開始1」と「INPUT．「勤務変更申請．勤務時間終了1」の大小チェック
		if(appWorkChange.getWorkTimeStart1() > appWorkChange.getWorkTimeEnd1())  {
			// エラーメッセージ(Msg_579)をOUTPUT「エラーリスト」にセットする
			errorLst.add("Msg_579");
		}
		// INPUT．「勤務変更申請．勤務時間開始2」と「INPUT．「勤務変更申請．勤務時間終了2」の大小チェック
		if(appWorkChange.getWorkTimeStart2() > appWorkChange.getWorkTimeEnd2())  {
			// エラーメッセージ(Msg_580)をOUTPUT「エラーリスト」にセットする
			errorLst.add("Msg_580");
		}
		// INPUT．「勤務変更申請．勤務時間終了1」と「INPUT．「勤務変更申請．勤務時間開始2」の大小チェック
		if(appWorkChange.getWorkTimeEnd1() > appWorkChange.getWorkTimeStart2())  {
			// エラーメッセージ(Msg_581)をOUTPUT「エラーリスト」にセットする
			errorLst.add("Msg_581");
		}
		// INPUT．「勤務変更申請．勤務時間開始1」の入力範囲チェック
		if(appWorkChange.getWorkTimeStart1() < 5 * 60) {
			// エラーメッセージ(Msg_307)をOUTPUT「エラーリスト」にセットする
			errorLst.add("Msg_307");
		}
		// INPUT．「勤務変更申請．勤務時間終了1」の入力範囲チェック
		if(appWorkChange.getWorkTimeEnd1() > 29 * 60) {
			// エラーメッセージ(Msg_307)をOUTPUT「エラーリスト」にセットする
			errorLst.add("Msg_307");
		}
		return errorLst.stream().distinct().collect(Collectors.toList());
	}

	@Override
	public List<GeneralDate> checkHoliday(String employeeID, DatePeriod period) {
		String companyID = AppContexts.user().companyId();
		// Output．日付一覧<List>　＝　Empty
		List<GeneralDate> result = new ArrayList<>();
		// 申請期間から休日の申請日を取得する
		result = otherCommonAlgorithm.lstDateIsHoliday(companyID, employeeID, period);

		if (result.size() == period.datesBetween().size()) {
			//日付一覧(output)の件数 > 0
			String dateListString = "";

			for (int i = 0; i < result.size(); i++) {
				if (dateListString != "") {
					dateListString += "、";
				}
				dateListString += result.get(i).toString("yyyy/MM/dd");
			}
			throw new BusinessException("Msg_1459",dateListString);
		}
        return result;
	}

	@Override
	public AppWorkChangeDetailOutput startDetailScreen(String companyID, String appID) {
		AppWorkChangeDetailOutput result = new AppWorkChangeDetailOutput();
		AppWorkChangeDispInfo appWorkChangeDispInfo = new AppWorkChangeDispInfo();
		// 詳細画面起動前申請共通設定を取得する
		AppDispInfoStartupOutput appDispInfoStartupOutput = detailAppCommonSetService.getCommonSetBeforeDetail(companyID, appID);
		// ドメインモデル「勤務変更申請」より取得する (Lấy từ domain 「勤務変更申請」)
		AppWorkChange appWorkChange = appWorkChangeRepository.getAppworkChangeById(companyID, appID).get();
		// ドメインモデル「勤務変更申請設定」より取得する 
		AppWorkChangeSet appWorkChangeSet = appWorkChangeSetRepository.findWorkChangeSetByID(companyID).get();
		// 勤務種類を取得する
		AppEmploymentSetting appEmploymentSetting = appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getEmploymentSet();
		List<WorkType> workTypeLst = this.getWorkTypeLst(appEmploymentSetting);
		// 取得した情報をOUTPUT「勤務変更申請の表示情報」にセットする
		appWorkChangeDispInfo.setAppDispInfoStartupOutput(appDispInfoStartupOutput);
		appWorkChangeDispInfo.setAppWorkChangeSet(appWorkChangeSet);
		appWorkChangeDispInfo.setWorkTypeLst(workTypeLst);
		// 「勤務変更申請の表示情報」と「勤務変更申請」を返す
		result.setAppWorkChangeDispInfo(appWorkChangeDispInfo);
		result.setAppWorkChange(appWorkChange);
		return result;
	}

}
