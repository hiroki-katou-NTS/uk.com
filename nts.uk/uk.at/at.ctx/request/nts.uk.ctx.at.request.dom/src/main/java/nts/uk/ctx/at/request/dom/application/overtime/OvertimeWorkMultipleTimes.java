package nts.uk.ctx.at.request.dom.application.overtime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.request.dom.adapter.CalculationParams;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.DayOfWeek;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calcategory.CalAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.ChangeDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.CalculationState;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.NotUseAttribute;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.outsideworktime.OverTimeFrameTimeSheet;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeMethodSet;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.shr.com.time.TimeWithDayAttr;
import nts.uk.shr.com.time.TimeZone;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.申請承認.申請.残業休出申請.残業申請.複数回残業内容
 * 複数回残業内容
 */
@Getter
@AllArgsConstructor
public class OvertimeWorkMultipleTimes {
    // 残業時間帯
    private List<OvertimeHour> overtimeHours;

    // 残業理由
    private List<OvertimeReason> overtimeReasons;

    /**
     * 作成する
     * @param overtimeHours List<残業時間帯>
     * @param overtimeReasons List<残業理由>
     * @return 複数回残業内容
     */
    public static OvertimeWorkMultipleTimes create(List<OvertimeHour> overtimeHours, List<OvertimeReason> overtimeReasons) {
        overtimeHours.sort(Comparator.comparing(i -> i.getOvertimeHours().getStart()));
        for (int i = 0; i < overtimeHours.size() - 1; i++) {
            if (overtimeHours.get(i).getOvertimeHours().getDuplicatedWith(overtimeHours.get(i + 1).getOvertimeHours()).isPresent()) {
                throw new BusinessException("Msg_3238");
            }
        }

        List<OvertimeHour> newOvertimeHours = new ArrayList<>();
        List<OvertimeReason> newOvertimeReasons = new ArrayList<>();
        for (int i = 0; i < overtimeHours.size(); i++) {
            OvertimeHour overtimeHour = overtimeHours.get(i);
            OvertimeNumber overtimeNumber = new OvertimeNumber(i + 1);
            newOvertimeHours.add(new OvertimeHour(overtimeNumber, overtimeHour.getOvertimeHours()));
            overtimeReasons.stream().filter(r -> r.getOvertimeNumber().equals(overtimeHour.getOvertimeNumber())).findFirst().ifPresent(reason -> {
                newOvertimeReasons.add(new OvertimeReason(overtimeNumber, reason.getFixedReasonCode(), reason.getApplyReason()));
            });
        }
        return new OvertimeWorkMultipleTimes(newOvertimeHours, newOvertimeReasons);
    }

    /**
     * 申請の定型理由を作成する
     * @return Optional<申請定型理由コード>
     */
    public Optional<AppStandardReasonCode> createFixedReason() {
        if (overtimeReasons.isEmpty()) return Optional.empty();
        return overtimeReasons.get(0).getFixedReasonCode();
    }

    /**
     * 申請理由を作成する
     * @return Optional<申請理由>
     */
    public Optional<AppReason> createApplyReason() {
        List<String> appReasons = overtimeReasons.stream().filter(r -> r.getApplyReason().isPresent()).map(r -> r.getApplyReason().get().v()).collect(Collectors.toList());
        if (appReasons.isEmpty()) return Optional.empty();
        return Optional.of(new AppReason(StringUtil.cutOffAsLengthHalf(StringUtils.join(appReasons, "/"), 400)));
    }

    /**
     * [3] 出退勤時刻を取得する
     * @param workTimes
     * @return
     */
    public List<TimeZoneWithWorkNo> getArriveAndLeaveTime(List<TimeZoneWithWorkNo> workTimes) {
        List<TimeZoneWithWorkNo> result = new ArrayList<>();
        result.add(this.organizeWorkTime1(workTimes));
        this.organizeWorkTime2(workTimes).ifPresent(i -> result.add(i));
        return result;
    }

