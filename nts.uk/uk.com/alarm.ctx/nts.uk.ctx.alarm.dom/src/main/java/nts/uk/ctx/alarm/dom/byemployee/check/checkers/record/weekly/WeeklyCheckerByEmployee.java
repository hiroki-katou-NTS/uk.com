package nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.weekly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.gul.collection.IteratorUtil;
import nts.uk.ctx.alarm.dom.AlarmListCheckerCode;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.CheckingContextByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;

import java.util.Arrays;
import java.util.List;

/**
 * アラームリストのチェック条件（社員別・週次）
 */
@AllArgsConstructor
@Getter
public class WeeklyCheckerByEmployee implements DomainAggregate, AlarmListCheckerByEmployee {

    /** 会社ID */
    private final String companyId;

    /** コード */
    private final AlarmListCheckerCode code;

    /** 週別実績のエラーアラームチェック */
    private CheckErrorAlarmWeekly errorAlarm;

    /**
     * チェックする
     * @param require
     * @param context
     * @return
     */
    @Override
    public Iterable<AlarmRecordByEmployee> check(Require require, CheckingContextByEmployee context) {
        List<Iterable<AlarmRecordByEmployee>> alarmRecords = Arrays.asList(
                errorAlarm.check(require, context)
        );

        return IteratorUtil.flatten(alarmRecords);
    }
}
