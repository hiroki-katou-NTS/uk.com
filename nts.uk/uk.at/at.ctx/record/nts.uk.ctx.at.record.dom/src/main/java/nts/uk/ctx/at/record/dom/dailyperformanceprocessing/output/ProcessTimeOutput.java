package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.com.time.TimeWithDayAttr;
/**
 * 
 * @author dungdt
 * 処理中打刻時刻
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class ProcessTimeOutput {
	//丸め後の時刻
	private TimeWithDayAttr timeAfter;
	//時刻
	private TimeWithDayAttr timeOfDay;

}
