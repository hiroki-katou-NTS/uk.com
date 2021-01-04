package nts.uk.ctx.at.request.app.find.application.timeleaveapplication;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.app.find.application.common.service.other.output.AchievementDetailDto;
import nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto.*;
import nts.uk.ctx.at.request.dom.adapter.timeleaveapplication.DailyAttendanceTimeAdapter;
import nts.uk.ctx.at.request.dom.adapter.timeleaveapplication.DailyAttendanceTimeImport;
import nts.uk.ctx.at.request.dom.application.*;
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
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveAppReflectRepository;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveApplicationReflect;
import nts.uk.ctx.at.shared.dom.workingcondition.NotUseAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.service.WorkingConditionService;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHolidayFrame;
import nts.uk.shr.com.context.AppContexts;
import org.apache.commons.lang3.StringUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;

@Stateless
public class TimeLeaveApplicationFinder {

    @Inject
    private NewBeforeRegister processBeforeRegister;

    @Inject
    private TimeLeaveApplicationService timeLeaveApplicationService;

    @Inject
    private TimeLeaveAppReflectRepository timeLeaveAppReflectRepo;

    @Inject
    private WorkingConditionRepository workingConditionRepo;

    @Inject
    private DailyAttendanceTimeAdapter dailyAttendanceTimeAdapter;

    @Inject
    private TimeLeaveApplicationRepository timeLeaveApplicationRepository;

    @Inject
    private TimeLeaveAppReflectRepository timeLeaveAppReflectRepository;

    /**
     * 登録前チェック
     */
    public List<ConfirmMsgOutput> checkBeforeRegister(RequestParam param) {
        String sid = AppContexts.user().employeeId();
        List<ConfirmMsgOutput> confirmMsgOutputs;
        TimeLeaveApplicationOutput output = TimeLeaveAppDisplayInfoDto.mappingData(param.getTimeLeaveAppDisplayInfo());
        ApplicationDto applicationDto = param.getApplication();

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

        // アルゴリズム「2-1.新規画面登録前の処理」を実行する
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

        TimeLeaveApplication domain = TimeLeaveApplicationDto.toDomain(param.getTimeLeaveApplicationDto(), application);

        //時間休暇申請登録前チェック
        timeLeaveApplicationService.checkBeforeRigister(param.getTimeDigestAppType(), domain, output);

        return confirmMsgOutputs;
    }

    /**
     * 時間休申請の起動処理（新規）
     *
     * @param appDispInfoStartupOutput
     * @return
     */
    public TimeLeaveAppDisplayInfoDto initNewTimeLeaveApplication(AppDispInfoStartupDto appDispInfoStartupOutput) {
        String companyId = AppContexts.user().companyId();
        String employeeId = appDispInfoStartupOutput.getAppDispInfoNoDateOutput().getEmployeeInfoLst().get(0).getSid();
        GeneralDate baseDate = GeneralDate.fromString(appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getBaseDate(), "yyyy/MM/dd");

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
        TimeLeaveApplicationOutput output = new TimeLeaveApplicationOutput();
        output.setAppDispInfoStartup(appDispInfoStartupOutput.toDomain());
        output.setTimeLeaveApplicationReflect(reflectSetting);
        output.setWorkingConditionItem(workingConditionItem.get());
        output.setTimeVacationManagement(timeVacationManagement);
        output.setTimeVacationRemaining(timeVacationRemaining);

        // 特別休暇残数情報を取得する
        output = timeLeaveApplicationService.getSpecialLeaveRemainingInfo(
            companyId,
            output.getTimeVacationManagement().getTimeSpecialLeaveMng().getListSpecialFrame().stream().findFirst().map(SpecialHolidayFrame::getSpecialHdFrameNo),
            output
        );

        return TimeLeaveAppDisplayInfoDto.fromOutput(output);
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
            throw new BusinessException("Msg_1695", params.getApplyDate().toString("yyyy/MM/dd"));
        }
        // 「承認ルートの基準日」をチェックする
        if (params.getAppDisplayInfo().getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().getApplicationSetting().getRecordDate() == RecordDate.SYSTEM_DATE.value) {
            return params.getAppDisplayInfo();
        }
        // 社員の労働条件を取得する
        Optional<WorkingCondition> workingCondition = workingConditionRepo.getBySidAndStandardDate(
            companyId,
            params.getAppDisplayInfo().getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().getEmployeeInfoLst().get(0).getSid(),
            params.getApplyDate()
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
            params.getApplyDate()
        );

