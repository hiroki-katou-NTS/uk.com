package nts.uk.ctx.at.request.dom.application.gobackdirectly.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.OutputMode;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementDetail;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ActualContentDisplay;
import nts.uk.ctx.at.request.dom.application.common.service.setting.CommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.setting.WorkInfoListOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoWithDateOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.InitWkTypeWkTimeOutput;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.InforGoBackCommonDirectOutput;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.InforWorkGoBackDirectOutput;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.WorkInfo;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSet;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.TargetWorkTypeByApp;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.directgoback.GoBackReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.directgoback.GoBackReflectRepository;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingService;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.internal.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.worktype.DeprecateClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
@Stateless
public class GoBackDirectServiceImp implements GoBackDirectService {

	@Inject
	private WorkTypeRepository workTypeRepository;
	
	@Inject
	private GoBackReflectRepository goBackDirectServiceImp;
	

	@Inject
	private CommonAlgorithm commonAlgorithm;
	
	@Inject
	private GoBackDirectlyRepository goBackDirectlyRepository;
	
	@Inject 
	private WorkTimeSettingService workTimeSettingService;
	
	@Override
	public InforGoBackCommonDirectOutput getDataAlgorithm(
			String companyId,
			List<GeneralDate> dates,
			List<String> sids,
			AppDispInfoStartupOutput appDispInfoStartup) {
		InforGoBackCommonDirectOutput output =  new InforGoBackCommonDirectOutput();
		
		String sid = sids.get(0);
		
		GeneralDate date = CollectionUtil.isEmpty(dates) ? null : dates.get(0);
		
		GeneralDate baseDate = appDispInfoStartup.getAppDispInfoWithDateOutput().getBaseDate();

		AppEmploymentSet appEmployment = null;
		if (appDispInfoStartup.getAppDispInfoWithDateOutput().getOpEmploymentSet().isPresent()) {
			appEmployment = appDispInfoStartup.getAppDispInfoWithDateOutput().getOpEmploymentSet().get();
		}

		List<WorkTimeSetting> lstWts = null;
		if (appDispInfoStartup.getAppDispInfoWithDateOutput().getOpWorkTimeLst().isPresent()) {
			lstWts = appDispInfoStartup.getAppDispInfoWithDateOutput().getOpWorkTimeLst().get();
		}
		// 起動時の申請表示情報を取得する send from client KAF000 or KAFS00 appDispInfoStartup
		
		// 直行直帰申請起動時初期データを取得する
		InforWorkGoBackDirectOutput inforWorkGoBackDirectOutput = this.getInfoWorkGoBackDirect(companyId, sid, date, baseDate, appEmployment, lstWts, appDispInfoStartup);
		if(inforWorkGoBackDirectOutput.getOpErrorMsg().isPresent()) {
			appDispInfoStartup.getAppDispInfoWithDateOutput().getErrorMsgLst().add(inforWorkGoBackDirectOutput.getOpErrorMsg().get());
		}
		
		// ドメインモデル「直行直帰申請の反映」より取得する 
		Optional<GoBackReflect> goBackReflectOp = goBackDirectServiceImp.findByCompany(companyId);
		if (goBackReflectOp.isPresent()) {
			output.setGoBackReflect(goBackReflectOp.get());
		}
		output.setWorkType(inforWorkGoBackDirectOutput.getWorkType());
		output.setWorkTime(inforWorkGoBackDirectOutput.getWorkTime());
		output.setLstWorkType(inforWorkGoBackDirectOutput.getLstWorkType());
		if (appDispInfoStartup.getAppDetailScreenInfo().isPresent()) {
			if (appDispInfoStartup.getAppDetailScreenInfo().get().getOutputMode() == OutputMode.EDITMODE) {
				//新規モード：ドメイン「直行直帰申請」がない。
				// get Object
				output.setGoBackDirectly(goBackDirectlyRepository.find(companyId, appDispInfoStartup.getAppDetailScreenInfo().get().getApplication().getAppID()));
			}
		}
		output.setAppDispInfoStartup(appDispInfoStartup);
		return output;
	}
	
