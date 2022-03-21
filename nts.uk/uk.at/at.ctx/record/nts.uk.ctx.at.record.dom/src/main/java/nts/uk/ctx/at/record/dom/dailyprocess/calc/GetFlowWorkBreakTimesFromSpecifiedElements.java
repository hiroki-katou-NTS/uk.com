package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import nts.uk.ctx.at.record.dom.dailyprocess.calc.requestlist.PrevisionalForImp;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.CorrectDailyAttendanceService;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.ChangeDailyAttendance;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 指定した要素から流動休憩を取得する
 */
public class GetFlowWorkBreakTimesFromSpecifiedElements {
    /**
     * [1]取得する
     */
    public static List<BreakTimeSheet> get(Require require, PrevisionalForImp params) {
        Optional<IntegrationOfDaily> dailyRecord = CreateDailyRecordFromSpecifiedElementsService.create(require, params);
        if (!dailyRecord.isPresent()) return new ArrayList<>();

        ChangeDailyAttendance changeDailyAttendance = new ChangeDailyAttendance(
                false,
                false,
                true,
                false,
                ScheduleRecordClassifi.RECORD,
                false
        );

        IntegrationOfDaily correctedDailyRecord = CorrectDailyAttendanceService.processAttendanceRule(
                require,
                dailyRecord.get(),
                changeDailyAttendance
        );

        return correctedDailyRecord.getBreakTime().getBreakTimeSheets();
    }

    public interface Require extends CreateDailyRecordFromSpecifiedElementsService.Require {

    }
}
