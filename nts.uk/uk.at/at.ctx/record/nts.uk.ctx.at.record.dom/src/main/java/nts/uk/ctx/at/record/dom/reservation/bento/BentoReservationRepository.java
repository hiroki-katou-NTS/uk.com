package nts.uk.ctx.at.record.dom.reservation.bento;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;

public interface BentoReservationRepository {

	public Optional<BentoReservation> find(ReservationRegisterInfo registerInfor, ReservationDate reservationDate);

	public List<BentoReservation> findList(ReservationRegisterInfo registerInfor, ReservationDate reservationDate);

	public void add(BentoReservation bentoReservation);

	public void delete(BentoReservation bentoReservation);

	public List<BentoReservation> findByOrderedPeriodEmpLst(List<ReservationRegisterInfo> inforLst, DatePeriod period, boolean ordered);

	/**
	 * 注文状態から予約内容を取得する
	 */
	public List<BentoReservation> getReservationDetailFromOrder(
			List<ReservationRegisterInfo> inforLst, DatePeriod period,ReservationClosingTimeFrame closingTimeFrame,
			boolean ordered,List<WorkLocationCode> workLocationCode);

	/**
	 * 全て予約内容を取得
	 */
	public List<BentoReservation> getAllReservationDetail(
			List<ReservationRegisterInfo> inforLst, DatePeriod period,ReservationClosingTimeFrame closingTimeFrame,
			List<WorkLocationCode> workLocationCode);

	/**
	 * １商品２件以上の予約内容を取得する
	 */
	public List<BentoReservation> acquireReservationDetails (
			List<ReservationRegisterInfo> inforLst, DatePeriod period,ReservationClosingTimeFrame closingTimeFrame,
			List<WorkLocationCode> workLocationCode);

	/**
	 * 注文してない社員IDを取得する
	 */
	public List<BentoReservation> getEmployeeNotOrder (List<ReservationRegisterInfo> inforLst, ReservationDate reservationDate);

	/**
	 * List＜予約登録情報＞から取得する
	 */
	public List<BentoReservation> getReservationInformation (List<ReservationRegisterInfo> inforLst, ReservationDate reservationDate);


	public void update(BentoReservation bentoReservation);
}
