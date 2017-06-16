package nts.uk.ctx.at.shared.dom.worktime;

import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * 就業時間帯勤務区分
 * @author Doan Duy Hung
 *
 */

@Value
@EqualsAndHashCode(callSuper = false)
public class WorkTimeDivision {
	
	private WorkTimeDailyAtr workTimeDailyAtr;
	
	private WorkTimeMethodSet workTimeMethodSet;

	public WorkTimeDivision(WorkTimeDailyAtr workTimeDailyAtr, WorkTimeMethodSet workTimeMethodSet) {
		super();
		this.workTimeDailyAtr = workTimeDailyAtr;
		this.workTimeMethodSet = workTimeMethodSet;
	}
}
