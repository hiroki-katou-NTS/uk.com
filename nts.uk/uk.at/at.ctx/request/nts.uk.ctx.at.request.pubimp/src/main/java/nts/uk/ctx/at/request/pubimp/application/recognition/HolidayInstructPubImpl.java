package nts.uk.ctx.at.request.pubimp.application.recognition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.holidayinstruction.HolidayInstruct;
import nts.uk.ctx.at.request.dom.application.holidayinstruction.HolidayInstructRepository;
import nts.uk.ctx.at.request.pub.application.recognition.HolidayInstructExport;
import nts.uk.ctx.at.request.pub.application.recognition.HolidayInstructPub;

@Stateless
public class HolidayInstructPubImpl implements HolidayInstructPub {
	@Inject
	private HolidayInstructRepository repo;

	/**
	 * For request list No.231
	 */
	@Override
	public List<HolidayInstructExport> acquireBreakIndication(String sId, GeneralDate startDate, GeneralDate endDate) {
		List<HolidayInstruct> data = repo.getAllHolidayInstructBySId(sId, startDate, endDate);
		
		if(data != null) {
			List<HolidayInstructExport> result = new ArrayList<>();
			
			for (HolidayInstruct item : data) {
				HolidayInstructExport export = HolidayInstructExport.createFromJavaType(item.getWorkContent().v(), item.getInputDate(), item.getTargetPerson(), 
						item.getInstructDate(), item.getInstructor(), item.getHolidayInstructionReason().v(), 
						item.getHoilidayWorkHour().v(), item.getStartClock(), item.getEndClock());
				
				result.add(export);
			}
			
			return result;
		}
		
		return Collections.emptyList();
	}

}
