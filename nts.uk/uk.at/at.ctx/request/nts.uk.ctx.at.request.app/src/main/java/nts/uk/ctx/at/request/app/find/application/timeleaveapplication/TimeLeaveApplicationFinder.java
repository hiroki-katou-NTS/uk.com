package nts.uk.ctx.at.request.app.find.application.timeleaveapplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.app.find.application.common.service.other.output.AchievementDetailDto;
import nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto.OutingTimeZoneDto;
import nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto.TimeLeaveAppDetailDto;
import nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto.TimeLeaveAppDisplayInfoDto;
import nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto.TimeLeaveManagement;
import nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto.TimeLeaveRemaining;
import nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto.TimeZoneDto;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime.ChildCareTimeZoneExport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime.DailyAttendanceTimeCaculation;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime.DailyAttendanceTimeCaculationImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime.OutingTimeZoneExport;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.NewBeforeRegister;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeDigestAppType;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeLeaveApplication;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeLeaveApplicationRepository;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.output.TimeLeaveApplicationOutput;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.output.TimeVacationManagementOutput;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.output.TimeVacationRemainingOutput;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.service.TimeLeaveApplicationService;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.RecordDate;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;
import nts.uk.ctx.at.shared.app.find.scherec.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveAppReflectDto;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.TimeDigestionParam;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveApplicationReflect;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
public class TimeLeaveApplicationFinder {

    @Inject
    private NewBeforeRegister processBeforeRegister;

    @Inject
    private TimeLeaveApplicationService timeLeaveApplicationService;

    @Inject
    private WorkingConditionRepository workingConditionRepo;

    @Inject
    private TimeLeaveApplicationRepository timeLeaveApplicationRepository;

    @Inject
    private DailyAttendanceTimeCaculation dailyAttendanceTimeCaculation;

    @Inject
    private DetailBeforeUpdate detailBeforeProcessRegisterService;

    @Inject
    private PredetemineTimeSettingRepository predetemineTimeSettingRepo;

    /**
     * ??????????????????????????????????????????
     * ??????????????????????????????????????????
     * @param params (??????ID, ??????????????????)
     * @return
     */
    public TimeLeaveAppDisplayInfoDto initTimeLeaveApplication(StartProcessTimeLeaveParams params) {
        TimeLeaveAppDisplayInfoDto initData = new TimeLeaveAppDisplayInfoDto();
        String companyId = AppContexts.user().companyId();
        String employeeId;
        GeneralDate baseDate;
        if (!StringUtils.isEmpty(params.getAppId())) {
            //??????????????????????????????????????????????????????????????????
            Optional<TimeLeaveApplication> timeLeaveApplication = timeLeaveApplicationRepository.findById(companyId, params.getAppId());
            if (!timeLeaveApplication.isPresent())
                throw new BusinessException(new RawErrorMessage("Application not found!"));
            employeeId = timeLeaveApplication.get().getEmployeeID();
            baseDate = timeLeaveApplication.get().getAppDate().getApplicationDate();
            initData.setDetails(timeLeaveApplication.get().getLeaveApplicationDetails().stream().map(TimeLeaveAppDetailDto::fromDomain).collect(Collectors.toList()));
        } else {
            employeeId = params.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().getEmployeeInfoLst().get(0).getSid();
            baseDate = GeneralDate.fromString(params.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getBaseDate(), "yyyy/MM/dd");
        }

        // ???????????????????????????????????????
        TimeLeaveApplicationReflect reflectSetting = timeLeaveApplicationService.getTimeLeaveAppReflectSetting(companyId);

        // ?????????????????????????????????
        TimeVacationManagementOutput timeVacationManagement = timeLeaveApplicationService.getTimeLeaveManagement(companyId, employeeId, baseDate, reflectSetting.getCondition());
        TimeVacationRemainingOutput timeVacationRemaining = timeLeaveApplicationService.getTimeLeaveRemaining(companyId, employeeId, baseDate, timeVacationManagement);

        // ????????????????????????????????????
        Optional<WorkingCondition> workingCondition = workingConditionRepo.getBySidAndStandardDate(companyId, employeeId, baseDate);
        if (!workingCondition.isPresent())
            throw new BusinessException("Msg_430");
        Optional<WorkingConditionItem> workingConditionItem = workingConditionRepo.getWorkingConditionItem(workingCondition.get().getDateHistoryItem().get(0).identifier());
        if (!workingConditionItem.isPresent())
            throw new BusinessException("Msg_430");

        // ?????????????????????OUTPUT?????????????????????
        initData.setAppDispInfoStartupOutput(params.getAppDispInfoStartupOutput());
        initData.setReflectSetting(TimeLeaveAppReflectDto.fromDomain(reflectSetting));
        initData.setTimeLeaveManagement(TimeLeaveManagement.fromOutput(timeVacationManagement));
        initData.setTimeLeaveRemaining(TimeLeaveRemaining.fromOutput(timeVacationRemaining));

        // ???????????????????????????????????????
//        output = timeLeaveApplicationService.getSpecialLeaveRemainingInfo(
//                companyId,
//                output.getTimeVacationManagement().getTimeSpecialLeaveMng().getListSpecialFrame().stream().findFirst().map(SpecialHolidayFrame::getSpecialHdFrameNo),
//                output
//        );
        //?????????????????????????????????????????????????????????????????????>0:00??????????????????????????????
//        if (timeLeaveApplicationDto.getTimeDigestAppType() == TimeDigestAppType.USE_COMBINATION.value) {
//            //???????????????????????????
//            CalculationResult calculationResult = calculateApplicationTime(baseDate, timeLeaveAppDisplayInfo, new ArrayList<>(), new ArrayList<>());
//
//            startProcessTimeLeaveAppDto.setCalculationResult(calculationResult);
//        }

        return initData;
    }

