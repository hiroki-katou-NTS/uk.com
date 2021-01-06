package nts.uk.ctx.at.function.app.find.alarmworkplace.checkcondition;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.ExtractionMonthlyCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.primitivevalue.AverageNumberDays;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.primitivevalue.AverageNumberTimes;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.primitivevalue.AverageRatio;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.primitivevalue.AverageTime;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CountableTarget;
import nts.uk.ctx.at.shared.dom.workrecord.alarm.attendanceitemconditions.CompareRange;
import nts.uk.ctx.at.shared.dom.workrecord.alarm.attendanceitemconditions.CompareSingleValue;

import java.util.Collections;
import java.util.List;


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
    private List<Integer> additionAttendanceItems;
    private List<Integer> substractionAttendanceItems;

    public static ExtractionMonConDto fromDomain(ExtractionMonthlyCon domain) {
        String minValue = null;
        String maxValue = null;
        Integer operator = null;
        if (domain.getCheckConditions() != null) {
            if (domain.getCheckConditions().isSingleValue()) {
                switch (domain.getCheckMonthlyItemsType()) {
                    case AVERAGE_TIME:{
                        minValue = ((AverageTime) ((CompareSingleValue) domain.getCheckConditions()).getValue()).v().toString();
                        break;
                    }
                    case AVERAGE_NUMBER_DAY: {
                        minValue = ((AverageNumberDays) ((CompareSingleValue) domain.getCheckConditions()).getValue()).v().toString();
                        break;
                    }
                    case AVERAGE_NUMBER_TIME:{
                        minValue = ((AverageNumberTimes) ((CompareSingleValue) domain.getCheckConditions()).getValue()).v().toString();
                        break;
                    }
                    case AVERAGE_RATIO:{
                        minValue = ((AverageRatio) ((CompareSingleValue) domain.getCheckConditions()).getValue()).v().toString();
                        break;
                    }
                }
                operator = ((CompareSingleValue) domain.getCheckConditions()).getCompareOpertor().value;
            } else {
                switch (domain.getCheckMonthlyItemsType()) {
                    case AVERAGE_TIME:{
                        minValue = ((AverageTime) ((CompareRange) domain.getCheckConditions()).getStartValue()).v().toString();
                        maxValue = ((AverageTime) ((CompareRange) domain.getCheckConditions()).getEndValue()).v().toString();
                        break;
                    }
                    case AVERAGE_NUMBER_DAY:{
                        minValue = ((AverageNumberDays) ((CompareRange) domain.getCheckConditions()).getStartValue()).v().toString();
                        maxValue = ((AverageNumberDays) ((CompareRange) domain.getCheckConditions()).getEndValue()).v().toString();
                        break;
                    }
                    case AVERAGE_NUMBER_TIME:{
                        minValue = ((AverageNumberTimes) ((CompareRange) domain.getCheckConditions()).getStartValue()).v().toString();
                        maxValue = ((AverageNumberTimes) ((CompareRange) domain.getCheckConditions()).getEndValue()).v().toString();
                        break;
                    }
                    case AVERAGE_RATIO:{
                        minValue = ((AverageRatio) ((CompareRange) domain.getCheckConditions()).getStartValue()).v().toString();
                        maxValue = ((AverageRatio) ((CompareRange) domain.getCheckConditions()).getEndValue()).v().toString();
                        break;
                    }
                }
                operator = ((CompareRange) domain.getCheckConditions()).getCompareOperator().value;
            }
        }
        // TODO HopNT
        return new ExtractionMonConDto(
                domain.getErrorAlarmWorkplaceId(),
                domain.getNo(),
                domain.getCheckMonthlyItemsType().value,
                domain.isUseAtr(),
                domain.getErrorAlarmCheckID(),
                null,
                null,null,
                null,
                domain.getAverageRatio().isPresent() ? ((nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.enums.AverageRatio)domain.getAverageRatio().get()).value : null,
                domain.getMonExtracConName().v(),
                domain.getMessageDisp().v(),
                minValue,
                maxValue,
                operator,
                domain.getCheckedTarget().isPresent() ? ((CountableTarget) domain.getCheckedTarget().get()).getAddSubAttendanceItems().getAdditionAttendanceItems(): Collections.emptyList(),
                domain.getCheckedTarget().isPresent() ? ((CountableTarget) domain.getCheckedTarget().get()).getAddSubAttendanceItems().getSubstractionAttendanceItems(): Collections.emptyList()
        );
    }
    
}