	@Override
	public InforGoBackCommonDirectOutput getDataAlgorithmMobile(String companyId, Optional<List<GeneralDate>> dates,
			Optional<String> sids, AppDispInfoStartupOutput appDispInfoStartup) {
		InforGoBackCommonDirectOutput output =  new InforGoBackCommonDirectOutput();
		String sid = null;
		if (sids.isPresent()) {
			sid = sids.get();
		}
		GeneralDate date = null;
		if (dates.isPresent()) {
			if (!dates.get().isEmpty()) {
				date = dates.get().get(0);
			}
		}
		GeneralDate baseDate = appDispInfoStartup.getAppDispInfoWithDateOutput().getBaseDate();

		AppEmploymentSet appEmployment = null;
		if (appDispInfoStartup.getAppDispInfoWithDateOutput().getOpEmploymentSet().isPresent()) {
			appEmployment = appDispInfoStartup.getAppDispInfoWithDateOutput().getOpEmploymentSet().get();
		}

		List<WorkTimeSetting> lstWts = null;
		if (appDispInfoStartup.getAppDispInfoWithDateOutput().getOpWorkTimeLst().isPresent()) {
			lstWts = appDispInfoStartup.getAppDispInfoWithDateOutput().getOpWorkTimeLst().get();
		}
		// 起動時の申請表示情報を取得する send from client KAF000 or KAFS00 appDispInfoStartup
		
		// 直行直帰申請起動時初期データを取得する
		InforWorkGoBackDirectOutput inforWorkGoBackDirectOutput = this.getInfoWorkGoBackDirect(companyId, sid, date, baseDate, appEmployment, lstWts, appDispInfoStartup);
		if(inforWorkGoBackDirectOutput.getOpErrorMsg().isPresent()) {
			appDispInfoStartup.getAppDispInfoWithDateOutput().getErrorMsgLst().add(inforWorkGoBackDirectOutput.getOpErrorMsg().get());
		}
		
		// ドメインモデル「直行直帰申請の反映」より取得する 
		Optional<GoBackReflect> goBackReflectOp = goBackDirectServiceImp.findByCompany(companyId);
		if (goBackReflectOp.isPresent()) {
			output.setGoBackReflect(goBackReflectOp.get());
		}
		
		// 所定時間帯を取得する
		// ※勤務種類コード　OR　就業時間帯コード　がない場合：　処理を呼ばない
		Boolean isUseTimeZone = StringUtils.isBlank(inforWorkGoBackDirectOutput.getWorkType()) || StringUtils.isBlank(inforWorkGoBackDirectOutput.getWorkTime());
		if (!isUseTimeZone) {
			PredetermineTimeSetForCalc predetermineTimeSetForCalc = workTimeSettingService.getPredeterminedTimezone(companyId, inforWorkGoBackDirectOutput.getWorkTime(), inforWorkGoBackDirectOutput.getWorkType(), null);
			List<TimezoneUse> timezones = predetermineTimeSetForCalc.getTimezones();
			output.setTimezones(timezones);
		}
		output.setWorkType(inforWorkGoBackDirectOutput.getWorkType());
		output.setWorkTime(inforWorkGoBackDirectOutput.getWorkTime());
		output.setLstWorkType(inforWorkGoBackDirectOutput.getLstWorkType());
		if (appDispInfoStartup.getAppDetailScreenInfo().isPresent()) {
			if (appDispInfoStartup.getAppDetailScreenInfo().get().getOutputMode() == OutputMode.EDITMODE) {
				//新規モード：ドメイン「直行直帰申請」がない。
				// get Object
				output.setGoBackDirectly(goBackDirectlyRepository.find(companyId, appDispInfoStartup.getAppDetailScreenInfo().get().getApplication().getAppID()));
			}
		}
		output.setAppDispInfoStartup(appDispInfoStartup);
		return output;
	}

