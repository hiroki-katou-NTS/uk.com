package nts.uk.ctx.at.function.app.find.alarmworkplace.checkcondition;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.ExtractionMonthlyCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.primitivevalue.AverageNumberDays;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.primitivevalue.AverageNumberTimes;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.primitivevalue.AverageRatio;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.primitivevalue.AverageTime;
import nts.uk.ctx.at.shared.dom.workrecord.alarm.attendanceitemconditions.CompareRange;
import nts.uk.ctx.at.shared.dom.workrecord.alarm.attendanceitemconditions.CompareSingleValue;


@Data
@AllArgsConstructor
public class ExtractionMonConDto {

    private String errorAlarmWorkplaceId;
    private int no;
    private int checkMonthlyItemsType;
    private boolean useAtr;
    private String errorAlarmCheckID;
    private String checkTarget;
    private Integer averageNumberOfDays;
    private Integer averageNumberOfTimes;
    private Integer averageTime;
    private Integer averageRatio;
    private String monExtracConName;
    private String messageDisp;
    private String minValue;
    private String maxValue;
    private Integer operator;

    public static ExtractionMonConDto fromDomain(ExtractionMonthlyCon domain) {
        String minValue = null;
        String maxValue = null;
        Integer operator = null;
        if (domain.getCheckConditions() != null) {
            if (domain.getCheckConditions().isSingleValue()) {
                switch (domain.getCheckMonthlyItemsType()) {
                    case AVERAGE_TIME: {
                        minValue = ((AverageTime) ((CompareSingleValue) domain.getCheckConditions()).getValue()).v().toString();
                        break;
                    }
                    case AVERAGE_NUMBER_DAY: {
                        minValue = ((AverageNumberDays) ((CompareSingleValue) domain.getCheckConditions()).getValue()).v().toString();
                        break;
                    }
                    case AVERAGE_NUMBER_TIME: {
                        minValue = ((AverageNumberTimes) ((CompareSingleValue) domain.getCheckConditions()).getValue()).v().toString();
                        break;
                    }
                    case AVERAGE_RATIO: {
                        minValue = ((AverageRatio) ((CompareSingleValue) domain.getCheckConditions()).getValue()).v().toString();
                        break;
                    }
                }
                operator = ((CompareSingleValue) domain.getCheckConditions()).getCompareOpertor().value;
            } else {
                switch (domain.getCheckMonthlyItemsType()) {
                    case AVERAGE_TIME:
                    case TIME_FREEDOM:{
                        minValue = ((AverageTime) ((CompareRange) domain.getCheckConditions()).getStartValue()).v().toString();
                        maxValue = ((AverageTime) ((CompareRange) domain.getCheckConditions()).getEndValue()).v().toString();
                        break;
                    }
                    case AVERAGE_NUMBER_DAY:
                    case AVERAGE_DAY_FREE:{
                        minValue = ((AverageNumberDays) ((CompareRange) domain.getCheckConditions()).getStartValue()).v().toString();
                        maxValue = ((AverageNumberDays) ((CompareRange) domain.getCheckConditions()).getEndValue()).v().toString();
                        break;
                    }
                    case AVERAGE_NUMBER_TIME:
                    case AVERAGE_TIME_FREE:{
                        minValue = ((AverageNumberTimes) ((CompareRange) domain.getCheckConditions()).getStartValue()).v().toString();
                        maxValue = ((AverageNumberTimes) ((CompareRange) domain.getCheckConditions()).getEndValue()).v().toString();
                        break;
                    }
                    case AVERAGE_RATIO:
                    case AVERAGE_RATIO_FREE: {
                        minValue = ((AverageRatio) ((CompareRange) domain.getCheckConditions()).getStartValue()).v().toString();
                        maxValue = ((AverageRatio) ((CompareRange) domain.getCheckConditions()).getEndValue()).v().toString();
                        break;
                    }
                }
                operator = ((CompareRange) domain.getCheckConditions()).getCompareOperator().value;
            }
        }
        return new ExtractionMonConDto(
                domain.getErrorAlarmWorkplaceId(),
                domain.getNo(),
                domain.getCheckMonthlyItemsType().value,
                domain.isUseAtr(),
                domain.getErrorAlarmCheckID(),
                domain.getAverageValueItem().getCheckTarget().isPresent() ? domain.getAverageValueItem().getCheckTarget().get().v() : null,
                domain.getAverageValueItem().getAverageNumberOfDays().isPresent() ? domain.getAverageValueItem().getAverageNumberOfDays().get().value : null,
                domain.getAverageValueItem().getAverageNumberOfTimes().isPresent() ? domain.getAverageValueItem().getAverageNumberOfTimes().get().value : null,
                domain.getAverageValueItem().getAverageTime().isPresent() ? domain.getAverageValueItem().getAverageTime().get().value : null,
                domain.getAverageValueItem().getAverageRatio().isPresent() ? domain.getAverageValueItem().getAverageRatio().get().value : null,
                domain.getMonExtracConName().v(),
                domain.getMessageDisp().v(),
                minValue,
                maxValue,
                operator
        );
    }
    
}