    /**
     * [4]勤務時間、残業時間帯から所定外の休憩時間帯を判断する
     * @param scheduled 所定の勤務時間
     * @param actual 出退勤時刻
     * @return
     */
    public List<BreakTimeSheet> getBreakTimeOutsideSchedule(List<TimeZoneWithWorkNo> scheduled, List<TimeZoneWithWorkNo> actual) {
        List<BreakTimeSheet> breakTimes = new ArrayList<>();

        breakTimes.addAll(this.getBreakTimeBeforeWork(scheduled, actual));
        breakTimes.addAll(this.getBreakTimeBetweenWork(scheduled));
        breakTimes.addAll(this.getBreakTimeAfterWork(scheduled, actual));
        breakTimes.sort(Comparator.comparing(BreakTimeSheet::getStartTime));

        return CalculateBreakTimeZoneService.organizeBreakTimes(breakTimes);
    }

//    /**
//     * 残業時間を計算のために勤務時間を判断する
//     * @param timeZones List<時間帯(勤務NO付き)>
//     * @return List<時間帯(勤務NO付き)>
//     */
//    public List<TimeZoneWithWorkNo> getWorkingHoursToCalculateOvertime(List<TimeZoneWithWorkNo> timeZones) {
//        if (CollectionUtil.isEmpty(timeZones) || CollectionUtil.isEmpty(this.overtimeHours))
//            return timeZones;
//        timeZones.sort(Comparator.comparing(TimeZoneWithWorkNo::getWorkNo));
//        TimeZoneWithWorkNo zone1 = timeZones.get(0);
//        TimeZoneWithWorkNo zone2 = timeZones.size() > 1 ? timeZones.get(1) : null;
//        TimeWithDayAttr startTime = this.overtimeHours.get(0).getOvertimeHours().getStart();
//        TimeWithDayAttr endTime = this.overtimeHours.get(this.overtimeHours.size() - 1).getOvertimeHours().getEnd();
//        if (zone1.getTimeZone().getStartTime().greaterThan(startTime)) zone1.getTimeZone().setStartTime(startTime);
//        if (zone2 == null) {
//            if (zone1.getTimeZone().getEndTime().lessThan(endTime))
//                zone1.getTimeZone().setEndTime(endTime);
//            return Collections.singletonList(zone1);
//        }
//        TimeWithDayAttr end1 = overtimeHours.stream()
//                .filter(i -> i.getOvertimeHours().getEnd().lessThanOrEqualTo(zone2.getTimeZone().getStartTime()))
//                .reduce((first, second) -> second)
//                .map(i -> i.getOvertimeHours().getEnd()).orElse(null);
//        if (end1 != null) {
//            if (zone1.getTimeZone().getEndTime().lessThan(end1))
//                zone1.getTimeZone().setEndTime(end1);
//            TimeWithDayAttr start2 = overtimeHours.stream()
//                    .filter(i -> i.getOvertimeHours().getStart().greaterThanOrEqualTo(end1))
//                    .findFirst()
//                    .map(i -> i.getOvertimeHours().getStart()).orElse(null);
//            if (start2 != null) {
//                if (zone2.getTimeZone().getStartTime().greaterThan(start2))
//                    zone2.getTimeZone().setStartTime(start2);
//            }
//        }
//        if (zone2.getTimeZone().getEndTime().lessThan(endTime))
//            zone2.getTimeZone().setEndTime(endTime);
//        return Arrays.asList(zone1, zone2);
//    }

//    /**
//     * 残業時間を計算のために休憩時間帯を判断する
//     * @param require
//     * @param companyId 会社ID
//     * @param employeeId 申請者ID
//     * @param date 年月日
//     * @param workInfo 勤務情報
//     * @param workingHours 勤務時間List
//     * @param breakTimes 休憩時間帯List
//     * @return 休憩時間帯List
//     */
//    public List<BreakTimeSheet> getBreakTimeToCalculateOvertime(Require require,
//                                                                String companyId,
//                                                                String employeeId,
//                                                                GeneralDate date,
//                                                                WorkInformation workInfo,
//                                                                List<TimeZoneWithWorkNo> workingHours,
//                                                                List<BreakTimeSheet> breakTimes,
//                                                                boolean managementMultipleWorkCycles) {
//        List<TimeZoneWithWorkNo> predeterminedWorkingHours = workingHours;
//        if (workInfo.getWorkTimeCodeNotNull().isPresent()) {
//            Optional<PredetemineTimeSetting> predTimeSet = require.getPredetemineTimeSetting(companyId, workInfo.getWorkTimeCode().v());
//            if (predTimeSet.isPresent()) {
//                predeterminedWorkingHours = predTimeSet.get().getPrescribedTimezoneSetting().getLstTimezone()
//                        .stream().filter(i -> i.isUsed() && (i.getWorkNo() == 1 || managementMultipleWorkCycles))
//                        .map(i -> new TimeZoneWithWorkNo(i.getWorkNo(), i.getStart().v(), i.getEnd().v()))
//                        .collect(Collectors.toList());
//            }
//            Optional<WorkTimeSetting> workTimeSetting = require.getWorkTimeSetting(companyId, workInfo.getWorkTimeCode().v());
//            if (workTimeSetting.isPresent() && workTimeSetting.get().getWorkTimeDivision().getWorkTimeMethodSet() == WorkTimeMethodSet.FLOW_WORK) {
//                breakTimes = this.getFlowWorkBreakTime(require, employeeId, date, workInfo, predTimeSet, workingHours);
//                predeterminedWorkingHours.get(0).getTimeZone().setEndTime(this.getFlowWorkEndTime(require, employeeId, date, workInfo, predTimeSet, workingHours));
//            }
//        }
//        List<BreakTimeSheet> result = this.calculateNewBreakTimes(breakTimes, predeterminedWorkingHours, workingHours);
//        if (result.size() > 10) throw new BusinessException("Msg_3236");
//        return result;
//    }

//    /**
//     * [prv-1]流動勤務で 休憩時間帯を取得する
//     * @param require
//     * @param employeeId 申請者ID
//     * @param date 年月日
//     * @param workInfo 勤務情報
//     * @param predTimeSet 所定時間設定
//     * @param workingHours 勤務時間List
//     * @return 休憩時間帯List
//     */
//    private List<BreakTimeSheet> getFlowWorkBreakTime(Require require,
//                                                      String employeeId,
//                                                      GeneralDate date,
//                                                      WorkInformation workInfo,
//                                                      Optional<PredetemineTimeSetting> predTimeSet,
//                                                      List<TimeZoneWithWorkNo> workingHours) {
//        WorkInfoOfDailyAttendance workInformation = new WorkInfoOfDailyAttendance(
//                workInfo,
//                CalculationState.No_Calculated,
//                NotUseAttribute.Not_use,
//                NotUseAttribute.Not_use,
//                DayOfWeek.MONDAY,
//                new ArrayList<>(),
//                Optional.empty()
//        );
//        CalAttrOfDailyAttd calCategory = CalAttrOfDailyAttd.defaultData();
//        AffiliationInforOfDailyAttd affiliationInforOfDailyPerfor = new AffiliationInforOfDailyAttd(
//                null,
//                null,
//                null,
//                null,
//                Optional.empty(),
//                Optional.empty()
//        );
//
//        List<TimeZoneWithWorkNo> tmpWorkingHours = new ArrayList<>();
//        workingHours.forEach(wh -> {
//            if (wh.getWorkNo().v() == workingHours.size() && predTimeSet.isPresent()) {
//                tmpWorkingHours.add(new TimeZoneWithWorkNo(
//                        wh.getWorkNo().v(),
//                        wh.getTimeZone().getStartTime().v(),
//                        predTimeSet.get().getEndDateClock().v()
//                ));
//            } else {
//                tmpWorkingHours.add(wh);
//            }
//        });
//
//        TimeLeavingOfDailyAttd attendanceLeave = new TimeLeavingOfDailyAttd(
//                tmpWorkingHours.stream().map(i -> new TimeLeavingWork(
//                        i.getWorkNo(),
//                        new TimeActualStamp(
//                                WorkStamp.createByAutomaticSet(i.getTimeZone().getStartTime()),
//                                WorkStamp.createByAutomaticSet(i.getTimeZone().getStartTime()),
//                                1
//                        ),
//                        new TimeActualStamp(
//                                WorkStamp.createByAutomaticSet(i.getTimeZone().getEndTime()),
//                                WorkStamp.createByAutomaticSet(i.getTimeZone().getEndTime()),
//                                1
//                        )
//                )).collect(Collectors.toList()),
//                new WorkTimes(1)
//        );
//
//        IntegrationOfDaily dailyAttendance = new IntegrationOfDaily(
//                employeeId,
//                date,
//                workInformation,
//                calCategory,
//                affiliationInforOfDailyPerfor,
//                Optional.empty(),
//                new ArrayList<>(),
//                Optional.empty(),
//                new BreakTimeOfDailyAttd(),
//                Optional.empty(),
//                Optional.of(attendanceLeave),
//                Optional.empty(),
//                Optional.empty(),
//                Optional.empty(),
//                Optional.empty(),
//                new ArrayList<>(),
//                Optional.empty(),
//                new ArrayList<>(),
//                new ArrayList<>(),
//                new ArrayList<>(),
//                Optional.empty()
//        );
//        ChangeDailyAttendance changeAtt = new ChangeDailyAttendance(
//                false,
//                false,
//                true,
//                false,
//                ScheduleRecordClassifi.RECORD,
//                false
//        );
//        return new ArrayList<>(require.process(dailyAttendance, changeAtt).getBreakTime().getBreakTimeSheets());
//    }

//    /**
//     * [prv-2]休憩時間帯Newを判断する
//     * @param breakTimes 休憩時間帯List
//     * @param predeterminedWorkingHours 勤務時間List（所定）
//     * @param workingHours 勤務時間List（整理後）
//     * @return 休憩時間帯List
//     */
//    private List<BreakTimeSheet> calculateNewBreakTimes(List<BreakTimeSheet> breakTimes, List<TimeZoneWithWorkNo> predeterminedWorkingHours, List<TimeZoneWithWorkNo> workingHours) {
//        // ①休憩時間帯List(所定)
//        // ②「勤務時間と残業時刻」の間隔は「休憩時間」になる（仕事なし）
//        // ③２つ別の残業の間隔は「休憩時間」になる　（仕事なし）
//        // ④休憩時間帯が軒並の場合、１つに合同する
//        // ⑤休憩時間帯(所定）と残業時間帯が重複の場合、休憩時間帯が優先
//        // ⑥勤務時間(所定）と勤務時間（整理）の間に残業時間ない場合、休憩時間になる
//
//        List<TimeSpanForCalc> timeLine = new ArrayList<>();
//        timeLine.addAll(predeterminedWorkingHours.stream().map(i -> new TimeSpanForCalc(i.getTimeZone().getStartTime(), i.getTimeZone().getEndTime())).collect(Collectors.toList()));
//        this.overtimeHours.forEach(ot -> {
//            if (timeLine.stream().noneMatch(t -> t.contains(ot.getOvertimeHours()))) {
//                timeLine.add(ot.getOvertimeHours());
//            }
//        });
//        timeLine.sort(Comparator.comparing(TimeSpanForCalc::getStart));
//
//        if (!timeLine.isEmpty()) {
//            new TimeSpanForCalc(timeLine.get(0).getStart(), timeLine.get(timeLine.size() - 1).getEnd())
//                    .getNotDuplicatedWith(timeLine).forEach(bt -> {
//                breakTimes.add(new BreakTimeSheet(new BreakFrameNo(1), bt.getStart(), bt.getEnd()));
//            });
//        }
//
//        TimeZone workingTime = new TimeZone(null, null);
//        workingHours.forEach(wh -> {
//            if (workingTime.getStartTime() == null || workingTime.getStartTime().greaterThan(wh.getTimeZone().getStartTime())) {
//                workingTime.setStartTime(wh.getTimeZone().getStartTime());
//            }
//            if (workingTime.getEndTime() == null || workingTime.getEndTime().lessThan(wh.getTimeZone().getEndTime())) {
//                workingTime.setEndTime(wh.getTimeZone().getEndTime());
//            }
//        });
//        new TimeSpanForCalc(workingTime.getStartTime(), workingTime.getEndTime())
//                .getNotDuplicatedWith(timeLine).forEach(bt -> {
//            breakTimes.add(new BreakTimeSheet(new BreakFrameNo(1), bt.getStart(), bt.getEnd()));
//        });
//
//        breakTimes.sort(Comparator.comparing(BreakTimeSheet::getStartTime));
//
//        return CalculateBreakTimeZoneService.organizeBreakTimes(breakTimes);
//    }

//    /**
//     * [prv-6]流動勤務で勤務時間の終了時刻を取得する
//     * @param require Require
//     * @param employeeId 申請者ID
//     * @param date 年月日
//     * @param workInformation 勤務情報
//     * @param predTimeSet 所定時間設定
//     * @param workingHours 勤務時間List
//     * @return 終了時刻
//     */
//    private TimeWithDayAttr getFlowWorkEndTime(Require require, String employeeId, GeneralDate date, WorkInformation workInformation, Optional<PredetemineTimeSetting> predTimeSet, List<TimeZoneWithWorkNo> workingHours) {
//        List<TimeZoneWithWorkNo> tmpWorkingHours = new ArrayList<>();
//        workingHours.forEach(wh -> {
//            if (wh.getWorkNo().v() == workingHours.size() && predTimeSet.isPresent()) {
//                tmpWorkingHours.add(new TimeZoneWithWorkNo(
//                        wh.getWorkNo().v(),
//                        wh.getTimeZone().getStartTime().v(),
//                        predTimeSet.get().getEndDateClock().v()
//                ));
//            } else {
//                tmpWorkingHours.add(wh);
//            }
//        });
//
//        CalculationParams params = new CalculationParams(
//                employeeId,
//                date,
//                workInformation.getWorkTypeCode(),
//                workInformation.getWorkTimeCode(),
//                tmpWorkingHours,
//                Collections.emptyList(),
//                Collections.emptyList(),
//                Collections.emptyList()
//        );
//        IntegrationOfDaily calcResult = require.tempCalculateOneDayAttendanceTime(params);
//        if (!calcResult.getAttendanceTimeOfDailyPerformance().isPresent()
//                || !calcResult.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork().isPresent()) {
//            return workingHours.get(workingHours.size() - 1).getTimeZone().getEndTime();
//        }
//        List<OverTimeFrameTimeSheet> overTimeWorkFrameTimeSheet = calcResult
//                .getAttendanceTimeOfDailyPerformance().get()
//                .getActualWorkingTimeOfDaily()
//                .getTotalWorkingTime()
//                .getExcessOfStatutoryTimeOfDaily()
//                .getOverTimeWork().get()
//                .getOverTimeWorkFrameTimeSheet();
//        TimeWithDayAttr minOvertimeStart = overTimeWorkFrameTimeSheet.stream().map(i -> i.getTimeSpan().getStart()).sorted().findFirst().orElse(workingHours.get(workingHours.size() - 1).getTimeZone().getEndTime());
//        return minOvertimeStart;
//    }