	@Override
	public InforWorkGoBackDirectOutput getInfoWorkGoBackDirect(String companyId, String employeeId, GeneralDate appDate,
			GeneralDate baseDate, AppEmploymentSet appEmployment, List<WorkTimeSetting> lstWts, AppDispInfoStartupOutput appDispInfoStartupOutput) {
		InforWorkGoBackDirectOutput output = new InforWorkGoBackDirectOutput();
		// 起動時勤務種類リストを取得する
		List<WorkType> lstWorkType = this.getWorkTypes(companyId, appEmployment);
		Optional<AchievementDetail> archievementDetail = Optional.empty();
		if (appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().isPresent()){
			if (!CollectionUtil.isEmpty(appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().get())) {
				ActualContentDisplay actualContentDisplay = appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().get().get(0);
				archievementDetail = actualContentDisplay.getOpAchievementDetail();
			}
		}
		// 09_勤務種類就業時間帯の初期選択をセットする
		InitWkTypeWkTimeOutput initWkTypeWkTimeOutput = commonAlgorithm.initWorkTypeWorkTime(
				employeeId,
				baseDate,
				appDate,
				lstWorkType.isEmpty() ? Collections.emptyList() : lstWorkType,
				appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpWorkTimeLst().isPresent() ? appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpWorkTimeLst().get() : Collections.emptyList(),
				archievementDetail.isPresent() ? archievementDetail.get() : null);


		output.setWorkType(initWkTypeWkTimeOutput.getWorkTypeCD());
		output.setWorkTime(initWkTypeWkTimeOutput.getWorkTimeCD());
		output.setLstWorkType(lstWorkType);
		output.setOpErrorMsg(initWkTypeWkTimeOutput.getOpErrorMsg());
		return output;

	}

	@Override
	public List<WorkType> getWorkTypes(String companyID, AppEmploymentSet appEmploymentSet) {
		List<WorkType> result = Collections.emptyList();
		if(appEmploymentSet == null) {
			// ドメインモデル「勤務種類」を取得
			result = workTypeRepository.findNotDeprecated(companyID);
			result =  result.stream().filter(x -> x.getDeprecate() == DeprecateClassification.NotDeprecated && !x.getDailyWork().isHolidayType()).collect(Collectors.toList());
			return result;
		}
		// INPUT．雇用別申請承認設定．申請別対象勤務種類をチェックする
		Optional<TargetWorkTypeByApp> opWorkTypeObj = appEmploymentSet.getTargetWorkTypeByAppLst().stream()
				.filter(x -> x.getAppType() == ApplicationType.GO_RETURN_DIRECTLY_APPLICATION).findFirst();
		if(!opWorkTypeObj.isPresent()) {
			// ドメインモデル「勤務種類」を取得して返す
			result = workTypeRepository.findNotDeprecated(companyID);
			result =  result.stream().filter(x -> x.getDeprecate() == DeprecateClassification.NotDeprecated && !x.getDailyWork().isHolidayType()).collect(Collectors.toList());
			return result;
		}
		if(!opWorkTypeObj.get().isDisplayWorkType() || CollectionUtil.isEmpty(opWorkTypeObj.get().getWorkTypeLst())) {
			// ドメインモデル「勤務種類」を取得して返す
			result = workTypeRepository.findNotDeprecated(companyID);
			result =  result.stream().filter(x -> x.getDeprecate() == DeprecateClassification.NotDeprecated && !x.getDailyWork().isHolidayType()).collect(Collectors.toList());
			return result;
		}
		// INPUT．雇用別申請承認設定．申請別対象勤務種類．勤務種類リストを取得する
		List<String> workTypeCDLst = opWorkTypeObj.get().getWorkTypeLst();
		// ドメインモデル「勤務種類」を取得
		result = workTypeRepository.findNotDeprecatedByListCode(companyID, workTypeCDLst);
		result =  result.stream().filter(x -> x.getDeprecate() == DeprecateClassification.NotDeprecated && !x.getDailyWork().isHolidayType()).collect(Collectors.toList());
		return result;
	}

