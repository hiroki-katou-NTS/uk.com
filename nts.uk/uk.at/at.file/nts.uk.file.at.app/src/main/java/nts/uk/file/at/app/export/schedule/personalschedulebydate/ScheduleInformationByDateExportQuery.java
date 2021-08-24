package nts.uk.file.at.app.export.schedule.personalschedulebydate;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.primitive.PrimitiveValueBase;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortWorkingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.GetListWtypeWtimeUseDailyAttendRecordService;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flexset.GetTimezoneOfCoreTimeService;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeForm;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.screen.at.app.ksu003.start.dto.EmployeeWorkScheduleDto;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 日付別予定情報を取得する
 * UKDesign.UniversalK.就業.KSU_スケジュール.KSU003_個人スケジュール修正(日付別).D：出力の設定.メニュー別OCD.日付別予定情報を取得する.日付別予定情報を取得する
 */
@Stateless
public class ScheduleInformationByDateExportQuery {

    @Inject
    private WorkTypeRepository workTypeRepo;

    @Inject
    private WorkTimeSettingRepository workTimeSettingRepo;

    @Inject
    private FlexWorkSettingRepository flexWorkSet;

    @Inject
    private PredetemineTimeSettingRepository predetemineTimeSet;

    @Inject
    private FixedWorkSettingRepository fixedWorkSettingRepo;

