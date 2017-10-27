package nts.uk.ctx.at.record.dom.shorttimework;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author nampt
 * 日別実績の短時間勤務時間帯 - root
 *
 */
@Getter
public class ShortTimeOfDailyPerformance extends AggregateRoot {
	
	private String employeeId;
	
	private List<ShortWorkingTimeSheet> shortWorkingTimeSheets;
	
	private GeneralDate ymd;

}