    /**
     * ????????????????????????
     *
     * @param params
     * @return
     */
    public TimeLeaveAppDisplayInfoDto changeApplyDate(ChangeAppDateParams params) {
        String companyId = AppContexts.user().companyId();

        // ???????????????????????????????????????????????????
        boolean condition = params.getAppDisplayInfo().getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getOpActualContentDisplayLst() == null
                || params.getAppDisplayInfo().getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().isEmpty()
                || params.getAppDisplayInfo().getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().get(0).getOpAchievementDetail() == null
                || StringUtils.isEmpty(params.getAppDisplayInfo().getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().get(0).getOpAchievementDetail().getWorkTypeCD());
        if(!condition) {
	        // ??????????????????????????????????????????????????????
	        if (params.getAppDisplayInfo().getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().getApplicationSetting().getRecordDate() == RecordDate.SYSTEM_DATE.value) {
	            return params.getAppDisplayInfo();
	        }
        }
        // ????????????????????????????????????
        Optional<WorkingCondition> workingCondition = workingConditionRepo.getBySidAndStandardDate(
                companyId,
                params.getAppDisplayInfo().getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().getEmployeeInfoLst().get(0).getSid(),
                params.getAppDate()
        );
        if (!workingCondition.isPresent())
            throw new BusinessException("Msg_430");
        Optional<WorkingConditionItem> workingConditionItem = workingConditionRepo.getWorkingConditionItem(workingCondition.get().getDateHistoryItem().get(0).identifier());
        if (!workingConditionItem.isPresent())
            throw new BusinessException("Msg_430");

        // ??????????????????????????????????????????
        TimeVacationManagementOutput timeVacationManagement = timeLeaveApplicationService.getTimeLeaveManagement(
                companyId,
                params.getAppDisplayInfo().getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().getEmployeeInfoLst().get(0).getSid(),
                params.getAppDate(),
                TimeLeaveAppReflectDto.toDomain(params.getAppDisplayInfo().getReflectSetting()).getCondition()
        );

        params.getAppDisplayInfo().setTimeLeaveManagement(TimeLeaveManagement.fromOutput(timeVacationManagement));
        return params.getAppDisplayInfo();
    }

