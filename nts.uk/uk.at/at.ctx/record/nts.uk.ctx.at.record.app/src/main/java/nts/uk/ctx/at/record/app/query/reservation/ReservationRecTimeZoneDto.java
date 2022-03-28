package nts.uk.ctx.at.record.app.query.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationRecTimeZone;

@AllArgsConstructor
@Getter
public class ReservationRecTimeZoneDto {
	/**
	 * 	受付時間帯
	 */
	private ReservationRecTimeDto receptionHours;
	
	/**
	 * 	枠NO
	 */
	private int frameNo;
	
	public static ReservationRecTimeZoneDto fromDomain(ReservationRecTimeZone reservationRecTimeZone) {
		return new ReservationRecTimeZoneDto(
				ReservationRecTimeDto.fromDomain(reservationRecTimeZone.getReceptionHours()), 
				reservationRecTimeZone.getFrameNo().value);
	}
}
