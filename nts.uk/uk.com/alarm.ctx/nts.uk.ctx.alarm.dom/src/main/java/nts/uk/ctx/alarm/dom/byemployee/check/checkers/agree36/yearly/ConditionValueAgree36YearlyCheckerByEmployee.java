/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.alarm.dom.byemployee.check.checkers.agree36.yearly;

import java.util.HashMap;
import java.util.function.Function;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.Year;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueContext;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueLogic;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.AggregateAgreementTimeByYear;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.GetExcessTimesYear;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfYear;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeYear;

/**
 * 条件値チェック(社員別・36協定年次)
 * @author raiki_asada
 */
@RequiredArgsConstructor
public enum ConditionValueAgree36YearlyCheckerByEmployee implements ConditionValueLogic<ConditionValueAgree36YearlyCheckerByEmployee.Context>{
    限度時間(1, "限度時間", c -> getTimeValue(c, (agreementTime) -> agreementTime.getRecordTime())),
    特別条項による条件(2, "特別条項による条件", c -> getTimeValue(c, (agreementTime) -> agreementTime.getLimitTime())),
    超過回数(3, "超過回数", c -> (double)GetExcessTimesYear.get(c.require, c.employeeId, c.year).getExcessTimes()),
    ;
    
    public final int value;

    /**
     * 項目名
     */
    @Getter
    private final String name;

    private final Function<ConditionValueAgree36YearlyCheckerByEmployee.Context, Double> getValue;

    @Override
    public Double getValue(ConditionValueAgree36YearlyCheckerByEmployee.Context getValueContext) {
        return this.getValue.apply(getValueContext);
    }
    
    private static double getTimeValue(Context context, Function<AgreementTimeYear, AgreementTimeOfYear> function) {
        AgreementTimeYear timeForYear = AggregateAgreementTimeByYear.aggregate(
                context.require, 
                context.employeeId,
                // 36協定の閾値を取得するのに使う基準日　アラームを出す際には不要なので本日日付を渡す
                GeneralDate.today(), 
                context.year,
                // 年度集計をするときに、外から渡す際に使うMap　アラームを出す際には不要なので空を渡す
                new HashMap<>());
        
        return (double) function.apply(timeForYear).getTargetTime().v();
    }
        
    public interface Require extends AggregateAgreementTimeByYear.RequireM1, GetExcessTimesYear.RequireM1 {
    }
    
    @Value
    public static class Context implements ConditionValueContext {
        private Require require;
        private String employeeId;
        private Year year;
        
        @Override
        public AlarmListCategoryByEmployee getCategory() {
            return AlarmListCategoryByEmployee.AGREE36_YEARLY;
        }

        @Override
        public String getEmployeeId() {
            return this.employeeId;
        }

        @Override
        public DateInfo getDateInfo() {
            return new DateInfo(this.year);
        }
    }
}
