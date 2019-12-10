package nts.uk.ctx.at.record.app.query.reservation;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoMenuByClosingTime;

@Data
public class ReservationDto {
	private List<BentoReservationDto> listOder;	
	
	private BentoMenuByClosingTimeDto bentoMenuByClosingTimeDto;

	public ReservationDto() {
		super();
	}

	public ReservationDto(List<BentoReservationDto> listOder, BentoMenuByClosingTimeDto bentoMenuByClosingTimeDto) {
		super();
		this.listOder = listOder;
		this.bentoMenuByClosingTimeDto = bentoMenuByClosingTimeDto;
	}
	
}
