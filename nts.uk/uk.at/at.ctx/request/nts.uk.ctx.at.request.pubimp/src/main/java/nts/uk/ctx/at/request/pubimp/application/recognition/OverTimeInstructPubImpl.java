package nts.uk.ctx.at.request.pubimp.application.recognition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.overtimeinstruct.OverTimeInstruct;
import nts.uk.ctx.at.request.dom.overtimeinstruct.OvertimeInstructRepository;
import nts.uk.ctx.at.request.pub.application.recognition.OverTimeInstructPub;

@Stateless
public class OverTimeInstructPubImpl implements OverTimeInstructPub {
	@Inject
	private OvertimeInstructRepository repo;

	/**
	 * For RequestList230
	 */
	@Override
	public List<GeneralDate> acquireOverTimeWorkInstruction(String sId, GeneralDate startDate, GeneralDate endDate) {
		List<GeneralDate> date = new ArrayList<>();
		List<OverTimeInstruct> data = repo.getAllOverTimeInstructBySId(sId);
		
		if(data != null) {
			for (OverTimeInstruct item : data) {
				if(item.getInstructDate().after(startDate) && item.getInstructDate().before(endDate)) {
					date.add(item.getInstructDate());
				}
			}
			
			return date;
		}
		
		return Collections.emptyList();
	}

}