    /**
     * [prv-1] 指定時刻前の残業終了時刻を取得する
     * @param specifiedTime
     * @return
     */
    private TimeWithDayAttr getOvertimeEndTimeBeforeSpecifiedTime(Optional<TimeWithDayAttr> specifiedTime) {
        if (!specifiedTime.isPresent())
            return this.overtimeHours.get(this.overtimeHours.size() - 1).getOvertimeHours().getEnd();

        return this.overtimeHours.stream()
                .filter(i -> i.getOvertimeHours().getEnd().lessThanOrEqualTo(specifiedTime.get()))
                .reduce((first, second) -> second).get().getOvertimeHours().getEnd();
    }

    /**
     * [prv-2] 指定時刻後の残業開始時刻を取得する
     * @param specifiedTime
     * @return
     */
    private TimeWithDayAttr getOvertimeStartTimeAfterSpecifiedTime(Optional<TimeWithDayAttr> specifiedTime) {
        if (!specifiedTime.isPresent())
            return this.overtimeHours.get(0).getOvertimeHours().getStart();

        return this.overtimeHours.stream()
                .filter(i -> i.getOvertimeHours().getStart().greaterThanOrEqualTo(specifiedTime.get()))
                .findFirst().get().getOvertimeHours().getStart();
    }

    /**
     * [prv-6] 勤務時間1を整理する
     * @param workTimes
     * @return
     */
    private TimeZoneWithWorkNo organizeWorkTime1(List<TimeZoneWithWorkNo> workTimes) {
        TimeWithDayAttr overtimeStart = this.getOvertimeStartTimeAfterSpecifiedTime(Optional.empty());

        Optional<TimeZoneWithWorkNo> workTime2 = workTimes.stream().filter(i -> i.getWorkNo().v() == 2).findAny();

        Optional<TimeWithDayAttr> workTime2Start = workTime2.map(i -> i.getTimeZone().getEndTime());

        TimeWithDayAttr endTime = this.getOvertimeEndTimeBeforeSpecifiedTime(workTime2Start);

        Optional<TimeZoneWithWorkNo> workTime1 = workTimes.stream().filter(i -> i.getWorkNo().v() == 1).findAny();

        return this.setWorkTime(workTime1.get(), overtimeStart, endTime);
    }

