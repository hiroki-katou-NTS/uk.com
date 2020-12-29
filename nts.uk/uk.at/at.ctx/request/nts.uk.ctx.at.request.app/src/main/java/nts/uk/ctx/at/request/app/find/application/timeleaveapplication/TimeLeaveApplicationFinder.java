package nts.uk.ctx.at.request.app.find.application.timeleaveapplication;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.app.find.application.common.service.other.output.AchievementDetailDto;
import nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto.TimeLeaveAppDisplayInfo;
import nts.uk.ctx.at.request.dom.adapter.timeleaveapplication.DailyAttendanceTimeAdapter;
import nts.uk.ctx.at.request.dom.adapter.timeleaveapplication.DailyAttendanceTimeImport;
import nts.uk.ctx.at.request.dom.application.*;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.NewBeforeRegister;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeDigestAppType;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeLeaveApplication;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.output.LeaveRemainingInfo;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.output.TimeLeaveApplicationOutput;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.service.TimeLeaveApplicationService;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;
import nts.uk.ctx.at.shared.app.find.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveAppReflectDto;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.AppTimeType;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveAppReflectRepository;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveApplicationReflect;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.service.WorkingConditionService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    /**
     * 登録前チェック
     */
    public List<ConfirmMsgOutput> checkBeforeRegister(RequestParam param) {
        String sid = AppContexts.user().employeeId();
        List<ConfirmMsgOutput> confirmMsgOutputs;
        TimeLeaveApplicationOutput output = TimeLeaveAppDisplayInfo.mappingData(param.getTimeLeaveAppDisplayInfo());
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
    public TimeLeaveAppDisplayInfo initNewTimeLeaveApplication(AppDispInfoStartupDto appDispInfoStartupOutput) {
        String companyId = AppContexts.user().companyId();
        String employeeId = appDispInfoStartupOutput.getAppDispInfoNoDateOutput().getEmployeeInfoLst().get(0).getSid();
        GeneralDate baseDate = GeneralDate.fromString(appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getBaseDate(), "yyyy/MM/dd");

        // 時間休申請の設定を取得する
        TimeLeaveApplicationReflect reflectSetting = timeLeaveApplicationService.getTimeLeaveAppReflectSetting(companyId);

        // 休暇残数情報を取得する
        LeaveRemainingInfo remainInfo = timeLeaveApplicationService.getLeaveRemainingInfo(companyId, employeeId, baseDate);

        // 社員の労働条件を取得する
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
            }, employeeId, baseDate);

        if (!workingConditionItem.isPresent())
            throw new BusinessException("Msg_430");

        // 取得した情報をOUTPUTにセットしする
        TimeLeaveAppDisplayInfo output = new TimeLeaveAppDisplayInfo();
        output.setAppDispInfoStartupOutput(appDispInfoStartupOutput);
        output.setReflectSetting(TimeLeaveAppReflectDto.fromDomain(reflectSetting));
        output.setWorkingConditionItem(workingConditionItem.get());
        output.setTimeLeaveManagement(remainInfo.getTimeLeaveManagement());
        output.setTimeLeaveRemaining(remainInfo.getTimeLeaveRemaining());

        // TODO: 特別休暇残数情報を取得する
        return output;
    }

    /**
     * 申請時間を計算する
     */
    public TimeLeaveCalculateDto calculateApplicationTime(calculateAppTimeParam param) {
        String sid = AppContexts.user().employeeId();
        TimeLeaveApplicationOutput output = TimeLeaveAppDisplayInfo.mappingData(param.getTimeLeaveAppDisplayInfo());
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

        //TODO get(0)
        AchievementDetailDto achievementDetailDto = param.getTimeLeaveAppDisplayInfo().getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().
            getOpActualContentDisplayLst().get(0).getOpAchievementDetail();
        //1日分の勤怠時間を仮計算
        DailyAttendanceTimeImport dailyAttendanceTime = dailyAttendanceTimeAdapter.calcDailyAttendance(
            param.getTimeLeaveAppDisplayInfo().getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().getEmployeeInfoLst().get(0).getSid(),
            GeneralDate.fromString(applicationDto.getAppDate(), "yyyy/MM/dd"),
            achievementDetailDto.getWorkTypeCD(),
            achievementDetailDto.getWorkTimeCD(),

            //TODO not mapping EA and source
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.emptyList()
        );
        CalculationResult calculationResult = new CalculationResult();

        //取得した「計算結果」を返す
        return new TimeLeaveCalculateDto(timeDigestAppType, calculationResult, confirmMsgOutputs);
    }

}
