package nts.uk.ctx.at.record.dom.breakorgoout;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author nampt
 * 日別実績の外出時間帯 - root
 *
 */
@AllArgsConstructor
@Getter
public class OutingTimeOfDailyPerformance extends AggregateRoot {
	
	private String employeeId;
	
	private List<OutingTimeSheet> outingTimeSheets;
	
	private GeneralDate ymd;

}
