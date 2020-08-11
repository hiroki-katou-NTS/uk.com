package nts.uk.ctx.at.record.dom.reservation.bentomenu;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.reservation.bento.*;
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
		// inv-1	1 <= ＠メニュー.size <= 40
		if(menu.size() <= 0 || menu.size() > 40) {
			throw new RuntimeException("System error");
		}
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
	public BentoReservation reserve(ReservationRegisterInfo registerInfor, ReservationDate reservationDate, GeneralDateTime dateTime,
									Optional<WorkLocationCode> workLocationCode,Map<Integer, BentoReservationCount> bentoDetails) {
		receptionCheck(dateTime, reservationDate);
		List<BentoReservationDetail> bentoReservationDetails = bentoDetails.entrySet().stream()
				.map(x -> createBentoReservationDetail(reservationDate, x.getKey(), x.getValue(), dateTime)).collect(Collectors.toList());
		return BentoReservation.reserve(registerInfor, reservationDate,workLocationCode, bentoReservationDetails);
	}
	
	/**
	 * 締め時刻別のメニュー
	 * @return
	 */
	public BentoMenuByClosingTime getByClosingTime(Optional<WorkLocationCode> workLocationCode) {
		List<BentoItemByClosingTime> menu1 = menu.stream().filter(x -> x.isReservationTime1Atr() && x.getWorkLocationCode().equals(workLocationCode))
				.map(x -> x.itemByClosingTime())
				.collect(Collectors.toList());
		List<BentoItemByClosingTime> menu2 = menu.stream().filter(x -> x.isReservationTime2Atr() && x.getWorkLocationCode().equals(workLocationCode))
				.map(x -> x.itemByClosingTime())
				.collect(Collectors.toList());
		return BentoMenuByClosingTime.createForCurrent(closingTime, menu1, menu2);
	}
	
	/**
	 * 予約受付チェック
	 * @param dateTime
	 * @param reservationDate
	 */
	public void receptionCheck(GeneralDateTime dateTime, ReservationDate reservationDate) {
		if(reservationDate.isPastDay()) {
			throw new BusinessException("Msg_1584");
		}
		
		if(reservationDate.isToday() && !closingTime.canReserve(reservationDate.getClosingTimeFrame(), dateTime.clockHourMinute())) {
			throw new BusinessException("Msg_1585");
		}
	}
	
	/**
	 * 合計金額を計算する
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
	 * @param dateTime
	 * @return
	 */
	private BentoReservationDetail createBentoReservationDetail(ReservationDate reservationDate, int frameNo, BentoReservationCount count, GeneralDateTime dateTime) {
		return menu.stream().filter(x -> x.getFrameNo()==frameNo).findAny()
				.map(x -> x.reserve(reservationDate, count, dateTime)).get();
	}
	
	/**
	 * 金額を計算する
	 * @param frameNo
	 * @param quantity
	 * @return
	 */
	private BentoDetailsAmountTotal calculateAmount(int frameNo, int quantity) {
		return menu.stream().filter(x -> x.getFrameNo()==frameNo).findAny()
				.map(x -> x.calculateAmount(quantity)).orElseGet(() -> {
					throw new RuntimeException("System Error");
				});
	}

}
