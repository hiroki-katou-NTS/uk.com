package nts.uk.screen.at.app.dailyperformance.correction.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordDto;
import nts.uk.screen.at.app.dailymodify.query.DailyModifyResult;

/**
 * @author hungTT
 *
 */

@Data
@AllArgsConstructor
public class DailyPerformanceCalculationDto {

	private List<DailyRecordDto> calculatedRows;
	
	private List<DailyModifyResult> resultValues;
	
	private DataResultAfterIU resultError;
	
}
