package nts.uk.ctx.at.record.app.query.reservation;

import java.util.List;

import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoMenuByClosingTime;

public class ReservationDto {
	private List<BentoReservationDto> listOder;	
	
	private BentoMenuByClosingTime bentoMenuByClosingTime;

	public ReservationDto() {
		super();
	}

	public ReservationDto(List<BentoReservationDto> listOder, BentoMenuByClosingTime bentoMenuByClosingTime) {
		super();
		this.listOder = listOder;
		this.bentoMenuByClosingTime = bentoMenuByClosingTime;
	}
	
}
