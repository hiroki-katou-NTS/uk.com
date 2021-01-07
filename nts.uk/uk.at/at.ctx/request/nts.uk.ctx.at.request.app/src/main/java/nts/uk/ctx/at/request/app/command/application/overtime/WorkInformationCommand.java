package nts.uk.ctx.at.request.app.command.application.overtime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

@AllArgsConstructor
@NoArgsConstructor
public class WorkInformationCommand {
	// 勤務種類コード
	public String workType;
	// 就業時間帯コード
	public String workTime;
	
	public WorkInformation toDomain() {
		WorkInformation workInformation = new WorkInformation(null, "");
		if (workTime != null) {
			workInformation.setWorkTimeCode(new WorkTimeCode(workTime));
		}
		workInformation.setWorkTypeCode(new WorkTypeCode(workType));
		return workInformation;
	}
}
