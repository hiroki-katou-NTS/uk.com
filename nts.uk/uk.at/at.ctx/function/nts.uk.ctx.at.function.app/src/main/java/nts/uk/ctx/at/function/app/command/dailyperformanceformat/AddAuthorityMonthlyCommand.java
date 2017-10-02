package nts.uk.ctx.at.function.app.command.dailyperformanceformat;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.app.find.dailyperformanceformat.dto.DailyAttendanceAuthorityDetailDto;

/**
 * @author nampt
 *
 */
@Data
@NoArgsConstructor
public class AddAuthorityMonthlyCommand {

	private String dailyPerformanceFormatCode;
	
	private List<DailyAttendanceAuthorityDetailDto> dailyAttendanceAuthorityDetailDtos;
}
