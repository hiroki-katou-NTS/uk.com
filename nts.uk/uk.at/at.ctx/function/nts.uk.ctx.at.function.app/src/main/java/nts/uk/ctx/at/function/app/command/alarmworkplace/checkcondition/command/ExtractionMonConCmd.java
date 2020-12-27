package nts.uk.ctx.at.function.app.command.alarmworkplace.checkcondition.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.ExtractionMonthlyCon;

@Data
@AllArgsConstructor
public class ExtractionMonConCmd {

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

    public static ExtractionMonConCmd fromDomain(ExtractionMonthlyCon domain) {
        return new ExtractionMonConCmd(
                domain.getErrorAlarmWorkplaceId(),
                domain.getNo().value,
                domain.getCheckMonthlyItemsType().value,
                domain.isUseAtr(),
                domain.getErrorAlarmCheckID(),
                domain.getAverageValueItem().getCheckTarget().isPresent() ? domain.getAverageValueItem().getCheckTarget().get().v() : null,
                domain.getAverageValueItem().getAverageNumberOfDays().isPresent() ? domain.getAverageValueItem().getAverageNumberOfDays().get().value : null,
                domain.getAverageValueItem().getAverageNumberOfTimes().isPresent() ? domain.getAverageValueItem().getAverageNumberOfTimes().get().value : null,
                domain.getAverageValueItem().getAverageTime().isPresent() ? domain.getAverageValueItem().getAverageTime().get().value : null,
                domain.getAverageValueItem().getAverageRatio().isPresent() ? domain.getAverageValueItem().getAverageRatio().get().value : null,
                domain.getMonExtracConName().v(),
                domain.getMessageDisp().v()
        );
    }

}