	@Override
	public InforGoBackCommonDirectOutput getDateChangeAlgorithm(String companyId, List<GeneralDate> dates,
			List<String> sids, InforGoBackCommonDirectOutput inforGoBackCommonDirectOutput) {
		// 申請日を変更する
		// cause KAF000 call service to get AppDispInfoWithDateOutput so do not call this 申請日を変更する
		AppDispInfoWithDateOutput appDispInfoWithDateOutput = inforGoBackCommonDirectOutput.getAppDispInfoStartup().getAppDispInfoWithDateOutput();
		inforGoBackCommonDirectOutput.getAppDispInfoStartup().setAppDispInfoWithDateOutput(appDispInfoWithDateOutput);
		InforWorkGoBackDirectOutput inforWorkGoBackDirectOutput = this.getInfoWorkGoBackDirect(companyId,  sids == null ? null : sids.get(0), dates.get(0),
				inforGoBackCommonDirectOutput.getAppDispInfoStartup().getAppDispInfoWithDateOutput().getBaseDate(),
				inforGoBackCommonDirectOutput.getAppDispInfoStartup().getAppDispInfoWithDateOutput().getOpEmploymentSet().isPresent()
						? inforGoBackCommonDirectOutput.getAppDispInfoStartup().getAppDispInfoWithDateOutput().getOpEmploymentSet().get()
						: null,
						inforGoBackCommonDirectOutput.getAppDispInfoStartup().getAppDispInfoWithDateOutput().getOpWorkTimeLst().isPresent()
						? inforGoBackCommonDirectOutput.getAppDispInfoStartup().getAppDispInfoWithDateOutput().getOpWorkTimeLst().get()
						: null,
						inforGoBackCommonDirectOutput.getAppDispInfoStartup()		
				);
		if(inforWorkGoBackDirectOutput.getOpErrorMsg().isPresent()) {
			appDispInfoWithDateOutput.getErrorMsgLst().add(inforWorkGoBackDirectOutput.getOpErrorMsg().get());
		}
		
		inforGoBackCommonDirectOutput.setWorkType(inforWorkGoBackDirectOutput.getWorkType());
		inforGoBackCommonDirectOutput.setWorkTime(inforWorkGoBackDirectOutput.getWorkTime());
		inforGoBackCommonDirectOutput.setLstWorkType(inforWorkGoBackDirectOutput.getLstWorkType());
		if (inforGoBackCommonDirectOutput.getAppDispInfoStartup().getAppDetailScreenInfo().isPresent()) {
			if (inforGoBackCommonDirectOutput.getAppDispInfoStartup().getAppDetailScreenInfo().get().getOutputMode() == OutputMode.EDITMODE) {
				//新規モード：ドメイン「直行直帰申請」がない。
				inforGoBackCommonDirectOutput.setGoBackDirectly(Optional.empty());
			}
		}
		return inforGoBackCommonDirectOutput;
	}

	@Override
	public InforGoBackCommonDirectOutput getDataDetailAlgorithm(String companyId, String appId, AppDispInfoStartupOutput appDispInfoStartupOutput) {
		InforGoBackCommonDirectOutput output = new InforGoBackCommonDirectOutput();
		// ドメインモデル「直行直帰申請の反映」より取得する
		Optional<GoBackReflect> goBackReflect = goBackDirectServiceImp.findByCompany(companyId);
		if (goBackReflect.isPresent()) {
			output.setGoBackReflect(goBackReflect.get());
		}
		
		// ドメインモデル「直行直帰申請」を取得する
		Optional<GoBackDirectly> goBackDirectly = goBackDirectlyRepository.find(companyId, appId);
		output.setGoBackDirectly(goBackDirectly);
		// 起動時勤務種類リストを取得する
		List<WorkType> lstWorkType = this.getWorkTypes(companyId, appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpEmploymentSet().isPresent() ? appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpEmploymentSet().get() : null);
		output.setLstWorkType(lstWorkType);
		
		// 申請中の勤務種類・就業時間帯を取得する
		String workType = goBackDirectly.flatMap(x -> x.getDataWork()).flatMap(x -> Optional.ofNullable(x.getWorkTypeCode())).map(x -> x.v()).orElse(null);
		Optional<String> workTimeOp = goBackDirectly.flatMap(x -> x.getDataWork()).flatMap(x -> x.getWorkTimeCodeNotNull()).map(x -> x.v());
		WorkInfoListOutput result = commonAlgorithm.getWorkInfoList(
				companyId,
				workType,
				workTimeOp,
				lstWorkType,
				appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpWorkTimeLst().orElse(Collections.emptyList()));
		output.setWorkInfo(Optional.of(new WorkInfo(workType, workTimeOp.orElse(null))));
		
		// 申請中の勤務種類・就業時間帯を取得する
		if (goBackDirectly.isPresent()) {
			Optional<WorkInformation> dataWork = goBackDirectly.get().getDataWork();
			if (dataWork.isPresent()) {
				//直行直帰申請起動時の表示情報．勤務種類初期選択=取得したドメインモデル「直行直帰申請」．勤務情報．勤務種類 
				output.setWorkType(dataWork.get().getWorkTypeCode().v());
				//直行直帰申請起動時の表示情報．就業時間帯初期選択=取得したドメインモデル「直行直帰申請」．勤務情報．就業時間帯
				if (dataWork.get().getWorkTimeCode() != null) {
					output.setWorkTime(dataWork.get().getWorkTimeCode().v());
				}
			}
		}
		output.setAppDispInfoStartup(appDispInfoStartupOutput);
		output.setLstWorkType(result.getWorkTypes());
		output.getAppDispInfoStartup().getAppDispInfoWithDateOutput().setOpWorkTimeLst(Optional.of(result.getWorkTimes()));
		
		
		return output;
	}
	
