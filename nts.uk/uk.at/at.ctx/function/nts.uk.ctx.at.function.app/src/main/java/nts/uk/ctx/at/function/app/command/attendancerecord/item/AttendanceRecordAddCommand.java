package nts.uk.ctx.at.function.app.command.attendancerecord.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author locph
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AttendanceRecordAddCommand {
	List<SingleAttendanceRecordAddCommand> singleList;
	List<CalculateAttendanceRecordAddCommand> calculateList;
}
