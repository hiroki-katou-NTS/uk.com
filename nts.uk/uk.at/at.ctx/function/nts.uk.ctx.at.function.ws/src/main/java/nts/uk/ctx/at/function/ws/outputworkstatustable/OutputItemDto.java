package nts.uk.ctx.at.function.ws.outputworkstatustable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class OutputItemDto {
    private int rank;
    private String name;
    private boolean printTargetFlag;
    private int independentCalClassic;
    private int dailyMonthlyClassic;
    private int itemDetailAtt;
    private List<SelectionAttendanceItemDto> selectedAttItemList;
}
