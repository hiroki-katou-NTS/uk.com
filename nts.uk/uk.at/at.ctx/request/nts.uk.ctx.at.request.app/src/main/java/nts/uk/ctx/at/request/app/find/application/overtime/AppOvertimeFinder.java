package nts.uk.ctx.at.request.app.find.application.overtime;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.CheckBeforeOutputDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.DetailOutputDto;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReasonForReversion;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.CheckBeforeOutput;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.ICommonAlgorithmOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.service.DisplayInfoOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.service.DisplayInfoOverTimeMobile;
import nts.uk.ctx.at.request.dom.application.overtime.service.OvertimeService;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.request.dom.application.overtime.service.OvertimeSixProcess;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.FlexExcessUseSetAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.OvertimeAppSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.divergencereason.DivergenceReason;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
public class AppOvertimeFinder {
	public static final String PATTERN_DATE = "yyyy/MM/dd";
	
	@Inject
	private OvertimeService overtimeService;
	
	@Inject
	private ICommonAlgorithmOverTime commonAlgorithmOverTime;
//	private EmployeeRequestAdapter employeeAdapter;
	
	@Inject
	private WorkTimeSettingRepository workTimeRepository;
	
	@Inject
	private WorkTypeRepository workTypeRepository;
	
	@Inject
	private OvertimeRestAppCommonSetRepository overtimeRestAppCommonSetRepository;
	
//	@Inject
//	private AppTypeDiscreteSettingRepository appTypeDiscreteSettingRepository;
	
	@Inject
	private OvertimeWorkFrameRepository overtimeFrameRepository;
	
//	@Inject
//	private IOvertimePreProcess iOvertimePreProcess;
//	
//	@Inject
//	private OvertimeRepository overtimeRepository;
	@Inject
	private OvertimeSixProcess overtimeSixProcess;
//	@Inject
//	private OvertimeFourProcess overtimeFourProcess;
//	@Inject
//	private OtherCommonAlgorithm otherCommonAlgorithm;
//	@Inject
//	private DailyAttendanceTimeCaculation dailyAttendanceTimeCaculation;
	@Inject
	private OvertimeAppSetRepository appOvertimeSettingRepository;
	@Inject
	private WorkingConditionItemRepository workingConditionItemRepository;
	
//	@Inject
//	private AtEmployeeAdapter atEmployeeAdapter;
	
//	@Inject
//	private CommonOvertimeHoliday commonOvertimeHoliday;

	@Inject
	private PredetemineTimeSettingRepository predetemineTimeRepo;
	
//	@Inject
//	private PreActualColorCheck preActualColorCheck;
	
	/**
	 * Refactor5
	 * start at A
	 * @param paramOverTimeStart
	 * @return
	 */
	public DisplayInfoOverTimeDto start(ParamOverTimeStart param) {
		DisplayInfoOverTime output;
		String companyId = param.companyId;
		Optional<GeneralDate> dateOp = Optional.empty();
		if (StringUtils.isNotBlank(param.dateOp)) {
			dateOp = Optional.of(GeneralDate.fromString(param.dateOp, PATTERN_DATE));	
		}
		
		OvertimeAppAtr overtimeAppAtr = EnumAdaptor.valueOf(param.overtimeAppAtr, OvertimeAppAtr.class);
		AppDispInfoStartupOutput appDispInfoStartupOutput = param.appDispInfoStartupDto.toDomain();
		Optional<Integer> startTimeSPR = Optional.ofNullable(param.startTimeSPR);
		
		Optional<Integer> endTimeSPR = Optional.ofNullable(param.endTimeSPR);
		Boolean isProxy = param.isProxy;

		output = overtimeService.startA(
				companyId,
				dateOp,
				overtimeAppAtr,
				appDispInfoStartupOutput,
				startTimeSPR,
				endTimeSPR,
				isProxy
				);
		return DisplayInfoOverTimeDto.fromDomain(output);
	}
	public DisplayInfoOverTimeDto changeDate(ParamOverTimeChangeDate param) {
		DisplayInfoOverTime output = new DisplayInfoOverTime();
		String companyId = param.companyId;
		Optional<GeneralDate> dateOp = Optional.empty();
		if (StringUtils.isNotBlank(param.dateOp)) {
			dateOp = Optional.of(GeneralDate.fromString(param.dateOp, PATTERN_DATE));	
		}
		
		AppDispInfoStartupOutput appDispInfoStartupOutput = param.appDispInfoStartupDto.toDomain();
		Optional<Integer> startTimeSPR = Optional.ofNullable(param.startTimeSPR);
		
		Optional<Integer> endTimeSPR = Optional.ofNullable(param.endTimeSPR);

		output = overtimeService.changeDate(
				companyId,
				param.employeeId,
				dateOp,
				EnumAdaptor.valueOf(param.overtimeAppAtr, OvertimeAppAtr.class),
				appDispInfoStartupOutput,
				startTimeSPR,
				endTimeSPR,
				param.overTimeAppSet.toDomain(companyId),
				CollectionUtil.isEmpty(param.worktypes) ? Collections.emptyList() : param.worktypes
						.stream()
						.map(x -> x.toDomain(param.companyId))
						.collect(Collectors.toList())
				);
		return DisplayInfoOverTimeDto.fromDomainChangeDate(output);
	}
	
