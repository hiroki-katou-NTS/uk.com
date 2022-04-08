package nts.uk.ctx.at.request.dom.application.overtime;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeMethodSet;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 休憩時間帯を計算する
 */
public class CalculateBreakTimeZoneService {
    /**
     * [1]取得する
     * @param require
     * @param companyId
     * @param params
     * @param multiOvertimeContent
     * @return
     */
    public static List<BreakTimeSheet> get(Require require, String companyId, WorkInfoParams params, OvertimeWorkMultipleTimes multiOvertimeContent) {
        if (params.getWorkTimeCode() == null) return Collections.emptyList();
        Optional<WorkTimeSetting> workTime = require.findByCode(companyId, params.getWorkTimeCode().v());
        if (!workTime.isPresent()) return Collections.emptyList();

        List<BreakTimeSheet> result;
        if (workTime.get().getWorkTimeDivision().getWorkTimeMethodSet() == WorkTimeMethodSet.FIXED_WORK) {
            result = GetFixedWorkBreakTimesService.get(
                    require,
                    companyId,
                    params.getWorkTypeCode().v(),
                    params.getWorkTimeCode() == null? null : params.getWorkTimeCode().v(),
                    multiOvertimeContent,
                    params.getWorkTimes()
            );
        } else {
            result = GetFlowWorkBreakTimesService.get(require, params, multiOvertimeContent);
        }

        if (result.size() > 10) throw new BusinessException("Msg_3236");

        return result;
    }

    /**
     * [2]休憩時間帯を整理する
     * @param breakTimes
     * @return
     */
    public static List<BreakTimeSheet> organizeBreakTimes(List<BreakTimeSheet> breakTimes) {
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

    public interface Require extends GetFixedWorkBreakTimesService.Require, GetFlowWorkBreakTimesService.Require {
        Optional<WorkTimeSetting> findByCode(String companyId, String workTimeCode);
    }
}
