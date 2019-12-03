package nts.uk.ctx.at.record.dom.reservation.bentomenu;

import lombok.Getter;

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
	private final BentoMenuFrameNumber frameNo;
	
	/**
	 * 弁当名
	 */
	@Getter
	private final BentoName name;
	
	/**
	 * 金額１
	 */
	@Getter
	private final BentoAmount amount1;
	
	/**
	 * 金額２
	 */
	@Getter
	private final BentoAmount amount2;
	
	/**
	 * 単位
	 */
	@Getter
	private final BentoReservationUnitName unit;
	
	/**
	 * 締め時刻1で予約可能
	 */
	@Getter
	private final boolean reservationTime1Atr;
	
	/**
	 * 締め時刻2で予約可能
	 */
	@Getter
	private final boolean reservationTime2Atr;
	
	public Bento(BentoMenuFrameNumber frameNo, BentoName name, BentoAmount amount1, BentoAmount amount2,
			BentoReservationUnitName unit, boolean reservationTime1Atr, boolean reservationTime2Atr) {
		this.frameNo = frameNo; 
		this.name = name; 
		this.amount1 = amount1; 
		this.amount2 = amount2;
		this.unit = unit; 
		this.reservationTime1Atr = reservationTime1Atr;
		this.reservationTime2Atr = reservationTime2Atr;
	}
}
