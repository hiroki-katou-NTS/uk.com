package nts.uk.ctx.at.function.app.command.alarmworkplace.checkcondition.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.ExtractionMonthlyCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.enums.AverageRatio;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CountableTarget;

import java.util.Collections;
import java.util.List;

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

    private List<Integer> additionAttendanceItems;
    private List<Integer> substractionAttendanceItems;
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
                domain.getMessageDisp().v(),
                domain.getCheckedTarget().isPresent() ? ((CountableTarget) domain.getCheckedTarget().get()).getAddSubAttendanceItems().getAdditionAttendanceItems(): Collections.emptyList(),
                domain.getCheckedTarget().isPresent() ? ((CountableTarget) domain.getCheckedTarget().get()).getAddSubAttendanceItems().getSubstractionAttendanceItems(): Collections.emptyList()
        );
    }

}
