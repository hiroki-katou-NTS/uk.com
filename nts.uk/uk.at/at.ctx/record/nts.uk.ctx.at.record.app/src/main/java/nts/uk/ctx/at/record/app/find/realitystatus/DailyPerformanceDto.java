package nts.uk.ctx.at.record.app.find.realitystatus;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;

/**
 * @author dat.lh
 *
 */
@Value
@AllArgsConstructor
public class DailyPerformanceDto {
	private GeneralDate targetDate;
	private int performance;
	private boolean hasError;
}
