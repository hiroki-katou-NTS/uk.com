package nts.uk.ctx.at.function.app.query.outputworkstatustable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ItemDto {
    private int rank;
    private String name;
    private boolean printTargetFlag;
    private int independentCalClassic;
    private String independentCalClassicName;
    private int dailyMonthlyClassic;
    private String dailyMonthlyClassicName;
    private int itemDetailAtt;
    private String itemDetailAttName;
    private List<AttendanceItemDto> attendanceItemList;
}
