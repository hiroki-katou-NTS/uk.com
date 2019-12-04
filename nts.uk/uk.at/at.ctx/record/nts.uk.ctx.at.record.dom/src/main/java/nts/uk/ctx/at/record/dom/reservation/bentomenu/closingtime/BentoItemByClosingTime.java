package nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoAmount;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoName;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoReservationUnitName;

/**
 * 締め時刻別の弁当メニュー項目
 * @author Doan Duy Hung
 *
 */
public class BentoItemByClosingTime {
	
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
	
	public BentoItemByClosingTime(Integer frameNo, BentoName name, BentoAmount amount1, BentoAmount amount2, BentoReservationUnitName unit) {
		this.frameNo = frameNo;
		this.name = name;
		this.amount1 = amount1;
		this.amount2 = amount2;
		this.unit = unit;
	}
}