    public EmployeeWorkScheduleDto get(List<IntegrationOfDaily> lstIntegrationOfDaily, boolean graphVacationDisplay,
                                       boolean doubleWorkDisplay) {
        final String companyId = AppContexts.user().companyId();

        // 日別勤怠の勤務情報リスト = List<日別勤怠(Work)>．values: List $.勤務情報
        val lstWorkInfoOfDailyAttendance = lstIntegrationOfDaily.stream().map(IntegrationOfDaily::getWorkInformation)
                .collect(Collectors.toList());
        // 1. 取得(List<日別勤怠の勤務情報>): param arg input.日別勤怠の勤務情報 , return 日別勤怠の実績で利用する勤務種類と就業時間帯
        val workTypeWorkTimeUseDailyAttendanceRecord = GetListWtypeWtimeUseDailyAttendRecordService.getdata(lstWorkInfoOfDailyAttendance);

        // 2. get(ログイン社員ID, 日別勤怠の実績で利用する勤務種類と就業時間帯.勤務種類リスト) : return WorkType
        List<WorkType> workTypeList = workTypeRepo.findByCidAndWorkTypeCodes(
                companyId,
                workTypeWorkTimeUseDailyAttendanceRecord.getLstWorkTypeCode().stream().map(PrimitiveValueBase::v).collect(Collectors.toList())
        );

        // 3. get(社員ID,List<就業時間帯コード>): return WorkTimeSetting
        List<WorkTimeSetting> workTimeSettingList = workTimeSettingRepo.getListWorkTimeSetByListCode(
                companyId,
                workTypeWorkTimeUseDailyAttendanceRecord.getLstWorkTimeCode().stream().map(PrimitiveValueBase::v).collect(Collectors.toList())
        );

        // Declare variable
        List<TimeSpanForCalc> timeSpanForCalcList = new ArrayList<>();

        // 4. loop：日別勤怠(Work) in input.List<日別勤怠(Work)>
        for (IntegrationOfDaily integrationOfDaily : lstIntegrationOfDaily) {
            for (WorkTimeSetting workTimeSet : workTimeSettingList) {
                // 4.1. 勤務形態を取得する() : return 就業時間帯の勤務形態
                WorkTimeForm workTimeForm = workTimeSet.getWorkTimeDivision().getWorkTimeForm();

                // 4.2. 就業時間帯の勤務形態 == フレックス勤務用: コアタイム時間帯を取得する
                if (workTimeForm == WorkTimeForm.FLEX) {
                    RequireTimezoneOfCoreImpl requireImpl = new RequireTimezoneOfCoreImpl(workTypeRepo, flexWorkSet, predetemineTimeSet);
                    // コアタイム時間帯を取得する.取得する(Require, 勤務種類コード, 就業時間帯コード): return Optional<計算時間帯>
                    for (WorkType workType : workTypeList) {
                        val timeSpanForCalcOpt = GetTimezoneOfCoreTimeService.get(requireImpl, workType.getWorkTypeCode(), workTimeSet.getWorktimeCode()); //TODO: param WorkTypeCode
                        timeSpanForCalcOpt.ifPresent(timeSpanForCalcList::add); // Add to timeSpanForCalcs}
                    }

                }

                // 4.3. 就業時間帯の勤務形態 == 固定勤務
                if (workTimeForm == WorkTimeForm.FIXED) {
                    // 4.3.1. get(会社ID, 就業時間帯コード): return Optional<固定勤務設定>
                    val fixedWorkSettingOpt = fixedWorkSettingRepo.findByKey(companyId, workTimeSet.getWorktimeCode().v());
                    // 4.3.2. 固定勤務設定.isPresent: 指定した午前午後区分の残業時間帯を取得する(午前午後区分): param 出勤日区分.午前午後区分に変換() : return List<計算用時間帯>
                    if (fixedWorkSettingOpt.isPresent()) {
                        timeSpanForCalcList = fixedWorkSettingOpt.get().getTimeZoneOfOvertimeWorkByAmPmAtr(AmPmAtr.AM); //TODO: param 出勤日区分.午前午後区分に変換()
                    }
                }
            }

            // 4.4. グラフ休暇表示==true: 時間休暇を取得する() : return Map<時間休暇種類, 時間休暇>
            if (graphVacationDisplay) {

            }

            // 4.5. create()
            String employeeId = integrationOfDaily.getEmployeeId();
            GeneralDate date = integrationOfDaily.getYmd();
            Integer startTime1 = null;
            Integer startTime2 = null;
            Integer endTime1 = null;
            Integer endTime2 = null;

            // List<育児介護短時間帯>= 日別勤怠(Work)．短時間勤務時間帯．時間帯 : ※項目の値は存在しない場合はempty
            List<ShortWorkingTimeSheet> shortWorkingTimeSheets = integrationOfDaily.getShortTime().isPresent()
                    ? integrationOfDaily.getShortTime().get().getShortWorkingTimeSheets()
                    : Collections.emptyList();

            // ※ グラフ休暇表示==false場合はempty
            // Map<時間休暇種類, 時間休暇>== 日別勤怠(Work)．時間休暇を取得する() //TODO: ko tìm thấy hàm 時間休暇を取得する()
            Map<Object, Object> map = graphVacationDisplay ? new HashMap<>() : null;

            // List＜休憩時間帯＞＝日別勤怠(Work)．日別勤怠の休憩時間帯．時間帯
            List<BreakTimeSheet> breakTimeSheets = integrationOfDaily.getBreakTime().getBreakTimeSheets();

            // 開始時刻 1= 日別勤怠(Work)．出退勤．出退勤．出勤  : ※勤務NO = 1のもの。
            // 終了時刻 1= 日別勤怠(Work)．出退勤．出退勤．退勤  : ※勤務NO = 1のもの。
            Optional<TimeLeavingWork> dailyAttd1 = integrationOfDaily.getAttendanceLeave().get().getTimeLeavingWorks()
                    .stream().filter(x -> x.getWorkNo().v() == 1).findFirst();
            if (dailyAttd1.isPresent()) {
                startTime1 = dailyAttd1.get().getAttendanceStamp().get().getStamp().get().getTimeDay()
                        .getTimeWithDay().get().v();
                endTime1 = dailyAttd1.get().getLeaveStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get()
                        .v();
            }

            // 開始時刻 2= 日別勤怠(Work)．出退勤．出退勤．出勤  : ※勤務NO = 2のもの。, ２回勤務表示==false場合はempty
            // 終了時刻 2= 日別勤怠(Work)．出退勤．出退勤．退勤  : ※勤務NO = 2のもの。
            Optional<TimeLeavingWork> dailyAttd2 = integrationOfDaily.getAttendanceLeave().get().getTimeLeavingWorks()
                    .stream().filter(x -> x.getWorkNo().v() == 2).findFirst();
            if (doubleWorkDisplay && dailyAttd2.isPresent()) {
                startTime2 = dailyAttd2.get().getAttendanceStamp().get().getStamp().get().getTimeDay()
                        .getTimeWithDay().get().v();
                endTime2 = dailyAttd2.get().getLeaveStamp().get().getStamp().get().getTimeDay().getTimeWithDay()
                        .get().v();
            }

            // 勤務種類コード = 日別勤怠(Work)．勤務情報．勤務情報．勤務種類コード
            WorkTypeCode workTypeCode = integrationOfDaily.getWorkInformation().getRecordInfo().getWorkTypeCode();

            // 勤務種類名称= 勤務種類．勤務種類略名

            // 就業時間帯コード = 日別勤怠(Work)．勤務情報．勤務情報．就業時間帯コード

            // 就業時間帯名称 = 就業時間帯の設定．表示名．略名


        }

        return null;
    }

    @AllArgsConstructor
    public static class RequireTimezoneOfCoreImpl implements GetTimezoneOfCoreTimeService.Require {

        private final String companyId = AppContexts.user().companyId();

        @Inject
        private WorkTypeRepository workTypeRepo;

        @Inject
        private FlexWorkSettingRepository flexWorkSet;

        @Inject
        private PredetemineTimeSettingRepository predetemineTimeSet;

        @Override
        public PredetemineTimeSetting getPredetermineTimeSetting(WorkTimeCode wktmCd) {
            Optional<PredetemineTimeSetting> workSetting = predetemineTimeSet.findByWorkTimeCode(companyId, wktmCd.v());
            return workSetting.orElse(null);
        }

        @Override
        public Optional<WorkType> getWorkType(WorkTypeCode workTypeCode) {
            return workTypeRepo.findByPK(companyId, workTypeCode.v());
        }

        @Override
        public Optional<FlexWorkSetting> getFlexWorkSetting(WorkTimeCode workTimeCode) {
            return flexWorkSet.find(companyId, workTimeCode.v());
        }
    }
}