	public DisplayInfoOverTimeDto selectWorkInfo(ParamSelectWork param) {
		DisplayInfoOverTime output;
		Optional<GeneralDate> dateOp = Optional.empty();
		
		if (StringUtils.isNotBlank(param.date)) {
			dateOp = Optional.of(GeneralDate.fromString(param.date, PATTERN_DATE));	
		}
		
		Optional<Integer> startTimeSPR = Optional.ofNullable(param.startSPR);
		
		Optional<Integer> endTimeSPR = Optional.ofNullable(param.endSPR);
		
		output = overtimeService.selectWorkInfo(
				param.companyId,
				param.employeeId,
				dateOp,
				new WorkTypeCode(param.workType),
				new WorkTimeCode(param.workTime),
				startTimeSPR,
				endTimeSPR,
				param.appDispInfoStartupDto.toDomain(),
				param.overtimeAppSet.toDomain(param.companyId));
		
		return DisplayInfoOverTimeDto.fromDomainChangeDate(output);
	}
	
	public DisplayInfoOverTimeDto calculate(ParamCalculation param) {
		String companyId = param.companyId;
		Optional<GeneralDate> dateOp = Optional.empty();
		if (StringUtils.isNotBlank(param.dateOp)) {
			dateOp = Optional.of(GeneralDate.fromString(param.dateOp, PATTERN_DATE));	
		}
		
		DisplayInfoOverTime output = overtimeService.calculate(
				companyId,
				param.employeeId,
				dateOp,
				EnumAdaptor.valueOf(param.prePostInitAtr, PrePostAtr.class),
				param.overtimeLeaveAppCommonSet.toDomain(),
				param.advanceApplicationTime == null ? null : param.advanceApplicationTime.toDomain(),
				param.achieveApplicationTime == null ? null : param.achieveApplicationTime.toDomain(),
				param.workContent.toDomain());
		
		
		return DisplayInfoOverTimeDto.fromDomainCalculation(output);
	}
	
	public CheckBeforeOutputDto checkBeforeRegister(ParamCheckBeforeRegister param) {
		CheckBeforeOutput output = null;
		DisplayInfoOverTime displayInfoOverTime = param.displayInfoOverTime.toDomain();
		Application application = param.appOverTime.application.toDomain();
		AppOverTime appOverTime = param.appOverTime.toDomain();
		if (appOverTime.getDetailOverTimeOp().isPresent()) {
			appOverTime.getDetailOverTimeOp().get().setAppId(application.getAppID());
		}
		appOverTime.setApplication(application);
		output = overtimeService.checkErrorRegister(
				param.require,
				param.companyId,
				displayInfoOverTime,
				appOverTime);
		return CheckBeforeOutputDto.fromDomain(output);
	}
	
	public CheckBeforeOutputDto checkBeforeUpdate(ParamCheckBeforeUpdate param) {
		CheckBeforeOutput output = null;
		DisplayInfoOverTime displayInfoOverTime = param.displayInfoOverTime.toDomain();
		Application application = param.appOverTime.application.toDomain(param.displayInfoOverTime.appDispInfoStartup.getAppDetailScreenInfo().getApplication());
		AppOverTime appOverTime = param.appOverTime.toDomain();
		appOverTime.setApplication(application);
		output = overtimeService.checkBeforeUpdate(
				param.require,
				param.companyId,
				appOverTime,
				displayInfoOverTime);
		return CheckBeforeOutputDto.fromDomain(output);
	}
	
	public DetailOutputDto getDetail(ParamDetail param) {
		
		return DetailOutputDto.fromDomain(overtimeService.getDetailData(
				param.companyId,
				param.appId,
				param.appDispInfoStartup.toDomain()));
	}
	
