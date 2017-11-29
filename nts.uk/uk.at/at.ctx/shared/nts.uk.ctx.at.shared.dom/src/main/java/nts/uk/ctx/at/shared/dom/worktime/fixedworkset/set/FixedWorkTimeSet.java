package nts.uk.ctx.at.shared.dom.worktime.fixedworkset.set;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.OverTimeHourSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.WorkTimeOfTimeSheetSet;

/**
 * 固定勤務時間帯設定
 * @author keisuke_hoshina
 *
 */
@Value
public class FixedWorkTimeSet {

	/** 就業時間帯 */
	private final List<WorkTimeOfTimeSheetSet> workingHours;
	/** 残業時間帯*/
	private final List<OverTimeHourSet> overTimeHours;
	
}
