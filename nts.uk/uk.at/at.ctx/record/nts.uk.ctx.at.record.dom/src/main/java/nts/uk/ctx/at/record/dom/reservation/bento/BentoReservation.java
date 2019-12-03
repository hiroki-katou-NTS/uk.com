package nts.uk.ctx.at.record.dom.reservation.bento;

import java.util.List;

import nts.arc.layer.dom.AggregateRoot;

/**
 * 弁当予約
 * @author Doan Duy Hung
 *
 */
public class BentoReservation extends AggregateRoot{
	
	/**
	 * 予約登録情報
	 */
	private final ReservationRegisterInfo registerInfor;
	
	/**
	 * 予約対象日
	 */
	private final ReservationDate reservationDate;
	
	/**
	 * 注文済み
	 */
	private final boolean ordered;
	
	/**
	 * 弁当予約明細リスト
	 */
	private final List<BentoReservationDetail> bentoReservationDetails;
	
	public BentoReservation(ReservationRegisterInfo registerInfor, ReservationDate reservationDate, boolean ordered, 
			List<BentoReservationDetail> bentoReservationDetails) {
		this.registerInfor = registerInfor;
		this.reservationDate = reservationDate;
		this.ordered = ordered;
		this.bentoReservationDetails = bentoReservationDetails;
	} 
	
	public static BentoReservation createNew(ReservationRegisterInfo registerInfor, ReservationDate reservationDate, List<BentoReservationDetail> bentoReservationDetails) {
		return new BentoReservation(registerInfor, reservationDate, false, bentoReservationDetails);
	}
}
