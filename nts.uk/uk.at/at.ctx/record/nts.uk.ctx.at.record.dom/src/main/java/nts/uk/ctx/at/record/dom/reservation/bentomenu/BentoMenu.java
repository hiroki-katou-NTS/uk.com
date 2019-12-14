package nts.uk.ctx.at.record.dom.reservation.bentomenu;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.ClockHourMinute;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservation;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationCount;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationDetail;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationDate;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationRegisterInfo;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoItemByClosingTime;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoMenuByClosingTime;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoReservationClosingTime;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.totalfee.BentoAmountTotal;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.totalfee.BentoDetailsAmountTotal;

/**
 * 弁当メニュー
 * @author Doan Duy Hung
 *
 */
public class BentoMenu extends AggregateRoot {
	
	/**
	 * 履歴ID
	 */
	@Getter
	private final String historyID;
	
	/**
	 * メニュー
	 */
	@Getter
	private final List<Bento> menu;
	
	/**
	 * 締め時刻
	 */
	@Getter
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
	public BentoReservation reserve(ReservationRegisterInfo registerInfor, ReservationDate reservationDate, Map<Integer, BentoReservationCount> bentoDetails) {
		receptionCheck(registerInfor, reservationDate);
		List<BentoReservationDetail> bentoReservationDetails = bentoDetails.entrySet().stream()
				.map(x -> createBentoReservationDetail(reservationDate, x.getKey(), x.getValue())).collect(Collectors.toList());
		return BentoReservation.reserve(registerInfor, reservationDate, bentoReservationDetails);
	}
	
	/**
	 * 締め時刻別のメニュー
	 * @return
	 */
	public BentoMenuByClosingTime getByClosingTime() {
		List<BentoItemByClosingTime> menu1 = menu.stream().filter(x -> x.isReservationTime1Atr())
				.map(x -> x.itemByClosingTime())
				.collect(Collectors.toList());
		List<BentoItemByClosingTime> menu2 = menu.stream().filter(x -> x.isReservationTime2Atr())
				.map(x -> x.itemByClosingTime())
				.collect(Collectors.toList());
		menu1.addAll(menu2);
		return BentoMenuByClosingTime.createForCurrent(closingTime, menu1, menu2);
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
		ClockHourMinute time = ClockHourMinute.now();
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
	public BentoAmountTotal calculateTotalAmount(Map<Integer, Integer> bentoDetails) {
		List<BentoDetailsAmountTotal> detailsAmountTotal = bentoDetails.entrySet().stream()
				.map(x -> calculateAmount(x.getKey(), x.getValue())).collect(Collectors.toList());
		return BentoAmountTotal.createNew(detailsAmountTotal);
	}
	
	/**
	 * 弁当予約明細を作成する
	 * @param reservationDate
	 * @param frameNo
	 * @param count
	 * @return
	 */
	private BentoReservationDetail createBentoReservationDetail(ReservationDate reservationDate, Integer frameNo, BentoReservationCount count) {
		return menu.stream().filter(x -> x.getFrameNo()==frameNo).findAny()
				.map(x -> x.reserve(reservationDate, count)).get();
	}
	
	/**
	 * 金額を計算する
	 * @param frameNo
	 * @param quantity
	 * @return
	 */
	private BentoDetailsAmountTotal calculateAmount(Integer frameNo, Integer quantity) {
		return menu.stream().filter(x -> x.getFrameNo()==frameNo).findAny()
				.map(x -> x.calculateAmount(quantity)).orElseGet(() -> {
					throw new RuntimeException("System Error");
				});
	}
}
