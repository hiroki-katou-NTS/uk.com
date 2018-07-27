package nts.uk.ctx.at.request.pubimp.application.recognition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.overtimeinstruct.OverTimeInstruct;
import nts.uk.ctx.at.request.dom.overtimeinstruct.OvertimeInstructRepository;
import nts.uk.ctx.at.request.pub.application.recognition.OverTimeInstructExport;
import nts.uk.ctx.at.request.pub.application.recognition.OverTimeInstructPub;

@Stateless
public class OverTimeInstructPubImpl implements OverTimeInstructPub {
	@Inject
	private OvertimeInstructRepository repo;

	/**
	 * For RequestList230
	 */
	@Override
	public List<OverTimeInstructExport> acquireOverTimeWorkInstruction(String sId, GeneralDate startDate, GeneralDate endDate) {
		List<OverTimeInstruct> data = repo.getAllOverTimeInstructBySId(sId, startDate, endDate);
		
		if(data != null) {
			List<OverTimeInstructExport> result = new ArrayList<>();
			for (OverTimeInstruct item : data) {
				OverTimeInstructExport export = OverTimeInstructExport.createFromJavaType(item.getCompanyID(), item.getWorkContent().v(), 
						item.getInputDate(), item.getTargetPerson(), item.getInstructDate(), 
						item.getInstructor(), item.getOvertimeInstructReason().v(), item.getOvertimeHour().v(), 
						item.getStartClock(), item.getEndClock());
				
				result.add(export);
			}
			
			return result;
		}
		
		return Collections.emptyList();
	}

}
