package nts.uk.ctx.at.request.dom.application.holidayshipment;

import nts.arc.time.GeneralDate;

public interface HolidayShipmentService {
	
	/**
	 * 基準申請日の決定
	 * @param recDate 振出日
	 * @param absDate 振休日
	 * @return
	 */
	public GeneralDate detRefDate(GeneralDate recDate, GeneralDate absDate);
	
}
