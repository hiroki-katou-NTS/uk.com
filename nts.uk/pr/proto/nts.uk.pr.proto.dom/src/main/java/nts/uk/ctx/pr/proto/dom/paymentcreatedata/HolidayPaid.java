package nts.uk.ctx.pr.proto.dom.paymentcreatedata;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

public class HolidayPaid extends AggregateRoot {
	/**
	 * Remain day.
	 */
	@Getter
	private int remainDays;
	
	/**
	 * Remain time.
	 */
	@Getter
	private int remainTime;
}
