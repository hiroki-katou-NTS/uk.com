package nts.uk.ctx.at.shared.app.find.workrule.shiftmaster;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;

/**
 * @author anhdt
 *
 */
@Data
public class WorkTimeSettingDto {
	
	private String workTimeCode;
	private String workTimeName;
	
	public WorkTimeSettingDto (WorkTimeSetting domain) {
		this.workTimeCode = domain.getWorktimeCode().v();
		this.workTimeName = domain.getWorkTimeDisplayName().getWorkTimeName().v();
	}
	
	public WorkTimeSettingDto (String workTimeCode, String workTimeName) {
		this.workTimeCode = workTimeCode;
		this.workTimeName = workTimeName;
	}
}
