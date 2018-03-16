package nts.uk.ctx.at.record.dom.remainingnumber.base;

import java.util.Optional;

public class DayNumber {

	protected Integer days;

	protected Optional<Integer> hours;

	public DayNumber() {
		super();
	}

	public DayNumber(int days, Integer hours) {
		this.days = days;
		this.hours = hours != null ? Optional.of(hours) : Optional.empty();
	}
	
}
