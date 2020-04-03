package nts.uk.ctx.at.request.app.find.application.appabsence.dto;

import nts.uk.ctx.at.shared.app.find.specialholiday.specialholidayevent.SpecialHolidayEventDto;

public class SpecAbsenceParam {
	
	/**
	 * 特休枠NO
	 */
	public Integer frameNo;
	
	/**
	 * 事象に対する特別休暇
	 */
	public SpecialHolidayEventDto specHdEvent;
	
	/**
	 * 続柄コード
	 */
	public String relationCD;
	
}
