package nts.uk.screen.at.app.dailyperformance.correction.dto;

import java.util.List;

import lombok.Setter;
import lombok.Value;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordDto;
import nts.uk.screen.at.app.dailymodify.query.DailyModifyResult;

/**
 * @author hungTT
 *
 */

@Setter
@Value
public class DailyPerformanceCalculationDto {

	private List<DailyRecordDto> calculatedRows;
	
	private List<DailyModifyResult> resultValues;
	
	private DataResultAfterIU resultError;
	
}
