package nts.uk.ctx.at.function.app.command.dailyperformanceformat;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.app.find.dailyperformanceformat.dto.DailyAttendanceAuthorityDetailDto;

/**
 * @author anhdt
 *
 */
@Data
@NoArgsConstructor
public class UpdateAuthoritySDailyCommand {
	
	private String dailyPerformanceFormatCode;
	
	private String dailyPerformanceFormatName;
	
	private List<DailyAttendanceAuthorityDetailDto> dailyAttendanceAuthorityDetailDtos;
	
	private int isDefaultInitial;

}
