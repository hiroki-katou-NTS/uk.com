package nts.uk.ctx.at.function.app.find.alarmworkplace.checkcondition;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.ExtractionScheduleCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.primitivevalue.*;
import nts.uk.ctx.at.shared.dom.workrecord.alarm.attendanceitemconditions.CompareRange;
import nts.uk.ctx.at.shared.dom.workrecord.alarm.attendanceitemconditions.CompareSingleValue;

@Data
@AllArgsConstructor
public class ExtractionSchelConDto {

    private String errorAlarmWorkplaceId;
    private int orderNumber;
    private int checkDayItemsType;
    private boolean useAtr;
    private String errorAlarmCheckID;
    private String checkTarget;
    private Integer contrastType;
    private String daiExtracConName;
    private String messageDisp;
    private String minValue;
    private String maxValue;
    private Integer operator;

    public static ExtractionSchelConDto fromDomain(ExtractionScheduleCon domain) {
        String minValue = null;
        String maxValue = null;
        Integer operator = null;
        if (domain.getCheckConditions() != null) {
            if (domain.getCheckConditions().isSingleValue()) {
                switch (domain.getCheckDayItemsType()) {
                    case CONTRAST: {
                        minValue = ((Comparison) ((CompareSingleValue) domain.getCheckConditions()).getValue()).v().toString();
                        break;
                    }
                    case NUMBER_PEOPLE_COMPARISON: {
                        minValue = ((NumberOfPeople) ((CompareSingleValue) domain.getCheckConditions()).getValue()).v().toString();
                        break;
                    }
                    case TIME_COMPARISON: {
                        minValue = ((Time) ((CompareSingleValue) domain.getCheckConditions()).getValue()).v().toString();
                        break;
                    }
                    case AMOUNT_COMPARISON: {
                        minValue = ((Amount) ((CompareSingleValue) domain.getCheckConditions()).getValue()).v().toString();
                        break;
                    }
                    case RATIO_COMPARISON: {
                        minValue = ((RatioComparison) ((CompareSingleValue) domain.getCheckConditions()).getValue()).v().toString();
                        break;
                    }
                }
                operator = ((CompareSingleValue) domain.getCheckConditions()).getCompareOpertor().value;
            } else {
                switch (domain.getCheckDayItemsType()) {
                    case CONTRAST: {
                        minValue = ((Comparison) ((CompareRange) domain.getCheckConditions()).getStartValue()).v().toString();
                        maxValue = ((Comparison) ((CompareRange) domain.getCheckConditions()).getEndValue()).v().toString();
                        break;
                    }
                    case NUMBER_PEOPLE_COMPARISON: {
                        minValue = ((NumberOfPeople) ((CompareRange) domain.getCheckConditions()).getStartValue()).v().toString();
                        maxValue = ((NumberOfPeople) ((CompareRange) domain.getCheckConditions()).getEndValue()).v().toString();
                        break;
                    }
                    case TIME_COMPARISON: {
                        minValue = ((Time) ((CompareRange) domain.getCheckConditions()).getStartValue()).v().toString();
                        maxValue = ((Time) ((CompareRange) domain.getCheckConditions()).getEndValue()).v().toString();
                        break;
                    }
                    case AMOUNT_COMPARISON: {
                        minValue = ((Amount) ((CompareRange) domain.getCheckConditions()).getStartValue()).v().toString();
                        maxValue = ((Amount) ((CompareRange) domain.getCheckConditions()).getEndValue()).v().toString();
                        break;
                    }
                    case RATIO_COMPARISON: {
                        minValue = ((RatioComparison) ((CompareRange) domain.getCheckConditions()).getStartValue()).v().toString();
                        maxValue = ((RatioComparison) ((CompareRange) domain.getCheckConditions()).getEndValue()).v().toString();
                        break;
                    }
                }
                operator = ((CompareRange) domain.getCheckConditions()).getCompareOperator().value;
            }
        }
        return new ExtractionSchelConDto(
                domain.getErrorAlarmWorkplaceId(),
                domain.getOrderNumber(),
                domain.getCheckDayItemsType().value,
                domain.isUseAtr(),
                domain.getErrorAlarmCheckID(),
                domain.getComparisonCheckItems().getCheckTarget().isPresent() ? domain.getComparisonCheckItems().getCheckTarget().get().v() : null,
                domain.getComparisonCheckItems().getContrastType().isPresent() ? domain.getComparisonCheckItems().getContrastType().get().value : null,
                domain.getDaiExtracConName().v(),
                domain.getMessageDisp().v(),
                minValue,
                maxValue,
                operator
        );
    }
    
}
