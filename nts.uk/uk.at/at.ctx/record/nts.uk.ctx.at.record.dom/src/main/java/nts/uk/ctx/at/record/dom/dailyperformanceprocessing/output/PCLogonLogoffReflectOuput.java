package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.com.time.TimeWithDayAttr;
/**
 * 
 * @author dungdt 
 * 入退門反映先
 */
@Getter
@Setter
@NoArgsConstructor
public class PCLogonLogoffReflectOuput {

	// 時刻
		private TimeWithDayAttr timeOfDay;
}
