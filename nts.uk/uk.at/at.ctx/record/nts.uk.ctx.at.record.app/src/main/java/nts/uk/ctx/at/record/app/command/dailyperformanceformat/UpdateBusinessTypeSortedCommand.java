package nts.uk.ctx.at.record.app.command.dailyperformanceformat;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.BusinessTypeSortedDto;

/**
 * @author nampt
 *
 */
@Data
@NoArgsConstructor
public class UpdateBusinessTypeSortedCommand {
	
	List<BusinessTypeSortedDto> businessTypeSortedDtos;

}
