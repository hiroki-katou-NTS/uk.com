package nts.uk.screen.at.app.shift.workcycle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice.ReflectionImage;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycle;

import java.util.List;

/**
 * 勤務サイクル反映ダイアログDto
 * @author khai.dh
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkCycleReflectionDto {
	private List<WorkType> workTypes;
	private ReflectionImage reflectionImage;
	private WorkStyle workStyle;
	private List<WorkCycle> workCycleList;
}