package nts.uk.ctx.at.record.dom.reservation.bento;

import org.eclipse.persistence.internal.xr.ValueObject;

import lombok.Getter;

import java.util.Collections;
import java.util.Optional;

/**
 * 予約登録情報
 * @author Doan Duy Hung
 *
 */
public class ReservationRegisterInfo extends ValueObject {
	
	/**
	 * 予約者のカード番号
	 */
	@Getter
	private	final String reservationCardNo;
	
	public ReservationRegisterInfo(String reservationCardNo) {
		this.reservationCardNo = reservationCardNo;
	}

	public BentoReservation convertToBentoReservation(ReservationRegisterInfo reservationRegisterInfo,ReservationDate reservationDate){
		return new BentoReservation(reservationRegisterInfo,reservationDate,false, Optional.empty(), Collections.emptyList());
	}
}
