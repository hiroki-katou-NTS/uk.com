package nts.uk.ctx.at.record.app.query.reservation;

import java.util.List;

import lombok.Data;

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