    /**
     * [prv-7]勤務時間2を取得する
     * @param workTimes
     * @return
     */
    private Optional<TimeZoneWithWorkNo> organizeWorkTime2(List<TimeZoneWithWorkNo> workTimes) {
        Optional<TimeZoneWithWorkNo> workTime2 = workTimes.stream().filter(i -> i.getWorkNo().v() == 2).findAny();
        if (!workTime2.isPresent()) return Optional.empty();

        TimeWithDayAttr endTime = this.getOvertimeEndTimeBeforeSpecifiedTime(Optional.empty());

        Optional<TimeWithDayAttr> workTime1End = workTimes.stream()
                .filter(i -> i.getWorkNo().v() == 1)
                .findAny().map(i -> i.getTimeZone().getStartTime());

        TimeWithDayAttr startTime = this.getOvertimeStartTimeAfterSpecifiedTime(workTime1End);

        return Optional.of(this.setWorkTime(workTime2.get(), startTime, endTime));
    }

    /**
     * [prv-8]勤務時間をセットする
     * @param timeZone
     * @param startTime
     * @param endTime
     * @return
     */
    private TimeZoneWithWorkNo setWorkTime(TimeZoneWithWorkNo timeZone, TimeWithDayAttr startTime, TimeWithDayAttr endTime) {
        if (timeZone.getTimeZone().getStartTime().greaterThan(startTime)) {
            timeZone.getTimeZone().setStartTime(startTime);
        }
        if (timeZone.getTimeZone().getEndTime().lessThan(endTime)) {
            timeZone.getTimeZone().setEndTime(endTime);
        }
        return timeZone;
    }

