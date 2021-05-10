package nts.uk.ctx.at.function.app.query.arbitraryperiodsummarytable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable.AttendanceItemDtoValue;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AttendanceArbResultDto {
    private String employeeId;

    private YearMonth yearMonth;

    private List<AttendanceItemDtoValue> attendanceItems;
}
