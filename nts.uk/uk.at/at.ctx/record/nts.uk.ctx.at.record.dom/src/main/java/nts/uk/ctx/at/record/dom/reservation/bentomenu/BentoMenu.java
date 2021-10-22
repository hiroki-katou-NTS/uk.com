package nts.uk.ctx.at.record.dom.reservation.bentomenu;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservation;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationCount;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationDetail;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationDate;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationRegisterInfo;
import nts.uk.ctx.at.record.dom.reservation.bento.WorkLocationCode;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.totalfee.BentoAmountTotal;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.totalfee.BentoDetailsAmountTotal;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationRecTimeZone;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.支給賞与額履歴.予約.弁当メニュー.弁当メニュー
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
	
	public BentoMenu(String historyID, List<Bento> menu) {
		// inv-1	1 <= ＠メニュー.size <= 40
		if(menu.size() <= 0 || menu.size() > 40) {
			throw new RuntimeException("System error");
		}
		// 	inv-2	@メニュー.枠番が重複しないこと	
		if(CollectionUtil.isEmpty(menu)) {
			throw new RuntimeException("System error");
		}
		List<Integer> frameNoLst = menu.stream().map(x -> x.getFrameNo()).collect(Collectors.toList());
		List<Integer> frameNoDistinctLst = frameNoLst.stream().distinct().collect(Collectors.toList());
		if(frameNoLst.size()!=frameNoDistinctLst.size()) {
			throw new RuntimeException("System error");
		}
		this.historyID = historyID;
		this.menu = menu;
	}
	
	/**
	 * [1] 予約する
	 * @param registerInfor 登録情報
	 * @param reservationDate 対象日
	 * @param dateTime 予約登録日時
	 * @param workLocationCode 勤務場所コード
	 * @param bentoDetails 明細
	 * @param reservationRecTimeZone 予約受付時間帯
	 * @return
	 */
	public BentoReservation reserve(ReservationRegisterInfo registerInfor, ReservationDate reservationDate, GeneralDateTime dateTime, Optional<WorkLocationCode> workLocationCode, 
			Map<Integer, BentoReservationCount> bentoDetails, ReservationRecTimeZone reservationRecTimeZone) {
		receptionCheck(dateTime, reservationRecTimeZone, reservationDate);
		List<BentoReservationDetail> bentoReservationDetails = bentoDetails.entrySet().stream()
				.map(x -> createBentoReservationDetail(reservationDate, x.getKey(), x.getValue(), dateTime)).collect(Collectors.toList());
		return BentoReservation.reserve(registerInfor, reservationDate,workLocationCode, bentoReservationDetails);
	}
	
//	/**
//	 * 締め時刻別のメニュー
//	 * @return
//	 */
//	public BentoMenuByClosingTime getByClosingTime(Optional<WorkLocationCode> workLocationCode) {
//		List<BentoItemByClosingTime> menu1 = menu.stream().filter(x -> x.isReservationTime1Atr() && x.getWorkLocationCode().equals(workLocationCode))
//				.map(x -> x.itemByClosingTime())
//				.collect(Collectors.toList());
//		List<BentoItemByClosingTime> menu2 = menu.stream().filter(x -> x.isReservationTime2Atr() && x.getWorkLocationCode().equals(workLocationCode))
//				.map(x -> x.itemByClosingTime())
//				.collect(Collectors.toList());
//		return BentoMenuByClosingTime.createForCurrent(closingTime, menu1, menu2);
//	}
	
	/**
	 * 予約受付チェック
	 * @param dateTime 予約登録日時
	 * @param reservationRecTimeZone 予約受付時間帯
	 * @param reservationDate 対象日
	 */
	public void receptionCheck(GeneralDateTime dateTime, ReservationRecTimeZone reservationRecTimeZone, ReservationDate reservationDate) {
		if(!reservationRecTimeZone.canMakeReservation(
				EnumAdaptor.valueOf(reservationDate.getClosingTimeFrame().value, ReservationClosingTimeFrame.class), 
				reservationDate.getDate(), 
				dateTime.clockHourMinute())) {
			throw new BusinessException("Msg_2287");
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