    /**
     * ??????????????????????????????
     * @param params
     * @return
     */
    public TimeLeaveAppDisplayInfoDto changeSpecialLeaveFrame(ChangeSpecialLeaveFrameParams params) {
        String companyId = AppContexts.user().companyId();
        return TimeLeaveAppDisplayInfoDto.fromOutput(
                timeLeaveApplicationService.getSpecialLeaveRemainingInfo(
                        companyId,
                        Optional.ofNullable(params.getSpecialLeaveFrameNo()),
                        TimeLeaveAppDisplayInfoDto.mappingData(params.getTimeLeaveAppDisplayInfo())
                )
        );
    }

    /**
     * KAF012 : ???????????????????????????
     */
    public CalculationResultDto calculateApplicationTime(Integer timeLeaveType, GeneralDate baseDate, TimeLeaveAppDisplayInfoDto info, List<TimeZoneDto> lstTimeZone, List<OutingTimeZoneDto> lstOutingTimeZone) {
        String employeeId = info.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().getEmployeeInfoLst().get(0).getSid();
        AchievementDetailDto achievementDetailDto = CollectionUtil.isEmpty(info.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getOpActualContentDisplayLst())
                ? null
                : info.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().get(0).getOpAchievementDetail();
        Map<Integer, TimeZone> mapTimeZone = lstTimeZone.stream()
                .filter(i -> i.getStartTime() != null && i.getEndTime() != null)
                .collect(Collectors.toMap(TimeZoneDto::getWorkNo, i -> new TimeZone(new TimeWithDayAttr(i.getStartTime()), new TimeWithDayAttr(i.getEndTime()))));
        if (achievementDetailDto != null && achievementDetailDto.getWorkTimeCD() != null) {
            // 2????????????????????????????????????
            Optional<PredetemineTimeSetting> timeSetting = predetemineTimeSettingRepo.findByWorkTimeCode(
                    AppContexts.user().companyId(),
                    info.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().get(0).getOpAchievementDetail().getWorkTimeCD()
            );
            if (!timeSetting.isPresent() || !timeSetting.get().checkTwoTimesWork()){
                if (mapTimeZone.get(2) != null) mapTimeZone.remove(2);
            }
        } else {
            if (mapTimeZone.get(2) != null) mapTimeZone.remove(2);
        }
        // 1?????????????????????????????????
        DailyAttendanceTimeCaculationImport calcImport = dailyAttendanceTimeCaculation.getCalculation(
                employeeId,
                baseDate,
                achievementDetailDto == null ? null : achievementDetailDto.getWorkTypeCD(),
                achievementDetailDto == null ? null : achievementDetailDto.getWorkTimeCD(),
                mapTimeZone,
                achievementDetailDto != null ? achievementDetailDto.getBreakTimeSheets().stream().map(x -> x.getStartTime()).collect(Collectors.toList()) : new ArrayList<Integer>(),
                achievementDetailDto != null ? achievementDetailDto.getBreakTimeSheets().stream().map(x -> x.getEndTime()).collect(Collectors.toList()) : new ArrayList<Integer>(),
                lstOutingTimeZone.stream().map(i -> new OutingTimeZoneExport(
                        i.getOutingAtr() == 4 ? GoingOutReason.PRIVATE.value : GoingOutReason.UNION.value,
                        i.getStartTime(),
                        i.getEndTime()
                )).collect(Collectors.toList()),
                achievementDetailDto == null ? Collections.emptyList() : achievementDetailDto.getShortWorkTimeLst().stream().map(i -> new ChildCareTimeZoneExport(
                        i.getChildCareAttr(),
                        i.getStartTime(),
                        i.getEndTime()
                )).collect(Collectors.toList())
        );

        // ????????????????????????????????????????????????OUTPUT??????????????????
        CalculationResultDto calculationResult = new CalculationResultDto();
        calculationResult.setTimeBeforeWork1(calcImport.getLateTime1().v());
        calculationResult.setTimeAfterWork1(calcImport.getEarlyLeaveTime1().v());
        calculationResult.setTimeBeforeWork2(calcImport.getLateTime2().v());
        calculationResult.setTimeAfterWork2(calcImport.getEarlyLeaveTime2().v());
        calculationResult.setPrivateOutingTime(calcImport.getPrivateOutingTime().v());
        calculationResult.setUnionOutingTime(calcImport.getUnionOutingTime().v());

        TimeDigestAppType leaveType = EnumAdaptor.valueOf(timeLeaveType, TimeDigestAppType.class);
        if (leaveType == TimeDigestAppType.USE_COMBINATION)
            return calculationResult;
        else {
            // ?????????????????????????????????????????????????????????
            TimeVacationManagementOutput mng = TimeLeaveManagement.setDtaOutput(info.getTimeLeaveManagement());
            TimeDigestiveUnit unit;
            switch (leaveType) {
                case TIME_OFF:
                    unit = mng.getTimeAllowanceManagement().getTimeBaseRestingUnit();
                    break;
                case TIME_ANNUAL_LEAVE:
                    unit = mng.getTimeAnnualLeaveManagement().getTimeAnnualLeaveUnit();
                    break;
                case CHILD_NURSING_TIME:
                    unit = mng.getChildNursingManagement().getTimeChildDigestiveUnit();
                    break;
                case NURSING_TIME:
                    unit = mng.getChildNursingManagement().getTimeDigestiveUnit();
                    break;
                case SIXTY_H_OVERTIME:
                    unit = mng.getSupHolidayManagement().getSuper60HDigestion();
                    break;
                case TIME_SPECIAL_VACATION:
                    unit = mng.getTimeSpecialLeaveMng().getTimeSpecialLeaveUnit();
                    break;
                default:
                    unit = null;
                    break;
            }
            if (unit != null) {
                switch (unit) {
                    case OneMinute:
                        break;
                    case FifteenMinute:
                        if (calculationResult.getTimeBeforeWork1() % 15 != 0)
                            calculationResult.setTimeBeforeWork1(roundUpTime(calculationResult.getTimeBeforeWork1(), 15));
                        if (calculationResult.getTimeAfterWork1() % 15 != 0)
                            calculationResult.setTimeAfterWork1(roundUpTime(calculationResult.getTimeAfterWork1(), 15));
                        if (calculationResult.getTimeBeforeWork2() % 15 != 0)
                            calculationResult.setTimeBeforeWork2(roundUpTime(calculationResult.getTimeBeforeWork2(), 15));
                        if (calculationResult.getTimeAfterWork2() % 15 != 0)
                            calculationResult.setTimeAfterWork2(roundUpTime(calculationResult.getTimeAfterWork2(), 15));
                        if (calculationResult.getPrivateOutingTime() % 15 != 0)
                            calculationResult.setPrivateOutingTime(roundUpTime(calculationResult.getPrivateOutingTime(), 15));
                        if (calculationResult.getUnionOutingTime() % 15 != 0)
                            calculationResult.setUnionOutingTime(roundUpTime(calculationResult.getUnionOutingTime(), 15));
                        break;
                    case ThirtyMinute:
                        if (calculationResult.getTimeBeforeWork1() % 30 != 0)
                            calculationResult.setTimeBeforeWork1(roundUpTime(calculationResult.getTimeBeforeWork1(), 30));
                        if (calculationResult.getTimeAfterWork1() % 30 != 0)
                            calculationResult.setTimeAfterWork1(roundUpTime(calculationResult.getTimeAfterWork1(), 30));
                        if (calculationResult.getTimeBeforeWork2() % 30 != 0)
                            calculationResult.setTimeBeforeWork2(roundUpTime(calculationResult.getTimeBeforeWork2(), 30));
                        if (calculationResult.getTimeAfterWork2() % 30 != 0)
                            calculationResult.setTimeAfterWork2(roundUpTime(calculationResult.getTimeAfterWork2(), 30));
                        if (calculationResult.getPrivateOutingTime() % 30 != 0)
                            calculationResult.setPrivateOutingTime(roundUpTime(calculationResult.getPrivateOutingTime(), 30));
                        if (calculationResult.getUnionOutingTime() % 30 != 0)
                            calculationResult.setUnionOutingTime(roundUpTime(calculationResult.getUnionOutingTime(), 30));
                        break;
                    case OneHour:
                        if (calculationResult.getTimeBeforeWork1() % 60 != 0)
                            calculationResult.setTimeBeforeWork1(roundUpTime(calculationResult.getTimeBeforeWork1(), 60));
                        if (calculationResult.getTimeAfterWork1() % 60 != 0)
                            calculationResult.setTimeAfterWork1(roundUpTime(calculationResult.getTimeAfterWork1(), 60));
                        if (calculationResult.getTimeBeforeWork2() % 60 != 0)
                            calculationResult.setTimeBeforeWork2(roundUpTime(calculationResult.getTimeBeforeWork2(), 60));
                        if (calculationResult.getTimeAfterWork2() % 60 != 0)
                            calculationResult.setTimeAfterWork2(roundUpTime(calculationResult.getTimeAfterWork2(), 60));
                        if (calculationResult.getPrivateOutingTime() % 60 != 0)
                            calculationResult.setPrivateOutingTime(roundUpTime(calculationResult.getPrivateOutingTime(), 60));
                        if (calculationResult.getUnionOutingTime() % 60 != 0)
                            calculationResult.setUnionOutingTime(roundUpTime(calculationResult.getUnionOutingTime(), 60));
                        break;
                    case TwoHour:
                        if (calculationResult.getTimeBeforeWork1() % 120 != 0)
                            calculationResult.setTimeBeforeWork1(roundUpTime(calculationResult.getTimeBeforeWork1(), 120));
                        if (calculationResult.getTimeAfterWork1() % 120 != 0)
                            calculationResult.setTimeAfterWork1(roundUpTime(calculationResult.getTimeAfterWork1(), 120));
                        if (calculationResult.getTimeBeforeWork2() % 120 != 0)
                            calculationResult.setTimeBeforeWork2(roundUpTime(calculationResult.getTimeBeforeWork2(), 120));
                        if (calculationResult.getTimeAfterWork2() % 120 != 0)
                            calculationResult.setTimeAfterWork2(roundUpTime(calculationResult.getTimeAfterWork2(), 120));
                        if (calculationResult.getPrivateOutingTime() % 120 != 0)
                            calculationResult.setPrivateOutingTime(roundUpTime(calculationResult.getPrivateOutingTime(), 120));
                        if (calculationResult.getUnionOutingTime() % 120 != 0)
                            calculationResult.setUnionOutingTime(roundUpTime(calculationResult.getUnionOutingTime(), 120));
                        break;
                    default:
                        break;
                }
            }
            return calculationResult;
        }
    }

