package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.shr.com.time.TimeWithDayAttr;
/**
 * 控除項目の時間帯
 * @author keisuke_hoshina
 *
 */

@RequiredArgsConstructor

public class TimeSheetOfDeductionItem {
	@Getter
	private final TimeSpanForCalc span;
//	private final DedcutionClassification deductionClassification;
//	private final BreakClassification breakClassification;


	public TimeWithDayAttr start() {
		return this.span.getStart();
	}
	
	public TimeWithDayAttr end() {
		return this.span.getEnd();
	}
	
	public List<TimeSheeOfDeductionItem> devideAt(TimeWithDayAttr baseTime) {
		return this.span.timeDivide(baseTime).stream()
				.map(t -> new TimeSheeOfDeductionItem(t))
				.collect(Collectors.toList());
	}
	
	public List<TimeSheeOfDeductionItem> devideIfContains(TimeWithDayAttr baseTime) {
		if (this.contains(baseTime)) {
			return this.devideAt(baseTime);
		} else {
			return Arrays.asList(this);
		}
	}
	
	public boolean contains(TimeWithDayAttr baseTime) {
		return this.span.contains(baseTime);
	}
}
