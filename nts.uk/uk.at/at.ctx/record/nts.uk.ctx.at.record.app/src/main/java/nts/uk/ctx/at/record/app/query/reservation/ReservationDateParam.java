package nts.uk.ctx.at.record.app.query.reservation;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

public class ReservationDateParam {
	
	@Getter
	@Setter
	private GeneralDate date;
	
	@Getter
	@Setter
	private int closingTimeFrame;

}
