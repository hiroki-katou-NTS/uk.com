package nts.uk.ctx.at.request.dom.application.workchange.output;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WorkTypeWorkTimeSelect {
	
	/**
	 * 選択する勤務種類
	 */
	private WorkType workType;
	
	/**
	 * 選択する就業時間帯
	 */
	private Optional<WorkTimeSetting> workTime;
	
}
