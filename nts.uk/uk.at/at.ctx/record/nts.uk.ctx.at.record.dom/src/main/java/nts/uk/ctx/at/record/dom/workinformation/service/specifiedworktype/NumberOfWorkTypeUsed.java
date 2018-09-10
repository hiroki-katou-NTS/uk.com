package nts.uk.ctx.at.record.dom.workinformation.service.specifiedworktype;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

@Getter
@Setter
public class NumberOfWorkTypeUsed {
	
	private WorkTypeCode workTypeCode;
	
	private AttendanceDaysMonth attendanceDaysMonth;

}
