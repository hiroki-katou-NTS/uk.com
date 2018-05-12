package nts.uk.ctx.at.shared.app.find.worktime.dto;

import lombok.AllArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;

/**
 * 
 * @author Doan Duy Hung
 *
 */

@AllArgsConstructor
@Setter
public class WorkTimeDto {
	public String code;
	public String name;
	public String workTime1;
	public String workTime2;
	public String workAtr;
	public String remark;
	public Integer firstStartTime;
	public Integer firstEndTime;
	public Integer secondStartTime;
	public Integer secondEndTime;

	public static WorkTimeDto createFromWkTimeSet(WorkTimeSetting wkTimeSet) {

		return null;

	}
}
