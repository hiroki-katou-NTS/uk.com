package nts.uk.ctx.at.request.dom.application.holidayinstruction;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface HolidayInstructRepository {
	public HolidayInstruct getHolidayWorkInstruct(GeneralDate instructDate,String targetPerson);
	
	/**
	 * For request list No.231
	 * @param sId
	 * @return
	 */
	public List<HolidayInstruct> getAllHolidayInstructBySId(String sId, GeneralDate strDate, GeneralDate endDate);
}