	public BreakTimeZoneSettingDto getBreakTime(ParamBreakTime param) {
		return BreakTimeZoneSettingDto.fromDomain(commonAlgorithmOverTime.selectWorkTypeAndTime(
				param.companyId,
				new WorkTypeCode(param.workTypeCode),
				new WorkTimeCode(param.workTimeCode),
				param.startTime == null ? Optional.empty() : Optional.of(new TimeWithDayAttr(param.startTime)),
				param.endTime == null ? Optional.empty() : Optional.of(new TimeWithDayAttr(param.endTime)),
				CollectionUtil.isEmpty(param.actualContentDisplayDtos) ? Optional.empty() : 
					(param.actualContentDisplayDtos.get(0).getOpAchievementDetail() == null ?
							Optional.empty() : Optional.ofNullable(param.actualContentDisplayDtos.get(0).getOpAchievementDetail().toDomain()))
				));
	}	
	/**
	 * フレックス時間を表示するかチェック
	 * 
	 * @param baseDate
	 * @param employeeID
	 * @return フレックス時間を表示する区分
	 */
	public boolean flexDisplayCheck(GeneralDate baseDate, String employeeID) {
//		if (appOvertimeSettingRepository.getAppOver().get().getFlexJExcessUseSetAtr().equals(FlexExcessUseSetAtr.DISPLAY)) {
//			Optional<WorkingConditionItem> personalLablorCodition = workingConditionItemRepository.getBySidAndStandardDate(employeeID, baseDate);
//			if (personalLablorCodition.isPresent()) {
//				if (personalLablorCodition.get().getLaborSystem().isFlexTimeWork()) {
//					return true;
//				} else {
//					return false;
//				}
//			} else {
//				return false;
//			}
//		} else if (appOvertimeSettingRepository.getAppOver().get().getFlexJExcessUseSetAtr().equals(FlexExcessUseSetAtr.ALWAYSDISPLAY)) {
//			return true;
//		} else {
//			return false;
//		}
		return true;
	}
	
	
//	public Application createApplication(ApplicationDto application) {
//		
//		return Application.createFromNew(
//				EnumAdaptor.valueOf(application.getPrePostAtr(), PrePostAtr.class),
//				application.getEmployeeID(),
//				ApplicationType.OVER_TIME_APPLICATION,
//				new ApplicationDate(GeneralDate.fromString(application.getAppDate(), PATTERN_DATE)),
//				application.getEnteredPerson(),
//				application.getOpStampRequestMode() == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(application.getOpStampRequestMode(), StampRequestMode.class)),
//				application.getOpReversionReason() == null ? Optional.empty() : Optional.of(new ReasonForReversion(application.getOpReversionReason())),
//				application.getOpAppStartDate() == null ? Optional.empty() : Optional.of(new ApplicationDate(GeneralDate.fromString(application.getOpAppStartDate(), PATTERN_DATE))),
//				application.getOpAppEndDate() == null ? Optional.empty() : Optional.of(new ApplicationDate(GeneralDate.fromString(application.getOpAppEndDate(), PATTERN_DATE))),
//				application.getOpAppReason() == null ? Optional.empty() : Optional.of(new AppReason(application.getOpAppReason())),
//				application.getOpAppStandardReasonCD() == null ? Optional.empty() : Optional.of(new AppStandardReasonCode(application.getOpAppStandardReasonCD())));
//	}
	
	/**
	 * Mobile
	 */
	public DisplayInfoOverTimeMobileDto startMobile(ParamStartMobile param) {
		Optional<GeneralDate> dateOp = Optional.empty();
		if (StringUtils.isNotBlank(param.dateOptional)) {
			dateOp = Optional.of(GeneralDate.fromString(param.dateOptional, PATTERN_DATE));	
		}
		DisplayInfoOverTimeMobile output = overtimeService.startMobile(
				param.mode,
				param.companyId,
				StringUtils.isBlank(param.employeeIdOptional) ? Optional.empty() : Optional.of(param.employeeIdOptional),
				dateOp,
				param.disOptional == null ? Optional.empty() : Optional.of(param.disOptional.toDomain()),
				param.appOptional == null ? Optional.empty() : Optional.of(param.appOptional.toDomain()),
				param.appDispInfoStartupOutput.toDomain(),
				EnumAdaptor.valueOf(param.overtimeAppAtr, OvertimeAppAtr.class));
		return DisplayInfoOverTimeMobileDto.fromDomain(output);
	}
	
	public DisplayInfoOverTimeDto changeDateMobile(ParamChangeDateMobile param) {
		GeneralDate date = null;
		if (StringUtils.isNotBlank(param.date)) {
			date = GeneralDate.fromString(param.date, PATTERN_DATE);
		}
		DisplayInfoOverTime output = overtimeService.changeDateMobile(
				param.companyId,
				date,
				param.displayInfoOverTime.toDomain());
		return DisplayInfoOverTimeDto.fromDomain(output);
	}
}
