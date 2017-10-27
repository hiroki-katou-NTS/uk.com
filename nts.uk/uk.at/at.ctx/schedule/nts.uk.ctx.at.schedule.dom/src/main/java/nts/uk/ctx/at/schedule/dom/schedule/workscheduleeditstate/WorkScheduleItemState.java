package nts.uk.ctx.at.schedule.dom.schedule.workscheduleeditstate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

/**
 * 勤務予定項目状態
 * 
 * @author sonnh1
 *
 */
@Getter
@AllArgsConstructor
public class WorkScheduleItemState extends AggregateRoot {
	private ScheduleEditState scheduleEditState;
	private String scheduleItemId;
	private GeneralDate ymd;
	private String sId;
}