	@Override
	public InforGoBackCommonDirectOutput getDataDetailAlgorithmMobile(
			String companyId,
			String appId,
			AppDispInfoStartupOutput appDispInfoStartupOutput) {
		InforGoBackCommonDirectOutput output = new InforGoBackCommonDirectOutput();
		// ドメインモデル「直行直帰申請の反映」より取得する
		Optional<GoBackReflect> goBackReflect = goBackDirectServiceImp.findByCompany(companyId);
		if (goBackReflect.isPresent()) {
			output.setGoBackReflect(goBackReflect.get());
		}
		
		// ドメインモデル「直行直帰申請」を取得する
		Optional<GoBackDirectly> goBackDirectly = goBackDirectlyRepository.find(companyId, appId);
		output.setGoBackDirectly(goBackDirectly);
		// 起動時勤務種類リストを取得する
		List<WorkType> lstWorkType = this.getWorkTypes(companyId, appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpEmploymentSet().isPresent() ? appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpEmploymentSet().get() : null);
		output.setLstWorkType(lstWorkType);
		
		// 申請中の勤務種類・就業時間帯を取得する
		String workType = goBackDirectly.flatMap(x -> x.getDataWork()).flatMap(x -> Optional.ofNullable(x.getWorkTypeCode())).map(x -> x.v()).orElse(null);
		Optional<String> workTimeOp = goBackDirectly.flatMap(x -> x.getDataWork()).flatMap(x -> x.getWorkTimeCodeNotNull()).map(x -> x.v());
		WorkInfoListOutput result = commonAlgorithm.getWorkInfoList(
				companyId,
				workType,
				workTimeOp,
				lstWorkType,
				appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpWorkTimeLst().orElse(Collections.emptyList()));
		output.setWorkInfo(Optional.of(new WorkInfo(workType, workTimeOp.orElse(null))));
		
		// 申請中の勤務種類・就業時間帯を取得する
		if (goBackDirectly.isPresent()) {
			Optional<WorkInformation> dataWork = goBackDirectly.get().getDataWork();
			if (dataWork.isPresent()) {
				//直行直帰申請起動時の表示情報．勤務種類初期選択=取得したドメインモデル「直行直帰申請」．勤務情報．勤務種類 
				output.setWorkType(dataWork.get().getWorkTypeCode().v());
				//直行直帰申請起動時の表示情報．就業時間帯初期選択=取得したドメインモデル「直行直帰申請」．勤務情報．就業時間帯
				if (dataWork.get().getWorkTimeCode() != null) {
					output.setWorkTime(dataWork.get().getWorkTimeCode().v());
				}
			}
		}
		output.setAppDispInfoStartup(appDispInfoStartupOutput);
		output.setLstWorkType(result.getWorkTypes());
		output.getAppDispInfoStartup().getAppDispInfoWithDateOutput().setOpWorkTimeLst(Optional.of(result.getWorkTimes()));
		
		// 所定時間帯を取得する
		// ※勤務種類コード　OR　就業時間帯コード　がない場合：　処理を呼ばない
		Boolean isUseTimeZone = StringUtils.isBlank(output.getWorkType()) || StringUtils.isBlank(output.getWorkTime());
		if (!isUseTimeZone) {
			PredetermineTimeSetForCalc predetermineTimeSetForCalc = workTimeSettingService.getPredeterminedTimezone(companyId, output.getWorkTime(), output.getWorkType(), null);
			List<TimezoneUse> timezones = predetermineTimeSetForCalc.getTimezones();
			output.setTimezones(timezones);
		}
		
		return output;
	}

