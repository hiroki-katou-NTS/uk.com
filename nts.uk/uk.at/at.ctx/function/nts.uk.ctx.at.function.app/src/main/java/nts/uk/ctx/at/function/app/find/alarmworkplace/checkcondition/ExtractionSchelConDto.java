package nts.uk.ctx.at.function.app.find.alarmworkplace.checkcondition;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.ExtractionScheduleCon;

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

    public static ExtractionSchelConDto fromDomain(ExtractionScheduleCon domain) {
        return new ExtractionSchelConDto(
                domain.getErrorAlarmWorkplaceId(),
                domain.getOrderNumber().value,
                domain.getCheckDayItemsType().value,
                domain.isUseAtr(),
                domain.getErrorAlarmCheckID(),
                domain.getComparisonCheckItems().getCheckTarget().isPresent() ? domain.getComparisonCheckItems().getCheckTarget().get().v() : null,
                domain.getComparisonCheckItems().getContrastType().isPresent() ? domain.getComparisonCheckItems().getContrastType().get().value : null,
                domain.getDaiExtracConName().v(),
                domain.getMessageDisp().v()
        );
    }
    
}
