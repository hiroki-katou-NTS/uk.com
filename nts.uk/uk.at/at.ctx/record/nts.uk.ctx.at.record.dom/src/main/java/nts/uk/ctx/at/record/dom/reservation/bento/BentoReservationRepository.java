package nts.uk.ctx.at.record.dom.reservation.bento;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;

import java.util.List;
import java.util.Optional;

public interface BentoReservationRepository {

	Optional<BentoReservation> find(ReservationRegisterInfo registerInfor, ReservationDate reservationDate);

	List<BentoReservation> findList(ReservationRegisterInfo registerInfor, ReservationDate reservationDate);

	void add(BentoReservation bentoReservation);

	void delete(BentoReservation bentoReservation);

	List<BentoReservation> findByOrderedPeriodEmpLst(List<ReservationRegisterInfo> inforLst, DatePeriod period, boolean ordered, String companyID);

	/**
	 * 注文状態から予約内容を取得する
	 */
	List<BentoReservation> getReservationDetailFromOrder(
			List<ReservationRegisterInfo> inforLst, DatePeriod period,ReservationClosingTimeFrame closingTimeFrame,
			boolean ordered,List<WorkLocationCode> workLocationCode);

	/**
	 * 全て予約内容を取得
	 */
	List<BentoReservation> getAllReservationDetail(
			List<ReservationRegisterInfo> inforLst, DatePeriod period,ReservationClosingTimeFrame closingTimeFrame,
			List<WorkLocationCode> workLocationCode);

	/**
	 * １商品２件以上の予約内容を取得する
	 */
	List<BentoReservation> acquireReservationDetails (
			List<ReservationRegisterInfo> inforLst, DatePeriod period,ReservationClosingTimeFrame closingTimeFrame,
			List<WorkLocationCode> workLocationCode);

	/**
	 * 注文してない社員IDを取得する
	 */
	List<ReservationRegisterInfo> getEmployeeNotOrder (List<ReservationRegisterInfo> inforLst, ReservationDate reservationDate);

	/**
	 * List＜予約登録情報＞から取得する
	 */
	List<BentoReservation> getReservationInformation (List<ReservationRegisterInfo> inforLst, ReservationDate reservationDate);


	void update(BentoReservation bentoReservation);

	List<BentoReservation> getAllReservationOfBento(int frameNo,
			List<ReservationRegisterInfo> inforLst, DatePeriod period,ReservationClosingTimeFrame closingTimeFrame,
			List<WorkLocationCode> workLocationCode);
}