    /**
     * [prv-10]出勤前の休憩時間帯を取得する
     * @param scheduled
     * @param actual
     * @return
     */
    private List<BreakTimeSheet> getBreakTimeBeforeWork(List<TimeZoneWithWorkNo> scheduled, List<TimeZoneWithWorkNo> actual) {
        TimeWithDayAttr scheduledStart = scheduled.stream().min(Comparator.comparing(i -> i.getWorkNo().v())).get().getTimeZone().getStartTime();

        TimeWithDayAttr actualStart = actual.stream().min(Comparator.comparing(i -> i.getWorkNo().v())).get().getTimeZone().getStartTime();

        return this.getBreakTimeDuringSpecifiedPeriod(actualStart, scheduledStart);
    }

    /**
     * [prv-11]退勤後の休憩時間帯を取得する
     * @param scheduled
     * @param actual
     * @return
     */
    private List<BreakTimeSheet> getBreakTimeAfterWork(List<TimeZoneWithWorkNo> scheduled, List<TimeZoneWithWorkNo> actual) {
        TimeWithDayAttr scheduledEnd = scheduled.stream().max(Comparator.comparing(i -> i.getWorkNo().v())).get().getTimeZone().getEndTime();

        TimeWithDayAttr actualEnd = actual.stream().max(Comparator.comparing(i -> i.getWorkNo().v())).get().getTimeZone().getEndTime();

        return this.getBreakTimeDuringSpecifiedPeriod(scheduledEnd, actualEnd);
    }

