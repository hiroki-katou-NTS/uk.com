package nts.uk.ctx.at.record.app.command.dailyperformanceformat;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.BusinessTypeFormatDetailDto;

/**
 * @author nampt
 *
 */
@Data
@NoArgsConstructor
public class UpdateBusinessTypeMonthlyCommand {
	
	private String businesstypeCode;

	private List<BusinessTypeFormatDetailDto> businessTypeFormatDetailDtos;
}
