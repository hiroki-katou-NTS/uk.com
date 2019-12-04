package nts.uk.ctx.at.record.dom.reservation.bentomenu;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservation;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationCount;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationDetail;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationTime;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationDate;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationRegisterInfo;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoReservationClosingTime;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.totalfee.BentoAmountTotal;

/**
 * 弁当メニュー
 * @author Doan Duy Hung
 *
 */
public class BentoMenu extends AggregateRoot {
	
	/**
	 * 履歴ID
	 */
	private final String historyID;
	
	/**
	 * メニュー
	 */
	private final List<Bento> menu;
	
	/**
	 * 締め時刻
	 */
	private final BentoReservationClosingTime closingTime;
	
	public BentoMenu(String historyID, List<Bento> menu, BentoReservationClosingTime closingTime) {
		this.historyID = historyID;
		this.menu = menu;
		this.closingTime = closingTime;
	}
	
	/**
	 * 予約する
	 * @param registerInfor
	 * @param reservationDate
	 * @param bentoDetails
	 * @return
	 */
	public BentoReservation getBentoReservation(ReservationRegisterInfo registerInfor, ReservationDate reservationDate, Map<Integer, BentoReservationCount> bentoDetails) {
		receptionCheck(registerInfor, reservationDate);
		List<BentoReservationDetail> bentoReservationDetails = bentoDetails.entrySet().stream()
				.map(x -> BentoReservationDetail.createNew(x.getKey(), x.getValue())).collect(Collectors.toList());
		return BentoReservation.createNew(registerInfor, reservationDate, bentoReservationDetails);
	}
	
	/**
	 * 締め時刻別のメニュー
	 * @return
	 */
	public BentoMenu getByClosingTime() {
		List<Bento> menu1 = menu.stream().filter(x -> x.isReservationTime1Atr()).collect(Collectors.toList());
		List<Bento> menu2 = menu.stream().filter(x -> x.isReservationTime2Atr()).collect(Collectors.toList());
		menu1.addAll(menu2);
		return new BentoMenu(historyID, menu1, closingTime);
	}
	
	/**
	 * 予約受付チェック
	 * @param registerInfor
	 * @param reservationDate
	 */
	public void receptionCheck(ReservationRegisterInfo registerInfor, ReservationDate reservationDate) {
		if(reservationDate.isPastDay()) {
			throw new BusinessException("Msg_1584");
		}
		
		// thời gian check tay hệ thống chưa có, khởi tạo tạm
		BentoReservationTime time = new BentoReservationTime(0);
		if(reservationDate.isToday() && !closingTime.canReserve(reservationDate.getClosingTimeFrame(), time)) {
			throw new BusinessException("Msg_1585");
		}
	}
	
	/**
	 * 合計金額を計算する
	 * @param frameNo
	 * @param quantity
	 * @return
	 */
	public BentoAmountTotal calculateTotalAmount(Integer frameNo, Integer quantity) {
		return menu.stream().filter(x -> x.getFrameNo()==frameNo).findAny()
				.map(x -> x.calculateAmount(quantity)).orElseGet(() -> { 
					throw new BusinessException("System Error"); 
				});
	}
	
	private BentoReservationDetail createBentoReservationDetail(Integer frameNo, BentoReservationCount count) {
		/*
		Optional<BentoReservation> opBentoReservation = menu
				.stream().filter(x -> x.getFrameNo()==frameNo).findAny()
				.map(x -> x.reserve(reservationDate, count));
		*/
		return null;
	}
}
