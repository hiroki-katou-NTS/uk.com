package nts.uk.ctx.at.record.dom.workinformation;

import lombok.Builder;
import lombok.Getter;
import nts.arc.layer.dom.event.DomainEvent;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

@Builder
@Getter
public class WorkInfoChangeEvent extends DomainEvent {

	private String employeeId;
	
	private GeneralDate targetDate;
	
	private WorkTypeCode newWorkTypeCode;
	
	private WorkTimeCode newWorkTimeCode;
}
