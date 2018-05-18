package nts.uk.ctx.at.request.dom.overtimeinstruct;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface OvertimeInstructRepository {
	public OverTimeInstruct getOvertimeInstruct(GeneralDate instructDate,String targetPerson);
	
	/**
	 * For RequestList230
	 * @param sId
	 * @return
	 */
	public List<OverTimeInstruct> getAllOverTimeInstructBySId(String sId, GeneralDate strDate, GeneralDate endDate);
}
