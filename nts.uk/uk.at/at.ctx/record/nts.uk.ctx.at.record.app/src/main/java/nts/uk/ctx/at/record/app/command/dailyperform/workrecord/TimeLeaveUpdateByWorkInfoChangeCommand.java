package nts.uk.ctx.at.record.app.command.dailyperform.workrecord;

import java.util.Optional;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

@Builder
@Data
public class TimeLeaveUpdateByWorkInfoChangeCommand {

	/** 年月日: 年月日 */
	private String employeeId;
	
	/** 社員ID: 社員ID */
	private GeneralDate targetDate;
	
	/** 新しい勤務種類コード: 勤務種類コード */
	private WorkTypeCode newWorkTypeCode;
	
	/** 新しい就業時間帯コード: 就業時間帯コード */
	private Optional<WorkTimeCode> newWorkTimeCode;
}
