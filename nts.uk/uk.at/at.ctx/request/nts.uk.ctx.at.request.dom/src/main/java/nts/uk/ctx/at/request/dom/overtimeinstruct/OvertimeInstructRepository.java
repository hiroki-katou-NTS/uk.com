package nts.uk.ctx.at.request.dom.overtimeinstruct;

import nts.arc.time.GeneralDate;

public interface OvertimeInstructRepository {
	public OverTimeInstruct getOvertimeInstruct(GeneralDate instructDate,String targetPerson);
}
