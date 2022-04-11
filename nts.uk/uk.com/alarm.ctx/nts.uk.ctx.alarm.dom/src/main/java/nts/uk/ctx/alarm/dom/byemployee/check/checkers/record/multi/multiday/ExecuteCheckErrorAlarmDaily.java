package nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.multi.multiday;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.IteratorUtil;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.ErrorAlarmCondition;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.service.AttendanceItemConvertFactory;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.snapshot.SnapShot;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 日別実績のエラーアラームチェックを実行する
 */
public class ExecuteCheckErrorAlarmDaily {

    public static Iterable<GeneralDate> execute(
            Require require,
            String employeeId,
            DatePeriod period,
            ErrorAlarmWorkRecordCode errorAlarmCode) {

        val master = require.getErrorAlarmWorkRecord(errorAlarmCode).orElse(null);
        if (master == null) {
            // TODO: 何らかの業務エラー出力が必要
            return Collections.emptyList();
        }

        // 親Aggregateがあれば存在するはず
        val condition = require.getErrorAlarmConditionById(master.getErrorAlarmCheckID())
                .orElseThrow(() -> new RuntimeException("not found: " + master.getCode()));

        return IteratorUtil.iterableFilter(period.iterate(), date -> {
            boolean isError = checkErrorExists(require, employeeId, date, condition);
            return isError ? Optional.of(date) : Optional.empty();
        });
    }

    /**
     * エラーがあればtrue
     * @param require
     * @param employeeId
     * @param date
     * @param condition
     * @return
     */
    private static boolean checkErrorExists(
            Require require,
            String employeeId,
            GeneralDate date,
            ErrorAlarmCondition condition) {

        val record = require.getIntegrationOfDaily(employeeId, date).orElse(null);
        if (record == null) {
            return false;
        }

        val workInfo = new WorkInfoOfDailyPerformance(
                record.getEmployeeId(),
                record.getYmd(),
                record.getWorkInformation());

        Optional<SnapShot> snapShot = Optional.empty();

        val getValueFromItemIds = funcGetValueFromItemIds(require, record);

        return condition.checkWith(workInfo, snapShot, getValueFromItemIds);
    }

    private static Function<List<Integer>, List<Double>> funcGetValueFromItemIds(
            Require require,
            IntegrationOfDaily record) {

        val converter = require.getAttendanceItemConvertFactory().createDailyConverter();
        converter.setData(record);

        return item -> {
            if (item.isEmpty()) return Collections.emptyList();
            return converter.convert(item).stream().map(ItemValue::valueAsDouble)
                    .collect(Collectors.toList());
        };
    }

    public interface Require {

        Optional<ErrorAlarmWorkRecord> getErrorAlarmWorkRecord(ErrorAlarmWorkRecordCode code);

        Optional<ErrorAlarmCondition> getErrorAlarmConditionById(String id);

        Optional<IntegrationOfDaily> getIntegrationOfDaily(String employeeId, GeneralDate date);

        AttendanceItemConvertFactory getAttendanceItemConvertFactory();
    }
}
