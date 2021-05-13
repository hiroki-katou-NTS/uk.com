package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.RoundingSet;
import nts.uk.ctx.at.shared.dom.worktime.common.RoundingTimeSetMemento;
import nts.uk.shr.com.enumcommon.NotUseAtr;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class RoundingTimeDto implements RoundingTimeSetMemento {

    private Integer attendanceMinuteLaterCalculate;

    private Integer leaveWorkMinuteAgoCalculate;

    private List<RoundingSetDto> roundingSets;

    @Override
    public void setAttendanceMinuteLaterCalculate(NotUseAtr attendanceMinutelater) {
        this.attendanceMinuteLaterCalculate = attendanceMinutelater.value;
    }

    @Override
    public void setLeaveWorkMinuteAgoCalculate(NotUseAtr leaveMinutelater) {
        this.leaveWorkMinuteAgoCalculate = leaveMinutelater.value;
    }

    @Override
    public void setRoundingSets(List<RoundingSet> roundingSets) {
        this.roundingSets = roundingSets.stream().map(i ->
                new RoundingSetDto(
                        new InstantRoundingDto(
                                i.getRoundingSet().getFontRearSection().value,
                                i.getRoundingSet().getRoundingTimeUnit().value),
                        i.getSection().value)).collect(Collectors.toList());

    }
}
