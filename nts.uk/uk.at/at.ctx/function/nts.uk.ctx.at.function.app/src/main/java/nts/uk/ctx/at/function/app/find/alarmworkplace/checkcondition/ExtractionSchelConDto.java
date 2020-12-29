package nts.uk.ctx.at.function.app.find.alarmworkplace.checkcondition;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.ExtractionScheduleCon;
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
    private Integer minValue;
    private Integer maxValue;
    private Integer operator;

    public static ExtractionSchelConDto fromDomain(ExtractionScheduleCon domain) {
        Integer minValue = null;
        Integer maxValue = null;
        Integer operator = null;
        if (domain.getCheckConditions() != null) {
            if (domain.getCheckConditions().isSingleValue()) {
                minValue = (Integer) ((CompareSingleValue)domain.getCheckConditions()).getValue();
                operator = ((CompareSingleValue)domain.getCheckConditions()).getCompareOpertor().value;
            } else {
                minValue = (Integer) ((CompareRange)domain.getCheckConditions()).getStartValue();
                maxValue = (Integer) ((CompareRange)domain.getCheckConditions()).getStartValue();
                operator = ((CompareRange)domain.getCheckConditions()).getCompareOperator().value;
            }
        }
        return new ExtractionSchelConDto(
                domain.getErrorAlarmWorkplaceId(),
                domain.getOrderNumber().value,
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
