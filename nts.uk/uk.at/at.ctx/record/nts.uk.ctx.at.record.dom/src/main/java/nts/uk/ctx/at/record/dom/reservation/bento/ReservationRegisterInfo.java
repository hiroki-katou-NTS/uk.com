package nts.uk.ctx.at.record.dom.reservation.bento;

import lombok.Getter;

/**
 * 予約登録情報
 * @author Doan Duy Hung
 *
 */
public class ReservationRegisterInfo {
	
	/**
	 * 予約者のカード番号
	 */
	@Getter
	private	final String reservationCardNo;
	
	public ReservationRegisterInfo(String reservationCardNo) {
		this.reservationCardNo = reservationCardNo;
	}
}
