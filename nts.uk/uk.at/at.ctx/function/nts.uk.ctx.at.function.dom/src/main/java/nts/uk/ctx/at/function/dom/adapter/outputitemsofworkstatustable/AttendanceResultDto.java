package nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable;

import lombok.*;
import nts.arc.time.GeneralDate;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AttendanceResultDto {
    private String employeeId;

    private GeneralDate workingDate;

    private List<AttendanceItemDtoValue> attendanceItems;
}
