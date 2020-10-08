package nts.uk.screen.at.app.ksm008.query.i;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.screen.at.app.ksm008.ConsecutiveAttendanceCom.ConsecutiveAttendanceComDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ksm008IStartInfoDto {
    ConsecutiveAttendanceComDto consecutiveAttendanceComDto;
    WorkingHourListDto workingHourListDto;
}
