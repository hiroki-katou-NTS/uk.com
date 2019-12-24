package nts.uk.ctx.at.record.dom.reservation.bento;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;

/**
 * 弁当予約
 * @author Doan Duy Hung
 *
 */
public class BentoReservation extends AggregateRoot{
	
	/**
	 * 予約登録情報
	 */
	@Getter
	private final ReservationRegisterInfo registerInfor;
	
	/**
	 * 予約対象日
	 */
	@Getter
	private final ReservationDate reservationDate;
	
	/**
	 * 注文済み
	 */
	@Getter
	private boolean ordered;
	
	/**
	 * 弁当予約明細リスト
	 */
	@Getter
	private final List<BentoReservationDetail> bentoReservationDetails;
	
	public BentoReservation(ReservationRegisterInfo registerInfor, ReservationDate reservationDate, boolean ordered, 
			List<BentoReservationDetail> bentoReservationDetails) {
		// [inv-1] @明細リスト.size > 0
		if(bentoReservationDetails.size() <= 0) {
			throw new RuntimeException("System error");
		}
		this.registerInfor = registerInfor;
		this.reservationDate = reservationDate;
		this.ordered = ordered;
		this.bentoReservationDetails = bentoReservationDetails;
	} 
	
	/**
	 * 予約する
	 * @param registerInfor
	 * @param reservationDate
	 * @param bentoReservationDetails
	 * @return
	 */
	public static BentoReservation reserve(ReservationRegisterInfo registerInfor, ReservationDate reservationDate, List<BentoReservationDetail> bentoReservationDetails) {
		return new BentoReservation(registerInfor, reservationDate, false, bentoReservationDetails);
	}
	
	/**
	 * 予約を取り消す
	 * @param dateTime
	 * @return
	 */
	public Optional<BentoReservation> cancelReservation(GeneralDateTime dateTime) {
		isCancel();
		List<BentoReservationDetail> afterDetails = bentoReservationDetails.stream().filter(x -> !x.getDateTime().equals(dateTime)).collect(Collectors.toList());
		if(CollectionUtil.isEmpty(afterDetails)) {
			return Optional.empty();
		} else {
			return Optional.of(reserve(registerInfor, reservationDate, afterDetails));
		}
	}
	
	/**
	 * 取消可能かチェックする
	 */
	public void isCancel() {
		if(ordered) {
			throw new BusinessException("Msg_1586");
		}
	}
}
