/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.alarm.dom.byemployee.check.checkers.agree36.multimonth;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Value;
import lombok.val;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.alarm.dom.AlarmListAlarmMessage;
import nts.uk.ctx.alarm.dom.AlarmListCheckerCode;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.CheckingContextByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueExpression;

/**
 * アラームリストのチェック条件（社員別・36協定複数月）
 */
@Value
public class Agree36MultiMonthCheckerByEmployee implements DomainAggregate, AlarmListCheckerByEmployee {
    private String companyId;
    private AlarmListCheckerCode checkerCode;
    private String name;
    private Detail detail;

    @Override
    public Iterable<AlarmRecordByEmployee> check(Require require, CheckingContextByEmployee context) {
        String employeeId = context.getTargetEmployeeId();
        YearMonth yearMonth = null;

        val conditionValueContext = new ConditionValueAgree36MultiMonthCheckerByEmployee.Context(require, employeeId, yearMonth);

        return () -> this.detail.checkIfEnabled(conditionValueContext).iterator();
    }

    /**
     * アラームリストのチェック条件（社員別・36協定複数月）の詳細 
     * ・閾値 
     * ・使用区分 
     * ・メッセージ を登録出来るようにするために使う
     *
     * @author raiki_asada
     */
    private class Detail {

        private ConditionValueAgree36MultiMonthCheckerByEmployee logic;
        private ConditionValueExpression expression;
        private boolean enabled;
        private AlarmListAlarmMessage message;

        /**
         * 条件に該当するか
         *
         * @param context コンテキスト情報
         * @return チェック対象値が条件に該当すればtrue
         */
        public List<AlarmRecordByEmployee> checkIfEnabled(ConditionValueAgree36MultiMonthCheckerByEmployee.Context context) {

            if (!enabled) {
                return new ArrayList<>();
            }

            val actualValues = logic.getValue(context);
            if (actualValues.isEmpty()) {
                return new ArrayList<>();
            }

            return actualValues.stream()
                    .filter(value -> !this.expression.matches(value.getValue()))
                    .map(value -> this.createRecord(context.getEmployeeId(), value))
                    .collect(Collectors.toList());
        }

        private AlarmRecordByEmployee createRecord(String employeeId, ConditionValueAgree36MultiMonthCheckerByEmployee.ValueEachMonth value) {
            return new AlarmRecordByEmployee(
                    employeeId,
                    new DateInfo(value.getPeriod()),
                    AlarmListCategoryByEmployee.AGREE36_MULT_MONTH,
                    this.logic.getName(),
                    expression.toText(),
                    String.format("実績: %.2f", value.getValue()),
                    message);
        }
    }
    
    public interface RequireCheck extends ConditionValueAgree36MultiMonthCheckerByEmployee.Require {
    }

}
