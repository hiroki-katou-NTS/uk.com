package nts.uk.ctx.at.request.dom.overtimeinstruct;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface OvertimeInstructRepository {
	public List<OverTimeInstruct> getOvertimeInstruct(GeneralDate inputDate,GeneralDate instructDate,String targetPerson);
}
