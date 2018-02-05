package nts.uk.ctx.at.request.infra.repository.holidayinstruct;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.holidayinstruction.HolidayInstruct;
import nts.uk.ctx.at.request.dom.application.holidayinstruction.HolidayInstructRepository;

@Stateless
public class JpaHolidayInstructRepository implements HolidayInstructRepository {

	@Override
	public HolidayInstruct getHolidayWorkInstruct(GeneralDate instructDate, String targetPerson) {
		// TODO Auto-generated method stub
		return null;
	}

}
