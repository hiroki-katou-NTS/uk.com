package nts.uk.ctx.at.record.dom.reservation.bentomenu;

import java.util.List;

import nts.uk.ctx.at.record.dom.reservation.bento.ReservationDate;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationRegisterInfo;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoReservationClosingTime;

/**
 * 弁当メニュー
 * @author Doan Duy Hung
 *
 */
public class BentoMenu {
	
	/**
	 * 履歴ID
	 */
	private String historyID;
	
	/**
	 * メニュー
	 */
	private List<Bento> menu;
	
	/**
	 * 締め時刻
	 */
	private BentoReservationClosingTime closingTime;
	
	public BentoMenu(String historyID, List<Bento> menu, BentoReservationClosingTime closingTime) {
		this.historyID = historyID;
		this.menu = menu;
		this.closingTime = closingTime;
	}
	
	/**
	 * 予約受付チェック
	 * @param registerInfor
	 * @param reservationDate
	 */
	public void receptionCheck(ReservationRegisterInfo registerInfor, ReservationDate reservationDate) {
		
	}
}
