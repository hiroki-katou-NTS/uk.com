package nts.uk.ctx.at.record.app.query.reservation;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoMenuByClosingTime;

@Data
public class ReservationDto {
	private List<BentoReservationDto> listOrder;	
	
	private BentoMenuByClosingTimeDto bentoMenuByClosingTimeDto;

	public ReservationDto() {
		super();
	}

	public ReservationDto(List<BentoReservationDto> listOrder, BentoMenuByClosingTimeDto bentoMenuByClosingTimeDto) {
		super();
		this.listOrder = listOrder;
		this.bentoMenuByClosingTimeDto = bentoMenuByClosingTimeDto;
	}
	
}
