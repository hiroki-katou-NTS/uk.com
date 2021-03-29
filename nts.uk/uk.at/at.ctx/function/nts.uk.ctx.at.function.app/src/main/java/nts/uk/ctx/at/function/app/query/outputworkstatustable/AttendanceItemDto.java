package nts.uk.ctx.at.function.app.query.outputworkstatustable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AttendanceItemDto {
    private int operator;
    private String operatorName;

    private int attendanceItemId;
}
