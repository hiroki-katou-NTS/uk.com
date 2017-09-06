package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.val;

/**
 * 控除時間帯
 * @author keisuke_hoshina
 *
 */
@RequiredArgsConstructor
public class DedcutionTimeSheet {

	private final List<DeductionItemOfTimeSheet> forDeductionTimeZoneList;
	private final List<DeductionItemOfTimeSheet> forRecordTimeZoneList;
	
	public void devideDeductionsBy(ActualWorkingTimeSheet actualWorkingTimeSheet) {

		devide(this.forDeductionTimeZoneList, actualWorkingTimeSheet);
		devide(this.forRecordTimeZoneList, actualWorkingTimeSheet);
	}
	
	private static void devide(
			List<DeductionItemOfTimeSheet> source,
			ActualWorkingTimeSheet devider) {
		
		val devided = new ArrayList<DeductionItemOfTimeSheet>();
		source.forEach(deductionTimeZone -> {
			devided.addAll(deductionTimeZone.devideIfContains(devider.start()));
			devided.addAll(deductionTimeZone.devideIfContains(devider.end()));
		});
		
		source.clear();
		source.addAll(devided);
	}
}
