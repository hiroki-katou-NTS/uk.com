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
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
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
     * 残業時間を計算のために勤務時間を判断する
     * @param timeZones List<時間帯(勤務NO付き)>
     * @return List<時間帯(勤務NO付き)>
     */
    public List<TimeZoneWithWorkNo> getWorkingHoursToCalculateOvertime(List<TimeZoneWithWorkNo> timeZones) {
        if (CollectionUtil.isEmpty(timeZones) || CollectionUtil.isEmpty(this.overtimeHours))
            return timeZones;
        timeZones.sort(Comparator.comparing(TimeZoneWithWorkNo::getWorkNo));
        TimeZoneWithWorkNo zone1 = timeZones.get(0);
        TimeZoneWithWorkNo zone2 = timeZones.size() > 1 ? timeZones.get(1) : null;
        TimeWithDayAttr startTime = this.overtimeHours.get(0).getOvertimeHours().getStart();
        TimeWithDayAttr endTime = this.overtimeHours.get(this.overtimeHours.size() - 1).getOvertimeHours().getEnd();
        if (zone1.getTimeZone().getStartTime().greaterThan(startTime)) zone1.getTimeZone().setStartTime(startTime);
        if (zone2 == null) {
            if (zone1.getTimeZone().getEndTime().lessThan(endTime))
                zone1.getTimeZone().setEndTime(endTime);
            return Collections.singletonList(zone1);
        }
        TimeWithDayAttr end1 = overtimeHours.stream()
                .filter(i -> i.getOvertimeHours().getEnd().lessThanOrEqualTo(zone2.getTimeZone().getStartTime()))
                .reduce((first, second) -> second)
                .map(i -> i.getOvertimeHours().getEnd()).orElse(null);
        if (end1 != null) {
            if (zone1.getTimeZone().getEndTime().lessThan(end1))
                zone1.getTimeZone().setEndTime(end1);
            TimeWithDayAttr start2 = overtimeHours.stream()
                    .filter(i -> i.getOvertimeHours().getStart().greaterThanOrEqualTo(end1))
                    .findFirst()
                    .map(i -> i.getOvertimeHours().getStart()).orElse(null);
            if (start2 != null) {
                if (zone2.getTimeZone().getStartTime().greaterThan(start2))
                    zone2.getTimeZone().setStartTime(start2);
            }
        }
        if (zone2.getTimeZone().getEndTime().lessThan(endTime))
            zone2.getTimeZone().setEndTime(endTime);
        return Arrays.asList(zone1, zone2);
    }

    /**
     * 残業時間を計算のために休憩時間帯を判断する
     * @param require
     * @param companyId 会社ID
     * @param employeeId 申請者ID
     * @param date 年月日
     * @param workInfo 勤務情報
     * @param workingHours 勤務時間List
     * @param breakTimes 休憩時間帯List
     * @return 休憩時間帯List
     */
    public List<BreakTimeSheet> getBreakTimeToCalculateOvertime(Require require,
                                                               String companyId,
                                                               String employeeId,
                                                               GeneralDate date,
                                                               WorkInformation workInfo,
                                                               List<TimeZoneWithWorkNo> workingHours,
                                                               List<BreakTimeSheet> breakTimes,
                                                               boolean managementMultipleWorkCycles) {
        List<TimeZoneWithWorkNo> predeterminedWorkingHours = new ArrayList<>(workingHours);
        if (workInfo.getWorkTimeCodeNotNull().isPresent()) {
            Optional<PredetemineTimeSetting> predTimeSet = require.getPredetemineTimeSetting(companyId, workInfo.getWorkTimeCode().v());
            if (predTimeSet.isPresent()) {
                predeterminedWorkingHours = predTimeSet.get().getPrescribedTimezoneSetting().getLstTimezone()
                        .stream().filter(i -> i.isUsed() && (i.getWorkNo() == 1 || managementMultipleWorkCycles))
                        .map(i -> new TimeZoneWithWorkNo(i.getWorkNo(), i.getStart().v(), i.getEnd().v()))
                        .collect(Collectors.toList());
            }
            Optional<WorkTimeSetting> workTimeSetting = require.getWorkTimeSetting(companyId, workInfo.getWorkTimeCode().v());
            if (workTimeSetting.isPresent() && workTimeSetting.get().getWorkTimeDivision().getWorkTimeMethodSet() == WorkTimeMethodSet.FLOW_WORK) {
                breakTimes = this.getFlowWorkBreakTime(require, employeeId, date, workInfo, predTimeSet, new ArrayList<>(workingHours));
            }
        }
        List<BreakTimeSheet> result = this.calculateNewBreakTimes(breakTimes, predeterminedWorkingHours, workingHours);
        if (result.size() > 10) throw new BusinessException("Msg_3236");
        return result;
    }

    /**
     * 流動勤務で 休憩時間帯を取得する
     * @param require
     * @param employeeId 申請者ID
     * @param date 年月日
     * @param workInfo 勤務情報
     * @param predTimeSet 所定時間設定
     * @param workingHours 勤務時間List
     * @return 休憩時間帯List
     */
    private List<BreakTimeSheet> getFlowWorkBreakTime(Require require,
                                                      String employeeId,
                                                      GeneralDate date,
                                                      WorkInformation workInfo,
                                                      Optional<PredetemineTimeSetting> predTimeSet,
                                                      List<TimeZoneWithWorkNo> workingHours) {
        predTimeSet.ifPresent(predetemineTimeSetting -> workingHours.get(workingHours.size() - 1).getTimeZone().setEndTime(predetemineTimeSetting.getEndDateClock()));
        CalculationParams params = new CalculationParams(
                employeeId,
                date,
                workInfo.getWorkTypeCode(),
                workInfo.getWorkTimeCode(),
                workingHours,
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList()
        );
        IntegrationOfDaily calcResult = require.tempCalculateOneDayAttendanceTime(params);
        return calcResult != null ? calcResult.getBreakTime().getBreakTimeSheets() : new ArrayList<>();
    }

    /**
     * 休憩時間帯Newを判断する
     * @param breakTimes 休憩時間帯List
     * @param predeterminedWorkingHours 勤務時間List（所定）
     * @param workingHours 勤務時間List（整理後）
     * @return 休憩時間帯List
     */
    private List<BreakTimeSheet> calculateNewBreakTimes(List<BreakTimeSheet> breakTimes, List<TimeZoneWithWorkNo> predeterminedWorkingHours, List<TimeZoneWithWorkNo> workingHours) {
        // ①休憩時間帯List(所定)
        // ②「勤務時間と残業時刻」の間隔は「休憩時間」になる（仕事なし）
        // ③２つ別の残業の間隔は「休憩時間」になる　（仕事なし）
        // ④休憩時間帯が軒並の場合、１つに合同する
        // ⑤休憩時間帯(所定）と残業時間帯が重複の場合、休憩時間帯が優先
        // ⑥勤務時間(所定）と勤務時間（整理）の間に残業時間ない場合、休憩時間になる

        List<TimeSpanForCalc> timeLine = new ArrayList<>();
        timeLine.addAll(predeterminedWorkingHours.stream().map(i -> new TimeSpanForCalc(i.getTimeZone().getStartTime(), i.getTimeZone().getEndTime())).collect(Collectors.toList()));
        this.overtimeHours.forEach(ot -> {
            if (timeLine.stream().noneMatch(t -> t.contains(ot.getOvertimeHours()))) {
                timeLine.add(ot.getOvertimeHours());
            }
        });
        timeLine.sort(Comparator.comparing(TimeSpanForCalc::getStart));

        if (!timeLine.isEmpty()) {
            new TimeSpanForCalc(timeLine.get(0).getStart(), timeLine.get(timeLine.size() - 1).getEnd())
                    .getNotDuplicatedWith(timeLine).forEach(bt -> {
                        breakTimes.add(new BreakTimeSheet(new BreakFrameNo(1), bt.getStart(), bt.getEnd()));
                    });
        }

        TimeZone workingTime = new TimeZone(null, null);
        workingHours.forEach(wh -> {
            if (workingTime.getStartTime() == null || workingTime.getStartTime().greaterThan(wh.getTimeZone().getStartTime())) {
                workingTime.setStartTime(wh.getTimeZone().getStartTime());
            }
            if (workingTime.getEndTime() == null || workingTime.getEndTime().lessThan(wh.getTimeZone().getEndTime())) {
                workingTime.setEndTime(wh.getTimeZone().getEndTime());
            }
        });
        new TimeSpanForCalc(workingTime.getStartTime(), workingTime.getEndTime())
                .getNotDuplicatedWith(timeLine).forEach(bt -> {
                    breakTimes.add(new BreakTimeSheet(new BreakFrameNo(1), bt.getStart(), bt.getEnd()));
                });

        breakTimes.sort(Comparator.comparing(BreakTimeSheet::getStartTime));

        List<BreakTimeSheet> result = new ArrayList<>();
        for (int i = 0; i < breakTimes.size(); i++) {
            if (!result.isEmpty()) {
                if (result.get(result.size() - 1).convertToTimeSpanForCalc().contains(breakTimes.get(i).convertToTimeSpanForCalc())) {
                    continue;
                } else if (breakTimes.get(i).convertToTimeSpanForCalc().contains(result.get(result.size() - 1).convertToTimeSpanForCalc())) {
                    result.get(result.size() - 1).setStartTime(breakTimes.get(i).getStartTime());
                    result.get(result.size() - 1).setEndTime(breakTimes.get(i).getEndTime());
                    continue;
                } else if (result.get(result.size() - 1).convertToTimeSpanForCalc().contains(breakTimes.get(i).getStartTime())) {
                    result.get(result.size() - 1).setEndTime(breakTimes.get(i).getEndTime());
                    continue;
                } else if (result.get(result.size() - 1).convertToTimeSpanForCalc().contains(breakTimes.get(i).getEndTime())) {
                    result.get(result.size() - 1).setStartTime(breakTimes.get(i).getStartTime());
                    continue;
                }
            }
            result.add(new BreakTimeSheet(new BreakFrameNo(result.size() + 1), breakTimes.get(i).getStartTime(), breakTimes.get(i).getEndTime()));
        }

        return result;
    }

    public interface Require {
        /**
         * 就業時間帯の設定を取得する
         */
        Optional<WorkTimeSetting> getWorkTimeSetting(String companyId, String code);

        /**
         * 1日分の勤怠時間を仮計算
         */
        IntegrationOfDaily tempCalculateOneDayAttendanceTime(CalculationParams params);

        /**
         * 所定時間設定を取得する
         */
        Optional<PredetemineTimeSetting> getPredetemineTimeSetting(String companyId, String workTimeCode);
    }
}