    private Integer roundUpTime(Integer time, Integer unit) {
        return ((time / unit) + 1) * unit;
    }

    /**
     * ?????????????????????
     */
    public List<ConfirmMsgOutput> checkBeforeRegister(CheckRegisterParams params) {
        String companyId = AppContexts.user().companyId();
        List<ConfirmMsgOutput> confirmMsgOutputs;
        Application application;
        TimeLeaveApplicationOutput output = TimeLeaveAppDisplayInfoDto.mappingData(params.getTimeLeaveAppDisplayInfo());

        int over60h = 0;
        int nursingTime = 0;
        int childCareTime = 0;
        int subHolidayTime = 0;
        int annualTime = 0;
        for (TimeLeaveAppDetailDto time : params.getDetails()) {
            over60h += time.getApplyTime().getSuper60AppTime();
            nursingTime += time.getApplyTime().getCareAppTime();
            childCareTime += time.getApplyTime().getChildCareAppTime();
            subHolidayTime += time.getApplyTime().getSubstituteAppTime();
            annualTime += time.getApplyTime().getAnnualAppTime();
        }
        
        if (params.getApplicationNew() != null) {
            application = Application.createFromNew(
                    EnumAdaptor.valueOf(params.getApplicationNew().getPrePostAtr(), PrePostAtr.class),
                    params.getApplicationNew().getEmployeeID(),
                    EnumAdaptor.valueOf(params.getApplicationNew().getAppType(), ApplicationType.class),
                    new ApplicationDate(GeneralDate.fromString(params.getApplicationNew().getAppDate(), "yyyy/MM/dd")),
                    params.getApplicationNew().getEnteredPerson(),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.of(new ApplicationDate(GeneralDate.fromString(params.getApplicationNew().getOpAppStartDate(), "yyyy/MM/dd"))),
                    Optional.of(new ApplicationDate(GeneralDate.fromString(params.getApplicationNew().getOpAppEndDate(), "yyyy/MM/dd"))),
                    params.getApplicationNew().getOpAppReason() == null
                            ? Optional.empty()
                            : Optional.of(new AppReason(params.getApplicationNew().getOpAppReason())),
                    params.getApplicationNew().getOpAppStandardReasonCD() == null
                            ? Optional.empty()
                            : Optional.of(new AppStandardReasonCode(params.getApplicationNew().getOpAppStandardReasonCD()))
            );

            // ?????????????????????2-1.????????????????????????????????????????????????
            System.out.println("2-1.??????????????????????????????");
            
            confirmMsgOutputs = processBeforeRegister.processBeforeRegister_New(
                    companyId,
                    EmploymentRootAtr.APPLICATION,
                    params.isAgentMode(),
                    application,
                    null,
                    output.getAppDispInfoStartup().getAppDispInfoWithDateOutput().getOpMsgErrorLst().orElse(Collections.emptyList()),
                    Collections.emptyList(),
                    output.getAppDispInfoStartup(), 
                    new ArrayList<String>(), 
                    Optional.of(new TimeDigestionParam(over60h, nursingTime, childCareTime, subHolidayTime, annualTime, 0, params.getDetails().stream().map(TimeLeaveAppDetailDto::toShare).collect(Collectors.toList()))), 
                    Optional.empty(), 
                    false
            );
        } else {
            application = params.getApplicationUpdate().toDomain(params.getTimeLeaveAppDisplayInfo().getAppDispInfoStartupOutput().getAppDetailScreenInfo().getApplication());
            // 4-1.??????????????????????????????
            detailBeforeProcessRegisterService.processBeforeDetailScreenRegistration(
                    companyId,
                    application.getEmployeeID(),
                    application.getAppDate().getApplicationDate(),
                    EmploymentRootAtr.APPLICATION.value,
                    application.getAppID(),
                    application.getPrePostAtr(),
                    application.getVersion(),
                    null,
                    null,
                    output.getAppDispInfoStartup(), 
                    new ArrayList<String>(), 
                    Optional.of(new TimeDigestionParam(over60h, nursingTime, childCareTime, subHolidayTime, annualTime, 0, params.getDetails().stream().map(TimeLeaveAppDetailDto::toShare).collect(Collectors.toList()))), 
                    false, 
                    Optional.empty(), 
                    Optional.empty()
            );
            confirmMsgOutputs = new ArrayList<>();
        }

        TimeLeaveApplication domain = new TimeLeaveApplication(application, params.getDetails().stream().map(TimeLeaveAppDetailDto::toDomain).collect(Collectors.toList()));

        //???????????????????????????????????????
        timeLeaveApplicationService.checkBeforeRegister(
                companyId,
                EnumAdaptor.valueOf(params.getTimeDigestAppType(), TimeDigestAppType.class),
                domain,
                output
        );

        return confirmMsgOutputs;
    }

}
