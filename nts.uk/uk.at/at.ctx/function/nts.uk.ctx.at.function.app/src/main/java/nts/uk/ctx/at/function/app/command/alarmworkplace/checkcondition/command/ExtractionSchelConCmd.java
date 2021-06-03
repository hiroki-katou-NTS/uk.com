package nts.uk.ctx.at.function.app.command.alarmworkplace.checkcondition.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.ExtractionScheduleCon;

@Data
@AllArgsConstructor
public class ExtractionSchelConCmd {

    private String errorAlarmWorkplaceId;
    private int orderNumber;
    private int checkDayItemsType;
    private boolean useAtr;
    private String errorAlarmCheckID;
    private String checkTarget;
    private Integer contrastType;
    private String daiExtracConName;
    private String messageDisp;

    public static ExtractionSchelConCmd fromDomain(ExtractionScheduleCon domain) {
        return new ExtractionSchelConCmd(
                domain.getErrorAlarmWorkplaceId(),
                domain.getOrderNumber(),
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
