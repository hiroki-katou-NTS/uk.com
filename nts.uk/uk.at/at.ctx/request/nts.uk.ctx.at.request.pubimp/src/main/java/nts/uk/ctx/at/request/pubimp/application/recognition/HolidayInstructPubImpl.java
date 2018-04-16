package nts.uk.ctx.at.request.pubimp.application.recognition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.holidayinstruction.HolidayInstruct;
import nts.uk.ctx.at.request.dom.application.holidayinstruction.HolidayInstructRepository;
import nts.uk.ctx.at.request.pub.application.recognition.HolidayInstructPub;

@Stateless
public class HolidayInstructPubImpl implements HolidayInstructPub {
	@Inject
	private HolidayInstructRepository repo;

	/**
	 * For request list No.231
	 */
	@Override
	public List<GeneralDate> acquireBreakIndication(String sId, GeneralDate startDate, GeneralDate endDate) {
		List<GeneralDate> date = new ArrayList<>();
		List<HolidayInstruct> data = repo.getAllHolidayInstructBySId(sId);
		
		if(data != null) {
			for (HolidayInstruct item : data) {
				if(item.getInstructDate().after(startDate) && item.getInstructDate().before(endDate)) {
					date.add(item.getInstructDate());
				}
			}
			
			return date;
		}
		
		return Collections.emptyList();
	}

}
