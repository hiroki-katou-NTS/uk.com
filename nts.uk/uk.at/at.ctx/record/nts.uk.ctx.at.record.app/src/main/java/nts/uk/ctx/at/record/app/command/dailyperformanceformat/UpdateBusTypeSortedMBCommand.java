package nts.uk.ctx.at.record.app.command.dailyperformanceformat;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.BusinessTypeSortedMBDto;
/**
 * 
 * @author anhdt
 *
 */
@Data
@NoArgsConstructor
public class UpdateBusTypeSortedMBCommand {
	List<BusinessTypeSortedMBDto> businessTypeSortedDtos;
}
