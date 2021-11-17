package nts.uk.ctx.at.record.app.query.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoItemByClosingTime;

@AllArgsConstructor
@Getter
public class BentoItemByClosingTimeDto {
	/**
	 * 枠番
	 */
	private int frameNo;
	/**
	 * 弁当名
	 */
	private String name;
	/**
	 * 金額１
	 */
	private int amount1;
	/**
	 * 金額２
	 */
	private int amount2;
	/**
	 * 単位
	 */
	private String unit;
	
	public static BentoItemByClosingTimeDto fromDomain(BentoItemByClosingTime domain) {
		return new BentoItemByClosingTimeDto(
				domain.getFrameNo(), 
				domain.getName().v(), 
				domain.getAmount1().v(), 
				domain.getAmount2().v(), 
				domain.getUnit().v());
	}
}
