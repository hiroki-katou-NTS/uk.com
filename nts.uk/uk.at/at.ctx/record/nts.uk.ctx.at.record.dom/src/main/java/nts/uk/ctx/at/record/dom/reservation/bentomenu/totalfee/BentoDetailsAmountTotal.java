package nts.uk.ctx.at.record.dom.reservation.bentomenu.totalfee;

import lombok.Getter;

/**
 * 弁当合計金額明細
 * @author Doan Duy Hung
 *
 */
public class BentoDetailsAmountTotal {
	
	/**
	 * 	枠番
	 */
	@Getter
	private int frameNo;
	
	/**
	 * 金額１
	 */
	@Getter
	private final int amount1;
	
	/**
	 * 金額２
	 */
	@Getter
	private final int amount2;
	
	public BentoDetailsAmountTotal(int frameNo, int amount1, int amount2) {
		this.frameNo = frameNo;
		this.amount1 = amount1;
		this.amount2 = amount2;
	}
	
	/**
	 * 計算する
	 * @param frameNo
	 * @param quantity
	 * @param amount1
	 * @param amount2
	 * @return
	 */
	public static BentoDetailsAmountTotal calculate(int frameNo, int quantity, int amount1, int amount2) {
		return new BentoDetailsAmountTotal(
						frameNo, 
						quantity * amount1, 
						quantity * amount2);
	} 
}
