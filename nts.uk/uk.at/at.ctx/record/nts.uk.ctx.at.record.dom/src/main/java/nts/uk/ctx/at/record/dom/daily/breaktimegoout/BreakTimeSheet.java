package nts.uk.ctx.at.record.dom.daily.breaktimegoout;

import lombok.Value;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.BreakClassification;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;

/**
 * 休憩時間帯
 * @author keisuke_hoshina
 *
 */
@Value
public class BreakTimeSheet {
	
	private TimeSpanForCalc timeSheet;
	
	private AttendanceTime breakTime;
	
	private BreakClassification breakClassification;

	private BreakCategory breakCategory;
	
	/**
	 * 指定された時間帯に重複する休憩時間帯の重複時間（分）を返す
	 * @param baseTimeSheet
	 * @return　重複する時間（分）　　重複していない場合は0を返す
	 */
	public int calculateMinutesDuplicatedWith(TimeSpanForCalc baseTimeSheet) {
		
		return this.timeSheet.getDuplicatedWith(baseTimeSheet)
				.map(ts -> ts.lengthAsMinutes())
				.orElse(0);
	}
}
