package nts.uk.ctx.at.record.dom.breakorgoout;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.breakorgoout.enums.BreakType;

/**
 * 
 * @author nampt
 * 日別実績の休憩時間帯 - root
 *
 */
@Getter
@AllArgsConstructor
public class BreakTimeOfDailyPerformance extends AggregateRoot {
	
	private String employeeId;
	
	private BreakType breakType;
	
	private List<BreakTimeSheet> breakTimeSheets;
	
	private GeneralDate ymd;

}