	@Override
	public InforGoBackCommonDirectOutput getDateChangeMobileAlgorithm(String companyId, List<GeneralDate> dates,
			List<String> sids, InforGoBackCommonDirectOutput inforGoBackCommonDirectOutput) {
		AppDispInfoWithDateOutput appDispInfoWithDateOutput = commonAlgorithm.getAppDispInfoWithDate(companyId,
				ApplicationType.GO_RETURN_DIRECTLY_APPLICATION,
				dates,
				inforGoBackCommonDirectOutput.getAppDispInfoStartup().getAppDispInfoNoDateOutput(),
				true,
				Optional.empty());
		inforGoBackCommonDirectOutput.getAppDispInfoStartup().setAppDispInfoWithDateOutput(appDispInfoWithDateOutput);
		InforWorkGoBackDirectOutput inforWorkGoBackDirectOutput = this.getInfoWorkGoBackDirect(companyId,  sids == null ? null : sids.get(0), dates.get(0),
				inforGoBackCommonDirectOutput.getAppDispInfoStartup().getAppDispInfoWithDateOutput().getBaseDate(),
				inforGoBackCommonDirectOutput.getAppDispInfoStartup().getAppDispInfoWithDateOutput().getOpEmploymentSet().isPresent()
						? inforGoBackCommonDirectOutput.getAppDispInfoStartup().getAppDispInfoWithDateOutput().getOpEmploymentSet().get()
						: null,
						inforGoBackCommonDirectOutput.getAppDispInfoStartup().getAppDispInfoWithDateOutput().getOpWorkTimeLst().isPresent()
						? inforGoBackCommonDirectOutput.getAppDispInfoStartup().getAppDispInfoWithDateOutput().getOpWorkTimeLst().get()
						: null,
						inforGoBackCommonDirectOutput.getAppDispInfoStartup()		
				);
		if(inforWorkGoBackDirectOutput.getOpErrorMsg().isPresent()) {
			appDispInfoWithDateOutput.getErrorMsgLst().add(inforWorkGoBackDirectOutput.getOpErrorMsg().get());
		}
		
		inforGoBackCommonDirectOutput.setWorkType(inforWorkGoBackDirectOutput.getWorkType());
		inforGoBackCommonDirectOutput.setWorkTime(inforWorkGoBackDirectOutput.getWorkTime());
		inforGoBackCommonDirectOutput.setLstWorkType(inforWorkGoBackDirectOutput.getLstWorkType());
		if (inforGoBackCommonDirectOutput.getAppDispInfoStartup().getAppDetailScreenInfo().isPresent()) {
			if (inforGoBackCommonDirectOutput.getAppDispInfoStartup().getAppDetailScreenInfo().get().getOutputMode() == OutputMode.EDITMODE) {
				//新規モード：ドメイン「直行直帰申請」がない。
				inforGoBackCommonDirectOutput.setGoBackDirectly(Optional.empty());
			}
		}
		// 所定時間帯を取得する
				// ※勤務種類コード　OR　就業時間帯コード　がない場合：　処理を呼ばない
				Boolean isUseTimeZone = StringUtils.isBlank(inforWorkGoBackDirectOutput.getWorkType()) || StringUtils.isBlank(inforWorkGoBackDirectOutput.getWorkTime());
				if (!isUseTimeZone) {
					PredetermineTimeSetForCalc predetermineTimeSetForCalc = workTimeSettingService.getPredeterminedTimezone(companyId, inforWorkGoBackDirectOutput.getWorkTime(), inforWorkGoBackDirectOutput.getWorkType(), null);
					List<TimezoneUse> timezones = predetermineTimeSetForCalc.getTimezones();
					inforGoBackCommonDirectOutput.setTimezones(timezones);
				}
		return inforGoBackCommonDirectOutput;
	}

	

	

}
