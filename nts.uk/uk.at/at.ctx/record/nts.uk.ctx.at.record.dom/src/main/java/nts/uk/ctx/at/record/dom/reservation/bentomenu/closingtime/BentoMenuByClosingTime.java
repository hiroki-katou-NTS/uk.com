package nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.clock.ClockHourMinute;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.Bento;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationRecTimeZone;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationSetting;

/**
 * 	締め時刻別の弁当メニュー
 * @author Doan Duy Hung
 *
 */
public class BentoMenuByClosingTime {
	
	/**
	 * メニュー1
	 */
	@Getter
	private final List<BentoItemByClosingTime> menu1;
	
	/**
	 * メニュー2
	 */
	@Getter
	private final List<BentoItemByClosingTime> menu2;
	
	/**
	 * 締め時刻
	 */
	@Getter
	private final List<ReservationRecTimeZone> reservationRecTimeZoneLst;
	
	/**
	 * 締め時刻1が受付中
	 */
	@Getter
	private final boolean reservationTime1Atr;
	
	/**
	 * 締め時刻2が受付中
	 */
	@Getter
	private final boolean reservationTime2Atr;
	
	public BentoMenuByClosingTime(List<BentoItemByClosingTime> menu1, List<BentoItemByClosingTime> menu2, List<ReservationRecTimeZone> reservationRecTimeZoneLst,
			boolean reservationTime1Atr, boolean reservationTime2Atr) {
		this.menu1 = menu1;
		this.menu2 = menu2;
		this.reservationRecTimeZoneLst = reservationRecTimeZoneLst;
		this.reservationTime1Atr = reservationTime1Atr;
		this.reservationTime2Atr = reservationTime2Atr;
	}
	
	/**
	 * 現在時刻で作る
	 * @param roleID ロールID
	 * @param reservationSetting 予約設定
	 * @param bentoLst 弁当
	 * @param orderAtr 発注区分
	 * @param orderDate 注文日
	 * @return
	 */
	public static BentoMenuByClosingTime createForCurrent(String roleID, ReservationSetting reservationSetting, List<Bento> bentoLst, 
			Map<ReservationClosingTimeFrame, Boolean> orderAtr, GeneralDate orderDate) {
		ClockHourMinute reservationTime = ClockHourMinute.now();
		List<ReservationRecTimeZone> reservationRecTimeZoneLst = reservationSetting.getReservationRecTimeZoneLst();
		
		ReservationRecTimeZone reservationRecTimeZone1 = reservationSetting.getReservationRecTimeZoneLst().stream()
				.filter(x -> x.getFrameNo()==ReservationClosingTimeFrame.FRAME1).findAny().orElse(null);
		ReservationRecTimeZone reservationRecTimeZone2 = reservationSetting.getReservationRecTimeZoneLst().stream()
				.filter(x -> x.getFrameNo()==ReservationClosingTimeFrame.FRAME2).findAny().orElse(null);
		boolean reservationTime1Atr = false;
		boolean reservationTime2Atr = false;
		if(reservationRecTimeZone1!=null) {
			reservationTime1Atr = reservationSetting.getCorrectionContent().canEmployeeChangeReservation(
					roleID, 
					GeneralDate.today(), 
					reservationTime, 
					1, 
					orderDate, 
					orderAtr.get(ReservationClosingTimeFrame.FRAME1)!=null?orderAtr.get(ReservationClosingTimeFrame.FRAME1):false, 
					reservationRecTimeZone1);
		}
		if(reservationRecTimeZone2!=null) {
			reservationTime2Atr = reservationSetting.getCorrectionContent().canEmployeeChangeReservation(
					roleID, 
					GeneralDate.today(), 
					reservationTime, 
					2, 
					orderDate, 
					orderAtr.get(ReservationClosingTimeFrame.FRAME2)!=null?orderAtr.get(ReservationClosingTimeFrame.FRAME2):false, 
					reservationRecTimeZone2);
		}
		
		List<BentoItemByClosingTime> menu1 = bentoLst.stream().filter(x -> x.getReceptionTimezoneNo()==ReservationClosingTimeFrame.FRAME1)
				.map(x -> new BentoItemByClosingTime(x.getFrameNo(), x.getName(), x.getAmount1(), x.getAmount2(), x.getUnit()))
				.collect(Collectors.toList());
		List<BentoItemByClosingTime> menu2 = bentoLst.stream().filter(x -> x.getReceptionTimezoneNo()==ReservationClosingTimeFrame.FRAME2)
				.map(x -> new BentoItemByClosingTime(x.getFrameNo(), x.getName(), x.getAmount1(), x.getAmount2(), x.getUnit()))
				.collect(Collectors.toList());
		
		return new BentoMenuByClosingTime(menu1, menu2, reservationRecTimeZoneLst, reservationTime1Atr, reservationTime2Atr);
	}
}
