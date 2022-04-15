/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.alarm.dom.byemployee.check.checkers.agree36.monthly;

import java.util.Optional;
import java.util.function.Function;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import nts.arc.time.YearMonth;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueContext;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueLogic;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfManagePeriod;

/**
 * 条件値チェック(社員別・36協定月次)
 *
 * @author raiki_asada
 */
@RequiredArgsConstructor
public enum ConditionValueAgree36MonthlyCheckerByEmployee implements ConditionValueLogic<ConditionValueAgree36MonthlyCheckerByEmployee.Context> {
    月間限度時間(1, "月間限度時間", c -> {
        return getTargetValue(c, (agreementTime) -> agreementTime.getAgreementTime().getAgreementTime());
    }),
    特別条項による条件(2, "特別条項による条件", c -> {
        return getTargetValue(c, (agreementTime) -> agreementTime.getLegalMaxTime().getAgreementTime());
    }),;

    public final int value;

    /**
     * 項目名
     */
    @Getter
    private final String name;

    private final Function<ConditionValueAgree36MonthlyCheckerByEmployee.Context, Double> getValue;

    @Override
    public Double getValue(Context getValueContext) {
        return this.getValue.apply(getValueContext);
    }

    private static double getTargetValue(Context context, Function<AgreementTimeOfManagePeriod, AttendanceTimeMonth> function) {
        return (double) context.require.getAgreementTimeOfManagePeriod(context.employeeId, context.yearMonth)
                .map(agreementTime -> function.apply(agreementTime).v()).orElse(0);
    }

    public interface Require {

        Optional<AgreementTimeOfManagePeriod> getAgreementTimeOfManagePeriod(String employeeId, YearMonth yearMonth);
    }

    @Value
    public static class Context implements ConditionValueContext {

        private Require require;
        private String employeeId;
        private YearMonth yearMonth;

        @Override
        public AlarmListCategoryByEmployee getCategory() {
            return AlarmListCategoryByEmployee.AGREE36_MONTHLY;
        }

        @Override
        public String getEmployeeId() {
            return this.employeeId;
        }

        @Override
        public DateInfo getDateInfo() {
            return new DateInfo(this.yearMonth);
        }
    }
}
