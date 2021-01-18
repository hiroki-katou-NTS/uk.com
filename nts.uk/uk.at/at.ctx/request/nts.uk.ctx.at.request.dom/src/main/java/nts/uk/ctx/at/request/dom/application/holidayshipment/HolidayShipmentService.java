package nts.uk.ctx.at.request.dom.application.holidayshipment;

import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface HolidayShipmentService {
	
	/**
	 * 基準申請日の決定
	 * @param recDate 振出日
	 * @param absDate 振休日
	 * @return
	 */
	public Optional<GeneralDate> detRefDate(Optional<GeneralDate> recDate, Optional<GeneralDate> absDate);
	
}
