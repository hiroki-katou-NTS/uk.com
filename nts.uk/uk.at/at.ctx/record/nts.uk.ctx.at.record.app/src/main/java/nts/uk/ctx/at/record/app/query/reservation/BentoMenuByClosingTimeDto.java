package nts.uk.ctx.at.record.app.query.reservation;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoMenuByClosingTime;

@AllArgsConstructor
@Getter
public class BentoMenuByClosingTimeDto {
	private List<BentoItemByClosingTimeDto> menu1;
	
	private List<BentoItemByClosingTimeDto> menu2;
	
	private final List<ReservationRecTimeZoneDto> reservationRecTimeZoneLst;
	
	private boolean reservationTime1;
	
	private boolean reservationTime2;
	
	public static BentoMenuByClosingTimeDto fromDomain(BentoMenuByClosingTime domain) {
		return new BentoMenuByClosingTimeDto(
				domain.getMenu1().stream().map(x -> BentoItemByClosingTimeDto.fromDomain(x)).collect(Collectors.toList()), 
				domain.getMenu2().stream().map(x -> BentoItemByClosingTimeDto.fromDomain(x)).collect(Collectors.toList()),
				domain.getReservationRecTimeZoneLst().stream().map(x -> ReservationRecTimeZoneDto.fromDomain(x)).collect(Collectors.toList()),
				domain.isReservationTime1Atr(), 
				domain.isReservationTime2Atr()
				);
	}
	
}
