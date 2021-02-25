package nts.uk.ctx.at.record.app.query.reservation;

import lombok.Data;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTime;

@Data
public class ReservationClosingTimeDto {
	private String reservationTimeName;
	
	private Integer start;
	
	private int finish;
	
	public ReservationClosingTimeDto() {
		super();
	}
	
	public ReservationClosingTimeDto(String reservationTimeName, Integer optional, int finish) {
		super();
		this.reservationTimeName = reservationTimeName;
		this.finish = finish;
		this.start = optional;
	}
	
	public static ReservationClosingTimeDto fromDomain(ReservationClosingTime domain) {
		if (domain == null) return null;
		return new ReservationClosingTimeDto(domain.getReservationTimeName().v(), domain.getStart().isPresent() ? domain.getStart().get().v() : null,
				domain.getFinish().v());
	}
}
