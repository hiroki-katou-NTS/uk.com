package nts.uk.ctx.at.record.app.command.dailyperformanceformat;

import java.math.BigDecimal;
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
public class AddBusinessTypeDailyCommand {
	
	private String businesstypeCode;
	
	private BigDecimal sheetNo;
	
	private String sheetName;

	private List<BusinessTypeFormatDetailDto> businessTypeFormatDetailDtos;

}
