package nts.uk.ctx.at.record.dom.reservation.bentomenu;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationCount;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationDetail;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationDate;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoItemByClosingTime;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.totalfee.BentoDetailsAmountTotal;

/**
 * 弁当
 * @author Doan Duy Hung
 *
 */
public class Bento {
	
	/**
	 * 枠番
	 */
	@Getter
	private final Integer frameNo;
	
	/**
	 * 弁当名
	 */
	@Getter
	private BentoName name;
	
	/**
	 * 金額１
	 */
	@Getter
	private BentoAmount amount1;
	
	/**
	 * 金額２
	 */
	@Getter
	private BentoAmount amount2;
	
	/**
	 * 単位
	 */
	@Getter
	private BentoReservationUnitName unit;
	
	/**
	 * 締め時刻1で予約可能
	 */
	@Getter
	private boolean reservationTime1Atr;
	
	/**
	 * 締め時刻2で予約可能
	 */
	@Getter
	private boolean reservationTime2Atr;
	
	public Bento(Integer frameNo, BentoName name, BentoAmount amount1, BentoAmount amount2,
			BentoReservationUnitName unit, boolean reservationTime1Atr, boolean reservationTime2Atr) {
		this.frameNo = frameNo; 
		this.name = name; 
		this.amount1 = amount1; 
		this.amount2 = amount2;
		this.unit = unit; 
		this.reservationTime1Atr = reservationTime1Atr;
		this.reservationTime2Atr = reservationTime2Atr;
	}
	
	/**
	 * 予約する
	 * @param reservationDate
	 * @param bentoCount
	 * @return
	 */
	public BentoReservationDetail reserve(ReservationDate reservationDate, BentoReservationCount bentoCount) {
		if(reservationDate.getClosingTimeFrame()==ReservationClosingTimeFrame.FRAME1 && !reservationTime1Atr) {
			throw new BusinessException("System Error");
		}
		if(reservationDate.getClosingTimeFrame()==ReservationClosingTimeFrame.FRAME2 && !reservationTime2Atr) {
			throw new BusinessException("System Error");
		}
		return BentoReservationDetail.createNew(frameNo, bentoCount);
	}
	
	/**
	 * 締め時刻別のメニュー項目
	 * @return
	 */
	public BentoItemByClosingTime itemByClosingTime() {
		return new BentoItemByClosingTime(frameNo, name, amount1, amount2, unit);
	}
	
	/**
	 * 金額を計算する
	 * @param quantity
	 * @return
	 */
	public BentoDetailsAmountTotal calculateAmount(Integer quantity) {
		return BentoDetailsAmountTotal.calculate(frameNo, quantity, amount1.v(), amount2.v());
	}
}
