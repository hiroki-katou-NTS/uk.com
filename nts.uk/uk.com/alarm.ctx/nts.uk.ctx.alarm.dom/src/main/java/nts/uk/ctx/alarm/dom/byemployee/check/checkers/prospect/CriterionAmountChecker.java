package nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.alarm.dom.AlarmListAlarmMessage;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueContext;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmount;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountForEmployeeGettingService;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountList;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountValue;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;

import java.util.Optional;

/**
 * 目安金額との比較チェックをする
 */
public class CriterionAmountChecker {
    public static String CHECK_NAME = "（予定時間＋総労働時間）×基準単価と目安金額の比較";
    public static Optional<AlarmRecordByEmployee> check(
            Require require, ConditionValueContext context, GeneralDate baseDate, int amountValue, AlarmListAlarmMessage message){

        CriterionAmount criterionAmount =
                CriterionAmountForEmployeeGettingService.get(require, new EmployeeId(context.getEmployeeId()), baseDate);
        val list = context.getCategory() == AlarmListCategoryByEmployee.PROSPECT_MONTHLY
                ? criterionAmount.getMonthly()
                : criterionAmount.getYearly();
        val stepOfCA = list.getStepOfEstimateAmount(require, new CriterionAmountValue(amountValue));

        if(stepOfCA.getExceeded().v() == 0){
            return Optional.empty();
        }

        return Optional.of(
                new AlarmRecordByEmployee(
                context.getEmployeeId(),
                context.getDateInfo(),
                context.getCategory(),
                CHECK_NAME,
                "目安金額:" + stepOfCA.getExceeded().v() + " 実績:" + amountValue,
                null,
                message));
    }

    public interface Require extends
            CriterionAmountForEmployeeGettingService.Require,
            CriterionAmountList.Require {

    }
}
