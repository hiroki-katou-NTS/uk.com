package nts.uk.ctx.at.record.dom.daily.midnight;

import lombok.Value;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;

/**
 * 法定内深夜時間
 * @author keisuke_hoshina
 *
 */
@Value
public class WithinStatutoryMidNightTime {
	private TimeWithCalculation time; 
}
