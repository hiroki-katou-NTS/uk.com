package nts.uk.ctx.at.shared.dom.remainingnumber.base;

import java.util.Optional;

public class DayNumber {

	protected Float days;

	protected Optional<Integer> minutes;

	public DayNumber() {
		super();
	}

	public DayNumber(float days, Integer minutes) {
		this.days = days;
		this.minutes = minutes != null ? Optional.of(minutes) : Optional.empty();
	}
	
}
