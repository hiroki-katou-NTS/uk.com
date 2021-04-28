package nts.uk.ctx.at.function.app.find.alarmworkplace.extractionrange;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.EndSpecify;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.ExtractionPeriodDaily;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.StartSpecify;

@Data
@AllArgsConstructor
public class WkpSingleMonthDto {

	/** 前・先区分  */
	private int monthPrevious;

	/** 月数 */
	private int monthNo;

	/** 当月とする */
	private boolean curentMonth;
}