    /**
     * [prv-12]残業の間の休憩時間帯を取得する
     * @param overtimeHours
     * @return
     */
    private List<BreakTimeSheet> getBreakTimeBetweenOvertimes(List<OvertimeHour> overtimeHours) {
        List<BreakTimeSheet> result = new ArrayList<>();
        if (overtimeHours.size() <= 1) return result;

        List<TimeSpanForCalc> timeLine = overtimeHours.stream().map(OvertimeHour::getOvertimeHours).collect(Collectors.toList());
        new TimeSpanForCalc(
                overtimeHours.get(0).getOvertimeHours().getStart(),
                overtimeHours.get(overtimeHours.size() - 1).getOvertimeHours().getEnd()
        ).getNotDuplicatedWith(timeLine).forEach(bt -> {
            result.add(new BreakTimeSheet(new BreakFrameNo(1), bt.getStart(), bt.getEnd()));
        });
        return result;
    }

    /**
     * [prv-13]勤務１と勤務２の間の休憩時間帯を取得する
     * @param scheduled
     * @return
     */
    private List<BreakTimeSheet> getBreakTimeBetweenWork(List<TimeZoneWithWorkNo> scheduled) {
        if (scheduled.size() <= 1) return Collections.emptyList();

        TimeWithDayAttr work1End = scheduled.stream().filter(i -> i.getWorkNo().v() == 1).findFirst().get().getTimeZone().getEndTime();
        TimeWithDayAttr work2Start = scheduled.stream().filter(i -> i.getWorkNo().v() == 2).findFirst().get().getTimeZone().getStartTime();

        return this.getBreakTimeDuringSpecifiedPeriod(work1End, work2Start);
    }

