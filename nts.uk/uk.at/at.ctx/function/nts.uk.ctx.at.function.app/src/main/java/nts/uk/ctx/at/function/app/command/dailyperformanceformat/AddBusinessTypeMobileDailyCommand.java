package nts.uk.ctx.at.function.app.command.dailyperformanceformat;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.app.find.dailyperformanceformat.dto.BusinessTypeSFormatDetailDto;


/**
 * @author anhdt
 *
 */
@Data
@NoArgsConstructor
public class AddBusinessTypeMobileDailyCommand {
	private String businesstypeCode;
	private List<BusinessTypeSFormatDetailDto> businessTypeFormatDetailDtos;

}
