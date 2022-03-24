package nts.uk.ctx.at.request.dom.application.overtime;

import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.at.request.dom.adapter.CalculationParams;
import nts.uk.ctx.at.request.dom.adapter.dailyprocess.cal.PrevisionalForImpExport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime.ChildCareTimeZoneExport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime.OutingTimeZoneExport;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 流動勤務休憩時間帯を取得する
 */
public class GetFlowWorkBreakTimesService {

    /**
     * [1]取得する
     * @param require
     * @param workInfoParams 勤務情報パラメータ
     * @param multiOvertimeContent 複数回残業内容
     * @return
     */
    public static List<BreakTimeSheet> get(Require require, WorkInfoParams workInfoParams, OvertimeWorkMultipleTimes multiOvertimeContent) {
        List<BreakTimeSheet> result = new ArrayList<>();

        result.addAll(getRegularWorkBreakTimes(require, workInfoParams));
        workInfoParams.setBreakTimes(new ArrayList<>(result));
        result.addAll(getOvertimeWorkBreakTimes(require, workInfoParams, multiOvertimeContent));

        return CalculateBreakTimeZoneService.organizeBreakTimes(result);
    }

    /**
     * [prv-1]所定内の流動勤務休憩時間帯を取得する
     * @param require
     * @param workInfoParams
     * @return
     */
    private static List<BreakTimeSheet> getRegularWorkBreakTimes(Require require, WorkInfoParams workInfoParams) {
        PrevisionalForImpExport params = new PrevisionalForImpExport(
                workInfoParams.getEmployeeId(),
                workInfoParams.getDate(),
                workInfoParams.getWorkTimes().stream().collect(Collectors.toMap(i -> i.getWorkNo().v(), i -> new TimeZone(i.getTimeZone().getStartTime(), i.getTimeZone().getEndTime()))),
                workInfoParams.getWorkTypeCode(),
                workInfoParams.getWorkTimeCode(),
                new ArrayList<>(),
                workInfoParams.getOutTimes(),
                workInfoParams.getShortWorkTimes()
        );
        return require.getFlowWorkBreakTimes(params);
    }

    /**
     * [prv-2]所定外の流動勤務休憩時間帯を取得する
     * @param require
     * @param workInfoParams
     * @param multiOvertimeContent
     * @return
     */
    private static List<BreakTimeSheet> getOvertimeWorkBreakTimes(Require require, WorkInfoParams workInfoParams, OvertimeWorkMultipleTimes multiOvertimeContent) {
        Optional<TimeWithDayAttr> startTime = workInfoParams.getWorkTimes().stream()
                .filter(i -> i.getWorkNo().v() == 1)
                .findFirst().map(i -> i.getTimeZone().getStartTime());
        Optional<TimeWithDayAttr> overtimeStart = getFlowWorkOvertimeStartTime(require, workInfoParams);
        if (!overtimeStart.isPresent()) {
            overtimeStart = workInfoParams.getWorkTimes().stream()
                    .filter(i -> i.getWorkNo().v() == 1)
                    .findFirst().map(i -> i.getTimeZone().getEndTime());
        }
        TimeZone prescribedWorkingTime = new TimeZone(startTime.orElse(null), overtimeStart.orElse(null));
        return multiOvertimeContent.getBreakTimeOutsideSchedule(
                Arrays.asList(new TimeZoneWithWorkNo(1, prescribedWorkingTime.getStart().v(), prescribedWorkingTime.getEnd().v())),
                workInfoParams.getWorkTimes()
        );
    }

    /**
     * [prv-3]流動勤務の残業開始時刻を取得する
     * @param require
     * @param workInfoParams
     * @return
     */
    private static Optional<TimeWithDayAttr> getFlowWorkOvertimeStartTime(Require require, WorkInfoParams workInfoParams) {
        TimeWithDayAttr startTime = workInfoParams.getWorkTimes().stream().filter(i -> i.getWorkNo().v() == 1).findFirst().get().getTimeZone().getStartTime();
        TimeWithDayAttr endTime = getDayEndTime(require, workInfoParams).orElse(null);
        TimeZoneWithWorkNo workTime1 = new TimeZoneWithWorkNo(1, startTime.v(), endTime == null? null : endTime.v());
        CalculationParams params = new CalculationParams(
                workInfoParams.getEmployeeId(),
                workInfoParams.getDate(),
                workInfoParams.getWorkTypeCode(),
                workInfoParams.getWorkTimeCode(),
                Arrays.asList(workTime1),
                workInfoParams.getBreakTimes().stream().map(i -> new TimeZoneWithWorkNo(
                        i.getBreakFrameNo().v(),
                        i.getStartTime() == null ? null : i.getStartTime().v(),
                        i.getEndTime() == null ? null : i.getEndTime().v()
                )).collect(Collectors.toList()),
                workInfoParams.getOutTimes().stream().map(i -> new OutingTimeZoneExport(
                        i.getReasonForGoOut().value,
                        i.getGoOutWithTimeDay().map(PrimitiveValueBase::v).orElse(null),
                        i.getComeBackWithTimeDay().map(PrimitiveValueBase::v).orElse(null)
                )).collect(Collectors.toList()),
                workInfoParams.getShortWorkTimes().stream().map(i -> new ChildCareTimeZoneExport(
                        i.getChildCareAttr().value,
                        i.getStartTime().v(),
                        i.getEndTime().v()
                )).collect(Collectors.toList())
        );
        IntegrationOfDaily calResult = require.calculate(params);

        if (calResult == null
                || !calResult.getAttendanceTimeOfDailyPerformance().isPresent()
                || !calResult.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork().isPresent())
            return Optional.empty();
        return calResult.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getOvertimeStart();
    }

    /**
     * [prv-4]日付終了時刻を取得する
     * @param require
     * @param workInfoParams
     * @return
     */
    private static Optional<TimeWithDayAttr> getDayEndTime(Require require, WorkInfoParams workInfoParams) {
        if (workInfoParams.getWorkTimeCode() != null) {
            Optional<PredetemineTimeSetting> predetemineTimeSetting = require.findByWorkTimeCode(AppContexts.user().companyId(), workInfoParams.getWorkTimeCode().v());
            if (predetemineTimeSetting.isPresent()) {
                return Optional.of(predetemineTimeSetting.get().getEndDateClock());
            }
        }
        return Optional.empty();
    }

    public interface Require {
        List<BreakTimeSheet> getFlowWorkBreakTimes(PrevisionalForImpExport params);

        Optional<PredetemineTimeSetting> findByWorkTimeCode(String companyId, String workTimeCode);

        IntegrationOfDaily calculate(CalculationParams params);
    }
}
