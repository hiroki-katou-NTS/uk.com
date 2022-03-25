package nts.uk.ctx.at.record.app.query.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationRecTime;

@AllArgsConstructor
@Getter
public class ReservationRecTimeDto {
	/**
	 * 	受付名称
	 */
	private String receptionName;
	
	/**
	 * 	開始時刻
	 */
	private int startTime;
	
	/**
	 * 	終了時刻
	 */
	private int endTime;
	
	public static ReservationRecTimeDto fromDomain(ReservationRecTime reservationRecTime) {
		return new ReservationRecTimeDto(
				reservationRecTime.getReceptionName().v(), 
				reservationRecTime.getStartTime().v(), 
				reservationRecTime.getEndTime().v());
	}
}
