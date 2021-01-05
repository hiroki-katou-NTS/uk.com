package nts.uk.ctx.at.function.app.command.alarmworkplace.checkcondition.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.ExtractionMonthlyCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.enums.AverageRatio;

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

    // TODO HopNT
    public static ExtractionMonConCmd fromDomain(ExtractionMonthlyCon domain) {
        return new ExtractionMonConCmd(
                domain.getErrorAlarmWorkplaceId(),
                domain.getNo(),
                domain.getCheckMonthlyItemsType().value,
                domain.isUseAtr(),
                domain.getErrorAlarmCheckID(),
                 null,
                null,
                null,
               null,
                domain.getAverageRatio().isPresent() ? ((AverageRatio)domain.getAverageRatio().get()).value : null,
                domain.getMonExtracConName().v(),
                domain.getMessageDisp().v()
        );
    }

}
