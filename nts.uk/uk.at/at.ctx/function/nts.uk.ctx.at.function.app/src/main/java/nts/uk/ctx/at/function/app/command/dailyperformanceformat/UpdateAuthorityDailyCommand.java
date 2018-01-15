package nts.uk.ctx.at.function.app.command.dailyperformanceformat;

import java.math.BigDecimal;
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
public class UpdateAuthorityDailyCommand {
	
	private String dailyPerformanceFormatCode;
	
	private String dailyPerformanceFormatName;
	
	private BigDecimal sheetNo;
	
	private String sheetName;
	
	private List<DailyAttendanceAuthorityDetailDto> dailyAttendanceAuthorityDetailDtos;
	
	private int isDefaultInitial;

}