        params.getAppDisplayInfo().setTimeLeaveManagement(TimeLeaveManagement.fromOutput(timeVacationManagement));
        params.getAppDisplayInfo().setWorkingConditionItem(workingConditionItem.get());
        return params.getAppDisplayInfo();
    }

    /**
     * KAFS12 : 申請時間を計算する
     */
    public TimeLeaveCalculateDto calculateApplicationTime(CalculateAppTimeParams param) {
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

        param.getDigestApplications().forEach(x -> {
            if (x.getAppTimeType() == AppTimeType.ATWORK.value || x.getAppTimeType() == AppTimeType.OFFWORK.value) {
                lstTimeZone.add(new TimeZoneWithWorkNo(
                        1, x.getStartTime(), x.getEndTime()
                    )
                );
            } else if (x.getAppTimeType() == AppTimeType.ATWORK2.value || x.getAppTimeType() == AppTimeType.OFFWORK2.value) {
                lstTimeZone.add(new TimeZoneWithWorkNo(
                        2, x.getStartTime(), x.getEndTime()
                    )
                );
            } else if (x.getAppTimeType() == AppTimeType.PRIVATE.value || x.getAppTimeType() == AppTimeType.UNION.value) {
                lstOutingTimeZone.add(new TimeZoneWithWorkNo(
                        x.getWorkNo(), x.getStartTime(), x.getEndTime()
                    )
                );
            }
        });

        //1日分の勤怠時間を仮計算
        CalculationResult calculationResult = getDaily1AttendanceTime(GeneralDate.fromString(applicationDto.getAppDate(), "yyyy/MM/dd"),
            param.getTimeLeaveAppDisplayInfo(), lstTimeZone, lstOutingTimeZone);

        //取得した「計算結果」を返す
        return new TimeLeaveCalculateDto(timeDigestAppType, calculationResult, confirmMsgOutputs);
    }


    /**
     * KAF012 : 計算する
     */
    public CalculationResult getDaily1AttendanceTime(GeneralDate baseDate, TimeLeaveAppDisplayInfoDto info, List<TimeZoneWithWorkNo> lstTimeZone, List<TimeZoneWithWorkNo> lstOutingTimeZone) {

        AchievementDetailDto achievementDetailDto = info.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().
            getOpActualContentDisplayLst().get(0).getOpAchievementDetail();

        //1日分の勤怠時間を仮計算
        DailyAttendanceTimeImport daily1AttendanceTime = dailyAttendanceTimeAdapter.calcDailyAttendance(
            info.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().getEmployeeInfoLst().get(0).getSid(),
            baseDate,
            achievementDetailDto.getWorkTypeCD(),
            achievementDetailDto.getWorkTimeCD(),

            //TODO chờ team nsvn update RQL23
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.emptyList()
        );

        //TODO map daily1AttendanceTime
        CalculationResult calculationResult = new CalculationResult();
        return calculationResult;

    }


    /**
     * 時間休申請の起動処理（詳細）
     */
    public StartProcessTimeLeaveAppDto initUpdateTimeLeaveApp(StartProcessTimeLeaveParam param) {

        String cid = AppContexts.user().companyId();

        //ドメインモデル「時間休暇申請請」より取得する
        Optional<TimeLeaveApplication> timeLeaveApplication = timeLeaveApplicationRepository.findById(cid, param.getAppId());

        //時間休申請の設定を取得する
        TimeLeaveApplicationReflect timeLeaveApplicationReflect = timeLeaveApplicationService.getTimeLeaveAppReflectSetting(cid);

        if (!timeLeaveApplication.isPresent()) {
            return null;
        }

        String sid = timeLeaveApplication.get().getEmployeeID();
        GeneralDate baseDate = timeLeaveApplication.get().getAppDate().getApplicationDate();

        //休暇残数情報を取得する
        TimeVacationManagementOutput timeVacationManagementOutput = timeLeaveApplicationService.getTimeLeaveManagement(cid, sid, baseDate);
        TimeVacationRemainingOutput timeVacationRemainingOutput = timeLeaveApplicationService.getTimeLeaveRemaining(cid, sid, baseDate, timeVacationManagementOutput);

        //社員の労働条件を取得する
        Optional<WorkingConditionItem> workingConditionItem = WorkingConditionService
            .findWorkConditionByEmployee(new WorkingConditionService.RequireM1() {
                @Override
                public Optional<WorkingConditionItem> workingConditionItem(String historyId) {
                    return workingConditionRepo.getWorkingConditionItem(historyId);
                }

                @Override
                public Optional<WorkingCondition> workingCondition(String companyId, String employeeId, GeneralDate baseDate) {
                    return workingConditionRepo.getBySidAndStandardDate(companyId, employeeId, baseDate);
                }
            }, sid, baseDate);
        if (!workingConditionItem.isPresent())
            throw new BusinessException("Msg_430");

        TimeLeaveAppDisplayInfoDto timeLeaveAppDisplayInfo = new TimeLeaveAppDisplayInfoDto(
            workingConditionItem.get(),
            TimeLeaveRemaining.fromOutput(timeVacationRemainingOutput),
            TimeLeaveAppReflectDto.fromDomain(timeLeaveApplicationReflect),
            TimeLeaveManagement.fromOutput(timeVacationManagementOutput),
            param.getAppDispInfoStartupOutput()
        );

        TimeLeaveApplicationDto timeLeaveApplicationDto = TimeLeaveApplicationDto.fromDomain(timeLeaveApplication.get());
        StartProcessTimeLeaveAppDto startProcessTimeLeaveAppDto = new StartProcessTimeLeaveAppDto();
        startProcessTimeLeaveAppDto.setTimeLeaveAppDisplayInfo(timeLeaveAppDisplayInfo);
        startProcessTimeLeaveAppDto.setTimeLeaveApplicationDto(TimeLeaveApplicationDto.fromDomain(timeLeaveApplication.get()));

        //取得した「時間休暇申請．詳細．申請時間」の時間>0:00の項目種類が複数ある
        if (timeLeaveApplicationDto.getTimeDigestAppType() == TimeDigestAppType.USE_COMBINATION.value) {
            //申請時間を計算する
            CalculationResult calculationResult = getDaily1AttendanceTime(baseDate, timeLeaveAppDisplayInfo, new ArrayList<>(), new ArrayList<>());

            startProcessTimeLeaveAppDto.setCalculationResult(calculationResult);
        }
        return startProcessTimeLeaveAppDto;
    }
}