    /**
     * [prv-14]指定した間の休憩時間帯を取得する
     * @param start
     * @param end
     * @return
     */
    private List<BreakTimeSheet> getBreakTimeDuringSpecifiedPeriod(TimeWithDayAttr start, TimeWithDayAttr end) {
        List<OvertimeHour> filteredOvertimes = this.overtimeHours.stream()
                .filter(i -> (start.lessThanOrEqualTo(i.getOvertimeHours().getStart()) && end.greaterThanOrEqualTo(i.getOvertimeHours().getStart()))
                        || (start.lessThanOrEqualTo(i.getOvertimeHours().getEnd()) && end.greaterThanOrEqualTo(i.getOvertimeHours().getEnd())))
                .sorted(Comparator.comparing(i -> i.getOvertimeHours().getStart()))
                .collect(Collectors.toList());
        if (filteredOvertimes.isEmpty()) return Collections.emptyList();

        List<BreakTimeSheet> result = new ArrayList<>();

        OvertimeHour overtime1 = filteredOvertimes.get(0);
        if (start.lessThan(overtime1.getOvertimeHours().getStart()))
            result.add(new BreakTimeSheet(new BreakFrameNo(1), start, overtime1.getOvertimeHours().getStart()));

        result.addAll(this.getBreakTimeBetweenOvertimes(filteredOvertimes));

        OvertimeHour finalOvertime = filteredOvertimes.get(filteredOvertimes.size() - 1);
        if (end.greaterThan(finalOvertime.getOvertimeHours().getEnd()))
            result.add(new BreakTimeSheet(new BreakFrameNo(1), overtime1.getOvertimeHours().getEnd(), end));

        return result;
    }

//    public interface Require {
//        /**
//         * 就業時間帯の設定を取得する
//         */
//        Optional<WorkTimeSetting> getWorkTimeSetting(String companyId, String code);
//
//        /**
//         * 1日分の勤怠時間を仮計算
//         */
//        IntegrationOfDaily tempCalculateOneDayAttendanceTime(CalculationParams params);
//
//        /**
//         * 所定時間設定を取得する
//         */
//        Optional<PredetemineTimeSetting> getPredetemineTimeSetting(String companyId, String workTimeCode);
//
//        IntegrationOfDaily process(IntegrationOfDaily domainDaily, ChangeDailyAttendance changeAtt);
//    }
}
