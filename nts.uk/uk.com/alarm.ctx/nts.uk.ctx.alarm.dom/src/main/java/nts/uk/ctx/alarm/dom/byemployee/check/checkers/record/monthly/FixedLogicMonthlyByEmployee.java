package nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.monthly;

import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import lombok.val;
import nts.gul.collection.IteratorUtil;
import nts.uk.ctx.alarm.dom.AlarmListAlarmMessage;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.period.CheckingPeriodMonthly;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import lombok.Value;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos.ApproveRootStatusForEmpImport;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.enums.ApprovalStatusForEmployee;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.month.ConfirmationMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.IntegrationOfMonthly;
import nts.uk.shr.com.time.closure.ClosureMonth;

/**
 * 固定のチェック条件(社員別・月次)
 */
@RequiredArgsConstructor
public enum FixedLogicMonthlyByEmployee {

    本人未確認(1, (c) -> {
        return () -> c.period.stream()
                .filter(closureMonth -> c.require.getConfirmationMonth(c.getEmployeeId(), closureMonth).isPresent())
                .map(closureMonth -> c.alarm(closureMonth)).iterator();
    }),

    管理者未承認(2, c -> {
        return () -> c.period.stream()
                .filter(closureMonth -> !c.require.getApprovalStateMonth(c.employeeId, closureMonth).get(0).getApprovalStatus().equals(ApprovalStatusForEmployee.APPROVED))
                .map(closureMonth -> c.alarm(closureMonth))
                .iterator();
    }),

    手入力(3,c -> {
        return IteratorUtil.iterableFlatten(c.period, closureMonth -> {
            val editState = c.require.getIntegrationOfMonthly(c.employeeId, closureMonth).getEditState();
            return IteratorUtil.iterable(editState, es -> c.alarm(closureMonth, es.getAttendanceItemId()));
        });
    }),

    ;

    public final int value;

    private final Function<Context, Iterable<AlarmRecordByEmployee>> logic;

    /**
     * チェックする
     * 実装パターンが定まらないので、仮置き
     * @param require
     * @param checkingPeriod
     * @return
     */
    public Iterable<AlarmRecordByEmployee> check(RequireCheck require, CheckingPeriodMonthly checkingPeriod) {
        return () -> new ArrayList<AlarmRecordByEmployee>().iterator();
    }
    
    private String getName() {
        return "チェック項目名";
    }

    private String getAlarmCondition() {
        return "アラーム条件";
    }

    @Value
    private class Context {
        RequireCheck require;
        String employeeId;
        List<ClosureMonth> period;
        AlarmListAlarmMessage message;
        
        public AlarmRecordByEmployee alarm(ClosureMonth closureMonth) {
            return new AlarmRecordByEmployee(
                    employeeId,
                    // TODO どのように　アラームリスト上でどう出すかは後回し
                    new DateInfo(closureMonth),
                    AlarmListCategoryByEmployee.MASTER,
                    getName(),
                    getAlarmCondition(),
                    "",
                    message);
        }

        public AlarmRecordByEmployee alarm(ClosureMonth closureMonth, Integer attendanceItemId) {
            return new AlarmRecordByEmployee(
                    employeeId,
                    new DateInfo(closureMonth),
                    AlarmListCategoryByEmployee.RECORD_DAILY,
                    require.getMonthlyItemName(attendanceItemId),
                    getAlarmCondition(),
                    "",
                    message);
        }
    }

    public interface RequireCheck {
        Optional<ConfirmationMonth> getConfirmationMonth(String employeeId, ClosureMonth closureMonth);

        List<ApproveRootStatusForEmpImport> getApprovalStateMonth(String employeeId, ClosureMonth closureMonth);

        IntegrationOfMonthly getIntegrationOfMonthly(String employeeId, ClosureMonth closureMonth);

        String getMonthlyItemName(Integer attendanceItemId);
    }
}
