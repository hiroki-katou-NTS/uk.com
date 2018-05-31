package nts.uk.ctx.at.record.dom.worktime;

import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import nts.arc.layer.dom.event.DomainEvent;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/** Event：出退勤時刻を補正する */
@Builder
@Getter
public class TimeLeaveUpdateEvent extends DomainEvent {
	
	/** 年月日: 年月日 */
	private String employeeId;
	
	/** 社員ID: 社員ID */
	private GeneralDate targetDate;
	
	/** 新しい勤務種類コード: 勤務種類コード */
	private WorkTypeCode newWorkTypeCode;
	
	/** 新しい就業時間帯コード: 就業時間帯コード */
	private Optional<WorkTimeCode> newWorkTimeCode;
}
