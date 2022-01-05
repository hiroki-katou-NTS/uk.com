package nts.uk.ctx.at.record.dom.reservation.bento;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.支給賞与額履歴.予約.弁当予約.弁当予約
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
	 * 発注済み
	 */
	@Getter
	@Setter
	private boolean ordered;

	/**
	 * 勤務場所コード
	 */
	@Getter
	private Optional<WorkLocationCode> workLocationCode;

	/**
	 * 弁当予約明細リスト
	 */
	@Getter
	private List<BentoReservationDetail> bentoReservationDetails;
	
	public BentoReservation(ReservationRegisterInfo registerInfor, ReservationDate reservationDate, boolean ordered,
							Optional<WorkLocationCode> workLocationCode,List<BentoReservationDetail> bentoReservationDetails) {
		// [inv-1] @明細リスト.size > 0
		if(bentoReservationDetails.size() <= 0) {
			throw new RuntimeException("System error");
		}
		this.registerInfor = registerInfor;
		this.reservationDate = reservationDate;
		this.ordered = ordered;
		this.workLocationCode = workLocationCode;
		this.bentoReservationDetails = bentoReservationDetails;
	}
	/**
	 * 予約する
	 * @param registerInfor
	 * @param reservationDate
	 * @param bentoReservationDetails
	 * @return
	 */
	public static BentoReservation reserve(ReservationRegisterInfo registerInfor, ReservationDate reservationDate,
										   Optional<WorkLocationCode> workLocationCode,List<BentoReservationDetail> bentoReservationDetails) {
		return new BentoReservation(registerInfor, reservationDate, false,workLocationCode, bentoReservationDetails);
	}

	/**
	 * 予約を取り消す
	 * @param dateTime
	 * @return
	 */
	public Optional<BentoReservation> cancelReservation(GeneralDateTime dateTime) {
		checkCancelPossible();
		List<BentoReservationDetail> afterDetails = bentoReservationDetails.stream().filter(x -> !x.getDateTime().equals(dateTime)).collect(Collectors.toList());
		if(CollectionUtil.isEmpty(afterDetails)) {
			return Optional.empty();
		} else {
			return Optional.of(reserve(registerInfor, reservationDate,workLocationCode, afterDetails));
		}
	}

	/**
	 * 取消可能かチェックする
	 */
	public void checkCancelPossible() {
		if(ordered) {
			throw new BusinessException("Msg_1586");
		}
	}

	/**
	 * 強制弁当予約する
	 */
	public static BentoReservation bookLunch(ReservationRegisterInfo registerInfor, ReservationDate reservationDate,
			Optional<WorkLocationCode> workLocationCode,List<BentoReservationDetail> bentoReservationDetails)
	{
		return new BentoReservation(registerInfor, reservationDate, false,workLocationCode, bentoReservationDetails);
	}

	/**
	 * 強制弁当予約修正する
	 */
	public BentoReservation modifyLunch(boolean ordered, BentoReservationDetail bentoReservationDetail) {
	    this.ordered = ordered;
		this.bentoReservationDetails.add(bentoReservationDetail);
        return this;
	}

}
