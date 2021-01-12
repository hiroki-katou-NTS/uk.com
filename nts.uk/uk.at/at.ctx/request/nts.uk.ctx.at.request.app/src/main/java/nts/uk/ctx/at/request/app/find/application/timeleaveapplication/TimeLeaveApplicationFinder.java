package nts.uk.ctx.at.request.app.find.application.timeleaveapplication;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.common.service.other.output.AchievementDetailDto;
import nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto.*;
import nts.uk.ctx.at.request.dom.application.*;
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
import nts.uk.ctx.at.shared.app.find.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveAppReflectDto;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.AppTimeType;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveApplicationReflect;
import nts.uk.ctx.at.shared.dom.workingcondition.NotUseAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.shr.com.context.AppContexts;
import org.apache.commons.lang3.StringUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

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

//    @Inject
//    private DailyAttendanceTimeCaculation dailyAttendanceTimeCaculation;

    @Inject
    private DetailBeforeUpdate detailBeforeProcessRegisterService;

    /**
     * 時間休申請の起動処理（新規）
     * 時間休申請の起動処理（詳細）
     * @param params (申請ID, 申請表示情報)
     * @return
     */
    public TimeLeaveAppDisplayInfoDto initTimeLeaveApplication(StartProcessTimeLeaveParams params) {
        TimeLeaveAppDisplayInfoDto initData = new TimeLeaveAppDisplayInfoDto();
        String companyId = AppContexts.user().companyId();
        String employeeId;
        GeneralDate baseDate;
        if (!StringUtils.isEmpty(params.getAppId())) {
            //ドメインモデル「時間休暇申請請」より取得する
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

        // 時間休申請の設定を取得する
        TimeLeaveApplicationReflect reflectSetting = timeLeaveApplicationService.getTimeLeaveAppReflectSetting(companyId);

        // 休暇残数情報を取得する
        TimeVacationManagementOutput timeVacationManagement = timeLeaveApplicationService.getTimeLeaveManagement(companyId, employeeId, baseDate);
        TimeVacationRemainingOutput timeVacationRemaining = timeLeaveApplicationService.getTimeLeaveRemaining(companyId, employeeId, baseDate, timeVacationManagement);

        // 社員の労働条件を取得する
        Optional<WorkingCondition> workingCondition = workingConditionRepo.getBySidAndStandardDate(companyId, employeeId, baseDate);
        if (!workingCondition.isPresent())
            throw new BusinessException("Msg_430");
        Optional<WorkingConditionItem> workingConditionItem = workingConditionRepo.getWorkingConditionItem(workingCondition.get().getDateHistoryItem().get(0).identifier());
        if (!workingConditionItem.isPresent())
            throw new BusinessException("Msg_430");

        // 取得した情報をOUTPUTにセットしする
        initData.setAppDispInfoStartupOutput(params.getAppDispInfoStartupOutput());
        initData.setReflectSetting(TimeLeaveAppReflectDto.fromDomain(reflectSetting));
        initData.setTimeLeaveManagement(TimeLeaveManagement.fromOutput(timeVacationManagement));
        initData.setTimeLeaveRemaining(TimeLeaveRemaining.fromOutput(timeVacationRemaining));

        // 特別休暇残数情報を取得する
//        output = timeLeaveApplicationService.getSpecialLeaveRemainingInfo(
//                companyId,
//                output.getTimeVacationManagement().getTimeSpecialLeaveMng().getListSpecialFrame().stream().findFirst().map(SpecialHolidayFrame::getSpecialHdFrameNo),
//                output
//        );
        //取得した「時間休暇申請．詳細．申請時間」の時間>0:00の項目種類が複数ある
//        if (timeLeaveApplicationDto.getTimeDigestAppType() == TimeDigestAppType.USE_COMBINATION.value) {
//            //申請時間を計算する
//            CalculationResult calculationResult = calculateApplicationTime(baseDate, timeLeaveAppDisplayInfo, new ArrayList<>(), new ArrayList<>());
//
//            startProcessTimeLeaveAppDto.setCalculationResult(calculationResult);
//        }

        return initData;
    }

    /**
     * 申請日を変更する
     *
     * @param params
     * @return
     */
    public TimeLeaveAppDisplayInfoDto changeApplyDate(ChangeAppDateParams params) {
        String companyId = AppContexts.user().companyId();

        // 申請日のスケジュールをチェックする
        if (params.getAppDisplayInfo().getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getOpActualContentDisplayLst() == null
                || params.getAppDisplayInfo().getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().isEmpty()
                || params.getAppDisplayInfo().getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().get(0).getOpAchievementDetail() == null
                || StringUtils.isEmpty(params.getAppDisplayInfo().getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().get(0).getOpAchievementDetail().getWorkTypeCD())) {
            throw new BusinessException("Msg_1695", params.getAppDate().toString("yyyy/MM/dd"));
        }
        // 「承認ルートの基準日」をチェックする
        if (params.getAppDisplayInfo().getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().getApplicationSetting().getRecordDate() == RecordDate.SYSTEM_DATE.value) {
            return params.getAppDisplayInfo();
        }
        // 社員の労働条件を取得する
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

        // 時間休暇の管理区分を取得する
        TimeVacationManagementOutput timeVacationManagement = timeLeaveApplicationService.getTimeLeaveManagement(
                companyId,
                params.getAppDisplayInfo().getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().getEmployeeInfoLst().get(0).getSid(),
                params.getAppDate()
        );

        params.getAppDisplayInfo().setTimeLeaveManagement(TimeLeaveManagement.fromOutput(timeVacationManagement));
        return params.getAppDisplayInfo();
    }

    /**
     * 特別休暇枠を選択する
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
     * KAF012 : 申請時間を計算する
     */
    public CalculationResultDto calculateApplicationTime(Integer timeLeaveType, GeneralDate baseDate, TimeLeaveAppDisplayInfoDto info, List<TimeZoneWithWorkNo> lstTimeZone, List<TimeZoneWithWorkNo> lstOutingTimeZone) {
        String employeeId = info.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().getEmployeeInfoLst().get(0).getSid();
        AchievementDetailDto achievementDetailDto = info.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().
                getOpActualContentDisplayLst().get(0).getOpAchievementDetail();

        //1日分の勤怠時間を仮計算
        //TODO wait for requestlist

        // 取得した「日別勤怠の勤怠時間」をOUTPUTにセットする
        CalculationResultDto calculationResult = new CalculationResultDto();
        calculationResult.setTimeBeforeWork1(10);
        calculationResult.setTimeAfterWork1(10);
        calculationResult.setTimeBeforeWork2(10);
        calculationResult.setTimeAfterWork2(10);
        calculationResult.setPrivateOutingTime(10);
        calculationResult.setUnionOutingTime(10);

        TimeDigestAppType leaveType = EnumAdaptor.valueOf(timeLeaveType, TimeDigestAppType.class);
        if (leaveType == TimeDigestAppType.USE_COMBINATION)
            return calculationResult;
        else {
            // 「計算結果」を消化単位に切り上げて返す
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
            switch (unit) {
                case OneMinute:
                    break;
                case FifteenMinute:
                    if (calculationResult.getTimeBeforeWork1() % 15 != 0)
                        calculationResult.setTimeBeforeWork1(((calculationResult.getTimeBeforeWork1() / 15) + 1) * 15);
                    if (calculationResult.getTimeAfterWork1() % 15 != 0)
                        calculationResult.setTimeAfterWork1(((calculationResult.getTimeAfterWork1() / 15) + 1) * 15);
                    if (calculationResult.getTimeBeforeWork2() % 15 != 0)
                        calculationResult.setTimeBeforeWork2(((calculationResult.getTimeBeforeWork2() / 15) + 1) * 15);
                    if (calculationResult.getTimeAfterWork2() % 15 != 0)
                        calculationResult.setTimeAfterWork2(((calculationResult.getTimeAfterWork2() / 15) + 1) * 15);
                    if (calculationResult.getPrivateOutingTime() % 15 != 0)
                        calculationResult.setPrivateOutingTime(((calculationResult.getPrivateOutingTime() / 15) + 1) * 15);
                    if (calculationResult.getUnionOutingTime() % 15 != 0)
                        calculationResult.setUnionOutingTime(((calculationResult.getUnionOutingTime() / 15) + 1) * 15);
                    break;
                case ThirtyMinute:
                    if (calculationResult.getTimeBeforeWork1() % 30 != 0)
                        calculationResult.setTimeBeforeWork1(((calculationResult.getTimeBeforeWork1() / 30) + 1) * 30);
                    if (calculationResult.getTimeAfterWork1() % 30 != 0)
                        calculationResult.setTimeAfterWork1(((calculationResult.getTimeAfterWork1() / 30) + 1) * 30);
                    if (calculationResult.getTimeBeforeWork2() % 30 != 0)
                        calculationResult.setTimeBeforeWork2(((calculationResult.getTimeBeforeWork2() / 30) + 1) * 30);
                    if (calculationResult.getTimeAfterWork2() % 30 != 0)
                        calculationResult.setTimeAfterWork2(((calculationResult.getTimeAfterWork2() / 30) + 1) * 30);
                    if (calculationResult.getPrivateOutingTime() % 30 != 0)
                        calculationResult.setPrivateOutingTime(((calculationResult.getPrivateOutingTime() / 30) + 1) * 30);
                    if (calculationResult.getUnionOutingTime() % 30 != 0)
                        calculationResult.setUnionOutingTime(((calculationResult.getUnionOutingTime() / 30) + 1) * 30);
                    break;
                case OneHour:
                    if (calculationResult.getTimeBeforeWork1() % 60 != 0)
                        calculationResult.setTimeBeforeWork1(((calculationResult.getTimeBeforeWork1() / 60) + 1) * 60);
                    if (calculationResult.getTimeAfterWork1() % 60 != 0)
                        calculationResult.setTimeAfterWork1(((calculationResult.getTimeAfterWork1() / 60) + 1) * 60);
                    if (calculationResult.getTimeBeforeWork2() % 60 != 0)
                        calculationResult.setTimeBeforeWork2(((calculationResult.getTimeBeforeWork2() / 60) + 1) * 60);
                    if (calculationResult.getTimeAfterWork2() % 60 != 0)
                        calculationResult.setTimeAfterWork2(((calculationResult.getTimeAfterWork2() / 60) + 1) * 60);
                    if (calculationResult.getPrivateOutingTime() % 60 != 0)
                        calculationResult.setPrivateOutingTime(((calculationResult.getPrivateOutingTime() / 60) + 1) * 60);
                    if (calculationResult.getUnionOutingTime() % 60 != 0)
                        calculationResult.setUnionOutingTime(((calculationResult.getUnionOutingTime() / 60) + 1) * 60);
                    break;
                case TwoHour:
                    if (calculationResult.getTimeBeforeWork1() % 120 != 0)
                        calculationResult.setTimeBeforeWork1(((calculationResult.getTimeBeforeWork1() / 120) + 1) * 120);
                    if (calculationResult.getTimeAfterWork1() % 120 != 0)
                        calculationResult.setTimeAfterWork1(((calculationResult.getTimeAfterWork1() / 120) + 1) * 120);
                    if (calculationResult.getTimeBeforeWork2() % 120 != 0)
                        calculationResult.setTimeBeforeWork2(((calculationResult.getTimeBeforeWork2() / 120) + 1) * 120);
                    if (calculationResult.getTimeAfterWork2() % 120 != 0)
                        calculationResult.setTimeAfterWork2(((calculationResult.getTimeAfterWork2() / 120) + 1) * 120);
                    if (calculationResult.getPrivateOutingTime() % 120 != 0)
                        calculationResult.setPrivateOutingTime(((calculationResult.getPrivateOutingTime() / 120) + 1) * 120);
                    if (calculationResult.getUnionOutingTime() % 120 != 0)
                        calculationResult.setUnionOutingTime(((calculationResult.getUnionOutingTime() / 120) + 1) * 120);
                    break;
                default:
                    break;
            }
            return calculationResult;
        }

    }

    /**
     * KAFS12 : 申請時間を計算する
     */
    public TimeLeaveCalculateDto calculateApplicationTimeMobile(CalculateAppTimeMobileParams param) {
        String sid = AppContexts.user().employeeId();
        TimeLeaveApplicationOutput output = TimeLeaveAppDisplayInfoDto.mappingData(param.getTimeLeaveAppDisplayInfo());
        ApplicationDto applicationDto = param.getApplication();
        List<ConfirmMsgOutput> confirmMsgOutputs = new ArrayList<>();

        if (param.isScreenMode()) {
            Application application = Application.createFromNew(
                EnumAdaptor.valueOf(applicationDto.getPrePostAtr(), PrePostAtr.class),
                sid,
                EnumAdaptor.valueOf(applicationDto.getAppType(), ApplicationType.class),
                new ApplicationDate(GeneralDate.fromString(applicationDto.getAppDate(), "yyyy/MM/dd")),
                sid,
                Optional.empty(),
                Optional.empty(),
                Optional.of(new ApplicationDate(GeneralDate.fromString(applicationDto.getOpAppStartDate(), "yyyy/MM/dd"))),
                Optional.of(new ApplicationDate(GeneralDate.fromString(applicationDto.getOpAppEndDate(), "yyyy/MM/dd"))),
                applicationDto.getOpAppReason() == null ? Optional.empty() : Optional.of(new AppReason(applicationDto.getOpAppReason())),
                applicationDto.getOpAppStandardReasonCD() == null ? Optional.empty() : Optional.of(new AppStandardReasonCode(applicationDto.getOpAppStandardReasonCD())
                )
            );

            // 2-1.新規画面登録前の処理
            confirmMsgOutputs = processBeforeRegister.processBeforeRegister_New(
                AppContexts.user().companyId(),
                EmploymentRootAtr.APPLICATION,
                true,
                application,
                null,
                output.getAppDispInfoStartup().getAppDispInfoWithDateOutput().getOpErrorFlag().get(),
                Collections.emptyList(),
                output.getAppDispInfoStartup()
            );
        }

        int timeDigestAppType = 0;
        if (param.getTimeLeaveAppDisplayInfo().getReflectSetting().getCondition().checkTypeCombination() >= 2) {
            timeDigestAppType = TimeDigestAppType.USE_COMBINATION.value;
        } else if (param.getTimeLeaveAppDisplayInfo().getReflectSetting().getCondition().getSuperHoliday60H() == NotUseAtr.USE.value) {
            timeDigestAppType = TimeDigestAppType.SIXTY_H_OVERTIME.value;
        } else if (param.getTimeLeaveAppDisplayInfo().getReflectSetting().getCondition().getSubstituteLeaveTime() == NotUseAtr.USE.value) {
            timeDigestAppType = TimeDigestAppType.TIME_OFF.value;
        } else if (param.getTimeLeaveAppDisplayInfo().getReflectSetting().getCondition().getAnnualVacationTime() == NotUseAtr.USE.value) {
            timeDigestAppType = TimeDigestAppType.TIME_ANNUAL_LEAVE.value;
        } else if (param.getTimeLeaveAppDisplayInfo().getReflectSetting().getCondition().getChildNursing() == NotUseAtr.USE.value) {
            timeDigestAppType = TimeDigestAppType.CHILD_NURSING_TIME.value;
        } else if (param.getTimeLeaveAppDisplayInfo().getReflectSetting().getCondition().getNursing() == NotUseAtr.USE.value) {
            timeDigestAppType = TimeDigestAppType.NURSING_TIME.value;
        } else if (param.getTimeLeaveAppDisplayInfo().getReflectSetting().getCondition().getSpecialVacationTime() == NotUseAtr.USE.value) {
            timeDigestAppType = TimeDigestAppType.TIME_SPECIAL_VACATION.value;
        }

        //時間帯<List>
        List<TimeZoneWithWorkNo> lstTimeZone = new ArrayList<>();
        //外出時間帯<List>
        List<TimeZoneWithWorkNo> lstOutingTimeZone = new ArrayList<>();

        param.getDetails().forEach(x -> {
            if (x.getAppTimeType() == AppTimeType.ATWORK.value || x.getAppTimeType() == AppTimeType.OFFWORK.value) {
                lstTimeZone.addAll(x.getTimeZones().stream().map(i -> new TimeZoneWithWorkNo(1, i.getStartTime(), i.getEndTime())).collect(Collectors.toList()));
            } else if (x.getAppTimeType() == AppTimeType.ATWORK2.value || x.getAppTimeType() == AppTimeType.OFFWORK2.value) {
                lstTimeZone.addAll(x.getTimeZones().stream().map(i -> new TimeZoneWithWorkNo(2, i.getStartTime(), i.getEndTime())).collect(Collectors.toList()));
            } else if (x.getAppTimeType() == AppTimeType.PRIVATE.value || x.getAppTimeType() == AppTimeType.UNION.value) {
                lstOutingTimeZone.addAll(x.getTimeZones().stream().map(i -> new TimeZoneWithWorkNo(i.getWorkNo(), i.getStartTime(), i.getEndTime())).collect(Collectors.toList()));
            }
        });

        //1日分の勤怠時間を仮計算
        CalculationResultDto calculationResult = calculateApplicationTime(0, GeneralDate.fromString(applicationDto.getAppDate(), "yyyy/MM/dd"),
            param.getTimeLeaveAppDisplayInfo(), lstTimeZone, lstOutingTimeZone);

        //取得した「計算結果」を返す
        return new TimeLeaveCalculateDto(timeDigestAppType, calculationResult, confirmMsgOutputs);
    }

    /**
     * 登録前チェック
     */
    public List<ConfirmMsgOutput> checkBeforeRegister(CheckRegisterParams params) {
        String companyId = AppContexts.user().companyId();
        List<ConfirmMsgOutput> confirmMsgOutputs;
        Application application;
        TimeLeaveApplicationOutput output = TimeLeaveAppDisplayInfoDto.mappingData(params.getTimeLeaveAppDisplayInfo());

        if (params.getApplicationNew() != null) {
            String employeeId = AppContexts.user().employeeId();
            application = Application.createFromNew(
                    EnumAdaptor.valueOf(params.getApplicationNew().getPrePostAtr(), PrePostAtr.class),
                    employeeId,
                    EnumAdaptor.valueOf(params.getApplicationNew().getAppType(), ApplicationType.class),
                    new ApplicationDate(GeneralDate.fromString(params.getApplicationNew().getAppDate(), "yyyy/MM/dd")),
                    employeeId,
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

            // アルゴリズム「2-1.新規画面登録前の処理」を実行する
            System.out.println("2-1.新規画面登録前の処理");
            confirmMsgOutputs = processBeforeRegister.processBeforeRegister_New(
                    companyId,
                    EmploymentRootAtr.APPLICATION,
                    params.isAgentMode(),
                    application,
                    null,
                    output.getAppDispInfoStartup().getAppDispInfoWithDateOutput().getOpErrorFlag().get(),
                    Collections.emptyList(),
                    output.getAppDispInfoStartup()
            );
        } else {
            application = params.getApplicationUpdate().toDomain(params.getTimeLeaveAppDisplayInfo().getAppDispInfoStartupOutput().getAppDetailScreenInfo().getApplication());
            // 4-1.詳細画面登録前の処理
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
                    output.getAppDispInfoStartup()
            );
            confirmMsgOutputs = new ArrayList<>();
        }

        TimeLeaveApplication domain = new TimeLeaveApplication(application, params.getDetails().stream().map(TimeLeaveAppDetailDto::toDomain).collect(Collectors.toList()));

        //時間休暇申請登録前チェック
        timeLeaveApplicationService.checkBeforeRegister(
                companyId,
                EnumAdaptor.valueOf(params.getTimeDigestAppType(), TimeDigestAppType.class),
                domain,
                output
        );

        return confirmMsgOutputs;
    }

}
