package nts.uk.ctx.at.request.dom.application.overtime;

import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 固定勤務休憩時間帯を取得する
 */
public class GetFixedWorkBreakTimesService {
    /**
     * [1]取得する
     * @param require
     * @param companyId
     * @param workTypeCode
     * @param workTimeCode
     * @param multiOvertimeContent
     * @param actualWorkTimes
     * @return
     */
    public static List<BreakTimeSheet> get(Require require, String companyId, String workTypeCode, String workTimeCode, OvertimeWorkMultipleTimes multiOvertimeContent, List<TimeZoneWithWorkNo> actualWorkTimes) {
        List<BreakTimeSheet> breakTimesWithinSchedule = getFixedWorkBreakTimeWithinSchedule(require, companyId, workTypeCode, workTimeCode);

        Optional<PredetemineTimeSetting> predetemineTimeSetting = require.findByWorkTimeCode(AppContexts.user().companyId(), workTimeCode);
        if (predetemineTimeSetting.isPresent()) {
            return Collections.emptyList();
        }

        List<TimeZoneWithWorkNo> scheduledWorkTimes = predetemineTimeSetting.get().getAvailableTimeZone().stream()
                .map(i -> new TimeZoneWithWorkNo(i.getWorkNo(), i.getStart().v(), i.getEnd().v()))
                .collect(Collectors.toList());

        List<BreakTimeSheet> breakTimesOutsideSchedule = getFixedWorkBreakTimeOutsideSchedule(multiOvertimeContent, scheduledWorkTimes, actualWorkTimes);

        List<BreakTimeSheet> breakTimes = new ArrayList<>();
        breakTimes.addAll(breakTimesWithinSchedule);
        breakTimes.addAll(breakTimesOutsideSchedule);

        return CalculateBreakTimeZoneService.organizeBreakTimes(breakTimes);
    }

    /**
     * [prv-1]所定内の固定勤務休憩時間帯を取得する
     * @param companyId
     * @param workTypeCode
     * @param workTimeCode
     * @return
     */
    private static List<BreakTimeSheet> getFixedWorkBreakTimeWithinSchedule(Require require, String companyId, String workTypeCode, String workTimeCode) {
        List<DeductionTime> breakTimes = require.getBreakTimes(companyId, workTypeCode, workTimeCode, Optional.empty(), Optional.empty());
        breakTimes.sort(Comparator.comparing(TimeZone::getStart));
        List<BreakTimeSheet> result = new ArrayList<>();
        for (int i = 0; i < breakTimes.size(); i++) {
            result.add(new BreakTimeSheet(new BreakFrameNo(i + 1), breakTimes.get(i).getStart(), breakTimes.get(i).getEnd()));
        }
        return result;
    }

    /**
     * [prv-2]所定外の固定勤務休憩時間帯を取得する
     * @param multiOvertimeContent
     * @param scheduled
     * @param actual
     * @return
     */
    private static List<BreakTimeSheet> getFixedWorkBreakTimeOutsideSchedule(OvertimeWorkMultipleTimes multiOvertimeContent, List<TimeZoneWithWorkNo> scheduled, List<TimeZoneWithWorkNo> actual) {
        return multiOvertimeContent.getBreakTimeOutsideSchedule(scheduled, actual);
    }

    public interface Require {
        Optional<PredetemineTimeSetting> findByWorkTimeCode(String companyId, String workTimeCode);

        // 01-01_休憩時間帯を取得する
        List<DeductionTime> getBreakTimes(String companyID, String workTypeCode, String workTimeCode, Optional<TimeWithDayAttr> opStartTime, Optional<TimeWithDayAttr> opEndTime);
    }
}
