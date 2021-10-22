package nts.uk.ctx.at.record.app.query.reservation;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoMenuByClosingTime;

@Data
public class BentoMenuByClosingTimeDto {
	private List<BentoItemByClosingTimeDto> menu1;
	
	private List<BentoItemByClosingTimeDto> menu2;
	
//	private ReservationClosingTimeDto closingTime1;
//	
//	private ReservationClosingTimeDto closingTime2;
	
	private boolean reservationTime1;
	
	private boolean reservationTime2;

	public BentoMenuByClosingTimeDto() {
		super();
	}

	public BentoMenuByClosingTimeDto(List<BentoItemByClosingTimeDto> menu1, List<BentoItemByClosingTimeDto> menu2,
//			ReservationClosingTimeDto closingTime1, ReservationClosingTimeDto closingTime2,
			boolean reservationTime1, boolean reservationTime2) {
		super();
		this.menu1 = menu1;
		this.menu2 = menu2;
//		this.closingTime1 = closingTime1;
//		this.closingTime2 = closingTime2;
		this.reservationTime1 = reservationTime1;
		this.reservationTime2 = reservationTime2;
	}
	
	public static BentoMenuByClosingTimeDto fromDomain(BentoMenuByClosingTime domain) {
		return new BentoMenuByClosingTimeDto(
				domain.getMenu1().stream().map(x -> BentoItemByClosingTimeDto.fromDomain(x)).collect(Collectors.toList()), 
				domain.getMenu2().stream().map(x -> BentoItemByClosingTimeDto.fromDomain(x)).collect(Collectors.toList()),
//				ReservationClosingTimeDto.fromDomain(domain.getClosingTime().getClosingTime1()),
//				ReservationClosingTimeDto.fromDomain(domain.getClosingTime().getClosingTime2().isPresent() ? domain.getClosingTime().getClosingTime2().get() : null),
				domain.isReservationTime1Atr(), 
				domain.isReservationTime2Atr()
				);
	}
	
}
