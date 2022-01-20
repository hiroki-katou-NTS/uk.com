package nts.uk.ctx.at.shared.app.find.workrule.shiftmaster;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;

/**
 * @author anhdt
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkTimeSettingDto {
	
	private String workTimeCode;
	private String workTimeName;
	
	public WorkTimeSettingDto (WorkTimeSetting domain) {
		this.workTimeCode = domain.getWorktimeCode().v();
		this.workTimeName = domain.getWorkTimeDisplayName().getWorkTimeName().v();
	}
	
//	public WorkTimeSettingDto (String workTimeCode, String workTimeName) {
//		this.workTimeCode = workTimeCode;
//		this.workTimeName = workTimeName;
//	}
}
