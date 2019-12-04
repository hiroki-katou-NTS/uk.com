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
	private Integer frameNo;
	
	/**
	 * 金額１
	 */
	@Getter
	private final Integer amount1;
	
	/**
	 * 金額２
	 */
	@Getter
	private final Integer amount2;
	
	public BentoDetailsAmountTotal(Integer frameNo, Integer amount1, Integer amount2) {
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
	public static BentoDetailsAmountTotal calculate(Integer frameNo, Integer quantity, Integer amount1, Integer amount2) {
		return new BentoDetailsAmountTotal(
						frameNo, 
						quantity * amount1, 
						quantity * amount2);
	} 
}
